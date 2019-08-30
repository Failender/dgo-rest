package de.failender.dgo.rest.kampf;

import java.util.List;

public class Kampf {

    private String name;
    private int gruppe;
    private int currentTeilnehmer;
    private List<Teilnehmer> teilnehmer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGruppe() {
        return gruppe;
    }

    public void setGruppe(int gruppe) {
        this.gruppe = gruppe;
    }

    public List<Teilnehmer> getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(List<Teilnehmer> teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public int getCurrentTeilnehmer() {
        return currentTeilnehmer;
    }

    public void setCurrentTeilnehmer(int currentTeilnehmer) {
        this.currentTeilnehmer = currentTeilnehmer;
    }
}
