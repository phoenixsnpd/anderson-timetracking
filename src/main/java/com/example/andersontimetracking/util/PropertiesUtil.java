package com.example.andersontimetracking.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static{
        loadEmailProperties();
    }

    private PropertiesUtil(){

    }
    public static String getEmailProperty(String key){
        return PROPERTIES.getProperty(key);
    }
    public static Properties getEmailProperties(){
        return PROPERTIES;
    }
    private static void loadEmailProperties() {
        try (var stream = PropertiesUtil.class.getClassLoader().getResourceAsStream("emailsender.properties")) {
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}