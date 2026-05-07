package pages;
 
import java.util.ArrayList;
 
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
 
public class SipPage {
	
	WebDriver driver ;
	
	WebElement amountPerMonth;
	WebElement noOfInstalments ;
	WebElement intrest ;
	WebElement investment ;
	WebElement earnings ;
	WebElement totalAmount ;
	
	ArrayList<ArrayList<String>> list = new ArrayList<>();
	
	public void setElements(WebDriver driver) {
		this.driver=driver;
		 amountPerMonth = driver.findElement(By.id("sipAmount-Amt"));
		
		 noOfInstalments = driver.findElement(By.id("sipTenure-Amt"));
 
		 intrest = driver.findElement(By.id("sipInterestRate-Amt"));
	
		 investment = driver.findElement(By.id("sipTotalInvested"));
		
		 earnings = driver.findElement(By.id("sipTotalInterest"));
		
		 totalAmount = driver.findElement(By.id("sipFinalAmount"));
 
	}
	
	public void enterValues() throws InterruptedException {
		
		int[] years = {3, 6, 9};
		int i=1;
		
		for (int year : years) {
 
		    int months = year * 12;
 
		    // SIP amount
		    setUpValue(amountPerMonth,"1000");
 
		    // Tenure
		    setUpValue(noOfInstalments,String.valueOf(months));
		    
		    // Intrest
		    setUpValue(intrest,"10");
 
		    Thread.sleep(3000);
		    
		    ArrayList<String> childList= new ArrayList<>();
		    String invested = investment.getText();
		    String interestEarned = earnings.getText();
		    String finalAmount = totalAmount.getText();
		    
		    childList.add(String.valueOf(year));
		    childList.add(invested);
		    childList.add(interestEarned);
		    childList.add(finalAmount);
		    
		    list.add(childList);
 
		}
		
	}
	
	public void printValues() {
		
		 for(ArrayList<String> childList : list) {
			 System.out.println("----- " + childList.get(0) + " Years SIP -----");
			 System.out.println("Total Invested : " + childList.get(1));
			 System.out.println("Interest Earned: " + childList.get(2));
			 System.out.println("Final Amount  : " + childList.get(3));
			 System.out.println();
		 }
	}
	
	//belongs to utils class
	
	public void setUpValue(WebElement ele,String value) {
		
		ele.click();
		ele.sendKeys(Keys.CONTROL, "a");
		ele.sendKeys(Keys.DELETE);
		ele.sendKeys(value);
		ele.sendKeys(Keys.TAB);
		
	}
 
 
}