package com.example.andersontimetracking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector {
    public Connection getConnection()  {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("uri",
                    "user", "password");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
