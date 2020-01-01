package de.failender.ezql.queries;

import de.failender.ezql.EzqlConnector;
import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.clause.Clause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateQuery<ENTITY> extends BaseQuery{

	private final EntityMapper<ENTITY> mapper;
	private final List<BaseClause<ENTITY, ?>> updateClauses;

	public UpdateQuery(EntityMapper<ENTITY> mapper, List<BaseClause<ENTITY, ?>> updateClauses, List<Clause> whereClauses) {
		super(whereClauses);
		this.updateClauses = updateClauses;
		this.mapper = mapper;
	}

	public void execute() {

		String sql = "UPDATE "  + mapper.table() + " SET ";

		sql += updateClauses
				.stream()
				.map(BaseClause::toString)
				.collect(Collectors.joining(", "));
		sql = appendWhereClauses(sql);
//		System.out.println(sql);
		try (Statement statement = EzqlConnector.createStatement()) {

			int result = statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public static class Builder<T> {

		private final EntityMapper<T> mapper;
		private List<Clause> whereClauses = new ArrayList<>();
		private List<BaseClause<T, ?>> updateClauses = new ArrayList<>();

		private Builder(EntityMapper<T> mapper) {
			this.mapper = mapper;
		}

		public static <T> UpdateQuery.Builder<T> update (EntityMapper<T> mapper) {
			return new UpdateQuery.Builder(mapper);
		}

		public UpdateQuery<T> build() {
			return new UpdateQuery<T>(mapper, updateClauses, whereClauses);
		}

		public <VALUE> UpdateQuery.Builder<T> where(FieldMapper<T, VALUE> field, VALUE value) {
			whereClauses.add(new BaseClause(field, value));
			return this;
		}

		public <VALUE> UpdateQuery.Builder<T> update(FieldMapper<T, VALUE> field, VALUE value) {
			updateClauses.add(new BaseClause(field, value));
			return this;
		}

	}
}
