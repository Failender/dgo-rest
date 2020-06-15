package de.failender.dgo.rest;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import io.javalin.Context;

public class UtilService {

    public static HeldEntity getHeldEntity(Context context) {
        Long held = Long.valueOf(context.pathParam("held"));
        return HeldRepositoryService.findByIdReduced(held);

    }
}
