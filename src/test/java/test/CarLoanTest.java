package test;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;

import pages.CarLoan;
import utils.ScreenshotUtil;

public class CarLoanTest extends BaseTest {
    CarLoan page;
    JavascriptExecutor jse;
    WebDriverWait wait;
    ExtentTest test;
    private final String CAR_LOAN_URL = "https://www.hdfc.bank.in/car-loan/emi-calculator";
    @BeforeClass
    public void setUpPages() {
        // Use the driver inherited from BaseTest
        page = new CarLoan();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        jse = (JavascriptExecutor) driver;
    }

    @Test(priority = 15)
    public void navigatingToCarLoan(){
        test = extent.createTest("Car Loan: Navigation");
        // Navigate in the same browser window
        driver.get(CAR_LOAN_URL);
      
        jse.executeScript("window.scrollBy(0,600);");
        test.pass("Navigated to Car Loan EMI Calculator.");
        test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Navigated Successfully to Carloan page."));
    }

    @Test(priority = 16, dependsOnMethods = "navigatingToCarLoan")
    public void findingElements() {
        page.setElements(driver);
    }

    @Test(priority = 17, dependsOnMethods = "findingElements")
    public void enterValues() throws InterruptedException {
        page.enterValues();
        test.pass("Given Inputs to CarLoan");
        test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Values Passed Successfully."));
    }

    @Test(priority = 18, dependsOnMethods = "enterValues")
    public void printValues() throws InterruptedException {
        test = extent.createTest("Car Loan: Results Extraction");
        page.printResults(driver);
        test.pass("Car Loan results printed successfully.");
    }
}