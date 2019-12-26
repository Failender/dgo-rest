package de.failender.ezql.queries;

import de.failender.ezql.EzqlConnector;
import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.clause.Clause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExistsQuery<ENTITY> extends BaseQuery {


	private final EntityMapper<ENTITY> mapper;

	protected ExistsQuery(EntityMapper<ENTITY> mapper, List<Clause> whereClauses) {
		super(whereClauses);
		this.mapper = mapper;
	}

	public boolean execute() {
		String sql = "SELECT EXISTS(SELECT 1 FROM " + mapper.table() + " ";
		sql = appendWhereClauses(sql);
		sql += ");";

        try (Statement statement = EzqlConnector.createStatement()) {
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			return rs.getBoolean("exists");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static class Builder<ENTITY> {

		private final EntityMapper<ENTITY> mapper;

		private List<Clause> whereClauses = new ArrayList<>();


		private Builder(EntityMapper<ENTITY> mapper) {
			this.mapper = mapper;
		}

		public static <ENTITY> ExistsQuery.Builder<ENTITY> exists(EntityMapper<ENTITY> entityMapper) {
			return new Builder<>(entityMapper);
		}


		public ExistsQuery<ENTITY> build() {
			return new ExistsQuery<>(mapper, whereClauses);
		}


		public <VALUE> ExistsQuery.Builder<ENTITY> where(FieldMapper<ENTITY, VALUE> field, VALUE value) {
			whereClauses.add(new BaseClause(field, value));
			return this;
		}


	}
}
