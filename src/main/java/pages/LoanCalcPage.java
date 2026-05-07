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

  
    public void scrollToHeader() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(objReader.getObjectValue("calcHeading"))));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", header);
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