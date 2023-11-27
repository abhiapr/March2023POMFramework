
package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtil {
	
	public static final String TEST_DATA_SHEET_PATH="src/test/resources/testdata/OpenCartTestData.xlsx";  // copy path of excel	
	//public static workBook book;
	public static Sheet sheet;
	
	//works only if we have Licensed Excel sheet - paste it in Testdata under resources
	// don't use many excel files (high maintenance)  . USE Different TABS in SAME EXCEL
	// Limit is 255 excel sheets
	
	public static Object[][] getTestData(String sheetName) {
		System.out.println(" reading the data from the sheet: " +sheetName);
		
		Object data[][]=null;
		
		try {
			FileInputStream ip=new FileInputStream(TEST_DATA_SHEET_PATH);			
			// from workbook to sheet
			//book=WorkbookFactory.create(ip); // ip= input file pointing excel
			//sheet=book.getSheet(sheetName);
			
			// like static array in java : int i[][]=new int[][] [row,column]
			//data= new Object[3][5];   = making dynamic below
			
			data= new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			//getRow(0) - from 0th row give me last column (getLastCellNum) -5
			
			for(int i=0; i<sheet.getLastRowNum(); i++) { //row 0 to last row
				
				for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j]=sheet.getRow(i+1).getCell(j).toString();  // i=0,j=1(0,1, 0,2  0,3  0,4)then i=1
							
				 // i=0,j=0 => not header ; it's first data in excel like pooja
				}
			}
			
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		//}catch(InvalidFormatException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
			
		}
		
		return data;
	}
	
	

}
