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
import org.apache.tools.zip.*;
import de.idyl.crypto.zip.*;
import de.idyl.crypto.zip.impl.*;
import com.sertek.util.*;
import com.sertek.file.*;
/**
 * Apche Zip
 * ���Y����A��_Zip.class �Ө��A�i�H�B�z�����ɮ�(�Y���[�K�A�h���䴩����)
 * 1.���ݦ�apache ant �� org.apache.tools.zip �M��C
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
 *
 */
public class AZip {
	private String zipfile = "";
	private FileOutputStream fos;
	private ZipOutputStream zos;
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
	 * @param containDir �O�_�bzip�ɸ̥]�t���㪺���|
	 */
	public AZip(boolean containDir) {
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
			zos = new ZipOutputStream(fos);
			zos.setMethod(ZipOutputStream.DEFLATED);
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
			System.out.println("os="+os);
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
			zos.flush();

			//Step 6: Close the zip entry and other open streams
			zos.closeEntry();
			zos.close();
			
			retVal = true;
			//-------------�[�K---------------------
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
			System.out.println("���Y " + dataFileName + " ....");
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
			//�ק���
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
			System.out.println("���Y " + dataFileName + " .... OK ");
			sourceStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}