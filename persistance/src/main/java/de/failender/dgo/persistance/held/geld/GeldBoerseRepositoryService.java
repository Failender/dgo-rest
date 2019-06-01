package de.failender.dgo.persistance.held.geld;

import de.failender.dgo.persistance.held.HeldEntity;

public class GeldBoerseRepositoryService {


    public static GeldBoerseEntity findGeldboerseForHeld(HeldEntity heldEntity) {
        GeldBoerseEntity geldBoerseEntity = GeldBoerseRepository.INSTANCE.findById(heldEntity.getId());
        if(geldBoerseEntity != null) {
            return geldBoerseEntity;
        }
        return new GeldBoerseEntity();
    }

    public static void updateGeldboerseForHeld(GeldBoerseEntity geldBoerseEntity, HeldEntity heldEntity) {

        if (geldBoerseEntity.getId() == null) {
            GeldBoerseRepository.INSTANCE.persist(geldBoerseEntity);
        } else {
            GeldBoerseRepository.INSTANCE.updateAnzahl(geldBoerseEntity);
        }
    }
}
