/*
----------------------------------------------------------------------------------------
問題單號：Bug #2831 - 消債院外查詢-法院端轉出程式
修改摘要：轉檔程式修改sertek.jar的Zip.class，同步修改jud_k及jud_e的Zip
修改人員：eason
修改日期：0970305             
更新版本：V9704-消債&司法事務官
----------------------------------------------------------------------------------------
*/
package com.sertek.util;

import java.io.*;
import java.util.*;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream.UnicodeExtraFieldPolicy;
import org.apache.commons.compress.utils.IOUtils;
import de.idyl.crypto.zip.*;
import de.idyl.crypto.zip.impl.*;
import com.sertek.util.*;
import com.sertek.file.*;
/**
 * Apche Zip
 * 壓縮元件，可以處理中文檔案及 UTF8 (若有加密，則不支援中文)
 * 1.必需有commons-compress 套件。
 * 2.必需有 winzipaes 套件
 * <pre>
 * 使用範例: 
 * AZip z = new AZip();
 * z.createZip("c:\\ddd.zip");
 * z.addFile("c:\\fff");
 * //z.Encrypter("abc");    //解密密碼
 * if (z.writeZip()==false){
 * 	out.println("失敗");
 * }
 * </pre>
 * @author wengchi
 * @version 1.1
 *
 */
public class AZip2 {
	private String zipfile = "";
	private FileOutputStream fos;
	private ZipArchiveOutputStream zos;
	private Vector Entries = new Vector(); //放置所有要加入 zip 的檔名名稱
	private boolean containDir = true;
	private String prefixPath = "";
	private String os = "";
	private String passwd = "";
	private String separator = File.separator;
	private boolean isEncrypt = false;

	/**
	 * 壓縮檔包含路徑資訊
	 *
	 */
	public AZip2() {
		checkOs();
		containDir = true;
	}
	private void checkOs(){
		if (File.separator.equals("\\"))
			os = "win";
		else
			os = "linux";
	}
	/**
	 * @param containDir 是否在zip檔裡包含完整的路徑
	 */
	public AZip2(boolean containDir) {
		checkOs();
		this.containDir = containDir;
	}
	/**
	 * 設定壓縮檔密碼
	 * @param passwd
	 */
	public void Encrypter(String passwd){
		this.passwd = passwd;
		this.isEncrypt = true;
	}
	public void createZip(String zipfile) {
		try {
			//Step 1: Create the zip output stream

			//如zip檔不存在,則新增一個(含目錄)
			String newZipfile = zipfile.replace('\\', '/');
			this.zipfile = zipfile.replace('\\', '/');
			int index = newZipfile.lastIndexOf('/');
			String dir1 = newZipfile.substring(0, index);
			String file1 = newZipfile.substring(index + 1);
			File zipDir = new File(dir1);
			File zipFile = new File(dir1, file1);

			if (!zipDir.exists())
				zipDir.mkdirs();

			if (!zipFile.exists())
				zipFile.createNewFile();

			fos = new FileOutputStream(zipfile);
			zos = new ZipArchiveOutputStream(new BufferedOutputStream( fos ));
			//zos = new ZipOutputStream(fos);
			zos.setEncoding("MS950");
			//zos.setFallbackToUTF8(true);
			zos.setUseLanguageEncodingFlag(true);								
			//zos.setCreateUnicodeExtraFields(ZipArchiveOutputStream.UnicodeExtraFieldPolicy.NOT_ENCODEABLE);
			zos.setCreateUnicodeExtraFields(UnicodeExtraFieldPolicy.ALWAYS);
			//zos.setMethod(ZipOutputStream.DEFLATED);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 增加檔案到 zip 檔，
	 * @param file    要增加到zip 檔的檔案，如果是目錄，則會將該目錄下的檔案做壓縮
	 */
	public void addFile(String file) {

		Entries.add(file);
	}

	/**
	 * 寫入zip 檔
	 * @deprecated
	 */
	public void write() {
		writeZip();
	}
	/**
	 * 寫入zip 檔
	 * @return
	 */
	public boolean writeZip(){
		boolean retVal = false;
		try {
			for (int i = 0; i < Entries.size(); i++) {
				//Step 2:Open the source data file
				String dataFileName = (String) Entries.elementAt(i);
				this.prefixPath = dataFileName;
				System.out.println("dataFileName=" + dataFileName);
				if ((checkFileOrDir(dataFileName)).equals("file")) //檔案
				{
					WriteIntoZip(dataFileName);

				} else { //目錄
					if (os.equals("linux")){
						this.prefixPath = util.replace( this.prefixPath,"\\","/" );
						if ( prefixPath.charAt( prefixPath.length() -1 )!='/')
							prefixPath += "/";
					}else{
						this.prefixPath = util.replace( this.prefixPath,"/","\\" );
						if ( prefixPath.charAt( prefixPath.length() -1 )!='\\')
							prefixPath += "\\";
					}
					WriteWithDir(dataFileName);

				}
			}
			retVal = true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			//Step 6: Close the zip entry and other open streams
			try{
				zos.closeArchiveEntry();
				zos.close();
				zos.finish();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		
		//-------------加密---------------------
		if (retVal && this.isEncrypt){
			retVal = Encrypt();
		}
		
		return retVal ;
	}
	
	private boolean Encrypt(){
		boolean retVal = false;
		String tempZip = this.zipfile+"__";
		AesZipFileEncrypter enc = null;
		try{	
			enc = new AesZipFileEncrypter(tempZip); 
			enc.addAll( new java.io.File(this.zipfile), this.passwd );
			enc.close();
			
			File zf = new File(this.zipfile);
			if (zf.exists()) zf.delete();
			zf = null;
			FileCopy fc = new FileCopy();
			fc.copyFiles(tempZip,this.zipfile);
			fc = null;
			retVal = true;
		}catch(Exception eee){
			eee.printStackTrace();	
			retVal = false;
		}finally{
			File f = new File(tempZip);
			if (f.exists()) f.delete();
			
		}
		return retVal;
	}
	
	//寫目錄下的檔案
	private void WriteWithDir(String dataFileName) {
		System.out.println("WriteWithDir =" + dataFileName);
		File file = new File(dataFileName);
		File[] fileList = file.listFiles();
		for (int j = 0; j < fileList.length; j++) {
			File subFile = (File) fileList[j];
			String dataFileName1 = "";
			dataFileName1 = subFile.getAbsolutePath();
			if (subFile.isFile()) //如果是檔案則寫檔案
			{
				WriteIntoZip(dataFileName1);
			} else { //如果是目錄則自己呼叫自己(WriteWithDir)
				WriteWithDir(dataFileName1);
			}

			subFile = null;

		}

		file = null;

	}

	//檢查是檔案或目錄
	private String checkFileOrDir(String data) {
		String retVal = "file";
		File file = new File(data);
		if (file.isDirectory())
			retVal = "dir";

		file = null;
		return retVal;
	}

	//將取得的檔案名稱寫到zip檔案
	private void WriteIntoZip(String dataFileName) {
		try {
			//System.out.println("壓縮 " + dataFileName + " ....");
			FileInputStream fis = new FileInputStream(dataFileName);
			BufferedInputStream sourceStream = new BufferedInputStream(fis);
			//Step 3: Create the zip entry
			File f = new File(dataFileName);
			ZipArchiveEntry theEntry = null;
			if (containDir) {
				//ZipArchiveEntry a = new ZipArchiveEntry();
				theEntry = new ZipArchiveEntry(util.replace(dataFileName, prefixPath, ""));
			} else {
				theEntry = new ZipArchiveEntry(f.getName());
			}
			//修改日期
			theEntry.setTime(f.lastModified());
			
			//Step 4: Put the zip entry into the archive
			zos.putArchiveEntry(theEntry);
		
			
			//Step 5: Read source and write the data to the zip output stream
			
			
	        IOUtils.copy(sourceStream, zos);
	        

			sourceStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}