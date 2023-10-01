package com.example.andersontimetracking.servlets;

import com.example.andersontimetracking.dao.TaskDaoImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletNumberOfTask", value = "/ServletNumberOfTask")
public class ServletNumberOfTask extends HttpServlet {
    TaskDaoImpl taskDao = new TaskDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = String.valueOf(taskDao.getNumberOfTasks());
        response.addHeader("length",number);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(number);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}