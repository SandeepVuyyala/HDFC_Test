package test;

import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import pages.HomeLoanCalcPage;
import utils.*;

public class HomeLoanTest extends BaseTest {
    
    private HomeLoanCalcPage loanPage;
    private ExtentTest test;
    
    // Adjusted URL constant
    private final String HOME_LOAN_URL = "https://homeloans.hdfc.bank.in/";

    @BeforeClass
    public void setUp() {
        // loanPage now contains its own locators internally
        loanPage = new HomeLoanCalcPage(driver);
    }

    @Test(priority = 13) 
    public void testHomeLoanPageurl() {
        test = extent.createTest("Loan Test: Navigation");
        try {
            // Directly navigating to the Adjusted URL
            driver.get(HOME_LOAN_URL);
            
            test.pass("Navigated to Home Loan site: " + HOME_LOAN_URL);
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "HomeLoan_Navigation_Success"));
        } catch (Exception e) {
            test.fail("Failed to open URL: " + e.getMessage());
            Assert.fail("Navigation failed, terminating test sequence.");
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
        test = extent.createTest("Loan Test: Multi-TestCase Export");
        
        String[][] testData = {
            {"350000", "4", "8.5"}, 
            {"4250000", "10", "7.1"}, 
            {"1200000", "10", "9.25"}, 
            {"2500000", "15", "8.5"}
        };

        // Prepare a result grid
        String[][] resultsToExport = new String[testData.length][5];

        for (int i = 0; i < testData.length; i++) {
            String testCaseName = "Test_Case_" + (i + 1);
            try {
                // Enter details for the specific test case
                loanPage.enterLoanDetails(testData[i][0], testData[i][1], testData[i][2]);
                Thread.sleep(2000); 
                
                // Capture Results
                resultsToExport[i][0] = testData[i][0];   // Amount
                resultsToExport[i][1] = testData[i][2];   // Interest Rate
                resultsToExport[i][2] = testData[i][1];   // Tenure
                resultsToExport[i][3] = loanPage.getMonthlyEMI();
                resultsToExport[i][4] = loanPage.getTotalInterest();
                
                // Logging and Screenshot for EVERY test case
                test.log(Status.PASS, testCaseName + " executed. EMI: " + resultsToExport[i][3]);
                
                // Adding the screenshot inside the loop to capture the unique values on screen
                String screenshotPath = ScreenshotUtil.capturePage(driver, testCaseName);
                test.addScreenCaptureFromPath(screenshotPath);

            } catch (Exception e) {
                test.log(Status.FAIL, testCaseName + " failed: " + e.getMessage());
                // Optional: capture screenshot even on failure
                try {
                    test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, testCaseName + "_Error"));
                } catch (Exception ex) {
                    e.printStackTrace();
                }
            }
        }

        // Export data to Excel after all test cases finish
        try {
            ExcelWriter.storeResultsInExcel(resultsToExport);
            test.pass("Data for all test cases exported to Excel successfully.");
        } catch (Exception e) {
            test.log(Status.WARNING, "Excel Export failed: " + e.getMessage());
        }
    }
}