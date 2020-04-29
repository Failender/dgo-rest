package helden.plugin.werteplugin2;

import java.util.ArrayList;

/**
 * Liefert die Schnittstelle f�r einen Knotem im Gruppen Baum
 *
 * @author Maik Schiefer-Gehrke
 * @since 4.7.5
 */
public interface PluginTreeNode {

    /**
     * Gibt eine Array mit den Kindern. Kann such die L�nge 0 Haben
     *
     * @return ArrayList<PluginTreeNode>
     */
    ArrayList<PluginTreeNode> getChilds();


    /**
     * Die ID des Helden wenn es einer ist
     *
     * @return ID
     */
    String getID();


    /**
     * Liefert den Namen der Gruppe oder des Helden
     *
     * @return Name
     */
    String getName();


    /**
     * Gibt an ob es ein Blatt und damit ein Held ist
     *
     * @return Ist Blatt?
     */
    boolean isLeaf();
}
