package com.sertek.file;

import java.io.*;
import java.util.*;

public class GenerateFile {
	/**
		*有關檔案名稱路徑的分隔符號，請先用/，本程式中會做相關系統的轉換
		*有關資料寫入檔案
		@author		Ellin Chen
		@Date		2001/06/26
	*/
	public GenerateFile() {
		this("c:/temp.txt");
	}
	
	/**
		*	建構子
		@param		name	檔案的路徑(含檔名)。
	*/
	public GenerateFile(String name) {
		setFile(name);
	}
	
	/**
		*	設定檔案的路徑(含檔名)。
		@param		name	檔案的路徑(含檔名)。
		@return		void
		
	*/
	public void setFile(String name) {
		this.path = name;
	}
	
	/**
		*產生檔案。
		@param		無
		@return		傳回產生成功或失敗，成功true，失敗false
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
		*產生相關的路徑目錄
		@param		無
		@return		傳回產生成功或失敗，成功true，失敗false
		
	*/
	public boolean createPath() {
		boolean result = false;
		System.out.println("path = " + path);
		if(path.length()>0) {
			String temp = path.substring(0,(path.lastIndexOf(File.separatorChar)));
			System.out.println("目錄路徑=" + temp);
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
		@deprecate	檢核檔案是否已經存在
		@param		無
		@return		傳回存在與否，存在true，不存在false
	*/
	public boolean exists() {
		boolean result = true;
		path = checkSeparate(path,'/');
		f = new File(path);	
		result = f.exists();
		return result;
	}
	
	/**
		*檢核檔案，不存在則產生相對路徑與檔案
		@param		無
		@return		傳回檢核的結果。有檔案true，無檔案false。
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
		*產生相對作業系統的路徑檔名
		@param		path	檔案的路徑(含檔名)
		@param		FileSeparate	(char)此path中的檔案目錄分隔符號
		@return		傳回相對作業系統的路徑檔名字串
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
		*將Vector的內容寫入檔案中
		@param		data	存有要寫入檔案的相關內容Vector
		@return		回傳寫入成功與否，成功true，失敗false
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
			//System.out.println("寫入檔案失敗");
		}
		//f = null;
		temp = null;
		fileEncode = null;
		return result;
	}

	 /**
     @deprecated	產生相對作業系統的路徑檔名
     @param		path	檔案的路徑(含檔名)
     @param		FileSeparate	(char)此path中的檔案目錄分隔符號
     @return		傳回相對作業系統的路徑檔名字串
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