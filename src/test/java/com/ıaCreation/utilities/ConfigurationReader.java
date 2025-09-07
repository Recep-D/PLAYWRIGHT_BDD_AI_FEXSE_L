package com.ıaCreation.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    
    private static Properties properties;
    
    static {
        try {
            String path = "configuration.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration.properties file");
        }
    }
    
    public static String getProperty(String keyName) {
        return properties.getProperty(keyName);
    }
    
    public static String getBrowserName() {
        return properties.getProperty("browserName");
    }
    
    public static String getUrl() {
        return properties.getProperty("url");
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless"));
    }
}
