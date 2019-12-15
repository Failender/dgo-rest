package de.failender.dgo.rest.helden.steigern;

import java.util.ArrayList;
import java.util.List;

public class Kategorie {


    private final SteigerungsSpalte spalte;
    private final String name;
    private List<Talent> talente = new ArrayList<>();

    public Kategorie(SteigerungsSpalte spalte, String name) {
        this.spalte = spalte;
        this.name = name;
    }

    public void addTalent(Talent talent) {
        talente.add(talent);
    }

    public SteigerungsSpalte getSpalte() {
        return spalte;
    }

    public String getName() {
        return name;
    }
}
