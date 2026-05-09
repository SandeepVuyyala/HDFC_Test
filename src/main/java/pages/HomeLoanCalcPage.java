package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomeLoanCalcPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators moved from ObjectRepo to Page Class using By
    private By calcHeading = By.xpath("//h2[contains(@class, 'cmp-title__text') and contains(text(), 'Calculate Your Home Loan')]");
    private By loanAmtInput = By.id("loan-amt-inp");
    private By tenureInput = By.id("tenure-inp");
    private By rateInput = By.id("rate-inp");
    private By emiDisplay = By.id("monthly-emi");
    private By interestDisplay = By.id("interest-amt");
    private By principalDisplay = By.id("principal-amt");

    public HomeLoanCalcPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void scrollToHeader() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(calcHeading));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", header);
    }

    public void enterLoanDetails(String amt, String tenure, String rate) {
        // Perform the precise scroll first
        scrollToHeader();
        
        // Use the By locators directly
        updateFieldViaJS(loanAmtInput, amt);
        updateFieldViaJS(tenureInput, tenure);
        updateFieldViaJS(rateInput, rate);
    }

    private void updateFieldViaJS(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + value + "';", element);
        jse.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
        jse.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", element);
    }

    public String getMonthlyEMI() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emiDisplay)).getText();
    }

    public String getTotalInterest() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(interestDisplay)).getText();
    }

    public String getPrincipalAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(principalDisplay)).getText();
    }
}