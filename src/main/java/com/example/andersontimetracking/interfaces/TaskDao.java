package com.example.andersontimetracking.interfaces;

import com.example.andersontimetracking.models.Post;
import com.example.andersontimetracking.models.Task;

import java.util.List;

public interface TaskDao {
    List<Task> getAllTasksByUserID(int userID);
    void addTask(Task task);
    void removeTask(int id);
    List<Post> getAllTask(int limit, int page);           // POST???
    int getNumberOfTasks();
    List<Task> getAllTasksByUserIDAndCurrentDate(int userID);

}
