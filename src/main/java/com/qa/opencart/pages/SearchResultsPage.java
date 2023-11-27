package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;	
	private By productResults=By.cssSelector("div.product-layout");
	
	public SearchResultsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}

 public int getSearchResultsCount() {
	return eleUtil.waitforElementsVisible(productResults,AppConstants.MEDIUM_TIME_OUT).size();
}

public ProductInfoPage selectProduct(String productName) {
	
	//Link text - text can be anything(mac book...)
	eleUtil.ClickElementWhenReady(By.linkText(productName),AppConstants.MEDIUM_TIME_OUT);  //Dynamic locators-> on the basis of product we are selecting
	return new ProductInfoPage(driver); //create TTD approach /also constructor
	//returning next landing page
}

//pages are dependent on page classes
// there is no need of page class and test cases numbers are same
}


