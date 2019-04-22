package de.failender.dgo.persistance.gruppe;

import com.sun.istack.Nullable;
import de.failender.dgo.persistance.HibernateUtil;
import de.failender.dgo.persistance.held.VersionEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

public class GruppeRepository {


	@Nullable
	public static GruppeEntity findByName(String name) {

		String sql = "from GruppeEntity WHERE name = :name";
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<GruppeEntity> query = session.createQuery(sql, GruppeEntity.class);
			query.setParameter("name", name);
			query.setMaxResults(1);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static boolean existsByName(String name) {
		// TODO rewrite to correct sql
		try {
			findByName(name);
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	public static void save(GruppeEntity gruppeEntity) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			gruppeEntity.setId((Integer) session.save(gruppeEntity));
		}
	}
}
