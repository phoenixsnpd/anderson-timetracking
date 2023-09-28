package com.example.andersontimetracking.dao;

import com.example.andersontimetracking.interfaces.Connector;
import com.example.andersontimetracking.interfaces.TaskDao;
import com.example.andersontimetracking.models.Task;
import com.example.andersontimetracking.util.ServiceLocator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl implements TaskDao {
    private final Connector connector;

    public TaskDaoImpl() {
         connector = ServiceLocator.getServiceImpl(Connector.class);
    }

    @Override
    public List<Task> getAllTasksByUserID(int userID) {
        List<Task> userTasks = new ArrayList<>();
        String getUserTasksSql = "SELECT description, date FROM tasks WHERE userid = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(getUserTasksSql)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String description = resultSet.getString("description");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                Task task = new Task(userID, description, date);
                userTasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userTasks;
    }

    @Override
    public void addTask(Task task) {
        String addTaskSql = "INSERT INTO tasks(userid, description, date) VALUES (?,?,?)";
        Date sqlDate = Date.valueOf(task.getDate());
        try(Connection connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(addTaskSql)) {
            statement.setInt(1, task.getUserId());
            statement.setString(2, task.getDescription());
            statement.setDate(3, sqlDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
