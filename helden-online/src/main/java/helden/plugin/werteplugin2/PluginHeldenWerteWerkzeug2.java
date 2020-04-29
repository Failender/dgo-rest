package helden.plugin.werteplugin2;

import helden.plugin.werteplugin.*;

import java.util.ArrayList;

/**
 * Die Erweiterte Version des PluginHeldenWerteWerkzeug
 *
 * @author Maik Schiefer-Gehrke
 * @since 4.7.5
 */
public interface PluginHeldenWerteWerkzeug2 extends PluginHeldenWerteWerkzeug {

    /**
     * Liefert die Ausr�stung des Helden
     *
     * @return PluginAusr�stung
     * @since 4.7.5
     */
    PluginAusruestung2 getAusruestung2();


    /**
     * Liefert Basis Komplexit�t des Zaubers
     *
     * @param pz der Zauber
     * @return Komplexit�t a bis h
     * @since 4.7.5
     */
    String getBasisKomplexitaet(PluginZauber pz);


    /**
     * gibt die eingestzten AP zur�ck
     *
     * @return eingesetzten Abenteuerpunkte
     * @since 4.7.5
     */
    int getEingestzteAbenteuerpunkte();


    /**
     * Gibt die Gegenst�nde aus dem Inventar des Helden zur�ck, die zu dem
     * Bezeichner geh�ren
     *
     * @param bezeichnung Des Gegenstandes
     * @return PluginGegenstand
     * @since 4.7.5
     */
    PluginGegenstand[] getGegenstand(String bezeichnung);


    /**
     * Liefert die Geschwindigkeit des Helden
     *
     * @return Geschwindigkeit
     * @since 4.7.5
     */
    int getGeschwindigkeit();


    /**
     * Liefert den Pfad zu dem Aktuell benutzen Pfad
     *
     * @return Pfad zu der Aktiuell benutzen Gruppe
     */
    String[] getGruppenPath();


    /**
     * Gibt den eindeutigen Identifikator des aktuellen Helden zur�ck
     *
     * @return HeldenID
     * @since 4.7.5
     */
    String getHeldenID();


    /**
     * Gibt das Inventar des Helden als Strings zur�ck
     *
     * @return ArrayList<Strings ( Inventar-Gegenstand )>
     * @since 4.7.5
     */
    ArrayList<String> getHeldenInventarAlsString();


    /**
     * Lifert den Kommentar zu einer Sonderfetigkeit
     *
     * @param psf Sonderfetigkeit
     * @return Kommentar
     * @since 4.7.5
     */
    String getKommentar(PluginSonderfertigkeit psf);


    /**
     * Lifert den Kommentar zu einem Vorteil
     *
     * @param pvt Vorteil
     * @return Kommentar
     * @since 4.7.5
     */
    String getKommentar(PluginVorteil pvt);


    /**
     * Liefert Lern Komplexit�t des Zaubers
     *
     * @param pz der Zauber
     * @return Komplexit�t a bis h (Kleiner als a -> a+, a2, a3, ..)
     * @since 4.7.5
     */
    String getLernKomplexitaet(PluginZauber pz);


    /**
     * Liefert den Pfad zum Protrait Sollte kein Pfad eingetragen sein wird Null
     * zur�ck gegeben. Es wird auch nicht sichergestellt, das das Bild vorhanden
     * ist.
     *
     * @return Pfad zum Bild
     * @since 4.7.5
     */
    String getPfadZumPortrait();


    /**
     * Liefert Komplexit�t einer Sprache oder Schrift Sonst "";
     *
     * @param pt die Sprache oder Schrift
     * @return Komplexit�t a bis h (Kleiner als a -> a+, a2, a3, ..)
     * @since 4.7.5
     */
    String getSprachKomplexitaet(PluginTalent pt);


    /**
     * gitb den Baum der Gruppen zur�ck
     *
     * @return Root des Gruppen Baumes
     * @since 4.7.5
     */
    PluginTreeNode getTreeRoot();


    /**
     * gibt die verf�hgbaren (also freien) AP zur�ck
     *
     * @return Verfuegbare Abenteuerpunkte
     * @since 4.7.5
     */
    int getVerfuegbareAbenteuerpunkte();
}
