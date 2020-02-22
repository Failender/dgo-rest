package de.failender.ezql.mapper.converter;

import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.util.TriConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;

public interface IntFieldConverter extends FieldConverter<Integer> {

    static <ENTITY> TriConsumer<ENTITY, ResultSet, String> convertedSetter(BiConsumer<ENTITY, Integer> original, String baseField) {
        return (ENTITY entity, ResultSet rs, String prefix) -> {
            try {
                String field = FieldMapper.getField(baseField, prefix);
                if (rs.getObject(field) == null) {
                    return;
                }
                original.accept(entity, rs.getInt(field));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }


    default String convertEntry(Integer value) {
        return String.valueOf(value);
    }
}
