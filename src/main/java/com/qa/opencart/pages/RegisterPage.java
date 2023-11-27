package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage{
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public RegisterPage(WebDriver driver) { //creating constructor
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}


private By firstName=By.id("input-firstname");
private By lastName=By.id("input-lastname");
private By Email=By.id("input-email");
private By telephone=By.id("input-telephone");
private By password=By.id("input-password");
private By Confirmpassword=By.id("input-confirm");

//radio button
private By subscribeYes=By.xpath("//label[normalize-space()='Yes']");   //inside label
private By subscribeNo=By.xpath("//label[normalize-space()='No']");

private By agreeCheckBox=By.name("agree");
private By  continueButton=By.xpath("//input[@type='submit' and @value='Continue']");

private By SuccessMsg=By.cssSelector("div#content h1");
private By logoutLink=By.linkText("Logout");
private By registerLink=By.linkText("Register");
	

 // passing parameter not same as By locator name



public boolean registerUser(String firstName,String lastName,String Email,String telephone,String password,String subscribe) {
	eleUtil.waitforElementVisible(this.firstName, AppConstants.MEDIUM_TIME_OUT).sendKeys(firstName); // parameter,passing locator
	eleUtil.doSendKeys(this.lastName,lastName );
	eleUtil.doSendKeys(this.Email,Email);
	eleUtil.doSendKeys(this.telephone,telephone);
	
	eleUtil.doSendKeys(this.password,password);
	eleUtil.doSendKeys(this.Confirmpassword,password);  // pwd are same
	
	if(subscribe.equalsIgnoreCase("Yes")) {
		eleUtil.doClick(subscribeYes);		
	}
	else {
		eleUtil.doClick(subscribeNo);		
	}
	eleUtil.doClick(agreeCheckBox);
	eleUtil.doClick(continueButton);
	
	String SuccessMsg=eleUtil.waitforElementVisible(this.SuccessMsg,AppConstants.MEDIUM_TIME_OUT ).getText(); // getting text
	System.out.println(SuccessMsg);
	if(SuccessMsg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
		eleUtil.doClick(logoutLink);
		eleUtil.doClick(registerLink);
		return true;
	}
	return false;
}

}