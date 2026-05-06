package tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utils.DriverManager;

public class BaseTest {

    protected static WebDriver driver;
    protected static DriverManager drivermanager;

    @BeforeSuite
    public void setUpDriver() throws IOException {
        if (driver == null) {
            drivermanager = new DriverManager();
            driver = drivermanager.setWebDriver();
        }
    }
    @AfterSuite
    public void tearDownDriver() {
    	driver.close();
    }
}