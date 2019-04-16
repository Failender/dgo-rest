package de.failender.dgo.rest.integration;

import de.failender.heldensoftware.api.HeldenApi;

import java.io.File;

public class Beans {

	public static HeldenApi HELDEN_API;

	static {
		HELDEN_API = new HeldenApi(new File("E://webdev//cache"));
	}

}
