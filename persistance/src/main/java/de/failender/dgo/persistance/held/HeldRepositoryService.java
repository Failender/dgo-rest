package de.failender.dgo.persistance.held;

import java.util.List;

public class HeldRepositoryService {
    public static List<HeldEntity> findByUserId(Long id) {
        return HeldRepository.findByUserIdOrdered(id);
    }

    public static void saveHeld(HeldEntity heldEntity) {
        HeldRepository.persist(heldEntity);
    }

    public static HeldEntity findById(Long id) {
        return HeldRepository.findById(id);
    }

    public static void updatePublic(HeldEntity heldEntity, boolean value) {
        HeldRepository.updatePublic(heldEntity.getId(), value);
    }

    public static void updateActive(HeldEntity heldEntity, boolean value) {
        HeldRepository.updateActive(heldEntity.getId(), value);
    }
}
