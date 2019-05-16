package de.failender.ezql.queries;


import de.failender.ezql.EzqlConnector;
import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.clause.Clause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeleteQuery<ENTITY> extends BaseQuery {


    private final EntityMapper<ENTITY> mapper;

    protected DeleteQuery(EntityMapper<ENTITY> mapper, List<Clause> whereClauses) {
        super(whereClauses);
        this.mapper = mapper;
    }

    public void execute() {
        String sql = "DELETE FROM " + mapper.table() + " ";
        sql = appendWhereClauses(sql);

        EzqlConnector.execute(sql);
    }

    public static class Builder<ENTITY> {

        private final EntityMapper<ENTITY> mapper;

        private List<Clause> whereClauses = new ArrayList<>();


        private Builder(EntityMapper<ENTITY> mapper) {
            this.mapper = mapper;
        }

        public static <ENTITY> DeleteQuery.Builder<ENTITY> delete(EntityMapper<ENTITY> entityMapper) {
            return new Builder<>(entityMapper);
        }


        public DeleteQuery<ENTITY> build() {
            return new DeleteQuery<>(mapper, whereClauses);
        }


        public <VALUE> DeleteQuery.Builder<ENTITY> where(FieldMapper<ENTITY, VALUE> field, VALUE value) {
            whereClauses.add(new BaseClause(field, value));
            return this;
        }


    }
}
