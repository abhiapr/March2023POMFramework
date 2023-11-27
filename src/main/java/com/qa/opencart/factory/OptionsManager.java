package com.qa.opencart.factory;

import java.util.Properties;
//import java.util.HashMap;
//import java.util.Map;
//import java .util.Properties;


import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	private Properties prop ;
	
	// to link properties here =>constructor
	
	public OptionsManager (Properties prop ) {
		this.prop=prop;  //like this.driver=driver;
	}
	
	
	//headless works only if =true
	//incognito = doesn't maintain history ;private browser
	public ChromeOptions  getChromeOptions() {
		co=new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) { // converting string get property to boolean using parse
			co.addArguments("--headless=new");			
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) { // converting string get property to boolean using parse
			co.addArguments("--incognito");
			
		}
		
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "chrome");
			co.setBrowserVersion(prop.getProperty("browserversion").trim());
		}
		return co;				
	}
	
	//fire fox  -same for edge
	public FirefoxOptions getFirefoxOptions() {
		fo=new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) { // converting string get property to boolean using parse
			fo.addArguments("--headless=new");			
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) { // converting string get property to boolean using parse
			fo.addArguments("--incognito");
			
		}
		
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			fo.setCapability("browserName", "firefox");
			fo.setBrowserVersion(prop.getProperty("browserversion").trim());
		}
		return fo;				
	}
	
	//edge
	public EdgeOptions getEdgeOptions() {
		eo=new EdgeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) { // converting string get property to boolean using parse
			eo.addArguments("--headless=new");			
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) { // converting string get property to boolean using parse
			eo.addArguments("--incognito");
			
		}
		
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			eo.setCapability("browserName", "edge");
 		}
		return eo;
				
	}
	
	//safari doesn't support in headless mode

}
