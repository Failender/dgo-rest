package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Kampfset;
import de.failender.heldensoftware.xml.datenxml.Ruestung;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.PluginRuestungsTeil;

public class PRuestungsTeil implements PluginRuestungsTeil {

    private final Ruestung ruestung;
    private final Kampfset kampfset;

    public PRuestungsTeil(Ruestung ruestung) {
        this.ruestung = ruestung;
        this.kampfset = null;
    }

    public PRuestungsTeil(Kampfset kampfset) {
        this.kampfset = kampfset;
        this.ruestung = null;
    }


    @Override
    public int getAnzahlTeile() {
        throw new NotImplementedException();
    }

    @Override
    public int getBauchSchutz() {
        return ruestung.getBauch().intValue();
    }

    @Override
    public int getBrustSchutz() {
        return ruestung.getBrust().intValue();
    }

    @Override
    public int getGesammtBehinderung() {
        if (kampfset != null) {
            return Integer.valueOf(kampfset.getRuestungzonen().getBehinderung());
        }
        return Integer.valueOf(ruestung.getBehinderung());
    }

    @Override
    public int getGesammtZonenSchutz() {
        return ruestung.getGesamtzonenschutz().intValue();
    }

    @Override
    public int getGesamtSchutz() {
        return ruestung.getGesamt().intValue();
    }

    @Override
    public int getKopfSchutz() {
        return ruestung.getKopf().intValue();
    }

    @Override
    public int getLinkerArmSchutz() {
        return ruestung.getLinkerarm().intValue();

    }

    @Override
    public int getLinkesBeinSchutz() {
        return ruestung.getLinkesbein().intValue();

    }

    @Override
    public int getRechterArmSchutz() {
        return ruestung.getRechterarm().intValue();


    }

    @Override
    public int getRechtesBeinSchutz() {
        return ruestung.getRechtesbein().intValue();

    }

    @Override
    public int getRueckenSchutz() {
        return ruestung.getRuecken().intValue();

    }

    @Override
    public Object getRuestungsteil() {
        throw new NotImplementedException();
    }

    @Override
    public boolean istZeug() {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return ruestung.getName();
    }
}
