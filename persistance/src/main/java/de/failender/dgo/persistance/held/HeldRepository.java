package de.failender.dgo.persistance.held;

import de.failender.ezql.clause.OrderClause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;
import de.failender.ezql.queries.UpdateQuery;
import de.failender.ezql.repository.EzqlRepository;

import java.util.List;

class HeldRepository extends EzqlRepository<HeldEntity> {

	public HeldEntity findByIdReduced(Long id) {
		return findOneBy(HeldMapper.ID, id, HeldMapper.USER_ID);
	}

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


	@Override
	protected EntityMapper<HeldEntity> getMapper() {
		return HeldMapper.INSTANCE;
	}

}
