package com.example.andersontimetracking.interfaces;

import com.example.andersontimetracking.util.PropertiesUtil;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.io.IOException;
import java.util.Properties;

public interface EmailService {
    String FROM = "juanmvcproject@gmail.com";
    String PASSWORD = "vuoilgkedltuysif";
    String TO = "taliyevcode@gmail.com";

    default Session getSession() {
        Properties properties = PropertiesUtil.getEmailProperties();
        Session session = Session.getInstance(
                properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM,
                                PASSWORD);
                    }
                }
        );

        session.setDebug(true);
        return session;
    }


    void createSMTP();

    void sendMail();

}