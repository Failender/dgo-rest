package de.failender.fantasygrounds;

import org.w3c.dom.Document;

import java.util.List;

public class CampaignInformation {

    private final List<Character> characters;
    private final Document xml;


    public CampaignInformation(List<Character> characters, Document xml) {
        this.characters = characters;
        this.xml = xml;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public Document getXml() {
        return xml;
    }
}
