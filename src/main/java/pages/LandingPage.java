package pages;
 
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
 
public class LandingPage {
	private WebDriver driver;
	public LandingPage(WebDriver driver) {
		this.driver = driver;
		System.out.println("hello");
	}
	public void hoverToDeposits() throws InterruptedException {
		if(driver==null) {
			System.out.println("driver is null");
		}
		Thread.sleep(3000);
		Actions action = new Actions(driver);
		WebElement element = driver.findElement(By.xpath("//div[@class='navLevelOuter']/ul[1]/li[@class='nav2dropdown']/a"));
		action.moveToElement(element).click().perform();
		Thread.sleep(2000);
		WebElement ele_deposits = driver.findElement(By.xpath("//div[@class='navLevelOuter']/ul/li/ul/li[2]/a[normalize-space()='Deposits']"));
		action.moveToElement(ele_deposits).click().perform();
		Thread.sleep(2000);
		WebElement viewAll = driver.findElement(By.xpath("//a[@href='/fixed-deposit'][normalize-space()='View All']"));
		viewAll.click();
	}
	public void click_fixedDeposite() throws InterruptedException {
		Thread.sleep(6000);
		JavascriptExecutor  js = (JavascriptExecutor) driver;
		WebElement deposite_ele = driver.findElement(By.xpath("//a[@title='Know More ']"));
		js.executeScript("arguments[0].scrollIntoView();", deposite_ele);
		 js.executeScript("arguments[0].click();", deposite_ele);
		Thread.sleep(4000);
	}
}