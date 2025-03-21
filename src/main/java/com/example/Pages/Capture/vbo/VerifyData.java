package com.example.Pages.Capture.vbo;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.example.Pages.ActionCapture;
import com.example.dto.FileRecord;
import com.example.dto.StatusEnum;

public class VerifyData extends ActionCapture {

    public VerifyData(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record) {
        super(driver, document, tableStatus, record, "Pilih data yang akan diverifikasi");
    }

    public void execute(Integer stepNumber) {

        String channel = record.getChannel();
        String nik = record.getNik();
        String name = record.getName();

        if (channel.equals("OM") || channel.equals("3O")) {
            return;
        }

        boolean dataFound = false;
        while (true) {
            try {
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-table-container']")));
                WebElement data = driver.findElement(By.xpath("//div[@class='ant-table-container']//td[text()='"+nik+"']"));
                if (data.isDisplayed()) {
                    dataFound = true;
                    break;
                }
            } catch (Exception e) {
                // Move to next page if element not found
                try {
                    WebElement nextPageButton = driver.findElement(By.xpath("//li[@title='Next Page']"));
                    js.executeScript("arguments[0].scrollIntoView(true);", nextPageButton);

                    if (nextPageButton.isEnabled()) {
                        nextPageButton.click();
                        Thread.sleep(2000); // Allow time for the page to load
                    } else {
                        break; // No more pages to check, exit loop
                    }
                } catch (Exception ex) {
                    break; // If no next button exists, exit loop
                }
            }
        }

        boolean testPassed = dataFound;
        StatusEnum status = StatusEnum.getValidate(testPassed);

        // Capture screenshot and insert into Word file
        if (testPassed) {
            capture("Result : Data yang akan diverifikasi yaitu data dari " + name + " dengan NIK " + nik + " - "
                    + status.getMessage());
        }

        render(stepNumber, status);

        doVerify(nik);
    }

    void doVerify(String nik) {

        WebElement btnDetail = driver.findElement(By.xpath("//div[@class='ant-table-container']//td[text()='" + nik
                + "']/following-sibling::td[@class='ant-table-cell ant-table-cell-fix-right ant-table-cell-fix-right-first']"));
        btnDetail.click();
        sleep(5000);

    }
}
