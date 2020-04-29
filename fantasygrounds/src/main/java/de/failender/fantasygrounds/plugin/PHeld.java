package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Daten;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.HeldAngaben;
import helden.plugin.werteplugin.PluginHeld;

public class PHeld implements PluginHeld {

    private final Daten daten;

    public PHeld(Daten daten) {
        this.daten = daten;
    }

    @Override
    public int getAbenteuerpunkte() {
        return daten.getAngaben().getAp().getGesamt().intValue();
    }

    @Override
    public HeldAngaben getAngaben() {
        return new PAngaben(daten.getAngaben());
    }

    @Override
    public String getGeschlechtString() {
        return daten.getAngaben().getGeschlecht();
    }

    @Override
    public Object getHeld() {
        throw new NotImplementedException();
    }

    @Override
    public String getKulturString() {
        return daten.getAngaben().getKultur();
    }

    @Override
    public String getProfessionString() {
        return daten.getAngaben().getProfession().getText();
    }

    @Override
    public String getRasseString() {
        return daten.getAngaben().getRasse();
    }

    @Override
    public int getStufe() {
        return daten.getAngaben().getStufe41().getAkt().intValue();
    }

    @Override
    public boolean hatZaubersprueche() {
        return daten.getZauberliste().getZauber().isEmpty();
    }

    @Override
    public String toString() {
        return daten.getAngaben().getName();
    }
}
