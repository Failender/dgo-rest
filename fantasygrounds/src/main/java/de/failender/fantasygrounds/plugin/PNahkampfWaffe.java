package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Nahkampfwaffe;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.PluginTalent;
import helden.plugin.werteplugin2.PluginNahkampfWaffe2;

import java.util.ArrayList;

public class PNahkampfWaffe implements PluginNahkampfWaffe2 {

    private final Nahkampfwaffe nahkampfwaffe;

    public PNahkampfWaffe(Nahkampfwaffe nahkampfwaffe) {
        this.nahkampfwaffe = nahkampfwaffe;
    }

    @Override
    public int getAttacke() {
        return Integer.valueOf(nahkampfwaffe.getAt());
    }

    @Override
    public PluginTalent getBenutztesTalent() {
        throw new NotImplementedException();
    }

    @Override
    public int[] getBF() {
        return new int[]{nahkampfwaffe.getBfmin().intValue(), nahkampfwaffe.getBfakt().intValue()};
    }

    @Override
    public int[] getEndTP() {
        throw new NotImplementedException();
    }

    @Override
    public int getINIMod() {
        return nahkampfwaffe.getIni().intValue();
    }

    @Override
    public String getName() {
        return nahkampfwaffe.getName();
    }

    @Override
    public int getParade() {
        return Integer.valueOf(nahkampfwaffe.getPa().trim());
    }

    @Override
    public int getWaffenNummer() {
        throw new NotImplementedException();
    }

    @Override
    public String[] getDistanzklasse() {
        return new String[]{nahkampfwaffe.getDk()};

    }

    @Override
    public ArrayList<PluginTalent> getKampfTalente() {
        throw new NotImplementedException();
    }

    @Override
    public int[] getKoerperkraftzuschlag() {
        return new int[]{nahkampfwaffe.getTpkk().getSchwelle().intValue(), nahkampfwaffe.getTpkk().getSchrittweite().intValue()};
    }

    @Override
    public PluginTalent[] getTalente() {
        throw new NotImplementedException();
    }

    @Override
    public int[] getTrefferpunkte() {
        int diceCount = Integer.valueOf(nahkampfwaffe.getTp().substring(0, nahkampfwaffe.getTp().indexOf("W")));
        int diceBonus = Integer.valueOf(nahkampfwaffe.getTp().substring(nahkampfwaffe.getTp().indexOf("+") + 1));
        return new int[]{diceCount, 6, diceBonus};
    }

    @Override
    public int getWmAT() {
        return Integer.valueOf(nahkampfwaffe.getWm().split("/")[0].trim());
    }

    @Override
    public int getWmPA() {
        return Integer.valueOf(nahkampfwaffe.getWm().split("/")[1].trim());
    }

    @Override
    public boolean isSchadensartAusdauer() {
        throw new NotImplementedException();
    }
}
