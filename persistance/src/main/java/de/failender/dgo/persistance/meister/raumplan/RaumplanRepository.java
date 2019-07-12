package de.failender.dgo.persistance.meister.raumplan;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

import java.util.List;

class RaumplanRepository extends EzqlRepository {

    public static final RaumplanRepository INSTANCE = new RaumplanRepository();

    @Override
    protected EntityMapper getMapper() {
        return RaumplanMapper.INSTANCE;
    }

    public List<RaumplanEntity> findByUserId(Long userId) {
        return findBy(RaumplanMapper.OWNER, userId);
    }
}
