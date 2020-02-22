package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

class GegenstandToLagerortRepository extends EzqlRepository<GegenstandToLagerortEntity> {

    static final GegenstandToLagerortRepository INSTANCE = new GegenstandToLagerortRepository();
    @Override
    protected EntityMapper<GegenstandToLagerortEntity> getMapper() {
        return GegenstandtoLagerortMapper.INSTANCE;
    }
}
