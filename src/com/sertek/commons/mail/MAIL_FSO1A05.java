package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.commons.mail.MAIL_TEMPLATE;
import com.sertek.form.XDHelper;
import com.sertek.sys.StaticObj;

public class MAIL_FSO1A05 extends MAIL_TEMPLATE {

	public MAIL_FSO1A05(HashMap param) {
		super(param);
	}

	public String getMailSubject() {
		String acctype = check.checkNull(param.get("ACCTYPE"), "").toString();
		String mail_subject = "";
		if (XDHelper.ACCTYPE_4_CODE.equals(acctype)) {
			mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n�u�W�����b��ú�O�����q���H";
		} else if (XDHelper.ACCTYPE_3_CODE.equals(acctype)) {
			mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n�u�WWeb ATMú�O�����q���H";
		} else if (XDHelper.ACCTYPE_1_CODE.equals(acctype) || XDHelper.ACCTYPE_2_CODE.equals(acctype)) {
			mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n�u�WID+Accountú�O�����q���H";
		}
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {	
			String acctype = check.checkNull(param.get("ACCTYPE"), "").toString();
			if (XDHelper.ACCTYPE_4_CODE.equals(acctype)) {
				mail_content = MailHelper.getMailTemplate("FSO1A0501.html");
			} else if (XDHelper.ACCTYPE_3_CODE.equals(acctype)) {
				mail_content = MailHelper.getMailTemplate("FSO1A0502.html");
			} else if (XDHelper.ACCTYPE_1_CODE.equals(acctype) || XDHelper.ACCTYPE_2_CODE.equals(acctype)) {
				mail_content = MailHelper.getMailTemplate("FSO1A0503.html");
			}
			param.put("CLNM", check.checkNull(param.get("rcpt_name"), "").toString());
			mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}