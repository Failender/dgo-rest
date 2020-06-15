package de.failender.dgo.rest.helden.version;

import de.failender.dgo.integration.Differences;
import de.failender.dgo.integration.VersionService;
import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.rest.UtilService;
import io.javalin.Context;
import io.javalin.Javalin;

import java.util.List;
import java.util.stream.Collectors;

public class VersionController {

    public static final String PREFIX = "api/helden/versionen/";
    public static final String FOR_HELD = PREFIX + "held/:held/";
    public static final String COMPARE = FOR_HELD + "compare/:from/:to";


    public VersionController(Javalin app) {
        app.get(FOR_HELD, this::getVersionenForHeld);
        app.get(COMPARE, this::compare);
    }

    private void getVersionenForHeld(Context ctx) {
        HeldEntity heldEntity = UtilService.getHeldEntity(ctx);
        List<VersionEntity> versionEntityList =VersionRepositoryService.findVersionsByHeld(heldEntity);
        ctx.json(versionEntityList.stream().map(VersionDto::new).collect(Collectors.toList()));
    }

    private void compare(Context ctx) {
        Long id = Long.valueOf(ctx.pathParam("held"));
        int from = Integer.valueOf(ctx.pathParam("from"));
        int to = Integer.valueOf(ctx.pathParam("to"));
        Differences differences = VersionService.calculateDifferences(id, from, to);
        ctx.json(differences);


    }
}
