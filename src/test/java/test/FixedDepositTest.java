package test;
 
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.FixedDeposit;
import utils.ScreenshotUtil;
 
import com.aventstack.extentreports.ExtentTest;
 
public class FixedDepositTest extends BaseTest {
    FixedDeposit fixedropdown;
    ExtentTest test;
 
    @BeforeClass
    public void initilizeDriver() {
        // Inherits the 'driver' initialized in BaseTest
        fixedropdown = new FixedDeposit(driver);
    }
    @Test(priority=3)
    public void testFixed_deposite() {
    	try {
    		
    		 test = extent.createTest("Test: FD");
    		String autual_fixed_label =fixedropdown.checkFixedPage();
    		String Expected_fixed_label = "Fixed Deposit Calculator";
    		Assert.assertEquals(autual_fixed_label, Expected_fixed_label);
    		 test.pass("Fixed deposite label " + autual_fixed_label);
         test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "fixed_label"));
    	}catch(Exception e) {
    		 System.out.print("Fixed deposite label is failed: " + e.getMessage());
    	}
    }
    @Test(priority=4) // Priority follows LandingPageTest (1, 2)
    public void depositeDropDownTest() throws InterruptedException {
    	try {
    		
        test = extent.createTest("Fixed Deposit: Expand Dropdown");
        fixedropdown.depositeDropDown();
        test.pass("Fixed Deposit dropdown expanded successfully.");
    	}catch(Exception e) {
    		test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "fixed_deposit_box"));
    	}
    }
 
    @Test(priority=5, dependsOnMethods="depositeDropDownTest")
    public void dateTest() throws InterruptedException {
    	try {
        test = extent.createTest("Fixed Deposit: Select Date/Interest");
        fixedropdown.date();
        test.pass("Date/Interest option selected successfully.");
    	}catch(Exception e) {
    		test.addScreenCaptureFromPath(ScreenshotUtil.capturePage(driver, "fixed_deposit_date"));
    	}
    }
}