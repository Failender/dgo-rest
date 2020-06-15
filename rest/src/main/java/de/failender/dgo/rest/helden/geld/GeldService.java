package de.failender.dgo.rest.helden.geld;

import de.failender.dgo.integration.Beans;
import de.failender.dgo.integration.VersionService;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.HeldWithUser;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.persistance.held.geld.GeldBoerseEntity;
import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.api.requests.ReturnHeldXmlRequest;
import de.failender.heldensoftware.xml.datenxml.Muenze;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class GeldService {

    public static void updateGeld(Long heldid, GeldBoerseEntity entity) {
        HeldWithUser heldWithUser = HeldRepositoryService.getHeldWithUser(heldid);
        VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(heldWithUser.getHeldEntity());
        //securityUtils.canCurrentUserEditHeld(heldWithVersion.getHeld());
        if(!heldWithUser.getUserEntity().isCanWrite()) {
            throw new RuntimeException();
            //throw new NoWritePermissionException();
        }
        String xml = Beans.HELDEN_API.request(new ReturnHeldXmlRequest(heldid, null, versionEntity.getCacheId()))
                .block();
        Element held = XmlUtil.getHeldFromXml(xml);
        Element geldboerse = (Element) held.getElementsByTagName("geldboerse").item(0);

        List<Muenze> muenzen = new ArrayList<>();
        NodeList nodeList = geldboerse.getElementsByTagName("muenze");
        for(int i = 0; i< nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if(!(node instanceof Element) ) {
                continue;
            }
            Element mElement = (Element) node;
            Muenze muenze = new Muenze();
            muenze.setAnzahl(Long.valueOf(mElement.getAttribute("anzahl")));
            muenze.setName(mElement.getAttribute("name"));
            muenzen.add(muenze);
        }
        long totalMoney = entity.getAnzahl();

        Waehrung waehrung = Waehrung.Kreuzer;
        while(totalMoney != 0) {
            // Final variable for lambda
            final Waehrung wwährung = waehrung;
            Muenze muenze = muenzen
                    .stream()
                    .filter(data -> data.getName().equals(wwährung.name()))
                    .findFirst()
                    .orElseGet(() -> {
                        Muenze m = new Muenze();
                        m.setAnzahl(0L);
                        m.setName(wwährung.name());
                        muenzen.add(m);

                        Element element = geldboerse.getOwnerDocument().createElement("muenze");
                        element.setAttribute("name", wwährung.name());
                        element.setAttribute("waehrung", wwährung.getWährungName());
                        geldboerse.appendChild(element);

                        return m;
                    });
            if(wwährung.getNext() == null) {
                muenze.setAnzahl(totalMoney);
                totalMoney = 0;
            } else{
                muenze.setAnzahl((totalMoney % 10));
                totalMoney /= 10;
                waehrung = waehrung.getNext();
            }
            Element element = XmlUtil.findIn(nodeList, (e) -> e.getAttribute("name").equals(wwährung.name()));
            element.setAttribute("anzahl", muenze.getAnzahl().toString());
        }

        Element ereignisse = (Element) held.getElementsByTagName("ereignisse").item(0);
        Element ereignis = ereignisse.getOwnerDocument().createElement("ereignis");
        ereignis.setAttribute("text", "Geld");
        ereignis.setAttribute("obj", "Geld editiert");
        ereignis.setAttribute("time", String.valueOf(System.currentTimeMillis()));
        ereignis.setAttribute("Alt", "");
        ereignis.setAttribute("Info", "Geldbörse");
        ereignis.setAttribute("Neu", "Test");
        ereignisse.appendChild(ereignis);

        xml = XmlUtil.toString(geldboerse.getOwnerDocument());

        VersionService.updateVersion(xml, heldWithUser, versionEntity);
    }
}
