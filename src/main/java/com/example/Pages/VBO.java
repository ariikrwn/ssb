//package com.example.Pages;
//
//import com.example.Function.CaptureFunction;
//import com.example.Function.TestStep;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class VBO {
//
//    List<TestStep> testResults = new ArrayList<>();
//    XWPFDocument document;
//    XWPFTable tableStatus;
//
//    public VBO(XWPFDocument document, XWPFTable tableStatus) {
//        this.document = document;
//        this.tableStatus = tableStatus;
//    }
//
//    public void getUrl(WebDriver driver, String channel, String reffNum)
//            throws Exception, IOException, InterruptedException {
//
//        String step = "Akses VBO SSB";
//        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(60));
//        String urlVBO = Config.getProperty("url.vbo");
//
//        // Access VBO
//        if (channel.equals("OM") || channel.equals("3O")) {
//            driver.get(urlVBO + "/" + reffNum);
//        } else {
//            driver.get(urlVBO);
//        }
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.ant-typography")));
//        Thread.sleep(2000);
//
//        String actualURL = driver.getCurrentUrl();
//        String expectedURL = (channel.equals("OM") || channel.equals("3O")) ? urlVBO + "/" + reffNum : urlVBO;
//        boolean testPassed = actualURL.equals(expectedURL);
//        String status = testPassed ? "Passed" : "Failed";
//
//        // Capture screenshot and insert into Word file
//        if (testPassed) {
//            CaptureFunction.takeScreenshotAndInsertIntoWord(
//                    document,
//                    driver,
//                    step,
//                    "Result : Berhasil akses VBO SSB - " + status,
//                    false,
//                    new TestStep(1, step, testPassed ? "Passed" : "Failed"), status, tableStatus);
//        }
//    }
//
//    public void Login(WebDriver driver, JavascriptExecutor js, WebDriverWait wait, String username, String password) throws Exception, IOException, InterruptedException {
//        String step = "Input username dan password VBO";
//
//        WebElement uname = driver.findElement(By.id("u"));
//        uname.click();
//        uname.sendKeys(username);
//
//        WebElement pwd = driver.findElement(By.id("p"));
//        pwd.click();
//        pwd.sendKeys(password);
//
//        // Verify input values
//        String enteredUsername = (String) js.executeScript("return arguments[0].value;", uname);
//        String enteredPassword = (String) js.executeScript("return arguments[0].value;", pwd);
//
//        boolean testPassed = enteredUsername.equals(username) && enteredPassword.equals(password);
//        String status = testPassed ? "Passed" : "Failed";
//        TestStep resultStep = new TestStep(2, step, testPassed ? "Passed" : "Failed");
//
//        if (testPassed) {
//            CaptureFunction.takeScreenshotAndInsertIntoWord(
//                    document,
//                    driver,
//                    step,
//                    "Result : Berhasil input username dan password - " + status,
//                    false,
//                    resultStep, status, tableStatus);
//        }
//
//        WebElement buttonLogin = driver.findElement(By.cssSelector(".ant-btn"));
//        buttonLogin.click();
//        Thread.sleep(5000);
//
//        WebElement confirmReffNum = null;
//
//        try {
//            // Wait for the element to be visible
//            confirmReffNum = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.ant-btn:nth-child(2)")));
//        } catch (Exception e) {
//            // Handle the case when the element is not found
//            confirmReffNum = null;
//        }
//
//        if (confirmReffNum != null) {
//            // If element is found, it is not null
//            confirmReffNum.click();
//            Thread.sleep(5000);
//        }
//
//    }
//
//    public void VerifyData(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String NIK, String name)
//            throws Exception, IOException, InterruptedException {
//        boolean dataFound = false;
//        while (true) {
//            try {
//                wait.until(
//                        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-table-container']")));
//                WebElement nikElement = driver.findElement(By.xpath("//div[@class='ant-table-container']//td[text()='"
//                        + NIK
//                        + "']/following-sibling::td[@class='ant-table-cell ant-table-cell-fix-right ant-table-cell-fix-right-first']"));
//                if (nikElement.isDisplayed()) {
//                    nikElement.click();
//                    dataFound = true;
//                    break;
//                }
//            } catch (Exception e) {
//                // Move to next page if element not found
//                try {
//                    WebElement nextPageButton = driver.findElement(By.xpath("//li[@title='Next Page']"));
//                    js.executeScript("arguments[0].scrollIntoView(true);", nextPageButton);
//
//                    if (nextPageButton.isEnabled()) {
//                        nextPageButton.click();
//                        Thread.sleep(2000); // Allow time for the page to load
//                    } else {
//                        break; // No more pages to check, exit loop
//                    }
//                } catch (Exception ex) {
//                    break; // If no next button exists, exit loop
//                }
//            }
//        }
//
//        if (dataFound) {
//            wait.until(ExpectedConditions
//                    .visibilityOfElementLocated(By.xpath("//h5[@class='ant-typography css-a6g51m']")));
//            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "3. Cari data yang akan diverifikasi",
//                    "Result : Data yang akan diverifikasi adalah data dari " + name + " dengan NIK " + NIK + "", false,
//                    testResults);
//        } else {
//            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "3. Cari data yang akan diverifikasi",
//                    "Result: Data tidak ditemukan di semua halaman", false, testResults);
//        }
//
//        Thread.sleep(3000);
//    }
//
//    public void verifyKTP(WebDriver driver, WebDriverWait wait, JavascriptExecutor js)
//            throws Exception, IOException, InterruptedException {
//        // Verify Data KTP
//        WebElement NIK2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@title='NIK']")));
//        WebElement foto = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@title='Foto']")));
//        WebElement alamat = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ktpPos")));
//
//        js.executeScript("arguments[0].scrollIntoView(true);", NIK2);
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "4. Data KTP Nasabah", "", false, testResults);
//        js.executeScript("arguments[0].scrollIntoView(true);", foto);
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "", true, testResults);
//        js.executeScript("arguments[0].scrollIntoView(true);", alamat);
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Data KTP Nasabah sudah sesuai", true,
//                testResults);
//        Thread.sleep(3000);
//    }
//
//    public void verifyDataUpload(WebDriver driver, WebDriverWait wait, JavascriptExecutor js)
//            throws Exception, IOException, InterruptedException {
//        // Verify Data Upload
//        WebElement dataupload = driver.findElement(By.xpath("//div[@title='Data Upload']"));
//        js.executeScript("arguments[0].scrollIntoView(true);", dataupload);
//        Thread.sleep(2000);
//        dataupload.click();
//        Thread.sleep(2000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "5. Data Upload Nasabah",
//                "Result : Data Upload Nasabah sudah sesuai", false, testResults);
//        Thread.sleep(3000);
//    }
//
//    public void verifyDataPersonal(WebDriver driver, WebDriverWait wait, JavascriptExecutor js)
//            throws Exception, IOException, InterruptedException {
//        // Verify Data Personal
//        WebElement datapersonal = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Data Personal']")));
//        js.executeScript("arguments[0].scrollIntoView(true);", datapersonal);
//        datapersonal.click();
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "6. Data Personal Nasabah", "", false, testResults);
//
//        WebElement namalengkap = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Nama Lengkap']")));
//        WebElement alamatlengkap = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Kode Pos']")));
//
//        js.executeScript("arguments[0].scrollIntoView(true);", namalengkap);
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "", true, testResults);
//
//        js.executeScript("arguments[0].scrollIntoView(true);", alamatlengkap);
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Data Personal Nasabah sudah sesuai", true,
//                testResults);
//    }
//
//    public void verifyDataPekerjaan(WebDriver driver, WebDriverWait wait, JavascriptExecutor js)
//            throws Exception, IOException, InterruptedException {
//        // Verify Data Pekerjaan
//        WebElement datapekerjaan = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Data Pekerjaan']")));
//        js.executeScript("arguments[0].scrollIntoView(true);", datapekerjaan);
//        datapekerjaan.click();
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "6. Data Pekerjaan Nasabah", "", false, testResults);
//
//        WebElement amlStatus = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='AML Status']")));
//        WebElement alamatKantor = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Kode Pos']")));
//
//        js.executeScript("arguments[0].scrollIntoView(true);", amlStatus);
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "", true, testResults);
//
//        js.executeScript("arguments[0].scrollIntoView(true);", alamatKantor);
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Data Pekerjaan Nasabah sudah sesuai", true,
//                testResults);
//    }
//
//    public void verifyDataRekening(WebDriver driver, WebDriverWait wait, JavascriptExecutor js)
//            throws Exception, IOException, InterruptedException {
//        // Verify Data Pembukaan Rekening
//        WebElement dataPembukaanRekening = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Data Pembukaan Rekening']")));
//        js.executeScript("arguments[0].scrollIntoView(true);", dataPembukaanRekening);
//        dataPembukaanRekening.click();
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "7. Data Pembukaan Rekening Nasabah", "", false,
//                testResults);
//
//        WebElement debitcard = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Debit Card Number']")));
//        WebElement staffID = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Staff ID']")));
//
//        js.executeScript("arguments[0].scrollIntoView(true);", debitcard);
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "", true, testResults);
//
//        js.executeScript("arguments[0].scrollIntoView(true);", staffID);
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Data Pembukaan Rekening Nasabah sudah sesuai",
//                true, testResults);
//    }
//
//    public void verifyDokumentNasabah(WebDriver driver, WebDriverWait wait, JavascriptExecutor js)
//            throws Exception, IOException, InterruptedException {
//        // Verify Dokumen Nasabah
//        WebElement NPWP = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rc-tabs-0-tab-2")));
//        WebElement fotottd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rc-tabs-0-tab-3")));
//        WebElement dokumenpendukung = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.id("rc-tabs-0-tab-4")));
//
//        js.executeScript("arguments[0].scrollIntoView(true);", NPWP);
//        NPWP.click();
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "8. NPWP Nasabah", "Foto NPWP Nasabah sudah sesuai",
//                false, testResults);
//
//        js.executeScript("arguments[0].scrollIntoView(true);", fotottd);
//        fotottd.click();
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "9. Tanda Tangan Nasabah",
//                "Foto Tanda Tangan Nasabah sudah sesuai", false, testResults);
//
//        js.executeScript("arguments[0].scrollIntoView(true);", dokumenpendukung);
//        dokumenpendukung.click();
//        Thread.sleep(3000);
//        CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "10. Dokumen Pendukung Nasabah",
//                "Foto Dokumen Pendukung Nasabah sudah sesuai", false, testResults);
//    }
//
//    public void ApproveVBO(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String statusVBO, String Note)
//            throws Exception, IOException, InterruptedException {
//
//        WebElement verifynasabah = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("VERIFYALL")));
//        WebElement noteToDAO = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note")));
//        WebElement btnReject = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Reject Aplikasi']")));
//        WebElement butVerifikasi = wait
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Verifikasi']")));
//
//        if (statusVBO.equals("Reject")) {
//            noteToDAO.click();
//            noteToDAO.sendKeys(Note);
//            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "11. Input Note to DAO",
//                    "Berhasil input note to DAO", false, testResults);
//
//            js.executeScript("arguments[0].scrollIntoView(true);", btnReject);
//            btnReject.click();
//            WebElement rejectConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//h5[text()='Apakah anda yakin nasabah ini akan direject?']")));
//            rejectConfirmation.click();
//            Thread.sleep(3000);
//            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "12. Lakukan reject VBO data nasabah", "", false,
//                    testResults);
//
//            wait.until(ExpectedConditions
//                    .visibilityOfElementLocated(By.xpath("//div[text()='Data telah berhasil direject']")));
//            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Berhasil melakukan reject VBO", true,
//                    testResults);
//
//        } else if (statusVBO.equals("Approve")) {
//            // Verify All Data
//            js.executeScript("arguments[0].scrollIntoView(true);", verifynasabah);
//            verifynasabah.click();
//            Thread.sleep(3000);
//            wait.until(ExpectedConditions
//                    .visibilityOfElementLocated(By.xpath("//span[@class='ant-switch-inner-checked']")));
//            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "11. Checklist verifikasi Data Nasabah",
//                    "Result : Berhasil checklist seluruh verifikasi data nasabah", false, testResults);
//
//            // Note to DAO
//            noteToDAO.click();
//            noteToDAO.sendKeys("Approve");
//            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "12. Lakukan approval VBO", "", false, testResults);
//            js.executeScript("arguments[0].scrollIntoView(true);", butVerifikasi);
//            butVerifikasi.click();
//            Thread.sleep(3000);
//
//            // Approval VBO
//            WebElement approvalconfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//h5[text()='Apakah anda yakin nasabah sudah terverifikasi?']")));
//            approvalconfirmation.click();
//
//            WebElement butOK = wait.until(ExpectedConditions
//                    .visibilityOfElementLocated(By.cssSelector("button.ant-btn-primary:nth-child(2)")));
//            butOK.click();
//
//            wait.until(ExpectedConditions
//                    .visibilityOfElementLocated(By.xpath("//div[text()='Data telah berhasil diverifikasi']")));
//            CaptureFunction.takeScreenshotAndInsertIntoWord(driver, "", "Result : Berhasil melakukan approval VBO",
//                    true, testResults);
//        }
//
//    }
//
//    public void logoutVBO(WebDriver driver) throws InterruptedException, IOException {
//        WebElement logout = driver.findElement(By.xpath("//span[text()='Logout']"));
//        logout.click();
//        Thread.sleep(3000);
//
//        driver.quit();
//    }
//
//}
