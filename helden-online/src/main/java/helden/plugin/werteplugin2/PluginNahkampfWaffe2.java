package helden.plugin.werteplugin2;

import helden.plugin.werteplugin.PluginNahkampfWaffe;
import helden.plugin.werteplugin.PluginTalent;

/**
 * Erweitert die Plugin-Nahkampfwaffe um weitere Informationen
 *
 * @author Maik Schiefer-Gehrke
 * @sicne 4.7.5
 */
public interface PluginNahkampfWaffe2 extends PluginNahkampfWaffe {

    /**
     * Gibt den AttackeWert nach allen Modifikationen
     *
     * @return AttackeWert
     * @since 4.7.5
     */
    int getAttacke();


    /**
     * Gibt das Talent mit dem die Waffe gef�hrt wird
     *
     * @return PluginTalent
     * @since 4.7.5
     */
    PluginTalent getBenutztesTalent();


    /**
     * Gibt den BF der Waffe zur�ck
     *
     * @return int [Min, Akt]
     * @since 4.7.5
     */
    int[] getBF();


    /**
     * Gibt den Finalen TP-Wert nach allen Modifikationen (TP/KK, RS)
     *
     * @return TP [Mul, W, SUM]
     * @since 4.7.5
     */
    int[] getEndTP();


    /**
     * Gibt den die Initive-Modifikation zur�c
     *
     * @return int INI-Mod
     * @since 4.7.5
     */
    int getINIMod();


    /**
     * Liefert den Namen der Waffe, kann von toString abweichen
     *
     * @return Name
     * @since 4.7.5
     */
    String getName();


    /**
     * Gibt den ParadeWert nach allen Modifikationen
     *
     * @return ParadeWert (-99 wenn keine Parade existiert)
     * @since 4.7.5
     */
    int getParade();


    /**
     * Gibt die nummer der Waffe in der Ausruestung an.
     *
     * @return Nummer des Slots [1 bis 5]
     * @since 4.7.5
     */
    int getWaffenNummer();

}
