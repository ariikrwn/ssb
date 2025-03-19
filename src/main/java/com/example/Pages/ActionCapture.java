package com.example.Pages;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.Pages.Capture.vbo.GetUrl;
import com.example.Pages.Capture.vbo.Login;
import com.example.dto.FileRecord;

public abstract class ActionCapture {
    protected WebDriver driver;
    protected XWPFDocument document;
    protected XWPFTable tableStatus;
    protected JavascriptExecutor js;
    protected WebDriverWait wait;
    protected String stepName;
    protected FileRecord record;

    public ActionCapture(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record, String stepName) {
        this.document = document;
        this.tableStatus = tableStatus;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(60));
        this.js = (JavascriptExecutor) driver;
        this.stepName = stepName;
        this.record = record;
    }

    public abstract void execute() throws Exception, IOException, InterruptedException;

    public static void VBO(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record) throws Exception, IOException, InterruptedException {
        List<ActionCapture> list = List.of(
            new GetUrl(driver, document, tableStatus, record),
            new Login(driver, document, tableStatus, record)
        );
        for(ActionCapture item : list) {
            item.execute();
        }
    }
}
