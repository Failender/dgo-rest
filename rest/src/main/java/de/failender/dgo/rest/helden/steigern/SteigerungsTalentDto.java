package de.failender.dgo.rest.helden.steigern;

import de.failender.heldensoftware.xml.listtalente.SteigerungsTalent;

import javax.xml.bind.annotation.XmlElement;

public class SteigerungsTalentDto {

    private String talent;
    private String lernmethode;
    private int talentwert;
    private String art;
    private int kosten;
    private boolean se;

    public SteigerungsTalentDto(SteigerungsTalent steigerungsTalent, boolean se) {
        this.talent = steigerungsTalent.getTalent();
        this.lernmethode = steigerungsTalent.getLernmethode();
        this.talentwert = steigerungsTalent.getTalentwert();
        this.art = steigerungsTalent.getArt();
        this.kosten = steigerungsTalent.getKosten();
        this.se = se;
    }

    public String getTalent() {
        return talent;
    }

    public void setTalent(String talent) {
        this.talent = talent;
    }

    public String getLernmethode() {
        return lernmethode;
    }

    public void setLernmethode(String lernmethode) {
        this.lernmethode = lernmethode;
    }

    public int getTalentwert() {
        return talentwert;
    }

    public void setTalentwert(int talentwert) {
        this.talentwert = talentwert;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public int getKosten() {
        return kosten;
    }

    public void setKosten(int kosten) {
        this.kosten = kosten;
    }

    public boolean isSe() {
        return se;
    }

    public void setSe(boolean se) {
        this.se = se;
    }
}
