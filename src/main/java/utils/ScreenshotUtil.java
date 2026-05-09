package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

	//(System.currentTimeMillis() % 100)
    public static String capturePage(WebDriver driver, String fileName) {
        String name = fileName + "_" +  ".png";
        String savePath = System.getProperty("user.dir") + "/screenshots/" + name;

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(savePath));
        } catch (IOException e) {
            System.err.println("Failed to capture page screenshot: " + e.getMessage());
        }
        
        // Returns the relative path for the Extent Report to locate the image
        return "../screenshots/" + name;
    }
}
