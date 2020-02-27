package de.failender.dgo.persistance.held.inventar;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.ezql.queries.DeleteQuery;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.util.List;
import java.util.Optional;

public class HeldInventarRepositoryService {

    public static void persist(HeldEntity heldEntity, HeldInventarEntity entity) {
        HeldInventarRepository.INSTANCE.persist(entity);
    }

    public static List<HeldInventarEntity> findByHeldid(HeldEntity heldEntity) {
        return HeldInventarRepository.INSTANCE.findByHeldid(heldEntity.getId());
    }

    public static Optional<HeldInventarEntity> findById(Long id) {
        return HeldInventarRepository.INSTANCE.findById(id);
    }

    public static void updateAnzahl(HeldInventarEntity entity, int anzahl) {
        HeldInventarRepository.INSTANCE.updateAnzahl(entity.getId(), anzahl);
    }

    public static void delete(HeldInventarEntity entity) {
        HeldEntity heldEntity = HeldRepositoryService.findByIdReduced(entity.getHeldid());
        HeldRepositoryService.canCurrentUserEditHeld(heldEntity);
        HeldInventarRepository.delete(entity.getId());
    }
}
