package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.commons.mail.MAIL_TEMPLATE;

public class MAIL_LOGINPSWDERROR extends MAIL_TEMPLATE {

	public MAIL_LOGINPSWDERROR(HashMap param) {
		super(param);
	}

	public String getMailSubject() {
		String mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n�b���K�X�n�J���ѳq���H";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {
			mail_content = MailHelper.getMailTemplate("LOGINPSWDERROR.html");
			mail_content = ut.StrTran(mail_content, "@USRID@", check.checkNull(param.get("usrid"), "").toString());
			mail_content = ut.StrTran(mail_content, "@USRNM@", check.checkNull(param.get("usrnm"), "").toString());
			mail_content = ut.StrTran(mail_content, "@USRIP@", check.checkNull(param.get("usrip"), "").toString());
			mail_content = ut.StrTran(mail_content, "@LDT@", ud.formatCDateUnit(check.checkNull(param.get("ldt"), "").toString()));
			mail_content = ut.StrTran(mail_content, "@LTM@", ud.formatTimeUnit(check.checkNull(param.get("ltm"), "").toString()));
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}