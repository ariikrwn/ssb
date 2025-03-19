package com.example.Pages.Capture.vbo;

import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.Function.CaptureFunction;
import com.example.Function.TestStep;
import com.example.Pages.ActionCapture;
import com.example.Pages.Config;
import com.example.dto.FileRecord;

public class GetUrl extends ActionCapture {

    public GetUrl(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record) {
        super(driver, document, tableStatus, record, "Akses VBO SSB");
    }

    public void execute() throws Exception, IOException, InterruptedException {
        String channel = this.record.channel;
        String reffNum = this.record.reffNum;
        String urlVBO = Config.getProperty("url.vbo");

        // Access VBO
        if (channel.equals("OM") || channel.equals("3O")) {
            driver.get(urlVBO + "/" + reffNum);
        } else {
            driver.get(urlVBO);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.ant-typography")));
        Thread.sleep(2000);

        String actualURL = driver.getCurrentUrl();
        String expectedURL = (channel.equals("OM") || channel.equals("3O")) ? urlVBO + "/" + reffNum : urlVBO;
        boolean testPassed = actualURL.equals(expectedURL);
        String status = testPassed ? "Passed" : "Failed";

        // Capture screenshot and insert into Word file
        if (testPassed) {
            CaptureFunction.takeScreenshotAndInsertIntoWord(
                    document,
                    driver,
                    this.stepName,
                    "Result : Berhasil akses VBO SSB - " + status,
                    false,
                    new TestStep(1, this.stepName, testPassed ? "Passed" : "Failed"), status, tableStatus);
        }
        // String status = testPassed ? "Passed" : "Failed";
    }
    
}
