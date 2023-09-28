package com.example.andersontimetracking.servlets;

import com.example.andersontimetracking.dao.TaskDaoImpl;
import com.example.andersontimetracking.models.Task;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="json-servlet" ,value="/jsonparcer")

public class JsonParcer  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String line;

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Gson gson = new Gson();
        Task task=gson.fromJson(sb.toString(),Task.class);
        TaskDaoImpl taskDao=new TaskDaoImpl();
        taskDao.addTask(task);

    }
}
