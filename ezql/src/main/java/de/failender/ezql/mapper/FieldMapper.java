package de.failender.ezql.mapper;

import de.failender.ezql.util.TriConsumer;

import java.sql.ResultSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class FieldMapper<ENTITY, FIELD> {

	private final String field;


	private final TriConsumer<ENTITY, ResultSet, String> setter;
	private final Function<ENTITY, FIELD> getter;

	public FieldMapper(String field, TriConsumer<ENTITY, ResultSet, String> setter, Function<ENTITY, FIELD> getter) {
		this.field = field;
		this.setter = setter;
		this.getter = getter;
	}

	public String getField() {
		return field;
	}

	public void setValue(ENTITY entity, ResultSet resultSet, String prefix) {
			setter.accept(entity, resultSet, prefix
			);
	}

	protected abstract String converter(FIELD value);

	public static String getField(String baseField, String prefix) {
		if(prefix == null) {
			return baseField;
		}
		return prefix + "_" + baseField;
	}


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
