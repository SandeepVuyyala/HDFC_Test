package test;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LandingPage;
import utils.ScreenshotUtil;

import com.aventstack.extentreports.ExtentTest;

public class LandingPageTest extends BaseTest {
    LandingPage lpage;
    ExtentTest test;

    private final String LANDING_PAGE_URL = "https://www.hdfcbank.com/";
    @BeforeClass
    public void initializeDriver() {
        lpage = new LandingPage(driver);
    }

    @Test(priority=1)
    public void hoverToDiscoverProducts() throws InterruptedException {
        test = extent.createTest("Landing Page: Discover Products");
        driver.get(LANDING_PAGE_URL); // Navigate to start the flow
        lpage.hoverToDeposits();
        test.pass("Hovered over Deposits successfully");
        test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Navigated Successfully to Hdfc Landing Page."));
    }

    @Test(priority=2, dependsOnMethods="hoverToDiscoverProducts")
    public void testClickOnfixedDeposite() throws InterruptedException {
        lpage.click_fixedDeposite();
        test.pass("Clicked on Fixed Deposit");
    }
}