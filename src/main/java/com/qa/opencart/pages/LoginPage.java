package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;


//Industry Standard Model (com. team name)

public class LoginPage {   //page class/page library /page Object for login page ; page class responsible is  return something 
	private WebDriver driver;
	private ElementUtil eleUtil;
	// 1.private By Locators -page locators
	
	private  By emailId=By.id("input-email");
	private  By password=By.id("input-password");
	private By loginBtn=By.xpath("//input[@value='Login']");
	private By forgotPwdLink=By.linkText("Forgotten Password");
	
	private By registerLink=By.linkText("Register");
	
	// 2. public Page constructors (if private, i cannot create object of Login page class)
	
	public LoginPage(WebDriver driver) { //same passing to line 9
		this.driver=driver;
		eleUtil=new ElementUtil(driver);  //ElementUtility is not responsible for creating driver
		// so, creating element util object class
		
		
	}
	
	// 3. public page actions/methods  =>getting title
	public String getLoginPageTitle() {
		//String title=driver.getTitle();
		String title=eleUtil.WaitForTitleContains(AppConstants.LOGIN_PAGE_TITLE,AppConstants.SHORT_TIME_OUT); // ("Account Login", 5);  //using from Element Util
		System.out.println("Login page title is :" + title);
		return title;
	}
	
	//current url 
	public String getLoginPageUrl() {
		String url=eleUtil.WaitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION,AppConstants.SHORT_TIME_OUT);   //("route=account/login", 5);
		System.out.println("Login page url is:"+url);
		return url;		
	}
	
	//Forgot link exists or not
	
	//all these loc are private loc =>ENCAPSULATION
	public boolean isForgotPwdLinkExist() {
		
		// return driver.findElement(forgotPwdLink).isDisplayed();  //returning boolean 
		
		return eleUtil.waitforElementVisible(forgotPwdLink,AppConstants.MEDIUM_TIME_OUT).isDisplayed();   // (forgotPwdLink, 10)    //returning web element
		
	}
	
	// This method is performing PAGE CHAINING
	// this is also Test data driven approach 
	public AccountPage doLogin(String username, String pwd ) {  //instead of string class name AccountPage
		System.out.println("App cred are : " + username + ":" + pwd);
	    eleUtil.waitforElementVisible(emailId, AppConstants.MEDIUM_TIME_OUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		//return eleUtil.WaitForTitleValue(AppConstants.ACCOUNT_PAGE_TITLE, AppConstants.MEDIUM_TIME_OUT);  //("My Account",10)
		
		//it's doLogin responsibility to return object
		return new AccountPage(driver);  //created object without any reference.not eligible for GC
		
		// so next landing page  of ACCOUNT PAGE will be here(66 line) .so obj will return from here
		// acc page ref is coming from Base test
				
//				driver.findElement(emailId).sendKeys(username);
//				driver.findElement(password).sendKeys(pwd);
//				driver.findElement(loginBtn).click();
//              return driver.getTitle();  //returning something
	}
	
	//register page
	
	public RegisterPage NavigateToRegister() {
		eleUtil.waitforElementVisible(registerLink, AppConstants.SHORT_TIME_OUT).click();
		return new RegisterPage(driver);  // creating constructorin reg page
		
	}

}
