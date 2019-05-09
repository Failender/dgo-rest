package de.failender.dgo.persistance.held;

import de.failender.ezql.clause.OrderClause;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

class VersionRepository  {

	public static VersionEntity findFirstByHeldidOrderByVersionDesc(Long id) {

		return SelectQuery.Builder.selectAll(VersionMapper.INSTANCE)
				.where(VersionMapper.HELD_ID, id)
				.orderBy(VersionMapper.VERSION, OrderClause.ORDER.DESC)
				.limit(1)
				.build()
				.execute()
				.get(0);
	}

	public static void persist(VersionEntity versionEntity) {
		new InsertQuery<>(VersionMapper.INSTANCE, versionEntity).execute();
	}

	public static VersionEntity findByHeldAndVersion(Long id, int version) {
		return SelectQuery.Builder.selectAll(VersionMapper.INSTANCE)
				.where(VersionMapper.HELD_ID, id)
				.where(VersionMapper.VERSION, version)
				.limit(1)
				.build()
				.execute()
				.get(0);
	}
}
