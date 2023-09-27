package com.example.andersontimetracking.interfaces;

import com.example.andersontimetracking.models.User;

import java.util.List;

public interface ReportGenerator<T>{
     T generateReport(List<User> users);
}
