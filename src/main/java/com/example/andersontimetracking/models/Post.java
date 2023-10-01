package com.example.andersontimetracking.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class Post {
    int id;
    String title;
    String body;

    public Post(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}