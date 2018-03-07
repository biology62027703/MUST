package com.sertek.form;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.sertek.commons.MailHelper;
import com.sertek.commons.SendMail;
import com.sertek.commons.ServiceDateObj;
import com.sertek.commons.mail.MAIL_RCPT;
import com.sertek.commons.mail.MAIL_TEMPLATE;
import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.sys.StaticObj;
import com.sertek.sys.sys_Helper;
import com.sertek.sys.sys_User;
import com.sertek.table.S03;
import com.sertek.table.S08;
import com.sertek.util.Base64Decoder;
import com.sertek.util.CheckObject;
import com.sertek.util.CryptoUtil;
import com.sertek.util.util_date;
import com.sertek.util.utility;

public class LoginController extends BaseAbstractCommandController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private String indexView = "LOGIN.jsp";
	private String successView = "INDEX.jsp";
	private String errorView = "LOGIN.jsp";
	
	private String pswderrsendmail = "";
	private String lastsuccess = "";
	private String lastfailure = "";
	private String servername = "";
	private String sn = "";
	private String errmsg = "";
	
	private CheckObject check = new CheckObject();
	
	private util_date ud = new util_date();
	
	private SqlDBUtility sqlDBUtility;

	public SqlDBUtility getSqlDBUtility() {
		return sqlDBUtility;
	}

	public void setSqlDBUtility(SqlDBUtility sqlDBUtility) {
		this.sqlDBUtility = sqlDBUtility;
	}
	
	public LoginController() {
		setCommandClass(HashMap.class);
	}

	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object obj, BindException arg3) throws Exception {
				
		if (request.getSession().getAttribute("SOL_CNT_INDEX") == null) {
			request.getSession().setAttribute("SOL_CNT_INDEX", getCnt("INDEX"));
		}
		
		String method = request.getParameter("method");
		servername = request.getServerName();		
		//System.out.println("servername:"+servername+"　　時間："+ud.nowTime());
		
		//this.sn = ud.nowMilliTime()
		try {
			//add by James Bug #12781 忘記密碼寄送該帳號下S03儲存的EMAIL
			errmsg = "";
			if("sendps".equals(method)){
				//String pswd = "";
				String usrnm = "";
				String usrid = request.getParameter("usrid");
				
				String sn="";
				//隨機編碼
				for(int i=0;i<10;i++){
					int z=(int)((Math.random()*7)%3);
					if(z==1){ //放數字 
						sn += (char)((Math.random()*10)+48); 
					}/*else if(z==2){ //放大寫英文 
						sn += (char)(((Math.random()*26) + 65)); 
					}*/else{// 放小寫英文 
						sn +=  ((char)((Math.random()*26)+97)); 
					} 
				}
				
				
				HashMap usrlogMap = new HashMap();				
				usrlogMap.put("sn", sn);
				usrlogMap.put("usrid", usrid);
				usrlogMap.put("reqdt", ud.nowCDate());
				usrlogMap.put("reqtm", ud.nowTime());
				usrlogMap.put("status", "0");
				System.out.println("usrlogMap:"+usrlogMap);
				sqlDBUtility.insert("RESETPSWD", "RESETPSWD.insert", usrlogMap);
				logger.info("usrid = " + request.getParameter("usrid"));
				
				
				
				CryptoUtil crypto = new CryptoUtil();
				String resetpswd = (request.getScheme()+"://"+servername+"/"+"SOL"+"/RESETPSWD.do?info="+crypto.encrypt(sn+"||"+usrid));
				System.out.println("resetpswd:"+resetpswd);
							
				HashMap s03Key = new HashMap();
				s03Key.put("usrid", usrid);		
				List s03List = sqlDBUtility.queryForList("SYS.LoginCheck", s03Key);
				if (s03List.size() > 0) {
					S03 s03 = (S03) s03List.get(0);
					pswderrsendmail = s03.getEmail();
					usrnm = s03.getUsrnm();
					System.out.println("EMAIL:"+pswderrsendmail);
				} else {
					HashMap form = new HashMap();
					form.put("errmsg", "帳號不存在!");
					return new ModelAndView(errorView, "form", form);
				}
				s03List = sqlDBUtility.queryForList("S03.queryByKey", s03Key);
				if (s03List.size() > 0) {
					S03 s03 = (S03) s03List.get(0);
					//pswd =s03.getPswd();			
				}
				doLog(usrid, usrnm, getIpAddr(request), "6", "0",resetpswd);
				HashMap form = new HashMap();
				form.put("errmsg", "密碼已寄到此帳號所註冊之信箱!");
				return new ModelAndView(errorView, "form", form);
			}
			
			else {
				
				return new ModelAndView(successView);
			}
		} catch (Exception e) {
			HashMap form = new HashMap();
			form.put("errmsg", "系統發生錯誤!");
			logger.error(e, e);
			return new ModelAndView(indexView, "form", form);
		}
	}
	
	private void doLog(String usrid, String usrnm, String usrip, String actid, String pswdcnt, String resetpswd) throws Exception {
		String detail = "";
		if ("1".equals(actid)) {
			detail = "登入成功";
		} else if ("2".equals(actid)) {
			detail = "密碼錯誤";
		} else if ("3".equals(actid)) {
			detail = "帳號不存在";
		} else if ("4".equals(actid)) {
			detail = "帳號已停用";
		} else if ("5".equals(actid)) {
			detail = "登入IP不允許";
		} else if ("6".equals(actid)) {
			detail = "忘記密碼";
		}
		
		HashMap usrlogMap = new HashMap();
		usrlogMap.put("usrid", usrid);
		usrlogMap.put("usrnm", usrnm);
		usrlogMap.put("usrip", usrip);
		usrlogMap.put("resetpswd", resetpswd);
		usrlogMap.put("ldt", ud.nowCDate());
		usrlogMap.put("ltm", ud.nowTime().substring(0, 4));
		usrlogMap.put("act", "登入");
		usrlogMap.put("detail", detail);
		usrlogMap.put("c_no", "");
		usrlogMap.put("mailcon", "");
		
		//寄MAIL通知有登入失敗
		if( "6".equals(actid) ) {
			System.out.println("帳號:"+usrid);
			System.out.println("resetpswd:"+resetpswd);
			setS08Info(usrlogMap);
			usrlogMap.put("mail_id", MAIL_TEMPLATE.MAIL_FORGETPSWD);
			List rcptList = new ArrayList();			
			String[] addMails = getEmails(pswderrsendmail);
			for (int j = 0; j < addMails.length; j++) {				
				MAIL_RCPT rcpt = new MAIL_RCPT();
				rcpt.setName(check.checkNull(usrlogMap.get("usrnm"), "").toString());
				rcpt.setAddr(addMails[j]);
				rcptList.add(rcpt);
			}			
			usrlogMap.put("rcptList", rcptList);
			new SendMail(usrlogMap);
		}
		
		//寄MAIL通知有登入失敗
		if( "2".equals(actid) ) {
			setS08Info(usrlogMap);
			usrlogMap.put("mail_id", MAIL_TEMPLATE.MAIL_LOGINPSWDERROR);
			List rcptList = new ArrayList();			
			String[] addMails = getEmails(pswderrsendmail);
			for (int j = 0; j < addMails.length; j++) {
				MAIL_RCPT rcpt = new MAIL_RCPT();
				rcpt.setName(check.checkNull(usrlogMap.get("usrnm"), "").toString());
				rcpt.setAddr(addMails[j]);
				rcptList.add(rcpt);
			}			
			usrlogMap.put("rcptList", rcptList);
			new SendMail(usrlogMap);
		}
		
		//取得上次登入成功或失敗的時間
		lastsuccess = "";
		lastfailure = "";
		if( "1".equals(actid) ) {
			List ls = this.sqlDBUtility.queryForList("SYS.LOGIN_USRLOG0_GetLastInfo", usrlogMap);
			for(int i=0;i<ls.size();i++) {
				HashMap data = (HashMap)ls.get(i);
				if( data.get("DETAIL").toString().equals("登入成功") ) {
					lastsuccess = "最近登入成功時間："+ud.formatCDate(data.get("LDT").toString(), "/")+" "+ud.formatTime(data.get("LTM").toString(), ":")+"，IP:"+data.get("USRIP").toString();
				}else if( data.get("DETAIL").toString().equals("密碼錯誤") ) {
					lastfailure = "上次登入錯誤時間："+ud.formatCDate(data.get("LDT").toString(), "/")+" "+ud.formatTime(data.get("LTM").toString(), ":")+"，IP:"+data.get("USRIP").toString();
				}
			}
		}
		
		sqlDBUtility.insert("USRLOG", "USRLOG.insertSelective", usrlogMap);
		
		// 1.登入成功2.密碼錯誤3.帳號不存在
		if ("1".equals(actid)) {
			doUpdateS03(usrid, ud.nowCDate(), "0");
		} else if ("2".equals(actid)) {
			doUpdateS03(usrid, "", pswdcnt);
		}
	}
	
	public String[] getEmails(String email) {
		utility ut = new utility();
		String[] emails = null;
		try {
			emails = email.split("[；;,]"); // 分號或逗號
			for (int i = 0; i < emails.length; i++) {
				emails[i] = ut.Ltrim(emails[i].trim());
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return emails;
	}
	
	private void setS08Info(HashMap  form) {
		String[] ftpKeys = { "FTP_IP", "FTP_USRID", "FTP_PSWD", "FTP_IPC", "XMLPATH", "IPCWebs" };
		for (int i = 0; i < ftpKeys.length; i++) {
			form.put(ftpKeys[i], this.getS08Argvl("TSO1E01", ftpKeys[i]));
			//logger.info(ftpKeys[i] + " = " + check.checkNull(form.get(ftpKeys[i]), ""));
		}
	}
	
	public String getS08Argvl(String prgid, String argnm) {
		return StaticObj.getS08(prgid, argnm);
	}
	
	private void doLoginSuccess(HttpServletRequest request, HttpServletResponse response, S03 s03) throws Exception {
		// 寫COOKIE
		StaticObj.setMenuCntAdd1();
		Cookie cookie = new Cookie("sol_usrid", URLEncoder.encode(s03.getUsrid(), "UTF-8"));
		cookie.setMaxAge(604800);
		response.addCookie(cookie);

		sys_User user = new sys_User(request, sqlDBUtility, s03);
		request.getSession().setAttribute("User", user);
		
		if ("T".equals(user.readString("S_PRIV"))) {
			request.setAttribute("mainUrl", "sos/SSOMain.do?action=viewForm&init=Y&check_viptype=Y"); //管理者
		} else {
			request.setAttribute("mainUrl", "sof/FSOMain.do?action=viewForm&init=Y&check_viptype=Y"); //使用者
		}
		
		//BOS的使用者取得功能的權限
		if( s03.getOwner().equals("BOS") ) {
			HashMap fun_group = new HashMap();
			fun_group.put("syskd", user.readString("S_SYSKD"));
			fun_group.put("priv", user.readString("S_PRIV"));
			fun_group.put("group_id", user.readString("S_GROUP_ID"));
			user.setFun_group_sub_id(sqlDBUtility.queryForList("SYS.FUN_GROUP_Query", fun_group));
		}
		
		File utemp = new File(user.readString("UTEMP"));
		if (!utemp.exists()) {
			utemp.mkdirs();
		}
		
		sys_Helper helper = new sys_Helper(request, sqlDBUtility);
		request.getSession().setAttribute("Helper", helper);

		delFile(user.readString("UTEMP"));
		
		doLog(s03.getUsrid(), user.readString("S_USRNM"), getIpAddr(request), "1", "0", "");
		user.writeString("lastsuccess", lastsuccess);
		user.writeString("lastfailure", lastfailure);
	}
	
	private void doUpdateS03(String usrid, String lstlgndt, String pswdcnt) throws Exception {
		HashMap keyMap = new HashMap();
		keyMap.put("usrid", usrid);
		if (!"".equals(lstlgndt)) {
			keyMap.put("lstlgndt", ud.nowCDate());
			keyMap.put("lstlgntm", ud.nowTime().substring(0, 4));
		}
		keyMap.put("pswdcnt", pswdcnt);
		sqlDBUtility.update("SYS.updateSOS03Info", keyMap);
		sqlDBUtility.update("SYS.updateBOSS03Info", keyMap);
	}
	
	private synchronized String getCnt(String page) {
		String count = "";
//		boolean isUpdate = false;
//		try {
//			HashMap keyMap = new HashMap();
//			keyMap.put("page", page);
//			List cntList = sqlDBUtility.queryForList("CNT.queryByKey", keyMap);
//			if (cntList.size() > 0) {
//				CNT cnt = (CNT) cntList.get(0);
//				count = cnt.getCnt();
//				isUpdate = true;
//			} else {
//				count = "0";
//			}
//
//			count = "" + (Integer.parseInt(count) + 1);
//			keyMap.put("cnt", count);
//
//			if (isUpdate) {
//				sqlDBUtility.update("SYS.updateCntByPage", keyMap);
//			} else {
//				sqlDBUtility.insert("CNT.insertSelective", keyMap);
//			}
//
//		} catch (Exception e) {
//			logger.error(e, e);
//		}
		return count;
	}
	
	private void delFile(String path) throws IOException {
		File file_path = new File(path);
		File afile[] = file_path.listFiles();

		int Day = 1;

		long now = System.currentTimeMillis();
		now = now / 1000 / 60 / 60 / 24;

		for (int k = 0; k < afile.length; k++) {
			if (!afile[k].isDirectory()) {
				long lastmodified = 0;
				lastmodified = afile[k].lastModified();
				lastmodified = lastmodified / 1000 / 60 / 60 / 24;
				if ((now - lastmodified) >= Day) {
					afile[k].delete();
				}
			}
		}
	}
	
	private boolean checkLoginIp(String ip) {
		
		ip += ".";
		String allowip="";
		boolean ret = false;
		List ls = this.sqlDBUtility.queryForList("SYS.S08_ADMIN_IP_select");
		if( ls.size()>0 ) {
			String allowips[] = ((HashMap)ls.get(0)).get("ARGVL").toString().split(";");
			for(int i=0;i<allowips.length;i++) {
				allowip = allowips[i];
				while( allowip.substring(allowip.length()-2).equals(".*") )
					allowip = allowip.substring(0, allowip.length()-2);
				allowip+=".";				
				if( ip.indexOf(allowip)==0 )
					ret = true;
			}
		}else{
			ret = true;
		}
		return ret;
	}
}