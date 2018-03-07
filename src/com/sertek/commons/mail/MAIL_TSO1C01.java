package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.sys.StaticObj;

public class MAIL_TSO1C01 extends MAIL_TEMPLATE {

	public MAIL_TSO1C01(HashMap param) throws Exception {
		super(param);
	}

	public String getMailSubject() {
		//String mail_subject = "�����y����(" + check.checkNull(param.get("doc_no"), "") + ")�ץ��Ƴq��";
		
		String mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n�ץ��Ƨ�s�q���H(�׸�:"+check.checkNull(param.get("CRMYY"), "").toString()+"�~"+check.checkNull(param.get("CRMID"), "").toString()+"�r"+check.checkNull(param.get("CRMNO"), "").toString()+"��)";
		if( check.checkNull(param.get("CRMYY"), "").toString().equals("") )
			mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n�ץ��Ƨ�s�q���H";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {
			if( param.get("ODOC_NO").toString().equals("") )
				mail_content = MailHelper.getMailTemplate("TSO1C01.html");
			else
				mail_content = MailHelper.getMailTemplate("TSO1C0101.html");
			param.put("CLNM", check.checkNull(param.get("rcpt_name"), "").toString());
			mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}