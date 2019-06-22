package de.failender.ezql.mapper.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;

public interface IntFieldConverter extends FieldConverter<Integer> {

    static <ENTITY> BiConsumer<ENTITY, ResultSet> convertedSetter(BiConsumer<ENTITY, Integer> original, String field) {
        return (ENTITY entity, ResultSet rs) -> {
            try {
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
