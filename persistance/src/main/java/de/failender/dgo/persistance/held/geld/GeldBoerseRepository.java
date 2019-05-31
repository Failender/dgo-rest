package de.failender.dgo.persistance.held.geld;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

public class GeldBoerseRepository extends EzqlRepository<GeldBoerseEntity> {
    @Override
    protected EntityMapper<GeldBoerseEntity> getMapper() {
        return GeldBoerseMapper.INSTANCE;
    }
}
