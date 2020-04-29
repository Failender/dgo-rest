package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Fernkampfwaffe;
import de.failender.heldensoftware.xml.datenxml.Talent;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.PluginFernkampfWaffe;
import helden.plugin.werteplugin.PluginTalent;

import java.util.List;
import java.util.stream.Stream;

public class PFernkampfWaffe implements PluginFernkampfWaffe {

    private final Fernkampfwaffe fernkampfwaffe;
    private final List<Talent> talente;

    public PFernkampfWaffe(Fernkampfwaffe fernkampfwaffe, List<Talent> talente) {
        this.fernkampfwaffe = fernkampfwaffe;
        this.talente = talente;
    }

    @Override
    public int getLaden() {
        return fernkampfwaffe.getLadezeit().intValue();
    }

    @Override
    public String getMunitionsArt() {
        throw new NotImplementedException();
    }

    @Override
    public int[] getReichweite() {
        return Stream.of(fernkampfwaffe.getReichweite().split("/"))
                .map(String::trim)
                .map(Integer::valueOf)
                .mapToInt(value -> value)
                .toArray();
    }

    @Override
    public PluginTalent getTalent() {
        return talente.stream().filter(entry -> entry.getName().equals(fernkampfwaffe.getKampftalent())).findFirst()
                .map(PTalent::new).orElse(null);
    }

    @Override
    public int[] getTrefferpunkte() {
        int diceCount = Integer.valueOf(fernkampfwaffe.getTp().substring(0, fernkampfwaffe.getTp().indexOf("W")));
        int diceBonus = Integer.valueOf(fernkampfwaffe.getTp().substring(fernkampfwaffe.getTp().indexOf("+") + 1));
        return new int[]{diceCount, 6, diceBonus};
    }

    @Override
    public int[] getTrefferpunkteModifikation() {

        return Stream.of(fernkampfwaffe.getTpmod().split("/"))
                .map(String::trim)
                .map(Integer::valueOf)
                .mapToInt(value -> value)
                .toArray();
    }

    @Override
    public String toString() {
        return fernkampfwaffe.getName();
    }
}
