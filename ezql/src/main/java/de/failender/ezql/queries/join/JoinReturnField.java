package de.failender.ezql.queries.join;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;

import java.util.List;
import java.util.function.BiConsumer;

public class JoinReturnField<RESULT, ENTITY>{

    private final EntityMapper<ENTITY> entityMapper;
    private final List<FieldMapper<ENTITY, ?>> fields;
    private final BiConsumer<RESULT, ENTITY> setter;

    public JoinReturnField(EntityMapper<ENTITY> entityMapper, List<FieldMapper<ENTITY, ?>> fields, BiConsumer<RESULT, ENTITY> setter) {
        this.entityMapper = entityMapper;
        this.fields = fields;
        this.setter = setter;
    }

    public EntityMapper<ENTITY> getEntityMapper() {
        return entityMapper;
    }


    public List<FieldMapper<ENTITY, ?>> getFields() {
        return fields;
    }

    public BiConsumer<RESULT, ENTITY> getSetter() {
        return setter;
    }
}
