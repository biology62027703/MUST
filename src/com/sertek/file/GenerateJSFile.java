package com.sertek.file;

import java.util.*;
import java.io.*;

public class GenerateJSFile {
	/**
		*���غc�l�|�w�]�������w�]��
		*�ΨӲ��ͬ����N�X�һݪ�js�}�C���e�ɮ�
		@author 	Ellin Chen
		@Date		2001.06.22
	*/
	public GenerateJSFile() {
		//GenerateJSFile(path,name,array_name);
		this("c:/test.js","testarray");
	}
	
	/**
		*Hashtable����key��filename,path,array_name,field
		@drecated	Hashtable�s���JS�|�Ψ쪺�����ѼƸ��
		@param		detail	�s���JS�|�Ψ쪺�����ѼƸ��
	*/
	public GenerateJSFile(Hashtable detail) {
		if(detail.get("filename")!=null) setFileName((String)detail.get("filename"));
		if(detail.get("path")!=null) setPath((String)detail.get("path"));
		if(detail.get("array_name")!=null) setArrayName((String)detail.get("array_name"));
		if(detail.get("field")!=null) setField((String)detail.get("field"));
	}
	
	/**
		*	�غc�l
		@param		name		�ɮ׸��|(�t�ɦW)
		@param		arrayname	��JS�����}�C�W��
	*/
	public GenerateJSFile(String name,String arrayname) {
		setFileName(name);
		setArrayName(arrayname);
	}
	
	/**
		*	�]�w�ɮ׸��|(�t�ɦW)
		@param		name	�ɮ׸��|(�t�ɦW)
		@return 	void
	*/
	public void setFileName(String name) {
		this.filename = name;
	}
	
	/**
		*	�]�w.js�ɮפ����}�C�W��
		@param		name	.js�ɮפ����}�C�W��
		@return		void
	*/
	public void setArrayName(String name) {
		this.array_name = name;
	}
	
	/**
		*	�]�w.js�����|
		@param		path	���|
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
		*��vr���A�u��������A�Ĥ@����쬰�ȡA�ĤG����쬰��ܥX�Ӫ����e�C
		*	�]�w���Ʈw����M�X�Ӫ���Ƶ��G��Vector
		@param		vr	���Ʈw����M�X�Ӫ���Ƶ��G��Vector�A0:��value�ȡA1:��option�ȡC
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
		*	�]�w���Ʈw����M�X�Ӫ���Ƶ��G��Vector�A�������w��JS�}�C�����������X��
		@param		vr	���Ʈw����M�X�Ӫ���Ƶ��G��Vector
		@param		field	���X�bJS�}�C�����������X��
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
		*�NsetData�����w��vr���e�g�J���w�ɮ�.js���C
		@param		�L
		@return		�^�Ǽg�J���\�Υ��ѡA���\true�A����false�C
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
		*��vr���A�u��������A�Ĥ@����쬰�ȡA�ĤG����쬰��ܥX�Ӫ����e�C
		*�N(Vector)vr�����e�g�J���w���ɮ�.js��
		@param		vr	���Ʈw����M�X�Ӫ���Ƶ��G��Vector�A0:��value�ȡA1:��option�ȡC
		@return		�^�Ǽg�J���\�Υ��ѡA���\true�A����false�C
	*/
	public boolean writeData(Vector vr) {
		setData(vr);
		//System.out.println("JSFile setData");
		return writeData();
	}
	
	/**
		*��vr���A�u��������A�Ĥ@����쬰�ȡA�ĤG����쬰��ܥX�Ӫ����e�C
		*�N(Vector)vr�����e�g�J���w���ɮ�.js���A�ӷ|���w�bJS�ɤ��A�Ӱ}�C���������X�ӡA�w�]���G��
		@param		vr	���Ʈw����M�X�Ӫ���Ƶ��G��Vector
		@param		field	�|���w�bJS�ɤ��A�Ӱ}�C���������X�ӡA�w�]���G��
		@return		�^�Ǽg�J���\�Υ��ѡA���\true�A����false�C
	*/
	public boolean writeData(Vector vr,int field) {
		setField(field);
		setData(vr);
		//System.out.println("JSFile setData");
		return writeData();
	}
	
	
	/**
		*�ˮ��ɮ׬O�_�s�b�A�Y���s�b�A�h�����ͤ@���C
		@param		�L
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