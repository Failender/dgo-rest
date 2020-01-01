package de.failender.dgo.rest.helden;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.dgo.rest.integration.Beans;
import de.failender.heldensoftware.Unterscheidbar;
import de.failender.heldensoftware.api.HeldenApi;
import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.api.authentication.TokenAuthentication;
import de.failender.heldensoftware.api.requests.*;
import de.failender.heldensoftware.xml.datenxml.Daten;
import de.failender.heldensoftware.xml.datenxml.Eigenschaftswerte;
import de.failender.heldensoftware.xml.datenxml.Ereignis;
import one.util.streamex.EntryStream;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static Differences calculateDifferences(Long heldenid, int from, int to) {
        //If from is bigger then to flip the values
        if (from > to) {
            int tempFrom = from;
            from = to;
            to = tempFrom;
        }
        HeldEntity held = HeldRepositoryService.findById(heldenid);
        VersionEntity fromVersion = VersionRepositoryService.findVersion(held, from);
        VersionEntity toVersion = VersionRepositoryService.findVersion(held, to);
        return calculateDifferences(held, fromVersion, toVersion);
    }

    private static Differences calculateDifferences(HeldEntity held, VersionEntity from, VersionEntity to) {
        Tuple2<Daten, Daten> datenTuple = Beans.HELDEN_API.request(new ReturnHeldDatenWithEreignisseRequest(held.getId(), null, from.getCacheId()))
                .zipWith(Beans.HELDEN_API.request(new ReturnHeldDatenWithEreignisseRequest(held.getId(), null, to.getCacheId()))).block();
        return calculateDifferences(held.getName(), datenTuple.getT1(), datenTuple.getT2());
    }

    private static Differences calculateDifferences(String name, Daten from, Daten to) {
        //List of all ereignisse that are not in from but in to
        List<Ereignis> ereignisList = to.getEreignisse().getEreignis().subList(from.getEreignisse().getEreignis().size(), to.getEreignisse().getEreignis().size());
        Map<String, List<Ereignis>> map = EntryStream.of(ereignisList)
                .mapToKey((idx, ereignis) ->
                        ereignis.getObject()
                                .replaceFirst("Sprachen kennen ", "")
                                .replaceFirst("Lesen/Schreiben", "L/S")
                                .replaceFirst("Ritualkenntnis: ", "")
                                .replaceAll(" \\[.*\\]", "")).grouping();
        return new Differences(name, calculateDifference(from.getTalentliste().getTalent(), to.getTalentliste().getTalent(), map, true),
                calculateDifference(from.getZauberliste().getZauber(), to.getZauberliste().getZauber(), map, true),
                calculateDifference(from.getVorteile().getVorteil(), to.getVorteile().getVorteil(), map, false),
                calculateEigenschaftDifference(from, to, map),
                calculateSonderfertigkeitenDifference(ereignisList));
    }

    private static List<Difference> calculateEigenschaftDifference(Daten from, Daten to, Map<String, List<Ereignis>> ereignis) {
        return Stream.of(calculateEigenschaftDifference(from.getEigenschaften().getMut(), to.getEigenschaften().getMut(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getKlugheit(), to.getEigenschaften().getKlugheit(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getIntuition(), to.getEigenschaften().getIntuition(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getCharisma(), to.getEigenschaften().getCharisma(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getFingerfertigkeit(), to.getEigenschaften().getFingerfertigkeit(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getGewandtheit(), to.getEigenschaften().getGewandtheit(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getKonstitution(), to.getEigenschaften().getKonstitution(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getKoerperkraft(), to.getEigenschaften().getKoerperkraft(), ereignis)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static List<Difference> calculateSonderfertigkeitenDifference(List<Ereignis> ereignis) {
        List<Difference> differences = new ArrayList<>();
        for (Ereignis ereigni : ereignis) {
            if (ereigni.getAktion().startsWith("Sonderfertigkeit")) {
                differences.add(new Difference(-ereigni.getAp(), "aktiv", ereigni.getObject(), ereigni.getKommentar()));
            }
        }
        return differences;
    }

    private static Difference calculateEigenschaftDifference(Eigenschaftswerte from, Eigenschaftswerte to, Map<String, List<Ereignis>> map) {
        if (from.getAkt().equals(to.getAkt())) {
            return null;
        }
        String name = from.getName();
        int fromVal = from.getAkt().intValue();
        int toVal = to.getAkt().intValue();
        String tooltip = calculateTooltip(map, name, false);
        return new Difference(fromVal, toVal, name, tooltip);
    }

    private static List<Difference> calculateDifference(List<? extends Unterscheidbar> fromList, List<? extends Unterscheidbar> toList, Map<String, List<Ereignis>> ereignisse, boolean replaceSpace) {
        List<Difference> differences = new ArrayList<>();
        fromList.forEach(from -> {
            Optional<? extends Unterscheidbar> toOptional = toList.stream().filter(to -> to.name().equals(from.name())).findFirst();
            if (toOptional.isPresent()) {
                Unterscheidbar to = toOptional.get();
                toList.remove(to);
                Integer fromWert = from.getWert();
                Integer toWert = to.getWert();
                if (fromWert == null && toWert == null || fromWert != null && toWert != null && fromWert.equals(toWert)) {
                    return;
                }
                differences.add(new Difference(fromWert, toWert, from.name(), ""));
            } else {
                differences.add(new Difference(from.getWert(), null, from.name(), ""));
            }
        });
        toList.forEach(toTalent -> differences.add(new Difference(null, toTalent.getWert(), toTalent.name(), "")));
        differences.forEach(difference -> {
            String tooltip = calculateTooltip(ereignisse, difference.getName(), replaceSpace);
            difference.setTooltip(tooltip);
        });
        return differences;
    }

    private static String calculateTooltip(Map<String, List<Ereignis>> map, String name, boolean replaceSpace) {
        return map.getOrDefault(name, Collections.emptyList()).stream().map(ereignis -> ereignis.getBemerkung().replace(",", "")).map(value -> {
            if (replaceSpace && value.isEmpty()) {
                return "Aktiviert";
            } else {
                return value;
            }
        }).collect(Collectors.joining(", "));
    }
}
