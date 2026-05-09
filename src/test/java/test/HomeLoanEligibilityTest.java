package test;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import pages.HomeLoanEligibility;
import utils.ScreenshotUtil;

public class HomeLoanEligibilityTest extends BaseTest {

    HomeLoanEligibility homeLoanPage;
    private ExtentTest test;
    private final String HOME_LOANELIGIBILITY_URL = "https://homeloans.hdfc.bank.in/home-loan-eligibility-calculator";

    @BeforeClass
    public void setUp() {
        homeLoanPage = new HomeLoanEligibility(driver);
    }

    @Test(priority = 16)
    public void navigatingToHomeLoanEligibility() {
        test = extent.createTest("Home Loan Eligibility: Navigation");
        try {
            driver.get(HOME_LOANELIGIBILITY_URL);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,365)");
            Thread.sleep(2000);
            
            test.pass("Navigated to Eligibility Calculator and scrolled to view.");
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Eligibility_Page_Load"));
        } catch (Exception e) {
            test.fail("Navigation failed: " + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 17, dependsOnMethods = "navigatingToHomeLoanEligibility")
    public void findingElements() {
        test = extent.createTest("Home Loan Eligibility: Initialize Elements");
        try {
            homeLoanPage.setElements();
            test.pass("All input elements located successfully.");
        } catch (Exception e) {
            test.fail("Failed to locate elements: " + e.getMessage());
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Element_Discovery_Failure"));
            Assert.fail();
        }
    }

    @Test(priority = 18, dependsOnMethods = "findingElements")
    public void enterValues() {
        test = extent.createTest("Home Loan Eligibility: Data Entry");
        try {
            homeLoanPage.enterValues();
            test.pass("Eligibility values entered successfully.");
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Values_Entered"));
        } catch (Exception e) {
            test.fail("Data entry failed: " + e.getMessage());
        }
    }

    @Test(priority = 19, dependsOnMethods = "enterValues")
    public void CaptureResults() {
        test = extent.createTest("Home Loan Eligibility: Capture Results");
        try {
            // Assuming printResults is modified to return a string or you fetch results here
            homeLoanPage.printResults(); 
            test.log(Status.PASS, "Eligibility results captured and printed to console.");
            test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Eligibility_Results"));
        } catch (Exception e) {
            test.fail("Failed to capture results: " + e.getMessage());
        }
    }
}