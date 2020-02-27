package de.failender.dgo.persistance.held.uebersicht;

import de.failender.dgo.persistance.held.HeldEntity;

import java.util.Arrays;

public class HeldUebersichtRepositoryService {

    public static HeldUebersichtEntity findByHeld(HeldEntity heldEntity) {

        return  HeldUebersichtRepository.INSTANCE.findByHeldid(heldEntity.getId()).orElseGet(() -> {
            HeldUebersichtEntity value = new HeldUebersichtEntity();
            value = new HeldUebersichtEntity();
            value.setHeldid(heldEntity.getId());
            value.setWunden(Arrays.asList(0, 0, 0, 0, 0));
            persist(value);
            return value;
        });

    }

    public static void persist(HeldUebersichtEntity heldUebersichtEntity) {
        if (heldUebersichtEntity.getId() == null) {
            HeldUebersichtRepository.INSTANCE.persist(heldUebersichtEntity);
            return;
        }

        HeldUebersichtRepository.INSTANCE.updateLepAndAspAndWunden(heldUebersichtEntity.getId(), heldUebersichtEntity.getLep(), heldUebersichtEntity.getAsp(), heldUebersichtEntity.getWunden());

    }

}
