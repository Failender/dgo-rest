package de.failender.dgo.rest.helden.uebersicht;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.uebersicht.HeldUebersichtEntity;
import de.failender.dgo.persistance.held.uebersicht.HeldUebersichtRepositoryService;
import io.javalin.Context;
import io.javalin.Javalin;

public class HeldUebersichtController {

    private static final String PREFIX = "/api/helden/uebersicht/";
    private static final String FOR_HELD = PREFIX + "held/:held";

    public HeldUebersichtController(Javalin app) {
        app.get(FOR_HELD, this::getUebersichtForHeld);
        app.put(PREFIX, this::updateUebersicht);
    }

    private void getUebersichtForHeld(Context context) {
        Long held = Long.valueOf(context.pathParam("held"));
        HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(held);
        HeldUebersichtEntity heldUebersichtEntity = HeldUebersichtRepositoryService.findByHeld(heldEntity);
        context.json(heldUebersichtEntity);

    }

    private void updateUebersicht(Context context) {
        HeldUebersichtEntity heldUebersichtEntity = context.bodyAsClass(HeldUebersichtEntity.class);
        HeldUebersichtRepositoryService.persist(heldUebersichtEntity);
    }
}
