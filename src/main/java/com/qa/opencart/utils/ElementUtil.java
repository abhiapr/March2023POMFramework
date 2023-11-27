package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
//import org.openqa.selenium.StaleElementRefernceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

//import CustomException.FrameworkException;

public class ElementUtil {
	
	private WebDriver driver; //just declaring it .in static means CMA . so avoiding static here private static WebDriver driver;
	 //but using as static WebDriver driver, to avoid obj creation

	//if i'm using constructor, then i hv pass the browser , so i need to create object of ElementUtil
	// why using constructor ?  else driver will be NULL and and getElement method driver will throw NPE
	
	private Actions act;  // for actions class
	private JavaScriptUtil jsUtil;
	
	public ElementUtil( WebDriver driver) {//creating constructor //same sid=123
		this.driver=driver;	
		act=new Actions(driver);	
		jsUtil=new JavaScriptUtil(driver);
	}

//call these below methods in main by obj creation since it's NON STATIC METHOD	
	
    @Step("...entering value;{1} in locator: {0}")
	public void doSendKeys(By locator, String value) {
		if(value==null) {
			System.out.println("value cannot be null while using  dosendkeys method");
//			throw new FrameworkException("VALUE CANNOT BE NULL");
		}
		getElement(locator).sendKeys(value);
	
	}
	
	//adding generic methods and remove static
	public void doClick(By locator) {
		getElement(locator).click();		
	}
	
	public String getElementText(By locator) {
		String eleText = getElement(locator).getText();
		System.out.println("Element text is : "+ eleText);	
		return eleText;	
	}

	//
	public  WebElement getLinkTextValue(String LinkText) {
		return driver.findElement(By.linkText(LinkText));		
	}
	
	// generic for display
		public boolean checkElementIsDisplayed(By locator) {
			return getElement(locator).isDisplayed();
		}
		
	
	//generic attribute
	public String getElementAttribute(By locator,String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	
	//generic display (disable)
	public boolean CheckElementIsDisabled(By locator) {
		String disable_val=getElement(locator).getAttribute("disabled");
		if(disable_val.equals("true")) {
			return true;
		}
		return false;
	}
		
	
	@Step("getting element for locator:{0}")
	public WebElement getElement(By locator) {
		WebElement element=driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);	  		
	  	}
		return element;
	}
	
	// elements and image count in generic method
	public  int getTotalElementsCount(By locator) {
		return getElements(locator).size();	
	}
	
	public  List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
		
	}
	
	
	// text capture
	public  List<String> getElementsTextList(By locator){
		 List<WebElement> eleList= getElements(locator); //calling last one .// fetching all the text name
		 List<String> eleTextList = new ArrayList<String>(); // array list is child of List (TOP CASTING) .// and storing  text in a list.//pc=0
		 for(WebElement e:eleList) {
			 String text=e.getText();// capturing text . for images: e.getAttributes
			 if(!text.isEmpty()) {
				 eleTextList.add(text);// text will get added
				 
			 }
		 }
		 return  eleTextList;		 	
	}
	
	//generic for link text
	public void clickOnElement(By locator, String linkText) {
		List<WebElement> Linkslist =  getElements(locator);   //instead of = driver.findElements(locator);
		System.out.println("Total no. of links : " + Linkslist.size());

		// for loop
		for (WebElement e : Linkslist) {
			String Text = e.getText(); // capturing text
			System.out.println(Text);
			if (Text.equals("தமிழ்")) {
				e.click(); // google page will be in tamil
				break;
					}
				}
			}	
	
	//Suggestion Search generic
	public void doSearch(By Searchlocator, By SearchSuggLocator, String searchKey, String suggName)throws InterruptedException {
		
		//replaced by 127 ; driver.findElement(Searchlocator).sendKeys(searchKey); // instead of "Naveen Automation Labs" =search key
		doSendKeys(Searchlocator, searchKey);
		
		List<WebElement> suggList = getElements(SearchSuggLocator);  // findelements==>GET ELEMENTS
		Thread.sleep(5000);
		System.out.println(suggList.size()); // 10
		
		// for
		for (WebElement e : suggList) { 
			String Text = e.getText();
			System.out.println(Text); 
			if (Text.contains(suggName)) { // youtube ==>suggName
				e.click();
				break;
			}
		}			
	}
	


	
	
	// =========================**DROP DOWN utility
	//By Index Generic 
	
//	public  WebElement  getElement(By locator) {
//		return driver.findElement(locator); // find element return type is web element		
//	}
	
	public void doSelectDropdownByIndex(By locator, int index) {
		
		// check point for index<0 (-ve scenarios)
		if(index<0) {
			System.out.println("pls pass right positive index.."); 
			
			//IF IS A CONIDTION NOT LOOPS
			//break; // cannot be used  outside of a loop
			return; //  return means : returning void only 
		}
		Select sel=new Select(getElement(locator)); // passing get element method 
		sel.selectByIndex(index);
	}
	
	//By visible Generic
	public  void doSelectDropdownByVisible(By locator, String VisibleText) {
		// String - NULL check
	if (VisibleText == null) {
		System.out.println("pls pass visible text and it cannot be NULL...");
		return; 
		}
	
		Select sel = new Select(getElement(locator)); // passing get element method
		sel.selectByVisibleText(VisibleText);
	}
	
	//By Value Generic
		public  void doSelectDropdownByValue(By locator, String value) {
		if (value == null) {
			System.out.println("pls pass right value and it cannot be NULL...");
			return; 
			}
			Select sel = new Select(getElement(locator)); // passing get element method
			sel.selectByVisibleText(value);
		}
	
		//Select dropdown method generic
		
		// interview ques using SELECT CLASS
		public  int getOptionCount(By locator) {
			Select sel=new Select(getElement(locator));
			return  sel.getOptions().size();  // total no of elements in drop down 
		}
		
		
		public List<String> getDropdownTextList(By locator) {
					
			Select sel=new Select(getElement(locator));
			List<WebElement> OptionsList=sel.getOptions(); // get option return type is web element
//		    System.out.println(OptionsList.size());  // no.of drop downs in country =>233
			List<String>OptionList_Text=new ArrayList<String>();
			
			for (WebElement e:OptionsList) {
				String text=e.getText(); //capture text
				OptionList_Text.add(text);
				//System.out.println(text);
			}
			return OptionList_Text;
		}
		
		// without select methods (index,visible text,value)
		
		public void doSelectDropDownValue(By locator, String DropdownValue) {

			// Select sel=new Select (driver.findElement(Country));
			Select sel = new Select(getElement(locator)); // calling get element to pass country locator
			List<WebElement> OptionList = sel.getOptions();

			for (WebElement e : OptionList) {
				String text = e.getText();
				System.out.println(text);
				if (text.equals(DropdownValue)) { // DropdownValue =INDIA
					e.click();
					break; // break if it reaches India

				}
			}
		}
		
		//Without select class using xpath/css
		
		public  void doSelectDropDownValueUsingLocator(By locator,String DropdownValue) { 
			
			List<WebElement>OptionList=getElements(locator);		
			//8yrs back we don't hv index,value,Visible methods . so we use iterative approach	
			for(WebElement e:OptionList) {
				String text=e.getText();
				System.out.println(text);
				if(text.equals(DropdownValue)) {  // DropdownValue  =INDIA
					e.click();
					break;
				}
			}
}

		///******* ACTIONS UTIL
		// : to make action Optimise => make it private and add along with constructor
	
		
		// two ways of click element 
		// 1. web element .click
		// 2. actions.click
		
		public void doActionsClick(By locator) {
			//Actions act=new Actions(driver);
			act.click(getElement(locator)).perform();
		}
		
		public  void doActionsSendKeys(By locator,String value) {
			//Actions act=new Actions(driver);
			act.sendKeys(getElement(locator),value).perform();
		}
		
		
		// RIGHT CLICK generic
		public void SelectRightClickOption(By ContextMenulocator,String optionsValue) { //optionsValue= copy,edit
			//Actions act = new Actions(driver);
			act.contextClick(getElement(ContextMenulocator)).perform();
			By optionLocator=By.xpath("//*[text()='"+optionsValue+ "']");  //copy  ==> option value  ; //span is tag so its hard coded it may change also
			
			//span with *
			// getElement (optionLocator).click();// instead of this use DO CLICK
			doClick(optionLocator);
		}
		
		//MenuLevel Handle   ==INTERVIEW QUESTION
		
		// method overload with 2 ,3,4 parameters
		
		// 2 levels
		
		public void  MultiLevelMenuHandling(By level1MenuLocator,By level2MenuLocator) throws InterruptedException {
			//Actions act=new Actions(driver);
			act.moveToElement(getElement(level1MenuLocator)).perform();  //passing locator 
			Thread.sleep(1000);
			// getElement(level2MenuLocator).click(); // clicking next menu
			doClick(level2MenuLocator);
			
		}
		
		//BigBasket multilevel generic - 4 parameter
		
		public  void MultiLevelMenuHandling(By Level1Locator,String level2, String level3,String level4) throws InterruptedException {
			// Actions act=new Actions(driver); 
			 
			 act.moveToElement(getElement(Level1Locator)).perform();  // passing level 1 locator
			 Thread.sleep(1500);
			 act.moveToElement( getLinkTextValue(level2)).perform();  // level 2 Beverage
			 Thread.sleep(1500);
			 act.moveToElement( getLinkTextValue(level3)).perform();  // level 3  Tea
			 Thread.sleep(1500);
			 getLinkTextValue(level4).click();  //click green tea just click
		}
		
		//3 parameter 
		public  void MultiLevelMenuHandling(By Level1Locator,String level2, String level3) throws InterruptedException {
			// Actions act=new Actions(driver); 		 
			 act.moveToElement(getElement(Level1Locator)).perform(); 
			 Thread.sleep(1500);
			 act.moveToElement( getLinkTextValue(level2)).perform();  
			 Thread.sleep(1500);
			  getLinkTextValue(level3).click();  		  
		}
		
		//************WAIT Utils*********//
		/**
		 * An expectation for checking that element is present on the DOM of the page.
		 * this doesn't necessarily mean that element is visible
		 * @param locator
		 * @param timeout
		 * @return
		 */
		
		//check presence of ele
		public WebElement waitforElementPresenceBy(By locator,int timeout) {
			 WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout)); //instead of 10 sec
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));//Username
		}
		
		//generic -Polling time
		
		public WebElement waitforElementPresenceBy(By locator,int timeout,int Pollingtime) {
			 WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofSeconds(Pollingtime)); //10,2 sec
			 WebElement element= wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			 if(Boolean.parseBoolean(DriverFactory.highlight)) {  // if highlight is true
					jsUtil.flash(element);		
				}
			return element;
		}
//		public WebElement waitforElementPresenceBy(By locator,int timeout,int Pollingtime) {
//		 WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofSeconds(Pollingtime)); //10,2 sec
//		 return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//	}
//		
		
		@Step("...waiting for element is visible for locator:{0} with timeout: {1}")
		//visibility -check presence also visible in the DOM
		
		public WebElement waitforElementVisible(By locator,int timeout) {
			 WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout)); 
			 WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			 if(Boolean.parseBoolean(DriverFactory.highlight)) {
				 jsUtil.flash(element);
			 }
			 return element;
		}
		
		//generic -Polling time VISIBLE
		public WebElement waitforElementVisiblity(By locator,int timeout,int Pollingtime) {
		 WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout),Duration.ofSeconds(Pollingtime)); //10,2 sec
		 WebElement element=wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		 if(Boolean.parseBoolean(DriverFactory.highlight)) {  // if highlight is true
				jsUtil.flash(element);		
			}
		return element;
	}
		
		//generic  -ELEMENTS -presence
		/**An expectation for checking that there is at least one element present on a web page. not ABOUT DOM
		 * 
		 * @param locator
		 * @param timeout
		 * @return
		 */
		public  List<WebElement> waitforElementsPresenceBy(By locator,int timeout) {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator)); //just check at-least 1 element(PERFORMANCE IS BETTER)
		}
		
		//visibility -ELEMENTS
		/**An expectation for checking that all elements present on the web page that match the locator are visible. 
		 * Visibility means that the elements are not only displayed 
		 * but also have a height and width that is greater than 0.
		 * 
		 * @param locator
		 * @param timeout
		 * @return
		 */
		public List<WebElement>waitforElementsVisible(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));  //it'll check all 233 elements
		}
		
		
		//Title check [titleContains]
		
		public String WaitForTitleContains(String TitleFraction, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));// 10 sec=every use case will hv diff timeout. so DON'T make generic
			// if title is not available after 5 sec => we can use TRY/CATCH
			try {
				if (wait.until(ExpectedConditions.titleContains(TitleFraction))) {
					return driver.getTitle(); // =>String is return type
				} else {
					System.out.println(TitleFraction + " title value is not present..");
					return null;
				}
			} catch (Exception e) {  //we can also write else cond here
				e.printStackTrace();
				System.out.println(TitleFraction + " title value is not present..");
				return null;
			}
		}



      //String Value = whole value shd present  =>pass whole value  [titleIs]
		@Step("...waiting for title and capturing the title...")
		public String WaitForTitleValue(String Titlevalue, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));// 10 sec
			// if title is not available after 5 sec => we can use TRY/CATCH
			try {
				if (wait.until(ExpectedConditions.titleIs(Titlevalue))) {
					return driver.getTitle(); // =>String is return type
				} else {
					System.out.println(Titlevalue + " title value is not present..");
					return null;
				}
			} catch (Exception e) { // we can also write else cond here
				e.printStackTrace();
				System.out.println(Titlevalue + " title value is not present..");
				return null;
			}
		}

        //URL contains  [urlContains]  : getCurrentUrl()
		
		@Step("...waiting for page url and fetching it url fraction: {0}")
		public String WaitForURLContains(String UrlFraction, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));// 10 sec
			// if title is not available after 5 sec => we can use TRY/CATCH
			try {
				if (wait.until(ExpectedConditions.urlContains(UrlFraction))) {
					return driver.getCurrentUrl(); // =>String is return type
				} else {
					System.out.println(UrlFraction + " url value is not present..");
					return null;
				}
			} catch (Exception e) { // we can also write else cond here
				e.printStackTrace();
				System.out.println(UrlFraction + " url value is not present..");
				return null;
			}
		}


         //URL contains [urlToBe]

		public String WaitForURLToBe(String UrlValue, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));// 10 sec

			try {
				if (wait.until(ExpectedConditions.urlToBe(UrlValue))) {
					return driver.getCurrentUrl(); // =>String is return type
				} else {
					System.out.println(UrlValue + " url value is not present..");
					return null;
				}
			} catch (Exception e) { // we can also write else cond here
				e.printStackTrace();
				System.out.println(UrlValue + " url value is not present..");
				return null;
			}
		}
		
		
		//Generic -alert wait  =>JS ALERT same for prompt/confirm
		
		/**	An expectation of (JS) alert to be appeared on the page
		 * 
		 * @param timeout
		 * @return
		 */
		public Alert WaitforJSAlert(int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.alertIsPresent());  // if i return then it'll be Alert in return type
			
		}
		
		//generic polling time for alert
		
		public Alert WaitforJSAlert(int timeout,int Pollingtime) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(Pollingtime));
			return wait.until(ExpectedConditions.alertIsPresent());  // if i return then it'll be Alert in return type		
		}
		
		//Number for Browser window handle 
		
		public  boolean  waitForNumberOfBrowserWindow(int timeout,int NumOfWindows) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));//10
			return wait.until(ExpectedConditions.numberOfWindowsToBe(NumOfWindows));//2
			
		}
		
		//generic -frame wait  : below r overloading parameters
		public void waitforFrameByLocator(int timeout,By frameLocator) {
	 	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	 	    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}
		
		//by Index
		public  void waitforFrameByIndex(int timeout,int frameIndex) {
	 	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	 	    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
		
		//frameNameOrId
		
		public void waitforFrameByFrameNameOrId(int timeout,String frameNameOrId) {
	 	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	 	    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
	}
		
		// FrameElement
		public void waitforFrameElement(WebElement FrameElement,int timeout) {
	 	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	 	    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameElement));
	}
		
		
		//generic for click-able element
		
		/**An expectation for checking an element is visible and enabled such that you can click it.
		 * 
		 * @param locator
		 * @param timeout
		 */
		
		public void ClickElementWhenReady(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}
		

   // **********************Fluent Wait Generic

       //generic element visible  with Fluent wait
	public  WebElement waitforElementVisibleWithFluentWait(By locator,int timeout, int pollingtime) {
		
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingtime))
				.ignoring (NoSuchElementException.class) //if we face any Exception in btwn so using Ignoring
				.withMessage("-----time out is done ,Element is not found---"+locator);
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	// generic element presence with Fluent wait
	public WebElement waitforElementPresenceWithFluentWait(By locator,int timeout, int pollingtime) {
		
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingtime))
				.ignoring (NoSuchElementException.class) //if we face any Exception in btwn so using Ignoring
				//.ignoring(StaleElementReferenceException.class)
				.withMessage("-----time out is done ,Element is not found---"+locator);
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	
	//Alert with Fluent wait
	
 public Alert waitforJSAlertWithFluentWait(int timeout, int pollingtime) {
		
//				.ignoring (StaleElementReferenceException.class)
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingtime))
				.ignoring (NoAlertPresentException.class) //if we face any Exception in btwn so using Ignoring
				.withMessage("-----time out is done Js Alert is not found---");
		return wait.until(ExpectedConditions.alertIsPresent());
		
	}
 
 
 //frame with fluent wait
 
 public void waitforFrameWithFluentWait(String FrameNameOrId,int timeout, int pollingtime) {
		
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingtime))
				.ignoring (NoSuchFrameException.class) //if we face any Exception in btwn so using Ignoring
				.withMessage("-----time out is done Frame is not found...with name or id "+FrameNameOrId);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameNameOrId));
		
		//after 10 sec also frame is not avalialble then throw an exception
	}
 
 
	//generic WaitForElementAndEnterValue
	
	public  void WaitForElementAndEnterValue(By locator,int timeout,int Pollingtime,String value) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
		wait. 
		    pollingEvery(Duration.ofSeconds(Pollingtime))
		    	.ignoring (NoSuchElementException.class) //if we face any Exception in btwn so using Ignoring
		    		.withMessage("-----time out is done element is not found---" +locator)
		             .until(ExpectedConditions.presenceOfElementLocated(locator))
		             	.sendKeys(value);	
		
	}
	
	
	//WaitForElementAndClick
	
	public void WaitForElementAndClick(By locator,int timeout,int Pollingtime) {
	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
	wait. 
	    pollingEvery(Duration.ofSeconds(Pollingtime))
	    	.ignoring (NoSuchElementException.class) //if we face any Exception in btwn so using Ignoring
	    		.withMessage("-----time out is done element is not found---" +locator)
	             .until(ExpectedConditions.presenceOfElementLocated(locator))
	             	.click();	
	
}
	
	//************Custom wait
public  WebElement retryingElement(By locator,int timeout) { //Vustom
		
		WebElement element =null;
		int attempts=0;
		
		while(attempts<timeout) {  //0<10
			try {
				element=getElement(locator);
			//element is assigned with locator 
			System.out.println("element is found..." +locator+ "in attempt" +attempts);  //element is found...By.id: input-emailin attempt0
			break;
			
		}catch(NoSuchElementException e) {  //if element is not found after 10 seconds
			System.out.println("element is not found..." +locator+ "in attempt" +attempts);
			
			try {
				Thread.sleep(500);  //for thread using try/catch instead of throws
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		}
		attempts++;
			
		} 
		if(element==null) {
			System.out.println("element is not found..." +locator+ "in attempt" +attempts);		
		}
		return element;
	}
	
	//Polling time is overloaded
	
	public WebElement retryingElement(By locator, int TimeOut, int PollingTime) {  //2000ms -PT

		WebElement element = null;
		int attempts = 0;

		while (attempts < TimeOut) { // 0<10
			try {
				element = getElement(locator); // element is assigned with locator
				System.out.println("element is found..." + locator + "in attempt" + attempts); 
																								
				break;

			} catch (NoSuchElementException e) { // if element is not found after 10 seconds
				System.out.println("element is not found..." + locator + "in attempt" + attempts);

				try {
					Thread.sleep(PollingTime);    // default polling time=custom value
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			
		}
		attempts++;
			
		} 
		if(element==null) {
			System.out.println("element is not found..." +locator+ "in attempt" +attempts);		
		}
		return element;
	}
	
	
	//***********Page Load Timeout
	
	public boolean isPageLoaded(int timeout) { //10
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(timeout));
		
		//jsReturnsValue  -An expectation for String value from java script . so converting to string
		String flag=wait.until(ExpectedConditions.jsReturnsValue("retrun document.readyState =='complete'")).toString();  // until will become -->Boolean
		return Boolean.parseBoolean(flag); // wrapper class
	}
	
 }
		
	
