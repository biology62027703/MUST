package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.sys.StaticObj;

public class MAIL_TSO1F01 extends MAIL_TEMPLATE {

	public MAIL_TSO1F01(HashMap param) throws Exception {
		super(param);
	}

	public String getMailSubject() {
		//String mail_subject = "遞狀流水號(" + check.checkNull(param.get("doc_no"), "") + ")案件資料通知";
		String mail_subject = "《司法院線上起訴及書狀傳送作業平台》訴訟文書送達通知信(案號:"+check.checkNull(param.get("CRMYY"), "").toString()+"年"+check.checkNull(param.get("CRMID"), "").toString()+"字"+check.checkNull(param.get("CRMNO"), "").toString()+"號)";
		if( check.checkNull(param.get("CRMYY"), "").toString().equals("") )
			mail_subject = "《司法院線上起訴及書狀傳送作業平台》訴訟文書送達通知信";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {	
			if( param.get("ODOC_NO").toString().equals("") )
				mail_content = MailHelper.getMailTemplate("TSO1F01.html");
			else
				mail_content = MailHelper.getMailTemplate("TSO1F0101.html");
			param.put("CLNM", check.checkNull(param.get("rcpt_name"), "").toString());
			mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}