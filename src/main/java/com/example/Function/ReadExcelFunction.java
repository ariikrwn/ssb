package com.example.Function;

import com.example.dto.FileRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadExcelFunction {

    //Method to Read Excel Data
    public static List<FileRecord> readExcelData(String excelFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(1); // Assuming data is in the first sheet
        int rowCount = sheet.getPhysicalNumberOfRows();
        int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();

        String[][] excelData = new String[rowCount][columnCount];
        List<FileRecord> recordList = new ArrayList<>();

        // Reading the data into the 2D array
        for (int i = 0; i < rowCount; i++) {
            if (i == 0) continue;
            Row row = sheet.getRow(i);
            for (int j = 0; j < columnCount; j++) {
                if (row.getCell(j) != null) {
                    excelData[i][j] = row.getCell(j).toString();
                } else {
                    excelData[i][j] = "";
                }
            }
        }
        workbook.close();
        fileInputStream.close();

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

        for (int i = 1; i < excelData.length; i++) {
            FileRecord record = new FileRecord();

            record.setTestScriptId(excelData[i][testScriptIdIndex]);
            record.setNik(excelData[i][nikColumnIndex]);
            record.setName(excelData[i][nameColumnIndex]);
            record.setReffNum(excelData[i][reffNumColumnIndex]);
            record.setChannel(excelData[i][channelColumnIndex]);
            record.setChannelDesc(excelData[i][channelDescColumnIndex]);
            record.setNote(excelData[i][noteColumnIndex]);
            record.setStatusVBO(excelData[i][statusVBOColumnIndex]);
            record.setStatusDAO(excelData[i][statusDAOColumnIndex]);
            record.setIsActive(excelData[i][isActiveColumnIndex]);

            recordList.add(record);
        }
        return recordList;
    }

    // Method to print active rows (Active = "Y")
    public static List<FileRecord> printActiveRows(String excelFilePath) throws IOException {
        return readExcelData(excelFilePath).stream().filter(ReadExcelFunction::isActive).collect(Collectors.toList());
        // Start iterating from 1 to skip the header row (row index 0)
//        for (int i = 1; i < excelData.size(); i++) {
//            if (isActive(excelData.get(i))) {
//                // Print row data if it's active
////                for (int j = 0; j < excelData[i].length; j++) {
////                    System.out.print(excelData[i][j] + "\t");
////                }
//                System.out.println();
//            }
//        }
    }

    // Method to check if the row is active
    public static boolean isActive(FileRecord data) {
        return "Y".equalsIgnoreCase(data.getIsActive());  // Check if "Active" column has value "Y"
    }

    //Method to get Column Name by index
    public static int getColumnIndexByName(String[][] data, String columnName) {
        String[] header = data[0]; // First row contains column names
        for (int i = 0; i < header.length; i++) {
            System.out.println("Checking column: '" + header[i] + "'"); // Debugging line
            if (header[i].trim().equalsIgnoreCase(columnName.trim())) {  // Trim spaces and check case-insensitive
                return i;  // Return the index of the column
            }
        }
        System.out.println("Column '" + columnName + "' not found");  // Debugging line
        return -1;  // Return -1 if column name is not found
    }
}