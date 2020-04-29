/**
 * (C) Andreas Sch�nknecht 2006
 */
package helden.plugin.werteplugin2;

import helden.plugin.werteplugin.PluginSchild;

/**
 * Erm�glicht den erweiterten Zugriff auf die Werte eines Schild oder Pardewaffe
 *
 * @author Maik Schiefer-Gehrke
 * @since 4.7.5
 */

public interface PluginSchildParadewaffe extends PluginSchild {

    /**
     * Liefert die Benutzungsart zur�ck
     *
     * @return Schild / Paradewaffe
     * @since 4.7.5
     */
    String getBenutzungsart();


    /**
     * Gibt den Name des Schildes (Kann von toString abweichen)
     *
     * @return Name
     * @since 4.7.5
     */
    String getName();


    /**
     * Gibt den ParadeWert nach allen Modifikationen
     *
     * @return ParadeWert
     * @since 4.7.5
     */
    int getParade();


    /**
     * Gibt den Name des Schildes
     *
     * @return Name
     * @since 4.7.5
     */
    @Override
    String toString();
}
