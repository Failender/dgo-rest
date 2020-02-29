package de.failender.dgo.rest.helden.inventar;

public class Gegenstand {
    private String name;
    private int anzahl;
    private boolean deletable;
    private String lagerort;

    public Gegenstand(String name, int anzahl, boolean deletable) {
        this.name = name;
        this.anzahl = anzahl;
        this.deletable = deletable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public boolean getDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public void setLagerort(String lagerort) {
        this.lagerort = lagerort;
    }

    public String getLagerort() {
        return lagerort;
    }
}
