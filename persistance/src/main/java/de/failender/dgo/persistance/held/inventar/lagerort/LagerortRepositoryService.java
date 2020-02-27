package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;

import java.util.List;
import java.util.Optional;

public class LagerortRepositoryService {


    public static List<LagerortEntity> findByHeld(HeldEntity heldEntity) {
        return LagerortRepository.INSTANCE.findByHeldId(heldEntity.getId());
    }

    public static Optional<LagerortEntity> findByNameAndHeld(String name , HeldEntity heldEntity) {
        return LagerortRepository.INSTANCE.findOneByNameAndHeldId(name, heldEntity.getId());
    }

    public static void persist(HeldEntity heldEntity, LagerortEntity lagerortEntity) {
        HeldRepositoryService.canCurrentUserEditHeld(heldEntity);
        LagerortRepository.INSTANCE.persist(lagerortEntity);
    }
}
