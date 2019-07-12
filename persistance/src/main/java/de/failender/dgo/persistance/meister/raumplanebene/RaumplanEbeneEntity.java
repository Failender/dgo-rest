package de.failender.dgo.persistance.meister.raumplanebene;

import de.failender.dgo.persistance.BaseEntity;

public class RaumplanEbeneEntity extends BaseEntity {

    private String name;
    private Long parent;
    private String beschreibung;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
