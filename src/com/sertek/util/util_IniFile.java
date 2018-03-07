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
 *	File Name     : util_IniFile.java    
 *	Author        : Felix Lin              	
 *	Created Date  : 2001/5/20          	     
 *	Modified By   : Felix Lin              	
 *	Last Modified : 2001/5/20
 *	Description   : 用來讀取類似 window 的 System.ini 
 *
 *     
 ***************************************************************************************************** 
 */
package com.sertek.util;

import java.io.*;
import java.util.*;

/**
 *    Description:  用來讀取類似 window 的 System.ini
 *      Usage:
 *     請用 
 *     try {
 *        util_IniFile IniF = new util_IniFile("c:\\Sys.ini");
 *     
 *     ..load data... 
 *     就會直接載入 
 *     或 
 *     util_IniFile _IniF = new util_IniFile();
 *     if( _IniF.loadIniFile("c:\\Sys.ini") ) ..... 
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

public class util_IniFile {

	protected boolean isModified = false;

	protected Vector IniFileV;

	protected String FileName = null; // the Opened File FileName

	protected util_File f_handler = new util_File();

	protected boolean bIsValid = false; // 用來記錄開檔是否成功的

	protected long lastmodified = 0;

	/**
	 *
	 util_IniFile  Constructor 
	 參數:無
	 傳回:無  
	 說明:建立 util_IniFile 建構子
	 *
	 */
	public util_IniFile() {
	}

	/**
	 *
	 util_IniFile  Constructor 
	 參數:String sFile:傳入檔案路徑檔案名稱
	 傳回:無  
	 說明:建立 util_IniFile 建構子,載入檔案
	 *
	 */
	public util_IniFile(String sFile) throws IOException {
		loadIniFile(sFile);
	}

	/**
	 *
	 util_IniFile  Constructor 
	 參數:1.String path:傳入檔案路徑
	 2.String sFile:傳入檔案名稱
	 傳回:無  
	 說明:建立 util_IniFile 建構子,載入檔案
	 *
	 */
	public util_IniFile(String path, String sFile) throws IOException {
		loadIniFile(path, sFile);
	}

	/**
	 *
	 參數:無
	 傳回:無  
	 說明:用來檢查開檔是否成功
	 *
	 */
	public boolean isValid() {
		return bIsValid;
	}

	/**
	 *
	 參數:String sFile:傳入檔案路徑檔案名稱
	 傳回:無  
	 說明:載入的ini檔案內容
	 *
	 */
	public void loadIniFile(String sFile) throws IOException {
		setFileName(sFile);
		openIni();
		System.out.println(sFile + "重載");
	}

	/**
	 *
	 參數:1.String path:傳入檔案路徑
	 2.String sFile:傳入檔案名稱
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
	 參數:無
	 傳回:無  
	 說明:用來檢查檔案是否修改過
	 *
	 */
	public boolean isModified() {
		if (isModified)
			return true;
		File f = new File(FileName);
		long lm = f.lastModified();
		if (lm > lastmodified)
			return true;
		return false;
	}

	protected void openIni() throws IOException {
		close();
		try {

			File f = new File(FileName);
			if (!f.exists()) {
				f_handler.open(FileName, "w");
				f_handler.close();
			}
			
			lastmodified = f.lastModified();
			
			if (f_handler.open(FileName, "r")) {
				IniFileV = new Vector();
				while (readNextSection())
					;
			}
			
			bIsValid = true;
			System.out.print(bIsValid);
		} catch (IOException e) {
			bIsValid = false;
			throw e;
		} finally {
			f_handler.close();
		}
	}
	
	protected void openIni2() throws IOException {
		close();
		try {

			File f = new File(FileName);
			if (!f.exists()) {
				f_handler.open(FileName, "w");
				f_handler.close();
			}
			
			lastmodified = f.lastModified();
			
			if (f_handler.open(FileName, "r")) {
				if(IniFileV==null){
					IniFileV = new Vector();
				}
				//System.out.println("openIni_2");
				while (readNextSection())
					;
			}
			
			bIsValid = true;
			System.out.print(bIsValid);
		} catch (IOException e) {
			bIsValid = false;
			throw e;
		} finally {
			f_handler.close();
		}
	}

	protected boolean isSectionEqual(Vector V, String Section) {
		if (V.size() == 0)
			return false;
		util_IniEntry ent = (util_IniEntry) V.elementAt(0);
		return ent.getValue().equalsIgnoreCase(Section);
	}

	protected boolean isSectionExist(String Section) {
		for (int i = 0; i < IniFileV.size(); i++) {
			Vector V = getSection(i);
			if (isSectionEqual(V, Section)) {
				return true;
			}
		}
		return false;
	}

	protected Vector createSection(String Section) {
		if (isSectionExist(Section)) // section 已存在
			return FindSection(Section);
		Vector V = new java.util.Vector();
		util_IniEntry section = new util_IniEntry("SECTION", Section);
		V.addElement(section);
		IniFileV.addElement(V);
		return V;

	}

	protected boolean isEntryExist(String Entry, Vector Section) {
		// skip first entry for the 'SECTION Entry'
		for (int i = 1; i < Section.size(); i++) {
			util_IniEntry E;
			E = (util_IniEntry) Section.elementAt(i);
			if (E.getEntry().equalsIgnoreCase(Entry)) {
				return true;
			}
		}
		return false;
	}

	protected String EntryGetEntry(String eString) {
		int posfix = eString.indexOf('=');
		return eString.substring(0, posfix).trim().toUpperCase();
	}

	protected String EntryGetValue(String eString) {
		int posfix = eString.indexOf('=');
		return eString.substring(posfix + 1, eString.length());
	}

	protected void addEntry(String Entry, Vector Section) {
		String eEntry;
		String eValue;
		if (Entry.indexOf('=') == -1)
			return;
		eEntry = EntryGetEntry(Entry);
		eValue = EntryGetValue(Entry);
		if (isEntryExist(eEntry, Section))
			return;
		Section.addElement(new util_IniEntry(eEntry, eValue));
	}

	/* 讀進一個 section
	 * 先檢查 section 存不存在
	 * 讀各 Entry
	 * 放進 IniFileV
	 */
	protected String readSectionEntries(Vector sec) {
		String Entry = null;
		try {
			Entry = readContinueLine(f_handler);
			while (Entry != null) {
				if (Entry.length() > 0) {
					if (Entry.charAt(0) == '[') {
						if (Entry.indexOf(']') != -1) {
							// find next section
							return Entry;
						}
					}
				}
				// add the entry
				addEntry(Entry, sec);
				Entry = readContinueLine(f_handler);
			}
		} catch (Exception e) {
			//        sys_cfg.log("readSectionEntries:",e);
		}
		return null;
	}

	protected String readContinueLine(util_File f) throws Exception {
		String line = f.readline();
		if (line == null)
			return line;
		if (line.equals(""))
			return "";
		if (line.charAt(line.length() - 1) == '\\') {
			return line.substring(0, line.length() - 1) + readContinueLine(f);
		} else
			return line;
	}

	protected boolean readNextSection() {
		String Line;
		try {
			Line = f_handler.readline();
			if (Line == null)
				return false;
			while (Line != null) {
				int prefix;
				int posfix;

				if (Line.length() > 0) {
					if (Line.charAt(0) == ';') {
						Line = f_handler.readline();
						continue;
					}

					if (Line.charAt(0) == '[') {
						if (Line.indexOf(']') != -1) {
							prefix = 0;
							posfix = Line.indexOf(']');
							if ((posfix - prefix) > 0) {
								String Section;
								Vector V;
								Section = Line.substring(prefix + 1, posfix).trim();
								V = createSection(Section);
								Line = readSectionEntries(V);
							} else
								Line = f_handler.readline();
						} else
							Line = f_handler.readline();
					} else
						Line = f_handler.readline();
				} else
					Line = f_handler.readline();

				if (Line == null)
					return false;
			}
			return true;
		} catch (Exception e) {
			//      sys_cfg.log("readNextSection:",e);
			return false;
		}
	}

	protected Vector FindSection(String sec)
	/*  檢查 section sec 是否已存在於 Ini File Vector
	 *  有的話回傳
	 */
	{
		if (IniFileV == null)
			return null;
		for (int i = 0; i < IniFileV.size(); i++) {
			Vector V = getSection(i);
			if (V.elementAt(0).toString().equalsIgnoreCase(sec)) { // 相等
				return V;
			}
		}
		return null;
	}

	protected Vector getSection(int idx) {
		if (idx >= IniFileV.size())
			return null;
		return (Vector) (IniFileV.elementAt(idx));
	}

	/**
	 *
	 參數:無  
	 傳回:無  
	 說明:傳回所有存在section 
	 *
	 */
	public Vector getAllSection() {
		Vector v_sec = new Vector();
		if (IniFileV == null)
			return v_sec;
		for (int i = 0; i < IniFileV.size(); i++) {
			Vector V = getSection(i);
			v_sec.addElement(V.elementAt(0).toString());
		}
		return v_sec;
	}

	/*
	 * 檢查 entry 是否存在於 sec Vector 中
	 * 有的話回傳
	 */
	protected util_IniEntry findEntry(Vector V, String eString) {
		for (int i = 1; i < V.size(); i++) {
			util_IniEntry Entry;
			Entry = (util_IniEntry) V.elementAt(i);
			if (Entry.getEntry().equalsIgnoreCase(eString)) {
				return Entry;
			}
		}
		return null;
	}

	/*
	 *  get a entry from Section and Field
	 */
	protected util_IniEntry getEntry(String sSection, String sEntry) {
		Vector V;
		if ((V = FindSection(sSection)) != null) {
			util_IniEntry Entry;
			if ((Entry = findEntry(V, sEntry)) != null) {
				return Entry;
			}
		}
		return null;
	}

	/**
	 *
	 參數:無
	 傳回:無  
	 說明:把所有在 memory 的資料寫回 Ini 檔 
	 注意: 所有寫入的動作如果沒有 flush 則 
	 不會更改 Ini 檔案真正的內容 
	 *
	 */
	public void flush() throws IOException {
		if (FileName == null)
			throw new IOException("file not defined");
		flush(FileName,false);
	}
	
	public void flush(boolean append) throws IOException {
		if (FileName == null)
			throw new IOException("file not defined");
		flush(FileName, append);
	}

	/**
	 *
	 參數:無
	 傳回:無  
	 說明:把所有在 memory 的資料寫回 指定的 Ini 檔 
	 *
	 */
	protected void flush(String File) throws IOException {
		flush(File, false);
	}

	/**
	 *
	 參數:無
	 傳回:無  
	 說明:把所有在 memory 的資料寫回 指定的 Ini 檔 
	 *
	 */
	protected void flush(String File, boolean append) throws IOException {
		//System.out.println("flush_1");
		/*
		if(append){
			f_handler.open(File, "wa");
			//System.out.println("flush_2");
		}else{
			f_handler.open(File, "w");
			//System.out.println("flush_3");
		}
		*/
		f_handler.open(File, "w");
		
		//System.out.println("flush_4");
		
		for (int i = 0; i < IniFileV.size(); i++) {
			Vector V;
			V = (Vector) IniFileV.elementAt(i);
			f_handler.writeln("[" + V.elementAt(0).toString() + "]");
			for (int j = 1; j < V.size(); j++) {
				util_IniEntry Entry = (util_IniEntry) V.elementAt(j);
				f_handler.writeln(Entry.getEntry() + "=" + Entry.getValue());
			}
			f_handler.writeln();
		}
		f_handler.close();
	}

	
	/**
	 *
	 參數:1.String sSection:Section 名稱
	 2.String sEntry:Entry名稱
	 傳回:String  
	 說明:從 Ini 讀取資料
	 *
	 */
	public String readString(String sSection, String sEntry) {
		return readString(sSection, sEntry, null);
	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 3.String sDefaultValue : 若沒有 Section 和 Entry 則傳回 sDefaultValue
	 傳回:String  
	 說明:從 Ini 讀取資料,若沒有 Section 和 Entry 則傳回 sDefaultValue 的值
	 *
	 */
	public String readString(String sSection, String sEntry, String sDefaultValue) {
		util_IniEntry Entry;
		if ((Entry = getEntry(sSection, sEntry)) != null)
			return Entry.getValue();
		return sDefaultValue;
	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 傳回:String  
	 說明:若讀回的字串中含有%, 則將%間之字串視為另一個Entry之名稱, 再由INI檔
	 讀入, 並置換掉原有的%...%
	 *
	 */
	public String readStringMacro(String sSection, String sEntry) {
		util_IniEntry Entry;
		String _sValue = null;

		if ((Entry = getEntry(sSection, sEntry)) != null) {
			_sValue = Entry.getValue();
			String _sMacro;
			int _iPos1, _iPos2;

			_iPos1 = _sValue.indexOf("%");
			_iPos2 = _sValue.lastIndexOf("%");
			if (_iPos1 > 0 && _iPos2 > 0 && _iPos1 != _iPos2) {
				_sMacro = _sValue.substring(_iPos1 + 1, _iPos2);
				Entry = getEntry(sSection, _sMacro);
				_sMacro = Entry.getValue();
				if (_sMacro != null) {
					_sValue = _sValue.substring(0, _iPos1) + _sMacro
						+ _sValue.substring(_iPos2 + 1, _sValue.length());
				}
			}
		}
		return _sValue;
	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 傳回:int  
	 說明:從 Ini 讀取資料
	 *
	 */
	public int readInt(String sSection, String sEntry) {
		return readInt(sSection, sEntry, 0);
	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 3.String iDefaultValue : DefaultValue的值
	 傳回:int  
	 說明:從 Ini 讀取資料,若沒有 Section 和 Entry 則傳回 sDefaultValue 的值
	 *
	 */
	public int readInt(String sSection, String sEntry, int iDefaultValue) {
		util_IniEntry Entry;
		if ((Entry = getEntry(sSection, sEntry)) != null)
			try {
				return Integer.parseInt(Entry.getValue());
			} catch (Exception ignore) {
			}
		return iDefaultValue;

	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 傳回:boolean  
	 說明:從 Ini 讀取資料,若沒有 Section 和 Entry 則傳回 sDefaultValue 的值
	 *
	 */
	public boolean readBoolean(String sSection, String sEntry) {
		return readBoolean(sSection, sEntry, false);
	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 3.String bDefaultValue : DefaultValue的值
	 傳回:boolean  
	 說明:從 Ini 讀取資料,若沒有 Section 和 Entry 則傳回 bDefaultValue 的值
	 *
	 */
	public boolean readBoolean(String sSection, String sEntry, boolean bDefaultValue) {
		util_IniEntry Entry;
		if ((Entry = getEntry(sSection, sEntry)) != null) {
			if (Entry.getValue().equalsIgnoreCase("true"))
				return true;
			return false;
		}
		return bDefaultValue;
	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 3.int iValue : 寫入ini的值
	 傳回:無  
	 說明:寫入int 資料到ini
	 *
	 */
	public void writeInt(String sSection, String sEntry, int iValue) {
		isModified = true;
		writeObject(sSection, sEntry, new Integer(iValue));
	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 3.String sValue : 寫入ini的值
	 傳回:無  
	 說明:寫入String 資料到ini
	 *
	 */
	public void writeString(String sSection, String sEntry, String sValue) {
		isModified = true;
		writeObject(sSection, sEntry, sValue);
	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 3.Object Obj : 寫入ini的值
	 傳回:無  
	 說明:寫入Object 的資料到ini
	 *
	 */
	protected void writeObject(String sSection, String sEntry, Object Obj) {
		Vector V;
		util_IniEntry Entry;
		String sString;

		if (IniFileV == null)
			return;

		if ((sSection == null) || sSection.equals("")) {
			return;
		}
		sSection = sSection.trim();
		if ((sEntry == null) || sEntry.equals("")) {
			return;
		}
		sEntry = sEntry.trim();
		if (Obj == null)
			sString = "";
		else
			sString = Obj.toString();

		if ((V = FindSection(sSection)) != null) {
			if ((Entry = findEntry(V, sEntry)) != null) {
				Entry.setValue(sString);
				return;
			}
			// find section , but no entry , add entry
			Entry = new util_IniEntry(sEntry, sString);
			V.addElement(Entry);
		} else {
			// no section , no entry , add all...
			V = new Vector();
			Entry = new util_IniEntry("SECTION", sSection);
			V.addElement(Entry);
			Entry = new util_IniEntry(sEntry, sString);
			V.addElement(Entry);
			IniFileV.addElement(V);
		}
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

	/**
	 *
	 參數:String DumpFile:要Dump 出去的檔案名稱
	 傳回:無  
	 說明:除蟲用的,可以把在 memory 中的資料寫到
	 檔案中
	 *
	 */
	public void dump(String DumpFile) throws IOException {
		flush(DumpFile);
	}

	/*
	 *  set the "FileName"
	 */
	public void setFileName(String File) {
		FileName = File;
	}

	/**
	 *
	 參數:無
	 傳回:String  
	 說明:取得目前檔案的完整路徑名稱
	 *
	 */
	public String getFullFileName() {
		return FileName;
	}

	/**
	 *
	 參數:無
	 傳回:String  
	 說明:傳回載入的檔案檔名,省略前面的路徑
	 *
	 */
	public String getFileName() {
		int idx;
		if (FileName == null)
			return null;
		if ((idx = FileName.lastIndexOf('\\')) != -1)
			return FileName.substring(idx + 1, FileName.length());
		if ((idx = FileName.lastIndexOf('/')) != -1)
			return FileName.substring(idx + 1, FileName.length());
		if ((idx = FileName.lastIndexOf(':')) != -1)
			return FileName.substring(idx + 1, FileName.length());
		return FileName;
	}

	/**
	 *
	 參數:無
	 傳回:String  
	 說明:傳回載入的檔案的路徑,接受斜線和反斜線的 path separator
	 *
	 */
	public String extractFilePath() {
		int idx;
		if (FileName == null)
			return null;
		if ((idx = FileName.lastIndexOf('\\')) != -1) {
			if (idx == 0)
				return "\\";
			return FileName.substring(0, idx);
		}
		if ((idx = FileName.lastIndexOf('/')) != -1) {
			if (idx == 0)
				return "/";
			return FileName.substring(0, idx);
		}
		if ((idx = FileName.lastIndexOf(':')) != -1)
			return FileName.substring(0, idx + 1);

		return ".";
	}

	/**
	 *
	 參數:1.String sSection : Section 名稱
	 2.String sEntry : Entry名稱
	 傳回:String  
	 說明:readStrings("Exm2","TH");
	 將會把 TH 後接數字的 讀出來,連在一起,如 TH1....
	 但在 ini 裏要照順序排號, TH1 , TH2....
	 *
	 */
	public String readStrings(String sSection, String sEntry) {
		Vector V = FindSection(sSection);
		if (V == null)
			return "";
		sEntry = sEntry.toUpperCase();
		StringBuffer buf = new StringBuffer();
		for (int i = 1; i < V.size(); i++) {
			util_IniEntry ent = (util_IniEntry) V.elementAt(i);
			String entry = ent.getEntry().toUpperCase();
			int idx = -1;
			if ((idx = entry.indexOf(sEntry)) != -1) {
				idx = idx + sEntry.length();
				if (idx < entry.length()) {
					char c = entry.charAt(idx);
					if ((c <= '9') && (c >= '0')) {
						buf.append(ent.getValue());
					}
				}
			}
		}
		return buf.toString();
	}

	/**
	 *
	 參數:String sSection:Section名稱
	 傳回:Hashtable  
	 說明:讀取Section 內所有內容,以Hashtableo傳回
	 *
	 */
	public Hashtable readSection(String sSection) {
		Vector V = FindSection(sSection);
		if (V == null)
			return null;
		Hashtable table = new Hashtable();
		for (int i = 1; i < V.size(); i++) {
			util_IniEntry E;
			E = (util_IniEntry) V.elementAt(i);
			table.put(E.getEntry(), E.getValue());
		}
		return table;
	}

	/**
	 *
	 參數:String sSection:Section名稱
	 傳回:Hashtable  
	 說明:讀取Section 內所有內容,以Hashtableo傳回
	 *
	 */
	public void writeSection(String sSection, Hashtable table) {
		for (Enumeration enumeration = table.keys(); enumeration.hasMoreElements();) {
			String s1 = ((String) enumeration.nextElement()).trim();
			writeString(sSection, s1, (String) table.get(s1));
		}
	}

	/**
	 *
	 參數:String sSection:Section名稱
	 傳回:無  
	 說明:清除Section 內所
	 *
	 */
	public boolean removeSection(String sec)
	/*  檢查 section sec 是否已存在於 Ini File Vector
	 *  有的話回傳
	 */
	{
		if (IniFileV == null)
			return false;
		for (int i = 0; i < IniFileV.size(); i++) {
			Vector V = getSection(i);
			if (V.elementAt(0).toString().equalsIgnoreCase(sec)) { // 相等
				IniFileV.remove(i);
				return true;

			}
		}
		return false;
	}

}

