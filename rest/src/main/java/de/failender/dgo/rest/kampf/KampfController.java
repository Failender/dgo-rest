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
    private static final String HALTEN = KAMPF + "halten";
    private static final String TEILNEHMER = KAMPF + "teilnehmer/:teilnehmer/";
    private static final String AT = TEILNEHMER + "at";
    private static final String PA = TEILNEHMER + "pa";


    private Cache<Integer, Kampf> cache = Caffeine.newBuilder()
            .expireAfterAccess(8, TimeUnit.HOURS)
            .build();


    public KampfController(Javalin app) {
        app.get(KAMPF, this::getKampf);
        app.delete(KAMPF, this::removeKampf);
        app.get(PREFIX, this::getKaempfe);
        app.post(PREFIX, this::startKampf);
        app.put(PREFIX, this::updateKampf);
        app.post(NEXT, this::nextTeilnehmer);
        app.post(HALTEN, this::halten);
        app.post(AT, this::at);
        app.post(PA, this::pa);
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

    private void removeKampf(Context ctx) {
        int gruppe = Integer.valueOf(ctx.pathParam("gruppe"));
        cache.invalidate(gruppe);
    }

    private void getKaempfe(Context ctx) {

    }

    private void updateKampf(Context ctx) {
        Kampf kampf = ctx.bodyAsClass(Kampf.class);
        if(!validateKampf(kampf, ctx)) {
            return;
        }

        //kampf.getTeilnehmer().sort(Comparator.comparingInt(Teilnehmer::getIni));
        cache.put(kampf.getGruppe(), kampf);
        ctx.json(kampf);
    }

    private void nextTeilnehmer(Context ctx) {
        int gruppe = Integer.valueOf(ctx.pathParam("gruppe"));
        Kampf kampf = cache.getIfPresent(gruppe);
        if(kampf == null) {
            ctx.status(404);
            ctx.json("Aktuell ist kein Kampf aktiv");
            return;
        }
        Teilnehmer active = kampf.getTeilnehmer().get(kampf.getCurrentTeilnehmer());
        if(!nextTeilnehmer(kampf)) {
            active.setAtAktion(false);
        }

        ctx.json(kampf);
    }

    // Updates the teilnehmer to the next one. Will return true if new round began
    private boolean nextTeilnehmer(Kampf kampf) {
        if(kampf.getCurrentTeilnehmer() +1 == kampf.getTeilnehmer().size() ) {
            kampf.setCurrentTeilnehmer(0);
            for (Teilnehmer teilnehmer : kampf.getTeilnehmer()) {
                teilnehmer.resetAktionen();
            }


            this.sortTeilnehmer(kampf);
            return true;

        }
        kampf.setCurrentTeilnehmer(kampf.getCurrentTeilnehmer() +1);
        return false;
    }

    private void halten(Context ctx) {
        int gruppe = Integer.valueOf(ctx.pathParam("gruppe"));
        Kampf kampf = cache.getIfPresent(gruppe);
        if(kampf == null) {
            ctx.status(404);
            ctx.json("Aktuell ist kein Kampf aktiv");
            return;
        }

        nextTeilnehmer(kampf);

        ctx.json(kampf);
    }

    private void at(Context ctx) {
        int gruppe = Integer.valueOf(ctx.pathParam("gruppe"));
        Kampf kampf = cache.getIfPresent(gruppe);
        if(kampf == null) {
            ctx.status(404);
            ctx.json("Aktuell ist kein Kampf aktiv");
            return;
        }

        int teilnehmerId = Integer.valueOf(ctx.pathParam("teilnehmer"));
        Teilnehmer teilnehmer = getTeilnehmer(kampf, teilnehmerId);
        teilnehmer.setAtAktion(false);
        if(kampf.getTeilnehmer().get(kampf.getCurrentTeilnehmer()) == teilnehmer) {
            nextTeilnehmer(kampf);
        }
        ctx.json(kampf);

    }

    private void pa(Context ctx) {
        int gruppe = Integer.valueOf(ctx.pathParam("gruppe"));
        Kampf kampf = cache.getIfPresent(gruppe);
        if(kampf == null) {
            ctx.status(404);
            ctx.json("Aktuell ist kein Kampf aktiv");
            return;
        }

        int teilnehmerId = Integer.valueOf(ctx.pathParam("teilnehmer"));
        Teilnehmer teilnehmer = getTeilnehmer(kampf, teilnehmerId);
        teilnehmer.setPaAktion(false);
        ctx.json(kampf);
    }

    private Teilnehmer getTeilnehmer(Kampf kampf, int id) {
        return kampf.getTeilnehmer()
                .stream()
                .filter(teilnehmer -> teilnehmer.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void startKampf(Context ctx) {
        Kampf kampf = ctx.bodyAsClass(Kampf.class);
        if(!validateKampf(kampf, ctx)) {
            return;
        }
        if(cache.getIfPresent(kampf.getGruppe()) != null) {
            ctx.status(409);
            return;
        }

        sortTeilnehmer(kampf);
        for (int i = 0; i < kampf.getTeilnehmer().size(); i++) {
            kampf.getTeilnehmer().get(i).setId(i);
        }
        cache.put(kampf.getGruppe(), kampf);

    }

    private void sortTeilnehmer(Kampf kampf) {
        kampf.getTeilnehmer().sort((a,b) -> {
            if(a.getIni() == b.getIni()) {
                return b.getIniBasis() - a.getIniBasis();
            }
            return b.getIni() - a.getIni();
        });

    }

    private boolean validateKampf(Kampf kampf, Context ctx) {
        if(kampf.getName() == null || kampf.getName().isEmpty()) {
            ctx.status(400);
            return false;
        }

        return true;
    }




}
