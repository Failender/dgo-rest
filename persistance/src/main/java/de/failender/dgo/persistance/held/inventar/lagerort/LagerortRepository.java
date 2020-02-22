package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

class LagerortRepository extends EzqlRepository<LagerortEntity> {

    public static final LagerortRepository INSTANCE = new LagerortRepository();
    @Override
    protected EntityMapper<LagerortEntity> getMapper() {
        return LagerortMapper.INSTANCE;
    }
}
