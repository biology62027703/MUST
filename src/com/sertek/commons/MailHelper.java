package com.sertek.commons;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.sertek.commons.mail.MAIL_RCPT;
import com.sertek.sys.Project;
import com.sertek.sys.sys_User;
import com.sertek.util.CheckObject;
import com.sertek.util.utility;

public class MailHelper {

	private static Logger logger = Logger.getLogger(MailHelper.class);
	
	private static CheckObject check = new CheckObject();

	public static String getMailTemplate(String templateFile) {
		String charsetName = "UTF-8";
		StringBuffer template = new StringBuffer();
		String fileName = "";
		BufferedReader bufReader = null;
		InputStreamReader inputStreamReader = null;
		FileInputStream fileInputStream = null;
		try {
			fileName = Project.getMailTemplateDir() + templateFile;

			fileInputStream = new FileInputStream(fileName);
			inputStreamReader = new InputStreamReader(fileInputStream, charsetName);
			bufReader = new BufferedReader(inputStreamReader);
			String aLine = null;
			while ((aLine = bufReader.readLine()) != null) {
				template.append(aLine);
				template.append("\n");
			}
		} catch (Exception e) {
			logger.info("templateFile = " + fileName);
			logger.error(e, e);
		} finally {
			try {
				if (bufReader != null) {
					bufReader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (Exception ignored) {
			}
		}
		return template.toString();
	}
	
	public static List getRcpt(String name, String email) {
		List ls = new ArrayList();		
		String[] mails = getEmails(email);
		for(int i=0;i<mails.length;i++) {
			if( !mails[i].equals("") ) {
				MAIL_RCPT rcpt = new MAIL_RCPT();
				rcpt.setName(name);
				rcpt.setAddr(mails[i]);
				ls.add(rcpt);
			}
		}
		return ls;
	}	
	
	public static String[] getEmails(String email) {
		utility ut = new utility();
		String[] emails = null;
		try {
			emails = email.split("[；;,]"); // 分號或逗號
			for (int i = 0; i < emails.length; i++) {
				emails[i] = ut.Ltrim(emails[i].trim());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return emails;
	}
}