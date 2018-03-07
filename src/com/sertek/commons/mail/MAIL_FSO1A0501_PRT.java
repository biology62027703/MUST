package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.sys.StaticObj;

public class MAIL_FSO1A0501_PRT extends MAIL_TEMPLATE {

	public MAIL_FSO1A0501_PRT(HashMap param) throws Exception {
		super(param);
	}

	public String getMailSubject() {
		//String mail_subject = "�����y����(" + check.checkNull(param.get("doc_no"), "") + ")�u�W�C�Lú�O��";
		String mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n�u�W�C�Lú�O��q���H";
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