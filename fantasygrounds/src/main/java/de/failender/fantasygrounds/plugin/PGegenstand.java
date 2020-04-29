package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Gegenstand;
import helden.plugin.werteplugin2.PluginGegenstand;

public class PGegenstand implements PluginGegenstand {

    private final Gegenstand gegenstand;

    public PGegenstand(Gegenstand gegenstand) {
        this.gegenstand = gegenstand;
    }

    @Override
    public int getAnzahl() {
        return gegenstand.getAnzahl().intValue();
    }

    @Override
    public float getGewicht() {
        return gegenstand.getGewicht().floatValue();
    }

    @Override
    public String getName() {
        return gegenstand.getName();
    }

    @Override
    public int getPreis() {
        return gegenstand.getEinzelpreis().intValue();
    }
}
