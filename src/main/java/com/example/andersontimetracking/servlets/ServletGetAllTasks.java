package com.example.andersontimetracking.servlets;
import com.example.andersontimetracking.dao.TaskDaoImpl;
import com.example.andersontimetracking.models.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ServletGetAllTasks", value = "/ServletGetAllTasks")
public class ServletGetAllTasks extends HttpServlet {
    TaskDaoImpl taskDao = new TaskDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int limit = Integer.parseInt(request.getParameter("_limit"));
        int page = Integer.parseInt(request.getParameter("_page"));
        List<Post> list = taskDao.getAllTask(limit,page);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {

    }

}