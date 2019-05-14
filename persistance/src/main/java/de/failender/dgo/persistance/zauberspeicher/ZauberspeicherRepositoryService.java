package de.failender.dgo.persistance.zauberspeicher;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.util.List;

public class ZauberspeicherRepositoryService {

    public static void save(ZauberspeicherEntity zauberspeicherEntity) {
        ZauberspeicherRepository.save(zauberspeicherEntity);
    }

    public static List<ZauberspeicherEntity> findByHeld(HeldEntity heldEntity) {
        return ZauberspeicherRepository.findByHeldId(heldEntity.getId());
    }
}
