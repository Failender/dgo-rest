package de.failender.dgo.rest.fantasygrounds;

import java.util.List;

public class ExportSettingsDTO {

    private List<ExportCharacterDTO> characters;

    public List<ExportCharacterDTO> getCharacters() {
        return characters;
    }

    public void setCharacters(List<ExportCharacterDTO> characters) {
        this.characters = characters;
    }
}
