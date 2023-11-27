package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountPageTest extends BaseTest {  //No driver methods in Test
	
	
	//RUN-> Base test ->launch delete all cookies ,enter login page url-> then  come acc page test  ->Before Test -> then call the getAccPageTitle
	// -->accPage  -> then goto doLogin clicks login Button 
	
	//Method is returning landing page of next page class mode ==>PAGE CHAINING
	
	// pre-condition is :1st Login then MY account
	@BeforeClass
   public void accPagesetup() {
	   // inherting accpage 
	   accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password")); 
	  // acc page ref is coming from Base test becz we'r inheriting that reference class 
   }
	
	@Test
	public void getPageTitleTest() {
		String accPageTitle=accPage.getAccPageTitle();
		Assert.assertEquals(accPageTitle, AppConstants.ACCOUNT_PAGE_TITLE); //act vs exp
		
	}
	
	@Test
	public void LogOutLinkExist() {
		Assert.assertTrue(accPage.isLogOutLinkExist());
		
	}
	
	@Test
	public void accountsPageHeaderCount() {
		int AccountPageHeaderCount=accPage.getAccountsPageHeaderCount();
		System.out.println("Account acc Page Header's Count :" + AccountPageHeaderCount);
		Assert.assertEquals(AccountPageHeaderCount,AppConstants.MEDIUM_TIME_OUT);
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actHeaderPageList= accPage.getAccountsPageHeader();
		Assert.assertEquals(actHeaderPageList,AppConstants.EXPECTED_ACC_PAGE_HEADER_LIST);  // for actual Vs expected header test
	}
	
	@DataProvider  //test data
	public Object[][] getSearchkey(){
		return new Object[][] {
			{"macbook",3},  //not possible to test all 10,000 products ( manually we can check 5 so it'll work for remaining)
			{"imac",1},
		    {"samsung",2}
		};  
	} 

	
	@Test(dataProvider="getSearchkey")   //passing data provider
	public void searchTest(String searchKey,int productCount) {
		//call search page reference
		//clear the search key in Account page ==>do search method
		searchResPage=accPage.doSearch("macbook");  //TEST DRIVEN DEVELOPMENT APPROACH(TDD) - acc. to test requirement creating page /util class
		int actResultsCount=searchResPage.getSearchResultsCount();
		Assert.assertEquals(actResultsCount, productCount);  //3   //for mac book,imac,samsung  results; run 3 times		
		// 1st do test cases then do development in TDD		
		// 3 level of page chaining
		
	}
}
