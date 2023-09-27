package com.example.andersontimetracking.scheduler;

import com.example.andersontimetracking.interfaces.EmailService;
import com.example.andersontimetracking.util.ServiceLocator;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@WebListener
public class EmailSchedulerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JobDetail jobDetail = JobBuilder.newJob(EmailSenderJob.class)
                .withIdentity("EmailJob")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("EmailTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 23 * * ?"))
                .build();

        try{
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}