package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class IntFieldMapper<ENTITY> extends FieldMapper<ENTITY, Integer>{

	private final BiConsumer<ENTITY, Integer> originalSetter;

	public IntFieldMapper(String field, BiConsumer<ENTITY, Integer> setter, Function<ENTITY, Integer> getter) {
		super(field, convertedSetter(setter, field), convertedGetter(getter), getter);
		this.originalSetter = setter;

	}

	private static final <ENTITY> BiConsumer<ENTITY, ResultSet> convertedSetter(BiConsumer<ENTITY, Integer> original, String field) {
		return (ENTITY entity, ResultSet rs)  -> {
			try {
				if(rs.getObject(field) == null) {
					return;
				}
				original.accept(entity, rs.getInt(field));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}

	private static final <ENTITY> Function<ENTITY, String> convertedGetter(Function<ENTITY, Integer> original) {
		return (ENTITY entity) -> String.valueOf(original.apply(entity));
	}

	public BiConsumer<ENTITY, Integer> getOriginalSetter() {
		return originalSetter;
	}

	@Override
	protected Function converter() {
		return value -> String.valueOf(value);
	}
}
