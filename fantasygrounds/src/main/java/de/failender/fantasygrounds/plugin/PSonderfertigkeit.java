package de.failender.fantasygrounds.plugin;

import de.failender.heldensoftware.xml.datenxml.Sonderfertigkeit;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.PluginSonderfertigkeit;
import helden.plugin.werteplugin.PluginTalent;

public class PSonderfertigkeit implements PluginSonderfertigkeit {

    private final Sonderfertigkeit sonderfertigkeit;

    public PSonderfertigkeit(Sonderfertigkeit sonderfertigkeit) {
        this.sonderfertigkeit = sonderfertigkeit;
    }

    @Override
    public int getArt() {
        throw new NotImplementedException();
    }

    @Override
    public String getSpezialisierung() {
        throw new NotImplementedException();
    }

    @Override
    public PluginTalent getTSTalent() {
        throw new NotImplementedException();
    }

    @Override
    public boolean istElfenlied() {
        return sonderfertigkeit.getName().contains("Elfenlied");
    }

    @Override
    public boolean istFernkampfsonderfertigkeit() {

        return sonderfertigkeit.getBereich().contains("Fernkampf");
    }

    @Override
    public boolean istGelaendekunde() {
        return sonderfertigkeit.getBereich().contains("Gel\\u00e4ndekunde");
    }

    @Override
    public boolean istHexenfluch() {
        return sonderfertigkeit.getName().contains("Hexenfluch");


    }

    @Override
    public boolean istKampfSonderfertigkeit() {
        return sonderfertigkeit.getBereich().contains("Kampf");
    }

    @Override
    public boolean istKlerikal() {
        return sonderfertigkeit.getBereich().contains("Geweiht");
    }

    @Override
    public boolean istLiturgie() {
        return sonderfertigkeit.getBereich().contains("Liturgie");
    }

    @Override
    public boolean istLiturgiekenntnis() {
        return sonderfertigkeit.getBereich().contains("Liturgiekenntnis");

    }

    @Override
    public boolean istMagisch() {
        throw new NotImplementedException();
    }

    @Override
    public boolean istManoever() {
        throw new NotImplementedException();
    }

    @Override
    public boolean istMerkmalskenntnis() {
        throw new NotImplementedException();
    }

    @Override
    public boolean istNahkampfsonderfertigkeit() {
        return sonderfertigkeit.getBereich().contains("Nahkampf");
    }

    @Override
    public boolean istRepraesentation() {
        throw new NotImplementedException();
    }

    @Override
    public boolean istRitual() {
        throw new NotImplementedException();
    }

    @Override
    public boolean istSchamanenRitualkenntnis() {
        return sonderfertigkeit.getBereich().contains("Schamane");
    }

    @Override
    public boolean istTalentspezialisierung() {
        throw new NotImplementedException();
    }

    @Override
    public boolean istWaffenloseKampfstil() {
        throw new NotImplementedException();
    }

    public String getKommentar() {
        return sonderfertigkeit.getKommentar();
    }

    @Override
    public String toString() {
        return sonderfertigkeit.getNameausfuehrlich();
    }
}
