

package com.sertek.util;

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
/**
 * �ݭn mail.jar, activation.jar
 * webSphere 3.5 �õL���ѡA�Цۦ�W������A�B���n�N jar �Ѷ}�ө��
 * servlets ���覡�Ӵ��ѡA�]���o�G��jar�� �̭��� META-INF �O�ݭn���C
 * �ϥνd��
 * SMTPMail mail = new SMTPMail("10.5.1.1");//10.5.1.1���l����A�� 
 * mail.setSender("���p�_");						//�H��̦W��
 *mail.setFrom("jason@my.com.tw");       //�H���mail
 *mail.addRecipient("Alex@my.com.tw");   //����̡A�@��u��H���@�H
 *mail.setSubject("test");                  //�D��
 *mail.setBody("just test it");             //���e
 *mail.addFile("c:\\jason.txt");            //���[��
 *mail.addFile("c:\\sla.xls");              //���[��
 *mail.SendMail();                          //�H�H
 * @author wangchi
 *
 */
public class SMTPMail
{
	
    public SMTPMail()
    {
        CharSet = "utf-8";
        BodyString = "";
        FromString = "test@kimo.com.tw";
        Sender = "";
        TimeOut = "1200000";
        PortNo = 25;
        AttachFile = new Vector();
        setHostName("kimo.com.tw");
    }
   
    public SMTPMail(String s)
    {
        CharSet = "utf-8";
        BodyString = "";
        FromString = "test@kimo.com.tw";
        Sender = "";
        TimeOut = "1200000";
        PortNo = 25;
        AttachFile = new Vector();
        setHostName(s);
    }

    public SMTPMail(String s, int i)
    {
        CharSet = "utf-8";
        BodyString = "";
        FromString = "test@kimo.com.tw";
        Sender = "";
        TimeOut = "1200000";
        PortNo = 25;
        AttachFile = new Vector();
        PortNo = i;
        setHostName(s);
    }
    public SMTPMail(String s, String userid,String passwd)
    {
        CharSet = "utf-8";
        BodyString = "";
        FromString = "test@kimo.com.tw";
        Sender = "";
        this.userid=userid;
        this.passwd = passwd;
        TimeOut = "1200000";
        PortNo = 25;
        AttachFile = new Vector();
        setHostName(s,userid,passwd);
    }
    /*
    public void ssl(boolean ssl){
    	this.isSsl = ssl;
    }
    */
    public SMTPMail(String s, String userid,String passwd,int port)
    {
        CharSet = "utf-8";
        BodyString = "";
        FromString = "test@kimo.com.tw";
        Sender = "";
        TimeOut = "1200000";
        PortNo = port;
        this.userid=userid;
        this.passwd = passwd;
        AttachFile = new Vector();
        setHostName(s,userid,passwd);
    }
    public SMTPMail(String s, String userid,String passwd,int port,boolean isSsl)
    {
        CharSet = "utf-8";
        BodyString = "";
        FromString = "test@kimo.com.tw";
        Sender = "";
        TimeOut = "1200000";
        PortNo = port;
        this.userid=userid;
        this.passwd = passwd;
        this.isSsl = isSsl;
        AttachFile = new Vector();
        setHostName(s,userid,passwd);
    }
    /**
     * �ǰeHTML �榡��Email
     * @param s HTML�ɮצW��
     * @return
     */

    public boolean SendHTMLFile(String s)
    {
        String s1 = getTextFileData(s);
        System.out.println("html_data =  " + s1);
        return SendHTMLMail(s1);
    }
    /**
     * �ǰeHTML �榡��Email
     * @param s	HTML�ɮצW��	
     * @param 	charset �s�X�覡
     * @return
     */
    public boolean SendHTMLFile(String s, String charset)
    {
        String s2 = "";
        CharSet = charset;
        s2 = getTextFileData(s);
        System.out.println("html_data =  " + s2);
        return SendHTMLMail(s2);
    }
    /**
     * �ߧY�ǰeHTML�榡��mail
     * @param s
     * @return
     */
    public boolean SendHTMLMail(String s)
    {
        return SendHTMLMail(s,true);
    }
    
    /**
     * �ǰeHTML�榡��mail
     * @param s ���e
     * @param send �O�_�߸ӶǰeMail �Ature=�ߨ�e�H  , false=�Y�S�n�߸ӭn�e�A����ЦA�I�sSend�禡�A 
     * @return
     */
    public boolean SendHTMLMail(String s,boolean send)
    {
        try
        {
            InternetAddress internetaddress = null;
            try
            {
                if(Sender == null)
                    internetaddress = new InternetAddress(FromString);
                else
                    internetaddress = new InternetAddress(FromString, Sender, CharSet);
            }
            catch(Exception _ex)
            {
            	err = _ex.toString();
                return false;
            }
            message.setFrom(internetaddress);
            //System.out.println("from = " + FromString);
            message.setDataHandler(new DataHandler(s, "text/html;charset=" + CharSet));
            
            
            //�^��
            if (isReturnCase)
            	setReturnCase();
            
           if (send)
            	Transport.send(message);
            
            return true;
        }
        catch(MessagingException _ex)
        {
        	err = _ex.toString();
            return false;
        }
    }
    /**
     * �ǰemail
     * @return
     */
    public boolean Send(){
    		boolean retVal = true;
    	 try
         {
    		 Transport.send(message);
         }catch(MessagingException _ex){
        	 	_ex.printStackTrace();
        	 	err = _ex.toString();
        	 	retVal = false;
         
         }
         return retVal;
    }

    public boolean SendMail()
    {
        try
        {
            InternetAddress internetaddress = null;
            try
            {
                if(Sender == null)
                    internetaddress = new InternetAddress(FromString);
                else
                    internetaddress = new InternetAddress(FromString, Sender, CharSet);
            }
            catch(Exception _ex)
            {
            	err = _ex.toString();
            	_ex.printStackTrace();
                return false;
            }
            message.setFrom(internetaddress);
            //System.out.println("from=" + FromString);
            MimeBodyPart mimebodypart = new MimeBodyPart();
            MimeMultipart mimemultipart = new MimeMultipart();
            mimebodypart.setText(BodyString, CharSet);
            //System.out.println("body=" + BodyString + " ;chartset=" + CharSet);
            mimemultipart.addBodyPart(mimebodypart);
            if(!AttachFile.isEmpty())
            {
                for(int i = 0; i < AttachFile.size(); i++)
                {
                    MimeBodyPart mimebodypart1 = new MimeBodyPart();
                    File file = new File((String)AttachFile.elementAt(i));
                    if(!file.exists())
                    {
                        System.out.println((String)AttachFile.elementAt(i) + " NOT Found.");
                        return false;
                    }
                    String s = "";
                    try
                    {
                        Object obj = null;
                        s = MimeUtility.encodeText(file.getName(), CharSet, "Q");
                    }
                    catch(UnsupportedEncodingException _ex)
                    {
                    	err = _ex.toString();
                    	_ex.printStackTrace();
                        return false;
                    }
                    FileDataSource filedatasource = new FileDataSource((String)AttachFile.elementAt(i));
                    mimebodypart1.setDataHandler(new DataHandler(filedatasource));
                    mimebodypart1.setFileName(s);
                    mimemultipart.addBodyPart(mimebodypart1);
                    //System.out.println("attachFile=" + (String)AttachFile.elementAt(i));
                    //System.out.println("FileName=" + file.getName());
                }

            }
            message.setContent(mimemultipart);
            
            
            //�^��
            if (isReturnCase)
            	setReturnCase();
            
            Transport.send(message);
            return true;
        }
        catch(MessagingException _ex)
        {
        	err = _ex.toString();
        	_ex.printStackTrace();	
            return false;
        }
    }
    /**
     * �W�[�K�󦬥��
     * @param s �K�󦬥��
     * @return
     */
    public boolean addBCC(String bcc)
    {
        System.out.println("addBCC=" + bcc);
        try
        {
            message.addRecipient(javax.mail.Message.RecipientType.BCC, new InternetAddress(bcc));
            sendCount ++;
            return true;
        }
        catch(MessagingException _ex)
        {
        	err = _ex.toString();	
            return false;
        }
    }
    /**
     * �W�[�K�󦬥��
     * @param s �K�󦬥��address
     * @return
     */
    public boolean addCC(String cc)
    {
        System.out.println("addCC=" + cc);
        try
        {
            message.addRecipient(javax.mail.Message.RecipientType.CC, new InternetAddress(cc));
            sendCount ++;
            return true;
        }
        catch(MessagingException _ex)
        {
        	err = _ex.toString();	
            return false;
        }
    }
    /**
     * �W�[�����ɮצW��
     * @param s
     */
    public void addFile(String s)
    {
        AttachFile.addElement(s);
    }

    public void addHeader(String s, String s1)
        throws MessagingException
    {
        message.addHeader(s, s1);
    }
    /**
     * �W�[�����
     * @param s �����mail
     * @return
     */
    
    public boolean addRecipient(String s)
    {
        //System.out.println("addRecipient=" + s);
        try
        {
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(s));
            sendCount ++;
            return true;
        }
        catch(MessagingException _ex)
        {
        	err = _ex.toString();	
            return false;
        }
    }
    
    /**
     * �W�[�����
     * @param s �����mail
     * @param name ����̦W��
     * @return
     */
    
    public boolean addRecipient(String s,String name)
    {
        //System.out.println("addRecipient=" + s);
        try
        {
        	
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(s,name,"utf-8"));
            sendCount ++;
            return true;
        }
        catch(MessagingException _ex)
        {
        	err = _ex.toString();	
            return false;
        }catch(Exception e){
        	err = e.toString();	
            return false;
        }
    }

    private String getTextFileData(String s)
    {
        String s1 = "";
        StringBuffer stringbuffer = new StringBuffer();
        try
        {
            FileReader filereader = new FileReader(s);
            BufferedReader bufferedreader = new BufferedReader(filereader);
            String s2;
            while((s2 = bufferedreader.readLine()) != null) 
            {
                stringbuffer.append(s2);
                stringbuffer.append("\n");
            }
            //System.out.println("text=" + stringbuffer.toString());
            return stringbuffer.toString();
        }
        catch(IOException _ex)
        {
        	err = _ex.toString();
            return "";
        }
    }
    /**
     * �]�w�D���r���e
     * @param s �D���r���e
     */
    public void setBody(String s)
    {
        BodyString = s;
    }
    /**
     * �]�w�D���r���e
     * @param s	�D���r���e
     * @param charset �s�X�覡
     */
    public void setBody(String s, String charset)
    {
        BodyString = s;
        CharSet = charset;
    }
    /**
     * �]�w�s�X�y��
     * @param charset
     */
    public void setCharSet(String charset)
    {
        CharSet = charset;
        //System.out.println("setCharSet=" + charset);
    }
    /**
     * �]�w�H���mail
     * @param from �H���mail
     */
    public void setFrom(String from)
    {
        FromString = from;
    }
    /**
     * �]�wMail Server ��m
     * @param s mailServer �� IP �� dns
     */
    public void setHostName(String s)
    {
    	setHostName(s,"","");
    }
    /**
     * �]�wMail Server ��m
     * @param s
     * @param userid 
     * @param passwd
     */
    public  void setHostName(String s,String userid,String passwd)
    {
        
        properties.put("mail.smtp.host", s);
        properties.put("mail.smtp.timeout", TimeOut);
        properties.put("mail.smtp.port", String.valueOf( PortNo ));
        //System.out.println("PortNo="+PortNo);
        Session session = null;
        if (!userid.equals("")){
        	if (isSsl){
        		System.out.println("use ssl...............");	
        		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        		properties.put("mail.smtp.socketFactory.port", String.valueOf(PortNo));
        		
        		properties.put("mail.smtp.socketFactory.fallback", "false");
        		//properties.put("mail.smtps.auth", "true");
        	}else{
        		//properties.put("mail.smtp.auth", "true");
        	}
        	properties.put("mail.smtp.auth", "true");
        	javax.mail.Authenticator smtpAuthenticator =
                new SMTPAuthenticator(userid, passwd);
        	 
	        //properties.put("mail.send.user", userid);
	        //properties.put("mail.send.password", passwd);
        	session = Session.getInstance(properties, smtpAuthenticator);
        }else{
        	session = Session.getInstance(properties, null);
        }
        
        
       
        
        
       // session.setDebug(true);
        message = new MimeMessage(session);
        
    }
    /**
     * �]�w�H��̦W��
     * @param s
     */
    public void setSender(String s)
    {
        Sender = s;
    }
    /**
     * �]�w�D�����e
     * @param s �D�����e
     * @return
     */
    public boolean setSubject(String s)
    {
        //System.out.println("setSubject=" + s);
        try
        {
            message.setSubject(s, CharSet);
            return true;
        }
        catch(MessagingException _ex)
        {
        	err = _ex.toString();
            return false;
        }
    }
    /**
     * �]�w�D�����e
     * @param s �D�����e
     * @param charset �s�X�覡
     * @return
     */
    public boolean setSubject(String s, String charset)
    {
        //System.out.println("setSubject=" + s);
        try
        {
            message.setSubject(s, charset);
            return true;
        }
        catch(MessagingException _ex)
        {
        	err = _ex.toString();	
            return false;
        }
    }
    /**
     * �]�w SMTP port number
     * @param port
     */
    public void setSMTPPort(int port){
    	this.PortNo=port;
    	System.out.println("setSMTPPort =" + port);
    	properties.put("mail.smtp.port", new Integer(PortNo));
    }
    /**
     * �]�w�ǰe�u������
     * 
     * @param lv  1:�̰��u��  2:�@�� 3:�C
     */
    public void setPriority(int lv) {
    	try{
	    	if (lv==1){
	            message.addHeader("X-Priority","1");
	            message.addHeader("x-msmail-priority", "high"); 
	    	}else if (lv==2){
	    		message.addHeader("X-Priority","2");
	            message.addHeader("x-msmail-priority", "Normal"); 
	    	}else{
	    		message.addHeader("X-Priority","3");
	            message.addHeader("x-msmail-priority", "Normal"); 
	    	}
    	}catch( MessagingException e) {
    		err = e.toString();	
    	}	
    }
    
    /*
     * �O�_�n�^��( �u��outlook �~���� )
     */
    public void returnCase(boolean isReturnCase) {
    	
    		this.isReturnCase = isReturnCase;
    	
    }
    /**
     * ���o�@�H���h�֤H
     * @return
     */
    public int getSendCount(){
    	return sendCount;
    }
    /**
     * 
     *
     */
    private void setReturnCase(){
    		//msg.setHeader("Disposition-Notification-To", "aabb <xxx@ddt.com.tw>");
    	try{
	    	 if(Sender == null || Sender.equals(""))
	    		 message.addHeader("Disposition-Notification-To", FromString +" " + "<" + FromString + ">");
	    	 else
	    		 message.addHeader("Disposition-Notification-To", Sender +" " + "<" + FromString + ">"); 
	    	 
    	}catch( MessagingException e) {
    		err = e.toString();	
    	}
    }
    /**
     * ���o�̫᪺���~�T��
     *
     */
    public String getLastMessage(){
    	return err;
    }
    private final String HOST_NAME = "kimo.com.tw";
    private String CharSet;
    private String BodyString;
    private String FromString;
    private String Sender;
    private String TimeOut;
    private int PortNo;
    private Vector AttachFile;
    private MimeMessage message;
    private String err = "";
    private boolean isReturnCase = false;		//�O�_�n�^��
    private boolean isSsl = false;
    private String userid = "";
    private String passwd = "";
    private java.util.Properties properties = System.getProperties();
    private int sendCount = 0;
}
