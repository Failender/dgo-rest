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

	public List<String> getUserRights(int userid) {

		String queryString = "SELECT RIGHTS.NAME FROM USERS U INNER JOIN ROLES_TO_USER RTU ON RTU.USER_ID = U.ID INNER JOIN ROLES_TO_RIGHTS RTR ON RTR.ROLE_ID = RTU.ROLE_ID INNER JOIN RIGHTS ON RIGHTS.ID = RTR.RIGHT_ID WHERE U.ID = :user";

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<String> query = session.createSQLQuery(queryString);
			query.setParameter("user", userid);
			return query.getResultList();
		}
	}


}
