package de.failender.dgo.rest.helden.inventar;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.dgo.persistance.held.inventar.HeldInventarEntity;
import de.failender.dgo.persistance.held.inventar.HeldInventarRepositoryService;
import de.failender.dgo.security.EntityNotFoundException;
import io.javalin.Context;
import io.javalin.Javalin;

import java.util.List;

public class HeldInventarController {

    private static final String PREFIX ="/api/helden/inventar/";
    private static final String FOR_HELD = PREFIX + "held/:held";
    private static final String ANZAHL = PREFIX + "entry/:id/anzahl/:anzahl";
    private static final String ENTRY = PREFIX + "entry/:id";
    public HeldInventarController(Javalin app) {
        app.get(FOR_HELD, this::getInventarForHeld);
        app.post(PREFIX, this::addInventar);
        app.put(ANZAHL, this::updateAnzahl);
        app.delete(ENTRY, this::deleteEntry);
    }

    private void deleteEntry(Context context) {
        Long id = Long.valueOf(context.pathParam("id"));
        HeldInventarRepositoryService.findById(id).ifPresent(HeldInventarRepositoryService::delete);
    }

    private void getInventarForHeld(Context context) {
        Long held = Long.valueOf(context.pathParam("held"));


        HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(held);
        List<Gegenstand> inventar = HeldInventarService.getInventar(held);
        List<HeldInventarEntity> heldInventar = HeldInventarRepositoryService.findByHeldid(heldEntity);
        context.json(heldInventar);
    }

    private void addInventar(Context context) {
        HeldInventarEntity heldInventarEntity = context.bodyAsClass(HeldInventarEntity.class);
        HeldInventarService.addItem(heldInventarEntity.getHeldid(), heldInventarEntity.getName(), heldInventarEntity.getAnzahl());

        HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(heldInventarEntity.getHeldid());
        HeldInventarRepositoryService.persist(heldEntity, heldInventarEntity);
    }

    private void updateAnzahl(Context context) {
        long id = Long.valueOf(context.pathParam("id"));
        int anzahl = Integer.valueOf(context.pathParam("anzahl"));
        HeldInventarEntity heldInventarEntity = HeldInventarRepositoryService.findById(id).orElseThrow(() -> new EntityNotFoundException("Inventar konnte nicht gefunden werden"));
        HeldInventarRepositoryService.updateAnzahl(heldInventarEntity, anzahl);
    }
}
