package com.sertek.file;

import java.util.*;
import java.io.*;

public class GenerateJS_HTMFile {
	/**
		*�ΥH���ͬ����}���һݭn��JS�PHTM�ɮ�
	*/
	public GenerateJS_HTMFile() {
		setPath(rootpath);
		
	}

	/**
		*key�Ȧ�:<BR>
			jsfilename:	JS���ɦW�A���t���|<BR>
			ex:"d:/resin1.2.0/public_htm/N/script/Array_temp.js"<BR>
			arrayname:	JS�ɤ���array�W��<BR>
			htmfilename:	HTM���ɦW�A���t���|<BR>
			ex:"d:/resin1.2.0/public_htm/N/HHD/Temp_Code.htm"
			selectname:	HTM�ɤ����U�Կ��W��<BR>
			selectsize:	HTM�ɤ����U�Կ�檺�j�p<BR>
			path:		�������ɮת��ڸ��|<BR>
			ex:"d:/resin1.2.0/public_htm/N"�C��js�Phtm�����ͩ󦹸��|�U��script�ؿ��Phtml�ؿ��U�C<BR>
			field:		���ͪ�HTM���A�ӤU�Կ�椤��value�Poption�ȬO���O��2�����X�����͡C
			ex:		(String)1:value��vr���Ĥ@�����Aoption��vr���ĤG�����<BR>
					(String)2:value��vr���Ĥ@�����P�ĤG�����Aoption��vr���Ĥ@�����P�ĤG�����C
			
		*	�غc�l
		@param		detail	(Hashtable)�s������JS�PHTM�����ɮת��Ѽ�
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
		*	�]�wjs���ɦW�P���|
		@param		name	js���ɦW�P���|
		@return 	void
	*/
	public void setJSFileName(String name) {
		this.jsfilename = name;
	}
	
	/**
		*	�]�wHTM���ɦW�P���|
		@param		name	htm���ɦW�P���|
		@return		void
	*/
	public void setHTMFileName(String name) {
		this.htmfilename = name;
	}
	
	/**
		*	�]�wjS�ɤ����}�C�W��
		@param		name	jS�ɤ����}�C�W��
		@return 	void
	*/
	public void setArrayName(String name) {
		this.arrayname = name;	
	}
	
	/**
		*	�]�whtm�ɤ����U�Կ��W��
		@param		name	htm�ɤ����U�Կ��W��
		@return 	void
	*/
	public void setSelectName(String name) {
		this.selectname = name;
	}
	
	
	/**
		*	�]�whtm�ɤ����U�Կ��j�p
		@param		size	(String)htm�ɤ����U�Կ��j�p
		@return 	void
	*/
	public void setSelectSize(String size) {
		try {
			setSelectSize(Integer.parseInt(size));
		} catch(NumberFormatException e) {
		
		}
	}
	
	/**
		*	�]�whtm�ɤ����U�Կ��j�p
		@param		size	(int)htm�ɤ����U�Կ��j�p
		@return 	void
	*/
	public void setSelectSize(int size) {
		this.size = size;
	}
	
	/**
		*Ex:String[] scriptfile = {"../script/window.js"};
		*	�]�whtm�ɤ���include ��script�ɮ׸��|�P�W��
		@param		size	(String[])htm�ɤ���include ��script�ɮ׸��|�P�W��
		@return 	void
	*/
	public void setScriptFile(String[] scriptFile) {
		this.scriptfile = scriptFile;
	}
	
	
	/**
		*	�]�w��htm�ɮפ���style
		@param		s	htm�ɮפ���style
		@return 	void
	*/
	public void setStyle(String s) {
		this.style = s;	
	}
	
	/**
		*�u����JS�ɮסA�Ǧ^���\:true�A����false
		@param		vr	�t���n����js�������}���Ҷ���Ƥ��e�Cex:�Ĥ@���s�N�X�A�ĤG���s�W�١C
		@return 	���\:true�A����:false�C
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
		*�u����HTM�ɮסA�Ǧ^���\:true�A����false
		@param		vr	�t���n����js�������}���Ҷ���Ƥ��e�Cex:�Ĥ@���s�N�X�A�ĤG���s�W�١C
		@return 	���\:true�A����:false�C
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
		*����JS�PHTM�ɮסA�Ǧ^���\:true�A����false
		@param		vr	�t���n����js�������}���Ҷ���Ƥ��e�Cex:�Ĥ@���s�N�X�A�ĤG���s�W�١C
		@return		���\:true�A����:false�C
	*/
	public boolean generateFile (Vector vr) {
		boolean result = false;
		result = generateJSFile(vr);
		if(result == false) return result;
		else result = generateHTMFile(vr);
		return result;
	}
	
	/**
		*	�]�w�����ɮת��ڥؿ�
		@param		path	�����ɮת��ڥؿ�
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
		*ex:		(String)1:value��vr���Ĥ@�����Aoption��vr���ĤG�����<BR>
				(String)2:value��vr���Ĥ@�����P�ĤG�����Aoption��vr���Ĥ@�����P�ĤG�����C
		*���ͪ�HTM���A�ӤU�Կ�椤��value�Poption�ȬO���O��2�����X�����͡C
		@param		field	(int)
		@return 	void
	*/
	public void setField(int field) {
		this.field = field;	
	}
	
	/**
		*ex:		(String)1:value��vr���Ĥ@�����Aoption��vr���ĤG�����<BR>
				(String)2:value��vr���Ĥ@�����P�ĤG�����Aoption��vr���Ĥ@�����P�ĤG�����C
		*���ͪ�HTM���A�ӤU�Կ�椤��value�Poption�ȬO���O��2�����X�����͡C
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
		*�ˮ��ɮ׬O�_�s�b�A�Y���s�b�A�h�����ͤ@���C
		@param		�L
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
