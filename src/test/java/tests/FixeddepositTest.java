package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.Fixeddeposit;

public class FixeddepositTest extends BaseTest{
	
	Fixeddeposit fixedropdown;
	@BeforeClass
	public void initilizeDriver() {
		fixedropdown = new Fixeddeposit(driver);
	}
	
	@Test(priority=1)
	public void depositeDropDownTest() throws InterruptedException {
		fixedropdown.depositeDropDown();
	}
	@Test(priority=2)
	public void dateTest() throws InterruptedException {
		fixedropdown.date();
	}
	
}
