package pages;

import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SipPage {
    
    WebDriver driver;
    WebDriverWait wait;
    
    // Store locators as By objects for better stability with JS Executor
    By amountPerMonthLoc = By.id("sipAmount-Amt");
    By noOfInstalmentsLoc = By.id("sipTenure-Amt");
    By interestRateLoc = By.id("sipInterestRate-Amt");
    
    // Result elements
    By investmentLoc = By.id("sipTotalInvested");
    By earningsLoc = By.id("sipTotalInterest");
    By totalAmountLoc = By.id("sipFinalAmount");
    
    ArrayList<ArrayList<String>> list = new ArrayList<>();
    
    public SipPage(WebDriver driver) {

    	JavascriptExecutor jse = (JavascriptExecutor) driver;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        jse.executeScript("window.scrollBy(0,500);");
    }
    
    // setElements is now simplified as we use locators directly in the loop
    public void setElements() {
        // Ensure the calculator is visible before starting
        wait.until(ExpectedConditions.visibilityOfElementLocated(amountPerMonthLoc));
    }
    
    public void enterValues() throws InterruptedException {
        int[] years = {3, 6, 9};
        
        for (int year : years) {
            int months = year * 12;

            // 1. Enter Values using the corrected JS utility
            setUpValueJS(amountPerMonthLoc, "1000"); // Added a default amount value
            setUpValueJS(noOfInstalmentsLoc, String.valueOf(months));
            setUpValueJS(interestRateLoc, "10");

            // 2. Wait for calculation to refresh
            Thread.sleep(3000);
            
            // 3. Capture Results using fresh element lookups to avoid StaleElementReference
            ArrayList<String> childList = new ArrayList<>();
            String invested = driver.findElement(investmentLoc).getText();
            String interestEarned = driver.findElement(earningsLoc).getText();
            String finalAmount = driver.findElement(totalAmountLoc).getText();
            
            childList.add(String.valueOf(year));
            childList.add(invested);
            childList.add(interestEarned);
            childList.add(finalAmount);
            
            list.add(childList);
        }
    }
    
    public void printValues() {
        for (ArrayList<String> childList : list) {
            System.out.println("----- " + childList.get(0) + " Years SIP -----");
            System.out.println("Total Invested : " + childList.get(1));
            System.out.println("Interest Earned: " + childList.get(2));
            System.out.println("Final Amount   : " + childList.get(3));
            System.out.println();
        }
    }
    
    /**
     * Corrected JS Utility: Now accepts By locator to handle dynamic lookups correctly.
     */
    public void setUpValueJS(By locator, String value) {
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll and update value directly in the DOM
        //js.executeScript("arguments[0].scrollIntoView({block: 'center'});", ele);
        js.executeScript("arguments[0].value = '" + value + "';", ele);
        
        // Trigger events so the site's calculation logic realizes the value changed
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", ele);
        js.executeScript("arguments[0].dispatchEvent(new Event('blur'));", ele);
    }
}