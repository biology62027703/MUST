package com.sertek.form;

import java.io.File;
import java.util.List;
import com.sertek.util.*;
/**
 *目的:GOOGLE清單從CSV切檔的FOLDER轉檔成XLSX檔案
 */
public class Google_List {

public static void main(String[] args) throws Exception {	
    	String[] filenames;
    	File filefolder = new File("D:\\google相關\\2017Q3\\csv\\");
        String fullpath = filefolder.getAbsolutePath();
        if(filefolder.isDirectory()){
          filenames=filefolder.list();
          for (int i = 0 ; i < filenames.length ; i++){         
            File Filepath = new File(fullpath + "\\" + filenames[i]);
            if(Filepath.isDirectory()){
              System.out.println("目錄:" + filenames[i]);
            }
            else{
              System.out.println("檔案:" + Filepath.toString());
              CsvUtil CsvUtil = new CsvUtil();
              List<String[]> list = CsvUtil.readCsv(Filepath.toString());
              ReadWriteExcel ReadWriteExcel = new ReadWriteExcel();
              ReadWriteExcel.setKeys(new String[]{"Video ID","Video Title","Views","Gross Revenue","Revenue Currency","Song Title","ISRC","ISWC","Publishers","Artist"});
              ReadWriteExcel.writeXLSXFile_google(Filepath.toString().replace(".csv", ".xlsx"), list);
              list.clear();
	        	
            }
            Thread.sleep(1000);
          }
        }
    }
}