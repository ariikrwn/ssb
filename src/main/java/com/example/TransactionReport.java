//package com.example;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.Month;
//
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class TransactionReport {
//
//    public static void ReportAccountOpening(){
//        System.setProperty("webdriver.gecko.driver","C://Program Files//Mozilla Firefox//geckodriver.exe");
//        WebDriver driver = new FirefoxDriver();
//        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(60));
//        Actions actions = new Actions(driver);
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        driver.manage().window().maximize();
//        LocalDate date = LocalDate.now();
//        int day = date.getDayOfMonth();
//        Month month = date.getMonth();
//        int year = date.getYear();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//
//
//    }
//
//
//}
