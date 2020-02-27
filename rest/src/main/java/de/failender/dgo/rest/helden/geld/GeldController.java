package de.failender.dgo.rest.helden.geld;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.held.VersionRepositoryService;
import de.failender.dgo.persistance.held.geld.GeldBoerseEntity;
import de.failender.dgo.rest.integration.Beans;
import de.failender.heldensoftware.api.requests.ReturnHeldDatenWithEreignisseRequest;
import de.failender.heldensoftware.xml.datenxml.Daten;
import de.failender.heldensoftware.xml.datenxml.Muenze;
import io.javalin.Context;
import io.javalin.Javalin;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


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
        VersionEntity versionEntity = VersionRepositoryService.findLatestVersion(heldEntity);
        Daten daten = Beans.HELDEN_API.request(new ReturnHeldDatenWithEreignisseRequest(heldEntity.getId(), null, versionEntity.getCacheId()))
                .block();
        List<Muenze> mittelreichMuenzen = daten.getMuenzen().getMuenze()
                .stream()
                .filter(entry -> entry.getWaehrung().equals("Mittelreich"))
                .collect(Collectors.toList());

        long anzahl = mittelreichMuenzen
                .stream()
                .map(entry -> {
                    long value = entry.getWertinsilberstuecken().multiply(BigDecimal.valueOf(100)).longValue() * entry.getAnzahl();
                    return value;
                }).mapToLong(value -> value).sum();
        GeldBoerseEntity geldBoerseEntity = new GeldBoerseEntity();
        geldBoerseEntity.setAnzahl(anzahl);
        geldBoerseEntity.setHeldid(held);
        context.json(geldBoerseEntity);


    }

    private void updateGeldBoerseForHeld(Context context) {

        GeldBoerseEntity geldBoerseEntity = context.bodyAsClass(GeldBoerseEntity.class);
        GeldService.updateGeld(geldBoerseEntity.getHeldid(), geldBoerseEntity);
        context.json(geldBoerseEntity);
    }
}
