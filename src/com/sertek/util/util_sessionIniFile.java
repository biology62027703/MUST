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
 *	Description   : �Ψ�Ū������ window �� System.ini ,
 ������|�`�n��session�Ѹg�`Ū����ini�ϥ�
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
 *    Description:  �Ψ�Ū������ window �� System.ini
 * Usage:
 *     �Х� 
 *     try {
 *        util_sessionIniFile _IniF = new util_sessionIniFile("SYS:\\Sys.ini");
 *     
 *     ..load data... 
 *     �N�|�������J 
 *     �� 
 *     util_sessionIniFile _IniF = new util_sessionIniFile();
 *     if( _IniF.loadIniFile("SYS:\\Sys.ini") ) ..... 
 *     ....load data.....
 *
 *     �Ъ`�N�ϥ� loadIniFile( String File )
 *     �i�H�����������e���}��,�L�|�۰����� 
 *     �Y�Q���J�� FileName ���s�b�h�Ǧ^ false 
 *     �M��N�i�H�ϥΥH�U method 
 *        getFileName            
 *        extractFilePath        
 *        readInt                
 *        readString             
 *        readStrings            
 *        writeInt               
 *        writeString            
 *        close                  
 *     �Y�n�s�^�ܧ�,��           
 *        flush()                
 *     �Y�n debug ,��            
 *        dump(DumpFileName)     
 *
 *  limitation: 
 *  1. �Ъ`�N,�]�� Java �b JSP ����, working directory
 *     �ä��@�w�M�������ؿ��@��,�ҥH FileName �̦n�O�]�w
 *     ������|,�b dump �ɤ]�O�@�� , ���M�i��|�g��ڥؿ�
 *     in Netware,Java Startup Working directory is SYS:/
 *     Example:  Ini.dump("Sys.ini");
 *     it will dump all data into SYS:/Sys.ini
 *
 *  2. �ä��䴩�h�H�����ɮ���w,�ϥΤW��C�ӤH���ݨϥΤ��P���ɮ�
 *     writeXXX() ��,���ݭn�I�s flush ��,���ʤ~�|�g�^,���M
 *     �N�u�O�s�b����
 *
 *  3. ���� Ini File �� comment �r���� ';'
 *
 */

public class util_sessionIniFile extends util_IniFile {

	protected HttpSession session;

	/**
	 *
	 util_IniFile  Constructor 
	 �Ѽ�:HttpSession  in_session:�ǤJsession
	 �Ǧ^:�L  
	 ����:�إ� util_IniFile �غc�l
	 *
	 */
	public util_sessionIniFile(HttpSession in_session) {
		super();
		session = in_session;
	}

	/**
	 *
	 util_IniFile  Constructor 
	 �Ѽ�:1.String sFile:�ǤJ�ɮ׸��|�ɮצW��
	 2.HttpSession  in_session:�ǤJsession
	 �Ǧ^:�L  
	 ����:�إ� util_IniFile �غc�l,���J�ɮ�
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
	 �Ѽ�:1.String path:�ǤJ�ɮ׸��|
	 2.String sFile:�ǤJ�ɮצW��
	 3.HttpSession  in_session:�ǤJsession
	 �Ǧ^:�L  
	 ����:�إ� util_IniFile �غc�l,���J�ɮ�
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
	 �Ѽ�:String sFile:�ǤJ�ɮ׸��|�ɮצW��
	 �Ǧ^:�L  
	 ����:���J��ini�ɮפ��e
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
	 �Ѽ�:1.String path:�ǤJ�ɮ׸��|
	 2.String sFile:�ǤJ�ɮצW��
	 �Ǧ^:�L  
	 ����:���J��ini�ɮפ��e
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
		System.out.println(sFile + "����");
		data_Cache.putSessionIni(this, session);
	}

	public void loadIniFile() throws IOException {
		openIni2();
		data_Cache.putSessionIni(this, session);
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:Vector  
	 ����:Ū��ini ��Vector ���A���e 
	 *
	 */
	public Vector getContent() {
		if (IniFileV == null)
			return null;
		return (Vector) IniFileV.clone();
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:�L  
	 ����:����Ҧ��귽
	 *
	 */
	public void close() {
		if (f_handler.isReadable())
			f_handler.close();
	}

}

