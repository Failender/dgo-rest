package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Vorteil;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.PluginVorteil;

public class PVorteil implements PluginVorteil {

    private final Vorteil vorteil;

    public PVorteil(Vorteil vorteil) {
        this.vorteil = vorteil;
    }

    @Override
    public String[] getAusgewaehltes() {
        throw new NotImplementedException();
    }

    @Override
    public String getName() {
        return vorteil.getBezeichner();
    }

    @Override
    public int getWert() {
        return vorteil.getWert();
    }

    @Override
    public boolean isAuswahlVorteil() {
        return vorteil.getAuswahlen() != null && !vorteil.getAuswahlen().getAuswahl().isEmpty();
    }

    @Override
    public boolean isMehfachAuswahlVorteil() {
        return vorteil.getAuswahlen() != null && !vorteil.getAuswahlen().getAuswahl().isEmpty();

    }

    @Override
    public boolean isNachteil() {
        return vorteil.isIstnachteil();
    }

    @Override
    public boolean isWertVorteil() {
        return vorteil.getWert() != null;
    }

    public String getKommentar() {
        return vorteil.getKommentar();
    }
}
