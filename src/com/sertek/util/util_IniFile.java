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
 *	Description   : �Ψ�Ū������ window �� System.ini 
 *
 *     
 ***************************************************************************************************** 
 */
package com.sertek.util;

import java.io.*;
import java.util.*;

/**
 *    Description:  �Ψ�Ū������ window �� System.ini
 *      Usage:
 *     �Х� 
 *     try {
 *        util_IniFile IniF = new util_IniFile("c:\\Sys.ini");
 *     
 *     ..load data... 
 *     �N�|�������J 
 *     �� 
 *     util_IniFile _IniF = new util_IniFile();
 *     if( _IniF.loadIniFile("c:\\Sys.ini") ) ..... 
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

public class util_IniFile {

	protected boolean isModified = false;

	protected Vector IniFileV;

	protected String FileName = null; // the Opened File FileName

	protected util_File f_handler = new util_File();

	protected boolean bIsValid = false; // �ΨӰO���}�ɬO�_���\��

	protected long lastmodified = 0;

	/**
	 *
	 util_IniFile  Constructor 
	 �Ѽ�:�L
	 �Ǧ^:�L  
	 ����:�إ� util_IniFile �غc�l
	 *
	 */
	public util_IniFile() {
	}

	/**
	 *
	 util_IniFile  Constructor 
	 �Ѽ�:String sFile:�ǤJ�ɮ׸��|�ɮצW��
	 �Ǧ^:�L  
	 ����:�إ� util_IniFile �غc�l,���J�ɮ�
	 *
	 */
	public util_IniFile(String sFile) throws IOException {
		loadIniFile(sFile);
	}

	/**
	 *
	 util_IniFile  Constructor 
	 �Ѽ�:1.String path:�ǤJ�ɮ׸��|
	 2.String sFile:�ǤJ�ɮצW��
	 �Ǧ^:�L  
	 ����:�إ� util_IniFile �غc�l,���J�ɮ�
	 *
	 */
	public util_IniFile(String path, String sFile) throws IOException {
		loadIniFile(path, sFile);
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:�L  
	 ����:�Ψ��ˬd�}�ɬO�_���\
	 *
	 */
	public boolean isValid() {
		return bIsValid;
	}

	/**
	 *
	 �Ѽ�:String sFile:�ǤJ�ɮ׸��|�ɮצW��
	 �Ǧ^:�L  
	 ����:���J��ini�ɮפ��e
	 *
	 */
	public void loadIniFile(String sFile) throws IOException {
		setFileName(sFile);
		openIni();
		System.out.println(sFile + "����");
	}

	/**
	 *
	 �Ѽ�:1.String path:�ǤJ�ɮ׸��|
	 2.String sFile:�ǤJ�ɮצW��
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
	 �Ѽ�:�L
	 �Ǧ^:�L  
	 ����:�Ψ��ˬd�ɮ׬O�_�ק�L
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
		if (isSectionExist(Section)) // section �w�s�b
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

	/* Ū�i�@�� section
	 * ���ˬd section �s���s�b
	 * Ū�U Entry
	 * ��i IniFileV
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
	/*  �ˬd section sec �O�_�w�s�b�� Ini File Vector
	 *  �����ܦ^��
	 */
	{
		if (IniFileV == null)
			return null;
		for (int i = 0; i < IniFileV.size(); i++) {
			Vector V = getSection(i);
			if (V.elementAt(0).toString().equalsIgnoreCase(sec)) { // �۵�
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
	 �Ѽ�:�L  
	 �Ǧ^:�L  
	 ����:�Ǧ^�Ҧ��s�bsection 
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
	 * �ˬd entry �O�_�s�b�� sec Vector ��
	 * �����ܦ^��
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
	 �Ѽ�:�L
	 �Ǧ^:�L  
	 ����:��Ҧ��b memory ����Ƽg�^ Ini �� 
	 �`�N: �Ҧ��g�J���ʧ@�p�G�S�� flush �h 
	 ���|��� Ini �ɮׯu�������e 
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
	 �Ѽ�:�L
	 �Ǧ^:�L  
	 ����:��Ҧ��b memory ����Ƽg�^ ���w�� Ini �� 
	 *
	 */
	protected void flush(String File) throws IOException {
		flush(File, false);
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:�L  
	 ����:��Ҧ��b memory ����Ƽg�^ ���w�� Ini �� 
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
	 �Ѽ�:1.String sSection:Section �W��
	 2.String sEntry:Entry�W��
	 �Ǧ^:String  
	 ����:�q Ini Ū�����
	 *
	 */
	public String readString(String sSection, String sEntry) {
		return readString(sSection, sEntry, null);
	}

	/**
	 *
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 3.String sDefaultValue : �Y�S�� Section �M Entry �h�Ǧ^ sDefaultValue
	 �Ǧ^:String  
	 ����:�q Ini Ū�����,�Y�S�� Section �M Entry �h�Ǧ^ sDefaultValue ����
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
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 �Ǧ^:String  
	 ����:�YŪ�^���r�ꤤ�t��%, �h�N%�����r������t�@��Entry���W��, �A��INI��
	 Ū�J, �øm�����즳��%...%
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
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 �Ǧ^:int  
	 ����:�q Ini Ū�����
	 *
	 */
	public int readInt(String sSection, String sEntry) {
		return readInt(sSection, sEntry, 0);
	}

	/**
	 *
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 3.String iDefaultValue : DefaultValue����
	 �Ǧ^:int  
	 ����:�q Ini Ū�����,�Y�S�� Section �M Entry �h�Ǧ^ sDefaultValue ����
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
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 �Ǧ^:boolean  
	 ����:�q Ini Ū�����,�Y�S�� Section �M Entry �h�Ǧ^ sDefaultValue ����
	 *
	 */
	public boolean readBoolean(String sSection, String sEntry) {
		return readBoolean(sSection, sEntry, false);
	}

	/**
	 *
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 3.String bDefaultValue : DefaultValue����
	 �Ǧ^:boolean  
	 ����:�q Ini Ū�����,�Y�S�� Section �M Entry �h�Ǧ^ bDefaultValue ����
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
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 3.int iValue : �g�Jini����
	 �Ǧ^:�L  
	 ����:�g�Jint ��ƨ�ini
	 *
	 */
	public void writeInt(String sSection, String sEntry, int iValue) {
		isModified = true;
		writeObject(sSection, sEntry, new Integer(iValue));
	}

	/**
	 *
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 3.String sValue : �g�Jini����
	 �Ǧ^:�L  
	 ����:�g�JString ��ƨ�ini
	 *
	 */
	public void writeString(String sSection, String sEntry, String sValue) {
		isModified = true;
		writeObject(sSection, sEntry, sValue);
	}

	/**
	 *
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 3.Object Obj : �g�Jini����
	 �Ǧ^:�L  
	 ����:�g�JObject ����ƨ�ini
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
	 �Ѽ�:�L
	 �Ǧ^:�L  
	 ����:����Ҧ��귽
	 *
	 */
	public void close() {
		if (f_handler.isReadable())
			f_handler.close();
	}

	/**
	 *
	 �Ѽ�:String DumpFile:�nDump �X�h���ɮצW��
	 �Ǧ^:�L  
	 ����:���ΥΪ�,�i�H��b memory ������Ƽg��
	 �ɮפ�
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
	 �Ѽ�:�L
	 �Ǧ^:String  
	 ����:���o�ثe�ɮת�������|�W��
	 *
	 */
	public String getFullFileName() {
		return FileName;
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:String  
	 ����:�Ǧ^���J���ɮ��ɦW,�ٲ��e�������|
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
	 �Ѽ�:�L
	 �Ǧ^:String  
	 ����:�Ǧ^���J���ɮת����|,�����׽u�M�ϱ׽u�� path separator
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
	 �Ѽ�:1.String sSection : Section �W��
	 2.String sEntry : Entry�W��
	 �Ǧ^:String  
	 ����:readStrings("Exm2","TH");
	 �N�|�� TH �ᱵ�Ʀr�� Ū�X��,�s�b�@�_,�p TH1....
	 ���b ini �حn�Ӷ��ǱƸ�, TH1 , TH2....
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
	 �Ѽ�:String sSection:Section�W��
	 �Ǧ^:Hashtable  
	 ����:Ū��Section ���Ҧ����e,�HHashtableo�Ǧ^
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
	 �Ѽ�:String sSection:Section�W��
	 �Ǧ^:Hashtable  
	 ����:Ū��Section ���Ҧ����e,�HHashtableo�Ǧ^
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
	 �Ѽ�:String sSection:Section�W��
	 �Ǧ^:�L  
	 ����:�M��Section ����
	 *
	 */
	public boolean removeSection(String sec)
	/*  �ˬd section sec �O�_�w�s�b�� Ini File Vector
	 *  �����ܦ^��
	 */
	{
		if (IniFileV == null)
			return false;
		for (int i = 0; i < IniFileV.size(); i++) {
			Vector V = getSection(i);
			if (V.elementAt(0).toString().equalsIgnoreCase(sec)) { // �۵�
				IniFileV.remove(i);
				return true;

			}
		}
		return false;
	}

}

