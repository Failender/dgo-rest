package de.failender.dgo.persistance.held.geld;

import de.failender.dgo.persistance.held.HeldEntity;

public class GeldBoerseRepositoryService {


    public static GeldBoerseEntity findGeldboerseForHeld(HeldEntity heldEntity) {
        GeldBoerseEntity geldBoerseEntity = new GeldBoerseRepository().findById(heldEntity.getId());
        if(geldBoerseEntity != null) {
            return geldBoerseEntity;
        }
        return new GeldBoerseEntity();
    }
}
