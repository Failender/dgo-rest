package de.failender.fantasygrounds;

import de.failender.heldensoftware.xml.datenxml.Daten;

import java.io.InputStream;
import java.util.List;

public class FantasyGroundsConverterService {

    public static String convert(List<Daten> helden, InputStream xml) {

        XmlWorker.Oeffne_Held(xml);
        for (Daten daten : helden) {
            DGOPluginHeldenWerteWerkzeug werteWerkzeug = new DGOPluginHeldenWerteWerkzeug(daten);
            XmlWorker.Konvertiere_Held(werteWerkzeug);
        }

        String converted = XmlWorker.documentToString();
        return converted;


    }
}
