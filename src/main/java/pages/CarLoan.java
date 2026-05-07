package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CarLoan {
    WebDriver driver;
    WebDriverWait wait;

    // Use Locators instead of WebElements at the class level to avoid StaleElementExceptions
    By loanAmountLoc = By.id("carLoanNewFixedLoanRange-Amt");
    By loanTenureLoc = By.id("carLoanNewFixedLoanTenureRange-Amt");
    By interestRateLoc = By.id("carLoanNewFixedInterestRateRange-Amt");

    public void setElements(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterValues() throws InterruptedException {
        // Use JS to set values directly to avoid keyboard interaction issues
        setUpValueJS(loanAmountLoc, "1500000");
        setUpValueJS(loanTenureLoc, "1");
        setUpValueJS(interestRateLoc, "9.5");
        Thread.sleep(2000);
    }

    public void printResults(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        // Wait for results to be visible before fetching text
        String monthlyEmi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("carLoanNewPersonalLoanEmiResult"))).getText();
        String interestAmount = driver.findElement(By.id("carLoanNewFixedEmiResult")).getText();
        String totalAmount = driver.findElement(By.id("carLoanNewPFixedTotalAmount")).getText();

        
        System.out.println("----------CarLoanDetails-------");
        System.out.println("Monthly EMI     : " + monthlyEmi);
        System.out.println("Interest Amount : " + interestAmount);
        System.out.println("Amount Payable  : " + totalAmount);
    }

    /**
     * JS-based utility method to set input values.
     * Bypasses overlays and keyboard simulation.
     */
    public void setUpValueJS(By locator, String value) {
        WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Scroll the element into view center
        //js.executeScript("arguments[0].scrollIntoView({block: 'center'});", ele);
        
        // 2. Clear and set value directly via JS
        js.executeScript("arguments[0].value = '" + value + "';", ele);
        
        // 3. Trigger 'change' and 'blur' events to notify the site's scripts the value changed
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", ele);
        js.executeScript("arguments[0].dispatchEvent(new Event('blur'));", ele);
    }
}