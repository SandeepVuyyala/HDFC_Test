//package homeloan_hdfc;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//public class Test_HomeLoan {
//		WebDriver driver;
//   
//	
//		@BeforeMethod
//		public void setup() {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("https://homeloans.hdfc.bank.in/");
//		}
//        
//        @Test
//        public void HomeLoan() throws InterruptedException {
//        // Create a JavascriptExecutor instance
//        JavascriptExecutor jse = (JavascriptExecutor) driver;
//
//        // Locate the element
//        WebElement loan_amt = driver.findElement(By.id("loan-amt-inp"));
//
//    
//        jse.executeScript("arguments[0].value='4000000';", loan_amt);
//        jse.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", loan_amt);
//        jse.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", loan_amt);
//        System.out.println("Value set successfully via JavaScript.");
//        
//        
//        Thread.sleep(5000);
//        WebElement tenure_duration = driver.findElement(By.id("tenure-inp"));
//        
//        jse.executeScript("arguments[0].value='10';",tenure_duration);
//        jse.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", tenure_duration);
//        jse.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", tenure_duration);
//        
//        System.out.println("Tenure set");
//        Thread.sleep(5000);
//        WebElement intrest_rate = driver.findElement(By.id("rate-inp"));
//        jse.executeScript("arguments[0].value='8.5';",intrest_rate);
//        jse.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", intrest_rate);
//        jse.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", intrest_rate);
//        
//        Thread.sleep(5000);
//
//        WebElement emiResult = driver.findElement(By.id("monthly-emi"));
//        WebElement intrest_amt = driver.findElement(By.id("interest-amt"));
//        WebElement principal_amt = driver.findElement(By.id("principal-amt"));
//        // Get the text from the element
//        String emiValue = emiResult.getText();
//        String intrestvalue = intrest_amt.getText();
//        String principalvalue = principal_amt.getText(); 
//
//        Thread.sleep(5000);
//        System.out.println("Montly Emi: "+ emiValue);
//        System.out.println("Total Intrest: "+ intrestvalue);
//        System.out.println("Principal Amount: "+principalvalue);
//        }
//        
//        @AfterMethod
//        public void tearDown() {
//        	  driver.quit();
//        }
//    
//}





package homeloan_hdfc;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

public class Test_HomeLoan {
    
    WebDriver driver;
    WebDriverWait wait;

    // --- PAGE REPOSITORY (Locators) ---
    private final By LOAN_AMT_FIELD = By.id("loan-amt-inp");
    private final By TENURE_FIELD = By.id("tenure-inp");
    private final By RATE_FIELD = By.id("rate-inp");
    private final By EMI_DISPLAY = By.id("monthly-emi");
    private final By INTEREST_DISPLAY = By.id("interest-amt");
    private final By PRINCIPAL_DISPLAY = By.id("principal-amt");
    private final String url = "https://homeloans.hdfc.bank.in/";

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);
    }

    @Test
    public void calculateHomeLoan() throws InterruptedException, IOException {
        // 1. Set Values using our Page Methods
        updateFieldViaJS(LOAN_AMT_FIELD, "4000000");
        updateFieldViaJS(TENURE_FIELD, "10");
        updateFieldViaJS(RATE_FIELD, "8.5");

        // 2. Wait for calculation to sync
        Thread.sleep(3000); 

        // 3. Extract and Print Results
        System.out.println("Monthly EMI: " + getElementText(EMI_DISPLAY));
        System.out.println("Total Interest: " + getElementText(INTEREST_DISPLAY));
        System.out.println("Principal Amount: " + getElementText(PRINCIPAL_DISPLAY));
        
        String emi = getElementText(EMI_DISPLAY);
        String interest = getElementText(INTEREST_DISPLAY);
        String principal = getElementText(PRINCIPAL_DISPLAY);
        
    
        writeResultsToExcel(emi, interest, principal);
        
        System.out.println("Data successfully written to Excel file.");
    }    

    
    private void writeResultsToExcel(String emi, String interest, String principal) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Loan Results");

        // Create Header Row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Monthly EMI");
        header.createCell(1).setCellValue("Total Interest");
        header.createCell(2).setCellValue("Principal Amount");

        // Create Data Row
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(emi);
        dataRow.createCell(1).setCellValue(interest);
        dataRow.createCell(2).setCellValue(principal);

    
        File folder = new File("test-output-data");
        if (!folder.exists()) {
            folder.mkdir(); // Creates the folder if it doesn't exist
        }

        try (FileOutputStream fileOut = new FileOutputStream(folder.getPath() + "/LoanResults.xlsx")) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
  
    private void updateFieldViaJS(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        
        jse.executeScript("arguments[0].value='" + value + "';", element);
        jse.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
        jse.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", element);
    }

    private String getElementText(By locator) {
        return driver.findElement(locator).getText();
    }

    @AfterMethod
    public void tearDown() {
     
            driver.quit();
        
    }
}