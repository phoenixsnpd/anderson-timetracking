package com.example.andersontimetracking.servlets;

import com.example.andersontimetracking.dao.TaskDaoImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ServletRemoveTask", value = "/ServletRemoveTask")
public class ServletRemoveTask extends HttpServlet {
    TaskDaoImpl taskDao = new TaskDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        taskDao.removeTask(Integer.parseInt(req.getParameter("id")));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        taskDao.removeTask(Integer.parseInt(req.getParameter("id")));
    }
}