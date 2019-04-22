package de.failender.dgo.persistance;

import com.sun.istack.Nullable;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.user.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public abstract class BaseRepository<T extends BaseEntity> {

	protected abstract String entityName();
	protected abstract Class<T> entityClass();


	@Nullable
	public T findBy(String field, Object value) {

		String queryString = "from " + entityName() + " WHERE " + field + " = :value";
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<T> query = session.createQuery(queryString, entityClass());
			query.setParameter("value", value);
			return query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}

	public boolean existsBy(String field, Object value) {

		return findBy(field, value) != null;
	}

	public void save(T entity) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			entity.setId((Integer) session.save(entity));
		}
	}

	public List<T> findAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from " + entityName(), entityClass()).list();
		}
	}



}
