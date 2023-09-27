package com.example.andersontimetracking.servlets;

import java.io.*;

import com.example.andersontimetracking.interfaces.EmailService;
import com.example.andersontimetracking.util.ServiceLocator;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private final EmailService emailService;
    private String message;

    public HelloServlet() {
        this.emailService = ServiceLocator.getServiceImpl(EmailService.class);
        emailService.sendMail();
    }

    public void init() {
        message = "Hello my dear HEEEEEY :P";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}
