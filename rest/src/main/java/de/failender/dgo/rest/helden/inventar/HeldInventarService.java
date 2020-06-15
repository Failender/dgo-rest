package de.failender.dgo.rest.helden.inventar;

import de.failender.dgo.integration.Beans;
import de.failender.dgo.integration.VersionService;
import de.failender.dgo.persistance.held.*;
import de.failender.dgo.persistance.held.inventar.lagerort.GegenstandToLagerortEntity;
import de.failender.dgo.persistance.held.inventar.lagerort.GegenstandToLagerortRepositoryService;
import de.failender.dgo.persistance.held.inventar.lagerort.LagerortEntity;
import de.failender.dgo.persistance.held.inventar.lagerort.LagerortRepositoryService;
import de.failender.dgo.persistance.user.UserEntity;
import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.api.authentication.TokenAuthentication;
import de.failender.heldensoftware.api.requests.ReturnHeldXmlRequest;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HeldInventarService {


    public static List<Gegenstand> getInventar(Long heldid) {
        List<Gegenstand> values = new ArrayList<>();
        HeldWithUser heldWithUser = HeldRepositoryService.getHeldWithUser(heldid);
        VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(heldWithUser.getHeldEntity());
        Element items = getItemsElement(heldWithUser, versionEntity);
        outer:
        for(int i = 0; i<items.getChildNodes().getLength(); i++) {
            Node node = items.getChildNodes().item(i);
            if(!(node instanceof Element)) {
                continue;
            }
            values.add(parseGegenstand(node));
        }
        tryComputeLagerorte(values, heldWithUser.getHeldEntity());
        return values;
    }

    private static Gegenstand parseGegenstand(Node node) {
        return parseGegenstand((Element)node);
    }

    private static Gegenstand parseGegenstand(Element gegenstand) {
        int anzahl = Integer.valueOf(gegenstand.getAttribute("anzahl"));
        if(gegenstand.getChildNodes().getLength() == 0 ) {
            return new Gegenstand(gegenstand.getAttribute("name"), anzahl, true);
        } else {
            for (int j = 0; j < gegenstand.getChildNodes().getLength(); j++) {
                Node n = gegenstand.getChildNodes().item(j);
                if (!(n instanceof Element)) {
                    continue;
                }
                Element modallgemein = (Element) n;
                if (!modallgemein.getTagName().equals("modallgemein")) {
                    continue;
                }

                for (int k = 0; k < modallgemein.getChildNodes().getLength(); k++) {
                    Node no = modallgemein.getChildNodes().item(k);
                    if (!(no instanceof Element)) {
                        continue;
                    }
                    Element e = (Element) no;
                    if (!((Element) no).getTagName().equals("name")) {
                        continue;
                    }
                    String name = e.getAttribute("value");
                    return new Gegenstand(name, anzahl, true);


                }
            }
            return new Gegenstand(gegenstand.getAttribute("name"), anzahl, true);
        }
    }

    private static void tryComputeLagerorte(List<Gegenstand> items, HeldEntity heldEntity) {
        List<LagerortEntity> lagerorte = LagerortRepositoryService.findByHeld(heldEntity);
        for (LagerortEntity lagerortEntity : lagerorte) {

            List<Long> removal = new ArrayList<>();
            for (GegenstandToLagerortEntity gl : GegenstandToLagerortRepositoryService.findByLagerort(lagerortEntity.getId())) {
                List<Gegenstand> matches = items.stream()
                        .filter(i -> i.getName().equals(gl.getName()))
                        .collect(Collectors.toList());
                if(matches.isEmpty()) {
                    removal.add(gl.getId());
                    return;
                }
                if(matches.size() == 1) {
                    matches.get(0).setLagerort(lagerortEntity.getName());
                    continue;
                }
                List<Gegenstand> preciseMatches = matches
                        .stream()
                        .filter(g -> g.getAnzahl() == gl.getAmount())
                        .collect(Collectors.toList());
                if(preciseMatches.size() == 1) {
                    preciseMatches.get(0).setLagerort(lagerortEntity.getName());
                    continue;
                }
                // If this happens we cant be sure where to place this item.
                // Leave it as is until we find a better solution for precisley matching items

            }
            if(!removal.isEmpty()) {

                GegenstandToLagerortRepositoryService.removeAll(heldEntity, removal);
            }
        }
    }

    public static void addItem(Long heldid, String name, int amount) {
        HeldWithUser heldWithUser = HeldRepositoryService.getHeldWithUser(heldid);
        VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(heldWithUser.getHeldEntity());
        UserEntity userEntity = heldWithUser.getUserEntity();
        if(!userEntity.isCanWrite()) {
            throw new RuntimeException();
        }
        Element items = getItemsElement(heldWithUser, versionEntity);
        Element item = items.getOwnerDocument().createElement("gegenstand");
        item.setAttribute("anzahl", amount + "");
        item.setAttribute("name", "Armreif");
        item.setAttribute("slot", "0");

        Element mod = items.getOwnerDocument().createElement("modallgemein");
        Element gewicht = items.getOwnerDocument().createElement("gewicht");
        gewicht.setAttribute("value", "10");
        mod.appendChild(gewicht);
        Element preis = items.getOwnerDocument().createElement("preis");
        preis.setAttribute("value", "10");
        mod.appendChild(preis);
        Element nameElement = items.getOwnerDocument().createElement("name");
        nameElement.setAttribute("value", name);
        mod.appendChild(nameElement);
        item.appendChild(mod);
        items.appendChild(item);
        String xml = XmlUtil.toString(items.getOwnerDocument());
        VersionService.updateVersion(xml, heldWithUser, versionEntity);
    }

    public static void setLagerort(Long heldid, String from, String to, String gegenstand, int amount) {
        HeldWithUser heldWithUser = HeldRepositoryService.getHeldWithUser(heldid);
        Optional<LagerortEntity> toOrt = LagerortRepositoryService.findByNameAndHeld(to, heldWithUser.getHeldEntity());
        if(from != null) {
            Optional<LagerortEntity> lagerortEntity = LagerortRepositoryService.findByNameAndHeld(from, heldWithUser.getHeldEntity());
            lagerortEntity.ifPresent(lagerort -> {
                GegenstandToLagerortRepositoryService.removeByNameAndHeld(lagerort, gegenstand, heldWithUser.getHeldEntity() );

            });

        }

        LagerortEntity toLagerort = toOrt.orElseGet(() -> {
            LagerortEntity lagerortEntity = new LagerortEntity();
            lagerortEntity.setName(to);
            lagerortEntity.setHeldid(heldWithUser.getHeldEntity().getId());
            LagerortRepositoryService.persist(heldWithUser.getHeldEntity(), lagerortEntity);
            return lagerortEntity;
        });
        GegenstandToLagerortEntity gegenstandLagerort = new GegenstandToLagerortEntity();
        gegenstandLagerort.setLagerort(toLagerort.getId());
        gegenstandLagerort.setAmount(amount);
        gegenstandLagerort.setName(gegenstand);
        GegenstandToLagerortRepositoryService.persist(heldWithUser.getHeldEntity(), gegenstandLagerort);



    }

    private static Element getItemsElement(HeldWithUser heldWithUser, VersionEntity versionEntity) {
        String xml = Beans.HELDEN_API.request(new ReturnHeldXmlRequest(heldWithUser.getHeldEntity().getId(), new TokenAuthentication(heldWithUser.getUserEntity().getToken()), versionEntity.getCacheId()))
                .block();
        Element held = XmlUtil.getHeldFromXml(xml);
        return (Element) held.getElementsByTagName("gegenstände").item(0);
    }
}
