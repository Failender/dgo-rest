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
import de.failender.heldensoftware.xml.datenxml.Sonderfertigkeit;
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
        Differences differences = new Differences(name, calculateDifference(from.getTalentliste().getTalent(), to.getTalentliste().getTalent(), map, "Aktiviert"),
                calculateDifference(from.getZauberliste().getZauber(), to.getZauberliste().getZauber(), map, "Aktiviert"),
                calculateDifference(from.getVorteile().getVorteil(), to.getVorteile().getVorteil(), map, null),
                calculateEigenschaftDifference(from, to, map),
                calculateSonderfertigkeitenDifference(ereignisList));

        calculateSteigerungen(differences, to);
        return differences;
    }

    private static void calculateSteigerungen(Differences differences, Daten to) {
        //The following requires lehrmeister:
        // 1. talents via lehrmeister
        // 2. zauber via lehrmeister
        // 3. sonderfertigkeiten
        List<Steigerung> steigerungen = new ArrayList<>();
        steigerungen.addAll(calculateSteigerungen(differences.getTalente(), 1));
        steigerungen.addAll(calculateSteigerungen(differences.getZauber(), 5));

        for (Difference difference : differences.getSonderfertigkeiten()) {
            // TODO find a way to find the verbreitung of the sf to calculate highestLehren..
            int ap = difference.getKosten();
            int highestLehren = 7;
            String name = difference.getName();
            Sonderfertigkeit sf = to.getSonderfertigkeiten().getSonderfertigkeit()
                    .stream()
                    .filter(entry -> entry.getName().equals(name))
                    .findFirst().orElse(null);
            boolean isMagisch = Optional.ofNullable(sf)
                    .map(entry -> entry.getBereich().stream().filter(bereich -> bereich.equals("Magisch")).findFirst().map(bereich -> true).orElse(false))
                    .orElse(false);
            int modifier = isMagisch ? 5 : 1;
            int kosten = ap * modifier * highestLehren;
            steigerungen.add(new Steigerung(name, highestLehren, ap, kosten, modifier, "Aktiviert"));
        }


        differences.setSteigerungen(steigerungen);


    }

    private static List<Steigerung> calculateSteigerungen(List<Difference> differences, int modifier) {
        return differences
                .stream()
                .map(difference -> {
                    String name = difference.getName();
                    int ap = 0;
                    int highestLehren = 7;
                    List<Integer> lehrmeisterSteigerungen = new ArrayList<>();
                    if (difference.getEreignisse() != null) {
                        for (Ereignis ereignis : difference.getEreignisse()) {
                            if (!ereignis.getBemerkung().contains("Lehrmeister")) {
                                continue;
                            }
                            ap -= ereignis.getAp();
                            int idx = ereignis.getNeuerzustand().indexOf(";");
                            if (idx == -1) {
                                idx = ereignis.getNeuerzustand().length();
                            }
                            int neuerZustand = Integer.valueOf(ereignis.getNeuerzustand().substring(0, idx));
                            lehrmeisterSteigerungen.add(neuerZustand);
                            highestLehren = Math.max(neuerZustand - 2, highestLehren);
                        }
                    }
                    if (ap == 0) {
                        return null;
                    }
                    int kosten = ap * highestLehren * modifier;
                    String tooltip;
                    try {
                        tooltip = steigerungsTooltip(lehrmeisterSteigerungen);

                    } catch (Exception e) {
                        e.printStackTrace();
                        tooltip = "Fehler beim Berechnen";
                    }


                    return new Steigerung(name, highestLehren, ap, kosten, modifier, tooltip);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    private static String steigerungsTooltip(List<Integer> lehrmeisterSteigerungen) {
        String tooltip = "";
        Integer from = null;
        Integer to = null;
        for (Integer integer : lehrmeisterSteigerungen) {
            if (from == null) {
                from = integer - 1;
                to = integer;
                continue;
            }
            if (integer > to + 1) {
                tooltip += from + "=>" + to + ",";
                to = integer + 1;
                from = integer;
                continue;
            }

            to = integer;
        }
        if (to != null) {
            tooltip += from + "=>" + to;
        }
        return tooltip;
    }

    private static List<Difference> calculateEigenschaftDifference(Daten from, Daten to, Map<String, List<Ereignis>> ereignis) {
        return Stream.of(calculateEigenschaftDifference(from.getEigenschaften().getMut(), to.getEigenschaften().getMut(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getKlugheit(), to.getEigenschaften().getKlugheit(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getIntuition(), to.getEigenschaften().getIntuition(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getCharisma(), to.getEigenschaften().getCharisma(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getFingerfertigkeit(), to.getEigenschaften().getFingerfertigkeit(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getGewandtheit(), to.getEigenschaften().getGewandtheit(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getKonstitution(), to.getEigenschaften().getKonstitution(), ereignis), calculateEigenschaftDifference(from.getEigenschaften().getKoerperkraft(), to.getEigenschaften().getKoerperkraft(), ereignis)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static List<Difference> calculateSonderfertigkeitenDifference(List<Ereignis> ereignisse) {
        List<Difference> differences = new ArrayList<>();
        for (Ereignis ereignis : ereignisse) {
            if (ereignis.getAktion().startsWith("Sonderfertigkeit")) {
                differences.add(new Difference("-", "aktiv", ereignis.getObject(), ereignis.getKommentar(), -ereignis.getAp(), Collections.singletonList(ereignis)));
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
        String tooltip = calculateTooltip(map, name, "Normal");
        List<Ereignis> ereignisse = map.get(name);
        int kosten = ereignisse == null ? 0 : ereignisse.stream()
                .map(ereignis -> -ereignis.getAp())
                .reduce((a, b) -> a + b)
                .orElse(0);
        //TODO
        return new Difference(fromVal, toVal, name, tooltip, kosten, ereignisse);
    }

    private static List<Difference> calculateDifference(List<? extends Unterscheidbar> fromList, List<? extends Unterscheidbar> toList, Map<String, List<Ereignis>> ereignisMap, String emptyReplacer) {
        List<Difference> differences = new ArrayList<>();
        fromList.forEach(from -> {
            Optional<? extends Unterscheidbar> toOptional = toList.stream().filter(to -> to.name().equals(from.name())).findFirst();
            List<Ereignis> ereignisse = ereignisMap.get(from.name());
            int kosten = ereignisse == null ? 0 : ereignisse.stream()
                    .map(ereignis -> -ereignis.getAp())
                    .reduce((a, b) -> a + b)
                    .orElse(0);
            if (toOptional.isPresent()) {
                Unterscheidbar to = toOptional.get();
                toList.remove(to);
                Integer fromWert = from.getWert();
                Integer toWert = to.getWert();
                if (fromWert == null && toWert == null || fromWert != null && toWert != null && fromWert.equals(toWert)) {
                    return;
                }


                differences.add(new Difference(fromWert, toWert, from.name(), "", kosten, ereignisse));
            } else {
                differences.add(new Difference(from.getWert(), null, from.name(), "", kosten, ereignisse));
            }
        });
        toList.forEach(toTalent -> {
            List<Ereignis> ereignisse = ereignisMap.get(toTalent.name());
            int kosten = ereignisse == null ? 0 : ereignisse.stream()
                    .map(ereignis -> -ereignis.getAp())
                    .reduce((a, b) -> a + b)
                    .orElse(0);
            differences.add(new Difference(null, toTalent.getWert(), toTalent.name(), "", kosten, ereignisse));
        });
        differences.forEach(difference -> {
            String tooltip = calculateTooltip(ereignisMap, difference.getName(), emptyReplacer);
            difference.setTooltip(tooltip);
        });
        return differences;
    }

    private static String calculateTooltip(Map<String, List<Ereignis>> map, String name, String emptyReplacer) {
        return map.getOrDefault(name, Collections.emptyList()).stream().map(ereignis -> ereignis.getBemerkung().replace(",", "")).map(value -> {
            if (emptyReplacer != null && value.isEmpty()) {
                return emptyReplacer;
            } else {
                return value;
            }
        }).collect(Collectors.joining(", "));
    }

}
