package com.example.andersontimetracking.services;

import com.example.andersontimetracking.dao.TimeRecordDao;

public class TimeRecordPdfConverter {
    private TimeRecordDao recordDao;

    public TimeRecordPdfConverter(TimeRecordDao recordDao) {
        recordDao = new TimeRecordDao();
    }
}
