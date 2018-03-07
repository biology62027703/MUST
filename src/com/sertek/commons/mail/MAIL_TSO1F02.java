package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.sys.StaticObj;

public class MAIL_TSO1F02 extends MAIL_TEMPLATE {

	public MAIL_TSO1F02(HashMap param) throws Exception {
		super(param);
	}

	public String getMailSubject() {
		//String mail_subject = "�����y����(" + check.checkNull(param.get("doc_no"), "") + ")�ץ��Ƴq��";
		String mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n�ĤT�H�ѥ[�q���H(�׸�:"+check.checkNull(param.get("CRMYY"), "").toString()+"�~"+check.checkNull(param.get("CRMID"), "").toString()+"�r"+check.checkNull(param.get("CRMNO"), "").toString()+"��)";
		if( check.checkNull(param.get("CRMYY"), "").toString().equals("") )
			mail_subject = "�m�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x�n�ĤT�H�ѥ[�q���H";
		return mail_subject;
	}

	public String getMailContent() {
		String mail_content = "";
		try {	
			mail_content = MailHelper.getMailTemplate("TSO1F02.html");
			param.put("CLNM", check.checkNull(param.get("rcpt_name"), "").toString());
			mail_content = this.Conversion(mail_content, param);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}