package com.example.andersontimetracking.dao;

import com.example.andersontimetracking.JdbcConnector;
import com.example.andersontimetracking.models.TimeRecord;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeRecordDao {
    private final JdbcConnector connector;

    public TimeRecordDao() {
        connector = new JdbcConnector();
    }

    public List<TimeRecord> getAllRecords() {
        List<TimeRecord> records = new ArrayList<>();
        String getAllSql = "SELECT date, name, description, total_time FROM timerecords";
        try(Connection connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(getAllSql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double totalTime = resultSet.getDouble("total_time");
                TimeRecord record = new TimeRecord(date, name, description, totalTime);
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void addRecord(TimeRecord record) {
        String addRecordSql = "INSERT INTO timerecords(date, name, description, total_time) VALUES (?, ?, ?, ?)";
        Date sqlDate = Date.valueOf(record.getDate());
        try(Connection connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(addRecordSql)) {
            statement.setDate(1, sqlDate);
            statement.setString(2, record.getName());
            statement.setString(3, record.getDescription());
            statement.setDouble(4, record.getTotalTime());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
