package de.failender.dgo.rest.helden;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.rest.integration.Beans;
import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.api.authentication.TokenAuthentication;
import de.failender.heldensoftware.api.requests.GetAllHeldenRequest;
import de.failender.heldensoftware.api.requests.ReturnHeldDatenWithEreignisseRequest;
import de.failender.heldensoftware.api.requests.ReturnHeldPdfRequest;
import de.failender.heldensoftware.api.requests.ReturnHeldXmlRequest;
import de.failender.heldensoftware.xml.datenxml.Daten;
import de.failender.heldensoftware.xml.heldenliste.Held;
import io.javalin.Context;
import org.apache.commons.io.IOUtils;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

public class HeldenService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HeldenService.class);

    public static SynchronizationResult updateHeldenForUser(UserEntity userEntity) {
		if (userEntity.getToken() == null || userEntity.getToken().isEmpty()) {
            log.error("User with getName {} has null token ", userEntity.getName());
            return null;
        }
        List<Held> helden = Beans.HELDEN_API.request(new GetAllHeldenRequest(new TokenAuthentication(userEntity.getToken())), false).block().getHeld();
        return updateHeldenForUser(userEntity, helden);
    }

    public static SynchronizationResult updateHeldenForUser(UserEntity userEntity, List<Held> helden) {
        SynchronizationResult result = new SynchronizationResult();

        log.debug("Updating helden for user {}, online found {}", userEntity.getName(), helden.size());
        HeldRepositoryService.findByUserId(userEntity.getId()).forEach(heldEntity -> {

            Optional<Held> heldOptional = helden.stream().filter(_held -> _held.getHeldenid().equals(heldEntity.getId())).findFirst();
            if (!heldOptional.isPresent()) {
                log.debug("Held with Name {} is no longer online, disabling it", heldEntity.getName());
                heldEntity.setDeleted(true);
                HeldRepositoryService.saveHeld(heldEntity);
            } else {
                if (heldEntity.isDeleted()) {
                    heldEntity.setDeleted(false);
                    log.debug("Held with Name {} is no online again, enabling it", heldEntity.getName());
                    HeldRepositoryService.saveHeld(heldEntity);
                }
                Held xmlHeld = heldOptional.get();
                helden.remove(xmlHeld);
                if(heldEntity.getLockExpire() != null && heldEntity.getLockExpire().isBefore(LocalDateTime.now())) {
                    log.info("Held {} is locked - skipping", heldEntity.getName());
                }
                VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(heldEntity);
                if (isOnlineVersionOlder(xmlHeld, versionEntity.getCreatedDate())) {
                    log.info("Got a new version for held with name {}", heldEntity.getName());
                    //We got a new version of this xmlHeld
                    UUID uuid = UUID.randomUUID();
                    //Fetch all the data before creating the version, to make sure the helden-api doesnt fail
                    Tuple3<String, Daten, InputStream> data = Mono.zip(
                            Beans.HELDEN_API.request(new ReturnHeldXmlRequest(xmlHeld.getHeldenid(), new TokenAuthentication(userEntity.getToken()), uuid), false),
                            Beans.HELDEN_API.request(new ReturnHeldDatenWithEreignisseRequest(heldEntity.getId(), new TokenAuthentication(userEntity.getToken()), uuid), false),
                            Beans.HELDEN_API.request(new ReturnHeldPdfRequest(heldEntity.getId(), new TokenAuthentication(userEntity.getToken()), uuid), false)).block();
                    IOUtils.closeQuietly(data.getT3());
                    String xml = data.getT1();
                    versionEntity = VersionService.persistVersion(heldEntity, versionEntity.getVersion() + 1, xml, uuid, data.getT2());
                    result.incrementUpdated();

                } else {
                    log.debug("Held with Name {} is already on latest version", heldEntity.getName());
                }
            }
        });
        helden.forEach(held -> {
            UUID uuid = UUID.randomUUID();
            Tuple3<String, Daten, InputStream> data = Mono.zip(
                    Beans.HELDEN_API.request(new ReturnHeldXmlRequest(held.getHeldenid(), new TokenAuthentication(userEntity.getToken()), uuid)),
                    Beans.HELDEN_API.request(new ReturnHeldDatenWithEreignisseRequest(held.getHeldenid(), new TokenAuthentication(userEntity.getToken()), uuid)),
                    Beans.HELDEN_API.request(new ReturnHeldPdfRequest(held.getHeldenid(), new TokenAuthentication(userEntity.getToken()), uuid))).block();
            IOUtils.closeQuietly(data.getT3());
            String xml = data.getT1();
            HeldEntity heldEntity = new HeldEntity();
            heldEntity.setGruppe(userEntity.getGruppe());
            heldEntity.setName(held.getName());
            heldEntity.setId(held.getHeldenid());
            heldEntity.setUserId(userEntity.getId());
            heldEntity.setKey(XmlUtil.getKeyFromString(xml));
            heldEntity.setPublic(false);
            heldEntity.setActive(true);
            heldEntity.setDeleted(false);
            HeldRepositoryService.saveHeld(heldEntity);
            VersionService.persistVersion(heldEntity, 1, xml, uuid, data.getT2());

            result.incrementCreated();
            log.info("Saving new held {} for user {} with version {}", heldEntity.getName(), userEntity.getName(), 1);
        });
        return result;
    }

    private static boolean isOnlineVersionOlder(Held xmlHeld, LocalDateTime heldCreatedDate) {

        Long lastEditedTimestamp = (xmlHeld.getHeldlastchange().longValue() / 1000L) * 1000L;
        LocalDateTime lastEditedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastEditedTimestamp),
                TimeZone.getDefault().toZoneId());
        if (lastEditedDate.equals(heldCreatedDate)) {
            return false;
        }

        return lastEditedDate.isAfter(heldCreatedDate);
    }

    public static HeldEntity getHeldEntity(Context context) {
        Long held = Long.valueOf(context.pathParam("held"));
        return HeldRepositoryService.findByIdReduced(held);

    }

    public static class SynchronizationResult {
        private int created;
        private int updated;

        public void incrementCreated() {
            created++;
        }

        public void incrementUpdated() {
            updated++;
        }

        public int getCreated() {
            return created;
        }

        public void setCreated(int created) {
            this.created = created;
        }

        public int getUpdated() {
            return updated;
        }

        public void setUpdated(int updated) {
            this.updated = updated;
        }
    }
}
