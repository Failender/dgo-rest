package de.failender.dgo.persistance.held;

import de.failender.dgo.persistance.BaseRepository;
import de.failender.dgo.persistance.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigInteger;

public class VersionRepository extends BaseRepository<VersionEntity> {

	public static VersionEntity findFirstByHeldidOrderByVersionDesc(BigInteger id) {

		String queryString = "from VersionEntity WHERE heldid = :held ORDER BY version desc";
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<VersionEntity> query = session.createQuery(queryString, VersionEntity.class);
			query.setParameter("held", id);
			query.setMaxResults(1);
			return query.getSingleResult();
		}
	}

	@Override
	protected String entityName() {
		return "VersionEntity";
	}

	@Override
	protected Class<VersionEntity> entityClass() {
		return VersionEntity.class;
	}
}
