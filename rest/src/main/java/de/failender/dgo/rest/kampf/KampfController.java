package de.failender.dgo.rest.kampf;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.javalin.Context;
import io.javalin.Javalin;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class KampfController {

    private static final String PREFIX = "/api/kampf/";
    private static final String KAMPF = PREFIX + ":gruppe/";
    private static final String NEXT = KAMPF + "next";


    private Cache<Integer, Kampf> cache = Caffeine.newBuilder()
            .expireAfterAccess(8, TimeUnit.HOURS)
            .build();


    public KampfController(Javalin app) {
        app.get(KAMPF, this::getKampf);
        app.get(PREFIX, this::getKaempfe);
        app.post(PREFIX, this::startKampf);
        app.put(PREFIX, this::startKampf);
        app.post(NEXT, this::nextTeilnehmer);
    }

    private void getKampf(Context ctx) {
        int gruppe = Integer.valueOf(ctx.pathParam("gruppe"));
        Kampf kampf = cache.getIfPresent(gruppe);
        if(kampf != null) {
            ctx.json(kampf);
        } else {
            ctx.status(404);
            ctx.json("Aktuell ist kein Kampf aktiv");
        }
    }

    private void getKaempfe(Context ctx) {

    }

    private void updateKampf(Context ctx) {
        Kampf kampf = ctx.bodyAsClass(Kampf.class);
        if(!validateKampf(kampf, ctx)) {
            return;
        }

        kampf.getTeilnehmer().sort(Comparator.comparingInt(Teilnehmer::getIni));
        cache.put(kampf.getGruppe(), kampf);
    }

    private void nextTeilnehmer(Context ctx) {
        int gruppe = Integer.valueOf(ctx.pathParam("gruppe"));
        Kampf kampf = cache.getIfPresent(gruppe);
        if(kampf == null) {
            ctx.status(404);
            ctx.json("Aktuell ist kein Kampf aktiv");
            return;
        }
        kampf.setCurrentTeilnehmer((kampf.getCurrentTeilnehmer() +1) % kampf.getTeilnehmer().size());

        ctx.json(kampf);
    }

    private void startKampf(Context ctx) {
        Kampf kampf = ctx.bodyAsClass(Kampf.class);
        if(!validateKampf(kampf, ctx)) {
            return;
        }

        kampf.getTeilnehmer().sort(Comparator.comparingInt(Teilnehmer::getIni));
        cache.put(kampf.getGruppe(), kampf);

    }

    private boolean validateKampf(Kampf kampf, Context ctx) {
        if(kampf.getName() == null || kampf.getName().isEmpty()) {
            ctx.status(400);
            return false;
        }

        if(cache.getIfPresent(kampf.getGruppe()) != null) {
            ctx.status(409);
            return false;
        }

        return true;
    }




}
