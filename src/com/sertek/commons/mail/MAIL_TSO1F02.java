package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.sys.StaticObj;

public class MAIL_TSO1F02 extends MAIL_TEMPLATE {

	public MAIL_TSO1F02(HashMap param) throws Exception {
		super(param);
	}

	public String getMailSubject() {
		//String mail_subject = "遞狀流水號(" + check.checkNull(param.get("doc_no"), "") + ")案件資料通知";
		String mail_subject = "《司法院線上起訴及書狀傳送作業平台》第三人參加通知信(案號:"+check.checkNull(param.get("CRMYY"), "").toString()+"年"+check.checkNull(param.get("CRMID"), "").toString()+"字"+check.checkNull(param.get("CRMNO"), "").toString()+"號)";
		if( check.checkNull(param.get("CRMYY"), "").toString().equals("") )
			mail_subject = "《司法院線上起訴及書狀傳送作業平台》第三人參加通知信";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {	
			mail_content = MailHelper.getMailTemplate("TSO1F02.html");
			param.put("CLNM", check.checkNull(param.get("rcpt_name"), "").toString());
			mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}