package com.sertek.util;

import java.io.File;
import java.util.*;
import java.util.zip.*;
import java.io.*;
/**
 * @deprecated
 * 壓縮檔內有中文檔名時會有問題，請改用 AzipUtil (apahce zip util)
 * 取得壓縮檔內的檔案資料
 * 使用範例：
 *  import="java.util.zip.*;
 *  
 * zipUtil zu = new zipUtil();
 *if (zu.loadZip("c:\\setup.zip")){
*	//Enumeration zu.
*	Enumeration e = zu.getEntries();
*	int i = 1;
*	while (e.hasMoreElements() ){
*		ZipEntry z = (ZipEntry)e.nextElement();
*		//不是路徑才處理
*		if (!z.isDirectory()){			
*			out.println("第"+i+"筆<BR>");
*			out.println("路徑="+ zu.getZipPath(z) +"<BR>" );
*			out.println("大小="+ zu.getZipentrySize(z) +"<BR>" );
*			out.println("日期="+ zu.getZipentryDate(z)+"<BR>" );
*			out.println("時間="+ zu.getZipentryTime(z)+"<BR>" );
*			i++;
*		}	
*	}
*	
*	
*}
 *
 */
public class zipUtil {
	util_date ud = new util_date();

	ZipFile zf = null;
	String zipPath = "";
	
	
	public zipUtil(){
		
	}
	public boolean loadZip(String zipPath){
		boolean retVal = true;
		this.zipPath = zipPath;
		try{
			zf = new ZipFile(zipPath);
		}catch(IOException e){
			retVal = false;
		}
		return retVal;
	}
	
	public Enumeration getEntries(){
		return zf.entries();
	}
	public String getZipPath(ZipEntry zn){
		return zn.getName();
	}
	/**
	 * 取得壓縮檔內的 檔案大小
	 * @param entryName 檔案名稱(必需包含壓縮檔內的路徑資訊)
	 * @return
	 */
	public long getZipentrySize(String entryName){
		long retVal = 0;
		ZipEntry zn  = zf.getEntry(entryName) ;
		if (zn!=null){
			retVal =  getZipentrySize(zn);
			zn = null;
		}
		return retVal;
	}
	/**
	 * 取得壓縮檔內的 檔案大小
	 * @param zn ZipEntry 
	 * @return 
	 */
	public long getZipentrySize(ZipEntry zn){
		long retVal = 0;
		if (zn!=null){
			retVal =  zn.getSize();
			
		}
		return retVal;
	}
	
	/**
	 * 取得壓縮檔內的 檔案時間
	 * @param entryName 檔案名稱((必需包含壓縮檔內的路徑資訊)
	 * @return 回傳六碼時間，否則回傳空白
	 */
	public String getZipentryTime(String entryName){
		String retVal = "";
		ZipEntry zn  = zf.getEntry(entryName) ;
		if (zn!=null){	
			retVal = getZipentryTime(zn);
			zn = null;
		}
		return retVal;
		
	}
	
	/**
	 * 取得壓縮檔內的 檔案時間
	 * @param entryName 檔案名稱((必需包含壓縮檔內的路徑資訊)
	 * @return 回傳六碼時間，否則回傳空白
	 */
	public String getZipentryTime(ZipEntry zn){
		String retVal = "";
		if (zn!=null){
			Calendar rightNow = Calendar.getInstance();
			Date d = new Date(zn.getTime());
			rightNow.setTime(d);
			retVal = ud.nowTime(rightNow);
		
		}
		return retVal;
		
	}
	
	/**
	 * 取得壓縮檔內的 檔案日期
	 * @param entryName 檔案名稱((必需包含壓縮檔內的路徑資訊)
	 * @return 回傳七碼日期，否則回傳空白
	 */
	public String getZipentryDate(String entryName){
		String retVal = "";
		ZipEntry zn  = zf.getEntry(entryName) ;
		if (zn!=null){
			retVal = getZipentryDate(zn);
			zn = null;
		}
		return retVal;
	}
	
	/**
	 * 取得壓縮檔內的 檔案日期
	 * @param zn ZipEntry
	 * @return 回傳七碼日期，否則回傳空白
	 */
	public String getZipentryDate(ZipEntry zn){
		String retVal = "";
		if (zn!=null){
			Calendar rightNow = Calendar.getInstance();
			Date d = new Date(zn.getTime());
			rightNow.setTime(d);
			retVal = ud.nowCDate(rightNow);
			
		}
		return retVal;
	}
	
	
}
