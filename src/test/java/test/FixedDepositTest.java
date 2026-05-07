//package test;
//
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//import pages.FixedDeposit;
//import com.aventstack.extentreports.ExtentTest;
//
//public class FixedDepositTest extends BaseTest {
//    FixedDeposit fixedropdown;
//    ExtentTest test;
//
//    @BeforeClass
//    public void initializeDriver() {
//        fixedropdown = new FixedDeposit(driver);
//    }
//
//    @Test(priority=3) // Continuing priority
//    public void depositeDropDownTest() throws InterruptedException {
//        test = extent.createTest("Fixed Deposit: Dropdown Selection");
//        fixedropdown.depositeDropDown();
//        test.pass("Selected Deposit Dropdown");
//    }
//
//    @Test(priority=4)
//    public void dateTest() throws InterruptedException {
//        fixedropdown.date();
//        test.pass("Date selection completed");
//    }
//}



package test;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.FixedDeposit;
import com.aventstack.extentreports.ExtentTest;

public class FixedDepositTest extends BaseTest {
    FixedDeposit fixedropdown;
    ExtentTest test;

    @BeforeClass
    public void initilizeDriver() {
        // Inherits the 'driver' initialized in BaseTest
        fixedropdown = new FixedDeposit(driver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
    }

    @Test(priority=3) // Priority follows LandingPageTest (1, 2)
    public void depositeDropDownTest() throws InterruptedException {
        test = extent.createTest("Fixed Deposit: Expand Dropdown");
        fixedropdown.depositeDropDown();
        test.pass("Fixed Deposit dropdown expanded successfully.");
    }

    @Test(priority=4, dependsOnMethods="depositeDropDownTest")
    public void dateTest() throws InterruptedException {
        test = extent.createTest("Fixed Deposit: Select Date/Interest");
        fixedropdown.date();
        test.pass("Date/Interest option selected successfully.");
    }
}