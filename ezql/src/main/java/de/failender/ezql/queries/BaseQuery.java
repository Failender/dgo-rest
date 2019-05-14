package de.failender.ezql.queries;

import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.clause.Clause;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseQuery {

	private final List<Clause> whereClauses;

	protected BaseQuery(List<Clause> whereClauses) {
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
				.collect(Collectors.joining(" AND "));
		return sql;
	}
}
