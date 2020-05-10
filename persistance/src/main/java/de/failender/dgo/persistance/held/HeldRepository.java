package de.failender.dgo.persistance.held;

import de.failender.dgo.security.EntityNotFoundException;
import de.failender.ezql.clause.OrderClause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.SelectQuery;
import de.failender.ezql.queries.UpdateQuery;
import de.failender.ezql.repository.EzqlRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class HeldRepository extends EzqlRepository<HeldEntity> {

	static final HeldRepository INSTANCE = new HeldRepository();

	public static void updateActiveByUserId(Long userId, boolean value) {
		UpdateQuery.Builder.update(HeldMapper.INSTANCE)
				.where(HeldMapper.USER_ID, userId)
				.update(HeldMapper.PUBLIC, value)
				.build().execute();
	}

	public boolean existsById(Long id) {
		return findByIdReduced(id) != null;
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

		List<HeldEntity> helden = builder.build().execute();
		helden = helden
				.stream()
				.filter(HeldRepositoryService::canCurrentUserViewHeld)
				.collect(Collectors.toList());
		return helden;
	}

	public static List<HeldEntity> findByUserIdOrdered(Long id) {
		return SelectQuery.Builder.selectAll(HeldMapper.INSTANCE)
				.where(HeldMapper.USER_ID, id)
				.orderBy(HeldMapper.ID, OrderClause.ORDER.ASC)
				.build()
				.execute();
	}

	public HeldEntity findByIdReduced(Long id) {
		HeldEntity heldEntity = findOneBy(HeldMapper.ID, id, HeldMapper.USER_ID, HeldMapper.PUBLIC).orElseThrow(() -> new EntityNotFoundException("Held konnnte nicht gefunden werden"));
		heldEntity.setId(id);
		return heldEntity;
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
		return findAll(HeldMapper.USER_ID, HeldMapper.ID, HeldMapper.PUBLIC);
	}
}
