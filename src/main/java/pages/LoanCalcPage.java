package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ObjectReader;

public class LoanCalcPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ObjectReader objReader;

    public LoanCalcPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.objReader = new ObjectReader(); 
    }

    /**
     * Precise scroll using coordinates and a custom offset.
     * Use a negative offset (e.g., -100) to stop the scroll earlier (higher up).
     * Use a positive offset (e.g., 50) to scroll further down.
     */
    public void scrollToHeader() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath(objReader.getObjectValue("calcHeading"))));
        
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        
        // Adjust '-100' to whatever number fits your exact visual requirement
        // A value of -100 usually leaves enough room for a sticky top navigation bar
        int yOffset = -100; 
        
        jse.executeScript(
            "window.scrollTo({ top: arguments[0].getBoundingClientRect().top + window.pageYOffset + " + yOffset + ", behavior: 'smooth' });", 
            header
        );
    }

    public void enterLoanDetails(String amt, String tenure, String rate) {
        // Perform the precise scroll first
        scrollToHeader();
        
        // Logic remains consistent with your framework's ObjectReader usage
        updateFieldViaJS(By.id(objReader.getObjectValue("loanAmt")), amt);
        updateFieldViaJS(By.id(objReader.getObjectValue("tenure")), tenure);
        updateFieldViaJS(By.id(objReader.getObjectValue("rate")), rate);
    }

    private void updateFieldViaJS(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + value + "';", element);
        jse.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
        jse.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", element);
    }

    public String getMonthlyEMI() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(objReader.getObjectValue("emiDisplay")))).getText();
    }

    public String getTotalInterest() {
        return driver.findElement(By.id(objReader.getObjectValue("interestDisplay"))).getText();
    }

    public String getPrincipalAmount() {
        return driver.findElement(By.id(objReader.getObjectValue("principalDisplay"))).getText();
    }
}