package com.sertek.commons;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.sertek.commons.mail.MAIL_RCPT;
import com.sertek.commons.mail.MAIL_TEMPLATE;
import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.sys.Project;
import com.sertek.sys.StaticObj;
import com.sertek.sys.sys_User;
import com.sertek.table.sndSO2Clog;
import com.sertek.util.CheckObject;
import com.sertek.util.util_File;
import com.sertek.util.util_date;
import com.sertek.util.utility;

public class MailObj {

	private static Logger logger = Logger.getLogger(MailHelper.class);	
	private static CheckObject check = new CheckObject();
	private static util_date ud = new util_date();
	
	
	
	private static HashMap tranMap1(HashMap param) {		
		param.put("CRTID", StaticObj.getCourt(param.get("CRTID").toString()));		
		param.put("SYS", StaticObj.getPhrase("SYS", param.get("SYS").toString()));		
		param.put("SYSKD", StaticObj.getPhrase("SYSKD", param.get("SYSKD").toString()));
		param.put("S_KD", StaticObj.getSuitkd(param.get("S_KD").toString()));	
		param.put("RCVDT", ud.formatCDate(param.get("RCVDT").toString(), "/"));
		param.put("RCVTM", ud.formatTime(param.get("RCVTM").toString(), ":"));
		param.put("C_DT", ud.formatCDate(param.get("C_DT").toString(), "/"));
		param.put("C_TM", ud.formatTime(param.get("C_TM").toString(), ":"));
		param.put("ORCVDT", ud.formatCDate(param.get("ORCVDT").toString(), "/"));
		param.put("ORCVTM", ud.formatTime(param.get("ORCVTM").toString(), ":"));
		param.put("CRMDT", ud.formatCDate(param.get("CRMDT").toString(), "/"));
		param.put("PEDT", ud.formatCDate(param.get("PEDT").toString(), "/"));		
		return param;
	}
	
	private static HashMap tranMap2(HashMap param) {		
		param.put("CRTID", StaticObj.getCourt(param.get("CRTID").toString()));		
		param.put("SYS", StaticObj.getPhrase("SYS", param.get("SYS").toString()));		
		param.put("COMDT", ud.formatCDate(param.get("COMDT").toString(), "/"));
		param.put("COMTM", ud.formatTime(param.get("COMTM").toString(), ":"));
		param.put("CDDT", ud.formatCDate(param.get("CDDT").toString(), "/"));	
		return param;
	}
	
	private static List getUsrnmEmails(List rcptList) {
		List ret = new ArrayList();
		String usrnm = "";
		String emails = "";
		
		for(int i=0;i<rcptList.size();i++) {
			
			MAIL_RCPT rcpt = (MAIL_RCPT)rcptList.get(i);
			if( usrnm.equals("") ) {
				usrnm = rcpt.getName();
				emails = rcpt.getAddr();
			}else if( !usrnm.equals(rcpt.getName()) || i==rcptList.size()-1) {
				HashMap item = new HashMap();				
				item.put("USRNM", usrnm);
				item.put("EMAILS", emails);
				ret.add(item);
				usrnm = "";
				emails = "";
			}else{
				emails += "\r\n" + rcpt.getAddr();
			}			
		}
		
		return ret;
	}
	
	public static void writeSendMaillog(SqlDBUtility sqlDBUtility, HashMap param, MAIL_RCPT rcpt, MAIL_TEMPLATE mail, boolean success) {
		
		if( param.get("doc_no")!=null ) {
			HashMap form = new HashMap();
			form.put("owner", "SO");
			form.put("doc_no", param.get("doc_no").toString());
			form.put("senddt", ud.nowCDate());
			form.put("sendtm", ud.nowTime());
			form.put("mailid", param.get("mail_id").toString());
			form.put("usrnm", rcpt.getName());
			form.put("emails", rcpt.getAddr());
			form.put("status", success ? "Y" : "N");
			sqlDBUtility.update("SYS.SENDMAILLOG_INSERT", form);
			
			if( !success ) {
				writeMailErr(form, mail);
			}
		}
	}
	
	private static void writeMailErr(HashMap form, MAIL_TEMPLATE mail) {
		
		if( !Project.getMailErrTxtDir().equals("") ) {
			HashMap obj = new HashMap();
			obj.put("html", mail.getMailContent());
			obj.put("subject", mail.getMailSubject());
			
			String txtfile = Project.getMailErrTxtDir() + form.get("owner").toString() + form.get("doc_no").toString() + form.get("senddt").toString() + form.get("sendtm").toString() + ".txt";
			util_File _F = new util_File();
			try {		
				_F.open(txtfile,"w");
	            _F.writeln(new Gson().toJson(obj));
			} catch(Exception e){
			} finally {
	         	_F.close();
			}
		}
		
	}
}