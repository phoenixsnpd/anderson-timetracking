package com.example.andersontimetracking.servlets;

import com.example.andersontimetracking.dao.TaskDaoImpl;
import com.example.andersontimetracking.dao.UserDaoImpl;
import com.example.andersontimetracking.models.Post;
import com.example.andersontimetracking.models.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "ServletPost", value = "/ServletPost")
public class ServletPost extends HttpServlet {
    UserDaoImpl userDao = new UserDaoImpl();
    TaskDaoImpl taskDao = new TaskDaoImpl();
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
        Post post = objectMapper.readValue(jb.toString(),Post.class);
        Task task = new Task(userDao.findUserByName(post.getTitle()), post.getBody(), LocalDate.now());
        taskDao.addTask(task);
    }
}