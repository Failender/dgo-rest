package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Talent;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.PluginTalent;

public class PTalent implements PluginTalent {

    private final Talent talent;

    public PTalent(Talent talent) {
        this.talent = talent;
    }

    @Override
    public String getBehinderung() {
        return talent.getBehinderung();
    }

    @Override
    public String getBezeichnung() {
        throw new NotImplementedException();
    }

    @Override
    public String[] getProbe() {
        return talent.getProbe().split("/");
    }

    @Override
    public Object getTalent() {
        throw new NotImplementedException();
    }

    @Override
    public String getTalentart() {
        return talent.getBereich();
    }

    public int getTalentwert() {
        return talent.getWert();
    }

    public int getAttacke() {
        return Integer.parseInt(talent.getAt().trim());
    }

    public int getParade() {
        if (talent.getPa().trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(talent.getPa().trim());
    }

    public String getKomplexitaet() {
        return talent.getKomplexitaet();
    }


    public String getSprachKomplexitaet() {
        return String.valueOf(talent.getSprachkomplexitaet());
    }

    @Override
    public String toString() {
        return talent.getName();
    }

    public String getNameAusfuehrlich() {
        return talent.getNameausfuehrlich();
    }
}
