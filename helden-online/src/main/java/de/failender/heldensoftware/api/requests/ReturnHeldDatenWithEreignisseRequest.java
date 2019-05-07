// Generated by delombok at Thu Nov 22 18:54:11 CET 2018
package de.failender.heldensoftware.api.requests;

import de.failender.heldensoftware.JaxbUtil;
import de.failender.heldensoftware.api.CorruptXmlException;
import de.failender.heldensoftware.api.HeldenApi;
import de.failender.heldensoftware.api.authentication.Authentication;
import de.failender.heldensoftware.xml.datenxml.Daten;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.failender.heldensoftware.HeldenSoftwareUtil.clearEreigniskontrolle;


public class ReturnHeldDatenWithEreignisseRequest extends IdCachedRequest<Daten> {

	private final Authentication authentication;

	public ReturnHeldDatenWithEreignisseRequest(Long heldid, Authentication authentication, UUID cacheId) {
		super(cacheId, heldid, false);
		this.authentication = authentication;
	}

	@Override
	public Map<String, String> writeRequest() {
		Map<String, String> data = new HashMap<>();
		if (authentication != null) {
			authentication.writeToRequest(data);
		}
		data.put("action", "returnheld");
		data.put("format", HeldenApi.Format.datenxml.toString());
		data.put("heldenid", heldid.toString());
		data.put("opt", "ereignisse");
		return data;
	}

	@Override
	public String fileExtension() {
		return "xml";
	}

	@Override
	protected String cacheFolder() {
		return "datene";
	}

	@Override
	public Daten mapResponse(InputStream is) {
		Unmarshaller unmarshaller = JaxbUtil.getUnmarshaller(Daten.class);
		try {
			Daten daten = (Daten) unmarshaller.unmarshal(is);
			clearEreigniskontrolle(daten.getEreignisse().getEreignis());
			return daten;
		} catch (JAXBException e) {
			System.err.println("Critical error getting held-daten " + heldid);
			throw new CorruptXmlException(e);
		}
	}
}
