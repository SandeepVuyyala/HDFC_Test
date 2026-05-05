package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import browserImplementation.BrowserConfig;
import pages.LoanCalcPage;
import utils.ObjectReader;
import utils.ScreenshotUtil;
import utils.ExcelWriter; // Assuming ExcelWriter is placed in utils

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
        // Report initialization matching your format
        spark = new ExtentSparkReporter("reports/HomeLoan_ExecutionReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Browser implementation matching your framework
        browserconf = new BrowserConfig();
        driver = browserconf.chooseBrowser();
        objReader = new ObjectReader();
        loanPage = new LoanCalcPage(driver);
    }

    @Test
    public void calculateHomeLoan() {
        test = extent.createTest("Scenario: HDFC Home Loan Calculation");
        
        try {
            driver.get(objReader.geturl()); // Uses BaseUrl from properties
            
            loanPage.enterLoanDetails("4000000", "10", "8.5");
            Thread.sleep(3000); 

            String emi = loanPage.getMonthlyEMI();
            String interest = loanPage.getTotalInterest();
            String principal = loanPage.getPrincipalAmount();

            // Writing to Excel
            ExcelWriter.writeResultsToExcel(emi, interest, principal);
            
            test.pass("Loan values calculated and saved to Excel.");
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Calc_Success"));
            
        } catch (Exception e) {
            test.fail("Calculation failed: " + e.getMessage());
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Calc_Error"));
        }
    }

    @AfterSuite
    public void tearDown() {
        browserconf.closeBrowser(); // Clean termination
        extent.flush(); 
    }
}