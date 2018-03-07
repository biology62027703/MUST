package com.sertek.file;

import java.util.*;
import java.io.*;

public class GenerateJSFile {
	/**
		*此建構子會預設相關的預設值
		*用來產生相關代碼所需的js陣列內容檔案
		@author 	Ellin Chen
		@Date		2001.06.22
	*/
	public GenerateJSFile() {
		//GenerateJSFile(path,name,array_name);
		this("c:/test.js","testarray");
	}
	
	/**
		*Hashtable中的key值filename,path,array_name,field
		@drecated	Hashtable存放著JS會用到的相關參數資料
		@param		detail	存放著JS會用到的相關參數資料
	*/
	public GenerateJSFile(Hashtable detail) {
		if(detail.get("filename")!=null) setFileName((String)detail.get("filename"));
		if(detail.get("path")!=null) setPath((String)detail.get("path"));
		if(detail.get("array_name")!=null) setArrayName((String)detail.get("array_name"));
		if(detail.get("field")!=null) setField((String)detail.get("field"));
	}
	
	/**
		*	建構子
		@param		name		檔案路徑(含檔名)
		@param		arrayname	於JS中的陣列名稱
	*/
	public GenerateJSFile(String name,String arrayname) {
		setFileName(name);
		setArrayName(arrayname);
	}
	
	/**
		*	設定檔案路徑(含檔名)
		@param		name	檔案路徑(含檔名)
		@return 	void
	*/
	public void setFileName(String name) {
		this.filename = name;
	}
	
	/**
		*	設定.js檔案中的陣列名稱
		@param		name	.js檔案中的陣列名稱
		@return		void
	*/
	public void setArrayName(String name) {
		this.array_name = name;
	}
	
	/**
		*	設定.js的路徑
		@param		path	路徑
		@return 	void
	*/
	public void setPath(String path) {
		//this.path = path;
		if(path.lastIndexOf(File.separator)==-1) 
			filename = path + File.separator + filename;
		else
			filename = path + filename;
		//System.out.println("filenam = " + filename);
	}
	
	/**
		*此vr中，只有兩個欄位，第一個欄位為值，第二個欄位為顯示出來的內容。
		*	設定於資料庫中找尋出來的資料結果值Vector
		@param		vr	於資料庫中找尋出來的資料結果值Vector，0:屬value值，1:屬option值。
		@return		void
	*/
	public void setData(Vector vr) {
		/*StringBuffer temp = new StringBuffer();
		data.add(array_name + "= new Array(");
		System.out.println("JSFile set Data 1");
		for(int i=0;i<(vr.size()/2);i++) {	
			temp.append("['" + (String)vr.elementAt(i*2) + "','" + (String)vr.elementAt(i*2+1) + "']");
			if(i < ((vr.size()/2)-1)) temp.append(",");
			data.add(temp.toString());
			temp = temp.delete(0,temp.toString().length());
		}
		data.add(");");*/
		setData(vr,field);
	}
	
	/**
		*	設定於資料庫中找尋出來的資料結果值Vector，但有指定該JS陣列中的元素有幾個
		@param		vr	於資料庫中找尋出來的資料結果值Vector
		@param		field	指出在JS陣列中的元素有幾個
		@return 	void
	*/
	public void setData(Vector vr,int field) {
		StringBuffer temp = new StringBuffer();
		data.add(array_name + "= new Array(");
		//System.out.println("JSFile set Data 1");
		System.out.println(field);
		for(int i=0;i<(vr.size()/field);i++) {	
			temp.append("[");
			for(int j=0;j<field;j++) {
				temp.append("'" + (String)vr.elementAt(i*field + j) + "'");// + "','" + (String)vr.elementAt(i*field+1) + "']");
				if((j+1) < field) temp.append(",");
			}
			temp.append("]");
			if(i < ((vr.size()/field)-1)) temp.append(",");
			data.add(temp.toString());
			temp = temp.delete(0,temp.toString().length());
		}
		data.add(");");
	}
	
	/**
		*將setData中指定的vr內容寫入指定檔案.js內。
		@param		無
		@return		回傳寫入成功或失敗，成功true，失敗false。
	*/
	public boolean writeData() {
		boolean result = false;
		System.out.println("filename = " + filename);
		GenerateFile file = new GenerateFile(filename);
		result = file.writeData(data);
		file = null;
		return result;
	}
	
	/**
		*此vr中，只有兩個欄位，第一個欄位為值，第二個欄位為顯示出來的內容。
		*將(Vector)vr的內容寫入指定的檔案.js內
		@param		vr	於資料庫中找尋出來的資料結果值Vector，0:屬value值，1:屬option值。
		@return		回傳寫入成功或失敗，成功true，失敗false。
	*/
	public boolean writeData(Vector vr) {
		setData(vr);
		//System.out.println("JSFile setData");
		return writeData();
	}
	
	/**
		*此vr中，只有兩個欄位，第一個欄位為值，第二個欄位為顯示出來的內容。
		*將(Vector)vr的內容寫入指定的檔案.js內，而會指定在JS檔中，該陣列的元素有幾個，預設有二個
		@param		vr	於資料庫中找尋出來的資料結果值Vector
		@param		field	會指定在JS檔中，該陣列的元素有幾個，預設有二個
		@return		回傳寫入成功或失敗，成功true，失敗false。
	*/
	public boolean writeData(Vector vr,int field) {
		setField(field);
		setData(vr);
		//System.out.println("JSFile setData");
		return writeData();
	}
	
	
	/**
		*檢核檔案是否存在，若不存在，則先產生一份。
		@param		無
		@return		void
	*/
	public void checkFile(Vector vr) {
		GenerateFile file= new GenerateFile(filename);
		if(file.checkFile() == false) {
			file = null;
			this.writeData(vr);	
		} else {
			file = null;	
		}
		
	}
	
	public void setField(int field) {
		this.field = field;
	}
	
	public void setField(String field) {
		int i = 0;
		try {
			i = Integer.parseInt(field);
		} catch(NumberFormatException e) {
			i = 2;	
		}
		this.field = i;
	}
	
	private String array_name = "tempArray";
	private String filename = "";
	private Vector data = new Vector();
	private int field = 2;
	//private String path = "c:";
}