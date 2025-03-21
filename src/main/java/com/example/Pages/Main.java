package com.example.Pages;

import com.example.Function.CaptureFunction;
import com.example.Function.ReadExcelFunction;
import com.example.dto.FileRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Base base = new Base();

        logger.info("This is an info message");
        logger.error("This is an error message");

        WebDriver driver = base.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(60));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Simulate Ctrl + Minus (-) three times to reach ~80% zoom
        Actions actions = new Actions(driver);
        actions
                .keyDown(Keys.CONTROL)
                .sendKeys(Keys.SUBTRACT)
                .sendKeys(Keys.SUBTRACT)
                .sendKeys(Keys.SUBTRACT)
                .keyUp(Keys.CONTROL)
                .perform();

        String excelFilePath = Config.getProperty("file-test");
        logger.error(excelFilePath);
        logger.error("This is an error message");

        // Read Excel Data
        // Process rows based on "Active" = "Y"
        List<FileRecord> excelData = ReadExcelFunction.printActiveRows(excelFilePath);
        logger.error(excelData.size());

        for (int i = 0; i < excelData.size(); i++) {
            FileRecord record = excelData.get(i);

            String wordDocDir = Config.getProperty("folder-report");
            String wordFilePath = wordDocDir + record.getTestScriptId() + "_test_report_.docx";
            logger.error(wordFilePath);
            File file = new File(wordFilePath);

            XWPFDocument document;

            // Load existing Word document if it exists, otherwise create a new one
            if (file.exists()) {
                file.delete();
                document = new XWPFDocument();
                // }
            } else {
                document = new XWPFDocument();
            }

            // create header
            CaptureFunction.createDocumentHeader(document, record.getTestScriptId());
            // create table status
            XWPFTable tableStatus = CaptureFunction.getOrCreateStatusTable(document);

            ActionCapture.VBO(driver, document, tableStatus, record);

            try (FileOutputStream out = new FileOutputStream(wordFilePath)) {
                document.write(out);
            }
            document.close();
        }
    }

}
