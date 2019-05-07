package de.failender.dgo.persistance.held;

import java.util.List;

public class HeldRepositoryService {
    public static List<HeldEntity> findByUserId(Long id) {
        return HeldRepository.findByUserId(id);
    }

    public static void saveHeld(HeldEntity heldEntity) {
        HeldRepository.persist(heldEntity);
    }
}
