package de.failender.dgo.persistance.held.inventar;

import de.failender.dgo.persistance.BaseEntity;

public class HeldInventarEntity extends BaseEntity {

    private String name;
    private Long heldid;
    private Integer container;
    private Float gewicht;
    private int anzahl;
    private String notiz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHeldid() {
        return heldid;
    }

    public void setHeldid(Long heldid) {
        this.heldid = heldid;
    }

    public Integer getContainer() {
        return container;
    }

    public void setContainer(Integer container) {
        this.container = container;
    }

    public Float getGewicht() {
        return gewicht;
    }

    public void setGewicht(Float gewicht) {
        this.gewicht = gewicht;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }
}
