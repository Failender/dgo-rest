package de.failender.dgo.rest.helden.inventar;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.inventar.HeldInventarEntity;
import de.failender.dgo.persistance.held.inventar.HeldInventarRepositoryService;
import io.javalin.Context;
import io.javalin.Javalin;

import java.util.List;

public class HeldInventarController {

    private static final String PREFIX ="/api/helden/inventar/";
    private static final String FOR_HELD = PREFIX + "held/:held";
    public HeldInventarController(Javalin app) {
        app.get(FOR_HELD, this::getInventarForHeld);
        app.post(PREFIX, this::addInventar);
    }

    private void getInventarForHeld(Context context) {
        Long held = Long.valueOf(context.pathParam("held"));

        HeldEntity heldEntity = HeldRepositoryService.findById(held);
        List<HeldInventarEntity> heldInventar = HeldInventarRepositoryService.findByHeldid(heldEntity);
        context.json(heldInventar);
    }

    private void addInventar(Context context) {
        HeldInventarEntity heldInventarEntity = context.bodyAsClass(HeldInventarEntity.class);
        HeldEntity heldEntity = HeldRepositoryService.findById(heldInventarEntity.getHeldid());
        HeldInventarRepositoryService.persist(heldEntity, heldInventarEntity);
    }
}
