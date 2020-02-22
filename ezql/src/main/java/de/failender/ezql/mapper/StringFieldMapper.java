package de.failender.ezql.mapper;

import de.failender.ezql.util.TriConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StringFieldMapper<ENTITY> extends FieldMapper<ENTITY, String> {
	public StringFieldMapper(String field, BiConsumer<ENTITY, String> setter, Function<ENTITY, String> getter) {
        super(field, convertedSetter(setter, field), getter);
	}

	private static final <ENTITY> TriConsumer<ENTITY, ResultSet, String> convertedSetter(BiConsumer<ENTITY, String> original, String baseField) {
		return (ENTITY entity, ResultSet rs, String prefix)  -> {
			try {
				String field = getField(baseField, prefix);
				original.accept(entity, rs.getString(field));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}

	@Override
    protected String converter(String value) {
        return this.escape(value);
	}
}
