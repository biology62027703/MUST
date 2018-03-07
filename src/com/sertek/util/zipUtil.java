package com.sertek.util;

import java.io.File;
import java.util.*;
import java.util.zip.*;
import java.io.*;
/**
 * @deprecated
 * ���Y�ɤ��������ɦW�ɷ|�����D�A�Ч�� AzipUtil (apahce zip util)
 * ���o���Y�ɤ����ɮ׸��
 * �ϥνd�ҡG
 *  import="java.util.zip.*;
 *  
 * zipUtil zu = new zipUtil();
 *if (zu.loadZip("c:\\setup.zip")){
*	//Enumeration zu.
*	Enumeration e = zu.getEntries();
*	int i = 1;
*	while (e.hasMoreElements() ){
*		ZipEntry z = (ZipEntry)e.nextElement();
*		//���O���|�~�B�z
*		if (!z.isDirectory()){			
*			out.println("��"+i+"��<BR>");
*			out.println("���|="+ zu.getZipPath(z) +"<BR>" );
*			out.println("�j�p="+ zu.getZipentrySize(z) +"<BR>" );
*			out.println("���="+ zu.getZipentryDate(z)+"<BR>" );
*			out.println("�ɶ�="+ zu.getZipentryTime(z)+"<BR>" );
*			i++;
*		}	
*	}
*	
*	
*}
 *
 */
public class zipUtil {
	util_date ud = new util_date();

	ZipFile zf = null;
	String zipPath = "";
	
	
	public zipUtil(){
		
	}
	public boolean loadZip(String zipPath){
		boolean retVal = true;
		this.zipPath = zipPath;
		try{
			zf = new ZipFile(zipPath);
		}catch(IOException e){
			retVal = false;
		}
		return retVal;
	}
	
	public Enumeration getEntries(){
		return zf.entries();
	}
	public String getZipPath(ZipEntry zn){
		return zn.getName();
	}
	/**
	 * ���o���Y�ɤ��� �ɮפj�p
	 * @param entryName �ɮצW��(���ݥ]�t���Y�ɤ������|��T)
	 * @return
	 */
	public long getZipentrySize(String entryName){
		long retVal = 0;
		ZipEntry zn  = zf.getEntry(entryName) ;
		if (zn!=null){
			retVal =  getZipentrySize(zn);
			zn = null;
		}
		return retVal;
	}
	/**
	 * ���o���Y�ɤ��� �ɮפj�p
	 * @param zn ZipEntry 
	 * @return 
	 */
	public long getZipentrySize(ZipEntry zn){
		long retVal = 0;
		if (zn!=null){
			retVal =  zn.getSize();
			
		}
		return retVal;
	}
	
	/**
	 * ���o���Y�ɤ��� �ɮ׮ɶ�
	 * @param entryName �ɮצW��((���ݥ]�t���Y�ɤ������|��T)
	 * @return �^�Ǥ��X�ɶ��A�_�h�^�Ǫť�
	 */
	public String getZipentryTime(String entryName){
		String retVal = "";
		ZipEntry zn  = zf.getEntry(entryName) ;
		if (zn!=null){	
			retVal = getZipentryTime(zn);
			zn = null;
		}
		return retVal;
		
	}
	
	/**
	 * ���o���Y�ɤ��� �ɮ׮ɶ�
	 * @param entryName �ɮצW��((���ݥ]�t���Y�ɤ������|��T)
	 * @return �^�Ǥ��X�ɶ��A�_�h�^�Ǫť�
	 */
	public String getZipentryTime(ZipEntry zn){
		String retVal = "";
		if (zn!=null){
			Calendar rightNow = Calendar.getInstance();
			Date d = new Date(zn.getTime());
			rightNow.setTime(d);
			retVal = ud.nowTime(rightNow);
		
		}
		return retVal;
		
	}
	
	/**
	 * ���o���Y�ɤ��� �ɮפ��
	 * @param entryName �ɮצW��((���ݥ]�t���Y�ɤ������|��T)
	 * @return �^�ǤC�X����A�_�h�^�Ǫť�
	 */
	public String getZipentryDate(String entryName){
		String retVal = "";
		ZipEntry zn  = zf.getEntry(entryName) ;
		if (zn!=null){
			retVal = getZipentryDate(zn);
			zn = null;
		}
		return retVal;
	}
	
	/**
	 * ���o���Y�ɤ��� �ɮפ��
	 * @param zn ZipEntry
	 * @return �^�ǤC�X����A�_�h�^�Ǫť�
	 */
	public String getZipentryDate(ZipEntry zn){
		String retVal = "";
		if (zn!=null){
			Calendar rightNow = Calendar.getInstance();
			Date d = new Date(zn.getTime());
			rightNow.setTime(d);
			retVal = ud.nowCDate(rightNow);
			
		}
		return retVal;
	}
	
	
}
