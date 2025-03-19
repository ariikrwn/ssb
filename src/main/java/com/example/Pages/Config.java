package com.example.Pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static Properties properties = new Properties();

    static {
        try {
            // Load properties file (you can put the path of your config file here)
            FileInputStream inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading properties file.");
        }
    }

    // Method to get a property value by key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // You can add other methods to get properties with default values, etc.
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    


}
