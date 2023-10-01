package com.example.andersontimetracking.dao;

import com.example.andersontimetracking.interfaces.Connector;
import com.example.andersontimetracking.interfaces.TaskDao;
import com.example.andersontimetracking.models.Post;
import com.example.andersontimetracking.models.Task;
import com.example.andersontimetracking.util.ServiceLocator;

import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaskDaoImpl implements TaskDao {
    private final Connector connector;

    public TaskDaoImpl() {
        connector = ServiceLocator.getServiceImpl(Connector.class);
    }

    UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public List<Task> getAllTasksByUserID(int userID) {
        List<Task> userTasks = new ArrayList<>();
        String getUserTasksSql = "SELECT description, date FROM public.tasks WHERE userid = ?";
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
        String addTaskSql = "INSERT INTO public.tasks (userid, description, date) VALUES (?,?,?)";
        Date sqlDate = Date.valueOf(task.getDate());
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(addTaskSql)) {
            statement.setInt(1, task.getUserId());
            statement.setString(2, task.getDescription());
            statement.setDate(3, sqlDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeTask(int id) {
        String deleteOperation = "delete from public.tasks where id = {0}";
        try (Connection connection = connector.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(MessageFormat.format(deleteOperation, id));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Post> getAllTask(int limit, int page) {
        List<Post> posts = new LinkedList<>();
        String getAll = "SELECT * from public.tasks limit ? offset ?";
        int offset = 0;
        if (page == 1) {
            offset = 0;
        } else {
            offset = (page-1) * 10;
        }
        try {
            Connection connection = connector.getConnection();
            PreparedStatement ps = connection.prepareStatement(getAll);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                Post post = new Post();
                task.setId(resultSet.getInt("id"));
                task.setUserId(resultSet.getInt("userid"));
                task.setDescription(resultSet.getString("description"));
                task.setDate(resultSet.getDate("date").toLocalDate());
                post.setId(task.getId());
                post.setBody(task.getDescription());
                post.setTitle(userDao.findUserById(task.getUserId()).getName());
                posts.add(post);
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    public int getNumberOfTasks () {
        int count = 0;
        String query = "select count(*) from public.tasks";
        Connection connection = connector.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public List<Task> getAllTasksByUserIDAndCurrentDate(int userID) {
        List<Task> userTasks = new ArrayList<>();
        String getUserTasksSql = "SELECT description, date FROM public.tasks WHERE userid = ? AND date = CURRENT_DATE";
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
}