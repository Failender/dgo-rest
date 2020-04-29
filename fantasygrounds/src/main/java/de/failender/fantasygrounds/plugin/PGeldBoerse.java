package de.failender.fantasygrounds.plugin;

import helden.framework.geld.GeldBoerse;
import helden.framework.geld.Muenze;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class PGeldBoerse implements GeldBoerse {

    private final List<de.failender.heldensoftware.xml.datenxml.Muenze> muenzen;

    public PGeldBoerse(List<de.failender.heldensoftware.xml.datenxml.Muenze> muenzen) {
        this.muenzen = muenzen;
    }

    @Override
    public void addMuenze(Muenze muenze, int i) {
        throw new NotImplementedException();
    }

    @Override
    public Vector<String> getGeldStrings(boolean b) {
        throw new NotImplementedException();
    }

    @Override
    public int getMuenzAnzahl(Muenze muenze) {
        return ((PMuenze) muenze).getAnzahl();
    }

    @Override
    public Iterator<Muenze> getMuenzeIter() {
        return muenzen
                .stream()
                .map(PMuenze::new)
                .map(muenze -> (Muenze) muenze)
                .collect(Collectors.toList())
                .iterator();
    }

    @Override
    public void putMuenze(Muenze muenze, int i) {
        throw new NotImplementedException();
    }
}
