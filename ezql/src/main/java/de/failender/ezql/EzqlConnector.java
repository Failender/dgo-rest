package de.failender.ezql;

import de.failender.ezql.properties.PropertyReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class EzqlConnector {


    private static String url;
    private static String user;
    private static String password;

    private static List<Connection> availableConnections = new ArrayList<>();

    private static ThreadLocal<Connection> requestConnection = new ThreadLocal<>();

    public static void initialize(String driver, String url, String user, String password) {

        try {
            System.out.println("Loading driver " + driver);
            Class.forName(driver);
            System.out.println("Connecting to " + url);
            EzqlConnector.url = url;
            EzqlConnector.user = user;
            EzqlConnector.password = password;
            connect();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection connect() {
        try {
            return DriverManager.getConnection(url, user, password);
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

    public static Statement createStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            connect();
            return createStatement();
        }
    }

    public static Connection getConnection() {
        return requestConnection.get();
    }

    public static void execute(String sql) {
        try {
//			System.out.println(sql);
            Statement statement = EzqlConnector.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized void allocateConnection() {

        if (availableConnections.isEmpty()) {
        }
        Iterator<Connection> connectionIterator = availableConnections.iterator();
        while (connectionIterator.hasNext()) {
            Connection connection = connectionIterator.next();
            connectionIterator.remove();
            if (isValid(connection)) {
                requestConnection.set(connection);
                return;
            }
        }
        requestConnection.set(connect());


    }

    public static <T> T singleRequest(Supplier<T> supplier) {
        allocateConnection();
        T value = supplier.get();
        releaseConnection();
        return value;
    }

    public static synchronized void releaseConnection() {
        availableConnections.add(requestConnection.get());
    }

    private static boolean isValid(Connection connection) {
        try {
            connection.createStatement().execute("SELECT 1");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
