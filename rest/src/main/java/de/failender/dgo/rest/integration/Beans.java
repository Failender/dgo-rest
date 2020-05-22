package de.failender.dgo.rest.integration;

import de.failender.dgo.rest.helden.HeldUpdateListener;
import de.failender.ezql.properties.PropertyReader;
import de.failender.heldensoftware.api.HeldenApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Beans {

	public static HeldenApi HELDEN_API;
	public static List<HeldUpdateListener> HELD_UPDATE_LISTENER = new ArrayList<>();

	static {

		HELDEN_API = new HeldenApi(new File(PropertyReader.getProperty("helden.online.cache.directory")));
	}

}
