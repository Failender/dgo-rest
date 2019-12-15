package de.failender.dgo.rest.helden.steigern;

public class Talent {

    private final String nane;
    private Kategorie kategorie;

    public Talent(String nane) {
        this.nane = nane;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
        kategorie.addTalent(this);
    }

    public String getNane() {
        return nane;
    }
}
