package de.failender.ezql.mapper;

import de.failender.ezql.util.TriConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class LongFieldMapper<ENTITY> extends FieldMapper<ENTITY, Long>{

	private final BiConsumer<ENTITY, Long> originalSetter;

	public LongFieldMapper(String field, BiConsumer<ENTITY, Long> setter, Function<ENTITY, Long> getter) {
        super(field, convertedSetter(setter, field), getter);
		this.originalSetter = setter;

	}

	private static final <ENTITY> TriConsumer<ENTITY, ResultSet, String> convertedSetter(BiConsumer<ENTITY, Long> original, String baseField) {
		return (ENTITY entity, ResultSet rs, String prefix)  -> {
			try {
				String field = getField(baseField, prefix);
				if(rs.getObject(field) == null) {
					return;
				}
				original.accept(entity, rs.getLong(field));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		};
	}

	public BiConsumer<ENTITY, Long> getOriginalSetter() {
		return originalSetter;
	}

	@Override
    protected String converter(Long value) {
        return String.valueOf(value);
	}
}
