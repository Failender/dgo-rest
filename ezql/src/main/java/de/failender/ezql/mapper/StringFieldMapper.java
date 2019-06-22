package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StringFieldMapper<ENTITY> extends FieldMapper<ENTITY, String> {
	public StringFieldMapper(String field, BiConsumer<ENTITY, String> setter, Function<ENTITY, String> getter) {
        super(field, convertedSetter(setter, field), getter);
	}

	private static final <ENTITY> BiConsumer<ENTITY, ResultSet> convertedSetter(BiConsumer<ENTITY, String> original, String field) {
		return (ENTITY entity, ResultSet rs)  -> {
			try {

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
