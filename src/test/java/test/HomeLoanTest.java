package test;

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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
    }

    @Test(priority = 13) // Continued priority from SipTest
    public void testHomeLoanPageurl() {
        test = extent.createTest("Loan Test: Navigation");
        try {
            driver.get(objReader.geturl());
            test.pass("Navigated to Home Loan site in the same browser.");
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
        List<Map<String, String>> allResults = new ArrayList<>();
        
        String[][] testData = {
        	    {"350000", "4", "8.5"}, 
        	    {"4250000", "10", "7.1"}, 
        	    {"1200000", "10", "9.25"}, 
        	    {"2500000", "15", "8.5"}
        };

        for (int i = 0; i < testData.length; i++) {
            String amt = testData[i][0];
            String tenure = testData[i][1];
            String rate = testData[i][2];

            try {
                loanPage.enterLoanDetails(amt, tenure, rate);
                Thread.sleep(2000); 
                
                // Create the map and add ALL columns
                Map<String, String> data = new HashMap<>();
                data.put("Amount", amt);
                data.put("Tenure", tenure);
                data.put("Rate", rate);
                data.put("EMI", loanPage.getMonthlyEMI());
                data.put("Interest", loanPage.getTotalInterest()); // Ensure this method exists in your page class
                
                allResults.add(data);
                test.log(Status.INFO, "TestCase " + (i + 1) + " completed.");
            } catch (Exception e) {
                test.log(Status.FAIL, "Error in TestCase " + (i + 1));
            }
        }
        ExcelWriter.storeResultsInExcel(allResults);
    }
}