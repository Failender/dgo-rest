package de.failender.dgo.rest.gruppen;

import de.failender.dgo.persistance.gruppe.GruppeRepository;
import io.javalin.Context;
import io.javalin.Javalin;

public class GruppeController {

	private static final String PREFIX = "api/gruppen/";


	public GruppeController(Javalin app) {
		app.get(PREFIX, this::getGruppen);
	}

	private void getGruppen(Context ctx) {

		ctx.json(GruppeRepository.findAll());
	}

}
