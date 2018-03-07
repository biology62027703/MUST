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
 *	File Name     : util_cacheIniFile.java    
 *	Author        : Felix Lin              	
 *	Created Date  : 2001/5/20          	     
 *	Modified By   : Felix Lin              	
 *	Last Modified : 2001/5/20
 *	Description   : 用來讀取類似 window 的 System.ini ,
 *                      取之後會常駐到記憶體供經常讀取之ini使用
 *
 *     
 ***************************************************************************************************** 
*/
package com.sertek.util;

import java.io.*;
import java.util.*;

/**
 *    Description:  用來讀取類似 window 的 System.ini<BR>
 * Usage:<BR>
 *     請用 <BR>
 *     try {<BR>
 *        util_cacheIniFile _IniF = new util_cacheIniFile("SYS:\\Sys.ini");<BR>
 *     <BR>
 *     ..load data... <BR>
 *     就會直接載入 <BR>
 *     或 <BR>
 *     util_cacheIniFile _IniF = new util_cacheIniFile();<BR>
 *     if( _IniF.loadIniFile("SYS:\\Sys.ini") ) ..... <BR>
 *     ....load data.....
 *
 *     請注意使用 loadIniFile( String File )<BR>
 *     可以不用關閉之前的開檔,他會自動關閉 <BR>
 *     若想載入的 FileName 不存在則傳回 false <BR>
 *     然後就可以使用以下 method <BR>
 *        getFileName            <BR>
 *        extractFilePath        <BR>
 *        readInt                <BR>
 *        readString             <BR>
 *        readStrings            <BR>
 *        writeInt               <BR>
 *        writeString            <BR>
 *        close                  <BR>
 *     若要存回變更,用           <BR>
 *        flush()                <BR>
 *     若要 debug ,用            <BR>
 *        dump(DumpFileName)     <BR>
 *
 *  limitation: <BR>
 *  1. 請注意,因為 Java 在 JSP 中時, working directory<BR>
 *     並不一定和網頁的目錄一樣,所以 FileName 最好是設定<BR>
 *     絕對路徑,在 dump 時也是一樣 , 不然可能會寫到根目錄<BR>
 *     in Netware,Java Startup Working directory is SYS:/
 *     Example:  Ini.dump("Sys.ini");<BR>
 *     it will dump all data into SYS:/Sys.ini<BR>
 *
 *  2. 並不支援多人環境檔案鎖定,使用上對每個人必需使用不同的檔案<BR>
 *     writeXXX() 後,必需要呼叫 flush 後,異動才會寫回,不然<BR>
 *     就只是存在物件中<BR>
 *
 *  3. 接受 Ini File 的 comment 字元為 ';'<BR>
 *
 */

public class util_cacheIniFile extends util_IniFile {

  /**
  *<pre><br>
     util_IniFile  Constructor<br> 
     參數:無<BR>
     傳回:無  <br>
     說明:建立 util_IniFile 建構子<br>
  *</pre>
  */	
  public util_cacheIniFile() {super();}




  /**
  *<pre><br>
     util_IniFile  Constructor<br> 
     參數:String sFile:傳入檔案路徑檔案名稱<BR>
     傳回:無  <br>
     說明:建立 util_IniFile 建構子,載入檔案<br>
  *</pre>
  */	
  public util_cacheIniFile(String sFile) throws IOException {
    super();
    loadIniFile(sFile);
  }

  /**
  *<pre><br>
     util_IniFile  Constructor<br> 
     參數:1.String path:傳入檔案路徑<BR>
          2.String sFile:傳入檔案名稱<BR>
     傳回:無  <br>
     說明:建立 util_IniFile 建構子,載入檔案<br>
  *</pre>
  */	
  public util_cacheIniFile(String path,String sFile) throws IOException
  {
    super();
    loadIniFile(path,sFile);
  }

 

  /**
  *<pre><br>
     參數:String sFile:傳入檔案路徑檔案名稱<BR>
     傳回:無  <br>
     說明:載入的ini檔案內容<br>
  *</pre>
  */	
  public void loadIniFile(String path , String sFile) throws IOException
  {
     String FileSeperator = System.getProperty("file.separator") != null ? System.getProperty("file.separator") : "\\" ;
     if( path.charAt(path.length()-1) != FileSeperator.charAt(0))
     path = path.concat(FileSeperator);
     path = path.concat(sFile);
     loadIniFile(path);
  }
  
  /**
  *<pre><br>
     參數:1.String path:傳入檔案路徑<BR>
          2.String sFile:傳入檔案名稱<BR>
     傳回:無  <br>
     說明:載入的ini檔案內容<br>
  *</pre>
  */	
  public void loadIniFile(String sFile) throws IOException
  {
     util_cacheIniFile f = data_Cache.getCacheIni(sFile);
     System.out.println("GET_INI="+f);
     if( f != null )
     {
         IniFileV = f.getContent();
         if( IniFileV != null )
         {
             bIsValid = true;
             setFileName(sFile);
             return;
         }
     }
     setFileName(sFile);
     openIni();
     System.out.println(sFile+"重載");
     data_Cache.putCacheIni(this);
  }

  /**
  *<pre><br>
     參數:無<BR>
     傳回:Vector  <br>
     說明:讀取ini 之Vector 型態內容 <BR>
  *</pre>
  */	
  public Vector getContent()
  {
      if( IniFileV == null ) return null;
      return (Vector) IniFileV.clone();
  }
  /**
  *<pre><br>
     參數:無<BR>
     傳回:無  <br>
     說明:釋放所有資源<BR>
  *</pre>
  */	
  public void close()
  {
       if( f_handler.isReadable() )
       f_handler.close();
  }

} 

 







