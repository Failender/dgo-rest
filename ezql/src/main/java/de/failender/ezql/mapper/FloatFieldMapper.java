package de.failender.ezql.mapper;

import de.failender.ezql.util.TriConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class FloatFieldMapper<ENTITY> extends FieldMapper<ENTITY, Float>{

	public FloatFieldMapper(String field, BiConsumer<ENTITY, Float> setter, Function<ENTITY, Float> getter) {
        super(field, convertedSetter(setter, field), getter);

	}

	private static final <ENTITY> TriConsumer<ENTITY, ResultSet, String> convertedSetter(BiConsumer<ENTITY, Float> original, String baseField) {
		return (ENTITY entity, ResultSet rs, String prefix)  -> {
			try {
				String field = getField(baseField, prefix);
				if(rs.getObject(field) == null) {
					return;
				}
				original.accept(entity, rs.getFloat(field));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}

	@Override
    protected String converter(Float value) {
        return String.valueOf(value);
	}
}
