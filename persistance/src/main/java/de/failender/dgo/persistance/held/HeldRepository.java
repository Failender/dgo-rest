package de.failender.dgo.persistance.held;

import de.failender.ezql.clause.OrderClause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;
import de.failender.ezql.queries.UpdateQuery;

import java.util.List;

class HeldRepository {

	public static List<HeldEntity> findByUserIdOrdered(Long id) {
		return SelectQuery.Builder.selectAll(HeldMapper.INSTANCE)
				.where(HeldMapper.USER_ID, id)
				.orderBy(HeldMapper.ID, OrderClause.ORDER.ASC)
				.build()
				.execute();
	}

	public static void updatePublic(Long heldid, boolean value) {
		UpdateQuery.Builder.update(HeldMapper.INSTANCE)
				.where(HeldMapper.ID, heldid)
				.update(HeldMapper.PUBLIC, value)
				.build().execute();
	}

	public static void updateActive(Long heldid, boolean value) {
		UpdateQuery.Builder.update(HeldMapper.INSTANCE)
				.where(HeldMapper.ID, heldid)
				.update(HeldMapper.ACTIVE, value)
				.build().execute();
	}



	public static void persist(HeldEntity heldEntity) {
		new InsertQuery<>(HeldMapper.INSTANCE, heldEntity, true).execute();
	}

	public static HeldEntity findById(Long id) {
		return EntityMapper.firstOrNull(SelectQuery.Builder.selectAll(HeldMapper.INSTANCE)
				.where(HeldMapper.ID, id)
				.limit(1)
				.build().execute());
	}
}
