package com.sertek.sys;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.table.S03;
import com.sertek.util.CheckObject;

public class sys_User implements Serializable {

	private Logger logger = Logger.getLogger(this.getClass());	
	private SqlDBUtility sqlDBUtility;	
	private CheckObject check = new CheckObject();	
	private HashMap map = new HashMap();

	public sys_User(HttpServletRequest request, SqlDBUtility sqlDBUtility, S03 s03) throws Exception {
		this.sqlDBUtility = sqlDBUtility;
		setS03Info(s03);
		setFSInfo(s03);
		setXXXInfo();

		writeString("S_USRIP", getIpAddr(request));
	}
	
	private void setS03Info(S03 s03) {
		logger.info("載入使用者：" + s03.getUsrid() + "的S03資訊");
		
		logger.info("onwer = " + s03.getOwner());
		logger.info("syskd = " + s03.getSyskd());
		logger.info("scdnm = " + s03.getScdnm());
		logger.info("priv = " + s03.getPriv());

		writeString("S_OWNER", s03.getOwner());
		writeString("S_SYSKD", s03.getSyskd());
		writeString("S_SYS", s03.getSys());
		writeString("S_SCD", s03.getScd());
		writeString("S_SCDNM", s03.getScdnm());
		writeString("S_PRIV", s03.getPriv());
		
		writeString("S_USRID", s03.getUsrid());
		writeString("S_USRNM", s03.getUsrnm());
		writeString("S_IDNO", s03.getIdno());
		writeString("S_EMAIL", s03.getEmail());
		writeString("S_ADDR", s03.getAddr());
		writeString("S_TEL", s03.getTel());
		writeString("S_LSTLGNDT", s03.getLstlgndt());
		writeString("S_LSTLGNTM", s03.getLstlgntm());
		
		writeString("S_VIP_TYPE", s03.getVip_type());
		writeString("S_LAWOFFICE", s03.getLawoffice());
		writeString("S_OK_ID", s03.getOk_id());
		writeString("S_GROUP_ID", s03.getGroup_id());
		writeString("S_LAWOFFICEADMINCNT", s03.getLawofficeadmincnt());
		
		if( s03.getOwner().equals("BOS") ) {
			String scd_account = "RX"+s03.getSyskd()+"U"+s03.getScd();
			writeString("S_IS_SCD_ACCOUNT", scd_account.equals(s03.getUsrid()) ? "Y" : "N");
			setBOSMenu();
		}else{
			writeString("S_IS_SCD_ACCOUNT", "N");
		}
	}
	
	private void setBOSMenu() {
		HashMap ht = new HashMap();		
		ht.put("syskd", this.readString("S_SYSKD"));
		ht.put("priv", this.readString("S_PRIV"));
		ht.put("group_id", this.readString("S_GROUP_ID"));
		map.put("S_MENU_LIST", this.sqlDBUtility.queryForList("SYS.BOS_FUN_LIST_MENU_QUERY", ht));		
	}

	private void setFSInfo(S03 s03) {
		writeString("ATT", Project.getFSRoot() + "att" + File.separator);
		writeString("UHOME", Project.getFSRoot() + "user" + File.separator + s03.getUsrid() + File.separator);
		writeString("UTEMP", Project.getFSRoot() + "user" + File.separator + s03.getUsrid() + File.separator + "temp" + File.separator);
	}
	
	private void setXXXInfo() {
	}

	public void writeString(String key, String value) {
		map.put(key, value);
	}

	public String readString(String key) {
		return readString(key, "");
	}

	public String readString(String key, String defaultValue) {
		return check.checkNull(map.get(key), defaultValue).toString();
	}
	
	public void setFun_group_sub_id(List ls) {
		for(int i=0;i<ls.size();i++) {
			HashMap data = (HashMap)ls.get(i);
			writeString("RIGHT_"+data.get("FUN_ID").toString(), data.get("SUB_ID").toString());
		}
	}
	
	public String getFun_group_sub_id(String fun_id) {
		if( readString("S_OWNER").equals("BOS") ) {
			return readString("RIGHT_"+fun_id);
		}else{
			return "*";
		}
	}
	
	public List getMenuList() {
		return map.get("S_MENU_LIST")==null ? new ArrayList() : (List)map.get("S_MENU_LIST");
	}
	
	public String getIpAddr(HttpServletRequest request) {   
		String ip = request.getHeader("x-forwarded-for");   
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
		ip = request.getHeader("Proxy-Client-IP");  
		}   
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
		ip = request.getHeader("WL-Proxy-Client-IP");  
		}   
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
		ip = request.getRemoteAddr();  
		}  
		return ip.split("[;,]")[0]; 
		} 
}