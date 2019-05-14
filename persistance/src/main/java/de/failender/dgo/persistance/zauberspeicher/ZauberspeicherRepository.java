package de.failender.dgo.persistance.zauberspeicher;

import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.util.List;

class ZauberspeicherRepository {

    public static void save(ZauberspeicherEntity zauberspeicherEntity) {
        new InsertQuery<>(ZauberspeicherMapper.INSTANCE, zauberspeicherEntity);
    }

    public static List<ZauberspeicherEntity> findByHeldId(Long heldid) {
        return SelectQuery.Builder.selectAll(ZauberspeicherMapper.INSTANCE)
                .where(ZauberspeicherMapper.HELD_ID, heldid)
                .build()
                .execute();
    }
}
