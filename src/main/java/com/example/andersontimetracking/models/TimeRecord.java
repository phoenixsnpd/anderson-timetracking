package com.example.andersontimetracking.models;

import java.time.LocalDate;

public class TimeRecord {
    private int id;
    LocalDate date;
    String name;
    String description;
    double totalTime;

    public TimeRecord(LocalDate date, String name, String description, double totalTime) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.totalTime = totalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "TimeRecord{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", totalTime=" + totalTime +
                '}';
    }
}
