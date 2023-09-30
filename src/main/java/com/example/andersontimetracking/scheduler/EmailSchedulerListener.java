package com.example.andersontimetracking.scheduler;

import com.example.andersontimetracking.interfaces.EmailService;
import com.example.andersontimetracking.services.CustomTelegramBot;
import com.example.andersontimetracking.util.ServiceLocator;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.TimeZone;

@WebListener
public class EmailSchedulerListener implements ServletContextListener {
    private Scheduler emailScheduler;
    private Scheduler telegramBotScheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CustomTelegramBot bot = new CustomTelegramBot();
        new Thread(() -> {
            bot.startLongPolling();
        }).start();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("bot", bot);


        JobDetail jobDetailBot = JobBuilder.newJob(TelegramSenderJob.class)
                .usingJobData(jobDataMap)
                .withIdentity("TelegramBotJob")
                .build();

        Trigger triggerBot = TriggerBuilder.newTrigger()
                .withIdentity("TelegramTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 20 * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Etc/GMT-3")))
                .build();


        JobDetail jobDetail = JobBuilder.newJob(EmailSenderJob.class)
                .withIdentity("EmailJob")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("EmailTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 20 * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Etc/GMT-3")))
                .build();

        try {
            emailScheduler = new StdSchedulerFactory().getScheduler();
            emailScheduler.start();
            emailScheduler.scheduleJob(jobDetail, trigger);

            telegramBotScheduler = new StdSchedulerFactory().getScheduler();
            telegramBotScheduler.start();
            telegramBotScheduler.scheduleJob(jobDetailBot, triggerBot);

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}