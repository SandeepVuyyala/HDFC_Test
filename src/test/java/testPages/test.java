package testPages;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class test {
	
	@Test
	public void normalMethod() throws InterruptedException, IOException {
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.get("https://www.hdfc.bank.in/mutual-funds/sip-calculator");
		
		JavascriptExecutor js =  (JavascriptExecutor)driver;
		
		js.executeScript("window.scrollBy(0,500)");
		Thread.sleep(2000);
		
		WebElement amountPerMonth = driver.findElement(By.id("sipAmount-Amt"));
		
		WebElement noOfInstalments = driver.findElement(By.id("sipTenure-Amt"));

		WebElement intrest = driver.findElement(By.id("sipInterestRate-Amt"));
	
		WebElement investment = driver.findElement(By.id("sipTotalInvested"));
		
		WebElement earnings = driver.findElement(By.id("sipTotalInterest"));
		
		WebElement totalAmout = driver.findElement(By.id("sipFinalAmount"));

		
		int[] years = {3, 6, 9};
		int i=1;
		
		for (int year : years) {

		    int months = year * 12;

		    // SIP amount
		    amountPerMonth.clear();
		    js.executeScript("arguments[0].value='1000';", amountPerMonth);

		    // Tenure
		    noOfInstalments.clear();
		    js.executeScript("arguments[0].value='" + months + "';", noOfInstalments);

		    // Interest
		    intrest.click();
		    intrest.sendKeys(Keys.CONTROL, "a");
		    intrest.sendKeys(Keys.DELETE);
		    intrest.sendKeys("10");
		    intrest.sendKeys(Keys.TAB);

		    Thread.sleep(3000); 

		    // Results
		    String invested = investment.getText();
		    String interestEarned = earnings.getText();
		    String finalAmount = totalAmout.getText();

		    System.out.println("----- " + year + " Years SIP -----");
		    System.out.println("Total Invested : " + invested);
		    System.out.println("Interest Earned: " + interestEarned);
		    System.out.println("Final Amount  : " + finalAmount);
		    System.out.println();
		    
		    TakesScreenshot ts= (TakesScreenshot)driver;
		    File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		    String path= System.getProperty("user.dir")+ "/screenshots/" + "img-"+i +".png";
		    i++;
		    File destFile=new File(path);
		    FileUtils.copyFile(sourceFile,destFile);
		}
		
		
		
		Thread.sleep(3000);
		driver.quit();
	}

}
