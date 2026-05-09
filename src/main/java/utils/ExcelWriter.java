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