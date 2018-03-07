

package com.sertek.util;

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
/**
 * 需要 mail.jar, activation.jar
 * webSphere 3.5 並無提供，請自行上網抓取，且不要將 jar 解開來放到
 * servlets 的方式來提供，因為這二個jar檔 裡面的 META-INF 是需要的。
 * 使用範例
 * SMTPMail mail = new SMTPMail("10.5.1.1");//10.5.1.1為郵件伺服器 
 * mail.setSender("王小奇");						//寄件者名稱
 *mail.setFrom("jason@my.com.tw");       //寄件者mail
 *mail.addRecipient("Alex@my.com.tw");   //收件者，一行只能寄給一人
 *mail.setSubject("test");                  //主旨
 *mail.setBody("just test it");             //內容
 *mail.addFile("c:\\jason.txt");            //附加檔
 *mail.addFile("c:\\sla.xls");              //附加檔
 *mail.SendMail();                          //寄信
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
     * 傳送HTML 格式的Email
     * @param s HTML檔案名稱
     * @return
     */

    public boolean SendHTMLFile(String s)
    {
        String s1 = getTextFileData(s);
        System.out.println("html_data =  " + s1);
        return SendHTMLMail(s1);
    }
    /**
     * 傳送HTML 格式的Email
     * @param s	HTML檔案名稱	
     * @param 	charset 編碼方式
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
     * 立即傳送HTML格式的mail
     * @param s
     * @return
     */
    public boolean SendHTMLMail(String s)
    {
        return SendHTMLMail(s,true);
    }
    
    /**
     * 傳送HTML格式的mail
     * @param s 內容
     * @param send 是否立該傳送Mail ，ture=立刻送信  , false=若沒要立該要送，之後請再呼叫Send函式， 
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
            
            
            //回條
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
     * 傳送mail
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
            
            
            //回條
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
     * 增加密件收件者
     * @param s 密件收件者
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
     * 增加密件收件者
     * @param s 密件收件者address
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
     * 增加附屬檔案名稱
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
     * 增加收件者
     * @param s 收件者mail
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
     * 增加收件者
     * @param s 收件者mail
     * @param name 收件者名稱
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
     * 設定主體文字內容
     * @param s 主體文字內容
     */
    public void setBody(String s)
    {
        BodyString = s;
    }
    /**
     * 設定主體文字內容
     * @param s	主體文字內容
     * @param charset 編碼方式
     */
    public void setBody(String s, String charset)
    {
        BodyString = s;
        CharSet = charset;
    }
    /**
     * 設定編碼語言
     * @param charset
     */
    public void setCharSet(String charset)
    {
        CharSet = charset;
        //System.out.println("setCharSet=" + charset);
    }
    /**
     * 設定寄件者mail
     * @param from 寄件者mail
     */
    public void setFrom(String from)
    {
        FromString = from;
    }
    /**
     * 設定Mail Server 位置
     * @param s mailServer 的 IP 或 dns
     */
    public void setHostName(String s)
    {
    	setHostName(s,"","");
    }
    /**
     * 設定Mail Server 位置
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
     * 設定寄件者名稱
     * @param s
     */
    public void setSender(String s)
    {
        Sender = s;
    }
    /**
     * 設定主旨內容
     * @param s 主旨內容
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
     * 設定主旨內容
     * @param s 主旨內容
     * @param charset 編碼方式
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
     * 設定 SMTP port number
     * @param port
     */
    public void setSMTPPort(int port){
    	this.PortNo=port;
    	System.out.println("setSMTPPort =" + port);
    	properties.put("mail.smtp.port", new Integer(PortNo));
    }
    /**
     * 設定傳送優先等級
     * 
     * @param lv  1:最高優先  2:一般 3:低
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
     * 是否要回條( 只有outlook 才有用 )
     */
    public void returnCase(boolean isReturnCase) {
    	
    		this.isReturnCase = isReturnCase;
    	
    }
    /**
     * 取得共寄給多少人
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
     * 取得最後的錯誤訊息
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
    private boolean isReturnCase = false;		//是否要回條
    private boolean isSsl = false;
    private String userid = "";
    private String passwd = "";
    private java.util.Properties properties = System.getProperties();
    private int sendCount = 0;
}
