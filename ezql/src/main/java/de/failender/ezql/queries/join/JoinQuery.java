package de.failender.ezql.queries.join;

import de.failender.ezql.EzqlConnector;
import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JoinQuery<RESULT> {


    private final EntityMapper baseTable;
    private final List<JoinField> joins;
    private final List<JoinReturnField<RESULT, ?>> fields;
    private final List<JoinWhere> conditions;
    private final Supplier<RESULT> resultSupplier;


    JoinQuery(EntityMapper baseTable, List<JoinField> joins, List<JoinReturnField<RESULT, ?>> fields, List<JoinWhere> conditions, Supplier<RESULT> resultSupplier) {
        this.baseTable = baseTable;
        this.joins = joins;
        this.fields = fields;
        this.conditions = conditions;
        this.resultSupplier = resultSupplier;
    }

    public List<RESULT> query() {
        String sql = "SELECT ";
        boolean first = true;
        for (JoinReturnField<RESULT, ?> field : fields) {

            if(field.getFields() == null) {
                sql  += field.getEntityMapper().table() + ".* AS " + field.getEntityMapper().table() + " ";

                continue;
            }
            for (FieldMapper<?, ?> fieldField : field.getFields()) {

                if(!first) {
                    sql += ",";
                }
                first = false;

                sql  += field.getEntityMapper().table() + "." + fieldField.getField();
                sql += " AS " + field.getEntityMapper().table() + "_" + fieldField.getField() + " ";
            }

        }
        sql += " FROM " + baseTable.table() + " ";

        for (JoinField join : joins) {
            sql += join.getJoinType().toString() + " JOIN ";
            sql += join.getTarget().getEntityMapper().table() + " ON ";
            sql += join.getTarget().getEntityMapper().table() + "." + join.getTarget().getField().getField() + "=";
            sql += join.getSource().getEntityMapper().table() + "." + join.getSource().getField().getField();
        }

        if(!conditions.isEmpty()) {
            sql += " WHERE ";

            sql += conditions
                    .stream()
                    .map(clause -> {
                        return clause.getEntityMapper().table() + "." + clause.getClause().toString();
                    })
                    .collect(Collectors.joining(" AND "));
        }
        sql += ";";

        System.out.println(sql);
        List<RESULT> resultList;
        try (Statement statement = EzqlConnector.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
             resultList = new ArrayList<>(rs.getFetchSize());
            while(rs.next()) {

                RESULT result = resultSupplier.get();
                resultList.add(result);
                for (JoinReturnField<RESULT, ?> field : this.fields) {
                    fillResult(field, rs, result);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return resultList;

    }

    private <T> void fillResult(JoinReturnField<RESULT, T> field, ResultSet rs, RESULT result) {
        T entity  = field.getEntityMapper().create();
        if(field.getFields() == null) {

            for (FieldMapper<T, ?> fieldMapper : field.getEntityMapper().fieldMappers()) {
                fieldMapper.setValue(entity, rs, field.getEntityMapper().table());

            }
            return;
        }
        for (FieldMapper<T, ?> fieldField : field.getFields()) {
            fieldField.setValue(entity, rs, field.getEntityMapper().table());
        }
        field.getSetter().accept(result, entity);
    }

    public static class Builder<RESULT> {

        private final EntityMapper baseTable;
        private final Supplier<RESULT> resultSupplier;
        private final List<JoinField> joins = new ArrayList<>();
        private final List<JoinReturnField<RESULT, ?>> fields = new ArrayList<>();
        private final List<JoinWhere> conditions = new ArrayList<>();


        Builder(EntityMapper baseTable, Supplier<RESULT> resultSupplier) {
            this.baseTable = baseTable;
            this.resultSupplier = resultSupplier;
        }

        public static <RESULT> Builder<RESULT> create(EntityMapper baseTable, Supplier<RESULT> resultSupplier) {
            return new Builder(baseTable, resultSupplier);
        }

        public <ENTITY, FIELD> Builder<RESULT> where(EntityMapper<ENTITY> mapper, FieldMapper<ENTITY, FIELD> field, FIELD value) {
            conditions.add(new JoinWhere(mapper, new BaseClause(field, value)));
            return this;
        }


        public <ENTITY>Builder<RESULT> returns(EntityMapper<ENTITY> mapper, BiConsumer<RESULT, ENTITY> setter, List<FieldMapper<ENTITY, ?>> fields) {
            this.fields.add(new JoinReturnField<>(mapper, fields, setter));
            return this;
        }

        public <ENTITY>Builder<RESULT> returnAll(EntityMapper<ENTITY> mapper, BiConsumer<RESULT, ENTITY> setter) {
            this.fields.add(new JoinReturnField<>(mapper, null, setter));
            return this;
        }

        public <SOURCE, TARGET>Builder<RESULT> join(JoinType joinType, EntityMapper<SOURCE> sourceMapper, FieldMapper<SOURCE, ?> sourceField,
                                            EntityMapper<TARGET> targetMapper, FieldMapper<TARGET, ?> targetField) {
            this.joins.add(new JoinField(new JoinCondition(sourceMapper, sourceField), new JoinCondition(targetMapper, targetField), joinType));
            return this;
        }

        public List<RESULT> execute() {
            return new JoinQuery(baseTable, joins, fields, conditions, resultSupplier).query();
        }
    }
}
