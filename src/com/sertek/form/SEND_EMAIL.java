package com.sertek.form;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;

import com.sertek.db.DBUtility;


public class SEND_EMAIL extends BaseAbstractCommandController{

	String mailserver = "192.168.1.19";
	String fromName="MUST會員通知";
	String from = "member@must.org.tw";
	//String recipient = "james.huang@must.org.tw,jamesaaa00205@gmail.com,william.huang@must.org.tw";
	String recipient = "";
	String subject = "";
	StringBuffer htmlCode ;
	//1為需驗證 0為不需驗證
	//final String emailPwd = "G16kHU4vo";
	final String emailPwd = "";
	final String emailauth = "1" ;
	final String emailAcct = "member";	
	
	SEND_EMAIL(String from,String fromName,String recipient,String subject,StringBuffer htmlCode ) throws IOException, ServletException, SQLException {
		this.recipient=recipient;
		this.from=from;
		this.fromName=fromName;
		this.subject=subject;
		this.htmlCode=htmlCode;
		sendEmail();
	}
	
	private void sendEmail () throws IOException, ServletException, SQLException {
		//type="Nmust";
		InternetAddress[] address = null;
		Session mailSession=null;	
		try {
			
		  	Properties props = new Properties(); 
		  	props.put("mail.host", mailserver);
			if("1".equals(emailauth)){
				props.put("mail.smtp.auth", "true");
			}
			props.put("mail.transport.protocol", "smtp");
			if ("1".equals(emailauth)) {
				mailSession = Session.getInstance(
					props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(emailAcct,emailPwd);
					}
				});
			}	   
			if("0".equals(emailauth)){
				mailSession = Session.getDefaultInstance(props,null); 
			}
		
		   //讀取html code
		   MimeBodyPart textPart = new MimeBodyPart();
		   StringBuffer html = new StringBuffer();
		   html.append(htmlCode.toString());
		   textPart.setContent(html.toString(), "text/html; charset=UTF-8"); 
		   
		   Multipart email = new MimeMultipart();
		   email.addBodyPart(textPart);
		   
		   // 產生整封 email 的主體 message
		   Message message = new MimeMessage(mailSession);
		   //設定郵件內文
		   message.setContent(email);
		   // 設定主旨
		   message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
		   //設定日期
		   message.setSentDate(new Date());
		   //設定寄件者
		   message.setFrom(new InternetAddress(from,fromName));
		   //設定收件者
		   address = InternetAddress.parse(recipient,false);
		   message.setRecipients(Message.RecipientType.TO, address);
		   //寄出郵件
		   Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException, ServletException, SQLException {
	
		//String emailPwd = "G16kHU4vo";
		//信任從19主機發出去的MAIL所以不用帶密碼
		//sendEmail("192.168.1.19", "member@must.org.tw", "MUST會員通知","James.huang@must.org.rw", "TEST", "member", "", 1);
	}
}