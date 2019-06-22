package de.failender.ezql.mapper;

import java.sql.ResultSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class FieldMapper<ENTITY, FIELD> {

	private final String field;


	private final BiConsumer<ENTITY, ResultSet> setter;
	private final Function<ENTITY, FIELD> getter;

	public FieldMapper(String field, BiConsumer<ENTITY, ResultSet> setter, Function<ENTITY, FIELD> getter) {
		this.field = field;
		this.setter = setter;
		this.getter = getter;
	}

	public String getField() {
		return field;
	}

	public void setValue(ENTITY entity, ResultSet resultSet) {
			setter.accept(entity, resultSet);
	}

	protected abstract String converter(FIELD value);


	public String toSqlValue(FIELD value) {
		return converter(value);
	}

	public String toSqlValueFromEntity(ENTITY entity) {
		return converter(getter.apply(entity));
	}

	protected String escape(String string) {
		return "'" + string + "'";
	}
}
