package de.failender.dgo.persistance.held.inventar;

import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.repository.EzqlRepository;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.DeleteQuery;

import java.util.Collections;
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

    public void updateAnzahl(Long id, int anzahl) {
        update(new BaseClause<>(HeldInventarMapper.ID, id), new BaseClause<>(HeldInventarMapper.ANZAHL, anzahl));
    }

}
