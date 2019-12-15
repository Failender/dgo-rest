package de.failender.dgo.persistance.held;

import de.failender.ezql.clause.OrderClause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.DeleteQuery;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;
import de.failender.ezql.repository.EzqlRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class VersionRepository extends EzqlRepository<VersionEntity> {

	static VersionRepository INSTANCE = new VersionRepository();

	public VersionEntity findFirstByHeldidOrderByVersionDesc(Long id) {

		return SelectQuery.Builder.selectAll(VersionMapper.INSTANCE)
				.where(VersionMapper.HELD_ID, id)
				.orderBy(VersionMapper.VERSION, OrderClause.ORDER.DESC)
				.limit(1)
				.build()
				.execute()
				.get(0);
	}

	@Override
	protected EntityMapper<VersionEntity> getMapper() {
		return VersionMapper.INSTANCE;
	}

	public void persist(VersionEntity versionEntity) {
		new InsertQuery<>(VersionMapper.INSTANCE, versionEntity).execute();
	}

	public VersionEntity findByHeldAndVersion(Long id, int version) {
		return SelectQuery.Builder.selectAll(VersionMapper.INSTANCE)
				.where(VersionMapper.HELD_ID, id)
				.where(VersionMapper.VERSION, version)
				.limit(1)
				.build()
				.execute()
				.get(0);
	}

	public Optional<VersionEntity> findByHeldIdAndCreated(Long id, LocalDateTime stand) {
		return findOneBy(field(VersionMapper.HELD_ID, id), field(VersionMapper.CREATED_DATE, stand));
	}

	public List<VersionEntity> findVersionsByHeldOrderByVersionAsc(Long id) {
		return SelectQuery.Builder.selectAll(getMapper())
				.where(VersionMapper.HELD_ID, id)
				.orderBy(VersionMapper.VERSION, OrderClause.ORDER.ASC)
				.execute();
	}

	public void updateVersion(Long id, int version) {
		updateById(id, field(VersionMapper.VERSION, version));
	}

	public void deleteVersions(List<Long> ids) {
		deleteBulkById(ids);

	}
}
