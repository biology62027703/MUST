package com.sertek.util;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.sertek.db.DBUtility;

/**
----------------------------------------------------------------------------------------
拜肈虫腹Bug #2785 - 猭﹛竚传猭ㄆ叭﹛ノ method
э篕璶穝糤猭﹛竚传猭ㄆ叭﹛ノ method
穝セV9704-杜&猭ㄆ叭﹛
эnicole
эら戳0970221,0970304
----------------------------------------------------------------------------------------
拜肈虫腹Bug #3864-3 - TCH0MJ0980001
э篕璶穝糤function锣传珹腹(Ex.)籔硑珹腹, 秆∕V腹ノ硑τHぃノ硑, 硑Θ
		 тぃ戈拜肈(チㄆэ, 匡ノ掸魁para=1)
穝セJUD980220
эnicole
эら戳0980701
闽祘Α(1)wkj/REFCOURT_02.jsp
-----------------------------------------------------------------------------------------
拜肈虫腹Bug #4868 - TCH0MJ0980001
э篕璶竚传﹃笆, тぃ传ゎfor 郎ㄏノ
穝セJUD980220
эnicole
эら戳0990121
-----------------------------------------------------------------------------------------
 */

public class ReplaceUtil{
	
	protected static Logger logger = Logger.getLogger("com.sertek.util.ReplaceUtil"); 
	private String owner="g";//Bug #4022 - add by wythe@20090505
	private String dblink="";//Bug #4022 - add by wythe@20090609
	
	public ReplaceUtil() {}
	public ReplaceUtil(String owner) { this.owner=owner;} //Bug #4022 - add by wythe@20090505
	public ReplaceUtil(String owner,String dblink) { 
		this.owner=owner;
	    this.dblink = dblink;
	
	} //Bug #4022 - add by wythe@20090609
	
	/**
	 * 竚传﹃笆, 竚传Ω
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return String
	 */
	public String replaceStr(String str, String oldStr, String newStr){
		StringBuffer ret = new StringBuffer();
		try{
			int idx = str.indexOf(oldStr); 
			if(idx != -1){
				ret.append(str.substring(0, idx));
				ret.append(newStr);
				ret.append(str.substring(idx + oldStr.length()));
			}else{
				ret.append(str);
			}
		}catch(Exception err){
			logger.warn("ReplaceUtil replaceStr() can not find " + oldStr + " from " + str + " \tnewStr:" + newStr);
			System.out.println("ReplaceUtil replaceStr() can not find " + oldStr + " from " + str + " \tnewStr:" + newStr);
			ret.setLength(0);
			ret.append(str);
		}
		return ret.toString();
	}
	
	/** //(A)nicole [#4868]======
	 * 竚传﹃笆, тぃ传ゎ
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return String
	 */
	public String replaceStrForLoop(String str, String oldStr, String newStr){
		StringBuffer ret = new StringBuffer();
		try{
			if(!oldStr.equals("") && !oldStr.equals(" ")){ //(A)nicole [#4868-2]
				int idx = str.indexOf(oldStr); 
				if(idx == -1){
					ret.append(str);
				}else{
					int    j 	  = 0;
					String chgStr = "";
					while(idx != -1){
						if(j == 0){
							chgStr = str;
						}
						int idx3 = chgStr.indexOf(oldStr);
						//System.out.println("ReplaceUtil replaceStrForLoop() 1...j=["+j+"],idx3=["+idx3+"],chgStr=["+chgStr+"]");
						if(idx3 == -1){
							break;
						}else{
							ret.setLength(0);
							ret.append(chgStr.substring(0, idx3));
							ret.append(newStr);
							ret.append(chgStr.substring(idx3 + oldStr.length()));
							chgStr = ret.toString();
							j++;
						}
						//System.out.println("ReplaceUtil replaceStrForLoop() 2...ret=["+ret.toString()+"]");
					}
				}
			}else{ //(A)nicole [#4868-2]======
				ret.append(str);
			} //(A)nicole [#4868-2]======
		}catch(Exception err){
			logger.warn("ReplaceUtil replaceStrForLoop() can not find " + oldStr + " from " + str + " \tnewStr:" + newStr);
			System.out.println("ReplaceUtil replaceStrForLoop() can not find " + oldStr + " from " + str + " \tnewStr:" + newStr);
			ret.setLength(0);
			ret.append(str);
		}
		return ret.toString();
	} //(A)nicole [#4868]======
	
	/**
	 * 猭ㄆ叭﹛竚传﹃笆, 惠せ把计
	 * @param db
	 * @param sys - パowner祘Α狠穦竚传Θsys, 珿弄 sysㄓ琩高.
	 * @param dptcd
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return String
	 */
	public String replaceJDGKD(DBUtility db, String sys, String dptcd, String str, String oldStr, String newStr){
		utility utility   = new utility();
		StringBuffer ret  = new StringBuffer();
		StringBuffer sql  = new StringBuffer();
		Vector vr 		  = new Vector();
		char[] charOldStr = null;
		String tmpStr	  = "";
		int idx	 		  = 0;
		int idx1 		  = 0;
		int idx2 		  = 0;
		try{
			sql.setLength(0);
			//Bug #4022 - 糤 owner 盿C16 琩高, 讽owner="g",惠璶osys のdblink兵ン- modify by wythe@20090505
			sql.append("select jdgkd from " + owner + ".c16 ");
			if("g".equals(owner) || "G".equals(owner)){
				sql.append(" where ");
				sql.append("osys='"+sys+"' and ");
			}else{
				sql.append(dblink + " where ");
			}
			sql.append("dptcd='"+dptcd+"'");
System.out.println("[dblink in ReplaceUtil] sql="+sql.toString());
			System.out.println("ReplaceUtil replaceJDGKD()-猭ㄆ叭﹛竚传  sql="+sql.toString());
			vr = db.doSqlSelect(sql.toString(),1,false);
			System.out.println("ReplaceUtil replaceJDGKD() 竚传玡 str=["+str+"],oldStr=["+oldStr+"],newStr=["+newStr+"]");
			if(vr.size() > 0){
				if(vr.get(0).equals("A")){ //猭ㄆ叭﹛秈竚传
					if(!str.equals("")){
						//tmpStr  = util.strToFullSize(str);    //盢璣计﹃锣﹃
						tmpStr  = utility.noAnyBlank(str); //盢﹃ず籔フ场埃
						if(!tmpStr.equals("")){
							System.out.println("ReplaceUtil replaceJDGKD() tmpStr=["+tmpStr+"]");
							idx = tmpStr.indexOf(oldStr);
							if(idx > -1){
								charOldStr = oldStr.toCharArray();
								if(charOldStr.length > 0){
									//System.out.println("ReplaceUtil replaceJDGKD() charOldStr.length=["+charOldStr.length+"]");
									idx1 = str.indexOf(charOldStr[0]);
									idx2 = idx1 + str.substring(idx1,str.length()).indexOf(charOldStr[charOldStr.length-1]); //ノ磷琩高繷("猭")玡
									//System.out.println("ReplaceUtil replaceJDGKD() idx1=["+idx1+"],charOldStr[0]=["+charOldStr[0]+"],idx2=["+idx2+"],charOldStr[charOldStr.length-1]=["+charOldStr[charOldStr.length-1]+"]");
									ret.append(str.substring(0, idx1));
									ret.append(newStr);
									ret.append(str.substring((idx2-1) + oldStr.length()));
								}
							}else{
								System.out.println("ReplaceUtil replaceJDGKD() JDGKD='A' ゼ竚传!!!...");
								ret.append(str);
							}
						}
					}else{
						ret.append(str);
					}
				}else{
					ret.append(str);
				}
			}else{
				ret.append(str);
			}
		}catch(Exception err){
			logger.warn("ReplaceUtil replaceJDGKD() can not find "+oldStr+" from "+str+" \tnewStr:" + newStr);
			System.out.println("ReplaceUtil replaceJDGKD() can not find "+oldStr+" from "+str+" \tnewStr:" + newStr);
			ret.setLength(0);
			ret.append(str);
		}
		return ret.toString();
	}

	/**
	 * 猭ㄆ叭﹛竚传﹃笆, 惠せ把计
	 * @param db
	 * @param owner
	 * @param dptcd
	 * @param str
	 * @return String
	 */
	public String replaceJDGKD(DBUtility db, String sys, String dptcd, String str){
		return replaceJDGKD(db,sys,dptcd,str,"猭﹛","猭ㄆ叭﹛");
	}
	
	/**
	 * 竚传腹い珹腹笆, 竚传糶珹腹, 竚传硑珹腹(1-20)
	 * @param originStr
	 * @return String
	 */
	public String convertCrmid(String originStr){
		StringBuffer ret = new StringBuffer();
		try{
			//锣传糶珹腹
			originStr = replaceStr(originStr,"","(");
			originStr = replaceStr(originStr,"",")");
			//锣传硑珹腹1-20
			originStr = replaceStr(originStr,"嵦","()");
			originStr = replaceStr(originStr,"嵥","()");
			originStr = replaceStr(originStr,"嵤","()");
			originStr = replaceStr(originStr,"嵣","()");
			originStr = replaceStr(originStr,"嵢","(き)");
			originStr = replaceStr(originStr,"嵡","(せ)");
			originStr = replaceStr(originStr,"嵠","()");
			originStr = replaceStr(originStr,"嵟","()");
			originStr = replaceStr(originStr,"嵞","()");
			originStr = replaceStr(originStr,"嵜","()");
			originStr = replaceStr(originStr,"嵚","()");
			originStr = replaceStr(originStr,"嵙","()");
			originStr = replaceStr(originStr,"嵗","()");
			originStr = replaceStr(originStr,"嵖","()");
			originStr = replaceStr(originStr,"嵕","(き)");
			originStr = replaceStr(originStr,"嵔","(せ)");
			originStr = replaceStr(originStr,"嵓","()");
			originStr = replaceStr(originStr,"嵒","()");
			originStr = replaceStr(originStr,"嵑","()");
			originStr = replaceStr(originStr,"嵐","()");
			ret.setLength(0);
			ret.append(originStr);
		}catch(Exception err){
			logger.warn("ReplaceUtil convertCrmid() originStr-"+ originStr +"err="+err.toString());
			System.out.println("ReplaceUtil convertCrmid() originStr-"+ originStr +"err="+err.toString());
			ret.setLength(0);
			ret.append(originStr);
		}
		return ret.toString();
	}
}