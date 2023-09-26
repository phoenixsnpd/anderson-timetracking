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
