package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final  String URL = "jdbc:mysql://localhost:3306/librarymanagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "@Anhtu9725";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

//    public static void closeConnection(Connection connection) {
//        try {
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
