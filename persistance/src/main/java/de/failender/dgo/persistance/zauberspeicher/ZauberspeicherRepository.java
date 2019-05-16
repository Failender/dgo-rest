package de.failender.dgo.persistance.zauberspeicher;

import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.HeldRepositoryService;
import de.failender.ezql.queries.DeleteQuery;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.util.List;

class ZauberspeicherRepository {

    public static void save(ZauberspeicherEntity zauberspeicherEntity) {
        new InsertQuery<>(ZauberspeicherMapper.INSTANCE, zauberspeicherEntity)
                .execute();
    }

    public static List<ZauberspeicherEntity> findByHeldId(Long heldid) {
        return SelectQuery.Builder.selectAll(ZauberspeicherMapper.INSTANCE)
                .where(ZauberspeicherMapper.HELD_ID, heldid)
                .build()
                .execute();
    }

    public static void delete(Long speicher) {
        DeleteQuery.Builder.delete(ZauberspeicherMapper.INSTANCE)
                .where(ZauberspeicherMapper.ID, speicher)
                .build().execute();
    }
}
