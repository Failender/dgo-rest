package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class UUIDFieldMapper<ENTITY> extends FieldMapper<ENTITY, UUID>{

	public UUIDFieldMapper(String field, BiConsumer<ENTITY, UUID> setter, Function<ENTITY, UUID> getter) {
		super(field, convertedSetter(setter, field), convertedGetter(getter), getter);

	}

	private static final <ENTITY> BiConsumer<ENTITY, ResultSet> convertedSetter(BiConsumer<ENTITY, UUID> original, String field) {
		return (ENTITY entity, ResultSet rs)  -> {
			try {
				String value = rs.getString(field);
				if(value == null) {
					return;
				}
				original.accept(entity, UUID.fromString(value));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}

	private static final <ENTITY> Function<ENTITY, String> convertedGetter(Function<ENTITY, UUID> original) {
		return (ENTITY entity) -> String.valueOf(original.apply(entity));
	}

	@Override
	protected Function converter() {
		return value -> escape(String.valueOf(value));
	}
}

