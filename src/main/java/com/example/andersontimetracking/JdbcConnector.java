package com.example.andersontimetracking;

import com.example.andersontimetracking.interfaces.Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector implements Connector {
    @Override
    public Connection getConnection()  {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://34.159.59.142:5432/taskmanagerdb",
                    "admin", "AndersenProject2023");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
