package com.example.andersontimetracking.scheduler;

import com.example.andersontimetracking.interfaces.EmailService;
import com.example.andersontimetracking.util.ServiceLocator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EmailSenderJob implements Job {

    private final EmailService _emailService;
    public EmailSenderJob(){
        _emailService = ServiceLocator.getServiceImpl(EmailService.class);
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        _emailService.sendMail();
    }
}