package de.failender.dgo.persistance.held.geld;

import de.failender.dgo.persistance.BaseEntity;

public class GeldBoerseEntity extends BaseEntity {

    private Long heldid;
    private int anzahl;

    public Long getHeldid() {
        return heldid;
    }

    public void setHeldid(Long heldid) {
        this.heldid = heldid;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }
}
