package com.sertek.util;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.sertek.db.DBUtility;

/**
----------------------------------------------------------------------------------------
問題單號：Bug #2785 - 法官置換司法事務官共用 method
修改摘要：新增法官置換司法事務官共用 method
更新版本：V9704-消債&司法事務官
修改人員：nicole
修改日期：0970221,0970304
----------------------------------------------------------------------------------------
問題單號：Bug #3864-3 - TCH0MJ0980001
修改摘要：新增function轉換全形括號(Ex.（二）)與造字括號, 解決V的案號字別用造字而H不用造字, 造成
		 找不到資料的問題。(只限民事修改, 選用筆錄para=1)
更新版本：JUD980220
修改人員：nicole
修改日期：0980701
相關程式：(1)wkj/REFCOURT_02.jsp
-----------------------------------------------------------------------------------------
問題單號：Bug #4868 - TCH0MJ0980001
修改摘要：純置換字串動作, 找到不能換為止。for 檔卷使用
更新版本：JUD980220
修改人員：nicole
修改日期：0990121
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
	 * 純置換字串動作, 只置換一次
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
	 * 純置換字串動作, 找到不能換為止
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
	 * 司法事務官置換字串動作, 共需六個參數
	 * @param db
	 * @param sys - 由於owner值在程式後端會置換成sys值, 故以讀取 sys值來查詢.
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
			//Bug #4022 - 增加 owner 帶入C16 查詢, 當owner="g"時,需要多加osys 以及dblink條件- modify by wythe@20090505
			sql.append("select jdgkd from " + owner + ".c16 ");
			if("g".equals(owner) || "G".equals(owner)){
				sql.append(" where ");
				sql.append("osys='"+sys+"' and ");
			}else{
				sql.append(dblink + " where ");
			}
			sql.append("dptcd='"+dptcd+"'");
System.out.println("[dblink in ReplaceUtil] sql="+sql.toString());
			System.out.println("ReplaceUtil replaceJDGKD()-司法事務官置換  sql="+sql.toString());
			vr = db.doSqlSelect(sql.toString(),1,false);
			System.out.println("ReplaceUtil replaceJDGKD() 置換前 str=["+str+"],oldStr=["+oldStr+"],newStr=["+newStr+"]");
			if(vr.size() > 0){
				if(vr.get(0).equals("A")){ //司法事務官進入置換
					if(!str.equals("")){
						//tmpStr  = util.strToFullSize(str);    //將英數字串轉為全形字串
						tmpStr  = utility.noAnyBlank(str); //將字串內的全形與半形空白全部去除
						if(!tmpStr.equals("")){
							System.out.println("ReplaceUtil replaceJDGKD() tmpStr=["+tmpStr+"]");
							idx = tmpStr.indexOf(oldStr);
							if(idx > -1){
								charOldStr = oldStr.toCharArray();
								if(charOldStr.length > 0){
									//System.out.println("ReplaceUtil replaceJDGKD() charOldStr.length=["+charOldStr.length+"]");
									idx1 = str.indexOf(charOldStr[0]);
									idx2 = idx1 + str.substring(idx1,str.length()).indexOf(charOldStr[charOldStr.length-1]); //用以避免查詢到頭字("法")前面的相同字
									//System.out.println("ReplaceUtil replaceJDGKD() idx1=["+idx1+"],charOldStr[0]=["+charOldStr[0]+"],idx2=["+idx2+"],charOldStr[charOldStr.length-1]=["+charOldStr[charOldStr.length-1]+"]");
									ret.append(str.substring(0, idx1));
									ret.append(newStr);
									ret.append(str.substring((idx2-1) + oldStr.length()));
								}
							}else{
								System.out.println("ReplaceUtil replaceJDGKD() JDGKD='A' 未置換!!!...");
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
	 * 司法事務官置換字串動作, 共需六個參數
	 * @param db
	 * @param owner
	 * @param dptcd
	 * @param str
	 * @return String
	 */
	public String replaceJDGKD(DBUtility db, String sys, String dptcd, String str){
		return replaceJDGKD(db,sys,dptcd,str,"法官","司法事務官");
	}
	
	/**
	 * 置換案號字別中的括號動作, 先置換大寫括號, 再置換造字型括號(1-20)
	 * @param originStr
	 * @return String
	 */
	public String convertCrmid(String originStr){
		StringBuffer ret = new StringBuffer();
		try{
			//轉換大寫括號
			originStr = replaceStr(originStr,"（","(");
			originStr = replaceStr(originStr,"）",")");
			//轉換造字括號1-20
			originStr = replaceStr(originStr,"��","(一)");
			originStr = replaceStr(originStr,"��","(二)");
			originStr = replaceStr(originStr,"��","(三)");
			originStr = replaceStr(originStr,"��","(四)");
			originStr = replaceStr(originStr,"��","(五)");
			originStr = replaceStr(originStr,"��","(六)");
			originStr = replaceStr(originStr,"��","(七)");
			originStr = replaceStr(originStr,"��","(八)");
			originStr = replaceStr(originStr,"��","(九)");
			originStr = replaceStr(originStr,"��","(十)");
			originStr = replaceStr(originStr,"��","(十一)");
			originStr = replaceStr(originStr,"��","(十二)");
			originStr = replaceStr(originStr,"��","(十三)");
			originStr = replaceStr(originStr,"��","(十四)");
			originStr = replaceStr(originStr,"��","(十五)");
			originStr = replaceStr(originStr,"��","(十六)");
			originStr = replaceStr(originStr,"��","(十七)");
			originStr = replaceStr(originStr,"��","(十八)");
			originStr = replaceStr(originStr,"��","(十九)");
			originStr = replaceStr(originStr,"��","(二十)");
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