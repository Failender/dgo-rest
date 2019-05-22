package de.failender.dgo.persistance.held.inventar;

import com.sun.org.apache.bcel.internal.generic.Select;
import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.ezql.EzqlRepository;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.DeleteQuery;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import javax.swing.text.DefaultEditorKit;
import java.util.List;

class HeldInventarRepository extends EzqlRepository<HeldInventarEntity> {

    @Override
    protected EntityMapper<HeldInventarEntity> getMapper() {
        return HeldInventarMapper.INSTANCE;
    }

    public static final HeldInventarRepository INSTANCE = new HeldInventarRepository();


    public List<HeldInventarEntity> findByHeldid(Long heldid) {
        return findBy(HeldInventarMapper.HELDID, heldid);
    }

    public HeldInventarEntity findById(Long id) {
        return findOneBy(HeldInventarMapper.ID, id);
    }

    public static void delete(Long id) {
        DeleteQuery.Builder.delete(HeldInventarMapper.INSTANCE)
                .where(HeldInventarMapper.ID, id)
                .build().execute();
    }
}
