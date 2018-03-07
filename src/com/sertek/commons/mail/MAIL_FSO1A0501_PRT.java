package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.sys.StaticObj;

public class MAIL_FSO1A0501_PRT extends MAIL_TEMPLATE {

	public MAIL_FSO1A0501_PRT(HashMap param) throws Exception {
		super(param);
	}

	public String getMailSubject() {
		//String mail_subject = "遞狀流水號(" + check.checkNull(param.get("doc_no"), "") + ")線上列印繳費單";
		String mail_subject = "《司法院線上起訴及書狀傳送作業平台》線上列印繳費單通知信";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {
			mail_content = MailHelper.getMailTemplate("FSO1A0501_PRT.html");
			param.put("CLNM", check.checkNull(param.get("rcpt_name"), "").toString());
			mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}