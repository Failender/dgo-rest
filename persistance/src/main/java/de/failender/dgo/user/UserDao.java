package de.failender.dgo.user;

import org.hibernate.Session;

import java.util.List;

public class UserDao {

	public List<UserEntity> getUser() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from UserEntity", UserEntity.class).list();
		}
	}
}
