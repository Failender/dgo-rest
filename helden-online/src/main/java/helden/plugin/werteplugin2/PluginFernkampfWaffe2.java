package helden.plugin.werteplugin2;

import helden.plugin.werteplugin.PluginFernkampfWaffe;

/**
 * Erweitert die PluginFernkampfWaffe um weiter Angaben
 *
 * @author Maik Schiefer-Gehrke
 * @since 4.7.5
 */
public interface PluginFernkampfWaffe2 extends PluginFernkampfWaffe {

    /**
     * Gibt den Fernkampfwert der Waffe zur�ck unter ber�cksichtigung der BE
     *
     * @return Fernkampfwert
     * @since 4.7.5
     */
    int getFernkammpfWert();


    /**
     * Gibt den Namen der Waffe, kann von ToString abweichen
     *
     * @return Name
     * @since 4.7.5
     */
    String getName();
}
