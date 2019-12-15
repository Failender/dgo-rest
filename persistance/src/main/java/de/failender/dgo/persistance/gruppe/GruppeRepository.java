package de.failender.dgo.persistance.gruppe;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.util.List;

class GruppeRepository {


	static GruppeRepository INSTANCE = new GruppeRepository();

	public static GruppeEntity findByName(String name) {

		return EntityMapper.firstOrNull(SelectQuery.Builder.selectAll(GruppeMapper.INSTANCE)
				.where(GruppeMapper.NAME, name)
				.limit(1)
				.build()
				.execute());

	}

	public static boolean existsByName(String name) {
		return findByName(name) != null;
	}

	public static void save(GruppeEntity gruppeEntity) {
		new InsertQuery<>(GruppeMapper.INSTANCE, gruppeEntity).execute();
	}



    public static GruppeEntity findById(Long id) {
		return EntityMapper.firstOrNull(SelectQuery.Builder.selectAll(GruppeMapper.INSTANCE)
				.where(GruppeMapper.ID, id)
				.limit(1)
				.build()
				.execute());
    }

	public static List<GruppeEntity> findAll() {
		return SelectQuery.Builder.selectAll(GruppeMapper.INSTANCE)
				.build()
				.execute();
	}
}
