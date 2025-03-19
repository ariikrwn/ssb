package com.example.Pages.Capture.vbo;

import com.example.Pages.ActionCapture;
import com.example.Pages.Config;
import com.example.dto.FileRecord;
import com.example.dto.StatusEnum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GetUrl extends ActionCapture {

    public GetUrl(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record) {
        super(driver, document, tableStatus, record, "Akses VBO SSB");
    }

    public void execute(Integer stepNumber) {
        String channel = record.getChannel();
        String reffNum = record.getReffNum();
        String urlVBO = Config.getProperty("url.vbo");

        // Access VBO
        if (channel.equals("OM") || channel.equals("3O")) {
            driver.get(urlVBO + "/" + reffNum);
        } else {
            driver.get(urlVBO);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.ant-typography")));
        sleep(2000);

        String actualURL = driver.getCurrentUrl();
        String expectedURL = (channel.equals("OM") || channel.equals("3O")) ? urlVBO + "/" + reffNum : urlVBO;
        boolean testPassed = actualURL.equals(expectedURL);
        StatusEnum status = StatusEnum.getValidate(testPassed);

        // Capture screenshot and insert into Word file
        if (testPassed) {
            capture("Result : Berhasil akses VBO SSB - " + status.getMessage());
        }

        render(stepNumber, status);
    }
    
}
