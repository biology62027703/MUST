package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.sys.StaticObj;

public class MAIL_TSO1B01 extends MAIL_TEMPLATE {

	public MAIL_TSO1B01(HashMap param) throws Exception {
		super(param);
	}

	public String getMailSubject() {
		String mail_subject = "案件資料整批傳送失敗通知";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {
			mail_content = MailHelper.getMailTemplate("TSO1B01.html");
			param.put("CLNM", check.checkNull(param.get("rcpt_name"), "").toString());
			mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}