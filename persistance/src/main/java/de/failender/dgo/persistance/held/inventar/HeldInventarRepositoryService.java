package de.failender.dgo.persistance.held.inventar;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.ezql.queries.DeleteQuery;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.util.List;

public class HeldInventarRepositoryService {

    public static void persist(HeldEntity heldEntity, HeldInventarEntity entity) {
        HeldInventarRepository.persist(entity);
    }

    public static List<HeldInventarEntity> findByHeldid(HeldEntity heldEntity) {
        return HeldInventarRepository.findByHeldid(heldEntity.getId());
    }

    public static void delete(Long id) {
        HeldInventarRepository.delete(id);
    }
}
