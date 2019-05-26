package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class FloatFieldMapper<ENTITY> extends FieldMapper<ENTITY, Float>{

	public FloatFieldMapper(String field, BiConsumer<ENTITY, Float> setter, Function<ENTITY, Float> getter) {
		super(field, convertedSetter(setter, field), convertedGetter(getter), getter);

	}

	private static final <ENTITY> BiConsumer<ENTITY, ResultSet> convertedSetter(BiConsumer<ENTITY, Float> original, String field) {
		return (ENTITY entity, ResultSet rs)  -> {
			try {
				if(rs.getObject(field) == null) {
					return;
				}
				original.accept(entity, rs.getFloat(field));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}

	private static final <ENTITY> Function<ENTITY, String> convertedGetter(Function<ENTITY, Float> original) {
		return (ENTITY entity) -> String.valueOf(original.apply(entity));
	}
	@Override
	protected Function converter() {
		return value -> String.valueOf(value);
	}
}
