package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.commons.mail.MAIL_TEMPLATE;
import com.sertek.sys.StaticObj;

public class MAIL_FORGETPSWD extends MAIL_TEMPLATE {
	
	public MAIL_FORGETPSWD(HashMap param) {
		super(param);
	}

	public String getMailSubject() {
		//String mail_subject = "遞狀流水號(" + check.checkNull(param.get("doc_no"), "") + ")";
		String mail_subject = "《司法院線上起訴及書狀傳送作業平台》密碼通知信";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {				
				mail_content = MailHelper.getMailTemplate("FORGETPASSWORD.html");
				mail_content = ut.StrTran(mail_content, "@USRID@", check.checkNull(param.get("usrid"), "").toString());
				mail_content = ut.StrTran(mail_content, "@USRNM@", check.checkNull(param.get("usrnm"), "").toString());
				mail_content = ut.StrTran(mail_content, "@RESETPSWD@", check.checkNull(param.get("resetpswd"), "").toString());
			//mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}