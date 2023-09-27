package com.example.andersontimetracking.dao;

import com.example.andersontimetracking.JdbcConnector;
import com.example.andersontimetracking.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    JdbcConnector connector;

    public UserDao() {
        connector = new JdbcConnector();
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getUsersSql = "SELECT id, name, surname FROM users";
        try(Connection connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(getUsersSql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                User user = new User(id, name, surname);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
