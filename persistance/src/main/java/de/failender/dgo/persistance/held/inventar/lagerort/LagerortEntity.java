package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.dgo.persistance.BaseEntity;

import java.math.BigInteger;

public class LagerortEntity extends BaseEntity {

    private String name;
    private Long heldid;
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

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }
}
