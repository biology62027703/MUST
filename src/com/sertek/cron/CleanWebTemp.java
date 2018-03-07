package com.sertek.cron;
import com.sertek.util.*;
import org.quartz.impl.*;
import org.quartz.*;
import java.io.*;
public class CleanWebTemp implements StatefulJob {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO �۰ʲ��ͤ�k Stub
		String WebTempDir = com.sertek.sys.Project.getWebTempDir();
		System.out.println("�}�l�M�� "+WebTempDir +" ....");
		deleteDir(WebTempDir);
		System.out.println("�����M�� "+WebTempDir +" ....");
	}
	
	public void deleteDir(String dir){
		File f = new File(dir);
		File[] flist = f.listFiles();
		if (flist!=null){
			for(int i=0;i<flist.length;i++){
				if (flist[i].isFile()){
					flist[i].delete();
				}else{
					deleteDir( flist[i].getAbsolutePath() );
					flist[i].delete();
				}
			}
		}
		flist = null;
	}
}
