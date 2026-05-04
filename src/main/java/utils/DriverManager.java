package utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

	private WebDriver driver = null;
	private PropertiesReader propertiesReader;
	public DriverManager() throws IOException {
		driver = new ChromeDriver();
		propertiesReader = new PropertiesReader();
		String url = propertiesReader.getProperty("URL");
		driver.get(url);
		driver.manage().window().maximize();
	}
	
	public WebDriver setWebDriver() {
		return driver;
	}
	public void tearDown() throws IOException {
		if(driver!=null) {
			driver.close();
		}
		propertiesReader.closeFileInputStream();
		
	}
	
}
