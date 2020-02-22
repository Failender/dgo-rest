package de.failender.ezql.queries.join;

import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.mapper.EntityMapper;

public class JoinWhere <ENTITY>{
    private final EntityMapper<ENTITY> entityMapper;
    private final BaseClause<ENTITY, ?> clause;


    public JoinWhere(EntityMapper<ENTITY> entityMapper, BaseClause<ENTITY, ?> clause) {
        this.entityMapper = entityMapper;
        this.clause = clause;
    }

    public EntityMapper<ENTITY> getEntityMapper() {
        return entityMapper;
    }

    public BaseClause<ENTITY, ?> getClause() {
        return clause;
    }
}
