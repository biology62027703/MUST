package com.sertek.commons;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.sertek.commons.mail.MAIL_DEFAULT;
import com.sertek.commons.mail.MAIL_RCPT;
import com.sertek.commons.mail.MAIL_TEMPLATE;
import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.sys.StaticObj;
import com.sertek.util.CheckObject;
import com.sertek.util.SMTPMail;

public class SendMail {

	private Logger logger = Logger.getLogger(this.getClass());	
	private CheckObject check = new CheckObject();
	private HashMap param = new HashMap();	
	private SqlDBUtility sqlDBUtility = null;
	private String smtp_host = "";
	private String smtp_port = "";
	private String smtp_auth = "";
	private String smtp_usrid = "";
	private String smtp_pswd = "";
	private String smtp_ssl = "";
	private String from_mail = "";

	public SendMail(HashMap param) {
		this.param = param;
		run();
	}

	public SendMail(HashMap param, SqlDBUtility sqlDBUtility) {
		this.param = param;
		this.sqlDBUtility = sqlDBUtility;
		run();
	}

	public void run() {
		try {
			
			if( StaticObj.isSMTPonPower() ) {
				logger.info("使用第一組SMTP");
				smtp_host = StaticObj.getS08("MAIL", "SMTP_IP");
				smtp_port = StaticObj.getS08("MAIL", "SMTP_PORT");
				smtp_auth = StaticObj.getS08("MAIL", "SMTP_AUTH");
				smtp_usrid = StaticObj.getS08("MAIL", "SMTP_USRID");
				smtp_pswd = StaticObj.getS08("MAIL", "SMTP_PSWD");
				smtp_ssl = StaticObj.getS08("MAIL", "SMTP_SSL");
				from_mail = StaticObj.getS08("MAIL", "FROM_MAIL_ADDR");
			}else{
				logger.info("使用第二組SMTP");
				smtp_host = StaticObj.getS08("MAIL2", "SMTP_IP");
				smtp_port = StaticObj.getS08("MAIL2", "SMTP_PORT");
				smtp_auth = StaticObj.getS08("MAIL2", "SMTP_AUTH");
				smtp_usrid = StaticObj.getS08("MAIL2", "SMTP_USRID");
				smtp_pswd = StaticObj.getS08("MAIL2", "SMTP_PSWD");
				smtp_ssl = StaticObj.getS08("MAIL2", "SMTP_SSL");
				from_mail = StaticObj.getS08("MAIL2", "FROM_MAIL_ADDR");
			}
			
			String mail_id = check.checkNull(param.get("mail_id"), "").toString();
			logger.info("mail_id = " + mail_id);

			MAIL_TEMPLATE mail = null;
			
			if (!mail_id.equals("")) {
				try {
					Class clazz = Class.forName(mail_id);
					// 指定參數型態
					Class[] params = { HashMap.class };
					Constructor constructor = clazz.getConstructor(params);
					// 指定參數內容
					Object[] paramObjs = { param };
					// 實例化
					mail = (MAIL_TEMPLATE) constructor.newInstance(paramObjs);
				} catch (Exception e) {
					logger.error(e, e);
					mail = new MAIL_DEFAULT(param);
				}
			} else {
				mail = new MAIL_DEFAULT(param);
			}
			
			int port = 25;
			try {
				port = Integer.parseInt(smtp_port);
			} catch (Exception ignored) {
			}

			List rcptList = new ArrayList();
			
			if (param.get("rcptList") != null) {
				rcptList = (List) param.get("rcptList");
			}

			SMTPMail emailer = null;
			for (int i = 0; i < rcptList.size(); i++) {
				MAIL_RCPT rcpt = (MAIL_RCPT)rcptList.get(i);
				param.put("rcpt_name", rcpt.getName());
				
				if ("0".equals(smtp_auth)) {
					emailer = new SMTPMail(smtp_host, "", "", port);
				} else {
					emailer = new SMTPMail(smtp_host, smtp_usrid, smtp_pswd, port, "Y".equals(smtp_ssl));
				}
				emailer.setSender("司法院線上起訴及書狀傳送作業平台");
				emailer.setFrom(from_mail);
				emailer.setSubject(mail.getMailSubject());
				emailer.addRecipient(rcpt.getAddr());
				boolean success = emailer.SendHTMLMail(mail.getMailContent());				
				
				logger.info("[mail_id=" + mail.getClass().getSimpleName() + ", doc_no=" + check.checkNull(param.get("doc_no"), "") + ", name=" + rcpt.getName() + ", email=" + rcpt.getAddr() + ", success=" + success + "]");
				
				if (!success) {
					logger.info("smtp_host = " + smtp_host);
					logger.info("smtp_port = " + smtp_port);
					logger.info("smtp_auth = " + smtp_auth);
					logger.info("smtp_ssl = " + smtp_ssl);
					logger.info("from_mail = " + from_mail);
				}
				
				if( sqlDBUtility!=null ) {
					MailObj.writeSendMaillog(sqlDBUtility, param, rcpt, mail, success);
				}
				// logger.info("errmsg = " + emailer.getLastMessage());
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
}