package com.sertek.file;

import java.util.*;

public class GenerateHTMFile {
	/**
		*此主要在產生相關的開窗中所需要的list相關代碼的htm檔案。
		*	建構子，預設路徑檔名c:\test\test.htm
		@author 	Ellin Chen
		@Data		2001/06/26
	*/
	public GenerateHTMFile() {
		this("c:\test\test.htm");	
	}
	
	/**
		*	建構子，由使用者自行定議相關的路徑檔名
		@param		name	指定的路徑檔名
	*/
	public GenerateHTMFile(String name) {
		setFile(name);
		//System.out.println("html file path = " + name);
	}
	
	/**
		*由使用者自行定議相關的路徑檔名
		@param		name	指定的路徑檔名
		@return 	void
	*/
	public void setFile(String name) {
		this.filename = name;
	}
	
	/**
		*	設定List的大小
		@param		size	(int)List的大小
		@return		void
	*/
	public void setSelectSize(int size) {
		this.size = size;
	}
	
	/**
		*	設定於htm檔案中的form名稱
		@param		name	htm檔案中的form名稱
		@return		void
	*/
	public void setFormName(String name) {
		this.formname = name;
	}
	
	/**
		*	設定於htm檔案中的list名稱
		@param		name	htm檔案中的list名稱
		@return 	void
	*/
	public void setSelectName(String name) {
		this.selectname = name;	
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
		*	取出於htm檔案中的style
		@param		
		@return 	String
	*/
	public String setStyle() {
		return style;
	}
	
	/**
		*此vr中，只有兩個欄位，第一個欄位為值，第二個欄位為顯示出來的內容。
		*	設定於資料庫中找尋出來的資料結果值Vector
		@param		vr	於資料庫中找尋出來的資料結果值Vector，0:屬value值，1:屬option值。
		@return		void
	*/
	public void setData(Vector vr) {
		String temp = "";
		if(style.length()>0) {
			temp = "style='" + style + "'";	
		} else {
			temp = "style='width:100%;font-size:13pt'";	
		}
		data.add("<html>");
		data.add("<head>");
		data.add("<meta http-equiv='expires' content='0; charset=MS950'>");
		data.add("</head>");
		data.addAll(setScript());
		data.add("<body>");
		data.add("<form name='" + formname + "'>");
		data.add("<select name='" + selectname + "' size='" + size + "' onClick='DialogBack(" + formname + "." + selectname + ")' " + temp + ">");
		data.addAll(setSelect(vr));
		data.add("</select>");
		data.add("</form>");
		data.add("</body>");
		data.add("</html>");
	}
	
	/**
		*	設定預設的scipt檔案路徑
		@param		無
		@return 	Vector，以便於setData中設定相關的寫入檔案內容。
		
	*/
	public Vector setScript() {	
		Vector result = new Vector();
		for(int i=0;i<scriptfile.length;i++) {
			result.add(setScript(scriptfile[i]));
		}
		return result;
	}	
	
	/**
		*組合相關的script檔案字串
		@param		scriptfile	scipt的檔案路徑與檔名
		@return		組合後相關的script檔案字串
	*/
	public String setScript(String scriptfile) {
		return ("<script language='javascript' src='" + scriptfile + "'>" + "</script>");
	}
	
	/**
		*如: String[] scriptfile = {"../script/String.js","../script/Form.js","../script/utility.js"};
		*	設定此HTM中所用到的script檔案內容
		@param		scriptfile	(String[])存有相關的script的路徑檔名
		@return		void
	*/
	public void setScriptfile(String[] scriptfile) {
		this.scriptfile = scriptfile;
	}
	
	/**
		*此vr中，只有兩個欄位，第一個欄位為值，第二個欄位為顯示出來的內容。
		*	設定List的相關內容，以便寫入檔案內
		@param		vr	於資料庫中找尋出來的資料結果值Vector，0:屬value值，1:屬option值。	
		@return		Vector存有設定List的相關內容
	*/
	public Vector setSelect(Vector vr) {
		Vector result = new Vector();
		for(int i=0;i<(vr.size()/2);i++) {
			result.add(setSelect((String)vr.elementAt(i*2),(String)vr.elementAt(i*2+1)));	
		}
		return result;
	}
	
	/**
		*組合相關的option字串
		@param		value	於option中的value值
		@param		option	於option中的顯示值
		@return		組合後相關的option字串
	*/
	public String setSelect(String value,String option) {
		if(field == 1) 
			return ("<option value='" + value + "'>" + option + "</option>");
		else 
			return ("<option value='" + value + " " + option + "'>" + value + " " + option + "</option>");
	}
	
	
	
	/**
		*此vr中，只有兩個欄位，第一個欄位為值，第二個欄位為顯示出來的內容。
		*寫入指定內容到指定檔案路徑的檔案
		@param		vr	於資料庫中找尋出來的資料結果值Vector，0:屬value值，1:屬option值。
		@return		回傳寫入成功或失敗，成功true，失敗false。
	*/
	public boolean writeData(Vector vr) {
		setData(vr);
		return writeData();	
	}
	
	/**
		*此vr中，只有兩個欄位，第一個欄位為值，第二個欄位為顯示出來的內容。
		*寫入指定內容到指定檔案路徑的檔案
		@param		vr	於資料庫中找尋出來的資料結果值Vector，0:屬value值，1:屬option值。
		@param		field	1:只顯示一個欄位，2:顯示id值與內容值
		@return		回傳寫入成功或失敗，成功true，失敗false。
	*/
	public boolean writeData(Vector vr,int field) {
		setData(vr);
		setField(field);
		return writeData();	
	}
	
	
	/**
		*	設定在下拉選單中該顯示值是否要包含value值, 1，無，只有顯示內容。2:有包含id值。
		@param		field	1:只顯示一個欄位，2:顯示id值與內容值
		@return 	void
	*/
	public void setField (int field) {
		this.field = field;
	}
	
	
	/**
		*此vr中，只有兩個欄位，第一個欄位為值，第二個欄位為顯示出來的內容。
		*將setData中指定內容到指定檔案路徑的檔案
		@param		無
		@return		回傳寫入成功或失敗，成功true，失敗false。
	*/
	public boolean writeData() {
		boolean result = false;
		GenerateFile file = new GenerateFile(filename);
		//System.out.println("HTM file path = " + filename);
		result = file.writeData(data);
		file = null;
		return result;	
	}
	
	
	/**
		*檢核檔案是否存在，若不存在，則先產生一份。
		@param		無
		@return		void
	*/
	public void checkFile(Vector vr) {
		GenerateFile file= new GenerateFile(filename);
		//System.out.println("htm file name=" + filename);
		if(file.exists() == false && file.checkFile() == true) {
			System.out.println("checkFile false");
			file = null;
			//setData(vr);
			this.writeData(vr);	
			//this.writeData();
		} else {
			file = null;	
		}
		
	}
	
	private String filename = "";
	private Vector data = new Vector();
	//private Vector script = new Vector();
	private int size = 10;
	private String formname = "form1";
	private String selectname = "selectname";
	private String[] scriptfile = {"../script/WINDOW.js"};
	private int field = 2;
	private String style = "";
}