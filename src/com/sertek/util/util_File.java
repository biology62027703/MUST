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
 *	Description   : Ū�g�ɮץΪ����� class
 *
 *     
 ***************************************************************************************************** 
 */
package com.sertek.util;

import java.io.*;

/**
 *  Description:
 *    �o�� class �i�H���A�P�ɶ}�����
 *    �@����Ū,�@���ɼg
 *    <FONT COLOR=RED>�����i�H�P�ɹ�P�@���ɰ� [Ū/�g]</FONT>
 *    write , writeln ���O�� open_for_write
 *    ���ɰ��ʧ@
 *    Example:
 *    <H5>
 *       util_File _F = new util_File()
 *       try { 
 *         _F.open("tmp","w");
 *         if(_F.IsWriteable())
 *         {
 *             _F.writeln("test ���� test");
 *             _F.writeln();
 *         }
 *       } catch(Exception e){
 *            showerror(e);
 *       } finally {
 *           _F.close();
 *       }
 *    </H5>
 *
 *    read , readline ���O�� open_for_read
 *    ���ɰ��ʧ@
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

	// notice : ���i�H��t�Ϊ� file separator.. �|�O "\"
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
	 �Ѽ�:�L
	 �Ǧ^:�L  
	 ����:�إ� util_File �غc�l
	 *
	 */
	public util_File() {
	}

	/**
	 *
	 util_File  Constructor 
	 �Ѽ�:1.String filename:�ǤJ�ɮ׸��|�ɮצW��
	 2.String open_mode:�}�ɼҦ�
	 �Ǧ^:�L  
	 ����:�إ� util_File �غc�l,���J�ɮ�
	 *
	 */
	public util_File(String filename, String open_mode) throws IOException {
		open(filename, open_mode);
	}

	/**
	 *
	 util_File  Constructor 
	 �Ѽ�:1.String path :�ǤJ�ɮ׸��|�W��
	 2.String filename:�ǤJ�ɮ��ɮצW��
	 3.String open_mode:�}�ɼҦ�
	 �Ǧ^:�L  
	 ����:�إ� util_File �غc�l,���J�ɮ�
	 *
	 */
	public util_File(String path, String filename, String open_mode) throws IOException {
		open(path, filename, open_mode);
	}

	/**
	 * 
	 �Ѽ�:1.String filename:�ǤJ�ɮ׸��|�W��
	 �Ǧ^:boolean:�ɮ׬O�_�s�b  
	 ����:�ˬd filename �� file �s���s�b
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
	 �Ѽ�:1.String filename:�ǤJ�ɮצW��
	 2.String open_mode:�}�ɼҦ� 
	 �Ǧ^:boolean:�}�ɬO�_���\  
	 ����:�}��,�}��Ū�μg,
	 �i�H�P�ɶ}�Ҩ�� file handler,
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
	 �Ѽ�:1.String path:�ǤJ�ɮ׸��|
	 2.String filename:�ǤJ�ɮצW��
	 3.String open_mode:�}�ɼҦ� 
	 �Ǧ^:boolean:�}�ɬO�_���\  
	 ����:�}��,�}��Ū�μg,
	 �i�H�P�ɶ}�Ҩ�� file handler,
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
			if (next != -1) // �U���٦�
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
	 �Ѽ�:String dstFile:����ɮצW�r
	 �Ǧ^:boolean:����ɮצW�r�O�_���\  
	 ����:����ɮצW�r
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
	 �Ѽ�:String src:�ӷ��ɮצW��
	 String dst:�ئa�ɮצW��
	 �Ǧ^:boolean:����ɮצW�r�O�_���\  
	 ����:����ɮצW�r
	 *
	 */
	public static boolean renameTo(String src, String dst) throws Exception {
		File s = new File(src);
		File d = new File(dst);
		return s.renameTo(d);
	}

	/**
	 *
	 �Ѽ�:1.char []cbuf: �s��Ū����ƪ� buffer
	 2.int offset:�}�lŪ���r������m
	 3.int offset:Ū���r��������
	 �Ǧ^:int 
	 ����:Ū���ɮפ��e
	 *
	 */
	public int read(char[] cbuf, int offset, int len) throws IOException {
		checkRead();
		return reader.read(cbuf, offset, len);
	}

	/**
	 *
	 �Ѽ�:char []cbuf: �s��Ū����ƪ� buffer
	 �Ǧ^:int 
	 ����:Ū���ɮפ��e
	 *
	 */
	public int read(char[] cbuf) throws IOException {
		checkRead();
		return reader.read(cbuf);
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:String :�^�Ǥ@�CString 
	 ����:Ū���ɮפ��e,�C��Ū���@�C
	 *
	 */
	public String readline() throws IOException {
		checkRead();
		return reader.readLine();
	}

	/**
	 *
	 �Ѽ�:int v:�ƽ���
	 �Ǧ^:�L 
	 ����:�g�Jint��ƨ��ɮפ�
	 *
	 */
	public void write(int v) throws IOException {
		checkWrite();
		writer.write(String.valueOf(v));
	}

	/**
	 *
	 �Ѽ�:String sValue:�r����
	 �Ǧ^:�L 
	 ����:�g�JString ��ƨ��ɮפ�
	 *
	 */
	public void write(String sValue) throws IOException {
		checkWrite();
		writer.write(sValue);
	}

	/**
	 *
	 �Ѽ�:String sValue:�r����
	 �Ǧ^:�L 
	 ����:�g�J�@�C��ƨ��ɮפ�
	 *
	 */
	public void writeln(String sValue) throws IOException {
		checkWrite();
		write(sValue + LineSeparator);
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:�L 
	 ����:�g�J�@�C�Ū���ƨ��ɮפ�
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
	 �Ѽ�:�L
	 �Ǧ^:�L 
	 ����:close all handlers
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
	 �Ѽ�:String FileName:�ǤJ�ɮצW��
	 �Ǧ^:�L 
	 ����:�إߤ@�ӷs���ɮ�
	 *
	 */
	public void createFile(String FileName) throws IOException {
		Writer P = new FileWriter(FileName, false);
		P.close();
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:boolean:�O�_���\ 
	 ����:�����çR���ثe�ɮ�
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
	 �Ѽ�:String FileName:�ǤJ�ɮצW��
	 �Ǧ^:boolean:�O�_���\ 
	 ����:�R���ɮ�
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
	 �Ѽ�:�L
	 �Ǧ^:boolean:�O�_���\ 
	 ����:�}Ū�ɼҦ���,�ˬd�ɮ׬O�_�}�ɦ��\
	 *
	 */
	public boolean isReadable() {
		return pbIsReadValid;
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:boolean:�O�_���\ 
	 ����:�}�g�ɼҦ���,�ˬd�ɮ׬O�_�}�ɦ��\
	 *
	 */
	public boolean isWriteable() {
		return pbIsWriteValid;
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:String:�ɮצW�� 
	 ����:���o�}�Ҫ��ɮצW��
	 *
	 */
	public String getFileName() {
		return currentFileName;
	}

	/**
	 *
	 �Ѽ�:�L
	 �Ǧ^:�L 
	 ����:�{�������ɰ���
	 *
	 */
	protected void finalize() throws Throwable {
		close();
	}

}