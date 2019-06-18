package de.failender.ezql.repository;

import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.clause.Clause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;
import de.failender.ezql.queries.UpdateQuery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class EzqlRepository <ENTITY>{

    protected abstract EntityMapper<ENTITY> getMapper();

    public ENTITY findById(Long id) {
        return findOneBy(getMapper().idField(), id);
    }

    protected <FIELD> List<ENTITY> findBy(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field) {
        return SelectQuery.Builder.selectAll(getMapper())
                .where(fieldMapper, field)
                .build()
                .execute();
    }

    protected  <FIELD> ENTITY findOneBy(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field) {
        return firstOrNull(SelectQuery.Builder.selectAll(getMapper())
                .where(fieldMapper, field)
                .limit(1)
                .build()
                .execute());
    }

    protected <FIELD> ENTITY findOneBy(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field, FieldMapper<ENTITY, ?>... fields) {
        return firstOrNull(SelectQuery.Builder.select(getMapper(), fields)
                .where(fieldMapper, field)
                .limit(1)
                .build()
                .execute());
    }

    protected void updateById(Long id, List<BaseClause<ENTITY, ?>> updateClauses) {
        List<Clause> whereClauses = Arrays.asList(new BaseClause<>(getMapper().idField(), id));
        update(whereClauses, updateClauses);
    }

    public void update(List<Clause> whereClauses, List<BaseClause<ENTITY, ?>> updateClauses) {
        UpdateQuery.Builder.update(getMapper());
        new UpdateQuery<>(getMapper(), updateClauses, whereClauses)
                .execute();
    }

    public void update(Clause whereClauses, BaseClause<ENTITY, ?> updateClause) {
        update(Collections.singletonList(whereClauses), Collections.singletonList(updateClause));
    }

    public void persist(ENTITY entity) {
        new InsertQuery<>(getMapper(), entity).execute();
    }

    public void persist(ENTITY entity, boolean fixedId) {
        new InsertQuery<>(getMapper(), entity, fixedId).execute();
    }


    public static <T> T firstOrNull(List<T> list) {
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
