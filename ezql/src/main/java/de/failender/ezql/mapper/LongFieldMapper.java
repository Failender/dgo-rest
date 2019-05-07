package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class LongFieldMapper<ENTITY> extends FieldMapper<ENTITY, Long>{

	private final BiConsumer<ENTITY, Long> originalSetter;

	public LongFieldMapper(String field, BiConsumer<ENTITY, Long> setter, Function<ENTITY, Long> getter) {
		super(field, convertedSetter(setter, field), convertedGetter(getter), getter);
		this.originalSetter = setter;

	}

	private static final <ENTITY> BiConsumer<ENTITY, ResultSet> convertedSetter(BiConsumer<ENTITY, Long> original, String field) {
		return (ENTITY entity, ResultSet rs)  -> {
			try {
				original.accept(entity, rs.getLong(field));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}

	private static final <ENTITY> Function<ENTITY, String> convertedGetter(Function<ENTITY, Long> original) {
		return (ENTITY entity) -> String.valueOf(original.apply(entity));
	}

	public BiConsumer<ENTITY, Long> getOriginalSetter() {
		return originalSetter;
	}

	@Override
	protected Function converter() {
		return value -> String.valueOf(value);
	}
}
