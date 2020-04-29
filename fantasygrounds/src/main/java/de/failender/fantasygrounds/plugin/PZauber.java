package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Zauber;
import helden.plugin.werteplugin.PluginZauber;

public class PZauber implements PluginZauber {

    private final Zauber zauber;

    public PZauber(Zauber zauber) {
        this.zauber = zauber;
    }

    @Override
    public String getBezeichnung() {
        return zauber.getName();
    }

    @Override
    public String[] getMerkmale() {
        return null;
    }

    @Override
    public String[] getProbe() {
        return zauber.getProbe().split("/");
    }

    @Override
    public String[] getRepraesentationen() {
        return new String[]{zauber.getRepraesentation()};
    }

    @Override
    public Object getZauber() {
        return null;
    }

    public int getWert() {
        return zauber.getWert();
    }
}
