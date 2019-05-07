package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BooleanFieldMapper<ENTITY> extends FieldMapper<ENTITY, Boolean> {
    public BooleanFieldMapper(String field, BiConsumer<ENTITY, Boolean> setter, Function<ENTITY, Boolean> getter) {
        super(field, convertedSetter(setter, field), convertedGetter(getter), getter);


    }

    private static final <ENTITY> BiConsumer<ENTITY, ResultSet> convertedSetter(BiConsumer<ENTITY, Boolean> original, String field) {
        return (ENTITY entity, ResultSet rs)  -> {
            try {
                original.accept(entity, rs.getBoolean(field));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }

    private static final <ENTITY> Function<ENTITY, String> convertedGetter(Function<ENTITY, Boolean> original) {
        return (ENTITY entity) -> String.valueOf(original.apply(entity));
    }


    @Override
    protected Function converter() {
        return value -> String.valueOf(value);
    }
}
