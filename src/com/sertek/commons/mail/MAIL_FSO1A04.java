package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.commons.mail.MAIL_TEMPLATE;
import com.sertek.sys.StaticObj;

public class MAIL_FSO1A04 extends MAIL_TEMPLATE {

	public MAIL_FSO1A04(HashMap param) {
		super(param);
	}

	public String getMailSubject() {
		//String mail_subject = "»¼ª¬¬y¤ô¸¹(" + check.checkNull(param.get("doc_no"), "") + ")";
		String mail_subject = "";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {		
			if( param.get("ODOC_NO").toString().equals("") )
				mail_content = MailHelper.getMailTemplate("FSO1A04.html");
			else
				mail_content = MailHelper.getMailTemplate("FSO1A0401.html");
			param.put("CLNM", check.checkNull(param.get("rcpt_name"), "").toString());
			mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}