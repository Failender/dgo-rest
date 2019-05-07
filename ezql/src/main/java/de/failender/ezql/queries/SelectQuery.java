package de.failender.ezql.queries;

import de.failender.ezql.EzqlConnector;
import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.clause.OrderClause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectQuery<ENTITY> extends BaseQuery<ENTITY> {

	private final EntityMapper<ENTITY> mapper;
	private final Collection<FieldMapper<ENTITY, ?>> fieldMapper;
	private final String returnFields;
	private final Integer limit;
	private final List<OrderClause<?>> orderClauses;

	public SelectQuery(EntityMapper<ENTITY> mapper, Collection<FieldMapper<ENTITY, ?>> fieldMapper, String returnFields, List<BaseClause<ENTITY, ?>> whereClauses, Integer limit, List<OrderClause<?>> orderClauses) {
		super(whereClauses);
		this.mapper = mapper;
		this.fieldMapper = fieldMapper;
		this.returnFields = returnFields;
		this.limit = limit;
		this.orderClauses = orderClauses;
	}

	public List<ENTITY> execute() {
		String sql = "SELECT " + returnFields + " FROM " + mapper.table() + "";
		sql = appendWhereClauses(sql);

		if(limit != null) {
			sql += " LIMIT " + limit;
		}
		System.out.println(sql);
		try(Statement statement = EzqlConnector.getConnection().createStatement()) {

			ResultSet rs = statement.executeQuery(sql);
			List<ENTITY> results = new ArrayList<>();
			while(rs.next()) {
				ENTITY entity = mapper.create();
				for (FieldMapper<ENTITY, ?> entityFieldMapper : fieldMapper) {
					entityFieldMapper.setValue(entity, rs);
				}
				results.add(entity);
			}
			return results;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public static class Builder<T> {

		private final EntityMapper<T> mapper;
		private final Collection<FieldMapper<T, ?>> fieldMapper;
		private final String returnFields;
		private Integer limit;
		private List<BaseClause<T, ?>> whereClauses = new ArrayList<>();
		private List<OrderClause<?>> orderClauses = new ArrayList<>();

		private Builder(EntityMapper<T> mapper, List<FieldMapper<T, ?>> fieldMapper, String returnFields) {
			this.mapper = mapper;
			this.fieldMapper = fieldMapper;
			this.returnFields = returnFields;
		}

		public static <T> Builder<T> selectAll (EntityMapper<T> mapper) {
			return new Builder(mapper, mapper.fieldMappers(), "*");
		}

		public static <T> Builder<T> select(EntityMapper<T> mapper, FieldMapper<T, ?>... fields ) {
			String returnFields = Stream.of(fields)
					.map(FieldMapper::getField)
					.collect(Collectors.joining(","));
			return new Builder(mapper, Arrays.asList(fields), returnFields);
		}

		public SelectQuery<T> build() {
			return new SelectQuery(mapper, fieldMapper, returnFields, whereClauses, limit, orderClauses);
		}

		public Builder<T> limit(int limit) {
			this.limit = limit;
			return this;
		}

		public <VALUE> Builder<T> where(FieldMapper<T, VALUE> field, VALUE value) {
			whereClauses.add(new BaseClause(field, value));
			return this;
		}

		public <VALUE> Builder<T> orderBy(FieldMapper<T, VALUE> field, OrderClause.ORDER order) {
			orderClauses.add(new OrderClause<>(order, field));
			return this;
		}

	}
}
