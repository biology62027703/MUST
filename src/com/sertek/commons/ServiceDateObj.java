package com.sertek.commons;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.sys.Project;
import com.sertek.sys.StaticObj;
import com.sertek.sys.sys_User;
import com.sertek.table.sndSO2Clog;
import com.sertek.util.CheckObject;
import com.sertek.util.util_date;
import com.sertek.util.utility;
import com.sertek.table.C60;

public class ServiceDateObj {

	private static Logger logger = Logger.getLogger(MailHelper.class);	
	private static CheckObject check = new CheckObject();
	private static util_date ud = new util_date();
		
	public static void updateC60_ARG_ARRDT(SqlDBUtility sqlDBUtility, String doc_no) {
		
		//更新C60_AGR
		HashMap form = getData(sqlDBUtility, doc_no);
		form.put("nowdt", ud.nowCDate());
		form.put("nowtm", ud.nowTime());
		
		if( form.get("have").toString().equals("Y") ) {
			
			//將C61
			if( form.get("odoc_no").toString().equals("") ) {
				
				//先刪除C60_AGR_TMP
				sqlDBUtility.update("C60_AGR.deleteC60_AGR_TMP", form);
				
				//再寫入C60_AGR_TMP
				sqlDBUtility.update("C60_AGR.insertC60_AGR_TMP", form);
				
				//處理解除委任的資料
				sqlDBUtility.update("C60_AGR.updateC60_AGR_DELDTTM", form);
				
				//再寫入C60_AGR
				sqlDBUtility.update("C60_AGR.insertC60_AGR", form);				
				
				//更新C60_AGR.AGRDT
				sqlDBUtility.update("C60_AGR.updateC60_AGR_AGRDTTM", form);
				
			}
			
			//拿odoc_no去insert c60_arrdt
			if( !form.get("odoc_no").toString().equals("") ) {
				form.put("doc_no", form.get("odoc_no").toString());
			}
			sqlDBUtility.update("C60_AGR.insertC60_ARRDT", form);
			
		}
		
	}
	
	public static HashMap getData(SqlDBUtility sqlDBUtility, String doc_no) {
		
		HashMap form = new HashMap();
		form.put("doc_no", doc_no);
		List ls = sqlDBUtility.queryForList("C60.queryByKey", form);
		if( ls.size()>0 ) {			
			form.put("c_no", ((C60)ls.get(0)).getC_no());
			form.put("doc_no", ((C60)ls.get(0)).getDoc_no());
			form.put("odoc_no", ((C60)ls.get(0)).getOdoc_no());
			form.put("rcvdt", ((C60)ls.get(0)).getRcvdt());
			form.put("rcvtm", ((C60)ls.get(0)).getRcvtm());
			form.put("dlv_dtm", ((C60)ls.get(0)).getDlv_dtm());
			form.put("have", "Y");			
		}else{
			form.put("have", "N");
		}
		return form;
	}
	
	
}