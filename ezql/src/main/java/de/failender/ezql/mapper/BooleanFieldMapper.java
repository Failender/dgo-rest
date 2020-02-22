package de.failender.ezql.mapper;

import de.failender.ezql.util.TriConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BooleanFieldMapper<ENTITY> extends FieldMapper<ENTITY, Boolean> {
    public BooleanFieldMapper(String field, BiConsumer<ENTITY, Boolean> setter, Function<ENTITY, Boolean> getter) {
        super(field, convertedSetter(setter, field), getter);


    }

    private static final <ENTITY> TriConsumer<ENTITY, ResultSet, String> convertedSetter(BiConsumer<ENTITY, Boolean> original, String baseField) {
        return (ENTITY entity, ResultSet rs, String prefix)  -> {
            try {
                String field = getField(baseField, prefix);
                original.accept(entity, rs.getBoolean(field));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }


    @Override
    protected String converter(Boolean value) {
        return String.valueOf(value);
    }
}
