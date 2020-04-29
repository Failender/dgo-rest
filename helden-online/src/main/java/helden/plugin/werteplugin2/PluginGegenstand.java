package helden.plugin.werteplugin2;

/**
 * Liefert die Basis-Infos eines Gegenstandes
 *
 * @author Maik Schiefer-Gehrke
 * @since 4.7.5
 */
public interface PluginGegenstand {

    /**
     * Anzahl der Gegenst�nde diese Types
     *
     * @return Die Anzahl
     */
    int getAnzahl();


    /**
     * Gibt das Gewicht des Gegenstandes
     *
     * @return Das Gewicht
     */
    float getGewicht();


    /**
     * Gibt den alternativen Name des Gegenatdes
     *
     * @return Name kann von toString() abweichen.
     */
    String getName();


    /**
     * Der Preis des Gegenstandes
     *
     * @return Preis
     */
    int getPreis();


    /**
     * Gibt den Name des Gegenatdes
     *
     * @return Name
     */
    @Override
    String toString();

}
