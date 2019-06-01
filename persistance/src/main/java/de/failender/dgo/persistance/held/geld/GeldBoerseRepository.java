package de.failender.dgo.persistance.held.geld;

import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

import java.util.ArrayList;
import java.util.List;

public class GeldBoerseRepository extends EzqlRepository<GeldBoerseEntity> {

    public static final GeldBoerseRepository INSTANCE = new GeldBoerseRepository();

    private GeldBoerseRepository() {
    }
    @Override
    protected EntityMapper<GeldBoerseEntity> getMapper() {
        return GeldBoerseMapper.INSTANCE;
    }

    public void updateAnzahl(GeldBoerseEntity geldBoerseEntity) {
        List<BaseClause<GeldBoerseEntity, ?>> updateClauses = new ArrayList<>();
        updateClauses.add(new BaseClause<>(GeldBoerseMapper.ANZAHL, geldBoerseEntity.getAnzahl()));
        updateById(geldBoerseEntity.getId(), updateClauses);
    }

    public GeldBoerseEntity findByHeldid(Long heldid) {
        return findOneBy(GeldBoerseMapper.HELD_ID, heldid);
    }
}
