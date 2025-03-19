package com.example.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Base {

    protected WebDriver driver;

    public Base() {
        System.setProperty("webdriver.gecko.driver", Config.getProperty("webdriver.gecko.driver"));

        // Initialize the WebDriver
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();  // Close the browser and end the session
        }
    }


}
