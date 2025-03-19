package com.example.Function;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureFunction extends ReadExcelFunction {

    public static void takeScreenshotAndInsertIntoWord(XWPFDocument document, WebDriver driver, String testSteps,
                                                       String remarks, boolean appendScreenshot, TestStep result, String status, XWPFTable tableStatus) {
        // Define directories
        String screenshotDir = "D:/Testing Selenium SSB/Capture/";
//        String wordDocDir = "D:/Testing Selenium SSB/Reports/";

        // Create folders if they don't exist
        File screenshotFolder = new File(screenshotDir);
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();
        }

//        File wordDocFolder = new File(wordDocDir);
//        if (!wordDocFolder.exists()) {
//            wordDocFolder.mkdirs();
//        }

        // Capture screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = String.valueOf(System.currentTimeMillis()); // Custom timestamp
        File screenshotFile = new File(screenshotDir + "screenshot_" + timestamp + ".png");

        // Copy the screenshot to the folder
        try {
            FileUtils.copyFile(screenshot, screenshotFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Optionally: Check active Excel row data and perform some action
        // String excelFilePath = "D:/Active Data Selenium/Active Data.xlsx";
        // int testScriptIdIndex = 1;
        // int isActiveColumnIndex = 2;

        // String[][] excelData = readExcelData(excelFilePath);

        // for (int i = 1; i < excelData.length; i++) {
        // if (isActive(excelData, i, isActiveColumnIndex)) {
        // String testScriptId = excelData[i][testScriptIdIndex];
        // Process further if needed

        // Define Word document file name and path using Test Script ID
        // String wordFilePath = wordDocDir + testScriptId + "_test_report_.docx";
        // File file = new File(wordFilePath);
        // XWPFDocument document;

        // // **Load existing Word document if it exists, otherwise create a new one**
        // if (file.exists()) {
        // try (FileInputStream fis = new FileInputStream(file)) {
        // document = new XWPFDocument(fis);
        // }
        // } else {
        // document = new XWPFDocument();
        // }

        // **Find or create Automation Test Status table**
        // XWPFTable tableStatus = getOrCreateStatusTable(document);

        // **Append test step results**
        // testResults.add(new TestStep(testResults.size() + 1, testSteps, status));

        // for (TestStep result : testResults) {
        tableStatus.setWidth(10000);
        XWPFTableRow row = tableStatus.createRow();
        setStyledCell(row.getCell(0), String.valueOf(result.getStepNumber()), false, "FFFFFF", 1000,
                ParagraphAlignment.CENTER);
        setStyledCell(row.getCell(1), "  " + result.getStepName(), false, "FFFFFF", 7500,
                ParagraphAlignment.LEFT);
        setStyledCell(row.getCell(2), result.getStatus(), false, "FFFFFF", 1500, ParagraphAlignment.CENTER);

        // Add Space Before New Table (Optional)
        document.createParagraph().createRun().addBreak();
        // }

        // **Insert Screenshot & Remarks**
        insertScreenshotandRemarks(document, screenshotFile, testSteps, remarks, appendScreenshot);

        // try (FileOutputStream out = new FileOutputStream(wordFilePath)) {
        // document.write(out);
        // }
        // document.close();
        // }
        // }
    }

    public static void createDocumentHeader(XWPFDocument document, String testScriptId) {
        // TITLE : AUTOMATION TEST RESULT
        XWPFTable tableTitle = document.createTable(1, 1);
        tableTitle.removeBorders();
        tableTitle.setWidth(10000);
        XWPFTableRow rowTitle;
        XWPFTableCell cellTitle;
        rowTitle = tableTitle.getRow(0);
        cellTitle = rowTitle.getCell(0);
        XWPFParagraph titleParagraph = cellTitle.getParagraphs().get(0);
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText("AUTOMATION TEST RESULT");
        titleRun.setBold(true);
        titleRun.setFontSize(24);
        titleRun.setFontFamily("Arial");

        // PROJECT SUMMARY
        XWPFParagraph paragraph2 = document.createParagraph();
        XWPFRun projectSummary = paragraph2.createRun();
        projectSummary.setText("PROJECT SUMMARY");
        projectSummary.setBold(true);
        projectSummary.setFontSize(18);
        projectSummary.setFontFamily("Arial");

        XWPFTable tableHeader = document.createTable(6, 2);
        tableHeader.setWidth(10000);

        setStyledCell(tableHeader.getRow(0).getCell(0), "Project Name", true, "F4B083", 2000, ParagraphAlignment.LEFT);
        setStyledCell(tableHeader.getRow(0).getCell(1), "Owning Branch", false, "FFFFFF", 8000,
                ParagraphAlignment.LEFT);

        setStyledCell(tableHeader.getRow(1).getCell(0), "Application Environment", true, "F4B083", 2000,
                ParagraphAlignment.LEFT);
        setStyledCell(tableHeader.getRow(1).getCell(1), "DEV", false, "FFFFFF", 8000, ParagraphAlignment.LEFT);

        setStyledCell(tableHeader.getRow(2).getCell(0), "Test Script ID", true, "F4B083", 2000,
                ParagraphAlignment.LEFT);
        setStyledCell(tableHeader.getRow(2).getCell(1), testScriptId, false, "FFFFFF", 8000, ParagraphAlignment.LEFT);

        setStyledCell(tableHeader.getRow(3).getCell(0), "Date & Time", true, "F4B083", 2000, ParagraphAlignment.LEFT);
        setStyledCell(tableHeader.getRow(3).getCell(1), timestamp(), false, "FFFFFF", 8000, ParagraphAlignment.LEFT);

        setStyledCell(tableHeader.getRow(4).getCell(0), "Test Duration", true, "F4B083", 2000, ParagraphAlignment.LEFT);
        setStyledCell(tableHeader.getRow(4).getCell(1), "11 minutes", false, "FFFFFF", 8000, ParagraphAlignment.LEFT);

        setStyledCell(tableHeader.getRow(5).getCell(0), "Testing By", true, "F4B083", 2000, ParagraphAlignment.LEFT);
        setStyledCell(tableHeader.getRow(5).getCell(1), "Azhari Kurniawan", false, "FFFFFF", 8000,
                ParagraphAlignment.LEFT);

        // Add Space Before New Table (Optional)
        document.createParagraph().createRun().addBreak();

    }

    public static XWPFTable getOrCreateStatusTable(XWPFDocument document) {
        // for (XWPFTable table : document.getTables()) {
        // if (table.getRows().size() > 0 &&
        // table.getRow(0).getCell(0).getText().equals("NO.")) {
        // return table; // Return existing table
        // }
        // }

        // Create new table if not found
        XWPFTable tableStatus = document.createTable(1, 3);
        tableStatus.setWidth(10000);
        XWPFTableRow headerRow = tableStatus.getRow(0);
        setStyledCell(headerRow.getCell(0), "NO.", true, "F4B083", 1000, ParagraphAlignment.CENTER);
        setStyledCell(headerRow.getCell(1), "  TEST STEP", true, "F4B083", 7500, ParagraphAlignment.LEFT);
        setStyledCell(headerRow.getCell(2), "STATUS", true, "F4B083", 1500, ParagraphAlignment.CENTER);
        return tableStatus;

    }

    public static void insertScreenshotandRemarks(XWPFDocument document, File screenshotFile, String testSteps, String remarks, boolean appendScreenshot) {
        XWPFTable table;
        XWPFTableRow row;
        XWPFTableCell cell;

        if (!appendScreenshot) {
            // **Create a new 1-row, 1-column table for the first screenshot**
            table = document.createTable(1, 1);
            table.setWidth("100%");
            row = table.getRow(0);
            cell = row.getCell(0);

            // **Insert Test Step Description**
            XWPFParagraph testParagraph = cell.getParagraphs().get(0);
            XWPFRun testStep = testParagraph.createRun();
            testStep.setText(testSteps);
            testStep.setBold(true);
            testStep.setFontSize(12);

        } else {
            // **Get the existing last table cell**
            table = document.getTables().get(document.getTables().size() - 1);
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
        if (appendScreenshot) {
            XWPFRun runRemarks = cell.addParagraph().createRun();
            runRemarks.setItalic(true);
            runRemarks.setText(remarks);
        } else if (!appendScreenshot) {
            XWPFRun runRemarks = cell.addParagraph().createRun();
            runRemarks.setItalic(true);
            runRemarks.setText(remarks);
        }
    }

    public static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private static void setStyledCell(XWPFTableCell cell, String text, boolean bold, String bgColor, int width,
            ParagraphAlignment alignment) {
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(alignment);

        // Dynamically set background color
        if (text.equalsIgnoreCase("Passed")) {
            bgColor = "00FF00"; // Green for "Passed"
        } else if (text.equalsIgnoreCase("Failed")) {
            bgColor = "FF0000"; // Red for "Failed"
        }
        paragraph.setSpacingAfter(0);
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(bold);
        run.setFontSize(12);
        cell.setColor(bgColor); // Set background color
        cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(width));
    }

}
