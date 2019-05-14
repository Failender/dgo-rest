package de.failender.dgo.persistance.zauberspeicher;

import de.failender.dgo.persistance.BaseEntity;

public class ZauberspeicherEntity extends BaseEntity {

    private Long heldid;
    private Integer kosten;
    private String komplexitaet;
    private String spomos;

    public Long getHeldid() {
        return heldid;
    }

    public Integer getKosten() {
        return kosten;
    }

    public String getKomplexitaet() {
        return komplexitaet;
    }

    public String getSpomos() {
        return spomos;
    }

    public void setHeldid(Long heldid) {
        this.heldid = heldid;
    }

    public void setKosten(Integer kosten) {
        this.kosten = kosten;
    }

    public void setKomplexitaet(String komplexitaet) {
        this.komplexitaet = komplexitaet;
    }

    public void setSpomos(String spomos) {
        this.spomos = spomos;
    }
}

