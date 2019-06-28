package dao;

import entity.IssuedBooksEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String username = "postgres";
    private static String password = "admin";
    private static String url = "jdbc:postgresql://127.0.0.1:5432/Library 4.0";

    public static Connection openConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
