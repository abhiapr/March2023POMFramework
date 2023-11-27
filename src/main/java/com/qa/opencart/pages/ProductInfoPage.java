package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;


//total no of test cases NOT EQUAL TO  PAGES

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By ProductHeader=By.cssSelector("div#content h1");     //for Mac Pro text
	private By ProductImages=By.cssSelector("ul.thumbnails img");  // for all 4 images in macpro =>works for any product
	private By quantity=By.name("quantity");
	private By addToCartBtn=By.id("button-cart");
	
	// meta data xpath
	private By productMetaData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	private Map<String,String> productMap;  //declaring at class/global level for Master data
	//so comment map in 60th line and remove return
	//don't need to use external map like PriceMap ; everyone can use same MAP
	
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getProductHeaderValue() { //productname : 
		return eleUtil.getElementText(ProductHeader);
	}
	
	//images count
	public int getProductImagesCount() {
		int actProductImagesCount=eleUtil.waitforElementsVisible(ProductImages, AppConstants.MEDIUM_TIME_OUT).size();
		System.out.println("Total product images for mac book pro : " + getProductHeaderValue() + "===>" + actProductImagesCount);
		return actProductImagesCount;
	}

	
// hash map doesn't maintain order where list maintain order
// Meta data	
// Brand:Apple
// Product code: Product 18
// Product Header: MacBook Pro
// Reward Points:800
// Availability : In stock	
	
	
	// to avoid null pointer from productMap=>making as private
	// from master data only we can call these methods
	private void getproductMetaData() { //making private =>encapsulation  
		List<WebElement> metaList= eleUtil.waitforElementsVisible(productMetaData,AppConstants.MEDIUM_TIME_OUT);
		// to store key value pair
//		Map<String,String> metaMap=new HashMap<String,String>();
		for(WebElement e:metaList) {
				String metaText=e.getText();
				// key value pair-> split & trimming
				String key=metaText.split(":")[0].trim();
				String value=metaText.split(":")[1].trim();
				productMap.put(key, value);  //metaMap  ==>productMap
		}
//		return metaMap;
	}
	
// Pricing data	
// $ 2,000.00   -> o -th element
// Ex Tax:$ 2,000.00  -> 1st element
	private void getproductPriceData() {
		List<WebElement> priceList=eleUtil.waitforElementsVisible(productPriceData,AppConstants.MEDIUM_TIME_OUT);
//		Map<String,String> priceMap=new HashMap<String,String>();
		
		String actPrice=priceList.get(0).getText().trim();  //remove corner spaces
		String exTax=priceList.get(1).getText().split(":")[0].trim();  //key 
		String exTaxValue =priceList.get(1).getText().split(":")[1].trim();  //value
		
		productMap.put("price",actPrice);
		productMap.put(exTax,exTaxValue);
		
//		return priceMap;
			
	}
	
	// master function for above all
	
	public Map<String, String> getProductMasterdata() {
		
		productMap=new HashMap<String,String>();
		
		//productMap=new LinkedHashMap<String,String>();		
		//sorting order means:TreeMap =>sort based on Keys
		// productMap=new TreeMap<String,String>();
		productMap.put("productHeader",getProductHeaderValue()); //since master data, adding everything here 
		productMap.put("productHeader",String.valueOf(getProductImagesCount()));  // for images count -integer so convert it using valueOf
		getproductMetaData();
		getproductPriceData();
		
		return productMap;
	}
	
	
	
	
	//parent interface of hash map is MAP interface
}
