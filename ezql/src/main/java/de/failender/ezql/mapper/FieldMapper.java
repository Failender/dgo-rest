package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class FieldMapper<ENTITY, FIELD> {

	private final String field;


	private final BiConsumer<ENTITY, ResultSet> setter;
	private final Function<ENTITY, String> getter;
	private final Function<ENTITY, FIELD> originalGetter;

	public FieldMapper(String field, BiConsumer<ENTITY, ResultSet> setter, Function<ENTITY, String> getter, Function<ENTITY, FIELD> originalGetter) {
		this.field = field;
		this.getter = getter;
		this.setter = setter;
		this.originalGetter = originalGetter;
	}

	public String getField() {
		return field;
	}


	public Function<ENTITY, String> getGetter() {
		return getter;
	}

	public void setValue(ENTITY entity, ResultSet resultSet) {
			setter.accept(entity, resultSet);
	}

	protected abstract Function<FIELD, String> converter();

	public String toSqlValue(FIELD value) {
		return converter().apply(value);
	}

	public String toSqlValueFromEntity(ENTITY entity) {
		return converter().apply(originalGetter.apply(entity));
	}

	public Function<ENTITY, FIELD> getOriginalGetter() {
		return originalGetter;
	}
}
