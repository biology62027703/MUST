package com.sertek.file;
import java.io.*;
import java.util.*;
import java.text.*;


public class ErrorFile{
	/**
		*將有關執行過程中的錯誤，寫到log檔案中，方便日後維護之需要
		@authoer	Ellin Chen
	*/
	public ErrorFile() {
		this.fname = "c:/error.log";
	}
	
	/**
		*	設定錯誤寫入檔案的建構子
		@param		file	要寫入錯誤的檔案名稱
	*/
	public ErrorFile(String file) {
		this.setFile(file);
	}
	
	/**
		*	設定要寫入錯誤的檔案名稱
		@param		file	要寫入錯誤的檔案名稱
		@return		無
	*/
	public void setFile(String file) {
		this.fname = file;
		//System.out.println("fname = " + fname);
	}
	
	/**
		*取得檔案名稱與路徑
		@return		(String)
	*/
	public String getFile() {
		return this.fname;
	}
	
	/**
		*將錯誤的Vector寫入檔案中
		@param		ob	由哪個物件所傳送過來的錯誤
		@param		error	錯誤的訊息Vector
		@return		傳送寫入成功或失敗,結果<=0:失敗, 結果>0:成功
	*/
	public int writeError(Object ob,Vector error) {
		return write(ob,error,systemOut(ob));	
	}
	
	/**
		*將錯誤的String寫入檔案中
		@param		ob	由哪個物件所傳送過來的錯誤
		@param		error	錯誤的訊息String
		@return		傳送寫入成功或失敗，結果<=0:失敗, 結果>0:成功
	*/
	public int writeError(Object ob,String error) {
		Vector vr = new Vector();
		vr.add(error);
		return write(ob,vr,systemOut(ob));
	}
	
	/**
		*將錯誤的資料寫入檔案中
		@param		ob
		@param		error
		@param		sys
		@return		傳回成功或失敗，結果<=0:失敗, 結果>0:成功
	*/
	private int write(Object ob,Vector error,Vector sys) {
		int result = 0;
		try {
			//System.out.println("file name = " + getFile());
			//File f = new File(getFile());
			//if(!f.exists()) {
				//f.createNewFile();
			//}
			GenerateFile f = new GenerateFile(getFile());
			f.checkFile();
			f = null;
			//RandomAccessFile ranfile = new RandomAccessFile(fname,"rw");
			BufferedWriter w = new BufferedWriter(new FileWriter(fname,true));
			/*if(ranfile.length()==0) {
				ranfile.seek(0);
				//System.out.println("寫檔案的位置=" + 0);	
			} else {
				ranfile.seek(ranfile.length()+1);
				//System.out.println("寫檔案的位置=" + (ranfile.length()+1));
			}
			//System.out.println("ranfile length = " + ranfile.length());
			*/
			//寫入固定標題到檔案
			result = write(w,sys);
			//寫入錯誤訊息到檔案
			//if(error.size()>0) w.newLine();
			error.add(getEnd());
			result = write(w,error);
			//關閉file
			w.close();	
			//f.close();
			result = 1;
		}catch(FileNotFoundException e) {
			//System.out.println("找不到檔案");	
		} catch(IOException e) {
			//System.out.println("寫入檔案失敗");	
		} 
		return result;
	}
	
	/**
		*寫入檔案
		@param		ranfile
		@param		meg	Vector
		@return		傳回成功或失敗的結果，結果<=0:失敗，結果>0:成功。
	*/
	private int write(BufferedWriter ranfile,Vector meg) {
		int result = 0;
		String temp = "";
		try {
			for(int i=0;i<meg.size();i++) {
				temp = (String)meg.elementAt(i);
				ranfile.write(temp,0,temp.length());
				//if(i<(meg.size()-1)) ranfile.newLine();
				ranfile.newLine();
			}
			result = 1;
		} catch(IOException e) {
			
		}
		temp = null;
		return result;
	}
	
	
	public Vector readError(String filename) {
		setFile(filename);
		return readError();
	}
	
	/**
		*讀出所有的錯誤內容
		@param		無
		@return		Vector的錯誤內容
	*/
	private Vector readError() {
		Vector result = new Vector();
		String temp = "";
		try {
			File f = new File(fname);	
			BufferedReader r = new BufferedReader(new FileReader(f));
			while((temp = r.readLine())!=null) {
				//System.out.println("讀出的結果=" + temp);
				result.add(temp);
				//result.add(r.readLine());
				//result = ranfile.readUTF();
			}
			//result.add(getEnd());
			r.close();
			//f.close();
		} catch(FileNotFoundException e) {
			//System.out.println("讀不到檔案");
		} catch(IOException e) {
			//System.out.println(e);	
		}
		return result;	
	}
	/**
		*錯誤的固定傳出系統資料
		@param		無
		@return		系統的資料Vector
	*/
	public Vector systemOut(Object ob) {	
		Vector result = new Vector();
		result.add("System DateTime:" + nowDateTime());
		result.add("Object = " + getClassName(ob));
		return result;
	}
	
	/**
		*取得物件的名稱
		@param		ob	物件
		@return		物件的名稱
	*/
	private String getClassName(Object ob) {
		return ob.getClass().getName();
	}
	private String nowDateTime() {
		Date d = new Date();
		StringBuffer s = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		s = sf.format(d,s,new FieldPosition(0));	
		d = null;
		sf = null;
		return s.toString();
	}
	private String getEnd() {
		StringBuffer result = new StringBuffer();
		for(int i=0;i<100;i++) {
			result.append("=");
		}	
		return result.toString();
	}
	private String fname;
}