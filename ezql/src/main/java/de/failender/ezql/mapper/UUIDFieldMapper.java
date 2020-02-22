package de.failender.ezql.mapper;

import de.failender.ezql.util.TriConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class UUIDFieldMapper<ENTITY> extends FieldMapper<ENTITY, UUID>{

	public UUIDFieldMapper(String field, BiConsumer<ENTITY, UUID> setter, Function<ENTITY, UUID> getter) {
        super(field, convertedSetter(setter, field), getter);

	}

	private static final <ENTITY> TriConsumer<ENTITY, ResultSet, String> convertedSetter(BiConsumer<ENTITY, UUID> original, String baseField) {
		return (ENTITY entity, ResultSet rs, String prefix)  -> {
			try {
				String field = getField(baseField, prefix);
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

	@Override
    protected String converter(UUID value) {
        return escape(String.valueOf(value));
	}
}

