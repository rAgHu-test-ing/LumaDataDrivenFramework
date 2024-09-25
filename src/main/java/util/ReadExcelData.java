package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelData {

    public List<HashMap<String, String>> inputData() {
    	
        List<HashMap<String, String>> loginDataList = new ArrayList<>(); // List to store test data
        
        String inputDataExcelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\LumaDDF.xlsx";
        File inputDataFile = new File(inputDataExcelPath);
        
        try {
            FileInputStream fis = new FileInputStream(inputDataFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("data"); // Assuming the sheet is named "data"
            
            // Read the first row to get the header (column names)
            Row headerRow = sheet.getRow(1); // Row 1 contains headers in the sheet
            Map<String, Integer> columnMap = new HashMap<>();
            
            // Map the column name to its index and print the names for debugging
            for (int cellNum = 0; cellNum < headerRow.getLastCellNum(); cellNum++) {
            	
                String columnName = headerRow.getCell(cellNum).getStringCellValue();
                columnMap.put(columnName, cellNum);
            }

            // Start from row 2 (index 2) to skip the header
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null && row.getCell(columnMap.get("Runmode")).getStringCellValue().equalsIgnoreCase("Y")) {
                    HashMap<String, String> loginData = new HashMap<>();
                    
                    // Using column names to get the values
                    String username = row.getCell(columnMap.get("Username")).getStringCellValue();
                    String password = row.getCell(columnMap.get("Password")).getStringCellValue();
                    
                    loginData.put("email", username); // Use "email" key for the email field
                    loginData.put("password", password);

                    // Add this test case to the list
                    loginDataList.add(loginData);
                }
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return loginDataList; // Returning the list of login data
    }
}