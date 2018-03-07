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
 *	File Name     : util_File.java    
 *	Author        : Felix Lin              	
 *	Created Date  : 2001/5/20          	     
 *	Modified By   : Felix Lin              	
 *	Last Modified : 2001/5/20
 *	Description   : 讀寫檔案用的公用 class
 *
 *     
 ***************************************************************************************************** 
 */
package com.sertek.util;

import java.io.*;

/**
 *  Description:
 *    這個 class 可以讓你同時開兩個檔
 *    一個檔讀,一個檔寫
 *    <FONT COLOR=RED>但不可以同時對同一個檔做 [讀/寫]</FONT>
 *    write , writeln 等是對 open_for_write
 *    的檔做動作
 *    Example:
 *    <H5>
 *       util_File _F = new util_File()
 *       try { 
 *         _F.open("tmp","w");
 *         if(_F.IsWriteable())
 *         {
 *             _F.writeln("test 中文 test");
 *             _F.writeln();
 *         }
 *       } catch(Exception e){
 *            showerror(e);
 *       } finally {
 *           _F.close();
 *       }
 *    </H5>
 *
 *    read , readline 等是對 open_for_read
 *    的檔做動作
 *
 */
public class util_File {
	// the opened file file encode
	private static String fileEncode = "MS950";

	// the opened file file handler
	private File f = null;

	// read file handler
	private LineNumberReader reader = null;

	// write file handler
	private BufferedWriter writer = null;

	//private String LineSeparator = System.getProperty("line.separator") != null ? System.getProperty("line.separator") : "\r\n" ;
	private String LineSeparator = "\r\n";

	// notice : 不可以抓系統的 file separator.. 會是 "\"
	private String FileSeperator = "/";

	/**
	 *  used for open_flag in open() 
	 *    open("C:\test",OPEN_FOR_READ);
	 */
	private final int OPEN_FOR_READ = 1; // will failure if file no exist
	/**
	 *  used for open_flag in open() 
	 *    open("C:\test",OPEN_FOR_WRITE);
	 */
	private final int OPEN_FOR_WRITE = 2;
	/**
	 *  used for open_flag in open() 
	 *    open("C:\test",OPEN_FOR_WRITE | OPEN_FOR_APPEND);
	 */
	private final int OPEN_FOR_APPEND = 4;
	/**
	 *  used for open_flag in open() 
	 *    open("C:\test",OPEN_FOR_WRITE|OPEN_FOR_OVERWRITE);
	 */
	private final int OPEN_FOR_OVERWRITE = 8; // create a new one

	private boolean pbIsWriteValid = false;
	private boolean pbIsReadValid = false;
	private String currentFileName = null;

	/**
	 *
	 util_File  Constructor 
	 參數:無
	 傳回:無  
	 說明:建立 util_File 建構子
	 *
	 */
	public util_File() {
	}

	/**
	 *
	 util_File  Constructor 
	 參數:1.String filename:傳入檔案路徑檔案名稱
	 2.String open_mode:開檔模式
	 傳回:無  
	 說明:建立 util_File 建構子,載入檔案
	 *
	 */
	public util_File(String filename, String open_mode) throws IOException {
		open(filename, open_mode);
	}

	/**
	 *
	 util_File  Constructor 
	 參數:1.String path :傳入檔案路徑名稱
	 2.String filename:傳入檔案檔案名稱
	 3.String open_mode:開檔模式
	 傳回:無  
	 說明:建立 util_File 建構子,載入檔案
	 *
	 */
	public util_File(String path, String filename, String open_mode) throws IOException {
		open(path, filename, open_mode);
	}

	/**
	 * 
	 參數:1.String filename:傳入檔案路徑名稱
	 傳回:boolean:檔案是否存在  
	 說明:檢查 filename 的 file 存不存在
	 *
	 */
	public static boolean isFileExist(String filename) {
		File f_handler = new File(filename);
		if (f_handler == null)
			return false;
		return f_handler.exists();
	}

	/**
	 * 
	 參數:1.String filename:傳入檔案名稱
	 2.String open_mode:開檔模式 
	 傳回:boolean:開檔是否成功  
	 說明:開檔,開成讀或寫,
	 可以同時開啟兩個 file handler,
	 1. "r" for read 
	 exception if no such file...
	 2. "wa" for append 
	 exception is file not exist...
	 3. "w" for write 
	 will not exception unless permission deny
	 *
	 */
	public boolean open(String filename, String open_mode) throws IOException {
		open_mode = open_mode.toLowerCase();
		if (filename == null)
			throw new IOException("got null filename in parameter");
		if (filename.length() == 0)
			throw new IOException("got filename with length 0");
		if (open_mode.indexOf("r") != -1) {
			return _open(filename, OPEN_FOR_READ);
		}

		if (open_mode.indexOf("w") != -1) {
			if (open_mode.indexOf("a") != -1) {
				return _open(filename, OPEN_FOR_WRITE | OPEN_FOR_APPEND);
			}
			return _open(filename, OPEN_FOR_WRITE | OPEN_FOR_OVERWRITE);
		}
		return false; // error setting flags;
	}

	/**
	 * 
	 參數:1.String path:傳入檔案路徑
	 2.String filename:傳入檔案名稱
	 3.String open_mode:開檔模式 
	 傳回:boolean:開檔是否成功  
	 說明:開檔,開成讀或寫,
	 可以同時開啟兩個 file handler,
	 1. "r" for read 
	 exception if no such file...
	 2. "wa" for append 
	 exception is file not exist...
	 3. "w" for write 
	 will not exception unless permission deny
	 *
	 */
	public boolean open(String path, String filename, String open_mode)
		throws IOException {
		if (path.charAt(path.length() - 1) != FileSeperator.charAt(0))
			path = path.concat(FileSeperator);
		path = path.concat(filename);
		return open(path, open_mode);
	}

	private boolean _open(String filename, int open_flag) throws IOException {
		
		if ((open_flag & OPEN_FOR_READ) != 0) {
			pbIsReadValid = _openForRead(filename);
			return pbIsReadValid;
		} else {
			pbIsWriteValid = _openForWrite(filename, open_flag);
			return pbIsWriteValid;
		}
	}

	/*
	 *  used for open
	 */
	private boolean _openForRead(String filename) throws IOException {
		if (pbIsReadValid) {
			_close_read();
		}
		/*
		 if( filename.charAt(0)=='#')
		 {
		 String tmpFile = filename.substring(1);
		 try {
		 String path = getRemotePath(tmpFile);
		 String fname = getRemoteFileName(tmpFile);
		 reader = adm_RemoteFile.OpenFile(path,fname);
		 } catch (Exception io){
		 throw new IOException(io.getMessage());
		 }
		 return true;
		 }
		 */
		f = new File(filename);
		if (!f.exists()) {
			throw new IOException("file " + filename + " not exists");
		}
		//reader = new LineNumberReader(new BufferedReader(new FileReader(f)));
		if (FileSeperator.equals("\\"))
			fileEncode = "MS950";
		else
			//fileEncode=System.getProperty("file.encoding");
			fileEncode = "MS950";
		reader = new LineNumberReader(new BufferedReader(new InputStreamReader(
			new FileInputStream(f), fileEncode)));
		currentFileName = filename;
		return true;
	}

	/*
	 * used for open
	 */
	private boolean _openForWrite(String filename, int open_flag) throws IOException {
		if (pbIsWriteValid) {
			_close_write();
		}
		/*
		 if ( filename.charAt(0) == '#') // it is for remote
		 {
		 String tmpFile = filename.substring(1);
		 try {
		 String path = getRemotePath(tmpFile);
		 String fname = getRemoteFileName(tmpFile);
		 writer = adm_RemoteFile.CreateFile(path,fname);
		 } catch (Exception io){
		 throw new IOException(io.getMessage());
		 }
		 return true;
		 }
		 */
		if (FileSeperator.equals("\\"))
			fileEncode = "MS950";
		else
			//fileEncode=System.getProperty("file.encoding");
			fileEncode = "MS950";
		if ((open_flag & OPEN_FOR_APPEND) != 0) {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				filename, true), fileEncode));
		} else {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				filename, false), fileEncode));
		}
		currentFileName = filename;
		return true;
	}

	private String getRemotePath(String filename) throws Exception {
		int idx = -1;
		String volumn = null;
		if ((idx = filename.indexOf(":")) != -1) {
			volumn = filename.substring(0, idx);
			filename = filename.substring(idx + 1);
		} else {
			volumn = "SYS";
		}
		StringBuffer buf = new StringBuffer(volumn);
		String last = null;
		while ((idx = filename.indexOf("/")) != -1) {
			int next = filename.indexOf("/", idx + 1);
			if (next != -1) // 下面還有
			{
				if (idx != next) {
					buf.append(filename.substring(idx, next));
					filename = filename.substring(next);
				}
			} else {
				break;
			}
		}
		return buf.toString();
	}

	private String getRemoteFileName(String filename) throws Exception {
		int idx = filename.lastIndexOf("/");
		if (idx != -1)
			return filename.substring(idx + 1);
		idx = filename.lastIndexOf(":");
		if (idx != -1)
			return filename.substring(idx + 1);
		throw new Exception("Can not find remote filename");
	}

	/**
	 *  call renameTo , will auto close the
	 *  emis_File Object if not closed yet
	 *  and will rename to destFile
	 *  exception occurs if
	 *  File.renameTo throw Exception
	 *  @param dstFile destination file name
	 */
	/**
	 *
	 參數:String dstFile:更改檔案名字
	 傳回:boolean:更改檔案名字是否成功  
	 說明:更改檔案名字
	 *
	 */
	public boolean renameTo(String dstFile) throws Exception {
		File src = new File(currentFileName);
		File dst = new File(dstFile);
		if (isReadable() || isWriteable())
			close();
		return src.renameTo(dst);
	}

	/**
	 *
	 參數:String src:來源檔案名稱
	 String dst:目地檔案名稱
	 傳回:boolean:更改檔案名字是否成功  
	 說明:更改檔案名字
	 *
	 */
	public static boolean renameTo(String src, String dst) throws Exception {
		File s = new File(src);
		File d = new File(dst);
		return s.renameTo(d);
	}

	/**
	 *
	 參數:1.char []cbuf: 存放讀取資料的 buffer
	 2.int offset:開始讀取字元的位置
	 3.int offset:讀取字元的長度
	 傳回:int 
	 說明:讀取檔案內容
	 *
	 */
	public int read(char[] cbuf, int offset, int len) throws IOException {
		checkRead();
		return reader.read(cbuf, offset, len);
	}

	/**
	 *
	 參數:char []cbuf: 存放讀取資料的 buffer
	 傳回:int 
	 說明:讀取檔案內容
	 *
	 */
	public int read(char[] cbuf) throws IOException {
		checkRead();
		return reader.read(cbuf);
	}

	/**
	 *
	 參數:無
	 傳回:String :回傳一列String 
	 說明:讀取檔案內容,每次讀取一列
	 *
	 */
	public String readline() throws IOException {
		checkRead();
		return reader.readLine();
	}

	/**
	 *
	 參數:int v:數質資料
	 傳回:無 
	 說明:寫入int資料到檔案內
	 *
	 */
	public void write(int v) throws IOException {
		checkWrite();
		writer.write(String.valueOf(v));
	}

	/**
	 *
	 參數:String sValue:字串資料
	 傳回:無 
	 說明:寫入String 資料到檔案內
	 *
	 */
	public void write(String sValue) throws IOException {
		checkWrite();
		writer.write(sValue);
	}

	/**
	 *
	 參數:String sValue:字串資料
	 傳回:無 
	 說明:寫入一列資料到檔案內
	 *
	 */
	public void writeln(String sValue) throws IOException {
		checkWrite();
		write(sValue + LineSeparator);
	}

	/**
	 *
	 參數:無
	 傳回:無 
	 說明:寫入一列空的資料到檔案內
	 *
	 */
	public void writeln() throws IOException {
		checkWrite();
		write(LineSeparator);
	}

	private void checkRead() throws IOException {
		if (!pbIsReadValid || (reader == null))
			throw new IOException("File is not opened for Read");
	}

	private void checkWrite() throws IOException {
		if ((!pbIsWriteValid) || (writer == null))
			throw new IOException("file is not ready for write");
	}

	/**
	 *
	 參數:無
	 傳回:無 
	 說明:close all handlers
	 *
	 */
	public void close() {
		_close_read();
		_close_write();
		currentFileName = null;
	}

	/*
	 *  used for close
	 *  it close read file handler
	 */
	private void _close_read() {
		pbIsReadValid = false;
		if (reader != null) {
			try {
				reader.close();
			} catch (Exception e) {
				//sys_cfg.log("_close_read:",e);
			}
			reader = null;
		}
	}

	/*
	 *  used for close
	 *  it close write file handler
	 */
	private void _close_write() {
		pbIsWriteValid = false;
		if (writer != null) {
			try {
				writer.close();
			} catch (Exception e) {
				//sys_cfg.log("_close_write:",e);
			}
			writer = null;
		}
	}

	/**
	 *
	 參數:String FileName:傳入檔案名稱
	 傳回:無 
	 說明:建立一個新的檔案
	 *
	 */
	public void createFile(String FileName) throws IOException {
		Writer P = new FileWriter(FileName, false);
		P.close();
	}

	/**
	 *
	 參數:無
	 傳回:boolean:是否成功 
	 說明:關閉並刪除目前檔案
	 *
	 */
	public boolean delete() {
		close();
		f = new File(currentFileName);
		try {
			if (f.exists())
				return f.delete();
			else
				return false;
		} catch (Exception e) {
			return false;
		} finally {
			f = null;
		}
	}

	/**
	 *
	 參數:String FileName:傳入檔案名稱
	 傳回:boolean:是否成功 
	 說明:刪除檔案
	 *
	 */
	public boolean delete(String FileName) {
		File f_handler;
		try {
			f_handler = new File(FileName);
			if (f_handler.exists())
				return f_handler.delete();
			else
				return false;
		} catch (Exception e) {
			//sys_cfg.log("Delete:"+FileName,e);
			return false;
		}
	}

	/**
	 *
	 參數:無
	 傳回:boolean:是否成功 
	 說明:開讀檔模式時,檢查檔案是否開檔成功
	 *
	 */
	public boolean isReadable() {
		return pbIsReadValid;
	}

	/**
	 *
	 參數:無
	 傳回:boolean:是否成功 
	 說明:開寫檔模式時,檢查檔案是否開檔成功
	 *
	 */
	public boolean isWriteable() {
		return pbIsWriteValid;
	}

	/**
	 *
	 參數:無
	 傳回:String:檔案名稱 
	 說明:取得開啟的檔案名稱
	 *
	 */
	public String getFileName() {
		return currentFileName;
	}

	/**
	 *
	 參數:無
	 傳回:無 
	 說明:程式結束時執行
	 *
	 */
	protected void finalize() throws Throwable {
		close();
	}

}