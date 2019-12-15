package de.failender.dgo.persistance.held;

import de.failender.ezql.clause.OrderClause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.SelectQuery;
import de.failender.ezql.queries.UpdateQuery;
import de.failender.ezql.repository.EzqlRepository;

import java.time.LocalDateTime;
import java.util.List;

class HeldRepository extends EzqlRepository<HeldEntity> {

	static final HeldRepository INSTANCE = new HeldRepository();

	public boolean existsById(Long id) {
		return findByIdReduced(id) != null;
	}

	public HeldEntity findByIdReduced(Long id) {
		HeldEntity heldEntity = findOneBy(HeldMapper.ID, id, HeldMapper.USER_ID);
		heldEntity.setId(id);
		return heldEntity;
	}

	public static List<HeldEntity> findByUserIdOrdered(Long id) {
		return SelectQuery.Builder.selectAll(HeldMapper.INSTANCE)
				.where(HeldMapper.USER_ID, id)
				.orderBy(HeldMapper.ID, OrderClause.ORDER.ASC)
				.build()
				.execute();
	}

	public static List<HeldEntity> findByGruppe(Long gruppe, boolean includePrivate, boolean showInactive) {

		SelectQuery.Builder builder = SelectQuery.Builder.selectAll(HeldMapper.INSTANCE)
				.where(HeldMapper.GRUPPE, gruppe);
		if(!includePrivate) {
			builder.where(HeldMapper.PUBLIC, true);
		}
		if(!showInactive) {
			builder.where(HeldMapper.ACTIVE, true);
		}

		return builder.build().execute();
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

	public static void updateLockExpire(Long heldid, LocalDateTime value) {
		UpdateQuery.Builder.update(HeldMapper.INSTANCE)
				.where(HeldMapper.ID, heldid)
				.update(HeldMapper.LOCK_EXPIRE, value)
				.build().execute();
	}


	@Override
	protected EntityMapper<HeldEntity> getMapper() {
		return HeldMapper.INSTANCE;
	}

	public List<HeldEntity> findAllReduced() {
		return findAll(HeldMapper.USER_ID, HeldMapper.ID);
	}
}
