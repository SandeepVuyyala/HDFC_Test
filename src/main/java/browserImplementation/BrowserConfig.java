package browserImplementation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class BrowserConfig {
	
	private WebDriver driver;

	/**
	 * Initializes the WebDriver, maximizes the window, and returns the driver instance.
	 * Currently defaults to Chrome as per the project requirements.
	 */
	public WebDriver chooseBrowser() {
		// Defaulting to Chrome as per the framework's logic
		driver = new ChromeDriver();
		
		// Ensure the browser opens in full screen
		driver.manage().window().maximize();
		return driver;
	}

	/**
	 * Terminates the browser session and closes all associated windows.
	 */
	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
		}
	}
}