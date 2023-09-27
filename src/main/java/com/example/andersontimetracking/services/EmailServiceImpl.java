package com.example.andersontimetracking.services;

import com.example.andersontimetracking.interfaces.EmailService;
import com.example.andersontimetracking.interfaces.ReportGenerator;
import com.example.andersontimetracking.util.ServiceLocator;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class EmailServiceImpl implements EmailService {
    private final ReportProcessor reportGenerator;

    public EmailServiceImpl() {

       reportGenerator= new ReportProcessor();
    }

    @Override
    public void createSMTP() {

    }

    @Override
    public void sendMail() {

        try{
            Session session = getSession();
            Message message   = generateMailWithFile(session);
            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Message generateMailWithFile(Session session) throws MessagingException, IOException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(TO));
        message.setSubject("Test From Orange Team");

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Daily Report!");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test.txt");
//        if (inputStream == null) {
//            throw new RuntimeException("File not found in resources");
//        }

        PDDocument doc = reportGenerator.generateReport();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        doc.save(baos);
        doc.close();
        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
        DataSource source = new ByteArrayDataSource(inputStream, "application/pdf");
        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("Report.pdf");
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);

        return message;
    }
}