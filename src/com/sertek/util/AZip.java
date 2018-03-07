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
import org.apache.tools.zip.*;
import de.idyl.crypto.zip.*;
import de.idyl.crypto.zip.impl.*;
import com.sertek.util.*;
import com.sertek.file.*;
/**
 * Apche Zip
 * 壓縮元件，比起Zip.class 而言，可以處理中文檔案(若有加密，則不支援中文)
 * 1.必需有apache ant 的 org.apache.tools.zip 套件。
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
 *
 */
public class AZip {
	private String zipfile = "";
	private FileOutputStream fos;
	private ZipOutputStream zos;
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
	public AZip() {
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
	public AZip(boolean containDir) {
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
			zos = new ZipOutputStream(fos);
			zos.setMethod(ZipOutputStream.DEFLATED);
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
			System.out.println("os="+os);
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
			zos.flush();

			//Step 6: Close the zip entry and other open streams
			zos.closeEntry();
			zos.close();
			
			retVal = true;
			//-------------加密---------------------
			if (this.isEncrypt){
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
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return retVal ;
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
			System.out.println("壓縮 " + dataFileName + " ....");
			FileInputStream fis = new FileInputStream(dataFileName);
			BufferedInputStream sourceStream = new BufferedInputStream(fis);
			//Step 3: Create the zip entry
			File f = new File(dataFileName);
			ZipEntry theEntry = null;
			if (containDir) {
				theEntry = new ZipEntry(util.replace(dataFileName, prefixPath, ""));
				System.out.println( util.replace(dataFileName, prefixPath, "") );
			} else {
				theEntry = new ZipEntry(f.getName());
			}
			//修改日期
			theEntry.setTime(f.lastModified());
			//http://www.jexamples.com/vSrc/508/org.apache.tools.zip.ZipEntry?prodId=ant&lineNo=262&implExtId=30358&queryText=java.util.Vector.setElementAt&qType=clsMeth
			
			//Step 4: Put the zip entry into the archive
			zos.putNextEntry(theEntry);
			//Step 5: Read source and write the data to the zip output stream
			int DATA_BLOCK_SIZE = 2000;
			byte[] data = new byte[DATA_BLOCK_SIZE];
			int bCnt = 0;
			while ((bCnt = sourceStream.read(data, 0, DATA_BLOCK_SIZE)) != -1) {
				
				zos.write(data, 0, bCnt);

			}
			System.out.println("壓縮 " + dataFileName + " .... OK ");
			sourceStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}