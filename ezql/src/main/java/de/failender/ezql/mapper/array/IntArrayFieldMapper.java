package de.failender.ezql.mapper.array;

import de.failender.ezql.mapper.converter.IntFieldConverter;
import de.failender.ezql.util.TriConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IntArrayFieldMapper<ENTITY> extends ArrayFieldMapper<ENTITY, Integer> implements IntFieldConverter {
    public IntArrayFieldMapper(String field, BiConsumer<ENTITY, List<Integer>> setter, Function<ENTITY, List<Integer>> originalGetter) {
        super(field, convertedSetter(setter, field), originalGetter);
    }

    static <ENTITY> TriConsumer<ENTITY, ResultSet, String> convertedSetter(BiConsumer<ENTITY, List<Integer>> original, String baseField) {
        return (ENTITY entity, ResultSet rs, String prefix) -> {
            try {
                String field = getField(baseField, prefix);
                if (rs.getObject(field) == null) {
                    return;
                }
                String value = rs.getString(field);
                List<Integer> values = splitter(value)
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
                original.accept(entity, values);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }


}
