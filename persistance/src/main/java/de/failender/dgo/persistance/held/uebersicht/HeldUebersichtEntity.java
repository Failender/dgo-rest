package de.failender.dgo.persistance.held.uebersicht;

import de.failender.dgo.persistance.BaseEntity;

public class HeldUebersichtEntity extends BaseEntity {

    private Long heldid;
    private int lep;
    private int asp;


    public Long getHeldid() {
        return heldid;
    }

    public void setHeldid(Long heldid) {
        this.heldid = heldid;
    }

    public int getLep() {
        return lep;
    }

    public void setLep(int lep) {
        this.lep = lep;
    }

    public int getAsp() {
        return asp;
    }

    public void setAsp(int asp) {
        this.asp = asp;
    }
}


