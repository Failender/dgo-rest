package de.failender.dgo.persistance.held.geld;

import de.failender.dgo.persistance.BaseEntity;

public class GeldBoerseEntity extends BaseEntity {

    private Long heldid;
    private long anzahl;

    public Long getHeldid() {
        return heldid;
    }

    public void setHeldid(Long heldid) {
        this.heldid = heldid;
    }

    public long getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(long anzahl) {
        this.anzahl = anzahl;
    }
}
