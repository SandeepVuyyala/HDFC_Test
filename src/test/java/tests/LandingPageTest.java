package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LandingPage;

public class LandingPageTest extends BaseTest{
	LandingPage lpage ;
	@BeforeClass
	public void initilizeDriver() {
		if(driver==null) {
			System.out.println("driver is null in cons");
		}
		lpage = new LandingPage(driver);
	}
	@Test(priority=1)
	public void hoverToDiscoverProducts() throws InterruptedException {
		if(driver==null) {
			System.out.println("driver is n ull");
		}else {
			lpage.hoverToDeposits();
		}
		
	}
	
	@Test(priority=2)
	public void testClickOnfixedDeposite() throws InterruptedException {
		
			lpage.click_fixedDeposite();
	
		
	}
}
