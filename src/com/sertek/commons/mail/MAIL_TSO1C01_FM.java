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
		//String mail_subject = "�����y����(" + check.checkNull(param.get("doc_no"), "") + ")";
		String mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n"+param.get("STATUS").toString()+"�q���H";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {			
			if( param.get("STATUS").toString().equals("�������") )
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