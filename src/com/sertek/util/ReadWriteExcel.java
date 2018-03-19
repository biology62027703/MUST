package com.sertek.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sertek.form.REPORT_FORMAT;
//
@SuppressWarnings("rawtypes")
public class ReadWriteExcel {
	String keys[];
	int temp_int=0;
	HashMap hm = new HashMap();
	public void readXLSFile(String Filename) throws IOException
	{
		//InputStream ExcelFileToRead = new FileInputStream("D:/MUST_playlist_2016_01.xls");
		InputStream ExcelFileToRead = new FileInputStream(Filename);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();
		
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					//System.out.print(cell.getStringCellValue()+" ");
					putdata(temp_int,cell.getStringCellValue());
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					//System.out.print((int)cell.getNumericCellValue()+" ");
					putdata(temp_int,(int)cell.getNumericCellValue());
					
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
				temp_int++;
			}
			temp_int=0;
		}	
	}
	
	/*public static void writeXLSFile() throws IOException {
		
		String excelFileName = "C:/Test.xls";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			HSSFRow row = sheet.createRow(r);
	
			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				HSSFCell cell = row.createCell(c);
				
				cell.setCellValue("Cell "+r+" "+c);
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}*/
	
	
	public ArrayList readXLSXFile(String folder) throws IOException
	{
		File folder1 = new File(folder);
		String[] list1 = folder1.list();		
		ArrayList<HashMap> ls = new ArrayList<HashMap>();
		for (int i = 0; i < list1.length; i++) {
			System.out.println(folder1+"\\"+list1[i]);	
			if(list1[i].toString().indexOf(".xlsx")>-1) {
				InputStream ExcelFileToRead = new FileInputStream(folder1+"//"+list1[i]);			
				XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
				XSSFSheet sheet = wb.getSheetAt(0);
				XSSFRow row; 
				XSSFCell cell;		
				Iterator rows = sheet.rowIterator();
				
				root:while (rows.hasNext())
				{
					temp_int=0;
					row=(XSSFRow) rows.next();
					Iterator cells = row.cellIterator();
					hm = new HashMap(); 
					while (cells.hasNext())
					{
						cell=(XSSFCell) cells.next();
						if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
						{
							if(cell.getStringCellValue().equals("SEQ")){
								System.out.println("抬頭欄");
								continue root;
							}
							putdata(temp_int,cell.getStringCellValue());							
						}
						else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
						{
							putdata(temp_int,(int)cell.getNumericCellValue());
						}
						else
						{
							//U Can Handel Boolean, Formula, Errors
							System.out.println("沒東西?");
						}
						temp_int++;
					}
					ls.add(hm);
				}
			}
		}
		return ls;
	}
	
	public void writeXLSXFile_google(String FileName,List<String[]> ls) throws IOException {
		
		String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;
		
		//REPORT_FORMAT RF= new REPORT_FORMAT();
		
		for (int r = 0; r < ls.size(); r++) {
			XSSFRow row = sheet.createRow(r);
		
			XSSFCell cell = row.createCell(0);   
			if(r==0) {
				cell.setCellValue("D");
			} else {
				cell.setCellValue("20180101");
			}
            for (int c = 0; c < ls.get(r).length; c++) {
            	cell = row.createCell(c+1);
                String cellval = ls.get(r)[c];
                cell.setCellValue(cellval);
                if(c==ls.get(r).length-1){
	                if(r==0) {
	                	cell = row.createCell(c+2);
	    				cell.setCellValue("P");
	    				cell = row.createCell(c+3);
	    				cell.setCellValue("T");
	    			} else {
	    				cell = row.createCell(c+2);
	    				cell.setCellValue("V");
	    				cell = row.createCell(c+3);
	    				cell.setCellValue("");
	    			}
                }
            }
           
            if(r!=0 && r%20000==0){
            	System.out.println(r);
            	System.gc();
            }
        }
		

		FileOutputStream fileOut = new FileOutputStream(FileName);
		//write this workbook to an Outputstream.
		wb.write(fileOut);		
		fileOut.flush();
		fileOut.close();
		System.out.println("檔案寫入完畢");
		ls.clear();
		System.gc();
		
	}
	
	public void writeXLSXFile(String FileName,String File_title,List ls,String title[],String colunm[]) throws IOException {
		
		//String excelFileName = "C:/Test.xlsx";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;
		
		
		REPORT_FORMAT RF= new REPORT_FORMAT();
		
		//iterating r number of rows
		for (int r=0;r <= ls.size(); r++ )
		{
			XSSFRow row = sheet.createRow(r);
			HashMap hm = new HashMap();
			//iterating c number of columns
			for (int c=0;c < RF.LAL_END_REPORT3_COLUNM.length; c++ )
			{
				XSSFCell cell = row.createCell(c);	
				//先寫TITLE
				if(r==0) {
					cell.setCellValue(title[c]);
				} else {
					hm = (HashMap)ls.get(r-1);					
					cell.setCellValue(hm.get(colunm[c]).toString());
					//hm.clear();
				}
				//System.out.println(RF.LAL_END_REPORT3_COLUNM[c]+":"+hm.get(RF.LAL_END_REPORT3_COLUNM[c]).toString());
				
			}
		}

		FileOutputStream fileOut = new FileOutputStream(FileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	public static void main(String[] args) throws IOException {
		//writeXLSFile();
		//readXLSFile();	
		//setKeys(new String[] {"SEQ","NAME","CASE_NO","POST_TYPE","POST_DATE","POST_NAME","POST_TEXTNO","POST_NOTE","POST_PTRFLAG"});
		//writeXLSXFile(null, null, null);
		//readXLSXFile("D:\\google相關\\2017Q2\\premium_xlsx");

	}
	
	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	@SuppressWarnings("unchecked")
	private void putdata(int key,String value){
		hm.put(keys[key],value);
	}
	@SuppressWarnings("unchecked")
	private void putdata(int key,int value){
		hm.put(keys[key],value);
	}


}
