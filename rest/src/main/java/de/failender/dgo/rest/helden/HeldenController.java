package de.failender.dgo.rest.helden;

import io.javalin.Context;
import io.javalin.Javalin;

public class HeldenController {

	public static final String PREFIX = "api/helden";
	public static final String FOR_USER = PREFIX + "/:user";

	public HeldenController(Javalin javalin) {

		javalin.get(FOR_USER, this::getHeldenForUser);
	}

	private void getHeldenForUser(Context context) {

	}
}
