package de.failender.dgo.persistance.held.geld;

import de.failender.dgo.persistance.held.HeldEntity;

public class GeldBoerseRepositoryService {


    public static GeldBoerseEntity findGeldboerseForHeld(HeldEntity heldEntity) {
        GeldBoerseEntity geldBoerseEntity = GeldBoerseRepository.INSTANCE.findByHeldid(heldEntity.getId());
        if(geldBoerseEntity != null) {
            return geldBoerseEntity;
        }
        GeldBoerseEntity entity = new GeldBoerseEntity();
        entity.setHeldid(heldEntity.getId());
        return entity;
    }

    public static void updateGeldboerseForHeld(GeldBoerseEntity geldBoerseEntity, HeldEntity heldEntity) {

        if (geldBoerseEntity.getId() == null) {
            GeldBoerseRepository.INSTANCE.persist(geldBoerseEntity);
        } else {
            GeldBoerseRepository.INSTANCE.updateAnzahl(geldBoerseEntity);
        }

    }
}
