package de.failender.dgo.persistance.zauberspeicher;

import de.failender.dgo.persistance.BaseEntity;

public class ZauberspeicherEntity extends BaseEntity {

    private Long heldid;
    private Integer kosten;
    private Integer qualitaet;
    private String komplexitaet;
    private String spomos;
    private String zauber;
    private Integer zfw;
    private Integer mr;

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

    public Integer getQualitaet() {
        return qualitaet;
    }

    public void setQualitaet(Integer qualitaet) {
        this.qualitaet = qualitaet;
    }

    public String getZauber() {
        return zauber;
    }

    public void setZauber(String zauber) {
        this.zauber = zauber;
    }

    public Integer getZfw() {
        return zfw;
    }

    public void setZfw(Integer zfw) {
        this.zfw = zfw;
    }

    public Integer getMr() {
        return mr;
    }

    public void setMr(Integer mr) {
        this.mr = mr;
    }
}

