package com.sertek.file;

import java.util.*;
import java.io.*;

public class GenerateJS_HTMFile {
	/**
		*用以產生相關開窗所需要的JS與HTM檔案
	*/
	public GenerateJS_HTMFile() {
		setPath(rootpath);
		
	}

	/**
		*key值有:<BR>
			jsfilename:	JS的檔名，不含路徑<BR>
			ex:"d:/resin1.2.0/public_htm/N/script/Array_temp.js"<BR>
			arrayname:	JS檔中的array名稱<BR>
			htmfilename:	HTM的檔名，不含路徑<BR>
			ex:"d:/resin1.2.0/public_htm/N/HHD/Temp_Code.htm"
			selectname:	HTM檔中的下拉選單名稱<BR>
			selectsize:	HTM檔中的下拉選單的大小<BR>
			path:		此產生檔案的根路徑<BR>
			ex:"d:/resin1.2.0/public_htm/N"。此js與htm的產生於此路徑下的script目錄與html目錄下。<BR>
			field:		產生的HTM中，該下拉選單中的value與option值是不是由2個欄位合成產生。
			ex:		(String)1:value為vr的第一個欄位，option為vr的第二個欄位<BR>
					(String)2:value為vr的第一個欄位與第二個欄位，option為vr的第一個欄位與第二個欄位。
			
		*	建構子
		@param		detail	(Hashtable)存有產生JS與HTM相關檔案的參數
	*/
	public GenerateJS_HTMFile(Hashtable detail) {
		setDetail(detail);
		
	}
	
	public void setDetail(Hashtable detail) {
		if(detail.get("jsfilename")!=null) setJSFileName((String)detail.get("jsfilename"));
		if(detail.get("arrayname")!=null) setArrayName((String)detail.get("arrayname"));
		if(detail.get("htmfilename")!=null) setHTMFileName((String)detail.get("htmfilename"));
		if(detail.get("selectname")!=null) setSelectName((String)detail.get("selectname"));
		if(detail.get("selectsize")!=null) setSelectSize((String)detail.get("selectsize"));
		if(detail.get("path")!=null) setPath((String)detail.get("path"));
		if(detail.get("field")!=null) setField((String)detail.get("field"));
		if(detail.get("scriptfile")!=null) setScriptFile((String[])detail.get("scriptFile"));
		if(detail.get("style")!=null) setStyle((String)detail.get("style"));
	}
	
	/**
		*	設定js的檔名與路徑
		@param		name	js的檔名與路徑
		@return 	void
	*/
	public void setJSFileName(String name) {
		this.jsfilename = name;
	}
	
	/**
		*	設定HTM的檔名與路徑
		@param		name	htm的檔名與路徑
		@return		void
	*/
	public void setHTMFileName(String name) {
		this.htmfilename = name;
	}
	
	/**
		*	設定jS檔中的陣列名稱
		@param		name	jS檔中的陣列名稱
		@return 	void
	*/
	public void setArrayName(String name) {
		this.arrayname = name;	
	}
	
	/**
		*	設定htm檔中的下拉選單名稱
		@param		name	htm檔中的下拉選單名稱
		@return 	void
	*/
	public void setSelectName(String name) {
		this.selectname = name;
	}
	
	
	/**
		*	設定htm檔中的下拉選單大小
		@param		size	(String)htm檔中的下拉選單大小
		@return 	void
	*/
	public void setSelectSize(String size) {
		try {
			setSelectSize(Integer.parseInt(size));
		} catch(NumberFormatException e) {
		
		}
	}
	
	/**
		*	設定htm檔中的下拉選單大小
		@param		size	(int)htm檔中的下拉選單大小
		@return 	void
	*/
	public void setSelectSize(int size) {
		this.size = size;
	}
	
	/**
		*Ex:String[] scriptfile = {"../script/window.js"};
		*	設定htm檔中的include 的script檔案路徑與名稱
		@param		size	(String[])htm檔中的include 的script檔案路徑與名稱
		@return 	void
	*/
	public void setScriptFile(String[] scriptFile) {
		this.scriptfile = scriptFile;
	}
	
	
	/**
		*	設定於htm檔案中的style
		@param		s	htm檔案中的style
		@return 	void
	*/
	public void setStyle(String s) {
		this.style = s;	
	}
	
	/**
		*只產生JS檔案，傳回成功:true，失敗false
		@param		vr	含有要產生js的相關開窗所須資料內容。ex:第一欄位存代碼，第二欄位存名稱。
		@return 	成功:true，失敗:false。
	*/
	public boolean generateJSFile(Vector vr) {
		boolean result = false;
		String temp = "";
		temp = path.substring(path.length()-1,path.length());
		System.out.println("temp = " + temp);
		if(path.length()>0 && !temp.equals(File.separator)) 
			temp = path + File.separator ;
		else
			temp = path;
		System.out.println("temp = " + temp);
		if(vr.size()>0) {
			GenerateJSFile file = new GenerateJSFile(temp + "script" + File.separator + jsfilename,arrayname);
			file.setField(field);
			result = file.writeData(vr);
			file = null;
		} else
			result = true;
		temp = null;			
		return result;
	}
	
	/**
		*只產生HTM檔案，傳回成功:true，失敗false
		@param		vr	含有要產生js的相關開窗所須資料內容。ex:第一欄位存代碼，第二欄位存名稱。
		@return 	成功:true，失敗:false。
	*/
	public boolean generateHTMFile(Vector vr) {
		boolean result = false;
		String temp = "";
		temp = path.substring(path.length()-1,path.length());
		if(path.length()>0 && !temp.equals(File.separator)) 
			temp = path + File.separator ;
		else
			temp = path;
		System.out.println("temp = " + temp);			
		if(vr.size()> 0) {
			GenerateHTMFile file = new GenerateHTMFile(temp + "html" + File.separator + htmfilename);
			file.setSelectName(selectname);
			file.setSelectSize(size) ;
			file.setField(field);
			file.setStyle(style);
			result = file.writeData(vr);
			file = null;
		} else 
			result = true;
		temp = null;	
		return result;
	}
	
	/**
		*產生JS與HTM檔案，傳回成功:true，失敗false
		@param		vr	含有要產生js的相關開窗所須資料內容。ex:第一欄位存代碼，第二欄位存名稱。
		@return		成功:true，失敗:false。
	*/
	public boolean generateFile (Vector vr) {
		boolean result = false;
		result = generateJSFile(vr);
		if(result == false) return result;
		else result = generateHTMFile(vr);
		return result;
	}
	
	/**
		*	設定產生檔案的根目錄
		@param		path	產生檔案的根目錄
		@return 	void
	*/
	public void setPath(String path) {
		/*rootpath = path;
		jsfilename = rootpath + File.separator + "script" + File.separator + jsfilename;
		htmfilename = rootpath + File.separator + "html" + File.separator + htmfilename;
		System.out.println("jsfile = " + jsfilename);
		System.out.println("htmfile = " + htmfilename);*/
		this.path = path;
		
	}	
	
	/**
		*ex:		(String)1:value為vr的第一個欄位，option為vr的第二個欄位<BR>
				(String)2:value為vr的第一個欄位與第二個欄位，option為vr的第一個欄位與第二個欄位。
		*產生的HTM中，該下拉選單中的value與option值是不是由2個欄位合成產生。
		@param		field	(int)
		@return 	void
	*/
	public void setField(int field) {
		this.field = field;	
	}
	
	/**
		*ex:		(String)1:value為vr的第一個欄位，option為vr的第二個欄位<BR>
				(String)2:value為vr的第一個欄位與第二個欄位，option為vr的第一個欄位與第二個欄位。
		*產生的HTM中，該下拉選單中的value與option值是不是由2個欄位合成產生。
		@param		field	(String)
		@return 	void
	*/
	public void setField(String field) {
		try {
			System.out.println(field);
			this.field = Integer.parseInt(field);
		} catch(NumberFormatException e) {
			this.field = 2;	
		}	
	}
	
	/**
		*檢核檔案是否存在，若不存在，則先產生一份。
		@param		無
		@return		void
	*/
	public void checkFile(Vector vr) {
		GenerateJSFile js = new GenerateJSFile(path + File.separator + "script" + File.separator + jsfilename,arrayname);
		js.checkFile(vr);
		//System.out.println("check js file end");
		js = null;
		GenerateHTMFile htm = new GenerateHTMFile(path + File.separator + "html" + File.separator + htmfilename);
		htm.checkFile(vr);
		//System.out.println("check htm file end");
		htm = null;
	}
	
	
	protected String jsfilename = "";
	protected String htmfilename = "";
	protected String selectname = "selecname";
	protected int size = 10;
	protected String arrayname = "";
	protected int field = 2;
	protected String rootpath = "";
	protected String[] scriptfile = {"../script/WINDOW.js"};
	protected String path = "";
	protected String style = "";
}
