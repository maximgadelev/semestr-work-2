package ru.kpfu.itis.gadelev.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionHelper {
    private static Connection connection;
    private static final String DRIVER = "org.postgresql.Driver";
    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/gameDataBase";
    public static final String USER = "postgres";
    public static final String PASSWORD = "123";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );
            } catch (ClassNotFoundException | SQLException e) {

            }
        }
        return connection;
    }
}
