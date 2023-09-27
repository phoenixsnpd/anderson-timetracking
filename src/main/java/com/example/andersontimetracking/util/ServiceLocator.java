package com.example.andersontimetracking.util;

import com.example.andersontimetracking.interfaces.EmailService;
import com.example.andersontimetracking.interfaces.ReportGenerator;
import com.example.andersontimetracking.services.EmailServiceImpl;
import com.example.andersontimetracking.services.PdfGenerator;

import java.util.HashMap;
import java.util.Map;

public final class ServiceLocator {
    private static Map<Class<?>,Class<?>> services = new HashMap<>();

    static{
        services.put(EmailService.class, EmailServiceImpl.class);
        services.put(ReportGenerator.class, PdfGenerator.class);
    }
    private ServiceLocator(){

    }

    @SuppressWarnings("unchecked")
    public static <T> T getServiceImpl(Class<T> interfaceType) {
        Class<?> implementationType = services.get(interfaceType);
        if (implementationType != null) {
            try {
                return (T) implementationType.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
