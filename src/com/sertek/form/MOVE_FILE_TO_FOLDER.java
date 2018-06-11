package com.sertek.form;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MOVE_FILE_TO_FOLDER {
	public static void main(String[] args) {
	
		for (File f : new File("\\\\192.168.1.51\\song\\DIVAS").listFiles()) {
			if(f.isDirectory()){
				
			}else {
				//取中間的IP_BASE_NO當作關鍵字
				getDir("\\\\192.168.1.51\\song\\DIVAS",f);
			}
	        
		}
	}
	
	public static void getDir(String dir, File Filename){
        File nowFile = new File(dir);
        try {
            if (nowFile.isDirectory()) {
                File[] filelist = nowFile.listFiles();
                for (int i = 0; i < filelist.length; i++) {
                    if (filelist[i].isDirectory()) {                     
                    	String str = new String(filelist[i].getName());
                        Pattern pattern = Pattern.compile(Filename.getName().split("_")[1]);
                        Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            File DEST = new File(filelist[i].toString()+"\\"+Filename.getName().toString());
                        	boolean rt =Filename.renameTo(DEST);
                        	System.out.println(rt);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.print(e);
        }
    }
}
