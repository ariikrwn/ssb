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

public class VerifyKTP extends ActionCapture {

    public VerifyKTP(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record) {
        super(driver, document, tableStatus, record, "Verify Data KTP");
    }

    public void execute(Integer stepNumber) {

        String NIK = record.getNik();
        String name = record.getName();

        String dataNIK = (String) js.executeScript("return arguments[0].value;", NIK);
        String dataNama = (String) js.executeScript("return arguments[0].value;", name);

        boolean testPassed = dataNIK.equals(NIK) && dataNama.equals(name);
        StatusEnum status = StatusEnum.getValidate(testPassed);

        if (testPassed) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@title='NIK']")));
            capture("");

            WebElement labelfoto = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@title='Foto']")));
            js.executeScript("arguments[0].scrollIntoView(true);", labelfoto);
            capture("");

            WebElement labelalamat = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ktpPos")));
            js.executeScript("arguments[0].scrollIntoView(true);", labelalamat);
            capture("Result : Data KTP Nasabah sudah sesuai - " + status.getMessage());
        }

        render(stepNumber, status);

    }

}
