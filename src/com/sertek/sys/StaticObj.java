package com.sertek.sys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;

import com.google.gson.Gson;
import com.ibatis.common.resources.Resources;
import com.sertek.db.DBUtility;
import com.sertek.util.util_File;
import com.sertek.util.util_date;
import com.sertek.util.utility;
import com.sertek.table.*;
import com.sertek.bean.*;


public class StaticObj {
	
	public static HashMap ht = new HashMap();
	public static util_date ud = null;		
	
	public static void iniData() {
		
		/*ud = new util_date();	
		DBUtility db = null;
		try {
			db = com.sertek.form.FormUtil.getDBConnection();
			System.out.println( "StaticObj  ERPWEB 物件初始化==============" );
			iniS0B(db, "SOL");
			iniS08(db);
			iniCourt(db);
			iniS14(db);
			iniEnset(db);
			iniPhrase(db);
			iniSuitkd(db);
			iniSndSO2Clog(db);
			iniSndSO2IRPlog(db);
			iniCnt();
			//testDBconnection(db);
			System.out.println( "StaticObj.ht 測試資料==============" );
			System.out.println( new Gson().toJson(ht) );
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
	}
	
	private static void iniCnt() {
		CNT cnt = new CNT();		
		util_File _F = new util_File();
		String txt = "";
		try {		
			_F.open(Project.getScriptDir()+"CNT.json","r");
			txt = _F.readline();
			if( !txt.equals("") ) {
				Gson gson = new Gson();
				cnt = gson.fromJson(txt, CNT.class);
			}
		} catch(Exception e){;
			System.out.println(e.toString());
       	} finally {
           _F.close();
       	}		
		if( cnt.INDEX==null )
			cnt.INDEX = "0";
		if( cnt.MENU==null )
			cnt.MENU = "0";
		ht.put("cnt", cnt);
	}
	
	private static void writeCNTJson() {
		CNT cnt = (CNT)ht.get("cnt");		
		util_File _F = new util_File();
		try {		
			_F.open(Project.getScriptDir()+"CNT.json","w");
            _F.writeln(new Gson().toJson(cnt));
       } catch(Exception e){
       } finally {
           _F.close();
       }		
	}
	
	private static void testDBconnection(com.sertek.db.DBUtility db) {
		List s0b = new ArrayList();
		//System.out.println("TEST:select username from users) ");
		Vector vt = db.doSqlSelect(" SELECT S.SID, S.SERIAL#, S.USERNAME, S.OSUSER , S.MACHINE, S.PROGRAM , P.SPID " +
				" FROM  V$SESSION S ,  V$PROCESS P " +
				" WHERE S.PADDR = P.ADDR  AND S.TYPE !='BACKGROUND' AND S.OSUSER !='oracle' " +
				" and s.USERNAME='JAMESH' ", 7, false);
		System.out.println("VT--------------MUST---------TEST:"+vt);
		
	}
	
	public static String getIndexCnt() {
		CNT cnt = (CNT)ht.get("cnt");	
		return cnt.INDEX==null ? "0" : cnt.INDEX;
	}
	
	public static String getMenuCnt() {
		CNT cnt = (CNT)ht.get("cnt");		
		return cnt.MENU==null ? "0" : cnt.MENU;
	}
	
	public static void setIndexCntAdd1() {
		String count = getIndexCnt();
		try {		
			count = String.valueOf(Integer.parseInt(count)+1);
		} catch(Exception e){
			count = "1";
       	} finally {
       	}
		CNT cnt = (CNT)ht.get("cnt");
		cnt.INDEX = count;
		writeCNTJson();
	}
	
	public static void setMenuCntAdd1() {
		String count = getMenuCnt();
		try {		
			count = String.valueOf(Integer.parseInt(count)+1);
		} catch(Exception e){
			count = "1";
       	} finally {
       	}
		CNT cnt = (CNT)ht.get("cnt");
		cnt.MENU = count;
		writeCNTJson();
	}
	
	private static void iniEnset(com.sertek.db.DBUtility db) {
		HashMap enset = new HashMap();		
		String tblnm = "";
		String colnm = "";
		String olen = "";
		Vector vt = db.doSqlSelect("select upper(tblnm) as tblnm, upper(colnm) as colnm, olen from so..enset order by tblnm", 3, false);
		for(int i=0;i<vt.size()/3;i++) {
			tblnm = vt.get(i*3+0).toString();
			colnm = vt.get(i*3+1).toString();
			olen = vt.get(i*3+2).toString();
			
			//處理map
			if( enset.get(tblnm+"_MAP")==null ) {
				enset.put(tblnm+"_MAP", new HashMap());
			}
			((HashMap)enset.get(tblnm+"_MAP")).put(colnm, colnm);
			
			//處理list
			if( enset.get(tblnm+"_LIST")==null ) {
				enset.put(tblnm+"_LIST", new ArrayList());
			}
			ENSET row = new ENSET();
			row.setTblnm(tblnm);
			row.setColnm(colnm);
			row.setOlen(olen);
			((List)enset.get(tblnm+"_LIST")).add(row);
		}		
		ht.put("enset", enset);
	}
	
	private static void iniS08(com.sertek.db.DBUtility db) {
		HashMap s08 = new HashMap();
		
		Vector vt = db.doSqlSelect("select prgid, argnm, argvl from so..s08 ", 3, false);
		for(int i=0;i<vt.size()/3;i++) {
			s08.put(vt.get(i*3+0).toString()+"_"+vt.get(i*3+1).toString(), vt.get(i*3+2).toString());
			s08.put(vt.get(i*3+0).toString()+"_"+vt.get(i*3+1).toString()+"_JSONSTR", new Gson().toJson(argvltolist(vt.get(i*3+2).toString())));
		}
		
		try{
			int sdt = Integer.parseInt(s08.get("POWER_SMTP_SDT").toString());
			int edt = Integer.parseInt(s08.get("POWER_SMTP_EDT").toString());
			int ndt = Integer.parseInt(ud.nowCDate());
			ht.put("issmtponpower", ndt<=edt && ndt>=sdt ? "N" : "Y");			
		} catch(Exception e){
			ht.put("issmtponpower", "Y");
		}
		
		
		ht.put("s08", s08);
	}
	
	private static void iniCourt(com.sertek.db.DBUtility db) {
		HashMap court = new HashMap();
		List ls = new ArrayList();
		
		Vector vt = db.doSqlSelect("select crtid, crtnm, syskd, a2crtid, v2crtid, crtkd from so..court order by case when crtid like '%E' then 2 else 1 end, crtseq", 6, false);
		for(int i=0;i<vt.size()/6;i++) {
			court.put(vt.get(i*6+0).toString(), vt.get(i*6+1).toString());
			
			HashMap data = new HashMap();
			data.put("CRTID", vt.get(i*6+0).toString());
			data.put("CRTNM", vt.get(i*6+1).toString());
			data.put("SYSKD", vt.get(i*6+2).toString());
			data.put("A2CRTID", vt.get(i*6+3).toString());
			data.put("V2CRTID", vt.get(i*6+4).toString());
			data.put("CRTKD", vt.get(i*6+5).toString());
			ls.add(data);
		}
		
		ht.put("court", court);
		ht.put("courtlist", ls);
	}
	
	private static void iniSuitkd(com.sertek.db.DBUtility db) {
		HashMap suitkd = new HashMap();
		
		suitkd.put("S", "起訴狀");
		suitkd.put("C", "聲請狀");
		suitkd.put("AZ", "上訴狀");
		Vector vt = db.doSqlSelect("select s_kd, suit_nm from so..suitkd where s_kd not in ('S','C','AZ')", 2, false);
		for(int i=0;i<vt.size()/2;i++) {
			suitkd.put(vt.get(i*2+0).toString(), vt.get(i*2+1).toString());
		}
		
		ht.put("suitkd", suitkd);
	}	
	
	private static void iniS14(com.sertek.db.DBUtility db) {
		HashMap s14 = new HashMap();
		
		Vector vt = db.doSqlSelect("select crtid+'_'+prgid+'_'+argnm as [key], crtip+argvl as url1, crtip1+argvl as url2 from so..s14", 3, false);
		for(int i=0;i<vt.size()/3;i++) {
			s14.put(vt.get(i*3+0).toString()+"_URL1", vt.get(i*3+1).toString());
			s14.put(vt.get(i*3+0).toString()+"_URL2", vt.get(i*3+2).toString());
		}
		
		ht.put("s14", s14);
	}
	
	private static List argvltolist(String argvl) {
		List ls = new ArrayList();
		String[] ary = argvl.split("[;,]");
		for(int i=0;i<ary.length;i++) {
			HashMap data = new HashMap();
			data.put("__FIELD__", ary[i]);
			ls.add(data);
		}
		return ls;
	}
	
	private static void iniPhrase(com.sertek.db.DBUtility db) {
		HashMap phrase = new HashMap();
		HashMap datamapping = new HashMap();
		
		Vector vt = db.doSqlSelect("select name, id, data from so..phrase order by name", 3, false);
		String name, key, value;
		for(int i=0;i<vt.size()/3;i++) {
			name = vt.get(i*3+0).toString();
			key = vt.get(i*3+1).toString();
			value = vt.get(i*3+2).toString();
			
			phrase.put(name+"_"+key, value);
			
			if( datamapping.get(name.toLowerCase())==null ) {
				datamapping.put(name.toLowerCase(), new HashMap());
			}			
			((HashMap)datamapping.get(name.toLowerCase())).put(key, value);
		}
		util_File _F = new util_File();
		try {		
			_F.open(Project.getScriptDir()+"DATAMAPPING.js","w");
            _F.writeln("var datamapping = " + new Gson().toJson(datamapping) + ";");
       } catch(Exception e){
       } finally {
           _F.close();
       }
		ht.put("phrase", phrase);
	}
	
	public static String getS08(String prgid, String argnm) {
		HashMap map = (HashMap)ht.get("s08");
		String key = prgid+"_"+argnm;
		return map.get(key)==null ? "" : map.get(key).toString();
	}
	
	public static String getS08JsonStr(String prgid, String argnm) {
		HashMap map = (HashMap)ht.get("s08");
		String key = prgid+"_"+argnm+"_JSONSTR";
		return map.get(key)==null ? "" : map.get(key).toString();
	}
	
	public static String getS14Url1(String crtid, String prgid, String argnm) {
		crtid = getOcrtid(crtid);
		HashMap map = (HashMap)ht.get("s14");
		String key = crtid+"_"+prgid+"_"+argnm+"_URL1";
		return map.get(key)==null ? "" : map.get(key).toString();
	}
	
	public static String getS14Url2(String crtid, String prgid, String argnm) {
		crtid = getOcrtid(crtid);
		HashMap map = (HashMap)ht.get("s14");
		String key = crtid+"_"+prgid+"_"+argnm+"_URL2";
		return map.get(key)==null ? "" : map.get(key).toString();
	}
	
	public static String getOcrtid(String crtid) {
		List ls = (List)ht.get("courtlist");
		for(int i=0;i<ls.size();i++) {
			HashMap data = (HashMap)ls.get(i);
			if( data.get("CRTID").toString().equals(crtid) ) {
				if( data.get("CRTKD").toString().equals("E") )
					crtid = data.get("V2CRTID").toString();
				break;
			}
		}
		return crtid;
	}
	
	public static String getOcrtnmandcrtnm(String crtid) {
		String ocrtid = getOcrtid(crtid);
		String result = "";
		if(crtid.equals(ocrtid)) {
			return getCourt(crtid);
		} else {
			result = getCourt(ocrtid) + "　" + getCourt(crtid);
		}
		return result;
	}
	
	public static String getPhrase(String name, String id) {
		HashMap map = (HashMap)ht.get("phrase");	
		String key = name+"_"+id;
		return map.get(key)==null ? "" : map.get(key).toString();
	}
	
	public static String getS08CodeAry(String prgid, String argnm, String name, String field) {
		String jsonstr = getS08JsonStr(prgid, argnm);
		jsonstr = jsonstr.replaceAll("__FIELD__", field);
		if( jsonstr.equals("") )
			jsonstr = "[]";
		return "var _code_" + name + "=" + jsonstr + ";";
	}
	
	public static String getCourt(String crtid) {
		HashMap map = (HashMap)ht.get("court");
		String key = crtid;
		return map.get(key)==null ? "" : map.get(key).toString();
	}
	
	public static List getCourtList() {
		return (List)ht.get("courtlist");
	}
	
	public static HashMap getEnsetMap(String tblnm) {
		HashMap map = (HashMap)ht.get("enset");
		return map.get(tblnm+"_MAP")==null ? new HashMap() : (HashMap)map.get(tblnm+"_MAP");
	}
	
	public static List getEnsetList(String tblnm) {
		HashMap map = (HashMap)ht.get("enset");
		return map.get(tblnm+"_LIST")==null ? new ArrayList() : (List)map.get(tblnm+"_LIST");
	}
	
	public static String getSuitkd(String s_kd) {
		HashMap map = (HashMap)ht.get("suitkd");
		String key = s_kd;
		return map.get(key)==null ? "" : map.get(key).toString();
	}
	
	public static boolean powerCheck() {
		util_date ud = new util_date();
		String now = ud.nowCDate() + ud.nowTime();
		String sdt = getS08("POWER", "SDT");
		String edt = getS08("POWER", "EDT");
		while( sdt.length()<13 ) sdt += "0";
		while( edt.length()<13 ) edt += "0";
		
		if( Double.parseDouble(now)<Double.parseDouble(sdt) || Double.parseDouble(now)>Double.parseDouble(edt) )
			return true;
		else
			return false;
	}
	
	public static boolean isSMTPonPower() {
		return ht.get("issmtponpower").toString().equals("Y");
	}
}
