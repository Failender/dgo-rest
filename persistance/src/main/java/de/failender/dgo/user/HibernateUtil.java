package de.failender.dgo.user;

import com.fasterxml.classmate.AnnotationConfiguration;
import org.apache.commons.io.IOUtils;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil
{
	private static SessionFactory sessionFactory;

	private static void initialize() throws IOException {
		Configuration configuration = new Configuration();



		Properties properties = new Properties();

		properties.load(HibernateUtil.class.getResourceAsStream("/application.properties"));
		properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
		properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
		properties.put(Environment.SHOW_SQL, "true");

		configuration.setProperties(properties);
		configuration.addAnnotatedClass(UserEntity.class);
		configuration.addAnnotatedClass(GruppeEntity.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

		sessionFactory =  configuration.buildSessionFactory(serviceRegistry);
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
