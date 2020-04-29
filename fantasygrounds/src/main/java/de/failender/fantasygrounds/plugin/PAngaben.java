package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Angaben;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.HeldAngaben;

public class PAngaben implements HeldAngaben {

    private final Angaben angaben;

    public PAngaben(Angaben angaben) {
        this.angaben = angaben;
    }

    @Override
    public String getAugenFarbe() {
        return angaben.getAugenfarbe();
    }

    @Override
    public String[] getAussehenText() {
        throw new NotImplementedException();
    }

    @Override
    public String[] getFamilieText() {
        throw new NotImplementedException();

    }

    @Override
    public String getGeburtstagString() {
        return angaben.getGeburtstag();

    }

    @Override
    public int getGewicht(boolean fettleibig) {
        return angaben.getGewicht().intValue();

    }

    @Override
    public int getGPRest() {
        throw new NotImplementedException();

    }

    @Override
    public int getGPStart() {
        throw new NotImplementedException();

    }

    @Override
    public String getGpWerte() {
        throw new NotImplementedException();

    }

    @Override
    public int getGroesse() {
        return angaben.getGroesse().intValue();

    }

    @Override
    public String getHaarFarbe() {
        return angaben.getHaarfarbe();

    }

    @Override
    public String[] getNotiz() {
        throw new NotImplementedException();

    }

    @Override
    public String getStand() {
        return angaben.getStand();

    }

    @Override
    public String getTitel() {
        return angaben.getTitel();

    }
}
