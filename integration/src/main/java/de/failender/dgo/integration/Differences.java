// Generated by delombok at Thu Nov 22 18:54:11 CET 2018
package de.failender.dgo.integration;

import java.util.List;

public class Differences {
    private String heldname;
    private List<Difference> talente;
    private List<Difference> zauber;
    private List<Difference> vorteile;
    private List<Difference> eigenschaften;
    private List<Difference> sonderfertigkeiten;
    private List<Steigerung> steigerungen;


    public Differences(final String heldname, final List<Difference> talente,
                       final List<Difference> zauber, final List<Difference> vorteile,
                       final List<Difference> eigenschaften, final List<Difference> sonderfertigkeiten) {
        this.heldname = heldname;
        this.talente = talente;
        this.zauber = zauber;
        this.vorteile = vorteile;
        this.eigenschaften = eigenschaften;
        this.sonderfertigkeiten = sonderfertigkeiten;
    }

    public String getHeldname() {
        return this.heldname;
    }

    public List<Difference> getTalente() {
        return this.talente;
    }

    public List<Difference> getZauber() {
        return this.zauber;
    }

    public List<Difference> getVorteile() {
        return this.vorteile;
    }

    public List<Difference> getEigenschaften() {
        return this.eigenschaften;
    }

    public List<Difference> getSonderfertigkeiten() {
        return sonderfertigkeiten;
    }

    public List<Steigerung> getSteigerungen() {
        return steigerungen;
    }

    public void setSteigerungen(List<Steigerung> steigerungen) {
        this.steigerungen = steigerungen;
    }
}