package de.failender.dgo.rest.helden.version;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.rest.helden.HeldenService;
import io.javalin.Context;
import io.javalin.Javalin;

import java.util.List;
import java.util.stream.Collectors;

public class VersionController {

    public static final String PREFIX = "api/helden/versionen/";
    public static final String FOR_HELD= PREFIX + "held/:held";


    public VersionController(Javalin app) {
        app.get(FOR_HELD, this::getVersionenForHeld);
    }

    private void getVersionenForHeld(Context ctx) {
        HeldEntity heldEntity = HeldenService.getHeldEntity(ctx);
        List<VersionEntity> versionEntityList =VersionRepositoryService.findVersionsByHeld(heldEntity);
        ctx.json(versionEntityList.stream().map(VersionDto::new).collect(Collectors.toList()));


    }
}
