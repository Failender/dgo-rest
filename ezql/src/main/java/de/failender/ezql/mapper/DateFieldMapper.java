package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DateFieldMapper<ENTITY> extends FieldMapper<ENTITY, Date>{

	public DateFieldMapper(String field, BiConsumer<ENTITY, Date> setter, Function<ENTITY, Date> getter) {
		super(field, convertedSetter(setter, field), convertedGetter(getter), getter);

	}

	private static final <ENTITY> BiConsumer<ENTITY, ResultSet> convertedSetter(BiConsumer<ENTITY, Date> original, String field) {
		return (ENTITY entity, ResultSet rs)  -> {
			try {
				original.accept(entity, rs.getDate(field));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}

	private static final <ENTITY> Function<ENTITY, String> convertedGetter(Function<ENTITY, Date> original) {
		return (ENTITY entity) -> String.valueOf(original.apply(entity));
	}

	@Override
	protected Function converter() {
		return value -> String.valueOf(value);
	}
}
