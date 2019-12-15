package de.failender.dgo.rest.helden;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.rest.integration.Beans;
import de.failender.heldensoftware.api.HeldenApi;
import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.api.authentication.TokenAuthentication;
import de.failender.heldensoftware.api.requests.*;
import de.failender.heldensoftware.xml.datenxml.Daten;
import de.failender.heldensoftware.xml.datenxml.Ereignis;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class VersionService {
    public static VersionEntity persistVersion(HeldEntity held, int version, String xml, UUID uuid, Daten daten) {
        LocalDateTime date = XmlUtil.getStandFromString(xml);
        VersionEntity versionEntity = new VersionEntity();
        versionEntity.setVersion(version);
        versionEntity.setHeldid(held.getId());
        versionEntity.setCacheId(uuid);
        versionEntity.setCreatedDate(date);
        versionEntity.setLastEvent(extractLastEreignisString(daten.getEreignisse().getEreignis()));
        versionEntity.setAp(daten.getAngaben().getAp().getGesamt().intValue());
        VersionRepositoryService.saveVersion(held, versionEntity);


        return versionEntity;
    }

    public static String extractLastEreignisString(List<Ereignis> ereignisse) {
        Ereignis ereignis = extractLastEreignis(ereignisse);
        if (ereignis == null) {
            return null;
        }
        String s = ereignis.getKommentar();
        int index = s.indexOf("Gesamt AP");
        if (index == -1) {
            return s;
        }
        s = s.substring(0, index);
        return s;
    }

    public static Ereignis extractLastEreignis(List<Ereignis> ereignisse) {
        for (int i = ereignisse.size() - 1; i >= 0; i--) {
            if (ereignisse.get(i).getAp() > 0) {
                return ereignisse.get(i);
            }
        }
        return null;
    }

    public static void saveHeld(Long id, String xml) {
        HeldenApi heldenApi = Beans.HELDEN_API;
        Tuple2<InputStream, InputStream> converted = Mono.zip(heldenApi.requestRaw(new ConvertingRequest(HeldenApi.Format.datenxml, xml), true), heldenApi.requestRaw(new ConvertingRequest(HeldenApi.Format.pdfintern, xml), true)).block();
        HeldEntity held = HeldRepositoryService.findById(id);
        long key = XmlUtil.getKeyFromString(xml);
        if(held.getKey() != key) {
            return;
        }
        LocalDateTime stand = XmlUtil.getStandFromString(xml);
        if (VersionRepositoryService.findByHeldidAndCreated(held, stand).isPresent()) {
            System.out.println("Held ist bereits in der selben Version vorhanden");
            return;
        }
        String message = null;
        UserEntity userEntity = UserRepositoryService.findUserById(held.getUserId());
        HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(id);


        List<VersionEntity> versionEntities = VersionRepositoryService.findVersionsByHeld(heldEntity);
        VersionEntity latestVersion = versionEntities.get(0);
        VersionEntity firstVersionToMove = versionEntities.stream()
                .filter(version -> version.getCreatedDate().isBefore(stand))
                .sorted()
                .findFirst()
                .orElse(null);
        int firstVersionToMoveInt = firstVersionToMove == null ? latestVersion.getVersion() + 1 : firstVersionToMove.getVersion();
        if (firstVersionToMove == null) {
            System.err.println("Held that should get faked is newer then the newest one currently uploaded");
            heldenApi.request(new UpdateXmlRequest(new TokenAuthentication(userEntity.getToken()), xml))
                    .block();
        } else {
            for (int i = latestVersion.getVersion(); i >= firstVersionToMoveInt; i--) {
                VersionEntity versionEntity = VersionRepositoryService.findVersion(heldEntity, i);
                VersionRepositoryService.updateVersion(versionEntity, versionEntity.getVersion());
            }
        }
        UUID uuid = UUID.randomUUID();
        saveHeldenXml(new ByteArrayInputStream(xml.getBytes()), id, uuid);
        saveDatenXml(converted.getT1(), id, uuid);
        //No need to use authentication here since the file is coming from cache
        Daten daten = heldenApi.request(new ReturnHeldDatenWithEreignisseRequest(id, null, uuid)).block();
        savePdf(converted.getT2(), id, uuid);
        persistVersion(held, firstVersionToMoveInt, xml, uuid, daten);
    }

    private static void saveHeldenXml(InputStream is, Long heldid, UUID cacheId) {
        ReturnHeldXmlRequest req = new ReturnHeldXmlRequest(heldid, null, cacheId);
        Beans.HELDEN_API.getCacheHandler().doCache(req, is);
    }

    private static void savePdf(InputStream is, Long heldid, UUID cacheId) {
        ReturnHeldPdfRequest req = new ReturnHeldPdfRequest(heldid, null, cacheId);
        Beans.HELDEN_API.getCacheHandler().doCache(req, is);
    }

    private static void saveDatenXml(InputStream is, Long heldid, UUID cacheId) {
        ReturnHeldDatenWithEreignisseRequest req = new ReturnHeldDatenWithEreignisseRequest(heldid, null, cacheId);
        Beans.HELDEN_API.getCacheHandler().doCache(req, is);
    }

    public static void deleteAllVersions(HeldEntity heldEntity) {
        List<VersionEntity> versionEntities = VersionRepositoryService.findVersionsByHeld(heldEntity);
        for (VersionEntity versionEntity : versionEntities) {
            for (ApiRequest apiRequest : HeldenApi.getDataApiRequests(versionEntity.getCacheId())) {
                Beans.HELDEN_API.getCacheHandler().removeCache(apiRequest);
            }
        }
        VersionRepositoryService.deleteVersions(versionEntities);
    }
}
