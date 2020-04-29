package helden.plugin.werteplugin2;

import helden.plugin.werteplugin.PluginFernkampfWaffe;
import helden.plugin.werteplugin.PluginRuestungsTeil;

/**
 * Liefert die Tats�chliche Ausr�stung die auch vom Held getragen wird.
 *
 * @author Maik Schiefer-Gehrke
 * @since 4.7.5
 */
public interface PluginAusruestung2 {

    /**
     * Gibt den Ausweichern Wert
     *
     * @return Ausweichen
     * @since 4.7.5
     */
    int getAusweichen();


    /**
     * Liefert die FernkampfWaffen
     *
     * @return PluginFernkampfWaffe
     * @since 4.7.5
     */
    PluginFernkampfWaffe[] getFernkampfWaffen();


    /**
     * Liefert die berechnette GesammtR�stung aus den Teilen
     *
     * @return PluginRuestungsTeile
     * @since 4.7.5
     */
    PluginRuestungsTeil getGesammtRuestung();


    /**
     * Gibt zur�ck ob die Waffe eine Kombi mit einem Schild / Paradewaffe bildet
     * 0 seine Kombi, 1/2 f�r Schild Paradewaffe 1 oder 2
     *
     * @param pnw die Waffe
     * @return Kombi [0 bis 2]
     */
    int getKomboMitSchildParadewaffe(PluginNahkampfWaffe2 pnw);


    /**
     * Liefert die NahkampfWaffen
     *
     * @return PluginNahkampfWaffe2
     * @since 4.7.5
     */
    PluginNahkampfWaffe2[] getNahkampfWaffen();


    /**
     * Gibt die RaufenParade zur�ck
     *
     * @return Parade (Raufen)
     * @since 4.7.5
     */
    int getRaufenParade();


    /**
     * Gibt die RaufenTP zur�ck
     *
     * @return TP bei Raufen
     * @since 4.7.5
     */
    String getRaufenTP();


    /**
     * Gibt die RaufenAttacke zur�ck
     *
     * @return Attacke (Raufen)
     * @since 4.7.5
     */
    int getRauferAttacke();


    /**
     * Gibt die RingenAttacke zur�ck
     *
     * @return Attacke (Ringen)
     * @since 4.7.5
     */
    int getRingenAttacke();


    /**
     * Gibt die RingenParade zur�ck
     *
     * @return Parade (Ringen)
     * @since 4.7.5
     */
    int getRingenParade();


    /**
     * Gibt die RingenTP zur�ck
     *
     * @return TP bei Ringen
     * @since 4.7.5
     */
    String getRingenTP();


    /**
     * Liefert die R�stungsTeile
     *
     * @return PluginRuestungsTeile
     * @since 4.7.5
     */
    PluginRuestungsTeil[] getRuestungsTeile();


    /**
     * Liefert die Schilde
     *
     * @return PluginSchild
     * @since 4.7.5
     */
    PluginSchildParadewaffe[] getSchildParadewaffe();


    /**
     * Gibt an ob zur berechnung der gesammtruestung die
     * Zonenr�stungs-Berechnung benutzt wird
     *
     * @return Zonenr�stungs-Berechnung?
     * @sicne 4.7.5
     */
    boolean istZonenRuestungsBerechnung();

}
