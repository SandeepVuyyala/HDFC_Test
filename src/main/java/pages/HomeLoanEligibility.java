package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomeLoanEligibility {
    WebDriver driver;
    WebDriverWait wait;

    // Use Locators instead of WebElements at the class level to avoid StaleElementExceptions
    By incomeLoc = By.id("gross-income-inp");
    By tenureLoc = By.id("le-tenure-inp");
    By rateLoc = By.id("le-rate-inp");
    By emiLoc = By.id("le-emi-inp");
    
    By eligibilityResultLoc = By.id("eligibility-result");
    By emiResultLoc = By.id("hl-emi-result");
    By closeButtonLoc = By.id("eligi-close");

    List<Boolean> popUp = new ArrayList<>();
    List<List<String>> results = new ArrayList<>();
    public HomeLoanEligibility(WebDriver driver) {
    	JavascriptExecutor jse = (JavascriptExecutor) driver;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        jse.executeScript("window.scrollBy(0,365);");
    }

    public void setElements() {
        // Ensure the calculator is visible before interacting
        wait.until(ExpectedConditions.visibilityOfElementLocated(incomeLoc));
    }


    public void enterValues() throws InterruptedException {
        String[][] testData = {
            {"60000", "12", "5.4", "2000"},
            {"45000", "15", "10", "45000"}
        };

        for (int i = 0; i < testData.length; i++) {
            // Use JS to set values directly to avoid keyboard interaction issues
            setUpValueJS(incomeLoc, testData[i][0]);
            setUpValueJS(tenureLoc, testData[i][1]);
            setUpValueJS(rateLoc, testData[i][2]);
            setUpValueJS(emiLoc, testData[i][3]);

            Thread.sleep(3000);

            // Handle the potential "Not Eligible" popup
            try {
                WebElement closeButton = driver.findElement(closeButtonLoc);
                closeButton.click();
                popUp.add(true);
            } catch (Exception e) {
                popUp.add(false);
            }

            List<String> childList = new ArrayList<>();
            childList.add("Output " + (i + 1));
            childList.add(driver.findElement(eligibilityResultLoc).getText());
            childList.add(driver.findElement(emiResultLoc).getText());

            results.add(childList);
        }
    }

    public void printResults() {
        for (int i = 0; i < results.size(); i++) {
            List<String> childList = results.get(i);
            System.out.println("----- " + childList.get(0) + " -----");
            System.out.println("Eligibility : " + childList.get(1));
            System.out.println("EMI         : " + childList.get(2));
            if (popUp.get(i)) {
                System.out.println("Status      : You are not eligible for the home loan");
            }
            System.out.println();
        }
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