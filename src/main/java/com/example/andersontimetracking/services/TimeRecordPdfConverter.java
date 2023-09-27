package com.example.andersontimetracking.services;

import com.example.andersontimetracking.dao.TimeRecordDao;
import com.example.andersontimetracking.models.TimeRecord;

import java.util.List;

public class TimeRecordPdfConverter {
    private final TimeRecordDao recordDao;
    private final PdfGenerator generator;

    public TimeRecordPdfConverter() {
        recordDao = new TimeRecordDao();
        generator = new PdfGenerator();
    }

    public void convertRecordDBToPdfFile() {
        //List<TimeRecord> records = recordDao.getAllRecords();
        //generator.generatePdf(records);
    }
}
