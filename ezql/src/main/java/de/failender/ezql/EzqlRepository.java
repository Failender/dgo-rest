package de.failender.ezql;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.util.List;

public abstract class EzqlRepository <ENTITY>{

    protected abstract EntityMapper<ENTITY> getMapper();

    public <FIELD> List<ENTITY> findBy(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field) {
        return SelectQuery.Builder.selectAll(getMapper())
                .where(fieldMapper, field)
                .build()
                .execute();
    }

    public <FIELD> ENTITY findOneBy(FieldMapper<ENTITY, FIELD> fieldMapper, FIELD field) {
        return firstOrNull(SelectQuery.Builder.selectAll(getMapper())
                .where(fieldMapper, field)
                .limit(1)
                .build()
                .execute());
    }

    public void persist(ENTITY entity) {
        new InsertQuery<>(getMapper(), entity).execute();
    }


    public static <T> T firstOrNull(List<T> list) {
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
