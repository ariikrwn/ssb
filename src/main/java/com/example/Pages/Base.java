package com.example.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {

    protected static WebDriver driver;

    public static WebDriver getDriver() {
        
            // Set WebDriver system properties from the config file
            System.setProperty("webdriver.gecko.driver","C://Program Files//Mozilla Firefox//geckodriver.exe");

            // Initialize the WebDriver
            driver = new FirefoxDriver();
            driver.manage().window().maximize();

            return driver;
        
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();  // Close the browser and end the session
        }
    }
    
        
}
