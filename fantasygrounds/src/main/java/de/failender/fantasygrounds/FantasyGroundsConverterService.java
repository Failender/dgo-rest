package de.failender.fantasygrounds;

import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.xml.datenxml.Daten;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FantasyGroundsConverterService {

    public static String convert(List<Daten> helden, Document xml, List<Character> exportCharacters) {

        XmlWorker.Oeffne_Held(xml);
        return processHelden(helden, exportCharacters);
    }

    public static String convert(List<Daten> helden, InputStream xml, List<Character> exportCharacter) {

        XmlWorker.Oeffne_Held(xml);
        return processHelden(helden, exportCharacter);
    }

    private static String processHelden(List<Daten> helden, List<Character> exportCharacters) {
        for (Daten daten : helden) {
            Character exportCharacter = exportCharacters.stream().filter(entry -> entry.getName().equals(daten.getAngaben().getName())).findFirst().orElse(null);
            DGOPluginHeldenWerteWerkzeug werteWerkzeug = new DGOPluginHeldenWerteWerkzeug(daten);
            XmlWorker.Konvertiere_Held(werteWerkzeug, exportCharacter);
        }

        String converted = XmlWorker.documentToString();
        return converted;
    }

    public static CampaignInformation parseCampaignInformation(InputStream inputStream) {
        Document xml = XmlUtil.documentFromInputStream(inputStream);
        return parseCampaignInformation(xml);
    }

    public static CampaignInformation parseCampaignInformation(Document xml) {
        Element charsheet = (Element) xml.getDocumentElement().getElementsByTagName("charsheet").item(0);
        List<Character> characters = new ArrayList<>();
        if (charsheet == null) {
            return new CampaignInformation(characters, xml);
        }

        characters = parseCharacters(charsheet);
        while (charsheet.hasChildNodes()) {
            charsheet.removeChild(charsheet.getFirstChild());
        }
        return new CampaignInformation(characters, xml);
    }


    private static List<Character> parseCharacters(Element charsheet) {
        List<Character> characters = new ArrayList<>();

        for (int i = 0; i < charsheet.getChildNodes().getLength(); i++) {
            Node node = charsheet.getChildNodes().item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element element = (Element) node;

            Element nameElement = findChildWithName(element.getChildNodes(), "name");

            Character character = new Character();
            character.setName(nameElement.getTextContent());

            Element lepElement = findChildWithName(element.getChildNodes(), "lebensenergie");
            if (lepElement == null) {
                lepElement = findChildWithName(element.getChildNodes(), "lep");
            }
            if (lepElement != null) {
                Element aktElement = findChildWithName(lepElement.getChildNodes(), "akt");
                int currentLep = Integer.valueOf(aktElement.getTextContent());
                character.setCurrentLep(currentLep);
            }
            Element aspElement = findChildWithName(element.getChildNodes(), "aep");
            if (aspElement == null) {
                aspElement = findChildWithName(element.getChildNodes(), "astral");
            }
            if (aspElement != null) {
                Element aktElement = findChildWithName(aspElement.getChildNodes(), "akt");
                int currentAsp = Integer.valueOf(aktElement.getTextContent());
                character.setCurrentAsp(currentAsp);
            }
            character.setId(characters.size());
            characters.add(character);
        }

        return characters;
    }

    private static Element findChildWithName(NodeList nodeList, String name) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element element = (Element) node;
            if (element.getTagName().equals(name)) {
                return element;
            }
        }
        return null;
    }
}
