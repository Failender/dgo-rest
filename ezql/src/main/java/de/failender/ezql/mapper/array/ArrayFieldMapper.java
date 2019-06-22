package de.failender.ezql.mapper.array;

import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.converter.FieldConverter;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ArrayFieldMapper<ENTITY, FIELD> extends FieldMapper<ENTITY, List<FIELD>> implements FieldConverter<FIELD> {


    public ArrayFieldMapper(String field, BiConsumer<ENTITY, ResultSet> setter, Function<ENTITY, List<FIELD>> originalGetter) {
        super(field, setter, originalGetter);
    }

    protected static Stream<String> splitter(String sql) {
        return Stream.of(sql.substring(1, sql.length() - 1).split(","));
    }

    @Override
    protected String converter(List<FIELD> value) {
        String body = value
                .stream()
                .map(this::convertEntry)
                .collect(Collectors.joining(", "));
        return "'{" + body + "}'";
    }


}
