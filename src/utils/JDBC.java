package utils;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * The `JDBC` abstract class provides methods for managing the database connection using JDBC.
 * It includes methods to open and close a database connection, as well as a method to retrieve the connection object.
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone=SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";

    /**
     * Represents a database connection using the Connection interface.
     * This field is used to establish and manage a connection to a database.
     */
    public static Connection connection;

    /**
     * Opens a database connection using the JDBC driver.
     * If successful, it sets up the connection object.
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Retrieves the database connection object.
     *
     * @return The database connection object.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection.
     * If successful, it releases the connection resources.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
