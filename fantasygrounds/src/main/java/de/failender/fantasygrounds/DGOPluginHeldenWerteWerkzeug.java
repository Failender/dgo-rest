package de.failender.fantasygrounds;

import de.failender.fantasygrounds.plugin.*;
import de.failender.heldensoftware.xml.datenxml.Daten;
import helden.framework.geld.GeldBoerse;
import helden.framework.geld.WaehrungsCollection;
import helden.plugin.ausruestungsplugin.impl.NotImplementedException;
import helden.plugin.werteplugin.*;
import helden.plugin.werteplugin2.PluginAusruestung2;
import helden.plugin.werteplugin2.PluginGegenstand;
import helden.plugin.werteplugin2.PluginHeldenWerteWerkzeug2;
import helden.plugin.werteplugin2.PluginTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DGOPluginHeldenWerteWerkzeug implements PluginHeldenWerteWerkzeug2 {


    private final Daten daten;

    public DGOPluginHeldenWerteWerkzeug(Daten daten) {
        this.daten = daten;
    }

    @Override
    public PluginAusruestung2 getAusruestung2() {
        return new PPluginAusruestung(daten.getKampfsets().getKampfset().get(0), daten.getTalentliste().getTalent());
    }

    @Override
    public String getBasisKomplexitaet(PluginZauber pluginZauber) {
        throw new NotImplementedException();
    }

    @Override
    public int getEingestzteAbenteuerpunkte() {
        return daten.getAngaben().getAp().getGenutzt().intValue();
    }

    @Override
    public PluginGegenstand[] getGegenstand(String s) {
        return daten.getGegenstaende().getGegenstand().stream()
                .filter(entry -> entry.getName().equals(s))
                .map(PGegenstand::new).toArray(PluginGegenstand[]::new);

    }

    @Override
    public int getGeschwindigkeit() {
        return daten.getEigenschaften().getGeschwindigkeit().getAkt().intValue();
    }

    @Override
    public String[] getGruppenPath() {
        throw new NotImplementedException();
    }

    @Override
    public String getHeldenID() {
        throw new NotImplementedException();
    }

    @Override
    public ArrayList<String> getHeldenInventarAlsString() {
        return new ArrayList<>(daten.getGegenstaende().getGegenstand().stream()
                .map(item -> item.getName())
                .collect(Collectors.toList()));

    }

    public List<PluginGegenstand> getInventar() {
        return daten.getGegenstaende().getGegenstand().stream().map(PGegenstand::new).collect(Collectors.toList());
    }

    @Override
    public String getKommentar(PluginSonderfertigkeit pluginSonderfertigkeit) {
        String kommentar = ((PSonderfertigkeit) pluginSonderfertigkeit).getKommentar();
        if (kommentar == null || kommentar.isEmpty()) {
            return null;
        }
        return kommentar;
    }

    @Override
    public String getKommentar(PluginVorteil pluginVorteil) {
        PVorteil pVorteil = (PVorteil) pluginVorteil;
        if (pVorteil.getKommentar() == null || pVorteil.getKommentar().isEmpty()) {
            return null;
        }
        return pVorteil.getKommentar();
    }

    @Override
    public String getLernKomplexitaet(PluginZauber pluginZauber) {
        throw new NotImplementedException();
    }

    @Override
    public String getPfadZumPortrait() {
        throw new NotImplementedException();
    }

    @Override
    public String getSprachKomplexitaet(PluginTalent pluginTalent) {
        return ((PTalent) pluginTalent).getSprachKomplexitaet();

    }

    @Override
    public PluginTreeNode getTreeRoot() {
        throw new NotImplementedException();
    }

    @Override
    public int getVerfuegbareAbenteuerpunkte() {
        return daten.getAngaben().getAp().getFrei().intValue();
    }

    @Override
    public int getAttacke(PluginTalent pluginTalent) {
        return ((PTalent) pluginTalent).getAttacke();
    }

    @Override
    public PluginAusruestung getAusruestung() {
        return new PPluginAusruestung(daten.getKampfsets().getKampfset().get(0), daten.getTalentliste().getTalent());
    }

    @Override
    public String[] getEigenschaftsbezeichner() {
        throw new NotImplementedException();
    }

    @Override
    public int getEigenschaftswert(String s) {
        if (s == XmlWorker.MUT[0]) {
            return daten.getEigenschaften().getMut().getAkt().intValue();
        } else if (s == XmlWorker.KLUGHEIT[0]) {
            return daten.getEigenschaften().getKlugheit().getAkt().intValue();
        } else if (s == XmlWorker.INTUITION[0]) {
            return daten.getEigenschaften().getIntuition().getAkt().intValue();
        } else if (s == XmlWorker.CHARISMA[0]) {
            return daten.getEigenschaften().getCharisma().getAkt().intValue();
        } else if (s == XmlWorker.FINGERFERTIGKEIT[0]) {
            return daten.getEigenschaften().getFingerfertigkeit().getAkt().intValue();
        } else if (s == XmlWorker.GEWANDHEIT[0]) {
            return daten.getEigenschaften().getGewandtheit().getAkt().intValue();
        } else if (s == XmlWorker.KONSTITUION[0]) {
            return daten.getEigenschaften().getKonstitution().getAkt().intValue();
        } else if (s == XmlWorker.KOERPERKRAFT[0]) {
            return daten.getEigenschaften().getKoerperkraft().getAkt().intValue();
        } else if (s == XmlWorker.SOZIALSTATUS[0]) {
            return daten.getEigenschaften().getSozialstatus().getAkt().intValue();
        } else if (s == XmlWorker.MAGIERESISTENZ[0]) {
            return daten.getEigenschaften().getMagieresistenz().getAkt().intValue();
        } else if (s == XmlWorker.ATTACKE[0]) {
            return daten.getEigenschaften().getAttacke().getAkt().intValue();
        } else if (s == XmlWorker.PARADE[0]) {
            return daten.getEigenschaften().getParade().getAkt().intValue();
        } else if (s == XmlWorker.ASTRALENERGIE[0]) {
            return daten.getEigenschaften().getAstralenergie().getAkt().intValue();
        } else if (s == XmlWorker.KARMAENERGIE[0]) {
            return daten.getEigenschaften().getKarmaenergie().getAkt().intValue();
        } else if (s == XmlWorker.AUSDAUER[0]) {
            return daten.getEigenschaften().getAusdauer().getAkt().intValue();
        } else if (s == XmlWorker.FERNKAMPF[0]) {
            return daten.getEigenschaften().getFernkampfBasis().getAkt().intValue();
        } else if (s == XmlWorker.INITIATIVE[0]) {
            return daten.getEigenschaften().getInitiative().getAkt().intValue();
        } else if (s == XmlWorker.LEBENSENERGIE[0]) {
            return daten.getEigenschaften().getLebensenergie().getAkt().intValue();
        }
        System.err.println(s);
        throw new NotImplementedException();
    }

    @Override
    public GeldBoerse getGeldBoerse() {
        return new PGeldBoerse(daten.getMuenzen().getMuenze());
    }

    @Override
    public ArrayList<String> getInventarAlsString() {
        throw new NotImplementedException();
    }

    @Override
    public int getParade(PluginTalent pluginTalent) {
        return ((PTalent) pluginTalent).getParade();

    }

    @Override
    public int getRassenGeschwindigkeit() {
        throw new NotImplementedException();
    }

    @Override
    public PluginHeld getSelectesHeld() {
        return new PHeld(daten);
    }

    @Override
    public PluginSonderfertigkeit getSonderfertigkeit(String s) {
        return daten.getSonderfertigkeiten().getSonderfertigkeit().stream().filter(entry -> entry.getName().equals(s)).findFirst().map(PSonderfertigkeit::new).orElse(null);
    }

    @Override
    public String[] getSonderfertigkeitenAlsString() {
        return daten.getSonderfertigkeiten().getSonderfertigkeit()
                .stream().map(entry -> entry.getName()).toArray(String[]::new);
    }

    @Override
    public PTalent getTalent(String s) {

        return daten.getTalentliste().getTalent()
                .stream()
                .filter(entry -> entry.getName().equals(s))
                .findFirst()
                .map(PTalent::new)
                .orElse(null);
    }

    @Override
    public String[] getTalenteAlsString() {
        return daten.getTalentliste().getTalent().stream().map(talent -> talent.getName()).toArray(String[]::new);
    }

    @Override
    public int getTalentwert(PluginTalent pluginTalent) {
        return ((PTalent) pluginTalent).getTalentwert();
    }

    @Override
    public PluginVorteil getVorteil(String s) {

        return daten.getVorteile().getVorteil()
                .stream()
                .filter(entry -> entry.getName().equals(s))
                .findFirst()
                .map(PVorteil::new).orElse(null);
    }

    @Override
    public String[] getVorteileAlsString() {
        return daten.getVorteile().getVorteil()
                .stream().map(vorteil -> vorteil.getName()).toArray(String[]::new);
    }

    @Override
    public WaehrungsCollection getWaehrungen() {
        throw new NotImplementedException();
    }

    @Override
    public PluginZauber getZauber(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public String[][] getZauberAlsString() {
        throw new NotImplementedException();
    }

    public List<PZauber> getZauber() {
        return daten.getZauberliste().getZauber().stream().map(PZauber::new).collect(Collectors.toList());
    }

    @Override
    public int getZauberfertigkeitswert(PluginZauber pluginZauber) {
        throw new NotImplementedException();
    }

    @Override
    public void setAktivenHeld(PluginHeld pluginHeld) {

    }
}
