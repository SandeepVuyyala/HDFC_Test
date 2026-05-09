package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class FixedDeposit {
    WebDriver driver;

    // Locators using By
    private By fixedDepositLabel = By.xpath("//div[@id='text-fec1b2bc32']/h2/span");
    private By depositTypeBtn = By.className("fixed-select-btn");
    private By specificOption = By.xpath("//div[@class='calc_wrap']/form/div/div/ul/li[3]");
    private By sliderHandle = By.xpath("//div[@class='noUi-origin']/div/div");
    private By selectionFlags = By.xpath("//div[@class='quick-selection-flags']/div");
    private By interestAmount = By.xpath("//span[@id='fdInterestAmount']");
    private By dateInput = By.id("fdDateValue");
    private By monthDropdown = By.className("flatpickr-monthDropdown-months");
    private By yearInput = By.className("cur-year");
    private By arrowUp = By.className("arrowUp");
    private By day27 = By.xpath("//span[contains(@class,'flatpickr-day') and not(contains(@class,'disabled')) and normalize-space()='27']");

    public FixedDeposit(WebDriver driver) {
        this.driver = driver;
    }

    public String checkFixedPage() {
        WebElement fixed_deposite = driver.findElement(fixedDepositLabel);
        String fixed_label_ = fixed_deposite.getText();
        System.out.println(fixed_label_);
        return fixed_label_;
    }

    public void depositeDropDown() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement despiteTypeElemet = driver.findElement(depositTypeBtn);

        js.executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", despiteTypeElemet);
        js.executeScript("arguments[0].click();", despiteTypeElemet);

        WebElement ele = driver.findElement(specificOption);
        js.executeScript("arguments[0].click();", ele);
        Thread.sleep(3000);

        WebElement slider = driver.findElement(sliderHandle);
        Actions action = new Actions(driver);
        action.dragAndDropBy(slider, 39, 0).perform();
        Thread.sleep(3000);

        List<WebElement> list = driver.findElements(selectionFlags);
        System.out.println("Total elements: " + list.size());

        int i = 1;
        for (WebElement ele1 : list) {
            while (i <= list.size()) {
                WebElement el = ele1.findElement(By.xpath("//div[" + i + "]/input"));
                js.executeScript("arguments[0].click();", el);
                WebElement val = driver.findElement(interestAmount);
                System.out.println(val.getText());
                System.out.println("Clicked: " + ele1.getText());
                i++;
            }
        }
    }

    public void date() throws InterruptedException {
        WebElement ele2 = driver.findElement(dateInput);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", ele2);

        WebElement ele1 = driver.findElement(monthDropdown);
        Select s = new Select(ele1);
        s.selectByVisibleText("June");

        WebElement year = driver.findElement(yearInput);
        WebElement up = driver.findElement(arrowUp);

        while (!year.getAttribute("value").equals("2029")) {
            up.click();
            Thread.sleep(3000);
        }

        WebElement day = driver.findElement(day27);
        day.click();
        Thread.sleep(3000);

        WebElement date = driver.findElement(dateInput);
        System.out.println(date.getAttribute("value"));
    }
}