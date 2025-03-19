package com.example.Pages.Capture.vbo;

import com.example.Pages.ActionCapture;
import com.example.Pages.Config;
import com.example.dto.FileRecord;
import com.example.dto.StatusEnum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Login extends ActionCapture {

    public Login(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record) {
        super(driver, document, tableStatus, record, "Input username dan password VBO");
    }

    public void execute(Integer stepNumber) {
        String username = Config.getProperty("username");
        String password = Config.getProperty("password");

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
        StatusEnum status = StatusEnum.getValidate(testPassed);

        if (testPassed) {
            capture("Result : Berhasil input username dan password - " + status.getMessage());
        }

        render(stepNumber, status);

        doLogin();
    }

    void doLogin() {
        WebElement buttonLogin = driver.findElement(By.cssSelector(".ant-btn"));
        buttonLogin.click();
        sleep(5000);

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
            sleep(5000);
        }
    }

}
