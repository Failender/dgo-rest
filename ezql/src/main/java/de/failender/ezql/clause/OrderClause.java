package de.failender.ezql.clause;

import de.failender.ezql.mapper.FieldMapper;

public class OrderClause<FIELD> {



	private final ORDER order;
	private final FieldMapper<?, FIELD> field;


	public OrderClause(ORDER order, FieldMapper<?, FIELD> field) {
		this.order = order;
		this.field = field;
	}

	@Override
	public String toString() {
		return  field.getField() + " " + order;
	}

	public static enum ORDER {
		ASC, DESC
	}
}
