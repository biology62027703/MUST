package com.sertek.commons.mail;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sertek.util.CheckObject;
import com.sertek.util.util_date;
import com.sertek.util.utility;

public abstract class MAIL_TEMPLATE {
	
	public static String MAIL_DEFAULT = new String("com.sertek.commons.mail.MAIL_DEFAULT");
	
	public static String MAIL_FSO1A04 = new String("com.sertek.commons.mail.MAIL_FSO1A04");
	public static String MAIL_FSO1A05 = new String("com.sertek.commons.mail.MAIL_FSO1A05");
	public static String MAIL_FSO1A0501_PRT = new String("com.sertek.commons.mail.MAIL_FSO1A0501_PRT");	
	public static String MAIL_TSO1B01 = new String("com.sertek.commons.mail.MAIL_TSO1B01");
	public static String MAIL_TSO1C01 = new String("com.sertek.commons.mail.MAIL_TSO1C01");
	public static String MAIL_TSO1C01_FM = new String("com.sertek.commons.mail.MAIL_TSO1C01_FM");	
	public static String MAIL_TSO1F01 = new String("com.sertek.commons.mail.MAIL_TSO1F01");	
	public static String MAIL_TSO1F02 = new String("com.sertek.commons.mail.MAIL_TSO1F02");

	public static String MAIL_IDPROOF = new String("com.sertek.commons.mail.MAIL_IDPROOF");
	public static String MAIL_C60ATTPDF = new String("com.sertek.commons.mail.MAIL_C60ATTPDF");
	public static String MAIL_LOGINPSWDERROR = new String("com.sertek.commons.mail.MAIL_LOGINPSWDERROR");
	public static String MAIL_FORGETPSWD = new String("com.sertek.commons.mail.MAIL_FORGETPSWD");

	
	public MAIL_TEMPLATE(HashMap param) {
		this.param = param;
	}
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected CheckObject check = new CheckObject();
	
	protected HashMap param = new HashMap();
	
	protected util_date ud = new util_date();
	
	protected utility ut = new utility();

	public abstract String getMailSubject();

	public abstract String getMailContent();
	
	public static String Conversion(String html, HashMap ht) throws Exception
	{
	    try{
	    	html = passCheck(html, ht);

		    //處理「$」符號
		    String[] b=pass(html, "@");
		    if( b != null ) {
		        for(int i=0;i<b.length;i++){
		            if( ht.get(b[i]) != null )
			            html = com.sertek.util.util.replace(html, "@"+b[i]+"@", com.sertek.util.util.replace(ht.get(b[i]).toString(), "'", "''"));
					else   
						html = com.sertek.util.util.replace(html, "@"+b[i]+"@", "");
		        }
		    }
		    
        }catch(Exception e){
            System.out.println("Conversion 發生錯誤:" + e.toString());
        }
		return html;
	}

	private static String[] pass(String str, String chr) throws Exception {
		String[] kk = com.sertek.util.util.split(str, chr);
		String[] ret = null;

		if (kk.length > 1) {
			ret = new String[(kk.length - 1) / 2];
			int loc = 0;
			for (int i = 1; i < kk.length; i += 2){
				ret[loc++] = kk[i];
			}
		}
		return ret;
	}
	
	private static String passCheck(String html, HashMap ht) throws Exception {
		
		String chk = getSubString(html, "<check", ">");
		String inchk = "";
		String newinchk = "";
		String key = "";
		String mk = "";
		
		do{
			if( !chk.equals("") ) {
				chk = "<check" + chk + ">"; 
				if( getAfterString(html, chk).indexOf("</check>")>-1 ) {
					inchk = getSubString(html, chk, "</check>");
					newinchk = inchk;
					if( chk.indexOf("property='")>-1) {
						key = getSubString(chk, "property='", "'");
					}else{
						key = getSubString(chk, "property=\"", "\"");
					}
					
					if( ht.get(key)==null) {
						if( chk.indexOf("isempty")==-1 )
							newinchk = "";
					}else{
						if( ht.get(key).toString().equals("") && chk.indexOf("notempty")>-1 ) {
							newinchk = "";
						}else if( !ht.get(key).toString().equals("") && chk.indexOf("isempty")>-1 ) {
							newinchk = "";
						}
					}
					html = com.sertek.util.util.replace(html, chk+inchk+"</check>", newinchk);
				}else{
					html = com.sertek.util.util.replace(html, chk, "");
				}				
			}
			chk = getSubString(html, "<check", ">");
		}while( !chk.equals("") );			
		return html;
	}
	
	private static String getSubString(String str, String a, String b) {
		String ret = "";
		if( str.indexOf(a)>0 ) {
			str = str.substring(str.indexOf(a)+a.length());
			if(str.indexOf(b)>0) {
				ret = str.substring(0, str.indexOf(b));
			}
		}
		return ret;
	}
	
	private static String getAfterString(String str, String a) {
		String ret = "";
		if( str.indexOf(a)>0 ) {
			ret = str.substring(str.indexOf(a)+a.length());
		}
		return ret;
		
	}

}