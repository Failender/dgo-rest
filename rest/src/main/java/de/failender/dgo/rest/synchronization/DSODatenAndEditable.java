package de.failender.dgo.rest.synchronization;

import de.failender.heldensoftware.xml.datenxml.Daten;

public class DSODatenAndEditable {

    private Daten daten;
    private boolean editable;
    private boolean xmlEditable;
    private boolean ownHeld;

    public DSODatenAndEditable() {
    }

    public Daten getDaten() {
        return daten;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isXmlEditable() {
        return xmlEditable;
    }

    public boolean isOwnHeld() {
        return ownHeld;
    }

    public void setDaten(Daten daten) {
        this.daten = daten;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setXmlEditable(boolean xmlEditable) {
        this.xmlEditable = xmlEditable;
    }

    public void setOwnHeld(boolean ownHeld) {
        this.ownHeld = ownHeld;
    }
}
