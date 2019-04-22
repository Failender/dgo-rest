package de.failender.dgo.persistance.user;

import de.failender.dgo.persistance.BaseRepository;
import de.failender.dgo.persistance.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

class UserRepository extends BaseRepository<UserEntity> {

	public List<UserEntity> getUser() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from UserEntity", UserEntity.class).list();
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


	@Override
	protected String entityName() {
		return "UserEntity";
	}

	@Override
	protected Class entityClass() {
		return UserEntity.class;
	}
}
