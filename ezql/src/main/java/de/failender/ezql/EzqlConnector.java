package de.failender.ezql;

import de.failender.ezql.properties.PropertyReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EzqlConnector {


	private static Connection connection;

	public static void connect(String driver, String url, String user, String password) {

		try {
			Class.forName(driver);
			System.out.println("Connecting to " + url);
			connection = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void connect(String propertyPrefix) {
		connect(PropertyReader.getProperty(propertyPrefix + ".driver_class"),
				PropertyReader.getProperty(propertyPrefix + ".url"),
				PropertyReader.getProperty(propertyPrefix + ".username"),
				PropertyReader.getProperty(propertyPrefix + ".password"));
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void execute(String sql) {
		try {
			System.out.println(sql);
			Statement statement = EzqlConnector.getConnection().createStatement();
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
