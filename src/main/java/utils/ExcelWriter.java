package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    public static void writeResultsToExcel(String emi, String interest, String principal) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Loan Results");

        // Set Headers
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Monthly EMI");
        header.createCell(1).setCellValue("Total Interest");
        header.createCell(2).setCellValue("Principal Amount");

        // Set Data
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(emi);
        dataRow.createCell(1).setCellValue(interest);
        dataRow.createCell(2).setCellValue(principal);

        // Ensure the output directory exists
        File folder = new File("test-output-data");
        if (!folder.exists()) {
            folder.mkdir();
        }

        try (FileOutputStream fileOut = new FileOutputStream("test-output-data/LoanResults.xlsx")) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}