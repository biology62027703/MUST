/*
----------------------------------------------------------------------------------------
���D�渹�GBug #2831 - ���Ű|�~�d��-�k�|����X�{��
�ק�K�n�G���ɵ{���ק�sertek.jar��Zip.class�A�P�B�ק�jud_k��jud_e��Zip
�ק�H���Geason
�ק����G0970305             
��s�����GV9704-����&�q�k�ưȩx
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
 * ���Y����A�i�H�B�z�����ɮפ� UTF8 (�Y���[�K�A�h���䴩����)
 * 1.���ݦ�commons-compress �M��C
 * 2.���ݦ� winzipaes �M��
 * <pre>
 * �ϥνd��: 
 * AZip z = new AZip();
 * z.createZip("c:\\ddd.zip");
 * z.addFile("c:\\fff");
 * //z.Encrypter("abc");    //�ѱK�K�X
 * if (z.writeZip()==false){
 * 	out.println("����");
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
	private Vector Entries = new Vector(); //��m�Ҧ��n�[�J zip ���ɦW�W��
	private boolean containDir = true;
	private String prefixPath = "";
	private String os = "";
	private String passwd = "";
	private String separator = File.separator;
	private boolean isEncrypt = false;

	/**
	 * ���Y�ɥ]�t���|��T
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
	 * @param containDir �O�_�bzip�ɸ̥]�t���㪺���|
	 */
	public AZip2(boolean containDir) {
		checkOs();
		this.containDir = containDir;
	}
	/**
	 * �]�w���Y�ɱK�X
	 * @param passwd
	 */
	public void Encrypter(String passwd){
		this.passwd = passwd;
		this.isEncrypt = true;
	}
	public void createZip(String zipfile) {
		try {
			//Step 1: Create the zip output stream

			//�pzip�ɤ��s�b,�h�s�W�@��(�t�ؿ�)
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
	 * �W�[�ɮר� zip �ɡA
	 * @param file    �n�W�[��zip �ɪ��ɮסA�p�G�O�ؿ��A�h�|�N�ӥؿ��U���ɮװ����Y
	 */
	public void addFile(String file) {

		Entries.add(file);
	}

	/**
	 * �g�Jzip ��
	 * @deprecated
	 */
	public void write() {
		writeZip();
	}
	/**
	 * �g�Jzip ��
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
				if ((checkFileOrDir(dataFileName)).equals("file")) //�ɮ�
				{
					WriteIntoZip(dataFileName);

				} else { //�ؿ�
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
		
		//-------------�[�K---------------------
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
	
	//�g�ؿ��U���ɮ�
	private void WriteWithDir(String dataFileName) {
		System.out.println("WriteWithDir =" + dataFileName);
		File file = new File(dataFileName);
		File[] fileList = file.listFiles();
		for (int j = 0; j < fileList.length; j++) {
			File subFile = (File) fileList[j];
			String dataFileName1 = "";
			dataFileName1 = subFile.getAbsolutePath();
			if (subFile.isFile()) //�p�G�O�ɮ׫h�g�ɮ�
			{
				WriteIntoZip(dataFileName1);
			} else { //�p�G�O�ؿ��h�ۤv�I�s�ۤv(WriteWithDir)
				WriteWithDir(dataFileName1);
			}

			subFile = null;

		}

		file = null;

	}

	//�ˬd�O�ɮשΥؿ�
	private String checkFileOrDir(String data) {
		String retVal = "file";
		File file = new File(data);
		if (file.isDirectory())
			retVal = "dir";

		file = null;
		return retVal;
	}

	//�N���o���ɮצW�ټg��zip�ɮ�
	private void WriteIntoZip(String dataFileName) {
		try {
			//System.out.println("���Y " + dataFileName + " ....");
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
			//�ק���
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