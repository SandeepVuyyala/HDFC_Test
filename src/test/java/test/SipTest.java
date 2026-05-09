



package test;

import java.io.IOException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.SipPage;
import utils.ScreenshotUtil;

import com.aventstack.extentreports.ExtentTest;

public class SipTest extends BaseTest {
    
    SipPage sipPage;
    ExtentTest test;
    
    private final String SIPTest_URL = "https://www.hdfc.bank.in/mutual-funds/sip-calculator";
    @BeforeClass
    public void setUpPages() {
        sipPage = new SipPage(driver);
        
    }
    
    @Test(priority=5)
    public void navigatingToSip() throws InterruptedException, IOException {
        test = extent.createTest("SIP Test: Navigation");
        driver.get(SIPTest_URL);
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(2000);
        test.pass("Navigated to SIP Calculator");
        test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "Successfully navigated to Sip Page"));
    }
    
    @Test(priority=6, dependsOnMethods="navigatingToSip")
    public void findingElements() {
        sipPage.setElements();
        
    }
    
    @Test(priority=7, dependsOnMethods="findingElements")
    public void enterValues() throws InterruptedException {
        sipPage.enterValues();
        test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "After Giving Inputs."));
    }
    
    @Test(priority=8, dependsOnMethods="enterValues")
    public void printValues() {
        sipPage.printValues();
        test.pass("SIP Calculation Completed");
    }
}