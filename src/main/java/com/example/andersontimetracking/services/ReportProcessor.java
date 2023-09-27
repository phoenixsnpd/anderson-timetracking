package com.example.andersontimetracking.services;

import com.example.andersontimetracking.dao.TaskDao;
import com.example.andersontimetracking.dao.UserDao;
import com.example.andersontimetracking.models.User;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.List;

public class ReportProcessor {
    private UserDao userDao;
    private TaskDao taskDao;
    private PdfGenerator pdfGenerator;

    public ReportProcessor() {
        userDao = new UserDao();
        taskDao = new TaskDao();
        pdfGenerator = new PdfGenerator();
    }

    public PDDocument generateReport() {
        List<User> users = getReportFromBase();
        var report = pdfGenerator.generateReport(users);
        return report;
    }

    public List<User> getReportFromBase() {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            user.setTasks(taskDao.getAllTasksByUserID(user.getId()));
        }
        return users;
    }
}
