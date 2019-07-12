package de.failender.dgo.rest.meister.raumplan;

import de.failender.dgo.persistance.meister.raumplan.RaumplanEntity;
import de.failender.dgo.persistance.meister.raumplan.RaumplanRepositoryService;
import de.failender.dgo.persistance.meister.raumplanebene.RaumplanEbeneEntity;
import de.failender.dgo.persistance.meister.raumplanebene.RaumplanEbeneRepositoryService;
import io.javalin.Context;
import io.javalin.Javalin;

import java.util.stream.Collectors;

public class RaumplanController {

    public static final String PREFIX = "/api/meister/raumplan";
    public static final String EBENEN = PREFIX + "/ebenen/:raumplan";
    public static final String EBENE = PREFIX + "/ebenen/:raumplan/ebene/:ebene";

    public RaumplanController(Javalin app) {
        app.get(PREFIX, this::getRaumplaene);
        app.post(PREFIX, this::persistRaumplan);


        app.get(EBENEN, this::getEbenen);
        app.post(EBENEN, this::addEbene);
        app.delete(EBENEN, this::deleteRaumplan);

        app.delete(EBENE, this::deleteEbene);
    }

    private void getRaumplaene(Context context) {
        context.json(RaumplanRepositoryService.findRaumplaeneForCurrentUser().stream().map(RaumplanDto::new).collect(Collectors.toList()));
    }

    private void persistRaumplan(Context context) {
        RaumplanEntity raumplanEntity = context.bodyAsClass(RaumplanEntity.class);
        RaumplanRepositoryService.persist(raumplanEntity);
    }

    private void deleteRaumplan(Context context) {
        Long raumId = Long.valueOf(context.pathParam("raumplan"));
        RaumplanRepositoryService.deleteById(raumId);
    }

    private void getEbenen(Context context){
        Long raumId = Long.valueOf(context.pathParam("raumplan"));
        context.json(RaumplanEbeneRepositoryService.findByParent(raumId));
    }

    private void addEbene(Context context) {
        Long raumId = Long.valueOf(context.pathParam("raumplan"));
        RaumplanEbeneEntity entity = context.bodyAsClass(RaumplanEbeneEntity.class);
        entity.setParent(raumId);
        RaumplanEbeneRepositoryService.persist(entity);
    }

    private void deleteEbene(Context context) {
        Long ebene = Long.valueOf(context.pathParam("ebene"));
        RaumplanEbeneRepositoryService.deleteById(ebene);

    }
}
