package com.sertek.file;

import java.io.*;
import java.util.*;

public class GenerateFile {
	/**
		*�����ɮצW�ٸ��|�����j�Ÿ��A�Х���/�A���{�����|�������t�Ϊ��ഫ
		*������Ƽg�J�ɮ�
		@author		Ellin Chen
		@Date		2001/06/26
	*/
	public GenerateFile() {
		this("c:/temp.txt");
	}
	
	/**
		*	�غc�l
		@param		name	�ɮת����|(�t�ɦW)�C
	*/
	public GenerateFile(String name) {
		setFile(name);
	}
	
	/**
		*	�]�w�ɮת����|(�t�ɦW)�C
		@param		name	�ɮת����|(�t�ɦW)�C
		@return		void
		
	*/
	public void setFile(String name) {
		this.path = name;
	}
	
	/**
		*�����ɮסC
		@param		�L
		@return		�Ǧ^���ͦ��\�Υ��ѡA���\true�A����false
	*/
	public boolean createFile() {
		boolean result = true;
		try {
			result = f.createNewFile();
		} catch(IOException e) {
			//System.out.println("createNewFile error " + e);
		}
		return result;
	}
	
	/**
		*���ͬ��������|�ؿ�
		@param		�L
		@return		�Ǧ^���ͦ��\�Υ��ѡA���\true�A����false
		
	*/
	public boolean createPath() {
		boolean result = false;
		System.out.println("path = " + path);
		if(path.length()>0) {
			String temp = path.substring(0,(path.lastIndexOf(File.separatorChar)));
			System.out.println("�ؿ����|=" + temp);
			File ff = new File(temp);
			try {
				result = ff.mkdirs();
				result = true;
			} catch(SecurityException e) {
				result = false;	
			}
			temp = null;
			ff = null;
		}
		return result;	
	}
	
	/**
		@deprecate	�ˮ��ɮ׬O�_�w�g�s�b
		@param		�L
		@return		�Ǧ^�s�b�P�_�A�s�btrue�A���s�bfalse
	*/
	public boolean exists() {
		boolean result = true;
		path = checkSeparate(path,'/');
		f = new File(path);	
		result = f.exists();
		return result;
	}
	
	/**
		*�ˮ��ɮסA���s�b�h���ͬ۹���|�P�ɮ�
		@param		�L
		@return		�Ǧ^�ˮ֪����G�C���ɮ�true�A�L�ɮ�false�C
	*/
	public boolean checkFile() {
		boolean result = false;
		//System.out.println("GenerateFile checkFile path = " + path);
		if(path.length() == 0) return result;
		if(exists()==false) {
			System.out.println("exists = " + exists());
			if(createPath() == false) {
				System.out.println("MkDirs error");
				result = false;
			} else {
				System.out.println("MKDirs success");
				result = true;
			}
			//if(result == true ) {
				if(createFile() == false) {
					System.out.println("CreateFile error");
					result = false;
				} else {
					System.out.println("MKFile sucess");
					result = true;
				}
			//}
		} else {
			//System.out.println("file exists");
			result = true;
		}	
		return result;
	}
	
	/**
		*���ͬ۹�@�~�t�Ϊ����|�ɦW
		@param		path	�ɮת����|(�t�ɦW)
		@param		FileSeparate	(char)��path�����ɮץؿ����j�Ÿ�
		@return		�Ǧ^�۹�@�~�t�Ϊ����|�ɦW�r��
	*/
	public String checkSeparate(String path,char FileSeparate) {
		/*StringTokenizer st = new StringTokenizer(path,new Character(FileSeparate).toString());
		StringBuffer sb = new StringBuffer();
		String result = "";
		System.out.println("path 11=" + path);
		while(st.hasMoreTokens()) {
			sb.append(st.nextToken() + File.separator);
		}
		result = sb.toString();
		result.replace(':',File.pathSeparatorChar);
		
		st = null;
		sb = null;
		//result = result.substring(0,(result.length()-1));
		System.out.println("result12 = " + result);*/
		
		return checkPath(path);
	}
	
	/**
		*�NVector�����e�g�J�ɮפ�
		@param		data	�s���n�g�J�ɮת��������eVector
		@return		�^�Ǽg�J���\�P�_�A���\true�A����false
	*/
	public boolean writeData(Vector data) {
		boolean result = false;
		String temp = "";
		String fileEncode = System.getProperty("file.encoding");
		System.out.println("fileEncode = " + fileEncode);
		if(data.size()<=0) return result;
		try {
			if(checkFile() == false) {
				System.out.println("GenerateFile checkFile = false");
				return result;
			}
			System.out.println("GenerateFile file name = " + path);
			FileOutputStream fw = new FileOutputStream(path,false);
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(fw,fileEncode));
			for(int i=0;i<data.size();i++) {
				temp = (String)data.elementAt(i);
				//w.write(new String(temp.getBytes(),"MS950"),0,temp.length());	
				w.write(temp,0,temp.length());	
				w.newLine();
			}
			result = true;
			w.close();
			fw.close();
			w = null;
			fw = null;
			
		} catch(IOException e) {
			//System.out.println("�g�J�ɮץ���");
		}
		//f = null;
		temp = null;
		fileEncode = null;
		return result;
	}

	 /**
     @deprecated	���ͬ۹�@�~�t�Ϊ����|�ɦW
     @param		path	�ɮת����|(�t�ɦW)
     @param		FileSeparate	(char)��path�����ɮץؿ����j�Ÿ�
     @return		�Ǧ^�۹�@�~�t�Ϊ����|�ɦW�r��
  */
     public String checkPath(String path) {
	String Tk="/";
	if (File.separator.equals("/"))
	   Tk="\\";       
	StringTokenizer st = new StringTokenizer(path,Tk);
	StringBuffer sb = new StringBuffer();
	String result = "";
	while(st.hasMoreTokens()) {
		sb.append(st.nextToken() + File.separator);
	}
	result = sb.toString();
	result.replace(':',File.pathSeparatorChar);
	
	st = null;
	sb = null;    
        while(true)
        {
           int a=result.lastIndexOf(File.separator);
           if(a==((result.length()-1)))
               result=result.substring(0,a);
           else 
               break;    
        }    
	return result;
	
     }
	
	private File f;
	private String path = "";
	
}