package de.failender.ezql.repository;

import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.clause.Clause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.queries.DeleteQuery;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;
import de.failender.ezql.queries.UpdateQuery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class EzqlRepository <ENTITY>{

    protected abstract EntityMapper<ENTITY> getMapper();

    public Optional<ENTITY> findById(Long id) {
        return findOneBy(getMapper().idField(), id);
    }

    protected <FIELD> Field<FIELD> field(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field) {
        return new Field<>(fieldMapper, field);
    }

    protected List<ENTITY> findBy(Field... fields) {
        SelectQuery.Builder builder =  SelectQuery.Builder.selectAll(getMapper());
        for (Field field : fields) {
            builder.where(field.getFieldMapper(), field.getField());
        }
        return builder.execute();
    }

    protected <FIELD> List<ENTITY> findBy(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field) {
        return SelectQuery.Builder.selectAll(getMapper())
                .where(fieldMapper, field)
                .execute();
    }

    protected  <FIELD> Optional<ENTITY> findOneBy(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field) {
        return firstOrEmpty(SelectQuery.Builder.selectAll(getMapper())
                .where(fieldMapper, field)
                .limit(1)
                .execute());
    }

    protected Optional<ENTITY> findOneBy(Field... fields) {
        SelectQuery.Builder builder =  SelectQuery.Builder.selectAll(getMapper()).limit(1);
        for (Field field : fields) {
            builder.where(field.getFieldMapper(), field.getField());
        }
        List<ENTITY> list = builder.execute();
        if(list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    protected <FIELD> Optional<ENTITY> findOneBy(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field, FieldMapper<ENTITY, ?>... fields) {
        return firstOrEmpty(SelectQuery.Builder.select(getMapper(), fields)
                .where(fieldMapper, field)
                .limit(1)
                .execute());
    }

    public List<ENTITY> findAll() {
        return SelectQuery.Builder.selectAll(getMapper())
                .execute();
    }

    public List<ENTITY> findAll(FieldMapper<ENTITY, ?>... fields) {
        return SelectQuery.Builder.select(getMapper(), fields)
                .execute();
    }

    protected void updateById(Long id, List<BaseClause<ENTITY, ?>> updateClauses) {
        List<Clause> whereClauses = Arrays.asList(new BaseClause<>(getMapper().idField(), id));
        update(whereClauses, updateClauses);
    }

    protected void updateById(Long id, Field<?>... fields) {
        List<Clause> whereClauses = Arrays.asList(new BaseClause<>(getMapper().idField(), id));
        List<BaseClause<ENTITY, ?>> updateClauses = Stream.of(fields)
                .map(this::buildBaseClause)
                .collect(Collectors.toList());
        new UpdateQuery<>(getMapper(), updateClauses, whereClauses)
                .execute();
    }

    private <FIELD> BaseClause<ENTITY, ?> buildBaseClause(Field<FIELD> field) {
        return new BaseClause<ENTITY, FIELD>(field.getFieldMapper(), field.getField());
    }


    public void update(List<Clause> whereClauses, List<BaseClause<ENTITY, ?>> updateClauses) {
        new UpdateQuery<>(getMapper(), updateClauses, whereClauses)
                .execute();
    }

    public void update(Clause whereClauses, BaseClause<ENTITY, ?> updateClause) {
        update(Collections.singletonList(whereClauses), Collections.singletonList(updateClause));
    }

    public void deleteById(Long id) {
        DeleteQuery.Builder.delete(getMapper()).where(getMapper().idField(), id)
                .build().execute();
    }

    public void deleteBulkById(List<Long> ids) {
        DeleteQuery.Builder.delete(getMapper())
                .whereIn(getMapper().idField(), ids)
                .execute();
    }

    protected <FIELD> void deleteBy(FieldMapper<ENTITY, FIELD> field, FIELD value ) {
        DeleteQuery.Builder.delete(getMapper()).where(field, value)
                .build().execute();
    }
    protected void deleteBy(Field... fields) {
        DeleteQuery.Builder queryBuilder = DeleteQuery.Builder.delete(getMapper());
        for (Field field : fields) {
            queryBuilder.where(field.getFieldMapper(), field.getField());
        }
        queryBuilder.execute();
    }


    public void persist(ENTITY entity) {
        new InsertQuery<>(getMapper(), entity).execute();
    }

    public void persist(ENTITY entity, boolean fixedId) {
        new InsertQuery<>(getMapper(), entity, fixedId).execute();
    }


    public static <T> Optional<T> firstOrEmpty(List<T> list) {
        if(list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    protected class Field<FIELD> {
        private final FieldMapper<ENTITY, FIELD> fieldMapper;
         private final FIELD field;

        public Field(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field) {
            this.fieldMapper = fieldMapper;
            this.field = field;
        }

        public FIELD getField() {
            return field;
        }

        public FieldMapper<ENTITY, FIELD> getFieldMapper() {
            return fieldMapper;
        }
    }

}
