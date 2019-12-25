package de.failender.ezql;

import de.failender.ezql.properties.PropertyReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EzqlConnector {


	private static String url;
	private static String user;
	private static String password;
	private static Connection connection;

	public static void initialize(String driver, String url, String user, String password) {

		try {
			Class.forName(driver);
			System.out.println("Connecting to " + url);
			EzqlConnector.url = url;
			EzqlConnector.user= user;
			EzqlConnector.password = password;
			conect();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static Connection conect() {
		try {
			connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public static void initialize(String propertyPrefix) {
		initialize(PropertyReader.getProperty(propertyPrefix + ".driver_class"),
				PropertyReader.getProperty(propertyPrefix + ".url"),
				PropertyReader.getProperty(propertyPrefix + ".username"),
				PropertyReader.getProperty(propertyPrefix + ".password"));
	}

	public static Connection getConnection() {
		try {
			if(!connection.isValid(1)) {
				return conect();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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
