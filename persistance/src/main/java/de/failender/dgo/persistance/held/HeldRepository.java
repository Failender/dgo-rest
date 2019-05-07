package de.failender.dgo.persistance.held;

import de.failender.ezql.queries.InsertQuery;
import de.failender.ezql.queries.SelectQuery;

import java.util.List;

public class HeldRepository {

	public static List<HeldEntity> findByUserId(Long id) {
		return SelectQuery.Builder.selectAll(HeldMapper.INSTANCE)
				.where(HeldMapper.USER_ID, id)
				.build()
				.execute();
	}

	public static void persist(HeldEntity heldEntity) {
		new InsertQuery<>(HeldMapper.INSTANCE, heldEntity).execute();
	}
}
