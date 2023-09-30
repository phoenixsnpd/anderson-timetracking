package com.example.andersontimetracking.scheduler;

import com.example.andersontimetracking.services.CustomTelegramBot;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TelegramSenderJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        CustomTelegramBot bot = (CustomTelegramBot) dataMap.get("bot");
        bot.sendPdfToAll();
    }
}