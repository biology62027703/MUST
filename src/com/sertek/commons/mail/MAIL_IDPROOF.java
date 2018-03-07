package com.sertek.commons.mail;

import java.util.HashMap;

import com.sertek.commons.MailHelper;
import com.sertek.commons.mail.MAIL_TEMPLATE;
import com.sertek.sys.StaticObj;

public class MAIL_IDPROOF extends MAIL_TEMPLATE {

	public MAIL_IDPROOF(HashMap param) {
		super(param);
	}

	public String getMailSubject() {
		return "";
	}

	public String getMailContent() {
		String mail_content = "";
		try {
			
			
			mail_content = MailHelper.getMailTemplate("IDPROOF.html");
			mail_content = ut.StrTran(mail_content, "@CRTID@", StaticObj.getCourt(check.checkNull(param.get("crtid"), "").toString()));
			mail_content = ut.StrTran(mail_content, "@SYS@", StaticObj.getPhrase("SYS", check.checkNull(param.get("sys"), "").toString()));
			mail_content = ut.StrTran(mail_content, "@USRID@", check.checkNull(param.get("usrid"), "").toString());
			mail_content = ut.StrTran(mail_content, "@USRNM@", check.checkNull(param.get("usrnm"), "").toString());
			mail_content = ut.StrTran(mail_content, "@TEL@", check.checkNull(param.get("tel"), "").toString());
			mail_content = ut.StrTran(mail_content, "@ADDR@", check.checkNull(param.get("addr"), "").toString());
			mail_content = ut.StrTran(mail_content, "@IDNO@", check.checkNull(param.get("idno"), "").toString());
			mail_content = ut.StrTran(mail_content, "@EMAIL@", check.checkNull(param.get("email"), "").toString());
			mail_content = ut.StrTran(mail_content, "@LAWOFFICE@", check.checkNull(param.get("lawoffice"), "").toString());
			mail_content = ut.StrTran(mail_content, "@NOWDT@", ud.formatCDate(ud.nowCDate(), "/"));
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
}