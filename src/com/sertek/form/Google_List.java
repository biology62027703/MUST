package com.sertek.form;

import com.csvreader.CsvReader;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.sertek.util.*;
/**
 *目的:GOOGLE清單從CSV切檔的FOLDER轉檔成XLSX檔案
 */
public class Google_List {


@SuppressWarnings("static-access")
public static void main(String[] args) throws Exception {	
    	String[] filenames;
    	File filefolder = new File("D:\\google相關\\2017Q3\\VideoReport_YouTube_must_TW_2017Q3_ugc_music_partner_20171016062735483989_split_22\\");
        //String filefolder = "D:\\google相關\\2017Q2\\VideoReport_YouTube_must_TW_2017Q2_ugc_music_partner_20170919232749911923_split_12\\VideoReport_YouTube_must_TW_2017Q2_ugc_music_partner_20170919232749911923_1.csv";
        String fullpath = filefolder.getAbsolutePath();
        if(filefolder.isDirectory()){
          filenames=filefolder.list();
          for (int i = 0 ; i < filenames.length ; i++){         
            File Filepath = new File(fullpath + "\\" + filenames[i]);
            if(Filepath.isDirectory()){
              System.out.println("目錄:" + filenames[i]);
            }
            else{
              //String filepath = "D:\\google相關\\2017Q2\\VideoReport_YouTube_must_TW_2017Q2_ugc_music_partner_20170919232749911923_split_12\\VideoReport_YouTube_must_TW_2017Q2_ugc_music_partner_20170919232749911923_1.csv";
              System.out.println("檔案:" + Filepath.toString());
              CsvUtil CsvUtil = new CsvUtil();
              List<String[]> list = CsvUtil.readCsv(Filepath.toString());
              ReadWriteExcel ReadWriteExcel = new ReadWriteExcel();
              //System.out.println("----------");
              ReadWriteExcel.setKeys(new String[] {"SEQ","NAME","CASE_NO","POST_TYPE","POST_DATE","POST_NAME","POST_TEXTNO","POST_NOTE","POST_PTRFLAG"});
              ReadWriteExcel.writeXLSXFile_google(Filepath.toString().replace(".csv", ".xlsx"), list);
              list.clear();
	        	/*CsvUtil su = new CsvUtil();
	            List<String[]> list = su.readCsv(Filepath.toString());
	            ReadWriteExcel ReadWriteExcel = new ReadWriteExcel();
	            System.out.println("----------");
	            ReadWriteExcel.writeXLSXFile(Filepath.replace(".csv", ".xlsx"), list);
	            for (int r = 0; r < list.size(); r++) {
	                for (int c = 0; c < list.get(r).length; c++) {
	                    String cell = list.get(r)[c];
	                    System.out.print(cell + "\t");
	                }
	                System.out.print("\n");
	            }*/
            }
            Thread.sleep(1000);
          }
        }
    }
}