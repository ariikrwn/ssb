package com.example.Pages;

import com.example.Function.CaptureFunction;
import com.example.Function.ReadExcelFunction;
import com.example.Function.TestStep;
import com.example.dto.FileRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        logger.info("This is an info message");
        logger.error("This is an error message");

        WebDriver driver = Base.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(60));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Simulate Ctrl + Minus (-) three times to reach ~80% zoom
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.SUBTRACT).sendKeys(Keys.SUBTRACT).sendKeys(Keys.SUBTRACT)
                .keyUp(Keys.CONTROL).perform();

        if (driver == null) {
            System.out.println("Driver is not initialized!");
            return;
        }

        String excelFilePath = "D:/Active Data Selenium/Active Data.xlsx";
        int testScriptIdIndex = 1;
        int isActiveColumnIndex = 2;
        int nikColumnIndex = 3;
        int nameColumnIndex = 4;
        int reffNumColumnIndex = 5;
        int channelColumnIndex = 6;
        int channelDescColumnIndex = 7;
        int noteColumnIndex = 8;
        int statusVBOColumnIndex = 9;
        int statusDAOColumnIndex = 10;

        // Read Excel Data
        String[][] excelData = ReadExcelFunction.readExcelData(excelFilePath);

        // Define a single testResults list to track all test steps
        List<TestStep> testResults = new ArrayList<>();

        // Process rows based on "Active" = "Y"
        for (int i = 1; i < excelData.length; i++) {
            if (ReadExcelFunction.isActive(excelData, i, isActiveColumnIndex)) {
                FileRecord record = new FileRecord();
                record.testScriptId = excelData[i][testScriptIdIndex];
                record.nik = excelData[i][nikColumnIndex];
                record.name = excelData[i][nameColumnIndex];
                record.reffNum = excelData[i][reffNumColumnIndex];
                record.channel = excelData[i][channelColumnIndex];
                record.channelDesc = excelData[i][channelDescColumnIndex];
                record.note = excelData[i][noteColumnIndex];
                record.statusVBO = excelData[i][statusVBOColumnIndex];
                record.statusDAO = excelData[i][statusDAOColumnIndex];
                record.username = Config.getProperty("username");
                record.password = Config.getProperty("password");

                String wordDocDir = "D:/Testing Selenium SSB/Reports/";
                String wordFilePath = wordDocDir + record.testScriptId + "_test_report_.docx";
                File file = new File(wordFilePath);

                XWPFDocument document;

                // Load existing Word document if it exists, otherwise create a new one
                if (file.exists()) {
                    file.delete();
                    // try {
                    // FileInputStream fis = new FileInputStream(file);
                    // document = new XWPFDocument(fis);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    document = new XWPFDocument();
                    // }
                } else {
                    document = new XWPFDocument();
                }

                // create header
                CaptureFunction.createDocumentHeader(document, record.testScriptId);
                // create table status
                XWPFTable tableStatus = CaptureFunction.getOrCreateStatusTable(document);

                ActionCapture.VBO(driver, document, tableStatus, record);

                // VBO VBO = new VBO(document, tableStatus);
                // DAO DAO = new DAO();

                // // Get URL
                // VBO.getUrl(driver, channel, reffNum);

                // // Perform Login
                // VBO.Login(driver, js, wait, username, password);

                // get list method dari class VBO
                // List<method> for -> increment Call VBO by method name

                // Final save and close
                try (FileOutputStream out = new FileOutputStream(wordFilePath)) {
                    document.write(out);
                }
                document.close();

                /*
                 * //Verify Data
                 * VBO.VerifyData(driver, wait, js, nik, name);
                 * 
                 * //Verify KTP
                 * VBO.verifyKTP(driver, wait, js);
                 * 
                 * //Verify Data Upload
                 * VBO.verifyDataUpload(driver, wait, js);
                 * 
                 * //Verify Data Personal
                 * VBO.verifyDataPersonal(driver, wait, js);
                 * 
                 * //Verify Data Pekerjaan
                 * VBO.verifyDataPekerjaan(driver, wait, js);
                 * 
                 * //Verify Data Pembukaan Rekening
                 * VBO.verifyDataRekening(driver, wait, js);
                 * 
                 * //Approve VBO
                 * VBO.ApproveVBO(driver, wait, js, statusVBO, note);
                 * 
                 * //Get URL DAO
                 * DAO.getUrl(driver);
                 * 
                 * //Login DAO
                 * DAO.loginDAO(driver, wait, username, password);
                 * 
                 * //Access Approval KYC Online Menu
                 * DAO.accessApprovalKYC(driver, wait);
                 * 
                 * //Search Data
                 * DAO.searchData(driver,wait,nik,channelDesc);
                 * 
                 * //Hide Toolbar
                 * DAO.hideToolbar(driver);
                 * 
                 * //Verify Search Result
                 * DAO.verifySearchResult(driver, wait, js, nik, name);
                 * 
                 * //Verify KTP Nasabah
                 * DAO.verifyKTP(driver,js);
                 * 
                 * //Verify Data Upload VBO
                 * DAO.verifyDataUploadVBO(driver, js);
                 * 
                 * //Verify Data Personal
                 * DAO.verifyDataPersonal(driver, js);
                 * 
                 * //Verify Data Pekerjaan
                 * DAO.verifyDataPekerjaan(driver, js);
                 * 
                 * //Verify Data Pembukaan Rekening
                 * DAO.verifyDataPembukaanRekening(driver, js);
                 * 
                 * //Verify Dokumen Nasabah
                 * DAO.verifyDokumenNasabah(driver, wait, js);
                 * 
                 * //Approval DAO
                 * DAO.approvalDAO(driver, wait, js, statusDAO);
                 * 
                 * //Logout CMS
                 * DAO.logoutDAO(driver, wait);
                 */

            }
        }

        // for test result

    }

}
