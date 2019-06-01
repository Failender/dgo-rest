package de.failender.dgo.rest.helden.geld;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.geld.GeldBoerseEntity;
import de.failender.dgo.persistance.held.geld.GeldBoerseRepositoryService;
import io.javalin.Context;
import io.javalin.Javalin;


public class GeldController {

    private static final String PREFIX = "/api/helden/geld/";
    private static final String FOR_HELD = PREFIX + "held/:held";

    public GeldController(Javalin app) {
        app.get(FOR_HELD, this::getGeldBoerseForHeld);
        app.put(PREFIX, this::updateGeldBoerseForHeld);
    }

    private void getGeldBoerseForHeld(Context context) {
        Long held = Long.valueOf(context.pathParam("held"));

        HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(held);
        context.json(GeldBoerseRepositoryService.findGeldboerseForHeld(heldEntity));
    }

    private void updateGeldBoerseForHeld(Context context) {

        GeldBoerseEntity geldBoerseEntity = context.bodyAsClass(GeldBoerseEntity.class);
        HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(geldBoerseEntity.getHeldid());
        GeldBoerseRepositoryService.updateGeldboerseForHeld(geldBoerseEntity, heldEntity);
        context.json(geldBoerseEntity);
    }
}
