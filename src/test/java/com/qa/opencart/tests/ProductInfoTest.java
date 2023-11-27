package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoTest extends BaseTest{  // reach at login page
	
	//1st test,then before class
	// before method - will run before every test so we 'r avoiding
	
	@BeforeClass  //performing login 1st
	public void productInfoSetup() {
		
// 1. login page 2. acc page
		accPage=loginPage.doLogin("janautomation@gmail.com","Selenium@12345"); //using from base test
	}
		
//	do login will perform  and return acc page class object
 // then,we r using acc page class reference
		
	@DataProvider
	public Object[][] productTestData(){ 
		return new Object[][] {
			{"macbook", "Macbook Pro"}, // 1 key with 2 products; 2 columns
			{"macbook", "Macbook Air"},
			{"iMac", "imac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"}
		};
			
		}
	
	
	
	//search is  correctly done  or not => mac book pro is correctly coming or not	
	
	@Test(dataProvider="productTestData")
	public void productHeaderTest(String searchKey,String productName)  {
		searchResPage=accPage.doSearch(searchKey); //3. do search =>search page  ; serack key =macbook
		productInfoPage=searchResPage.selectProduct(productName); //pdt name   =Macbook Pro
		String actProductHeader= productInfoPage.getProductHeaderValue();  //4. Info page  =>4 level 
		Assert.assertEquals(actProductHeader,productName);
	}
	
	@DataProvider
	public Object[][] getSearchkey(){
		return new Object[][] {  // 2d array = example like excel sheet
			{"macbook", "Macbook Pro",4}, // 3 columns
			{"macbook", "Macbook Air",4},
			{"iMac", "imac",3},
			{"samsung","Samsung SyncMaster 941BW",1},
			{"samsung","Samsung Galaxy Tab 10.1",7}
		};
			
		}
	
	@Test
	public void productImagesCountTest(String searchKey,String productName,int expProductImagesCount)  {
		searchResPage=accPage.doSearch(searchKey); //3. do search =>search page 
		productInfoPage=searchResPage.selectProduct(productName);
		int actProductImagesCount=productInfoPage.getProductImagesCount();
		Assert.assertEquals(actProductImagesCount,expProductImagesCount);  // expProductImagesCount=4
		
	}
	
	@Test
	public void productInfoTest() {
		searchResPage=accPage.doSearch("macbook"); //3. do search =>search page 
		productInfoPage=searchResPage.selectProduct("MacBook Pro");
		Map<String,String> productActualData=productInfoPage.getProductMasterdata();
		System.out.println(productActualData);
		
		// if one assertion test case means= hard assertion
		
		//if one assertion got failed it'll not go to next line : Test will be failed		
		// so using soft Assertion it'll check all the asserts.
		softAssert.assertEquals(productActualData.get("Brand"),"Apple"); // just will verify 5 not all 100 products
		softAssert.assertEquals(productActualData.get("Availability"),"In stock");
		softAssert.assertEquals(productActualData.get("Reward Points"),"800");
		softAssert.assertEquals(productActualData.get("Product Header"),"MacBook Pro");
		softAssert.assertEquals(productActualData.get("Product code"),"Product 18");
		softAssert.assertAll(); // assert all  and give me test case passed , failed
		
		
		
	}
	
		
	}



