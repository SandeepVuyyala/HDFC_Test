package test; // Ensure this matches your project folder structure

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomeLoanEligibility;

public class HomeLoanEligibilityTest extends BaseTest {

    // REMOVED: WebDriver driver; (Child uses the static driver from BaseTest)
    HomeLoanEligibility homeLoanPage;

    @BeforeClass
    public void setUp() {
        // Initialize the page object using the refactored constructor
        homeLoanPage = new HomeLoanEligibility(driver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
    }

    @Test(priority = 16) // Priority follows CarLoan (12-15)
    public void navigatingToHomeLoanEligibility() throws InterruptedException {
        // Uses the inherited driver from BaseTest
        driver.get("https://homeloans.hdfc.bank.in/home-loan-eligibility-calculator");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,365)");
        Thread.sleep(2000);
    }

    @Test(priority = 17, dependsOnMethods = "navigatingToHomeLoanEligibility")
    public void findingElements() {
        // Initialize elements within the page object
        homeLoanPage.setElements();
    }

    @Test(priority = 18, dependsOnMethods = "findingElements")
    public void enterValues() throws InterruptedException {
        homeLoanPage.enterValues();
    }

    @Test(priority = 19, dependsOnMethods = "enterValues")
    public void CaptureResults() {
        homeLoanPage.printResults();
    }

    // REMOVED: tearDown() - BaseTest handles driver.quit() at the suite level
}