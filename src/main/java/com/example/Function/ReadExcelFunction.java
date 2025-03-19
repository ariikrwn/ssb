package com.example.Function;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadExcelFunction {

//Method to Read Excel Data
public static String[][] readExcelData(String excelFilePath) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
    Workbook workbook = new XSSFWorkbook(fileInputStream);
    Sheet sheet = workbook.getSheetAt(1); // Assuming data is in the first sheet
    int rowCount = sheet.getPhysicalNumberOfRows();
    int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
            
    String[][] data = new String[rowCount][columnCount];
            
    // Reading the data into the 2D array
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);
                for (int j = 0; j < columnCount; j++) {
                    if (row.getCell(j) != null) {
                        data[i][j] = row.getCell(j).toString();
                    } else {
                        data[i][j] = "";
                    }
            }
        }
    workbook.close();
    fileInputStream.close();
    return data;
}
    
// Method to print active rows (Active = "Y")
public static void printActiveRows(String excelFilePath, int sheetIndex, int isActiveColumnIndex) throws IOException {
    String[][] excelData = readExcelData(excelFilePath);
        // Start iterating from 1 to skip the header row (row index 0)
        for (int i = 1; i < excelData.length; i++) {
            if (isActive(excelData, i, isActiveColumnIndex)) {
                // Print row data if it's active
                for (int j = 0; j < excelData[i].length; j++) {
                    System.out.print(excelData[i][j] + "\t");
                }
                System.out.println();
            }
        }
}
    
// Method to check if the row is active
public static boolean isActive(String[][] data, int rowIndex, int isActiveColumnIndex) {
    return "Y".equalsIgnoreCase(data[rowIndex][isActiveColumnIndex]);  // Check if "Active" column has value "Y"
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