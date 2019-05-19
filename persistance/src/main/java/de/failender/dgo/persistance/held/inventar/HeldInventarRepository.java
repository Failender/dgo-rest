package de.failender.dgo.persistance.held.inventar;

import com.sun.org.apache.bcel.internal.generic.Select;
import de.failender.ezql.queries.DeleteQuery;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import javax.swing.text.DefaultEditorKit;
import java.util.List;

class HeldInventarRepository {

    public static void persist(HeldInventarEntity entity) {
        new InsertQuery<>(HeldInventarMapper.INSTANCE, entity).execute();
    }

    public static List<HeldInventarEntity> findByHeldid(Long heldid) {
        return SelectQuery.Builder.selectAll(HeldInventarMapper. INSTANCE)
                .where(HeldInventarMapper.HELDID, heldid)
                .build().execute();
    }

    public static void delete(Long id) {
        DeleteQuery.Builder.delete(HeldInventarMapper.INSTANCE)
                .where(HeldInventarMapper.ID, id)
                .build().execute();
    }
}
