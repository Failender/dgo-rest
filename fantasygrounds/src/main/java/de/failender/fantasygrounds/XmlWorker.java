package de.failender.fantasygrounds;

import de.failender.fantasygrounds.plugin.PTalent;
import de.failender.fantasygrounds.plugin.PZauber;
import de.failender.heldensoftware.api.XmlUtil;
import helden.framework.geld.Muenze;
import helden.gui.allgemein.ExampleFileFilter;
import helden.plugin.werteplugin.*;
import helden.plugin.werteplugin2.PluginGegenstand;
import helden.plugin.werteplugin2.PluginHeldenWerteWerkzeug2;
import helden.plugin.werteplugin2.PluginNahkampfWaffe2;
import helden.plugin.werteplugin2.PluginSchildParadewaffe;
import org.w3c.dom.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

public class XmlWorker {

    public static final String[] CHARISMA = {"Charisma", "charisma", "CH"};
    public static final String[] FINGERFERTIGKEIT = {"Fingerfertigkeit", "fingerfertigkeit", "FF"};
    public static final String[] GEWANDHEIT = {"Gewandtheit", "gewandtheit", "GE"};
    public static final String[] INTUITION = {"Intuition", "intuition", "IN"};
    public static final String[] KLUGHEIT = {"Klugheit", "klugheit", "KL"};
    public static final String[] KOERPERKRAFT = {"K\u00f6rperkraft", "koerperkraft", "KK"};
    public static final String[] KONSTITUION = {"Konstitution", "konstitution", "KO"};
    public static final String[] MUT = {"Mut", "mut", "MU"};
    public static final String[] SOZIALSTATUS = {"Sozialstatus", "sozialstatus", "SO"};
    public static final String[] ASTRALENERGIE = {"Astralenergie", "astralenergie", "AE"};
    public static final String[] AUSDAUER = {"Ausdauer", "ausdauer", "AU"};
    public static final String[] KARMAENERGIE = {"Karmaenergie", "karmaenergie", "KE"};
    public static final String[] LEBENSENERGIE = {"Lebensenergie", "lebensenergie", "LE"};
    public static final String[] ATTACKE = {"Attacke", "attacke", "AT-B"};
    public static final String[] PARADE = {"Parade", "parade", "PA-B"};
    public static final String[] FERNKAMPF = {"Fernkampf-Basis", "fernkampf", "FK-B"};
    public static final String[] INITIATIVE = {"Initiative", "initiative", "INI"};
    public static final String[] MAGIERESISTENZ = {"Magieresistenz", "magieresistenz", "MR"};
    public static final String[][] eigenschaften = new String[][]{CHARISMA, FINGERFERTIGKEIT, GEWANDHEIT, INTUITION, KLUGHEIT,
            KOERPERKRAFT, KONSTITUION, MUT, SOZIALSTATUS, ASTRALENERGIE, AUSDAUER, KARMAENERGIE, LEBENSENERGIE, ATTACKE, PARADE,
            FERNKAMPF, INITIATIVE, MAGIERESISTENZ};
    public static boolean local_char;
    private static File FG2XMLDatei;
    private static JDialog dialog;
    private static JFileChooser chooser;
    private static Document heldXML;
    private static DocumentBuilder builder;
    private static String g_rep;
    private static int g_wert;

    static {

        XmlWorker.g_rep = "";
        XmlWorker.g_wert = 0;
        XmlWorker.local_char = false;
    }

    private static void ShowMessage(final String aMessage) {
        JOptionPane.showMessageDialog(null, aMessage, "", 1);
    }

    public static void Oeffne_Held(InputStream xml) {
        try {
            XmlWorker.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            XmlWorker.heldXML = XmlWorker.builder.parse(xml);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String documentToString() {
        return XmlUtil.toString(XmlWorker.heldXML);
    }

    private static Element addNode(final Document doc, final Node parent, final String typ) {
        return addNode(doc, parent, typ, null, null);
    }

    private static Element addNode(final Document doc, final Node parent, final String typ, final String attribName, final String attribValue) {
        final Element node = doc.createElement(typ);
        if (attribName != null) {
            node.setAttribute(attribName, attribValue);
        }
        parent.appendChild(node);
        return node;
    }

    private static String toNormString(final int value) {
        final String stringVal = Integer.toString(value);
        switch (stringVal.length()) {
            case 0: {
                return "00000";
            }
            case 1: {
                return "0000" + stringVal;
            }
            case 2: {
                return "000" + stringVal;
            }
            case 3: {
                return "00" + stringVal;
            }
            case 4: {
                return "0" + stringVal;
            }
            default: {
                return stringVal;
            }
        }
    }

    private static String get_BE(final String ebe) {
        if (ebe.equals("BEx2")) {
            return "*2";
        }
        if (ebe.equals("BE")) {
            return "0";
        }
        if (ebe.equals("BE-1")) {
            return "-1";
        }
        if (ebe.equals("BE-2")) {
            return "-2";
        }
        if (ebe.equals("BE-3")) {
            return "-3";
        }
        if (ebe.equals("BE-4")) {
            return "-4";
        }
        if (ebe.equals("BE-5")) {
            return "-5";
        }
        return "";
    }

    private static String toProbeString(final String attribut) {
        String result = "";
        if (attribut.toLowerCase().equals("ch")) {
            result = "attribute.charisma.aktuell";
        } else if (attribut.toLowerCase().equals("ff")) {
            result = "attribute.fingerfertigkeit.aktuell";
        } else if (attribut.toLowerCase().equals("ge")) {
            result = "attribute.gewandtheit.aktuell";
        } else if (attribut.toLowerCase().equals("in")) {
            result = "attribute.intuition.aktuell";
        } else if (attribut.toLowerCase().equals("kl")) {
            result = "attribute.klugheit.aktuell";
        } else if (attribut.toLowerCase().equals("kk")) {
            result = "attribute.koerperkraft.aktuell";
        } else if (attribut.toLowerCase().equals("ko")) {
            result = "attribute.konstitution.aktuell";
        } else if (attribut.toLowerCase().equals("mu")) {
            result = "attribute.mut.aktuell";
        } else if (!(attribut.toLowerCase().trim().equals("--") || attribut.toLowerCase().trim().equals("**"))) {
            throw new RuntimeException(attribut);
        }
        return result;
    }

    private static String getNoteStringFromBuffer(final String[] buffer, final String ueberschrift) {
        String result = "";
        boolean hatUeberschrift = false;
        for (int i = 0; i < buffer.length; ++i) {
            final String text = buffer[i];
            if (!text.equals("")) {
                if (!hatUeberschrift) {
                    result = result + ueberschrift + "\r\r";
                    hatUeberschrift = true;
                }
                result = result + text + "\r";
            }
        }
        return result;
    }

    private static String toNodeName(final String input) {
        final StringBuffer output = new StringBuffer(input.length());
        for (int i = 0; i < input.length(); ++i) {
            switch (input.charAt(i)) {
                case ' ':
                case '(':
                case ')':
                case '-':
                case '/':
                case ':': {
                    break;
                }
                case '\u00e4': {
                    output.append("ae");
                    break;
                }
                case '\u00c4': {
                    output.append("Ae");
                    break;
                }
                case '\u00f6': {
                    output.append("oe");
                    break;
                }
                case '\u00d6': {
                    output.append("Oe");
                    break;
                }
                case '\u00fc': {
                    output.append("ue");
                    break;
                }
                case '\u00dc': {
                    output.append("Ue");
                    break;
                }
                case '\u00df': {
                    output.append("ss");
                    break;
                }
                default: {
                    output.append(input.charAt(i));
                    break;
                }
            }
        }
        return output.toString();
    }

    private static Element add_Node_base() {
        Element rootElement = XmlWorker.heldXML.getDocumentElement();
        if (rootElement == null) {
            rootElement = XmlWorker.heldXML.createElement("root");
            rootElement.setAttribute("version", "2.6");
            rootElement.setAttribute("release", "7");
            XmlWorker.heldXML.appendChild(rootElement);
        }
        if (!XmlWorker.local_char) {
            final NodeList charsheetElementList = rootElement.getElementsByTagName("charsheet");
            Element charsheetElement = null;
            if (charsheetElementList.getLength() == 0) {
                charsheetElement = addNode(XmlWorker.heldXML, rootElement, "charsheet");
            } else {
                charsheetElement = (Element) charsheetElementList.item(0);
            }
            NodeList idElementList = null;
            Element idroot = null;
            int idIndex = 1;
            String tagName;
            while (true) {
                tagName = "id-" + toNormString(idIndex);
                idElementList = charsheetElement.getElementsByTagName(tagName);
                if (idElementList.getLength() == 0) {
                    break;
                }
                ++idIndex;
            }
            idroot = addNode(XmlWorker.heldXML, charsheetElement, tagName);
            idIndex = Math.max(idIndex + 1, charsheetElementList.getLength());
            charsheetElement.setAttribute("idcounter", new Integer(idIndex).toString());
            return idroot;
        }
        final Element charsheetElement2 = addNode(XmlWorker.heldXML, rootElement, "character", "portrait", "");
        return charsheetElement2;
    }

    private static Element addNumberNode(final Document doc, final Node parent, final String tag, final int value) {
        return addValue(doc, parent, tag, "type", "number", Integer.toString(value));
    }

    private static Element addFloatNode(final Document doc, final Node parent, final String tag, final float value) {
        return addValue(doc, parent, tag, "type", "number", Float.toString(value));
    }

    private static Element addStringNode(final Document doc, final Node parent, final String tag, final String value) {
        return addValue(doc, parent, tag, "type", "string", value);
    }

    private static Element addValue(final Document doc, final Node parent, final String typ, final String attribName, final String attribValue, final String value) {
        final Element node = doc.createElement(typ);
        if (attribName != null) {
            node.setAttribute(attribName, attribValue);
        }
        final Text text = doc.createTextNode(value);
        node.appendChild(text);
        parent.appendChild(node);
        return node;
    }

    private static String toSlashString(final String[] inString) {
        if (inString.length == 0) {
            return "";
        }
        final StringBuffer retVal = new StringBuffer(inString[0]);
        for (int i = 1; i < inString.length; ++i) {
            retVal.append("/").append(inString[i]);
        }
        return retVal.toString();
    }

    private static String toSlashString(final int[] inValues, final boolean vorzeichen) {
        if (inValues.length == 0) {
            return "";
        }
        final StringBuffer retVal = new StringBuffer("");
        if (vorzeichen && inValues[0] > 0) {
            retVal.append("+");
        }
        retVal.append(inValues[0]);
        for (int i = 1; i < inValues.length; ++i) {
            retVal.append("/");
            if (vorzeichen && inValues[i] > 0) {
                retVal.append("+");
            }
            retVal.append(inValues[i]);
        }
        return retVal.toString();
    }

    private static String toDices(final int[] tp) {
        if (tp.length == 0) {
            return "";
        }
        final StringBuffer retVal = new StringBuffer("");
        final int wuerfel = tp[1];
        for (int anzahl = tp[0], i = 0; i < anzahl; ++i) {
            retVal.append("d" + wuerfel + ",");
        }
        if (retVal.length() > 0) {
            retVal.deleteCharAt(retVal.length() - 1);
        }
        return retVal.toString();
    }

    private static void getRepundWert(final String teilstring, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        XmlWorker.g_rep = "";
        XmlWorker.g_wert = 0;
        final String[] talente = phww.getTalenteAlsString();
        int i = 0;
        while (i < talente.length) {
            final PluginTalent talent = phww.getTalent(talente[i]);
            final String name = talent.toString();
            if (name.indexOf(teilstring) != -1) {
                XmlWorker.g_wert = phww.getTalentwert(talent);
                final int klammervon = name.indexOf("(");
                final int klammerbis = name.indexOf(")");
                if (klammervon != -1 && klammerbis != -1) {
                    XmlWorker.g_rep = name.substring(klammervon + 1, klammerbis);
                    break;
                }
                break;
            } else {
                ++i;
            }
        }
    }

    private static void add_Node_name(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        addStringNode(XmlWorker.heldXML, node, "name", held.toString());
    }

    private static void add_Node_ap(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element apNode = addNode(XmlWorker.heldXML, node, "ap");
        addNumberNode(XmlWorker.heldXML, apNode, "frei", phww.getVerfuegbareAbenteuerpunkte());
        addNumberNode(XmlWorker.heldXML, apNode, "gesamt", held.getAbenteuerpunkte());
        addNumberNode(XmlWorker.heldXML, apNode, "verbraucht", phww.getEingestzteAbenteuerpunkte());
    }

    private static void add_Node_attribute(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element attibuteNode = addNode(XmlWorker.heldXML, node, "attribute");
        int index;
        Element eigenschaftNode;
        for (index = 0, index = 0; index < 8; ++index) {
            eigenschaftNode = addNode(XmlWorker.heldXML, attibuteNode, XmlWorker.eigenschaften[index][1]);
            addNumberNode(XmlWorker.heldXML, eigenschaftNode, "aktuell", phww.getEigenschaftswert(XmlWorker.eigenschaften[index][0]));
            addNumberNode(XmlWorker.heldXML, eigenschaftNode, "basis", phww.getEigenschaftswert(XmlWorker.eigenschaften[index][0]));
        }
        final Element wundschwellNode = addNode(XmlWorker.heldXML, attibuteNode, "wundschwelle");
        addNumberNode(XmlWorker.heldXML, wundschwellNode, "wert", phww.getEigenschaftswert("Konstitution") / 2);
        addNumberNode(XmlWorker.heldXML, attibuteNode, "sozialstatus", phww.getEigenschaftswert("Sozialstatus"));
        addNumberNode(XmlWorker.heldXML, attibuteNode, "geschwindigkeit", phww.getGeschwindigkeit());
    }

    private static void add_Node_aep(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final int max = phww.getEigenschaftswert("Astralenergie");
        final Element astralnode = addNode(XmlWorker.heldXML, node, "aep");
        final int e1 = phww.getEigenschaftswert("Mut");
        final int e2 = phww.getEigenschaftswert("Intuition");
        final int e3 = phww.getEigenschaftswert("Charisma");
        final int base = Math.round((e1 + e2 + e3) / 2.0f);
        addNumberNode(XmlWorker.heldXML, astralnode, "akt", max);
        addNumberNode(XmlWorker.heldXML, astralnode, "base", base);
        addNumberNode(XmlWorker.heldXML, astralnode, "max", max);
        addNumberNode(XmlWorker.heldXML, astralnode, "mod1", 0);
        addNumberNode(XmlWorker.heldXML, astralnode, "mod2", max - base);
    }

    private static void add_Node_lep(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final int max = phww.getEigenschaftswert("Lebensenergie");
        final Element lebensnode = addNode(XmlWorker.heldXML, node, "lep");
        final int e1 = phww.getEigenschaftswert("Konstitution");
        final int e2 = phww.getEigenschaftswert("Konstitution");
        final int e3 = phww.getEigenschaftswert("K\u00f6rperkraft");
        final int base = Math.round((e1 + e2 + e3) / 2.0f);
        addNumberNode(XmlWorker.heldXML, lebensnode, "akt", max);
        addNumberNode(XmlWorker.heldXML, lebensnode, "base", base);
        addNumberNode(XmlWorker.heldXML, lebensnode, "max", max);
        addNumberNode(XmlWorker.heldXML, lebensnode, "mod1", 0);
        addNumberNode(XmlWorker.heldXML, lebensnode, "mod2", max - base);
    }

    private static void add_Node_aup(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final int max = phww.getEigenschaftswert("Ausdauer");
        final Element ausdauernode = addNode(XmlWorker.heldXML, node, "aup");
        final int e1 = phww.getEigenschaftswert("Mut");
        final int e2 = phww.getEigenschaftswert("Konstitution");
        final int e3 = phww.getEigenschaftswert("Gewandtheit");
        final int base = Math.round((e1 + e2 + e3) / 2.0f);
        addNumberNode(XmlWorker.heldXML, ausdauernode, "akt", max);
        addNumberNode(XmlWorker.heldXML, ausdauernode, "base", base);
        addNumberNode(XmlWorker.heldXML, ausdauernode, "max", max);
        addNumberNode(XmlWorker.heldXML, ausdauernode, "mod1", 0);
        addNumberNode(XmlWorker.heldXML, ausdauernode, "mod2", max - base);
    }

    private static void add_Node_kap(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final int max = phww.getEigenschaftswert("Karmaenergie");
        final Element karmanode = addNode(XmlWorker.heldXML, node, "kap");
        addNumberNode(XmlWorker.heldXML, karmanode, "akt", max);
        addNumberNode(XmlWorker.heldXML, karmanode, "max", max);
        addNumberNode(XmlWorker.heldXML, karmanode, "mod1", 0);
        addNumberNode(XmlWorker.heldXML, karmanode, "mod2", max);
    }

    private static void add_Node_at(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element baseNode = addNode(XmlWorker.heldXML, node, "at");
        addNumberNode(XmlWorker.heldXML, baseNode, "base", phww.getEigenschaftswert("Attacke"));
    }

    private static void add_Node_pa(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element baseNode = addNode(XmlWorker.heldXML, node, "pa");
        addNumberNode(XmlWorker.heldXML, baseNode, "base", phww.getEigenschaftswert("Parade"));
    }

    private static void add_Node_fk(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element baseNode = addNode(XmlWorker.heldXML, node, "fk");
        addNumberNode(XmlWorker.heldXML, baseNode, "base", phww.getEigenschaftswert("Fernkampf-Basis"));
    }

    private static void add_Node_ausweichen(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element ausweichenNode = addNode(XmlWorker.heldXML, node, "ausweichen");
        final int ausweichen = phww.getAusruestung2().getAusweichen();
        final int ausweichenMod = ausweichen - phww.getEigenschaftswert("Parade") + phww.getAusruestung2().getGesammtRuestung().getGesammtBehinderung();
        addNumberNode(XmlWorker.heldXML, ausweichenNode, "mod", ausweichenMod);
    }

    private static void add_Node_aw(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        addNumberNode(XmlWorker.heldXML, node, "aw", phww.getAusruestung2().getAusweichen());
    }

    private static void add_Node_vorteile(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element vorteilNode = addNode(XmlWorker.heldXML, node, "vorteile");
        final String[] vorteile = phww.getVorteileAlsString();
        for (int i = 0; i < vorteile.length; ++i) {
            final PluginVorteil vorteil = phww.getVorteil(vorteile[i]);
            Element baseNode = null;
            if (!vorteil.isNachteil()) {
                baseNode = vorteilNode;
            }
            if (baseNode != null) {
                final Element idnode = addNode(XmlWorker.heldXML, baseNode, "id-" + toNormString(i));
                if (vorteil.isAuswahlVorteil() || vorteil.isMehfachAuswahlVorteil()) {
                    addStringNode(XmlWorker.heldXML, idnode, "label", vorteile[i].toString());
                } else {
                    addStringNode(XmlWorker.heldXML, idnode, "label", vorteil.getName());
                }
                if (vorteil.isWertVorteil()) {
                    addNumberNode(XmlWorker.heldXML, idnode, "stufe", vorteil.getWert());
                }
                if (phww.getKommentar(vorteil) != null) {
                    addStringNode(XmlWorker.heldXML, idnode, "bemerkung", phww.getKommentar(vorteil));
                }
            }
        }
    }

    private static void add_Node_nachteile(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element nachteilNode = addNode(XmlWorker.heldXML, node, "nachteile");
        final String[] nachteile = phww.getVorteileAlsString();
        final int index = 0;
        for (int i = 0; i < nachteile.length; ++i) {
            final PluginVorteil nachteil = phww.getVorteil(nachteile[i]);
            Element baseNode = null;
            if (nachteil.isNachteil()) {
                baseNode = nachteilNode;
            }
            if (baseNode != null) {
                final Element idnode = addNode(XmlWorker.heldXML, baseNode, "id-" + toNormString(i));
                if (nachteil.isAuswahlVorteil() || nachteil.isMehfachAuswahlVorteil()) {
                    addStringNode(XmlWorker.heldXML, idnode, "label", nachteile[i]);
                } else {
                    addStringNode(XmlWorker.heldXML, idnode, "label", nachteil.getName());
                }
                if (nachteil.isWertVorteil()) {
                    addNumberNode(XmlWorker.heldXML, idnode, "stufe", nachteil.getWert());
                }
                if (phww.getKommentar(nachteil) != null) {
                    addStringNode(XmlWorker.heldXML, idnode, "bemerkung", phww.getKommentar(nachteil));
                }
            }
        }
        nachteilNode.setAttribute("idcounter", String.valueOf(index));
    }

    private static void add_Node_sf(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element sfNode = addNode(XmlWorker.heldXML, node, "sf");
        final Element litNode = addNode(XmlWorker.heldXML, node, "litlist");
        final Element ritualNode = addNode(XmlWorker.heldXML, node, "rklist");
        int litindex = 0;
        int rkindex = 0;
        int liturgiewert = 0;
        String liturgierep = "";
        int rkwert = 0;
        String rkrep = "";
        getRepundWert("Liturgiekenntnis", held, phww);
        liturgiewert = XmlWorker.g_wert;
        liturgierep = XmlWorker.g_rep;
        getRepundWert("Ritualkenntnis: Hexe", held, phww);
        rkwert = XmlWorker.g_wert;
        rkrep = "Hexe";
        final StringBuffer kampfsonderfertigkeiten = new StringBuffer();
        final String[] sonderfertigkeiten = phww.getSonderfertigkeitenAlsString();
        for (int i = 0; i < sonderfertigkeiten.length; ++i) {
            final PluginSonderfertigkeit sonderfertigkeit = phww.getSonderfertigkeit(sonderfertigkeiten[i]);
            final Element tnode = addNode(XmlWorker.heldXML, sfNode, "id-" + toNormString(i));
            addStringNode(XmlWorker.heldXML, tnode, "label", sonderfertigkeit.toString());
            if (phww.getKommentar(sonderfertigkeit) != null) {
                addStringNode(XmlWorker.heldXML, tnode, "bemerkung", phww.getKommentar(sonderfertigkeit));
            }
            if (sonderfertigkeit.istKampfSonderfertigkeit() || sonderfertigkeit.istFernkampfsonderfertigkeit() || sonderfertigkeit.istNahkampfsonderfertigkeit()) {
                kampfsonderfertigkeiten.append(sonderfertigkeit.toString() + ",");
            } else if (sonderfertigkeit.istKlerikal() || sonderfertigkeit.istLiturgie()) {
                ++litindex;
                final Element idnode = addNode(XmlWorker.heldXML, litNode, "id-" + toNormString(litindex));
                addStringNode(XmlWorker.heldXML, idnode, "label", sonderfertigkeit.toString());
                addStringNode(XmlWorker.heldXML, idnode, "rep", liturgierep);
                addNumberNode(XmlWorker.heldXML, idnode, "wert", liturgiewert);
                addStringNode(XmlWorker.heldXML, idnode, "e1", "attribute.mut.aktuell");
                addStringNode(XmlWorker.heldXML, idnode, "e2", "attribute.intuition.aktuell");
                addStringNode(XmlWorker.heldXML, idnode, "e3", "attribute.charisma.aktuell");
            } else if (sonderfertigkeit.istHexenfluch()) {
                ++rkindex;
                final Element idnode = addNode(XmlWorker.heldXML, ritualNode, "id-" + toNormString(rkindex));
                addStringNode(XmlWorker.heldXML, idnode, "label", sonderfertigkeit.toString());
                addStringNode(XmlWorker.heldXML, idnode, "rep", rkrep);
                addNumberNode(XmlWorker.heldXML, idnode, "wert", rkwert);
            } else if (sonderfertigkeit.istElfenlied()) {
                ++rkindex;
                final Element idnode = addNode(XmlWorker.heldXML, ritualNode, "id-" + toNormString(rkindex));
                addStringNode(XmlWorker.heldXML, idnode, "label", sonderfertigkeit.toString());
                addStringNode(XmlWorker.heldXML, idnode, "rep", "Elfen");
                addNumberNode(XmlWorker.heldXML, idnode, "wert", 0);
            } else if (sonderfertigkeit.istSchamanenRitualkenntnis()) {
                ++rkindex;
                final Element idnode = addNode(XmlWorker.heldXML, ritualNode, "id-" + toNormString(rkindex));
                addStringNode(XmlWorker.heldXML, idnode, "label", sonderfertigkeit.toString());
                addStringNode(XmlWorker.heldXML, idnode, "rep", "Schamane");
                addNumberNode(XmlWorker.heldXML, idnode, "wert", 0);
            }
        }
        sfNode.setAttribute("idcounter", String.valueOf(sonderfertigkeiten.length));
        String tSonderfertigkeiten = kampfsonderfertigkeiten.toString();
        if (tSonderfertigkeiten.length() > 0) {
            tSonderfertigkeiten = tSonderfertigkeiten.substring(0, tSonderfertigkeiten.length() - 1);
        }
        addStringNode(XmlWorker.heldXML, node, "soderfertigkeiten", tSonderfertigkeiten);
    }

    private static void add_Node_skillist(final Element node, final PluginHeld held, final DGOPluginHeldenWerteWerkzeug phww) {
        final Element talentNode = addNode(XmlWorker.heldXML, node, "skilllist");
        final Element ritualkenntnisnode = addNode(XmlWorker.heldXML, node, "ritklist");
        final Element gesellschaftNode = addNode(XmlWorker.heldXML, talentNode, "gesellschaft");
        final Element handwerkNode = addNode(XmlWorker.heldXML, talentNode, "handwerk");
        final Element kampfNode = addNode(XmlWorker.heldXML, talentNode, "kampf");
        final Element fernkampfNode = addNode(XmlWorker.heldXML, talentNode, "fernkampf");
        final Element koerperNode = addNode(XmlWorker.heldXML, talentNode, "koerper");
        final Element naturNode = addNode(XmlWorker.heldXML, talentNode, "natur");
        final Element schriftenNode = addNode(XmlWorker.heldXML, talentNode, "lesen");
        final Element sonstigesNode = addNode(XmlWorker.heldXML, talentNode, "sonstige");
        final Element sprachenNode = addNode(XmlWorker.heldXML, talentNode, "sprechen");
        final Element wissenNode = addNode(XmlWorker.heldXML, talentNode, "wissen");
        int index_gesellschaft = 0;
        int index_handwerk = 0;
        int index_kampf = 0;
        int index_fernkampf = 0;
        int index_koerper = 0;
        int index_natur = 0;
        int index_lesen = 0;
        int index_sonstige = 0;
        int index_sprechen = 0;
        int index_wissen = 0;
        int index_ritualkenntnis = 0;
        final String[] talente = phww.getTalenteAlsString();
        for (int i = 0; i < talente.length; ++i) {
            Element rkbaseNode = null;
            Element idNode = null;
            final PTalent talent = phww.getTalent(talente[i]);
            String talentName = talent.toString();
            if (talentName.equals("Sinnensch\u00e4rfe")) {
                talentName = "Sinnessch\u00e4rfe";
            } else if (talentName.equals("Sich verstecken")) {
                talentName = "Sich Verstecken";
            } else if (talentName.equals("G\u00f6tter und Kulte")) {
                talentName = "G\u00f6tter/Kulte";
            } else if (talentName.equals("Sagen und Legenden")) {
                talentName = "Sagen/Legenden";
            }
            final String[] probe = talent.getProbe();
            if (talent.getTalentart().equals("Gesellschaft")) {
                ++index_gesellschaft;
                idNode = addNode(XmlWorker.heldXML, gesellschaftNode, "id-" + toNormString(index_gesellschaft));
                addStringNode(XmlWorker.heldXML, idNode, "label", talentName);
                addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                if (probe != null && probe.length == 3) {
                    addStringNode(XmlWorker.heldXML, idNode, "e1", toProbeString(probe[0]));
                    addStringNode(XmlWorker.heldXML, idNode, "e2", toProbeString(probe[1]));
                    addStringNode(XmlWorker.heldXML, idNode, "e3", toProbeString(probe[2]));
                }
            } else if (talent.getTalentart().equals("Handwerk")) {
                ++index_handwerk;
                idNode = addNode(XmlWorker.heldXML, handwerkNode, "id-" + toNormString(index_handwerk));
                addStringNode(XmlWorker.heldXML, idNode, "label", talentName);
                addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                if (probe != null && probe.length == 3) {
                    addStringNode(XmlWorker.heldXML, idNode, "e1", toProbeString(probe[0]));
                    addStringNode(XmlWorker.heldXML, idNode, "e2", toProbeString(probe[1]));
                    addStringNode(XmlWorker.heldXML, idNode, "e3", toProbeString(probe[2]));
                }
            } else if (talent.getTalentart().equals("Kampf")) {
                final int attacke = phww.getAttacke(talent);
                final int parade = phww.getParade(talent);
                if (parade != 0) {
                    ++index_kampf;
                    idNode = addNode(XmlWorker.heldXML, kampfNode, "id-" + toNormString(index_kampf));
                    addStringNode(XmlWorker.heldXML, idNode, "label", talentName);
                    addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                    addStringNode(XmlWorker.heldXML, idNode, "ebe", get_BE(talent.getBehinderung()));
                    addNumberNode(XmlWorker.heldXML, idNode, "at", attacke);
                    addNumberNode(XmlWorker.heldXML, idNode, "pa", parade);
                } else {
                    ++index_fernkampf;
                    idNode = addNode(XmlWorker.heldXML, fernkampfNode, "id-" + toNormString(index_fernkampf));
                    addStringNode(XmlWorker.heldXML, idNode, "label", talentName);
                    addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                    addStringNode(XmlWorker.heldXML, idNode, "ebe", get_BE(talent.getBehinderung()));
                    addNumberNode(XmlWorker.heldXML, idNode, "at", attacke);
                }
            } else if (talent.getTalentart().equals("K\u00f6rperlich")) {
                ++index_koerper;
                idNode = addNode(XmlWorker.heldXML, koerperNode, "id-" + toNormString(index_koerper));
                addStringNode(XmlWorker.heldXML, idNode, "label", talentName);
                addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                if (probe != null && probe.length == 3) {
                    addStringNode(XmlWorker.heldXML, idNode, "e1", toProbeString(probe[0]));
                    addStringNode(XmlWorker.heldXML, idNode, "e2", toProbeString(probe[1]));
                    addStringNode(XmlWorker.heldXML, idNode, "e3", toProbeString(probe[2]));
                }
                addStringNode(XmlWorker.heldXML, idNode, "ebe", get_BE(talent.getBehinderung()));
            } else if (talent.getTalentart().equals("Natur")) {
                ++index_natur;
                idNode = addNode(XmlWorker.heldXML, naturNode, "id-" + toNormString(index_natur));
                addStringNode(XmlWorker.heldXML, idNode, "label", talentName);
                addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                if (probe != null && probe.length == 3) {
                    addStringNode(XmlWorker.heldXML, idNode, "e1", toProbeString(probe[0]));
                    addStringNode(XmlWorker.heldXML, idNode, "e2", toProbeString(probe[1]));
                    addStringNode(XmlWorker.heldXML, idNode, "e3", toProbeString(probe[2]));
                }
            } else if (talent.getTalentart().equals("Schriften")) {
                ++index_lesen;
                idNode = addNode(XmlWorker.heldXML, schriftenNode, "id-" + toNormString(index_lesen));
                addStringNode(XmlWorker.heldXML, idNode, "label", talentName.replace("L/S ", ""));
                addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                addNumberNode(XmlWorker.heldXML, idNode, "komp", Integer.parseInt(phww.getSprachKomplexitaet(talent)));
                if (probe != null && probe.length == 3) {
                    addStringNode(XmlWorker.heldXML, idNode, "e1", toProbeString(probe[0]));
                    addStringNode(XmlWorker.heldXML, idNode, "e2", toProbeString(probe[1]));
                    addStringNode(XmlWorker.heldXML, idNode, "e3", toProbeString(probe[2]));
                }
            } else if (talent.getTalentart().equals("Sprachen")) {
                ++index_sprechen;
                idNode = addNode(XmlWorker.heldXML, sprachenNode, "id-" + toNormString(index_sprechen));
                addStringNode(XmlWorker.heldXML, idNode, "label", talentName);
                addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                addNumberNode(XmlWorker.heldXML, idNode, "komp", Integer.parseInt(phww.getSprachKomplexitaet(talent)));
                if (probe != null && probe.length == 3) {
                    addStringNode(XmlWorker.heldXML, idNode, "e1", toProbeString(probe[0]));
                    addStringNode(XmlWorker.heldXML, idNode, "e2", toProbeString(probe[1]));
                    addStringNode(XmlWorker.heldXML, idNode, "e3", toProbeString(probe[2]));
                }
            } else if (talent.getTalentart().equals("Wissen")) {
                ++index_wissen;
                idNode = addNode(XmlWorker.heldXML, wissenNode, "id-" + toNormString(index_wissen));
                addStringNode(XmlWorker.heldXML, idNode, "label", talentName);
                addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                if (probe != null && probe.length == 3) {
                    addStringNode(XmlWorker.heldXML, idNode, "e1", toProbeString(probe[0]));
                    addStringNode(XmlWorker.heldXML, idNode, "e2", toProbeString(probe[1]));
                    addStringNode(XmlWorker.heldXML, idNode, "e3", toProbeString(probe[2]));
                }
            } else if (talent.getTalentart().equals("Ritualkenntnis")) {
                ++index_sonstige;
                ++index_ritualkenntnis;
                idNode = addNode(XmlWorker.heldXML, sonstigesNode, "id-" + toNormString(index_sonstige));
                rkbaseNode = addNode(XmlWorker.heldXML, ritualkenntnisnode, "id-" + toNormString(index_ritualkenntnis));
                String fullName = "Ritualkenntnis: " + talentName;
                addStringNode(XmlWorker.heldXML, idNode, "label", fullName);
                addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                if (probe != null && probe.length == 3) {
                    addStringNode(XmlWorker.heldXML, idNode, "e1", toProbeString(probe[0]));
                    addStringNode(XmlWorker.heldXML, idNode, "e2", toProbeString(probe[1]));
                    addStringNode(XmlWorker.heldXML, idNode, "e3", toProbeString(probe[2]));
                }
                addStringNode(XmlWorker.heldXML, rkbaseNode, "label", fullName);
                addStringNode(XmlWorker.heldXML, rkbaseNode, "rep", "");
                addNumberNode(XmlWorker.heldXML, rkbaseNode, "werte3", phww.getTalentwert(talent));
            } else {
                ++index_sonstige;
                idNode = addNode(XmlWorker.heldXML, sonstigesNode, "id-" + toNormString(index_sonstige));
                addStringNode(XmlWorker.heldXML, idNode, "label", talentName);
                addNumberNode(XmlWorker.heldXML, idNode, "wert", phww.getTalentwert(talent));
                if (probe != null && probe.length == 3) {
                    addStringNode(XmlWorker.heldXML, idNode, "e1", toProbeString(probe[0]));
                    addStringNode(XmlWorker.heldXML, idNode, "e2", toProbeString(probe[1]));
                    addStringNode(XmlWorker.heldXML, idNode, "e3", toProbeString(probe[2]));
                }
            }
        }
    }

    private static void add_Node_spellist(final Element node, final PluginHeld held, final DGOPluginHeldenWerteWerkzeug phww) {
        final Element zauberNode = addNode(XmlWorker.heldXML, node, "spelllist");
        int i = 0;
        for (PZauber spruch : phww.getZauber()) {
            final Element idnode = addNode(XmlWorker.heldXML, zauberNode, "id-" + toNormString(i + 1));

            addStringNode(XmlWorker.heldXML, idnode, "label", spruch.getBezeichnung());
            addStringNode(XmlWorker.heldXML, idnode, "rep", spruch.getRepraesentationen()[0]);

            addNumberNode(XmlWorker.heldXML, idnode, "wert", spruch.getWert());
            final String[] probe = spruch.getProbe();
            if (probe != null && probe.length == 3) {
                addStringNode(XmlWorker.heldXML, idnode, "e1", toProbeString(probe[0]));
                addStringNode(XmlWorker.heldXML, idnode, "e2", toProbeString(probe[1]));
                addStringNode(XmlWorker.heldXML, idnode, "e3", toProbeString(probe[2]));
            }
            i++;
        }
    }

    private static void add_Node_details(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        addStringNode(XmlWorker.heldXML, node, "gesch", held.getGeschlechtString());
        addStringNode(XmlWorker.heldXML, node, "kultur", held.getKulturString());
        addStringNode(XmlWorker.heldXML, node, "klasse", held.getProfessionString());
        addStringNode(XmlWorker.heldXML, node, "rasse", held.getRasseString());
        final HeldAngaben angaben = held.getAngaben();
        final boolean fettleibig = false;
        addStringNode(XmlWorker.heldXML, node, "gew", Integer.toString(angaben.getGewicht(fettleibig)) + " Stein");
        addStringNode(XmlWorker.heldXML, node, "faugen", angaben.getAugenFarbe());
        addStringNode(XmlWorker.heldXML, node, "hoehe", Integer.toString(angaben.getGroesse()) + " Halbfinger");
        addStringNode(XmlWorker.heldXML, node, "fhaare", angaben.getHaarFarbe());
        addStringNode(XmlWorker.heldXML, node, "geburtsdatum", angaben.getGeburtstagString());
        addStringNode(XmlWorker.heldXML, node, "stand", angaben.getStand());
        addStringNode(XmlWorker.heldXML, node, "titel", angaben.getTitel());
    }

    private static void add_Node_inventorylist(final Element node, final PluginHeld held, final DGOPluginHeldenWerteWerkzeug phww) {
        final Element ausruestungNode = addNode(XmlWorker.heldXML, node, "inventorylist");
        int index = 0;
        for (PluginGegenstand pluginGegenstand : phww.getInventar()) {
            final Element idNode = addNode(XmlWorker.heldXML, ausruestungNode, "id-" + toNormString(index + 1));
            addStringNode(XmlWorker.heldXML, idNode, "name", pluginGegenstand.getName());
            addNumberNode(XmlWorker.heldXML, idNode, "carried", 0);
            addNumberNode(XmlWorker.heldXML, idNode, "weight", Math.round(pluginGegenstand.getGewicht()));
            addNumberNode(XmlWorker.heldXML, idNode, "count", pluginGegenstand.getAnzahl());
            addStringNode(XmlWorker.heldXML, idNode, "location", "");
            ++index;
        }
    }

    private static void add_Node_money(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final StringBuffer otherCoins = new StringBuffer();
        final Element coinNode = addNode(XmlWorker.heldXML, node, "coins");
        final Iterator<Muenze> iter = (Iterator<Muenze>) phww.getGeldBoerse().getMuenzeIter();
        while (iter.hasNext()) {
            final Muenze muenze = iter.next();
            final int anzahl = phww.getGeldBoerse().getMuenzAnzahl(muenze);
            if (anzahl == 0) {
                continue;
            }
            if (muenze.getBezeichner().equals("Dukat")) {
                final Element aCoinNode = addNode(XmlWorker.heldXML, coinNode, "slot1");
                addNumberNode(XmlWorker.heldXML, aCoinNode, "amount", anzahl);
            } else if (muenze.getBezeichner().equals("Silbertaler")) {
                final Element aCoinNode = addNode(XmlWorker.heldXML, coinNode, "slot2");
                addNumberNode(XmlWorker.heldXML, aCoinNode, "amount", anzahl);
            } else if (muenze.getBezeichner().equals("Heller")) {
                final Element aCoinNode = addNode(XmlWorker.heldXML, coinNode, "slot3");
                addNumberNode(XmlWorker.heldXML, aCoinNode, "amount", anzahl);
            } else if (muenze.getBezeichner().equals("Kreuzer")) {
                final Element aCoinNode = addNode(XmlWorker.heldXML, coinNode, "slot4");
                addNumberNode(XmlWorker.heldXML, aCoinNode, "amount", anzahl);
            } else {
                otherCoins.append(anzahl + " " + muenze.getBezeichner() + "\r");
            }
        }
        addStringNode(XmlWorker.heldXML, node, "coinother", otherCoins.toString());
    }

    private static void add_Node_notes(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final StringBuffer notes = new StringBuffer();
        final HeldAngaben angaben = held.getAngaben();
        String text = "";
        text = getNoteStringFromBuffer(angaben.getAussehenText(), "Aussehen");
        if (!text.equals("")) {
            notes.append(text + "\r");
        }
        text = getNoteStringFromBuffer(angaben.getFamilieText(), "Familie");
        if (!text.equals("")) {
            notes.append(text + "\r");
        }
        text = getNoteStringFromBuffer(angaben.getNotiz(), "Notizen");
        if (!text.equals("")) {
            notes.append(text + "\r");
        }
    }

    private static void add_Node_be(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element beNode = addNode(XmlWorker.heldXML, node, "BE");
        final int bewert = 0;
        addNumberNode(XmlWorker.heldXML, beNode, "end", bewert);
    }

    private static void add_Node_mr(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element mrNode = addNode(XmlWorker.heldXML, node, "mr");
        final int base = Math.round((phww.getEigenschaftswert("Mut") + phww.getEigenschaftswert("Klugheit") + phww.getEigenschaftswert("Konstitution")) / 5.0f);
        final int max = phww.getEigenschaftswert("Magieresistenz");
        final int mod = max - base;
        addNumberNode(XmlWorker.heldXML, mrNode, "base", base);
        addNumberNode(XmlWorker.heldXML, mrNode, "max", max);
        addNumberNode(XmlWorker.heldXML, mrNode, "mod", mod);
    }

    private static void add_Node_ini(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element iniNode = addNode(XmlWorker.heldXML, node, "ini");
        final int base = Math.round((phww.getEigenschaftswert("Mut") + phww.getEigenschaftswert("Mut") + phww.getEigenschaftswert("Intuition") + phww.getEigenschaftswert("Gewandtheit")) / 5.0f);
        final int max = phww.getEigenschaftswert("Initiative");
        final int mod = max - base;
        addNumberNode(XmlWorker.heldXML, iniNode, "base", base);
        addNumberNode(XmlWorker.heldXML, iniNode, "max", max);
        addNumberNode(XmlWorker.heldXML, iniNode, "mod", mod);
    }

    private static void add_Node_ruestungsteile(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final Element RuestungNode = addNode(XmlWorker.heldXML, node, "weaponlist");
        int ruestindex = 0;
        final PluginRuestungsTeil[] ruestungen = phww.getAusruestung2().getRuestungsTeile();
        for (int i = 0; i < ruestungen.length; ++i) {
            final PluginRuestungsTeil ruestung = ruestungen[i];
            ++ruestindex;
            final Element idNode = addNode(XmlWorker.heldXML, RuestungNode, "id-" + toNormString(ruestindex));
            addNumberNode(XmlWorker.heldXML, idNode, "RSBauch", ruestung.getBauchSchutz());
            addNumberNode(XmlWorker.heldXML, idNode, "RSBrust", ruestung.getBrustSchutz());
            addNumberNode(XmlWorker.heldXML, idNode, "RSKopf", ruestung.getKopfSchutz());
            addNumberNode(XmlWorker.heldXML, idNode, "RSLA", ruestung.getLinkerArmSchutz());
            addNumberNode(XmlWorker.heldXML, idNode, "RSLB", ruestung.getLinkesBeinSchutz());
            addNumberNode(XmlWorker.heldXML, idNode, "RSRA", ruestung.getRechterArmSchutz());
            addNumberNode(XmlWorker.heldXML, idNode, "RSRB", ruestung.getRechtesBeinSchutz());
            addNumberNode(XmlWorker.heldXML, idNode, "RSRuecken", ruestung.getRueckenSchutz());
            addNumberNode(XmlWorker.heldXML, idNode, "RSteilgesamt", ruestung.getGesammtBehinderung());
            addNumberNode(XmlWorker.heldXML, idNode, "BEteilgesamt", ruestung.getGesammtZonenSchutz());
            addNumberNode(XmlWorker.heldXML, idNode, "carried", 0);
            addStringNode(XmlWorker.heldXML, idNode, "name", ruestung.toString());
        }
    }

    private static void add_Node_schildliste(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final PluginSchildParadewaffe[] schilde = phww.getAusruestung2().getSchildParadewaffe();
        final Element schildeNode = addNode(XmlWorker.heldXML, node, "schildliste", "idcounter", Integer.toString(schilde.length));
        for (int i = 0; i < schilde.length; ++i) {
            final Element idnode = addNode(XmlWorker.heldXML, schildeNode, "id-" + toNormString(i));
            addNumberNode(XmlWorker.heldXML, idnode, "bruchfaktor", schilde[i].getBruchfaktor());
            addNumberNode(XmlWorker.heldXML, idnode, "ini", schilde[i].getInitiativeModifikator());
            addStringNode(XmlWorker.heldXML, idnode, "label", schilde[i].toString());
            addNumberNode(XmlWorker.heldXML, idnode, "parade", schilde[i].getParade());
            addNumberNode(XmlWorker.heldXML, idnode, "showonminisheet", 1);
            addStringNode(XmlWorker.heldXML, idnode, "type", schilde[i].getBenutzungsart());
            addNumberNode(XmlWorker.heldXML, idnode, "wm1", schilde[i].getWaffenModifikatorAT());
            addNumberNode(XmlWorker.heldXML, idnode, "wm2", schilde[i].getWaffenModifikatorPA());
        }
    }

    private static void add_Node_fernkampfwaffenliste(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final PluginFernkampfWaffe[] fernwaffen = phww.getAusruestung().getFernkampfWaffen();
        final Element fernwaffennode = addNode(XmlWorker.heldXML, node, "fernkampfwaffenliste");
        final String[] talente = phww.getTalenteAlsString();
        for (int i = 0; i < fernwaffen.length; ++i) {
            final Element idnode = addNode(XmlWorker.heldXML, fernwaffennode, "id-" + toNormString(i));
            addNumberNode(XmlWorker.heldXML, idnode, "bruchfaktor", 0);
            final Element diceNode = addStringNode(XmlWorker.heldXML, idnode, "damagedice", toDices(fernwaffen[i].getTrefferpunkte()));
            diceNode.setAttribute("type", "dice");
            addNumberNode(XmlWorker.heldXML, idnode, "damagebonus", fernwaffen[i].getTrefferpunkte()[2]);
            final Element entfernungNode = addNode(XmlWorker.heldXML, idnode, "entfernungen");
            addStringNode(XmlWorker.heldXML, entfernungNode, "werte", toSlashString(fernwaffen[i].getReichweite(), false));
            addStringNode(XmlWorker.heldXML, entfernungNode, "tp", toSlashString(fernwaffen[i].getTrefferpunkteModifikation(), true));
            final PluginTalent aTalent = fernwaffen[i].getTalent();

            if (aTalent != null) {
                addNumberNode(XmlWorker.heldXML, idnode, "fernkampf", phww.getAttacke(aTalent));
            } else {
                final int base = phww.getEigenschaftswert("Fernkampf-Basis");
                addNumberNode(XmlWorker.heldXML, idnode, "fernkampf", base);
            }
            addNumberNode(XmlWorker.heldXML, idnode, "geschosse", 0);
            addStringNode(XmlWorker.heldXML, idnode, "label", fernwaffen[i].toString());
        }
    }

    private static void add_Node_nahkampfwaffenliste(final Element node, final PluginHeld held, final PluginHeldenWerteWerkzeug2 phww) {
        final PluginNahkampfWaffe2[] waffen = phww.getAusruestung2().getNahkampfWaffen();
        final Element nahwaffennode = addNode(XmlWorker.heldXML, node, "nahkampfwaffenliste", "idcounter", "" + (waffen.length + 2));
        int index = 0;
        for (int i = 0; i < waffen.length; ++i) {
            if (waffen[i] != null) {
                ++index;
                final Element idnode = addNode(XmlWorker.heldXML, nahwaffennode, "id-" + toNormString(index));
                addNumberNode(XmlWorker.heldXML, idnode, "attacke", waffen[i].getAttacke());
                addNumberNode(XmlWorker.heldXML, idnode, "bruchfaktor", waffen[i].getBF()[1]);
                final Element diceNode = addStringNode(XmlWorker.heldXML, idnode, "damagedice", toDices(waffen[i].getTrefferpunkte()));
                diceNode.setAttribute("type", "dice");
                addNumberNode(XmlWorker.heldXML, idnode, "damagebonus", waffen[i].getTrefferpunkte()[2]);
                final String[] dkString = new String[waffen[i].getDistanzklasse().length];
                for (int k = 0; k < waffen[i].getDistanzklasse().length; ++k) {
                    dkString[k] = "" + waffen[i].getDistanzklasse()[k].charAt(0);
                }
                addStringNode(XmlWorker.heldXML, idnode, "dk", toSlashString(dkString));
                addNumberNode(XmlWorker.heldXML, idnode, "ini", waffen[i].getINIMod());
                addStringNode(XmlWorker.heldXML, idnode, "label", waffen[i].getName());
                addNumberNode(XmlWorker.heldXML, idnode, "parade", waffen[i].getParade());
                addNumberNode(XmlWorker.heldXML, idnode, "showonminisheet", 1);
                addNumberNode(XmlWorker.heldXML, idnode, "wm1", waffen[i].getWmAT());
                addNumberNode(XmlWorker.heldXML, idnode, "wm2", waffen[i].getWmPA());
                final Element tpkkNode = addNode(XmlWorker.heldXML, idnode, "tpkk");
                addNumberNode(XmlWorker.heldXML, tpkkNode, "eins", waffen[i].getKoerperkraftzuschlag()[0]);
                addNumberNode(XmlWorker.heldXML, tpkkNode, "zwei", waffen[i].getKoerperkraftzuschlag()[1]);
            }
        }
        ++index;
        final PluginTalent raufen = phww.getTalent("Raufen");
        Element idnode = addNode(XmlWorker.heldXML, nahwaffennode, "id-" + toNormString(index));
        addNumberNode(XmlWorker.heldXML, idnode, "attacke", phww.getAusruestung2().getRauferAttacke());
        addNumberNode(XmlWorker.heldXML, idnode, "bruchfaktor", 0);
        Element diceNode = addStringNode(XmlWorker.heldXML, idnode, "damagedice", "d6");
        diceNode.setAttribute("type", "dice");
        addNumberNode(XmlWorker.heldXML, idnode, "damagebonus", 0);
        addStringNode(XmlWorker.heldXML, idnode, "dk", "H");
        addNumberNode(XmlWorker.heldXML, idnode, "ini", 0);
        addStringNode(XmlWorker.heldXML, idnode, "label", raufen.toString());
        addNumberNode(XmlWorker.heldXML, idnode, "parade", phww.getAusruestung2().getRaufenParade());
        addNumberNode(XmlWorker.heldXML, idnode, "showonminisheet", 1);
        Element tpkkNode2 = addNode(XmlWorker.heldXML, idnode, "tpkk");
        addNumberNode(XmlWorker.heldXML, tpkkNode2, "eins", 10);
        addNumberNode(XmlWorker.heldXML, tpkkNode2, "zwei", 3);
        ++index;
        boolean ringen_vorhanden = false;
        final String[] talente = phww.getTalenteAlsString();
        for (int j = 0; j < talente.length; ++j) {
            if (talente[j].toString().indexOf("Ringen") != -1) {
                ringen_vorhanden = true;
                break;
            }
        }
        if (ringen_vorhanden) {
            final PluginTalent ringen = phww.getTalent("Ringen");
            idnode = addNode(XmlWorker.heldXML, nahwaffennode, "id-" + toNormString(index));
            addNumberNode(XmlWorker.heldXML, idnode, "attacke", phww.getAusruestung2().getRingenAttacke());
            addNumberNode(XmlWorker.heldXML, idnode, "bruchfaktor", 0);
            diceNode = addStringNode(XmlWorker.heldXML, idnode, "damagedice", "d6");
            diceNode.setAttribute("type", "dice");
            addNumberNode(XmlWorker.heldXML, idnode, "damagebonus", 0);
            addStringNode(XmlWorker.heldXML, idnode, "dk", "H");
            addNumberNode(XmlWorker.heldXML, idnode, "ini", 0);
            addStringNode(XmlWorker.heldXML, idnode, "label", ringen.toString());
            addNumberNode(XmlWorker.heldXML, idnode, "parade", phww.getAusruestung2().getRingenParade());
            addNumberNode(XmlWorker.heldXML, idnode, "showonminisheet", 1);
            tpkkNode2 = addNode(XmlWorker.heldXML, idnode, "tpkk");
            addNumberNode(XmlWorker.heldXML, tpkkNode2, "eins", 10);
            addNumberNode(XmlWorker.heldXML, tpkkNode2, "zwei", 3);
        }
    }

    public static void Abschlussmeldung() {
        ShowMessage("Konvertierung abgeschlossen !");
    }

    public static void Konvertiere_Held(final DGOPluginHeldenWerteWerkzeug phww) {
        final Element idElement = add_Node_base();
        add_Node_details(idElement, phww.getSelectesHeld(), phww);
        add_Node_be(idElement, phww.getSelectesHeld(), phww);
        add_Node_aep(idElement, phww.getSelectesHeld(), phww);
        add_Node_ap(idElement, phww.getSelectesHeld(), phww);
        add_Node_at(idElement, phww.getSelectesHeld(), phww);
        add_Node_attribute(idElement, phww.getSelectesHeld(), phww);
        add_Node_aup(idElement, phww.getSelectesHeld(), phww);
        add_Node_ausweichen(idElement, phww.getSelectesHeld(), phww);
        add_Node_aw(idElement, phww.getSelectesHeld(), phww);
        add_Node_money(idElement, phww.getSelectesHeld(), phww);
        add_Node_fernkampfwaffenliste(idElement, phww.getSelectesHeld(), phww);
        add_Node_fk(idElement, phww.getSelectesHeld(), phww);
        add_Node_ini(idElement, phww.getSelectesHeld(), phww);
        add_Node_inventorylist(idElement, phww.getSelectesHeld(), phww);
        add_Node_kap(idElement, phww.getSelectesHeld(), phww);
        add_Node_lep(idElement, phww.getSelectesHeld(), phww);
        add_Node_mr(idElement, phww.getSelectesHeld(), phww);
        add_Node_nachteile(idElement, phww.getSelectesHeld(), phww);
        add_Node_nahkampfwaffenliste(idElement, phww.getSelectesHeld(), phww);
        add_Node_name(idElement, phww.getSelectesHeld(), phww);
//        add_Node_notes(idElement, phww.getSelectesHeld(), phww);
        add_Node_pa(idElement, phww.getSelectesHeld(), phww);
        add_Node_schildliste(idElement, phww.getSelectesHeld(), phww);
        add_Node_sf(idElement, phww.getSelectesHeld(), phww);
        add_Node_skillist(idElement, phww.getSelectesHeld(), phww);
        add_Node_spellist(idElement, phww.getSelectesHeld(), phww);
        add_Node_vorteile(idElement, phww.getSelectesHeld(), phww);
        add_Node_ruestungsteile(idElement, phww.getSelectesHeld(), phww);
    }

    public static boolean Zeige_Dialog() {
        final ExampleFileFilter filter = new ExampleFileFilter();
        filter.addExtension("xml");
        filter.setDescription("XML Files");
        (XmlWorker.chooser = new JFileChooser()).setAcceptAllFileFilterUsed(true);
        XmlWorker.chooser.setDialogTitle("Kampagnen-Datenbank ausw\u00e4hlen");
        XmlWorker.chooser.setFileFilter((FileFilter) filter);
        XmlWorker.chooser.setDialogType(1);
        XmlWorker.chooser.setSelectedFile(new File("db.xml"));
        String dirname = System.getenv("APPDATA");
        if (dirname != null) {
            if (!XmlWorker.local_char) {
                dirname += "\\Fantasy Grounds II\\campaigns\\";
            } else {
                dirname += "\\Fantasy Grounds II\\characters\\";
            }
            XmlWorker.chooser.setCurrentDirectory(new File(dirname));
        }
        if (XmlWorker.chooser.showSaveDialog(XmlWorker.dialog) == 0) {
            XmlWorker.FG2XMLDatei = new File(XmlWorker.chooser.getCurrentDirectory(), XmlWorker.chooser.getSelectedFile().getName());
//            XmlWorker.heldXML = Oeffne_Held();
            return XmlWorker.heldXML != null;
        }
        return false;
    }
}
