package com.qa.opencart.factory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

/**
 * 
 * @author Abhi  =>author name
 *
 */
public class DriverFactory {
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;  // to use highlight property ; static- no need to create object
	
	//if we have multi-threading sometimes it'll lead to DeadLock. 
	// so , use TL (it'll copy driver and send individual driver)
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>(); // from java not selenium
	
	
	
	/**
	 * This is used to initialize browser , returns driver
	 * @param browserName
	 * @return
	 */
	public  WebDriver initDriver(Properties prop) { //call by reference
		
		String browserName=prop.getProperty("browser");
		
		//String browserName=System.getProperty("browser"); //go to config. property page and get
		System.out.println("browser name is : " +browserName);
		highlight=prop.getProperty("highlight");  //true
		
		optionsManager=new OptionsManager(prop);  // passing properties reference
		
		switch (browserName.toLowerCase()) {
		case "chrome":
			
			//driver=new ChromeDriver(optionsManager.getChromeOptions());		//passing getChromeOptions from Option manager class 
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));  // create thread  copy of chrome WebDriver
			break;
			
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));		// run login page test and this method loginPageTitleTest	
			break;
		case "Edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));			
			break;	
		case "safari":
			tlDriver.set(new SafariDriver());			
			break;		

		default:
			System.out.println("plz pass right browser..."+browserName);
			break;
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("url");  // removing hard coded url
		// from driver to getDriver();
		return getDriver();  //same session id 123 in Base test
		
	}
	public WebDriver getDriver() {
		return tlDriver.get(); // returning my own copy of driver to test cases
		//get() -  will return web driver
	}
	
	
	
	
	/**
	 * 
	 */
	
	
	// click pom project -> run as-> maven build....
	// else run from testng.xml file
	
	public Properties intProp() {
		//mvn - clean install (minus) -D(option) env(variable name- we can give anything)
		
		// which command is used?
		//mvn clean install - default run in qa
		//mvn clean install -Denv="qa"  => maven clean install -always goes to POM .xml  (heart of maven) INTERVIEW QUE
		//sequence of maven command:
		//1. pom.xml file -> check all dependencies ->compile the code->(i. compiler plugin, 2. surefire plugin)
		// ->tetsng .xml file (having thread count) and test blocks and start running it
		
		FileInputStream ip=null;
		prop=new Properties();
		
		//to read -system is there since 1.0
		String envName=System.getProperty("env"); //env is "qa"
		System.out.println("env name is : "+ envName);
		
		try {			
		if(envName==null) {  // no env given
			System.out.println(" no env is given...henace running it on QA env by default"); //can be stage depends upon team leads
			ip=new FileInputStream("./src/test/resources/config/qa.config.properties");
		}
		else {  //env is given
			
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip=new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev":
				ip=new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stage":
				ip=new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "uat":
				ip=new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;	
			case "prod":
				ip=new FileInputStream("./src/test/resources/config/prod.config.properties");
				break;	
	
			default:
				System.out.println("pls pass right env.... "+envName);
				break;
			}
		}
		
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}		
		try {
		prop.load(ip);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
		
    //parallel execution is controlled by testng
	// file not found exception => go with TRY CATCH block Not throws declaration because:method have to handle the exception
		
		/**
		 */ // remote webdriver responsiblity to take SCREENSHOT.  Interface name is -TAKES SCREENSHOT
		 //driver is child of webdriver
		
      	public String getScreenshot() {
      		
			File srcFile=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE); // take SS amd store in source file
			String path=System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
			// user .dir ->gives current directory		
			File destination=new File(path); // copy path to destination file
			try {
				FileHandler.copy(srcFile, destination);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return path; // return path of SS

      	}

}
