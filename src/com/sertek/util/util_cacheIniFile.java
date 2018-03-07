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
 *	Description   : �Ψ�Ū������ window �� System.ini ,
 *                      ������|�`�n��O����Ѹg�`Ū����ini�ϥ�
 *
 *     
 ***************************************************************************************************** 
*/
package com.sertek.util;

import java.io.*;
import java.util.*;

/**
 *    Description:  �Ψ�Ū������ window �� System.ini<BR>
 * Usage:<BR>
 *     �Х� <BR>
 *     try {<BR>
 *        util_cacheIniFile _IniF = new util_cacheIniFile("SYS:\\Sys.ini");<BR>
 *     <BR>
 *     ..load data... <BR>
 *     �N�|�������J <BR>
 *     �� <BR>
 *     util_cacheIniFile _IniF = new util_cacheIniFile();<BR>
 *     if( _IniF.loadIniFile("SYS:\\Sys.ini") ) ..... <BR>
 *     ....load data.....
 *
 *     �Ъ`�N�ϥ� loadIniFile( String File )<BR>
 *     �i�H�����������e���}��,�L�|�۰����� <BR>
 *     �Y�Q���J�� FileName ���s�b�h�Ǧ^ false <BR>
 *     �M��N�i�H�ϥΥH�U method <BR>
 *        getFileName            <BR>
 *        extractFilePath        <BR>
 *        readInt                <BR>
 *        readString             <BR>
 *        readStrings            <BR>
 *        writeInt               <BR>
 *        writeString            <BR>
 *        close                  <BR>
 *     �Y�n�s�^�ܧ�,��           <BR>
 *        flush()                <BR>
 *     �Y�n debug ,��            <BR>
 *        dump(DumpFileName)     <BR>
 *
 *  limitation: <BR>
 *  1. �Ъ`�N,�]�� Java �b JSP ����, working directory<BR>
 *     �ä��@�w�M�������ؿ��@��,�ҥH FileName �̦n�O�]�w<BR>
 *     ������|,�b dump �ɤ]�O�@�� , ���M�i��|�g��ڥؿ�<BR>
 *     in Netware,Java Startup Working directory is SYS:/
 *     Example:  Ini.dump("Sys.ini");<BR>
 *     it will dump all data into SYS:/Sys.ini<BR>
 *
 *  2. �ä��䴩�h�H�����ɮ���w,�ϥΤW��C�ӤH���ݨϥΤ��P���ɮ�<BR>
 *     writeXXX() ��,���ݭn�I�s flush ��,���ʤ~�|�g�^,���M<BR>
 *     �N�u�O�s�b����<BR>
 *
 *  3. ���� Ini File �� comment �r���� ';'<BR>
 *
 */

public class util_cacheIniFile extends util_IniFile {

  /**
  *<pre><br>
     util_IniFile  Constructor<br> 
     �Ѽ�:�L<BR>
     �Ǧ^:�L  <br>
     ����:�إ� util_IniFile �غc�l<br>
  *</pre>
  */	
  public util_cacheIniFile() {super();}




  /**
  *<pre><br>
     util_IniFile  Constructor<br> 
     �Ѽ�:String sFile:�ǤJ�ɮ׸��|�ɮצW��<BR>
     �Ǧ^:�L  <br>
     ����:�إ� util_IniFile �غc�l,���J�ɮ�<br>
  *</pre>
  */	
  public util_cacheIniFile(String sFile) throws IOException {
    super();
    loadIniFile(sFile);
  }

  /**
  *<pre><br>
     util_IniFile  Constructor<br> 
     �Ѽ�:1.String path:�ǤJ�ɮ׸��|<BR>
          2.String sFile:�ǤJ�ɮצW��<BR>
     �Ǧ^:�L  <br>
     ����:�إ� util_IniFile �غc�l,���J�ɮ�<br>
  *</pre>
  */	
  public util_cacheIniFile(String path,String sFile) throws IOException
  {
    super();
    loadIniFile(path,sFile);
  }

 

  /**
  *<pre><br>
     �Ѽ�:String sFile:�ǤJ�ɮ׸��|�ɮצW��<BR>
     �Ǧ^:�L  <br>
     ����:���J��ini�ɮפ��e<br>
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
     �Ѽ�:1.String path:�ǤJ�ɮ׸��|<BR>
          2.String sFile:�ǤJ�ɮצW��<BR>
     �Ǧ^:�L  <br>
     ����:���J��ini�ɮפ��e<br>
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
     System.out.println(sFile+"����");
     data_Cache.putCacheIni(this);
  }

  /**
  *<pre><br>
     �Ѽ�:�L<BR>
     �Ǧ^:Vector  <br>
     ����:Ū��ini ��Vector ���A���e <BR>
  *</pre>
  */	
  public Vector getContent()
  {
      if( IniFileV == null ) return null;
      return (Vector) IniFileV.clone();
  }
  /**
  *<pre><br>
     �Ѽ�:�L<BR>
     �Ǧ^:�L  <br>
     ����:����Ҧ��귽<BR>
  *</pre>
  */	
  public void close()
  {
       if( f_handler.isReadable() )
       f_handler.close();
  }

} 

 







