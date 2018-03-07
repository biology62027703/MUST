package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.commons.mail.MAIL_TEMPLATE;

public class MAIL_DEFAULT extends MAIL_TEMPLATE {

	public MAIL_DEFAULT(HashMap param) {
		super(param);
	}

	public String getMailSubject() {
		String mail_subject = "»¼ª¬¬y¤ô¸¹(" + check.checkNull(param.get("doc_no"), "") + ")";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {
			mail_content = MailHelper.getMailTemplate("DEFAULT.html");
			mail_content = ut.StrTran(mail_content, "@CLNM@", check.checkNull(param.get("S_USRNM"), "").toString());
			mail_content = ut.StrTran(mail_content, "@DOC_NO@", check.checkNull(param.get("doc_no"), "").toString());
			mail_content = ut.StrTran(mail_content, "@RCVDT@", ud.formatCDateUnit(check.checkNull(param.get("rcvdt"), "").toString()));
			mail_content = ut.StrTran(mail_content, "@RCVTM@", ud.formatTimeUnit(check.checkNull(param.get("rcvtm"), "").toString()));
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}