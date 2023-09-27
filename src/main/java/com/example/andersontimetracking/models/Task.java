package com.example.andersontimetracking.models;

import java.time.LocalDate;

public class Task {
    private int id;
    private int userId;
    private String description;
    private LocalDate date;

    public Task(int userId, String description, LocalDate date) {
        this.userId = userId;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
