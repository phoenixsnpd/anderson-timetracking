package com.example.andersontimetracking.interfaces;

import com.example.andersontimetracking.models.Task;

import java.util.List;

public interface TaskDao {
    List<Task> getAllTasksByUserID(int userID);
    void addTask(Task task);
}
