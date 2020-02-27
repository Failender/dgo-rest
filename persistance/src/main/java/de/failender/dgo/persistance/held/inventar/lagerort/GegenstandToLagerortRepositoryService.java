package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

import java.util.List;

public class GegenstandToLagerortRepositoryService {

    public static List<GegenstandToLagerortEntity> findByLagerort(Long lagerort) {
        return GegenstandToLagerortRepository.INSTANCE.findByLagerort(lagerort);
    }

    public static void removeAll(HeldEntity heldEntity, List<Long> removal) {
        HeldRepositoryService.canCurrentUserEditHeld(heldEntity);

        GegenstandToLagerortRepository.INSTANCE.deleteBulkById(removal);
    }

    public static void removeByNameAndHeld(LagerortEntity from, String gegenstand, HeldEntity heldEntity) {

        HeldRepositoryService.canCurrentUserEditHeld(heldEntity);
        GegenstandToLagerortRepository.INSTANCE.deleteByNameAndHeldid(from.getId(), gegenstand);


    }

    public static void persist(HeldEntity heldEntity, GegenstandToLagerortEntity gegenstandLagerort) {
        HeldRepositoryService.canCurrentUserEditHeld(heldEntity);
        GegenstandToLagerortRepository.INSTANCE.persist(gegenstandLagerort);
    }
}
