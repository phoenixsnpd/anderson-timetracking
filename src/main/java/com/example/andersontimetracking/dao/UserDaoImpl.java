package com.example.andersontimetracking.dao;

import com.example.andersontimetracking.interfaces.Connector;
import com.example.andersontimetracking.interfaces.UserDao;
import com.example.andersontimetracking.models.User;
import com.example.andersontimetracking.util.ServiceLocator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final Connector connector;
    private PreparedStatement statement;

    public UserDaoImpl() {
        connector = ServiceLocator.getServiceImpl(Connector.class);
    }

    private final String insertNew = "INSERT INTO public.users(id,name,surname,password) values (?,?,?,?)";

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getUsersSql = "SELECT id, name, surname FROM public.users";
        try (Connection connection = connector.getConnection()) {
            statement = connection.prepareStatement(getUsersSql);
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

    @Override
    public void addUsers(List<User> users) {
        if(isEmpty()) {
            try {
                Connection connection = connector.getConnection();
                connection.setTransactionIsolation(8);
                for (int i = 0; i <= 3; i++) {
                    statement = connection.prepareStatement(insertNew);
                    statement.setInt(1,users.get(i).getId());
                    statement.setString(2, users.get(i).getName());
                    statement.setString(3, users.get(i).getSurname());
                    statement.setString(4, users.get(i).getPassword());
                    statement.execute();
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isEmpty() {
        Connection connection = connector.getConnection();
        int isEmpty = 0;
        try {
            statement = connection.prepareStatement("SELECT CASE WHEN EXISTS (SELECT * FROM public.users LIMIT 1) THEN 1 ELSE 0 END");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                isEmpty = resultSet.getInt("case");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isEmpty == 0;
    }

    @Override
    public User findUserById(int id) {
        User user = new User();
        String query = "SELECT * FROM public.users where id = " + id;
        try{
            Connection connection = connector.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPassword(resultSet.getString("password"));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public int findUserByName(String name) {
        int id = 0;
        String query = "SELECT id FROM public.users where name ilike ?";
        System.out.println(query);
        try{
            Connection connection = connector.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,name);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt("id");
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

}
