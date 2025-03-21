package com.example.Pages;

import com.example.Pages.Capture.vbo.GetUrl;
import com.example.Pages.Capture.vbo.Login;
import com.example.Pages.Capture.vbo.VerifyData;
import com.example.Pages.Capture.vbo.VerifyKTP;
import com.example.dto.FileRecord;
import com.example.dto.ResultCapture;
import com.example.dto.StatusEnum;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public abstract class ActionCapture {
    protected WebDriver driver;
    protected XWPFDocument document;
    protected XWPFTable tableStatus;
    protected JavascriptExecutor js;
    protected WebDriverWait wait;
    protected String stepName;
    protected FileRecord record;
    protected List<ResultCapture> resultCaptures;

    public ActionCapture(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record, String stepName) {
        this.document = document;
        this.tableStatus = tableStatus;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(60));
        this.js = (JavascriptExecutor) driver;
        this.stepName = stepName;
        this.record = record;
        this.resultCaptures = new ArrayList<>();
    }

    public abstract void execute(Integer step);

    public static void VBO(WebDriver driver, XWPFDocument document, XWPFTable tableStatus, FileRecord record) {
        List<ActionCapture> list = List.of(
            new GetUrl(driver, document, tableStatus, record),
            new Login(driver, document, tableStatus, record),
            new VerifyData(driver, document, tableStatus, record)
            //new VerifyKTP(driver, document, tableStatus, record)
        );
        for (int i = 0; i < list.size(); i++) {
            list.get(i).execute(i);
        }
    }

    protected void sleep(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void capture(String remarks) {
        String screenshotDir = Config.getProperty("folder-capture");
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = String.valueOf(System.currentTimeMillis()); // Custom timestamp
        File screenshotFile = new File(screenshotDir + "screenshot_" + timestamp + ".png");

        // Copy the screenshot to the folder
        try {
            FileUtils.copyFile(screenshot, screenshotFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        resultCaptures.add(new ResultCapture(remarks, screenshotFile));
    }

    protected void render(Integer stepNumber, StatusEnum status) {
        tableStatus.setWidth(10000);

        XWPFTableRow row = tableStatus.createRow();
        setStyledCell(row.getCell(0), String.valueOf(stepNumber + 1), false, "FFFFFF", 1000,
                ParagraphAlignment.CENTER);
        setStyledCell(row.getCell(1), "  " + stepName, false, "FFFFFF", 7500,
                ParagraphAlignment.LEFT);
        setStyledCell(row.getCell(2), status.getMessage(), true, status.getBgColor(), 1500, ParagraphAlignment.CENTER);

        document.createParagraph().createRun().addBreak();
        document.createParagraph().createRun().addBreak();

        XWPFTable table = document.createTable(1, 1);

        for (int i = 0; i < resultCaptures.size(); i++) {
            ResultCapture resultCapture = resultCaptures.get(i);
            insertScreenShootAndRemarks(table, resultCapture.getImage(), resultCapture.getRemark(), i == 0);
        }
    }


    private void insertScreenShootAndRemarks(XWPFTable table, File screenshotFile, String remarks, boolean isFirst) {
        XWPFTableRow row;
        XWPFTableCell cell;

        if (isFirst) {
            // **Create a new 1-row, 1-column table for the first screenshot**
//            table = document.createTable(1, 1);
            table.setWidth("100%");
            row = table.getRow(0);
            cell = row.getCell(0);

            // **Insert Test Step Description**
            XWPFParagraph testParagraph = cell.getParagraphs().get(0);
            XWPFRun testStep = testParagraph.createRun();
            testStep.setText(stepName);
            testStep.setBold(true);
            testStep.setFontSize(12);

        } else {
            // **Get the existing last table cell**
//            table = document.getTables().get(document.getTables().size() - 1);
            row = table.getRow(0);
            cell = row.getCell(0);
        }

        // **Insert Screenshot into Existing Cell**
        try {
            FileInputStream imageStream = new FileInputStream(screenshotFile);
            XWPFRun runImage = cell.addParagraph().createRun();
            runImage.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, screenshotFile.getName(),
                    Units.toEMU(500), Units.toEMU(300)); // Set image size
        } catch (Exception e) {
            e.printStackTrace();
        }

        // **Insert Remarks (Only Once)**
        XWPFRun runRemarks = cell.addParagraph().createRun();
        runRemarks.setItalic(true);
        runRemarks.setText(remarks);
//        if (appendScreenshot) {
//        } else if (!appendScreenshot) {
//            XWPFRun runRemarks = cell.addParagraph().createRun();
//            runRemarks.setItalic(true);
//            runRemarks.setText(remarks);
//        }
    }

    private static void setStyledCell(XWPFTableCell cell, String text, boolean bold, String bgColor, int width, ParagraphAlignment alignment) {
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(alignment);
        paragraph.setSpacingAfter(0);
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(bold);
        run.setFontSize(12);
        cell.setColor(bgColor);
        cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(width));
    }
}
