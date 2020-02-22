package de.failender.dgo.rest.helden.inventar;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.rest.integration.Beans;
import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.api.requests.ReturnHeldXmlRequest;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HeldInventarService {

    /*
    public List<Gegenstand> getInventar(BigInteger heldid) {
        List<Gegenstand> values = new ArrayList<>();
        Element items = getItemsElement(heldRepositoryService.findHeldWithLatestVersion(heldid));
        outer:
        for(int i = 0; i<items.getChildNodes().getLength(); i++) {
            Node node = items.getChildNodes().item(i);
            if(!(node instanceof Element)) {
                continue;
            }
            values.add(parseGegenstand(node));
        }
        tryComputeLagerorte(values, heldid);
        return values;
    }

    private Gegenstand parseGegenstand(Node node) {
        return parseGegenstand((Element)node);
    }

    private Gegenstand parseGegenstand(Element gegenstand) {
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

    private void tryComputeLagerorte(List<Gegenstand> items, BigInteger heldid) {
        List<LagerortEntity> lagerorte = lagerortRepository.findByHeldid(heldid);
        for (LagerortEntity lagerortEntity : lagerorte) {
            List<GegenstandLagerort> removal = new ArrayList<>();
            for (GegenstandLagerort gl : lagerortEntity.getGegenstandLagerorte()) {
                List<Gegenstand> matches = items.stream()
                        .filter(i -> i.getGegenstand().equals(gl.getName()))
                        .collect(Collectors.toList());
                if(matches.isEmpty()) {
                    removal.add(gl);
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
                lagerortEntity.getGegenstandLagerorte().removeAll(removal);
                lagerortRepository.save(lagerortEntity);
            }
        }
    }

    private Element getItemsElement(HeldEntity heldEntity, VersionEntity versionEntity) {
        String xml = Beans.HELDEN_API.request(new ReturnHeldXmlRequest(heldEntity.getId(), AuthenticationUtil.getAuthentication(), heldWithVersion.getVersion().getCacheId()))
                .block();
        Element held = XmlUtil.getHeldFromXml(xml);
        return (Element) held.getElementsByTagName("gegenstände").item(0);
    }


     */
}
