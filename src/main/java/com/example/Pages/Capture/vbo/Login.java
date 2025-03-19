package com.example.Pages.Capture.vbo;

import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.example.Function.CaptureFunction;
import com.example.Function.TestStep;
import com.example.Pages.ActionCapture;
import com.example.dto.FileRecord;

public class Login extends ActionCapture {

    public Login(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record) {
        super(driver, document, tableStatus, record, "Input username dan password VBO");
    }

    public void execute() throws Exception, IOException, InterruptedException {
        String username = this.record.username;
        String password = this.record.password;

        WebElement uname = driver.findElement(By.id("u"));
        uname.click();
        uname.sendKeys(username);

        WebElement pwd = driver.findElement(By.id("p"));
        pwd.click();
        pwd.sendKeys(password);

        // Verify input values
        String enteredUsername = (String) js.executeScript("return arguments[0].value;", uname);
        String enteredPassword = (String) js.executeScript("return arguments[0].value;", pwd);

        boolean testPassed = enteredUsername.equals(username) && enteredPassword.equals(password);
        String status = testPassed ? "Passed" : "Failed";
        TestStep resultStep = new TestStep(2, this.stepName, testPassed ? "Passed" : "Failed");

        if (testPassed) {
            CaptureFunction.takeScreenshotAndInsertIntoWord(
                    document,
                    driver,
                    this.stepName,
                    "Result : Berhasil input username dan password - " + status,
                    false,
                    resultStep, status, tableStatus);
        }

        WebElement buttonLogin = driver.findElement(By.cssSelector(".ant-btn"));
        buttonLogin.click();
        Thread.sleep(5000);

        WebElement confirmReffNum = null;

        try {
            // Wait for the element to be visible
            confirmReffNum = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.ant-btn:nth-child(2)")));
        } catch (Exception e) {
            // Handle the case when the element is not found
            confirmReffNum = null;
        }

        if (confirmReffNum != null) {
            // If element is found, it is not null
            confirmReffNum.click();
            Thread.sleep(5000);
        }
    }

}
