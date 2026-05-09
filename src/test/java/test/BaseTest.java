//package test;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//
//import pages.SipPage;
//
//public class BaseTest {
//
//	
//WebDriver driver;
//	@BeforeClass
//	public void setUp() {
//		driver = new ChromeDriver();
//		driver.manage().window().maximize();
//	}
//	
//	@AfterClass
//	public void teardown() {
//		driver.quit();
//	}
//}

package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {

    protected static WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentSparkReporter spark;
  
    @BeforeSuite
    public void globalSetup() {
        // 1. Initialize Report
        spark = new ExtentSparkReporter("reports/Combined_ExecutionReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        // 2. Initialize Single Browser Instance
        if (driver == null) {
        	ChromeOptions options = new ChromeOptions();
    		options.addArguments("--disable-notifications");
    		driver = new ChromeDriver(options);
           
            driver.manage().window().maximize();
        }
    }

    @AfterSuite
    public void globalTeardown() {
        // 1. Flush Report
        if (extent != null) {
            extent.flush();
        }
        // 2. Close Browser
        if (driver != null) {
            driver.quit();
        }
    }
}