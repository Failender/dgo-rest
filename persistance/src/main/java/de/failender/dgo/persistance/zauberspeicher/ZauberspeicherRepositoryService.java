package de.failender.dgo.persistance.zauberspeicher;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.util.List;

public class ZauberspeicherRepositoryService {

    public static void save(ZauberspeicherEntity zauberspeicherEntity) {
        HeldRepositoryService.findById(zauberspeicherEntity.getHeldid());
        ZauberspeicherRepository.save(zauberspeicherEntity);
    }

    public static List<ZauberspeicherEntity> findByHeld(HeldEntity heldEntity) {
        return ZauberspeicherRepository.findByHeldId(heldEntity.getId());
    }

    public static void delete(Long speicher) {
        ZauberspeicherRepository.delete(speicher);
    }
}
