package com.qa.opencart.base;


// Selenium will take screenshot if anything is failed

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	protected WebDriver driver;  // same session id of 20 ; //creating own driver here
	protected Properties prop;
	//private we can't use anywhere ; default also we can't access the modifier ;
	
	protected LoginPage loginPage;  //creating object class of LOGINPAGE so we can access those methods here.
	protected AccountPage accPage;  //just creating reference to call in Account Page test =>GCP
	protected SearchResultsPage searchResPage;
	protected ProductInfoPage productInfoPage;
	DriverFactory df;
	
	protected SoftAssert softAssert; //asserts are static thats why we'r accessing
	protected RegisterPage regPage;
	
	@BeforeTest
	public void setup() {
		df=new DriverFactory();
		//calling method by call by reference
		prop=df.intProp(); //initializing driver with chrome
		driver=df.initDriver(prop);  // it'll be TL Driver . so , eleUtil(TL)  every thing will be TL driver once initialized in InitDriver (DF)
		loginPage=new LoginPage(driver); //same session id of DF
		
		softAssert=new SoftAssert();
		
		// login -if i add  do login () here it'll affect Login page test; we already have
	}
	//create all ref here
	
//	@BeforeTest
//	public void setup() {
//		driver=new ChromeDriver();
//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
//		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
//		loginPage=new LoginPage	(driver);  //Passing driver here in object ref class
//		// we can't do it for Account  page because Login page is 1st it'll login to
//}
	

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
