package com.example.andersontimetracking.services;

import com.example.andersontimetracking.dao.TaskDaoImpl;
import com.example.andersontimetracking.dao.UserDaoImpl;
import com.example.andersontimetracking.interfaces.ReportGenerator;
import com.example.andersontimetracking.interfaces.TaskDao;
import com.example.andersontimetracking.interfaces.UserDao;
import com.example.andersontimetracking.models.User;
import com.example.andersontimetracking.util.ServiceLocator;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.List;

public class ReportProcessor {
    private UserDao userDao;
    private TaskDao taskDao;
    private ReportGenerator reportGenerator;

    public ReportProcessor() {
        userDao = ServiceLocator.getServiceImpl(UserDao.class);
        taskDao = ServiceLocator.getServiceImpl(TaskDao.class);
        reportGenerator = ServiceLocator.getServiceImpl(ReportGenerator.class);
    }

    public PDDocument generateReport() {
        List<User> users = getReportFromBase();
        var report = (PDDocument) reportGenerator.generateReport(users);
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
