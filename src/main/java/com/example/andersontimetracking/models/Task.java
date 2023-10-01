package com.example.andersontimetracking.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class Task {
    int id;
    int userId;
    String description;
    LocalDate date;

    public Task(int userId, String description, LocalDate date) {
        this.userId = userId;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                '}';
    }
}
