package test;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import pages.LoanCalcPage;
import utils.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeLoanTest extends BaseTest {
    
    private LoanCalcPage loanPage;
    private ObjectReader objReader;
    private ExtentTest test;

    @BeforeClass
    public void setUp() {
        objReader = new ObjectReader();
        // Uses the existing driver from the single browser session
        loanPage = new LoanCalcPage(driver);
    }

    @Test(priority = 13) // Continued priority from SipTest
    public void testHomeLoanPageurl() {
        test = extent.createTest("Loan Test: Navigation");
        try {
            driver.get(objReader.geturl());
            test.pass("Navigated to Home Loan site in the same browser.");
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Navigated to HomeLoan Page Successfully"));
        } catch (Exception e) {
            test.fail("Failed to open URL: " + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 14, dependsOnMethods = "testHomeLoanPageurl")
    public void testHomeLoanCalculation() {
        test = extent.createTest("Loan Test: Standard Calculation");
        try {
            loanPage.scrollToHeader();
            loanPage.enterLoanDetails("4000000", "10", "8.5");
            Thread.sleep(3000); 

            String emi = loanPage.getMonthlyEMI();
            test.pass("Standard calculation verified. EMI: " + emi);
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Standard_Calc"));
        } catch (Exception e) {
            test.fail("Calculation test failed: " + e.getMessage());
        }
    }

    @Test(priority = 15, dependsOnMethods = "testHomeLoanCalculation")
    public void testMultipleInputScenarios() {
        test = extent.createTest("Loan Test: Multi-Scenario Export");
        
        String[][] testData = {
            {"350000", "4", "8.5"}, 
            {"4250000", "10", "7.1"}, 
            {"1200000", "10", "9.25"}, 
            {"2500000", "15", "8.5"}
        };

        // Prepare a result grid (Rows = same as test data, Columns = 5)
        String[][] resultsToExport = new String[testData.length][5];

        for (int i = 0; i < testData.length; i++) {
            try {
                loanPage.enterLoanDetails(testData[i][0], testData[i][1], testData[i][2]);
                Thread.sleep(2000); 
                
                // Fill row only on success
                resultsToExport[i][0] = testData[i][0];              // Amount
                resultsToExport[i][1] = testData[i][2];              // Interest Rate
                resultsToExport[i][2] = testData[i][1];              // Tenure
                resultsToExport[i][3] = loanPage.getMonthlyEMI();    // EMI
                resultsToExport[i][4] = loanPage.getTotalInterest(); // Interest
                
                test.log(Status.PASS, "Scenario " + (i + 1) + " recorded.");
            } catch (Exception e) {
                test.log(Status.FAIL, "Scenario " + (i + 1) + " failed: " + e.getMessage());
                // resultToExport[i] stays null/empty here
            }
        }

        try {
            ExcelWriter.storeResultsInExcel(resultsToExport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}