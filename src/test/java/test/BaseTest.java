//
//package test;
//
//import java.util.Scanner;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.testng.annotations.AfterSuite;
//import org.testng.annotations.BeforeSuite;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
//public class BaseTest {
//
//    protected static WebDriver driver;
//    protected static ExtentReports extent;
//    protected static ExtentSparkReporter spark;
//  
//    @BeforeSuite
//    public void globalSetup() {
//        // 1. Initialize Report
//        spark = new ExtentSparkReporter("reports/Combined_ExecutionReport.html");
//        extent = new ExtentReports();
//        extent.attachReporter(spark);
//        
//        Scanner sc = new Scanner(System.in);
//        
//        System.out.println("Enter the browser of your choice: ");
//        System.out.println("1.Chrome");
//        System.out.println("2.Edge");
//
//        // 2. Initialize Single Browser Instance
//        if (driver == null) {
//        	ChromeOptions options = new ChromeOptions();
//    		options.addArguments("--disable-notifications");
//    		driver = new ChromeDriver(options);
//           
//            driver.manage().window().maximize();
//        }
//    }
//
//    @AfterSuite
//    public void globalTeardown() {
//        // 1. Flush Report
//        if (extent != null) {
//            extent.flush();
//        }
//        // 2. Close Browser
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}



package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.util.Scanner;

public class BaseTest {

    protected static WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentSparkReporter spark;

    @BeforeSuite
    public void globalSetup() {
        // 1. Initialize Report
        spark = new ExtentSparkReporter("reports/Combined_ExecutionReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        
        // Initialize Scanner for user input
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the browser of your choice: ");
        System.out.println("1. Chrome");
        System.out.println("2. Edge");

        // Capture user input
        int choice = sc.nextInt();

        // 2. Initialize Single Browser Instance based on choice
        if (driver == null) {
            if (choice == 1) {
                // Chrome Logic
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                driver = new ChromeDriver(options);
                System.out.println("Launched Chrome Browser...");
            } 
            else if (choice == 2) {
                // Edge Logic
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--disable-notifications");
                driver = new EdgeDriver(options);
                System.out.println("Launched Edge Browser...");
            } 
            else {
                // Default fallback
                System.out.println("Invalid input. Defaulting to Chrome...");
                driver = new ChromeDriver();
            }
           
            driver.manage().window().maximize();
        }
    }

    @AfterSuite
    public void globalTeardown() {
        if (extent != null) {
            extent.flush();
        }
        if (driver != null) {
            driver.quit();
        }
    }
}