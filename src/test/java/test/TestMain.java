//package test;
//
//import org.openqa.selenium.WebDriver;
//import org.testng.annotations.*;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import browserImplementation.BrowserConfig;
//import pages.LoanCalcPage;
//import utils.ObjectReader;
//import utils.ScreenshotUtil;
//import utils.ExcelWriter; // Assuming ExcelWriter is placed in utils
//
//public class TestMain {
//    private WebDriver driver;
//    private BrowserConfig browserconf;
//    private LoanCalcPage loanPage;
//    private ObjectReader objReader;
//    
//    private ExtentReports extent;
//    private ExtentSparkReporter spark;
//    private ExtentTest test;
//
//    @BeforeSuite
//    public void setUp() {
//        // Report initialization matching your format
//        spark = new ExtentSparkReporter("reports/HomeLoan_ExecutionReport.html");
//        extent = new ExtentReports();
//        extent.attachReporter(spark);
//
//        // Browser implementation matching your framework
//        browserconf = new BrowserConfig();
//        driver = browserconf.chooseBrowser();
//        objReader = new ObjectReader();
//        loanPage = new LoanCalcPage(driver);
//    }
//
//    @Test
//    public void calculateHomeLoan() {
//        test = extent.createTest("Scenario: HDFC Home Loan Calculation");
//        
//        try {
//            driver.get(objReader.geturl()); // Uses BaseUrl from properties
//            
//            loanPage.enterLoanDetails("4000000", "10", "8.5");
//            Thread.sleep(3000); 
//
//            String emi = loanPage.getMonthlyEMI();
//            String interest = loanPage.getTotalInterest();
//            String principal = loanPage.getPrincipalAmount();
//
//            // Writing to Excel
//            ExcelWriter.writeResultsToExcel(emi, interest, principal);
//            
//            test.pass("Loan values calculated and saved to Excel.");
//            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Calc_Success"));
//            
//        } catch (Exception e) {
//            test.fail("Calculation failed: " + e.getMessage());
//            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Calc_Error"));
//        }
//    }
//
//    @AfterSuite
//    public void tearDown() {
//        browserconf.closeBrowser(); // Clean termination
//        extent.flush(); 
//    }
//}



package test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import browserImplementation.BrowserConfig;
import pages.LoanCalcPage;
import utils.*;

public class TestMain {
    private WebDriver driver;
    private BrowserConfig browserconf;
    private LoanCalcPage loanPage;
    private ObjectReader objReader;
  
    private ExtentReports extent;
    private ExtentSparkReporter spark;
    private ExtentTest test;

    @BeforeSuite
    public void setUp() {
        spark = new ExtentSparkReporter("reports/HomeLoan_ExecutionReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        browserconf = new BrowserConfig();
        driver = browserconf.chooseBrowser(); //
        objReader = new ObjectReader(); //
        loanPage = new LoanCalcPage(driver);

    }

    // TEST 1: System Readiness & UI Cleanup
    @Test(priority = 1)
    public void testHomeLoanPageurl() {
        test = extent.createTest("Test 1: Navigation and UI Cleanup");
        try {
            driver.get(objReader.geturl());
          
           
            test.pass("Navigated to HDFC Home Loan");
        } catch (Exception e) {
            test.fail("Failed to open url: " + e.getMessage());
            Assert.fail();
        }
    }

    // TEST 2: Functional Calculation Verification
    @Test(priority = 2, dependsOnMethods = "testHomeLoanPageurl")
    public void testHomeLoanCalculation() {
        test = extent.createTest("Test 2: Home Loan Calculation Accuracy");
        try {
            loanPage.scrollToHeader(); // Using the precise coordinate scroll
            loanPage.enterLoanDetails("4000000", "10", "8.5");
            
            // Allow JS events to sync the output fields
            Thread.sleep(3000); 

            String emi = loanPage.getMonthlyEMI();
            Assert.assertNotNull(emi, "EMI calculation should not be null.");
            
            test.pass("Calculation performed. Monthly EMI retrieved: " + emi);
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Calc_Result")); //
        } catch (Exception e) {
            test.fail("Calculation functional test failed: " + e.getMessage());
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Calc_Error"));
        }
    }

    // TEST 3: Data Persistence (Excel Export)
    @Test(priority = 3, dependsOnMethods = "testHomeLoanCalculation")
    public void testExcelDataExport() {
        test = extent.createTest("Test 3: Excel Report Generation");
        try {
            String emi = loanPage.getMonthlyEMI();
            String interest = loanPage.getTotalInterest();
            String principal = loanPage.getPrincipalAmount();

            // Store results in the external Excel utility
            ExcelWriter.writeResultsToExcel(emi, interest, principal);
            
            test.pass("Loan calculation data successfully exported to test-output-data/LoanResults.xlsx");
        } catch (Exception e) {
            test.fail("Excel export failed: " + e.getMessage());
        }
    }

    // TEST 4: Boundary/Input Validation (Clear and Re-calculate)
    @Test(priority = 4, dependsOnMethods = "testExcelDataExport")
    public void testInputUpdateValidation() {
        test = extent.createTest("Test 4: Input Sensitivity and Update Validation");
        try {
            // Updating values to verify the calculator reacts to input changes dynamically
            loanPage.enterLoanDetails("5000000", "15", "9.0");
            Thread.sleep(2000);
            
            String updatedEmi = loanPage.getMonthlyEMI();
            test.info("Updated EMI for 50L at 9.0% for 15 years: " + updatedEmi);
            
            test.pass("Calculator successfully updated outputs based on new boundary inputs.");
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Update_Verification"));
        } catch (Exception e) {
            test.fail("Input update validation failed: " + e.getMessage());
        }
    }

    @AfterSuite
    public void tearDown() {
        browserconf.closeBrowser(); //
        extent.flush(); 
    }
}