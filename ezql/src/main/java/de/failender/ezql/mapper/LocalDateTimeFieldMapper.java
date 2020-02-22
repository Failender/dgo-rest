package de.failender.ezql.mapper;

import de.failender.ezql.util.TriConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class LocalDateTimeFieldMapper<ENTITY> extends FieldMapper<ENTITY, LocalDateTime>{

	public LocalDateTimeFieldMapper(String field, BiConsumer<ENTITY, LocalDateTime> setter, Function<ENTITY, LocalDateTime> getter) {
        super(field, convertedSetter(setter, field), getter);

	}

	private static final <ENTITY> TriConsumer<ENTITY, ResultSet, String> convertedSetter(BiConsumer<ENTITY, LocalDateTime> original, String baseField) {
		return (ENTITY entity, ResultSet rs, String prefix)  -> {
			try {
				String field = getField(baseField, prefix);
				Timestamp ts = rs.getTimestamp(field);
				if(ts != null) {
					original.accept(entity,ts.toLocalDateTime());

				}
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
