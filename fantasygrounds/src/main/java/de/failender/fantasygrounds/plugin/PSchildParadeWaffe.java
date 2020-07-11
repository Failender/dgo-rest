package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Schild;
import helden.plugin.werteplugin2.PluginSchildParadewaffe;

public class PSchildParadeWaffe implements PluginSchildParadewaffe {

    private final Schild schild;

    public PSchildParadeWaffe(Schild schild) {
        this.schild = schild;
    }

    @Override
    public String getBenutzungsart() {
        return schild.getTyp();
    }

    @Override
    public String getName() {
        return schild.getName();
    }

    @Override
    public int getParade() {
        return Integer.valueOf(schild.getPa().trim());
    }

    @Override
    public int getBruchfaktor() {
        return Integer.valueOf(schild.getBfakt().intValue());
    }

    @Override
    public int getBruchfaktorMin() {
        return schild.getBfmin().intValue();
    }

    @Override
    public int getInitiativeModifikator() {
        return schild.getIni().intValue();
    }

    @Override
    public int getWaffenModifikatorAT() {
        return Integer.valueOf(schild.getMod().split("/")[0].trim());
    }

    @Override
    public int getWaffenModifikatorPA() {
        return Integer.valueOf(schild.getMod().split("/")[1].trim());

    }

    @Override
    public String toString() {
        return schild.getName();
    }
}
