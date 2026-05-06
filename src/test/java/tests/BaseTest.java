package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import browserImplementation.BrowserConfig;
import pages.LoanCalcPage;
import utils.ObjectReader;

public class BaseTest {

	 protected WebDriver driver;
	 protected BrowserConfig browserconf;
	 protected LoanCalcPage loanPage;
	 protected ObjectReader objReader;
	  
	 protected ExtentReports extent;
	 protected ExtentSparkReporter spark;
	 protected ExtentTest test;
   @BeforeSuite
   public void setUp() {
   	 if (driver == null) {
   		 browserconf = new BrowserConfig();
	            driver = browserconf.chooseBrowser();
	        }
   }

   // TEST 1: System Readiness & UI Cleanup
   @Test(priority = 1)
   public void testHomeLoanPageurl() {
       test = extent.createTest("Test 1: Navigation and UI Cleanup");
       try {
           driver.get(objReader.getObjectValue("Fixed_Deposite_URL"));
           test.pass("Navigated to HDFC Home Page");
       } catch (Exception e) {
           test.fail("Failed to open url: " + e.getMessage());
           Assert.fail();
       }
   }
   
   @AfterSuite
   public void tearDownDriver() {
   	driver.close();
   }
}