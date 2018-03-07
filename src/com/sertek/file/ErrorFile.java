package com.sertek.file;
import java.io.*;
import java.util.*;
import java.text.*;


public class ErrorFile{
	/**
		*�N��������L�{�������~�A�g��log�ɮפ��A��K�����@���ݭn
		@authoer	Ellin Chen
	*/
	public ErrorFile() {
		this.fname = "c:/error.log";
	}
	
	/**
		*	�]�w���~�g�J�ɮת��غc�l
		@param		file	�n�g�J���~���ɮצW��
	*/
	public ErrorFile(String file) {
		this.setFile(file);
	}
	
	/**
		*	�]�w�n�g�J���~���ɮצW��
		@param		file	�n�g�J���~���ɮצW��
		@return		�L
	*/
	public void setFile(String file) {
		this.fname = file;
		//System.out.println("fname = " + fname);
	}
	
	/**
		*���o�ɮצW�ٻP���|
		@return		(String)
	*/
	public String getFile() {
		return this.fname;
	}
	
	/**
		*�N���~��Vector�g�J�ɮפ�
		@param		ob	�ѭ��Ӫ���Ҷǰe�L�Ӫ����~
		@param		error	���~���T��Vector
		@return		�ǰe�g�J���\�Υ���,���G<=0:����, ���G>0:���\
	*/
	public int writeError(Object ob,Vector error) {
		return write(ob,error,systemOut(ob));	
	}
	
	/**
		*�N���~��String�g�J�ɮפ�
		@param		ob	�ѭ��Ӫ���Ҷǰe�L�Ӫ����~
		@param		error	���~���T��String
		@return		�ǰe�g�J���\�Υ��ѡA���G<=0:����, ���G>0:���\
	*/
	public int writeError(Object ob,String error) {
		Vector vr = new Vector();
		vr.add(error);
		return write(ob,vr,systemOut(ob));
	}
	
	/**
		*�N���~����Ƽg�J�ɮפ�
		@param		ob
		@param		error
		@param		sys
		@return		�Ǧ^���\�Υ��ѡA���G<=0:����, ���G>0:���\
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
				//System.out.println("�g�ɮת���m=" + 0);	
			} else {
				ranfile.seek(ranfile.length()+1);
				//System.out.println("�g�ɮת���m=" + (ranfile.length()+1));
			}
			//System.out.println("ranfile length = " + ranfile.length());
			*/
			//�g�J�T�w���D���ɮ�
			result = write(w,sys);
			//�g�J���~�T�����ɮ�
			//if(error.size()>0) w.newLine();
			error.add(getEnd());
			result = write(w,error);
			//����file
			w.close();	
			//f.close();
			result = 1;
		}catch(FileNotFoundException e) {
			//System.out.println("�䤣���ɮ�");	
		} catch(IOException e) {
			//System.out.println("�g�J�ɮץ���");	
		} 
		return result;
	}
	
	/**
		*�g�J�ɮ�
		@param		ranfile
		@param		meg	Vector
		@return		�Ǧ^���\�Υ��Ѫ����G�A���G<=0:���ѡA���G>0:���\�C
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
		*Ū�X�Ҧ������~���e
		@param		�L
		@return		Vector�����~���e
	*/
	private Vector readError() {
		Vector result = new Vector();
		String temp = "";
		try {
			File f = new File(fname);	
			BufferedReader r = new BufferedReader(new FileReader(f));
			while((temp = r.readLine())!=null) {
				//System.out.println("Ū�X�����G=" + temp);
				result.add(temp);
				//result.add(r.readLine());
				//result = ranfile.readUTF();
			}
			//result.add(getEnd());
			r.close();
			//f.close();
		} catch(FileNotFoundException e) {
			//System.out.println("Ū�����ɮ�");
		} catch(IOException e) {
			//System.out.println(e);	
		}
		return result;	
	}
	/**
		*���~���T�w�ǥX�t�θ��
		@param		�L
		@return		�t�Ϊ����Vector
	*/
	public Vector systemOut(Object ob) {	
		Vector result = new Vector();
		result.add("System DateTime:" + nowDateTime());
		result.add("Object = " + getClassName(ob));
		return result;
	}
	
	/**
		*���o���󪺦W��
		@param		ob	����
		@return		���󪺦W��
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