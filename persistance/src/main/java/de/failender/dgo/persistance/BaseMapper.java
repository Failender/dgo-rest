package de.failender.dgo.persistance;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.LongFieldMapper;

public abstract class BaseMapper <ENTITY extends BaseEntity> extends EntityMapper<ENTITY> {

    public final LongFieldMapper<ENTITY> ID = new LongFieldMapper<>("ID", BaseEntity::setId, BaseEntity::getId);

    @Override
    public LongFieldMapper<ENTITY> idField() {
        return ID;
    }
}
