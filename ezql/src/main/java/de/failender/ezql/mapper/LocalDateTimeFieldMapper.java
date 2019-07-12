package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class LocalDateTimeFieldMapper<ENTITY> extends FieldMapper<ENTITY, LocalDateTime>{

	public LocalDateTimeFieldMapper(String field, BiConsumer<ENTITY, LocalDateTime> setter, Function<ENTITY, LocalDateTime> getter) {
        super(field, convertedSetter(setter, field), getter);

	}

	private static final <ENTITY> BiConsumer<ENTITY, ResultSet> convertedSetter(BiConsumer<ENTITY, LocalDateTime> original, String field) {
		return (ENTITY entity, ResultSet rs)  -> {
			try {
				original.accept(entity,rs.getTimestamp(field).toLocalDateTime());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}

	@Override
    protected String converter(LocalDateTime value) {
		if(value == null) {
			return "null";
		}
        return escape(String.valueOf(value));
	}
}
