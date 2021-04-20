package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    //JDBC URL Parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ07tqA";

    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    //Driver Interface Reference
    private static final String MYSQLJDBCDRIVER = "com.mysql.cj.jdbc.Driver";

    //Connection
    private static Connection connection = null;

    //Username
    private static final String username = "U07tqA";

    //Password
    private static final String password = "53689129674";

    public static Connection startConnection () {
        try {
            Class.forName(MYSQLJDBCDRIVER);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection () {
        return connection;
    }

    public static void closeConnection () {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
