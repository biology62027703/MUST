package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.commons.mail.MAIL_TEMPLATE;
import com.sertek.sys.StaticObj;

public class MAIL_TSO1C01_FM extends MAIL_TEMPLATE {

	public MAIL_TSO1C01_FM(HashMap param) {
		super(param);
	}

	public String getMailSubject() {
		//String mail_subject = "遞狀流水號(" + check.checkNull(param.get("doc_no"), "") + ")";
		String mail_subject = "《司法院線上起訴及書狀傳送作業平台》"+param.get("STATUS").toString()+"通知信";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {			
			if( param.get("STATUS").toString().equals("移轉管轄") )
				mail_content = MailHelper.getMailTemplate("TSO1C01_FM1.html");
			else
				mail_content = MailHelper.getMailTemplate("TSO1C01_FM2.html");
			param.put("CLNM", check.checkNull(param.get("rcpt_name"), "").toString());
			mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}