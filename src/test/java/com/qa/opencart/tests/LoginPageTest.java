package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

// RUN TEST CLASS;  from test class-->do to Page class -->element util

public class LoginPageTest extends BaseTest {  //No driver/selenium code inside Test class
	
	//severity - how much time it'll take to fix the bug for bussiness requirements
	// writing test case for all methods in  Login Page class
	
	@Test(priority=1)
	public void loginPageTitleTest() { //driver is with Base Test
		
		//inheriting loginPage from base test as it extends
		String ActTitle=loginPage.getLoginPageTitle();  //from LoginPage class
		Assert.assertEquals(ActTitle, AppConstants.LOGIN_PAGE_TITLE);   //(ActTitle, "Account Login");
		
	}

	@Test(priority=2)
    public void loginPageURLTest() { //driver is with Base Test
		
		//inheriting loginPage from base test as it extends
		String ActURL=loginPage.getLoginPageUrl();  //from LoginPage class
		Assert.assertTrue(ActURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));   //contains("route=account/login")		
	}
		
	@Test(priority=3)
	public void isForgotPwdLinkExist() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());		
	}
	
	@Test(priority=4)
	public void loginTest() {
		 accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password")); //using acc page ref class
		Assert.assertEquals(accPage.isLogOutLinkExist(),true); //anything any method we can check here from accPage
	}

	
}
