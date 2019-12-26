package de.failender.ezql.queries;

import de.failender.ezql.EzqlConnector;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQuery<ENTITY> {

    private final EntityMapper<ENTITY> mapper;
    private final ENTITY entity;
    private final boolean fixedId;

    public InsertQuery(EntityMapper<ENTITY> mapper, ENTITY entity) {
        this(mapper, entity, false);
    }

    public InsertQuery(EntityMapper<ENTITY> mapper, ENTITY entity, boolean fixedId) {
        this.mapper = mapper;
        this.entity = entity;
        this.fixedId = fixedId;
    }

    public void execute() {
        List<FieldMapper<ENTITY, ?>> mappers;
        if(fixedId) {
            mappers = mapper.fieldMappers();
        } else {
            mappers = mapper.fieldMappers()
                    .stream()
                    .filter(mapper -> mapper != this.mapper.idField())
                    .collect(Collectors.toList());
        }
        String columnDefinitions = mappers.stream()
                .map(FieldMapper::getField)
                .collect(Collectors.joining(", "));

        String columnValues = mappers.stream()
                .map(fieldMapper -> fieldMapper.toSqlValueFromEntity(entity))
                .collect(Collectors.joining(", "));
        String sql = "INSERT INTO " + mapper.table() + " (" + columnDefinitions + ") VALUES (" + columnValues + ") RETURNING " + mapper.idField().getField() + ";";

        try (Statement statement = EzqlConnector.createStatement()) {

            System.out.println(sql);

           ResultSet rs  =  statement.executeQuery(sql);
           rs.next();
           long id = rs.getLong(1);
           this.mapper.idField().getOriginalSetter().accept(this.entity, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
