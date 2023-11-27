package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	public static final String LOGIN_PAGE_TITLE="Account Login" ;//no one can change value
	public static final String LOGIN_PAGE_URL_FRACTION="route=account/login";
	
	public static final String ACCOUNT_PAGE_TITLE="My Account";
	public static final int ACCOUNTS_PAGE_HEADER_COUNT=4;
	
	//for expected header value List
	public static final List<String> EXPECTED_ACC_PAGE_HEADER_LIST=Arrays.asList("My Account","My Orders","My Affiliate Account","NewsLetter");
	
	
	//Account creation'
	public static final String USER_REGISTER_SUCCESS_MESSG="Your Account has been created";
	
	
	//*******Default Timeout values****************
	
	public static final int SHORT_TIME_OUT=5;
	public static final int MEDIUM_TIME_OUT=10;
	public static final int LONG_TIME_OUT=15;
	
	
	//*******Sheet name***********
	public static final String REGISTER_SHEET_NAME="register";
}
