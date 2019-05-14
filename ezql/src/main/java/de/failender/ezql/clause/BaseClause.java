package de.failender.ezql.clause;

import de.failender.ezql.mapper.FieldMapper;

public class BaseClause<ENTITY, FIELD> implements Clause{

	private final FieldMapper<ENTITY, FIELD> field;
	private final FIELD value;

	public BaseClause(FieldMapper<ENTITY, FIELD> field, FIELD value) {
		this.field = field;
		this.value = value;
	}

	@Override
	public String toString() {

		return field.getField() + " = " + field.toSqlValue(value);
	}
}
