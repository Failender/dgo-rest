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

		String sqlValue = field.toSqlValue(value);
		if(sqlValue == null) {
			return field.getField() + " IS NULL";
		}
		return field.getField() + " = " + field.toSqlValue(value);
	}
}
