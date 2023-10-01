package com.example.andersontimetracking.servlets;

import com.example.andersontimetracking.dao.UserDaoImpl;
import com.example.andersontimetracking.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletAutoLoadUser", value = "/ServletAutoLoadUser")
public class ServletAutoLoadUser extends HttpServlet {
    UserDaoImpl userDao = new UserDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = objectMapper.readValue(jb.toString(), new TypeReference<List<User>>(){});
        userDao.addUsers(users);
    }
}