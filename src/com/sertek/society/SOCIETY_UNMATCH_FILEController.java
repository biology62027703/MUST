package com.sertek.society;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sertek.form.ResponseBean;
import com.sertek.form.BaseAbstractCommandController;

import com.sertek.file.FileCopy;

@SuppressWarnings({"rawtypes"})
public class SOCIETY_UNMATCH_FILEController extends BaseAbstractCommandController {
	private final static String Local_path="D:\\MUST_LIST"+File.separator+"song"+File.separator+"ftp_files";
	private final static String FTP_Server_path="\\\\ksc"+File.separator+"Society_ftp"+File.separator+"TEST"+File.separator;
	private static String kind = "Unidentified";
	private String MSG = "";
	FileCopy FileCopy = new FileCopy();
	public void insert(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{
		try{
			for (File f : new File(FTP_Server_path).listFiles()) {
				if(f.isDirectory()){
					File Directory_unidentrfied_path = new File(f.toString()+File.separator+kind);
					if (Directory_unidentrfied_path.exists()) {
						for (File f_client : new File(Local_path).listFiles()) {
							FileCopy.copyFiles(f_client.toString(),Directory_unidentrfied_path.toString()+File.separator+f_client.getName().toString(),false);							
						}
					}						
				}
			}
			MSG="DONE";
		} catch(Exception e) {
			MSG=e.toString();
		} finally {
			responseBean.setAjaxData(MSG);
		}
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response, HashMap form, ResponseBean responseBean) throws Exception{
		try{
			for (File f : new File(FTP_Server_path).listFiles()) {
				if(f.isDirectory()){
					File Directory_unidentrfied_path = new File(f.toString()+File.separator+kind);
					if (Directory_unidentrfied_path.exists()) {
						for (File f_client : new File(Local_path).listFiles()) {
							File ftp_file = new File(Directory_unidentrfied_path.toString()+File.separator+f_client.getName().toString());
							if(ftp_file.exists()) {
								FileCopy.delete(ftp_file.toString());
							}
						}
					}						
				}		        
			}
			MSG="DONE";
		} catch(Exception e) {
			MSG=e.toString();
		} finally {
			responseBean.setAjaxData(MSG);
		}
	}
	

}
