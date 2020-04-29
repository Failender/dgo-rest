package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Fernkampfwaffe;
import de.failender.heldensoftware.xml.datenxml.Kampfset;
import de.failender.heldensoftware.xml.datenxml.Talent;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.*;
import helden.plugin.werteplugin2.PluginAusruestung2;
import helden.plugin.werteplugin2.PluginNahkampfWaffe2;
import helden.plugin.werteplugin2.PluginSchildParadewaffe;

import java.util.List;

public class PPluginAusruestung implements PluginAusruestung2, PluginAusruestung {

    private final Kampfset kampfset;
    private final List<Talent> talente;

    public PPluginAusruestung(Kampfset kampfset, List<Talent> talente) {
        this.kampfset = kampfset;
        this.talente = talente;
    }

    @Override
    public int getAusweichen() {
        return kampfset.getAusweichen().intValue();
    }

    @Override
    public PluginFernkampfWaffe[] getFernkampfWaffen() {
        return kampfset.getFernkampfwaffen().getFernkampfwaffe().stream()
                .map(this::map)
                .toArray(PFernkampfWaffe[]::new);
    }

    private PFernkampfWaffe map(Fernkampfwaffe fernkampfwaffe) {
        return new PFernkampfWaffe(fernkampfwaffe, talente);
    }

    @Override
    public PluginRuestungsTeil getGesammtRuestung(PluginRuestungsTeil[] pluginRuestungsTeils) {
        throw new NotImplementedException();
    }

    @Override
    public PluginRuestungsTeil getGesammtRuestung() {
        return new PRuestungsTeil(kampfset);

    }

    @Override
    public int getKomboMitSchildParadewaffe(PluginNahkampfWaffe2 pnw) {
        throw new NotImplementedException();
    }

    @Override
    public PluginNahkampfWaffe2[] getNahkampfWaffen() {
        return kampfset.getNahkampfwaffen().getNahkampfwaffe().stream()
                .map(PNahkampfWaffe::new).toArray(PluginNahkampfWaffe2[]::new);
    }

    @Override
    public PluginParadeWaffe[] getParadeWaffen() {
        throw new NotImplementedException();
    }

    @Override
    public int getRaufenParade() {
        return Integer.valueOf(kampfset.getRaufen().getPa());
    }

    @Override
    public String getRaufenTP() {
        return kampfset.getRaufen().getTp();
    }

    @Override
    public int getRauferAttacke() {
        return Integer.valueOf(kampfset.getRaufen().getAt());

    }

    @Override
    public int getRingenAttacke() {
        return Integer.valueOf(kampfset.getRingen().getAt());
    }

    @Override
    public int getRingenParade() {
        return Integer.valueOf(kampfset.getRingen().getPa());
    }

    @Override
    public String getRingenTP() {
        return kampfset.getRingen().getTp();
    }

    @Override
    public PluginRuestungsTeil[] getRuestungsTeile() {
        return kampfset.getRuestungen().getRuestung().stream().map(entry -> new PRuestungsTeil(entry)).toArray(PluginRuestungsTeil[]::new);
    }

    @Override
    public PluginSchild[] getSchilde() {
        throw new NotImplementedException();
    }

    @Override
    public PluginSchildParadewaffe[] getSchildParadewaffe() {
        return kampfset.getSchilder().getSchild().stream()
                .map(PSchildParadeWaffe::new)
                .toArray(PSchildParadeWaffe[]::new);
    }

    @Override
    public boolean istZonenRuestungsBerechnung() {
        return kampfset.isDefaultrsmodel();
    }
}
