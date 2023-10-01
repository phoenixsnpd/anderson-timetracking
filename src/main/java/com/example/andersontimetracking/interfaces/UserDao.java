package com.example.andersontimetracking.interfaces;

import com.example.andersontimetracking.models.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    void addUsers(List<User> users);
    boolean isEmpty();
    User findUserById(int id);
    int findUserByName(String name);

}
