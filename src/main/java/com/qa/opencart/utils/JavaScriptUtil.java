package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	private WebDriver driver; //class var ; so i need to create obj of class var // so im using constructor this.driver
	private JavascriptExecutor js; 
	
	//constructor same as class name
	public JavaScriptUtil(WebDriver driver) {
		this.driver=driver;
		js=(JavascriptExecutor)this.driver;
		
	}
	
	//generic method
	public String getTitleByJs() {
		return js.executeScript("return document.title;").toString();
	}
	
	//which js method to go back,forward,refresh ?INTERVIEW 
	public void goBackWithJs() {
		js.executeScript("history.go(-1)");
	}
	public void goForwardWithJs() {
		js.executeScript("history.go(1)");
	}
	public void RefreshWithJs() {
		js.executeScript("history.go(0)");
	}
	
	//1.for alert generate 
	public void generateJSAlert(String msg)  {
		js.executeScript("alert('"+msg+"')");
		try{
			Thread.sleep(500);  //just to show alert msg for few sec		
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
		
		
		//driver.switchTo().alert().dismiss();  //for dismiss
	}
	
	//2.confirm alert
	public void generateJSConfirmAlert(String msg)  {
		js.executeScript("Confirm alert('"+msg+"')");
		try{
			Thread.sleep(500);  //just to show alert msg for few sec		
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
		
	}
	
	//3. Prompt alert
	public void generateJSPromptAlert(String msg)  {
		js.executeScript("Prompt alert('"+msg+"')");
		try{
			Thread.sleep(500);  //just to show alert msg for few sec		
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		//for prompt we'll send keys
		Alert alert=driver.switchTo().alert();
		alert.sendKeys("prompt");
		alert.accept();
		
	}
	
	//for whole content in the page=>it'll be useful for CONTENT TESTING
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();

}
	
	//Page Scrolling =may be at interview
	
	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
		
	}
	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '" + height + "')");
		
	}
	public void scrollPageDownMiddlePage(String height) {  //middle
		js.executeScript("window.scrollTo(0,document.body.scrollHeight/2)");
		
	}
	
	//need to pass element
	public void scrollIntoView(WebElement element) { //element => arguments[0] //we use always with arg[0]
		js.executeScript("arguments[0].scrollIntoView(true);", element);  //keep scrolling down until i found an element
	}
	
    /**
     * 
     * @param zoomPercentage
     */
	//Zooming with js ; works for safari also  ==> RESPONSIVE TESTING(zoom things and all)
	public void zoomChromeEdge(String zoomPercentage) { //only for chrome & edge becz both r from CHROMIUM driver
		String zoom="document.body.style.zoom=' " +zoomPercentage+ "%'"; //400
		js.executeScript(zoom);
	}
	
	//Firefox
	public void zoomFirefox(String zoomPercentage) { //only for chrome & edge
		String zoom="document.body.style.MozTransform= 'scale("+zoomPercentage+")'";  //  'scale(0.5)
		js.executeScript(zoom);
	}
	
	//no imp
	public void DrawBorder(WebElement element) {//create red color border
		js.executeScript("arguments[0].style.border='3px solid red'", element);
		
	}
	
	//background clour chng is not all imp for interview =>refer from code doc
	//flash
	public void flash(WebElement element) {
		String bgColor=element.getCssValue("backgroundColor");
		for(int i=0;i<10;i++) {
			changecolor("rgb(0,200,0)",element) ;
			changecolor(bgColor,element);
		}
	}
	
	public void changecolor(String color,WebElement element ) {
		JavascriptExecutor js=((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor=' " + color + "'",element);
		
		try {
			Thread.sleep(20);
		}catch(InterruptedException e) {
			
	}
	
}
}
