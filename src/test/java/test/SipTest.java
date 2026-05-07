//package test;
// 
//import java.io.IOException;
// 
// 
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
// 
//import pages.SipPage;
// 
// 
// 
//public class SipTest extends BaseTest {
//	
//	WebDriver driver;
//	SipPage sipPage;
//	
//	@BeforeClass
//	public void setUp() {
//		sipPage = new SipPage();
//		
//	}
//	
//	@Test(priority=1)
//	public void navigatingToSip() throws InterruptedException, IOException {
//		
//		driver.get("https://www.hdfc.bank.in/mutual-funds/sip-calculator");
//		
//		JavascriptExecutor js =  (JavascriptExecutor)driver;
//		
//		js.executeScript("window.scrollBy(0,500)");
//		Thread.sleep(2000);
//		
//	}
//	
//	@Test(priority=2,dependsOnMethods="navigatingToSip")
//	public void findingElements() {
//		sipPage.setElements(driver);
//	}
//	
//	@Test(priority=3,dependsOnMethods="findingElements")
//	public void enterValues() throws InterruptedException {
//		sipPage.enterValues();
//	}
//	
//	@Test(priority=4,dependsOnMethods="enterValues")
//	public void printValues() {
//		sipPage.printValues();
//	}
//
// 
//}




package test;

import java.io.IOException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.SipPage;
import com.aventstack.extentreports.ExtentTest;

public class SipTest extends BaseTest {
    
    SipPage sipPage;
    ExtentTest test;
    
    @BeforeClass
    public void setUpPages() {
        sipPage = new SipPage(driver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        
    }
    
    @Test(priority=5)
    public void navigatingToSip() throws InterruptedException, IOException {
        test = extent.createTest("SIP Test: Navigation");
        driver.get("https://www.hdfc.bank.in/mutual-funds/sip-calculator");
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(2000);
        test.pass("Navigated to SIP Calculator");
    }
    
    @Test(priority=6, dependsOnMethods="navigatingToSip")
    public void findingElements() {
        sipPage.setElements();
    }
    
    @Test(priority=7, dependsOnMethods="findingElements")
    public void enterValues() throws InterruptedException {
        sipPage.enterValues();
    }
    
    @Test(priority=8, dependsOnMethods="enterValues")
    public void printValues() {
        sipPage.printValues();
        test.pass("SIP Calculation Completed");
    }
}