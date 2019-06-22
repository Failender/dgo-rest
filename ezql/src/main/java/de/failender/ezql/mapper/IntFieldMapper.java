package de.failender.ezql.mapper;

import de.failender.ezql.mapper.converter.IntFieldConverter;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static de.failender.ezql.mapper.converter.IntFieldConverter.convertedSetter;

public class IntFieldMapper<ENTITY> extends FieldMapper<ENTITY, Integer> implements IntFieldConverter {

	public IntFieldMapper(String field, BiConsumer<ENTITY, Integer> setter, Function<ENTITY, Integer> getter) {
		super(field, convertedSetter(setter, field), getter);

	}

	@Override
	public String converter(Integer value) {
		return convertEntry(value);
	}
}
