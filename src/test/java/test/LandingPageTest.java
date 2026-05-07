package test;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LandingPage;
import com.aventstack.extentreports.ExtentTest;

public class LandingPageTest extends BaseTest {
    LandingPage lpage;
    ExtentTest test;

    @BeforeClass
    public void initializeDriver() {
        // Inherited driver is already initialized by @BeforeSuite
        lpage = new LandingPage(driver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
    }

    @Test(priority=1)
    public void hoverToDiscoverProducts() throws InterruptedException {
        test = extent.createTest("Landing Page: Discover Products");
        driver.get("https://www.hdfcbank.com/"); // Navigate to start the flow
        lpage.hoverToDeposits();
        test.pass("Hovered over Deposits successfully");
    }

    @Test(priority=2, dependsOnMethods="hoverToDiscoverProducts")
    public void testClickOnfixedDeposite() throws InterruptedException {
        lpage.click_fixedDeposite();
        test.pass("Clicked on Fixed Deposit");
    }
}