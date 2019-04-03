package de.failender.dgo.persistance.user;

import de.failender.dgo.persistance.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

class UserRepository {

	public List<UserEntity> getUser() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from UserEntity", UserEntity.class).list();
		}
	}

	public UserEntity findUserByName(String name) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<UserEntity> query = session.createQuery("from UserEntity where name =:name", UserEntity.class);
			query.setParameter("name", name);
			return query.getSingleResult();
		}
	}
}
