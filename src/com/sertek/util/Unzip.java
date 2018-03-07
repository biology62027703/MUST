package com.sertek.util;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import de.idyl.crypto.zip.*;
import de.idyl.crypto.zip.impl.*;
/**
 * 
 * @author wengchi
 * <[tr>
 * 解壓縮中文檔名會有問題，請改用AUnzip(Apahce unzip)
 * 建構子 例子: 
 * Unzip z = new Unzip();
 * z.UnzipFrom("c:\\ddd.zip"); 
 * z.UnzipTo("d:\\safe");
 * z.writeZip();
 *
 */
public class Unzip {
	private String zipFromString = ""; //要從那個壓縮檔解壓縮
	private String zipToString = "./"; //要解壓縮到那個目錄
	private Enumeration entries;
	
	private utility ut = new utility();
	static String os = checkOS();
	private ZipFile zipFile;

	private boolean containDir = false; //是否包含路徑
	private boolean isDecrypt = false;	//解密
	private String passwd = ""; 
	//AesZipFileDecrypter zipFile = null;

	/**
	 * @deprecated
	 * 
	 */

	public Unzip() {
		this.containDir = false;
	}

	/**
	 * @param containDir
	 *            解壓縮時是否包含原本的路徑資料
	 */
	public Unzip(boolean containDir) {
		this.containDir = containDir;
	}
	/**
	 * 設定解密密碼
	 * @param passwd
	 */
	public void Decrypter(String passwd){
		this.passwd = passwd;
		this.isDecrypt = true;
	}

	public static final void copyInputStream(InputStream in, OutputStream out)
		throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();

	}

	/**
	 * 設定 zip 檔的位置
	 * 
	 * @param zipFile
	 *            zip 檔名(包含路徑)
	 */
	public void UnzipFrom(String zipFile) {
		this.zipFromString = zipFile;
	}

	/**
	 * 設定要解壓縮到那個目錄下
	 * 
	 * @param path
	 *            解壓縮到那個目錄
	 */
	public void UnzipTo(String path) {
		try {
			char index;
			index = path.charAt(path.length() - 1);

			if (os.equals("linux")) {
				if (index != '/')
					this.zipToString = path + "/";
				else
					this.zipToString = path ;

			} else {
				if (index != '\\')
					this.zipToString = path + "\\";
				else
					this.zipToString = path;
			}
			
			File f = new File(path);
			f.mkdirs();
			f = null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * 開始解壓縮
	 * @deprecated
	 */
	public void write() {
		writeZip();
	}
	/**
	 * 開始解壓縮
	 */
	public boolean writeZip(){
		boolean retVal = false;
		if (isDecrypt){
			retVal =unzipByDecypt();
		}else{
			retVal = unzip();
		}
		return retVal;
	}
	private boolean unzipDirByDecrypt(String dir,String passwd,AesZipFileDecrypter zipFile){
		boolean retVal = false;
		String dir1 = "";
		if (dir.indexOf( dir.length()-1 )!= File.separatorChar) dir+= File.separatorChar;
		List l = null;
		try{
			l = zipFile.getEntryList();
			String file = "";//記錄壓縮檔內  (路徑+檔名)
			String filename = ""; //只有檔名
			String tempfile = "";
			long filetime = 0;
			for (int i=0;i<l.size();i++ ){
				ExtZipEntry entry = (ExtZipEntry) l.get(i);
				file = entry.getName();	
				filetime = entry.getTime();
				java.io.File e = new java.io.File(dir + file);
				filename = e.getName();		//filename 只有檔名
				if (this.containDir){
					tempfile =  dir + file;
				}else{
					tempfile = dir + filename;	
				}
				zipFile.extractEntry(entry,new java.io.File(tempfile),passwd);
				this.setFileTime(tempfile,filetime);
			}
			retVal = true;
		}catch(Exception e){
			e.printStackTrace();
			retVal = false;
		}	
			
		l = null;	
		return retVal ;
	}
	private boolean unzipByDecypt(){
		boolean retVal = false;
		AesZipFileDecrypter zipFile = null;
		try{
			zipFile = new AesZipFileDecrypter(new java.io.File( zipFromString ));
			retVal = unzipDirByDecrypt(this.zipToString,this.passwd,zipFile);
			zipFile.close();
		}catch(Exception e){
			e.printStackTrace();
			retVal = false;
		}
		return retVal;
	}
	private boolean unzip(){
		boolean retVal = false;
		try {
			zipFile = new ZipFile(zipFromString);

			entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				if (entry.isDirectory()) {
					// Assume directories are stored parents first then children.
					//System.err.println("Extracting directory: " + entry.getName());
					// This is not robust, just for demonstration purposes.
					(new File(entry.getName())).mkdir();
					//System.out.println(" " + entry.getName());
					continue;
				}

				//System.err.println("Extracting file: " + entry.getName());
				String ddd = "";
				int index = -1;

				if (containDir == false) {
					if ((entry.getName()).lastIndexOf("/") != -1)
						index = (entry.getName()).lastIndexOf("/");
					else if ((entry.getName()).lastIndexOf("\\") != -1) {
						index = (entry.getName()).lastIndexOf("\\");
					}

					if (index != -1) {
						ddd = (entry.getName()).substring(index + 1, (entry.getName())
							.length());
					} else {
						ddd = entry.getName();
					}
				} else {
					//判斷 是否有 : ，表示 有路徑是 c:\ 或者是 d:\ ，只取\ 後面的路徑
					//例如 c:\test\test1 只要取 \test\test1 ，
					ddd = entry.getName();

					index = (entry.getName()).indexOf(":");
					if (index != -1) {
						ddd = (entry.getName()).substring(index + 2, (entry.getName())
							.length());

					}
					if ((checkOS()).equals("win")) {
						ddd = ut.StrTran(ddd, "/", "\\");
					} else {
						ddd = ut.StrTran(ddd, "\\", "/");
					}
				}
				//System.out.println("解壓縮到"+zipToString + ddd);
				createDir(zipToString + ddd);
				copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(
					new FileOutputStream(zipToString + ddd)));
				
				setFileTime(entry,zipToString + ddd);
			}
			zipFile.close();
			retVal = true;
		} catch (IOException ioe) {
			System.err.println("Unhandled exception:");
			ioe.printStackTrace();
			retVal = false;
		}
		return retVal;
	}
	
	/*
	 * 將解壓縮後的檔案日期改回正確的時間
	 */
	private void setFileTime(ZipEntry zn,String dins){
		try{
			if (zn!=null){
				File f = new File(dins);
				f.setLastModified(zn.getTime());
				f = null;
			}
		}catch(Exception e){}	
	}
	/*
	 * 將解壓縮後的檔案日期改回正確的時間
	 */
	private void setFileTime(String filename,long filetime){
		try{	
				File f = new File(filename);
				if (f.exists())
					f.setLastModified(filetime);
				f = null;
			
		}catch(Exception e){}	
	}
	public static String checkOS() {
		String retVal = "linux";
		if ((File.separator).equals("\\"))
			retVal = "win";

		//System.out.println("os="+retVal);
		return retVal;
	}

	public void createDir(String path) {
		int index = -1;
		
		if ((checkOS()).equals("win")) {

			index = path.lastIndexOf("\\");
		} else {
			index = path.lastIndexOf("/");
		}
		path = path.substring(0, index);
		
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();
		f = null;

	}

}