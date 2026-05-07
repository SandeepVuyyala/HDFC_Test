package pages;
 
import java.time.Duration;
import java.util.List;
 
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
 
public class FixedDeposit {
	WebDriver driver;
	public FixedDeposit(WebDriver driver) {
		this.driver = driver;
	}
	public void depositeDropDown() throws InterruptedException {
		//fixed label
		//WebElement fixed_label = driver.findElement(By.xpath("//div[@id='text-fec1b2bc32']/h2/span"));
		//System.out.println(fixed_label.getText());
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement despiteTypeElemet = driver.findElement(By.className("fixed-select-btn"));
		js.executeScript("arguments[0].scrollIntoView();", despiteTypeElemet);
		js.executeScript("arguments[0].click();", despiteTypeElemet);
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(6));
		//List<WebElement> selectDespositeElement = (List<WebElement>) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='calc_wrap']/form/div/div/ul/li")));
		List<WebElement> selectDespositeElement = driver.findElements(By.xpath("//div[@class='calc_wrap']/form/div/div/ul/li"));
//		js.executeScript("arguments[0].click();", selectDespositeElement);
		WebElement ele = driver.findElement(By.xpath("//div[@class='calc_wrap']/form/div/div/ul/li[3]"));
		js.executeScript("arguments[0].click();", ele);
		Thread.sleep(4000);
		//ele.click();
//		for(WebElement str:selectDespositeElement) {
//			System.out.println(str.getText());
//		}
//		System.out.println(selectDespositeElement.getText());

		WebElement slider = driver.findElement(By.xpath("//div[@class='noUi-origin']/div/div"));
		Actions action = new Actions(driver);
		action.dragAndDropBy(slider, 39,0).perform();
		Thread.sleep(3000);
//		List<WebElement> list = driver.findElements(By.xpath("//div[@class='quick-selection-flags']/div"));
////		for(WebElement ele:list) {
////			
////		}
//		System.out.println(list.size());
//		int size1 = list.size();
//		int i=1;
//		for(WebElement ele1:list) {
//			
//			while(i<=size1) {
////			WebElement quickSelectAmount = ele1.findElement(By.xpath(".//div[i]"));
//////			System.out.println(value);
/////
//				Thread.sleep(2000);
//			js.executeScript("arguments[0].click();", ele1);
//			i++;
//			}	
//		}
		List<WebElement> list =
		        driver.findElements(By.xpath("//div[@class='quick-selection-flags']/div"));
 
		System.out.println("Total elements: " + list.size());
 
//		JavascriptExecutor js = (JavascriptExecutor) driver;
		int i=1;
		for (WebElement ele1 : list) {
			while(i<=list.size()) {
			WebElement el = ele1.findElement(By.xpath("//div["+i+"]/input"));
			js.executeScript("arguments[0].click();", el);
		    WebElement val = driver.findElement(By.xpath("//span[@id='fdInterestAmount']"));
		    System.out.println(val.getText());
 
		    System.out.println("Clicked: " + ele1.getText());
 
		    Thread.sleep(1500); // Only for visual confirmation
		    i++;
		}
		}
	}
 
 
public void date() throws InterruptedException {
	WebElement ele2 = driver.findElement(By.id("fdDateValue"));
//     Actions action = new Actions(driver);
     JavascriptExecutor js = (JavascriptExecutor) driver;
     js.executeScript("arguments[0].click();", ele2);
     WebElement ele1 = driver.findElement(By.className("flatpickr-monthDropdown-months"));
     Select s = new Select(ele1);
     s.selectByVisibleText("June");
     WebElement inp = driver.findElement(By.xpath("//div[@class='numInputWrapper']/input"));
     inp.sendKeys("2029");
 
     WebElement day27 = driver.findElement(
            By.xpath("//span[contains(@class,'flatpickr-day') " +
                     "and not(contains(@class,'disabled')) " +
                     "and normalize-space()='27']")
        );
     WebElement ele = driver.findElement(By.xpath("//div[@class='datepicker']/input"));
     System.out.println("date tet");
     System.out.println(ele.getAttribute("value"));
     WebElement ele11 = driver.findElement(By.xpath("//div[@id='fdInterestBoxes']/div[2]"));
     ele11.click();
     //checking interest rates 
     WebElement ele21 = driver.findElement(By.xpath("//div[@class='label']/p/span"));
     System.out.println(ele21.getText());
     Thread.sleep(3000);
}
}




//package pages;

//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.time.Duration;
//
//public class FixedDeposit {
//    WebDriver driver;
//    WebDriverWait wait;
//
//    public FixedDeposit(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    }
//
//    public void depositeDropDown() {
//        // FIX: Using stable text matching to avoid dynamic ID failures
//        By dropdown = By.xpath("//h2[contains(.,'Fixed Deposit')]//span | //span[text()='Fixed Deposit']");
//        
//        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
//        element.click();
//    }
//
//    public void date() {
//        // FIX: Handle interception by scrolling and using JS click if necessary
//        By dateBox = By.xpath("//div[@id='fdInterestBoxes']/div[2]");
//        
//        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(dateBox));
//
//        // Scroll the element into the center of the viewport to clear headers/footers
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
//        
//        try {
//            // Attempt standard click first
//            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
//        } catch (Exception e) {
//            // Fallback to JavaScript click if physically intercepted by an overlay
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
//        }
//    }
//}