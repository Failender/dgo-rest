package de.failender.ezql.queries;

import de.failender.ezql.clause.BaseClause;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseQuery<ENTITY> {

	private final List<BaseClause<ENTITY, ?>> whereClauses;

	protected BaseQuery(List<BaseClause<ENTITY, ?>> whereClauses) {
		this.whereClauses = whereClauses;
	}

	protected String appendWhereClauses(String sql) {
		if(whereClauses.isEmpty()) {
			return sql;
		}

		sql += " WHERE ";

		sql += whereClauses
				.stream()
				.map(clause -> clause.toString())
				.collect(Collectors.joining(" AND"));
		return sql;
	}
}
