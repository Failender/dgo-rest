package de.failender.ezql.queries.join;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;

public class JoinCondition <ENTITY>{
    private final EntityMapper<ENTITY> entityMapper;
    private final FieldMapper<ENTITY, ?> field;


    public JoinCondition(EntityMapper<ENTITY> entityMapper, FieldMapper<ENTITY, ?> field) {
        this.entityMapper = entityMapper;
        this.field = field;
    }

    public EntityMapper<ENTITY> getEntityMapper() {
        return entityMapper;
    }

    public FieldMapper<ENTITY, ?> getField() {
        return field;
    }
}
