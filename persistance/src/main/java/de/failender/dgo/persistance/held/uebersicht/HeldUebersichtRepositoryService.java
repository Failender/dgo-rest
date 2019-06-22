package de.failender.dgo.persistance.held.uebersicht;

import de.failender.dgo.persistance.held.HeldEntity;

import java.util.Arrays;

public class HeldUebersichtRepositoryService {

    public static HeldUebersichtEntity findByHeld(HeldEntity heldEntity) {

        HeldUebersichtEntity entity = HeldUebersichtRepository.INSTANCE.findByHeldid(heldEntity.getId());
        if (entity != null) {
            return entity;
        }

        entity = new HeldUebersichtEntity();
        entity.setHeldid(heldEntity.getId());
        entity.setWunden(Arrays.asList(0, 0, 0, 0, 0));
        persist(entity);
        return entity;
    }

    public static void persist(HeldUebersichtEntity heldUebersichtEntity) {
        if (heldUebersichtEntity.getId() == null) {
            HeldUebersichtRepository.INSTANCE.persist(heldUebersichtEntity);
            return;
        }

        HeldUebersichtRepository.INSTANCE.updateLepAndAspAndWunden(heldUebersichtEntity.getId(), heldUebersichtEntity.getLep(), heldUebersichtEntity.getAsp(), heldUebersichtEntity.getWunden());

    }

}
