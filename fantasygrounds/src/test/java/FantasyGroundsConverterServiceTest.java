import de.failender.fantasygrounds.FantasyGroundsConverterService;
import de.failender.heldensoftware.JaxbUtil;
import de.failender.heldensoftware.api.XmlUtil;
import de.failender.heldensoftware.xml.datenxml.Daten;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FantasyGroundsConverterServiceTest extends XMLTestCase {

    @Test
    public void testConversion() throws IOException, SAXException {


        Daten zoromirDaten = JaxbUtil.datenFromStream(FantasyGroundsConverterServiceTest.class.getResourceAsStream("zoromir_daten.xml"));
        Daten toriDaten = JaxbUtil.datenFromStream(FantasyGroundsConverterServiceTest.class.getResourceAsStream("tori_daten.xml"));

        List<Daten> helden = new ArrayList<>();
        helden.add(toriDaten);
        helden.add(zoromirDaten);


        String tested = FantasyGroundsConverterService.convert(helden, FantasyGroundsConverterServiceTest.class.getResourceAsStream("db.xml"), Collections.emptyList());

        String migrated = toString(FantasyGroundsConverterServiceTest.class.getResourceAsStream("db_migrated.xml"));
        XMLUnit.setIgnoreWhitespace(true);
        Document testedDocument = XmlUtil.documentFromString(tested);
        Document migratedDocument = XmlUtil.documentFromString(migrated);
        assertXMLEqual(migrated, tested);

    }

    private String toString(InputStream is) throws IOException {
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (is, Charset.forName(StandardCharsets.ISO_8859_1.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }
}
