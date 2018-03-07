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
 *	File Name     : data_Chace.java    
 *	Author        : Felix Lin              	
 *	Created Date  : 2001/5/20           	     
 *	Modified By   : Felix Lin              	
 *	Last Modified : 2001/5/20
 *	Description   : 1.儲存系統常用片語的cache,讀取片語sql檔cached.sql的設定
 *                        將資料庫搜尋出的片語資料,放進Cache
 *                      2.另一功能為 儲放ini 檔案的cache 
 *
 *     
 ***************************************************************************************************** 
*/
package com.sertek.util;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import com.sertek.db.*;
import com.sertek.sys.*;
import javax.servlet.http.HttpSession;
public final class data_Cache {
  /**
  *<pre><br>
     data_Cache  Constructor<br> 
     參數:無<BR>
     傳回:無  <br>
     說明:建立 data_Cache 建構子<br>
  *</pre>
  */	
  private data_Cache() {}
  private static Hashtable sqltable = new Hashtable() ;
  private static Hashtable sqlresult = new Hashtable();
  private static Hashtable errtable = new Hashtable ();
  private static Hashtable initable = new Hashtable ();
  static {
      try {
          loadsqlFile();
      }catch(Exception ignore) { }
  }
  /**
  *<pre><br>
     參數:無<BR>
     傳回:無  <br>
     說明:載入judinit.ini檔案,將資料存放在cache<br>
  *</pre>
  */	  
  private static void loadjudinitFile() throws Exception
  {
      util_File file=new util_File();
      sqltable.clear();
      sqlresult.clear();
      errtable.clear();
      String judinitFile = sys_cfg. getValue("judinit");
      String FileSeperator = System.getProperty("file.separator");
      
      if( judinitFile == null )
          if (FileSeperator.equals("\\"))
          {  
             judinitFile = "C:/config/judinit.ini";  
          }
          else 
          {
             judinitFile = "/opt/IBMWebAS/hosts/default_host/config/judinit.ini";
          }
     util_cacheIniFile session_ini=new util_cacheIniFile(judinitFile);      
  }
  /**
  *<pre><br>
     參數:無<BR>
     傳回:無  <br>
     說明:載入cached.sql檔案並執行SQL,將資料存放在cache<br>
  *</pre>
  */	  
  private static void loadsqlFile() throws Exception
  {
      util_File file=new util_File();
      sqltable.clear();
      sqlresult.clear();
      errtable.clear();
      String sqlFile = sys_cfg. getValue("cachedsql");
      //String sqlFile = "c:\\config\\cached.sql";
      String FileSeperator = System.getProperty("file.separator");
      
      if( sqlFile == null )
          if (FileSeperator.equals("\\"))
          {
             //sqlFile = "C:/config/cached.sql";  
          }
          else 
          {
             //sqlFile = "/opt/IBMWebAS/hosts/default_host/config/cached.sql";
          }
      File f = new File(sqlFile);
      if(!f.exists()) throw new Exception("File:" + sqlFile + " not exists");
      LineNumberReader lreader = null;
      try {
          lreader = new LineNumberReader(
                    new BufferedReader (
                    new FileReader(f)));
          String line = null;
          while( (line = lreader.readLine() ) != null )
          {
              if ( line.length() > 0 )
              {
                 char c = line.charAt(0);
                 if( c == ';' ) continue;
                 if( c == '#' ) continue;
                 int idx =  line.indexOf("=");
                 if( idx > 0 )
                 {
                     String name="";
                     if (FileSeperator.equals("\\"))
                         name =name = line.substring(0,idx);
                     else
                         name =new String( line.substring(0,idx).getBytes(System.getProperty("file.encoding")) , "MS950" );
                         //name =new String( line.substring(0,idx).getBytes("ANSI_X3.4-1968") , "MS950" );
                     String value  = line.substring(idx+1);
                     sqltable.put(name.toUpperCase(),value);
          
                 }
               }
          }
          
     } catch (Exception e) { throw e;
      } finally {
          if( lreader != null )
          try { lreader.close();
          } catch (Exception ignore) {}
      } // after load sql file , run all sql and save it to hash
      Enumeration enumeration = sqltable.keys();
      DBUtility DB=null;
      try {
          
          DB=new DBUtility();
          DB.openConnection();
          StringBuffer tmp = new StringBuffer();
          while( enumeration.hasMoreElements() )
          {
              String name = (String) enumeration.nextElement();
              String sql = (String) sqltable.get(name);
              
              try {
                  
                  DB.doSqlSelect(sql);
                  System.out.println(sql);
                  ResultSetMetaData meta = DB.getResultSetObject().getMetaData();
                  int cols = meta.getColumnCount();
                  Vector V = new Vector();
                  tmp.append("\n"+name+"\n");
                  while( DB.next() )
                  {
                      Vector subV = new Vector();
                      String s1 = null;
                      String s2 = null;
                      String s3 = null;

                      if( cols == 1 )
                      {
                         s1 = DB.getString(1);
                         if( s1 == null ) s1 = "";
                         subV.addElement(s1); 
                         System.out.println(s1); 
                         tmp.append(s1+"\n");
                      }else if( cols == 2 ) {
                         s1=DB.getString(1);
                         s2=DB.getString(2);
                         if( s1==null) s1 = "";
                         if( s2==null) s2 = "";
                         subV.addElement(s1); 
                         subV.addElement(s2); 
                         System.out.println(s1+" , "+s2);
                         tmp.append(s1+" , "+s2+"\n");
                      }else if(cols >= 3){
                         s1=DB.getString(1);
                         s2=DB.getString(2);
                         s3=DB.getString(3);
                         if( s1==null) s1 = "";
                         if( s2==null) s2 = "";
                         if( s3==null) s3 = "";
                         subV.addElement(s1); 
                         subV.addElement(s2); 
                         subV.addElement(s3); 
                         System.out.println(s1+" , "+s2+" , "+s3);
                         tmp.append(s1+" , "+s2+" , "+s3+"\n");
                      }
                      V.addElement(subV);
                  }
                  sqlresult.put(name,V);
                  System.out.println(name);
              }catch(Exception sqlerr) {
                  sqltable.remove(name);
                  errtable.put(name,sql);
              }
          }
      } catch (Exception e) {
        throw e;
      } finally {
        if( DB != null ) DB.closeConnection(); DB = null;
      }
  }
  /**
  *<pre><br>
     參數:無<BR>
     傳回:無  <br>
     說明:重新載入cached.sql檔案並執行SQL,將資料存放在cache<br>
  *</pre>
  */	
  public static void reload() throws Exception
  {
      loadsqlFile();
  }
  /**
  *<pre><br>
     參數:JspWriter out:傳入 JspWriter物件<BR>
          String sqlname:片語名稱<BR>
     傳回:  <br>
     說明:從cache裏取出片語<br>
  *</pre>
  */	
  public static synchronized void sql(javax.servlet.jsp.JspWriter out,String sqlname) throws ServletException
  {
    if( sqlname == null ) return;
    try {
          
        Vector V = (Vector) sqlresult.get(sqlname.toUpperCase());
        StringBuffer buf = new StringBuffer();
        for(int i=0;i< V.size() ;i++)
        {
          Vector subV=(Vector) V.elementAt(i);
          int cols = subV.size();

          buf.setLength(0);
          String s1 = null;
          String s2 = null;
          String s3 = null;
               
          if( cols == 1 )
          {
             s1 = (String) subV.elementAt(0);
             if( s1 == null ) s1 = "";
             buf.append("<OPTION VALUE='")
                .append(s1)
                .append("'>")
                .append(s1);
          }else if( cols == 2 ) {
             s1=(String) subV.elementAt(0);
             s2=(String) subV.elementAt(1);
             if( s1==null) s1 = "";
             if( s2==null) s2 = "";
             buf.append("<OPTION VALUE='")
                .append(s1)
                .append("'>")
                .append(s1)
                .append(" ")
                .append(s2);

          }else if(cols >= 3){
             s1=(String) subV.elementAt(0);
             s2=(String) subV.elementAt(1);
             s3=(String) subV.elementAt(2);
             if( s1==null) s1 = "";
             if( s2==null) s2 = "";
             buf.append("<OPTION VALUE='")
                .append(s1)
                .append("'>")
                .append(s2)
                .append(" ")
                .append(s3);
          }                      
            out.println((String) buf.toString());
        }
    } catch (Exception e) {
      throw new ServletException(e);
    }

  }
  /**
  *<pre><br>
     參數:String sqlname:片語名稱<BR>
     傳回: Vector <br>
     說明:從cache裏取出片語資料 ,以Vector傳回<br>
  *</pre>
  */	
  public static Vector getCache(String sqlname)
  {
      Vector V = (Vector) sqlresult.get(sqlname.toUpperCase());
      if( V != null )
      return V;
      return null;
  }
   
  /**
  *<pre><br>
     參數:無<BR>
     傳回:無<br>
     說明:從cache裏取出所有片語資料 ,以Enumeration傳回<br>
  *</pre>
  */	  
  public static Enumeration getSqlCache()
  {
      if( sqltable != null )
      return sqltable.elements();
      return null;
  }
  
  
  /**
  *<pre><br>
     參數:無<BR>
     傳回:無<br>
     說明:取出錯誤資訊 ,以Enumeration傳回<br>
  *</pre>
  */
  public static Enumeration getSqlError()
  {
      if( errtable != null )
      return errtable.elements();
      return null;
  }
  /**
  *<pre><br>
     參數:1.String FileName:指定要取回的ini 名稱(完整路徑)<BR>
          2.HttpSession  in_session:傳入session<BR> 
     傳回:util_sessionIniFile<br>
     說明:取出cache裏ini檔的資料 ,以util_cacheIniFile傳回<br>
  *</pre>
  */
  public static synchronized util_sessionIniFile getSessionIni(String FileName,HttpSession  in_session)
  {
      if( FileName == null ) return null;
      FileName = FileName.toUpperCase();
      util_sessionIniFile f =(util_sessionIniFile)in_session.getAttribute(FileName);
      for(Enumeration enumeration = initable.keys(); enumeration.hasMoreElements();)
      {
          String s2 = ((String)enumeration.nextElement()).trim();
          System.out.println("INI_key="+s2);
      }
      if( f != null )
      {
        if( f.isModified() )
        {
            // free the File Content 
            Vector IniFileV = f.getContent();
            if ( IniFileV != null )
            {
              for( int i=0 ;i<IniFileV.size();i++ )
              {
                Vector V = (Vector) IniFileV.elementAt(i);
                if( V != null )
                    V.removeAllElements();  // delete entries
              }
            }
            in_session.removeAttribute(FileName);
            return null;
        } else {
            return f;
        }
      }
      return null;
   }
  
  /**
  *<pre><br>
     參數:1.util_sessionIniFile f:傳入util_cacheIniFile 物件<BR>
          2.HttpSession  in_session:傳入session<BR> 
     傳回:無<br>
     說明:將ini檔的內容放入Session裏<br>
  *</pre>
  */
  public static synchronized void putSessionIni(util_sessionIniFile f,HttpSession  in_session)
  {
      if( f == null ) return;
      if( f.isModified() ) return;
      String name = f.getFullFileName();
      System.out.println("PUT_INI="+name);
      if( name == null ) return;
      if( f.isValid() )
      { 
         in_session.setAttribute(name.toUpperCase(),f);
      }
  }
  
  /**
  *<pre><br>
     參數:String FileName:指定要取回的ini 名稱(完整路徑)<BR>
     傳回:util_cacheIniFile<br>
     說明:取出cache裏ini檔的資料 ,以util_cacheIniFile傳回<br>
  *</pre>
  */
  public static synchronized util_cacheIniFile getCacheIni(String FileName)
  {
      if( FileName == null ) return null;
      FileName = FileName.toUpperCase();
      util_cacheIniFile f = (util_cacheIniFile) initable.get(FileName);
      for(Enumeration enumeration = initable.keys(); enumeration.hasMoreElements();)
      {
          String s2 = ((String)enumeration.nextElement()).trim();
          System.out.println("INI_key="+s2);
      }
      if( f != null )
      {
        if( f.isModified() )
        {
            // free the File Content 
            Vector IniFileV = f.getContent();
            if ( IniFileV != null )
            {
              for( int i=0 ;i<IniFileV.size();i++ )
              {
                Vector V = (Vector) IniFileV.elementAt(i);
                if( V != null )
                    V.removeAllElements();  // delete entries
              }
            }
            initable.remove( FileName );
            return null;
        } else {
            return f;
        }
      }
      return null;
   }
   
  /**
  *<pre><br>
     參數:util_cacheIniFile f:傳入util_cacheIniFile 物件<BR>
     傳回:無<br>
     說明:將ini檔的內容放入cache裏<br>
  *</pre>
  */
  public static synchronized void putCacheIni(util_cacheIniFile f)
  {
      if( f == null ) return;
      if( f.isModified() ) return;
      String name = f.getFullFileName();
      System.out.println("PUT_INI="+name);
      if( name == null ) return;
      if( f.isValid() )
      { 
         initable.put(name.toUpperCase(),f);
      }
  }
  
  /**
  *<pre><br>
     參數:無<BR>
     傳回:Hashtable<br>
     說明:取出cache 裏所有ini 檔資料 ,以Hashtable傳回<br>
  *</pre>
  */
  public static Hashtable getIniFileHash() 
  {
      return initable;
  }

}




