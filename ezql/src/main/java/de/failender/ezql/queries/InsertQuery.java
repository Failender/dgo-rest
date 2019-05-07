package de.failender.ezql.queries;

import de.failender.ezql.EzqlConnector;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class InsertQuery<ENTITY> {

    private final EntityMapper<ENTITY> mapper;
    private final ENTITY entity;

    public InsertQuery(EntityMapper<ENTITY> mapper, ENTITY entity) {
        this.mapper = mapper;
        this.entity = entity;
    }

    public void execute() {
        String columnDefinitions = mapper.fieldMappers()
                .stream()
                .filter(mapper -> mapper != this.mapper.idField())
                .map(FieldMapper::getField)
                .collect(Collectors.joining(", "));

        String columnValues = mapper.fieldMappers()
                .stream()
                .filter(mapper -> mapper != this.mapper.idField())
                .map(fieldMapper -> fieldMapper.toSqlValueFromEntity(entity))
                .collect(Collectors.joining(", "));
        String sql = "INSERT INTO " + mapper.table() + " (" + columnDefinitions + ") VALUES (" + columnValues + ") RETURNING " + mapper.idField().getField() + ";";

        try(Statement statement = EzqlConnector.getConnection().createStatement()) {

            System.out.println(sql);

           ResultSet rs  =  statement.executeQuery(sql);
           rs.next();
           int id = rs.getInt(1);
           this.mapper.idField().getOriginalSetter().accept(this.entity, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
