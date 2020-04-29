package helden.plugin.werteplugin2;

import helden.plugin.werteplugin.PluginHeld;

/**
 * PluginInterface zum Abfragen von Heldenwerte
 *
 * @author Maik Schiefer-Gehrke
 * @since 4.7.5
 */
public interface PluginHeld2 extends PluginHeld {

    /**
     * Liefert die HeldenID
     *
     * @return die Helden-ID
     */
    String getHeldenID();
}
