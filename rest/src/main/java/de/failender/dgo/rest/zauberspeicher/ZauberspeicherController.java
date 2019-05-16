package de.failender.dgo.rest.zauberspeicher;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.zauberspeicher.ZauberspeicherEntity;
import de.failender.dgo.persistance.zauberspeicher.ZauberspeicherRepositoryService;
import io.javalin.Context;
import io.javalin.Javalin;

public class ZauberspeicherController {

    private static final String PREFIX = "/api/zauberspeicher/";
    private static final String FOR_HELD = PREFIX + "held/:held";
    private static final String SPEICHER = PREFIX + "speicher/:speicher";

    public ZauberspeicherController(Javalin app) {
        app.get(FOR_HELD, this::getZauberspeicherForHeld);
        app.post(PREFIX, this::persist);
        app.delete(SPEICHER, this::delete);
    }

    private void getZauberspeicherForHeld(Context context) {
        Long held = Long.valueOf(context.pathParam("held"));
        HeldEntity heldEntity = HeldRepositoryService.findById(held);
        context.json(ZauberspeicherRepositoryService.findByHeld(heldEntity));
    }

    private void persist(Context context) {
        ZauberspeicherEntity zauberspeicherEntity = context.bodyAsClass(ZauberspeicherEntity.class);
        ZauberspeicherRepositoryService.save(zauberspeicherEntity);
    }

    private void delete(Context context) {
        Long speicher = Long.valueOf(context.pathParam("speicher"));
        ZauberspeicherRepositoryService.delete(speicher);
    }
}
