package de.failender.ezql.clause;

import de.failender.ezql.mapper.FieldMapper;

import java.util.List;
import java.util.stream.Collectors;

public class InClause<ENTITY, FIELD> implements Clause{


	private final FieldMapper<ENTITY, FIELD> field;
	private final List<FIELD> value;
	public InClause(FieldMapper<ENTITY, FIELD> field, List<FIELD> value) {
		this.field = field;
		this.value = value;
	}

	@Override
	public String toString() {

		String inPart = value.stream()
				.map(value -> field.toSqlValue(value))
				.collect(Collectors.joining(","));
		return field.getField() + " IN( " + inPart + ")";
	}
}
