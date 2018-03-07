/*
 **************************************************************************************************
 *                         		      Sertek, Inc.
 *
 * 	CONFIDENTIAL AND PROPRIETARY SOURCE CODE OF Sertek INC.
 *
 * 	Copyright (c) 2000 Sertek, Inc. All Rights Reserved.
 * 	Use of this Source Code is subject to the terms of the applicable
 * 	license agreement from Sertek, Inc.  The c opyright notice(s) 
 * 	in this Source Code does not indicate actual or intended publication 
 * 	of this Source Code.
 *
 *	File Name     : util_sessionIniFile.java    
 *	Author        : Felix Lin              	
 *	Created Date  : 2001/5/20          	     
 *	Modified By   : Felix Lin              	
 *	Last Modified : 2001/5/20
 *	Description   : 用來讀取類似 window 的 System.ini ,
 取之後會常駐到session供經常讀取之ini使用
 *
 *     
 ***************************************************************************************************** 
 */
package com.sertek.util;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpSession;

import com.sertek.util.data_Cache;

/**
 *    Description:  用來讀取類似 window 的 System.ini
 * Usage:
 *     請用 
 *     try {
 *        util_sessionIniFile _IniF = new util_sessionIniFile("SYS:\\Sys.ini");
 *     
 *     ..load data... 
 *     就會直接載入 
 *     或 
 *     util_sessionIniFile _IniF = new util_sessionIniFile();
 *     if( _IniF.loadIniFile("SYS:\\Sys.ini") ) ..... 
 *     ....load data.....
 *
 *     請注意使用 loadIniFile( String File )
 *     可以不用關閉之前的開檔,他會自動關閉 
 *     若想載入的 FileName 不存在則傳回 false 
 *     然後就可以使用以下 method 
 *        getFileName            
 *        extractFilePath        
 *        readInt                
 *        readString             
 *        readStrings            
 *        writeInt               
 *        writeString            
 *        close                  
 *     若要存回變更,用           
 *        flush()                
 *     若要 debug ,用            
 *        dump(DumpFileName)     
 *
 *  limitation: 
 *  1. 請注意,因為 Java 在 JSP 中時, working directory
 *     並不一定和網頁的目錄一樣,所以 FileName 最好是設定
 *     絕對路徑,在 dump 時也是一樣 , 不然可能會寫到根目錄
 *     in Netware,Java Startup Working directory is SYS:/
 *     Example:  Ini.dump("Sys.ini");
 *     it will dump all data into SYS:/Sys.ini
 *
 *  2. 並不支援多人環境檔案鎖定,使用上對每個人必需使用不同的檔案
 *     writeXXX() 後,必需要呼叫 flush 後,異動才會寫回,不然
 *     就只是存在物件中
 *
 *  3. 接受 Ini File 的 comment 字元為 ';'
 *
 */

public class util_sessionIniFile extends util_IniFile {

	protected HttpSession session;

	/**
	 *
	 util_IniFile  Constructor 
	 參數:HttpSession  in_session:傳入session
	 傳回:無  
	 說明:建立 util_IniFile 建構子
	 *
	 */
	public util_sessionIniFile(HttpSession in_session) {
		super();
		session = in_session;
	}

	/**
	 *
	 util_IniFile  Constructor 
	 參數:1.String sFile:傳入檔案路徑檔案名稱
	 2.HttpSession  in_session:傳入session
	 傳回:無  
	 說明:建立 util_IniFile 建構子,載入檔案
	 *
	 */
	public util_sessionIniFile(String sFile, HttpSession in_session) throws IOException {
		super();
		session = in_session;
		loadIniFile(sFile);
	}

	/**
	 *
	 util_IniFile  Constructor 
	 參數:1.String path:傳入檔案路徑
	 2.String sFile:傳入檔案名稱
	 3.HttpSession  in_session:傳入session
	 傳回:無  
	 說明:建立 util_IniFile 建構子,載入檔案
	 *
	 */
	public util_sessionIniFile(String path, String sFile, HttpSession in_session)
		throws IOException {
		super();
		session = in_session;
		loadIniFile(path, sFile);
	}

	/**
	 *
	 參數:String sFile:傳入檔案路徑檔案名稱
	 傳回:無  
	 說明:載入的ini檔案內容
	 *
	 */
	public void loadIniFile(String path, String sFile) throws IOException {
		String FileSeperator = System.getProperty("file.separator") != null ? System
			.getProperty("file.separator") : "\\";
		if (path.charAt(path.length() - 1) != FileSeperator.charAt(0))
			path = path.concat(FileSeperator);
		path = path.concat(sFile);
		loadIniFile(path);
	}

	/**
	 *
	 參數:1.String path:傳入檔案路徑
	 2.String sFile:傳入檔案名稱
	 傳回:無  
	 說明:載入的ini檔案內容
	 *
	 */
	public void loadIniFile(String sFile) throws IOException {
		util_sessionIniFile f = data_Cache.getSessionIni(sFile, session);
		System.out.println("GET_INI=" + f);
		if (f != null) {
			IniFileV = f.getContent();
			if (IniFileV != null) {
				bIsValid = true;
				setFileName(sFile);
				return;
			}
		}
		setFileName(sFile);
		openIni();
		System.out.println(sFile + "重載");
		data_Cache.putSessionIni(this, session);
	}

	public void loadIniFile() throws IOException {
		openIni2();
		data_Cache.putSessionIni(this, session);
	}

	/**
	 *
	 參數:無
	 傳回:Vector  
	 說明:讀取ini 之Vector 型態內容 
	 *
	 */
	public Vector getContent() {
		if (IniFileV == null)
			return null;
		return (Vector) IniFileV.clone();
	}

	/**
	 *
	 參數:無
	 傳回:無  
	 說明:釋放所有資源
	 *
	 */
	public void close() {
		if (f_handler.isReadable())
			f_handler.close();
	}

}

