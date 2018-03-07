package com.sertek.form;
import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sertek.util.CsvUtil;
import com.sertek.util.ReadWriteExcel;
import com.sertek.util.util_date;

import java.io.File;
import java.util.HashMap;
import java.util.List;
	 
@MultipartConfig(location = "D:\\")
@SuppressWarnings({ "rawtypes", "unused", "unchecked" })

public class UploadFilesController extends BaseAbstractCommandController {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{		String folder="D:\\EXCEL_FILES";
		System.out.println("folder:"+folder);
		fileUpload.uploadFile(folder);		
		
		/*for( int i=0;i<fileUpload.getUploadCount();i++ ) {				
			
			if( !getFileNm(fileUpload.getFileUploadInfo(i).srcFile.getName()).equals("") ) {
				System.out.println("filenm:"+getFileNm(fileUpload.getFileUploadInfo(i).srcFile.getName()));		        
			}
		}*/
		//ReadWriteExcel ReadWriteExcel = new ReadWriteExcel();
		//ReadWriteExcel.setKeys(new String[] {"dist_no","chinese_name","ip_base_no"});
		//List ls = ReadWriteExcel.readXLSXFile(folder);
		//System.out.println("資料:"+ls);
		responseBean.setGotoUrl("FILESUPLOAD.html");
		//responseBean.setAjaxData("OK");	
	}

	protected String getFileNm(String filenm) {
		if( filenm.lastIndexOf("\\")>-1 )
			filenm = filenm.substring(filenm.lastIndexOf("\\")+1);
		if( filenm.lastIndexOf("/")>-1 )
			filenm = filenm.substring(filenm.lastIndexOf("/")+1);
		return filenm;
	}
	
	public void doCsv(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean)throws Exception {
		String[] filenames;
		File filefolder = new File("D:\\google相關\\2017Q2\\VideoReport_YouTube_must_TW_2017Q2_ugc_music_partner_20170919232749911923_split_13\\");
		//String filefolder = "D:\\google相關\\2017Q2\\VideoReport_YouTube_must_TW_2017Q2_ugc_music_partner_20170919232749911923_split_12\\VideoReport_YouTube_must_TW_2017Q2_ugc_music_partner_20170919232749911923_1.csv";
		String fullpath = filefolder.getAbsolutePath();
		util_date ud = new util_date();
		HashMap hm = new HashMap();
		if(filefolder.isDirectory()){
			filenames=filefolder.list();
			for (int i = 0 ; i < filenames.length ; i++){         
				File Filepath = new File(fullpath + "\\" + filenames[i]);
				if(Filepath.isDirectory()){
				  System.out.println("目錄:" + filenames[i]);
				} else {
				  /*//String filepath = "D:\\google相關\\2017Q2\\VideoReport_YouTube_must_TW_2017Q2_ugc_music_partner_20170919232749911923_split_12\\VideoReport_YouTube_must_TW_2017Q2_ugc_music_partner_20170919232749911923_1.csv";
				  System.out.println("檔案:" + Filepath.toString());
				  CsvUtil su = new CsvUtil();
				  List<String[]> list = su.readCsv(Filepath.toString());
				  ReadWriteExcel ReadWriteExcel = new ReadWriteExcel();
				  //System.out.println("----------");
				  ReadWriteExcel.writeXLSXFile(Filepath.toString().replace(".csv", ".xlsx"), list);*/
					CsvUtil su = new CsvUtil();
					List<String[]> list = su.readCsv(Filepath.toString());
					/*ReadWriteExcel ReadWriteExcel = new ReadWriteExcel();
					System.out.println("----------");
					ReadWriteExcel.writeXLSXFile(Filepath.replace(".csv", ".xlsx"), list);*/
						for (int r = 0; r < list.size(); r++) {
							hm.put("TVChannel", "GOOGLE");
							hm.put("Channel_Kind", "廣播");
							hm.put("Kind", "廣播/格式一");
							hm.put("FileName", Filepath.toString());
							hm.put("Imp_YYYYMMDD", ud.nowWDateTime().substring(0, 8));
							hm.put("Sheet", "Sheet1");
							hm.put("O_Date", ud.nowWDateTime().substring(0, 6)+"01");
							hm.put("TVChannel", "GOOGLE");
							String[] colunm={"Program","Song",""};
						    for (int c = 0; c < list.get(r).length; c++) {
						        String cell = list.get(r)[c];
						        System.out.print(cell + "\t");
						    }
						    System.out.print("\n");
						}
				  }
			  }
		}
	}
}

