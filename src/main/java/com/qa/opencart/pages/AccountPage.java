package com.qa.opencart.pages;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountPage { //No testng methods/ no ASSERTIONS in page ; all will be on util top of wrapper 
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logOutLink=By.linkText("Logout");
	private By accHeaders=By.cssSelector("div#content h2");  //after login -> My Account
	private By search=By.name("search");  //search bar
	private By searchIcon=By.cssSelector("div#search button"); //search icon button
		
	//public Page constructors
	public AccountPage(WebDriver driver) {  
		this.driver=driver;
		eleUtil=new ElementUtil(driver); //passing driver / waiting for driver		
	}
	
	//Page actions
	public String getAccPageTitle() {
		return eleUtil.WaitForTitleValue(AppConstants.ACCOUNT_PAGE_TITLE,AppConstants.SHORT_TIME_OUT);
	}
	
	public boolean isLogOutLinkExist() {
		return eleUtil.waitforElementPresenceBy(logOutLink,AppConstants.MEDIUM_TIME_OUT).isDisplayed();
		
	}	
	
	//these r un necessary calculation for capture text....
	public List<String>  getAccountsPageHeader(){
		List<WebElement> headerList=eleUtil.waitforElementsVisible(accHeaders,AppConstants.MEDIUM_TIME_OUT);
		List<String> headersValueList =new ArrayList<String>();
		for(WebElement e:headerList) {
			String header=e.getText();
			headersValueList.add(header);
		}
		System.out.println("Actual headers are ===>"+ headersValueList);
		return headersValueList;  //string is return type
		
	}
	
	
	//use this directly
	
	public int getAccountsPageHeaderCount() {
		return eleUtil.waitforElementsVisible(accHeaders,AppConstants.MEDIUM_TIME_OUT).size();  //size is int here 
	}
	
	public SearchResultsPage doSearch(String SearchKey) {
		WebElement searchField=eleUtil.waitforElementVisible(search,AppConstants.MEDIUM_TIME_OUT); //imac applying dynamic wait for searching
		searchField.clear();
		searchField.sendKeys(SearchKey);
		eleUtil.doClick(searchIcon);  //clicking icon here
		// we'll write code for i-mac result tabs
		return new SearchResultsPage(driver);  //searchResultsPage - it's resp. to return next landing page object
	}

}
