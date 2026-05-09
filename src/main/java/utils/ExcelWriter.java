//package utils;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//public class ExcelWriter {
//
//    /**
//     * Stores multiple loan calculation scenarios into an Excel file.
//     * @param results A list containing maps of scenario data (Amount, Rate, Tenure, EMI, etc.)
//     */
//    public static void storeResultsInExcel(List<Map<String, String>> results) {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Home Loan Scenarios");
//
//        // 1. Define Headers
//        String[] columns = {"Amount", "Interest Rate (%)", "Tenure (Yrs)", "Monthly EMI", "Interest"};
//        Row headerRow = sheet.createRow(0);
//        
//        for (int i = 0; i < columns.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(columns[i]);
//            
//            // Optional: Make headers bold
//            CellStyle style = workbook.createCellStyle();
//            Font font = workbook.createFont();
//            font.setBold(true);
//            style.setFont(font);
//            cell.setCellStyle(style);
//        }
//
//        // 2. Fill Data Rows
//        int rowNum = 1;
//        int val = 0;
//        for (Map<String, String> scenario : results) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(scenario.get("Amount"));
//            row.createCell(1).setCellValue(scenario.get("Rate"));
//            row.createCell(2).setCellValue(scenario.get("Tenure"));
//            row.createCell(3).setCellValue(scenario.get("EMI"));
//            row.createCell(4).setCellValue(scenario.get("Interest"));
//          
//           
//        }
//
//        // 3. Save to File System
//        try {
//            File folder = new File("test-output-data");
//            if (!folder.exists()) folder.mkdir();
//
//            FileOutputStream fileOut = new FileOutputStream("test-output-data/Loan_Scenarios_Report.xlsx");
//            workbook.write(fileOut);
//            fileOut.close();
//            workbook.close();
//            System.out.println("Excel report generated: test-output-data/HomeLoan_TestCases_Report.xlsx");
//        } catch (IOException e) {
//            System.err.println("Error writing Excel file: " + e.getMessage());
//        }
//    }
//}




package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class ExcelWriter {

    public static void storeResultsInExcel(String[][] data) throws IOException {
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Home Loan Scenarios");

        // 1. Define Headers (Hardcoded)
        String[] columns = {"Amount", "Interest Rate (%)", "Tenure (Yrs)", "Monthly EMI", "Interest"};
        XSSFRow headerRow = sheet.createRow(0);
        
        // Optional: Style for headers
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < columns.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // 2. Fill Data Rows (Starting from Row 1)
        for (int i = 0; i < data.length; i++) {
            // i + 1 ensures we don't overwrite the header at row 0
            XSSFRow row = sheet.createRow(i + 1);
            
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j]);
            }
        }

        // 3. Save to File System
        try {
            File folder = new File("test-output-data");
            if (!folder.exists()) folder.mkdir();

            FileOutputStream fileOut = new FileOutputStream("test-output-data/Loan_Scenarios_Report.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            
            System.out.println("Excel report generated with hardcoded headers!");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}