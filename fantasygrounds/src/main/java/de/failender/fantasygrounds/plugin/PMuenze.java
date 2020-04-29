package de.failender.fantasygrounds.plugin;

import helden.framework.geld.Muenze;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;

public class PMuenze implements Muenze {

    private final de.failender.heldensoftware.xml.datenxml.Muenze muenze;

    public PMuenze(de.failender.heldensoftware.xml.datenxml.Muenze muenze) {
        this.muenze = muenze;
    }

    @Override
    public String getBezeichner() {
        return muenze.getName();
    }

    @Override
    public Muenze getClone() {
        throw new NotImplementedException();
    }

    @Override
    public float getFaktorHoch() {
        throw new NotImplementedException();
    }

    @Override
    public float getFaktorRunter() {
        throw new NotImplementedException();
    }

    @Override
    public float getGewichtProStueck() {
        throw new NotImplementedException();
    }

    @Override
    public String getKurzString() {
        throw new NotImplementedException();
    }

    @Override
    public String getWaehrungsBezeichner() {
        throw new NotImplementedException();
    }

    @Override
    public float getWertInSilberstuecke() {
        throw new NotImplementedException();
    }

    public int getAnzahl() {
        return Math.toIntExact(muenze.getAnzahl());
    }
}
