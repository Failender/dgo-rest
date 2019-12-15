package de.failender.dgo.rest.gruppen;

import de.failender.dgo.persistance.gruppe.GruppeRepositoryService;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import io.javalin.Context;
import io.javalin.Javalin;

import static de.failender.dgo.rest.helden.HeldenController.map;

public class GruppeController {

	private static final String PREFIX = "api/gruppen/";
	private static final String HELDEN = PREFIX + "/gruppe/:gruppe/helden";


	public GruppeController(Javalin app) {
		app.get(PREFIX, this::getGruppen);
		app.get(HELDEN, this::getHeldenInGruppe);
	}

	private void getGruppen(Context ctx) {

		ctx.json(GruppeRepositoryService.findAll());
	}

	private void getHeldenInGruppe(Context ctx) {
		Long gruppe = Long.valueOf(ctx.pathParam("gruppe"));
		boolean includeHidden = Boolean.valueOf(ctx.queryParam("includePrivate", "false"));
		boolean showInactive = Boolean.valueOf(ctx.queryParam("showInactive", "false"));

		ctx.json(map(HeldRepositoryService.findByGruppeId(gruppe, includeHidden, showInactive)));
	}

}
