package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{
	
	@BeforeClass  //performing login 1st
	public void registerSetup() {
		
// 1. login page 2. acc page
		regPage=loginPage.NavigateToRegister(); 
	}
	
	// email id will be dynamic 
	public String getRandomEmail() {  // for email id so not writing in data provider method
		return "openauto"+System.currentTimeMillis()+"@open.com"; // returns current ms
	}
	
	@DataProvider
	public Object[][] getUserRegData(){
		return new Object[][] {
			{"Pooja","agrwal","909090909090","pooja@123","yes"},
			{"shubam","gupta","909090909092","shub@123","yes"},
			{"mitaj","kumar","909090909093","mitaj@123","yes"}
		};
		
	}
	
	@DataProvider
	public Object[][] getUserRegSheetData() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME); //getTestData in ExcelUtil
		
	}
	
	
@Test(dataProvider="getUserRegSheetData")
public void userRegisterTest(String firstName,String lastName,String Email,String telephone,String password,String subscribe) {
	Assert.assertTrue(regPage.registerUser("Naveen", "Testing", getRandomEmail(), "9898989898", "naveen@123" ,"yes"));
}
}
