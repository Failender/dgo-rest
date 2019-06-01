package de.failender.dgo.persistance.held.uebersicht;

import de.failender.dgo.persistance.held.HeldEntity;

public class HeldUebersichtRepositoryService {

    public static HeldUebersichtEntity findByHeld(HeldEntity heldEntity) {

        HeldUebersichtEntity entity = HeldUebersichtRepository.INSTANCE.findByHeldid(heldEntity.getId());
        if (entity != null) {
            return entity;
        }

        entity = new HeldUebersichtEntity();
        entity.setHeldid(heldEntity.getId());
        persist(entity);
        return entity;
    }

    public static void persist(HeldUebersichtEntity heldUebersichtEntity) {
        if (heldUebersichtEntity.getId() == null) {
            HeldUebersichtRepository.INSTANCE.persist(heldUebersichtEntity);
            return;
        }

        HeldUebersichtRepository.INSTANCE.updateLepAndAsp(heldUebersichtEntity.getId(), heldUebersichtEntity.getLep(), heldUebersichtEntity.getAsp());

    }

}
