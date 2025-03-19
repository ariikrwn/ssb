package com.example.Pages;

import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

import com.example.Function.CaptureFunction;
import com.example.Function.TestStep;


public class DAO{

static List<TestStep> testResults = new ArrayList<>();

public void getUrl(WebDriver driver) throws Exception, IOException, InterruptedException{
    String urlDAO = Config.getProperty("url.dao");  
    driver.get(urlDAO);
    WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(60));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.ant-typography")));
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "1. Akses CMS SSB", "Result : Berhasil akses ke CMS SSB",false, testResults);
}

public void loginDAO(WebDriver driver, WebDriverWait wait, String username, String password) throws Exception, IOException, InterruptedException{
     //Login CMS
     WebElement uname = driver.findElement(By.id("u"));
     WebElement pwd = driver.findElement(By.id("p"));
     WebElement btnLogin = driver.findElement(By.id("IButtonLogin"));

     uname.click();
     uname.sendKeys(username);
     Thread.sleep(3000);

     pwd.click();
     pwd.sendKeys(password);
     Thread.sleep(3000);
     CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "2. Input username dan password CMS", "Result : Berhasil input username dan password",false, testResults);

     btnLogin.click();
     Thread.sleep(3000);

     wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@class='ant-typography css-12lqnri']")));
     CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Berhasil masuk ke CMS SSB",true, testResults);
     Thread.sleep(3000);
    
}

public void accessApprovalKYC(WebDriver driver, WebDriverWait wait) throws Exception, IOException, InterruptedException{
    //Access Menu Administration
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "3. Akses menu Administration", "",false, testResults);
    WebElement administration = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("/administration")));
    administration.click();
    Thread.sleep(3000);

    //Access Menu Approval KYC Online
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "4. Akses Sub Menu Approval KYC Online", "",false, testResults);
    WebElement approvalKYC = driver.findElement(By.id("/administration/approval-kyc-online"));
    approvalKYC.click();
    Thread.sleep(3000);

    //Menu Approval KYC Online
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Approval KYC Online']")));
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Berhasil akses menu Approval KYC Online",true, testResults);
    Thread.sleep(3000);
}

public void searchData(WebDriver driver, WebDriverWait wait, String NIK, String channelDesc)  throws Exception, IOException, InterruptedException{
    //Search Date
        /*WebElement startDate = driver.findElement(By.xpath("//input[@date-range='start']"));
        WebElement endDate = driver.findElement(By.xpath("//input[@date-range='end']"));
        startDate.click();
        WebElement selectStartDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+date+"']")));
        selectStartDate.click();
        Thread.sleep(3000);
        WebElement selectEndDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+date+"']")));
        selectEndDate.click();
        Thread.sleep(3000);
        screenShot(driver);
        Thread.sleep(3000);
        */

        //Search Status
        WebElement statusKYC = driver.findElement(By.xpath("//span[@title='Verify VBO']"));
        statusKYC.click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Verify VBO']"))).click();
        Thread.sleep(2000); 

        //Search Channel
        WebElement channel = driver.findElement(By.id("channelCode"));
        channel.click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='"+channelDesc+"']"))).click();
        Thread.sleep(2000);

        //Search Customer Retake
        //WebElement custRetake = driver.findElement(By.id("retakeInfo"));
        //select.selectByValue("With Retake");
        //screenShot(driver);
        //Thread.sleep(3000);

        //Search NIK
        WebElement fieldNIK = driver.findElement(By.id("nik"));
        fieldNIK.click();
        fieldNIK.sendKeys(NIK);
        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "5. Cari data yang akan diverifikasi", "", false, testResults);

        //Search Data
        WebElement btnSearch = driver.findElement(By.id("IButtonSearch"));
        btnSearch.click();
        Thread.sleep(5000);
}

public void verifySearchResult(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String NIK, String name) throws Exception, IOException, InterruptedException{
    //Verify result
    WebElement tblDAO = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[@class='ant-table-tbody']")));
    js.executeScript("arguments[0].scrollIntoView(true);", tblDAO);
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Data yang akan diverifikasi yaitu data dari " +name+ " dengan NIK " +NIK+ "",true, testResults);
    WebElement btnDetail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-table-container']//td[text()='"+NIK+"']/following-sibling::td[@class='ant-table-cell ant-table-cell-fix-right ant-table-cell-fix-right-first']//button[@class='ant-btn css-12lqnri ant-btn-text ant-btn-color-default ant-btn-variant-text ant-btn-icon-only']")));
    btnDetail.click();

    //Detail Approval DAO
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Detail Approval KYC Online']")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Detail Approval KYC Online']")));
}

/* public void hideToolbar(WebDriver driver) throws Exception, IOException, InterruptedException{
     //Hide Toolbar Menu
     WebElement toggleMenu = driver.findElement(By.id("IButtonToggleMenu"));
     toggleMenu.click();
     Thread.sleep(3000);
} */

public void verifyKTP(WebDriver driver, JavascriptExecutor js) throws Exception, IOException, InterruptedException{
    //Verify Data KTP
    WebElement agama = driver.findElement(By.xpath("//label[@title='Agama']"));
    WebElement alamatKTP = driver.findElement(By.xpath("//label[@title='Alamat Lengkap']"));
    WebElement headerDetail = driver.findElement(By.xpath("//h4[text()='Detail Approval KYC Online']"));
    js.executeScript("arguments[0].scrollIntoView(true);", headerDetail);
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "6. Data KTP Nasabah", "",false, testResults);
    Thread.sleep(2000);

    js.executeScript("arguments[0].scrollIntoView(true);", agama);
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "",true,testResults);
    Thread.sleep(2000);

    js.executeScript("arguments[0].scrollIntoView(true);", alamatKTP);
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Data KTP Nasabah sudah sesuai",true, testResults);
    Thread.sleep(3000);
}

public void verifyDataUploadVBO(WebDriver driver, JavascriptExecutor js) throws Exception, IOException, InterruptedException{
    //Verify Data Upload VBO
    WebElement headerDetail = driver.findElement(By.xpath("//h4[text()='Detail Approval KYC Online']"));
    js.executeScript("arguments[0].scrollIntoView(true);", headerDetail);
    Thread.sleep(2000);

    WebElement dataUploadVBO = driver.findElement(By.id("rc-tabs-1-tab-2"));
    dataUploadVBO.click();
    Thread.sleep(2000);

    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "7. Data Upload VBO Nasabah", "Result : Data Upload VBO Nasabah sudah sesuai",false, testResults);
    Thread.sleep(2000);

    //tambahin logic jika ada foto tanda tangan dan dokumen pendukung
}

public void verifyDataPersonal(WebDriver driver, JavascriptExecutor js) throws Exception, IOException, InterruptedException{
     //Verify Data Personal
     WebElement headerDetail = driver.findElement(By.xpath("//h4[text()='Detail Approval KYC Online']"));
     js.executeScript("arguments[0].scrollIntoView(true);", headerDetail);
     Thread.sleep(2000);

     WebElement dataPersonal = driver.findElement(By.id("rc-tabs-1-tab-3"));
     dataPersonal.click();
     Thread.sleep(2000);
     CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "8. Data Personal Nasabah", "",false,testResults);
     Thread.sleep(2000);

     WebElement alamatRumah = driver.findElement(By.xpath("//h5[text()='Alamat Rumah']"));
     js.executeScript("arguments[0].scrollIntoView(true);", alamatRumah);
     CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Data Personal Nasabah sudah sesuai",true, testResults);
     Thread.sleep(2000);
     
}

public void verifyDataPekerjaan(WebDriver driver, JavascriptExecutor js) throws Exception, IOException, InterruptedException{
    //Verify Data Pekerjaan
    WebElement headerDetail = driver.findElement(By.xpath("//h4[text()='Detail Approval KYC Online']"));
    js.executeScript("arguments[0].scrollIntoView(true);", headerDetail);
    Thread.sleep(2000);

    WebElement dataPekerjaan = driver.findElement(By.id("rc-tabs-1-tab-5"));
    dataPekerjaan.click();
    Thread.sleep(2000);
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "9. Data Pekerjaan Nasabah", "",false, testResults);
    Thread.sleep(2000);

    WebElement alamatKantor = driver.findElement(By.xpath("//h5[text()='Alamat Kantor']"));
    js.executeScript("arguments[0].scrollIntoView(true);", alamatKantor);
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Data Pekerjaan Nasabah sudah sesuai",true, testResults);
    Thread.sleep(2000);
    
}

public void verifyDataPembukaanRekening(WebDriver driver, JavascriptExecutor js) throws Exception, IOException, InterruptedException{
    //Verify Data Pembukaan Rekening
    WebElement headerDetail = driver.findElement(By.xpath("//h4[text()='Detail Approval KYC Online']"));
    js.executeScript("arguments[0].scrollIntoView(true);", headerDetail);
    Thread.sleep(2000);

    WebElement dataPembukaanRekening = driver.findElement(By.id("rc-tabs-1-tab-6"));
    dataPembukaanRekening.click();
    Thread.sleep(2000);
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "10. Data Pembukaan Rekening Nasabah", "",false, testResults);
    Thread.sleep(2000);

    WebElement dataRekening = driver.findElement(By.xpath("//h5[text()='Data Rekening']"));
    js.executeScript("arguments[0].scrollIntoView(true);", dataRekening);
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Data Pembukaan Rekening Nasabah sudah sesuai",true, testResults);
    Thread.sleep(2000);
}

public void verifyDokumenNasabah(WebDriver driver, WebDriverWait wait, JavascriptExecutor js) throws Exception, IOException, InterruptedException{
    //Verify data NPWP
    WebElement headerDetail = driver.findElement(By.xpath("//h4[text()='Detail Approval KYC Online']"));
    js.executeScript("arguments[0].scrollIntoView(true);", headerDetail);
    Thread.sleep(2000);

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rc-tabs-2-tab-npwp")));
    Thread.sleep(2000);
    CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "11. Dokumen Nasabah", "Result : Dokumen Nasabah sudah sesuai",false, testResults);
    Thread.sleep(3000);

    //Verify data KTP BO
    //Verify data NPWP BO
}

public void approvalDAO(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String statusDAO) throws Exception, IOException, InterruptedException{
    
    //Verify data nasabah
    WebElement namaIbuKandung = driver.findElement(By.xpath("//label[@title='Nama Ibu Kandung']"));
    js.executeScript("arguments[0].scrollIntoView(true);", namaIbuKandung);
    Thread.sleep(2000);


    if(statusDAO.equals("Approve")){

        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "12. Lakukan Approve DAO Data Nasabah", "",false, testResults);

        //Note DAO
        WebElement noteDAO = driver.findElement(By.id("noteFromDao"));
        js.executeScript("arguments[0].scrollIntoView(true);", noteDAO);
        noteDAO.click();
        noteDAO.sendKeys(statusDAO);
        Thread.sleep(2000);
        
        //Approve DAO
        WebElement btnApprove = driver.findElement(By.id("IButtonApprove"));
        btnApprove.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Apakah anda yakin nasabah ini akan diapprove?']")));
        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "",true, testResults);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='ant-btn css-12lqnri ant-btn-primary ant-btn-color-primary ant-btn-variant-solid']//span[text()='Ok']")));
        WebElement btnOK = driver.findElement(By.xpath("//button[@class='ant-btn css-12lqnri ant-btn-primary ant-btn-color-primary ant-btn-variant-solid']//span[text()='Ok']"));
        btnOK.click();
        Thread.sleep(10000);

        boolean isApprovalUnsuccessful = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h5[text()='Approval is Unsuccessful']"))) != null;

        if (isApprovalUnsuccessful) {
            // Handle unsuccessful approval
            System.out.println("Approval failed.");
            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Gagal melakukan approval",true, testResults); // Capture a screenshot if approval fails
            driver.findElement(By.xpath("//span[text()='Close']")).click();
        } else {
            // Wait for the confirmation dialog "Apakah anda yakin nasabah ini akan diapprove?"
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Berhasil Approve']")));
            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Berhasil melakukan approval DAO",true, testResults);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='ant-btn css-12lqnri ant-btn-primary ant-btn-color-primary ant-btn-variant-solid ant-btn-lg']//span[text()='OK']"))).click();
            Thread.sleep(5000);
        }
        
    }else if(statusDAO.equals("Reject")){

        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "12. Lakukan Reject DAO Data Nasabah", "",false, testResults);

        //Note DAO
        WebElement noteDAO = driver.findElement(By.id("noteFromDao"));
        js.executeScript("arguments[0].scrollIntoView(true);", noteDAO);
        noteDAO.click();
        noteDAO.sendKeys(statusDAO);
        Thread.sleep(2000);

        //Reject DAO
        WebElement btnReject = driver.findElement(By.id("IButtonReject"));
        btnReject.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Apakah anda yakin nasabah ini akan direject?']")));
        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "",true, testResults);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='ant-btn css-12lqnri ant-btn-primary ant-btn-color-primary ant-btn-variant-solid']//span[text()='Ok']")));
        WebElement btnOK = driver.findElement(By.xpath("//button[@class='ant-btn css-12lqnri ant-btn-primary ant-btn-color-primary ant-btn-variant-solid']//span[text()='Ok']"));
        btnOK.click();
        Thread.sleep(10000);

        boolean isRejectUnsuccessful = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Reject is Unsuccessful']"))) != null;

        if (isRejectUnsuccessful) {
            // Handle unsuccessful approval
            System.out.println("Reject failed.");
            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Gagal melakukan reject DAO",true, testResults); // Capture a screenshot if approval fails
        } else {
            // Wait for the confirmation dialog "Apakah anda yakin nasabah ini akan diapprove?"
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Berhasil Reject']")));
            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Berhasil melakukan reject DAO",true, testResults);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='ant-btn css-12lqnri ant-btn-primary ant-btn-color-primary ant-btn-variant-solid ant-btn-lg']//span[text()='OK']"))).click();
            Thread.sleep(5000);
        }
    }

        //Back to KYC Approval Online Menu
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Approval KYC Online']")));
        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "",true, testResults);
        Thread.sleep(3000);
    }
    
public void logoutDAO(WebDriver driver, WebDriverWait wait) throws Exception, IOException, InterruptedException{
    WebElement logout = driver.findElement(By.xpath("//div[@class='ant-avatar ant-avatar-lg ant-avatar-circle css-12lqnri']"));
    Actions action = new Actions(driver);
    action.moveToElement(logout);

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout"))).click();
    Thread.sleep(2000);


}
}


