package de.failender.dgo.persistance;

import de.failender.dgo.persistance.gruppe.GruppeEntity;
import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.held.VersionEntity;
import de.failender.dgo.persistance.user.UserEntity;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;

public class HibernateUtil
{
	private static SessionFactory sessionFactory;

	private static void initialize() throws IOException {
		Configuration configuration = new Configuration();



		Properties properties = new Properties();

		properties.load(HibernateUtil.class.getResourceAsStream("/application.properties"));
		properties.put(Environment.SHOW_SQL, "true");

		configuration.setProperties(properties);
		configuration.addAnnotatedClass(UserEntity.class);
		configuration.addAnnotatedClass(GruppeEntity.class);
		configuration.addAnnotatedClass(HeldEntity.class);
		configuration.addAnnotatedClass(VersionEntity.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

		sessionFactory =  configuration.buildSessionFactory(serviceRegistry);

		String sql = IOUtils.toString(HibernateUtil.class.getResourceAsStream("/setup.sql"), "UTF-8");
		Session session = sessionFactory.openSession();

		Transaction transaction = session.beginTransaction();
		session.createNativeQuery(sql).executeUpdate();
		transaction.commit();


	}

	public static SessionFactory getSessionFactory()
	{
		if(sessionFactory == null) {
			try {
				initialize();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return sessionFactory;
	}
}
