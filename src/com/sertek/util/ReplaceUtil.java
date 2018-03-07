package com.sertek.util;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.sertek.db.DBUtility;

/**
----------------------------------------------------------------------------------------
���D�渹�GBug #2785 - �k�x�m���q�k�ưȩx�@�� method
�ק�K�n�G�s�W�k�x�m���q�k�ưȩx�@�� method
��s�����GV9704-����&�q�k�ưȩx
�ק�H���Gnicole
�ק����G0970221,0970304
----------------------------------------------------------------------------------------
���D�渹�GBug #3864-3 - TCH0MJ0980001
�ק�K�n�G�s�Wfunction�ഫ���άA��(Ex.�]�G�^)�P�y�r�A��, �ѨMV���׸��r�O�γy�r��H���γy�r, �y��
		 �䤣���ƪ����D�C(�u�����ƭק�, ��ε���para=1)
��s�����GJUD980220
�ק�H���Gnicole
�ק����G0980701
�����{���G(1)wkj/REFCOURT_02.jsp
-----------------------------------------------------------------------------------------
���D�渹�GBug #4868 - TCH0MJ0980001
�ק�K�n�G�¸m���r��ʧ@, ��줣�ഫ����Cfor �ɨ��ϥ�
��s�����GJUD980220
�ק�H���Gnicole
�ק����G0990121
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
	 * �¸m���r��ʧ@, �u�m���@��
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
	 * �¸m���r��ʧ@, ��줣�ഫ����
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
	 * �q�k�ưȩx�m���r��ʧ@, �@�ݤ��ӰѼ�
	 * @param db
	 * @param sys - �ѩ�owner�Ȧb�{����ݷ|�m����sys��, �G�HŪ�� sys�ȨӬd��.
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
			//Bug #4022 - �W�[ owner �a�JC16 �d��, ��owner="g"��,�ݭn�h�[osys �H��dblink����- modify by wythe@20090505
			sql.append("select jdgkd from " + owner + ".c16 ");
			if("g".equals(owner) || "G".equals(owner)){
				sql.append(" where ");
				sql.append("osys='"+sys+"' and ");
			}else{
				sql.append(dblink + " where ");
			}
			sql.append("dptcd='"+dptcd+"'");
System.out.println("[dblink in ReplaceUtil] sql="+sql.toString());
			System.out.println("ReplaceUtil replaceJDGKD()-�q�k�ưȩx�m��  sql="+sql.toString());
			vr = db.doSqlSelect(sql.toString(),1,false);
			System.out.println("ReplaceUtil replaceJDGKD() �m���e str=["+str+"],oldStr=["+oldStr+"],newStr=["+newStr+"]");
			if(vr.size() > 0){
				if(vr.get(0).equals("A")){ //�q�k�ưȩx�i�J�m��
					if(!str.equals("")){
						//tmpStr  = util.strToFullSize(str);    //�N�^�Ʀr���ର���Φr��
						tmpStr  = utility.noAnyBlank(str); //�N�r�ꤺ�����λP�b�Ϊťե����h��
						if(!tmpStr.equals("")){
							System.out.println("ReplaceUtil replaceJDGKD() tmpStr=["+tmpStr+"]");
							idx = tmpStr.indexOf(oldStr);
							if(idx > -1){
								charOldStr = oldStr.toCharArray();
								if(charOldStr.length > 0){
									//System.out.println("ReplaceUtil replaceJDGKD() charOldStr.length=["+charOldStr.length+"]");
									idx1 = str.indexOf(charOldStr[0]);
									idx2 = idx1 + str.substring(idx1,str.length()).indexOf(charOldStr[charOldStr.length-1]); //�ΥH�קK�d�ߨ��Y�r("�k")�e�����ۦP�r
									//System.out.println("ReplaceUtil replaceJDGKD() idx1=["+idx1+"],charOldStr[0]=["+charOldStr[0]+"],idx2=["+idx2+"],charOldStr[charOldStr.length-1]=["+charOldStr[charOldStr.length-1]+"]");
									ret.append(str.substring(0, idx1));
									ret.append(newStr);
									ret.append(str.substring((idx2-1) + oldStr.length()));
								}
							}else{
								System.out.println("ReplaceUtil replaceJDGKD() JDGKD='A' ���m��!!!...");
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
	 * �q�k�ưȩx�m���r��ʧ@, �@�ݤ��ӰѼ�
	 * @param db
	 * @param owner
	 * @param dptcd
	 * @param str
	 * @return String
	 */
	public String replaceJDGKD(DBUtility db, String sys, String dptcd, String str){
		return replaceJDGKD(db,sys,dptcd,str,"�k�x","�q�k�ưȩx");
	}
	
	/**
	 * �m���׸��r�O�����A���ʧ@, ���m���j�g�A��, �A�m���y�r���A��(1-20)
	 * @param originStr
	 * @return String
	 */
	public String convertCrmid(String originStr){
		StringBuffer ret = new StringBuffer();
		try{
			//�ഫ�j�g�A��
			originStr = replaceStr(originStr,"�]","(");
			originStr = replaceStr(originStr,"�^",")");
			//�ഫ�y�r�A��1-20
			originStr = replaceStr(originStr,"��","(�@)");
			originStr = replaceStr(originStr,"��","(�G)");
			originStr = replaceStr(originStr,"��","(�T)");
			originStr = replaceStr(originStr,"��","(�|)");
			originStr = replaceStr(originStr,"��","(��)");
			originStr = replaceStr(originStr,"��","(��)");
			originStr = replaceStr(originStr,"��","(�C)");
			originStr = replaceStr(originStr,"��","(�K)");
			originStr = replaceStr(originStr,"��","(�E)");
			originStr = replaceStr(originStr,"��","(�Q)");
			originStr = replaceStr(originStr,"��","(�Q�@)");
			originStr = replaceStr(originStr,"��","(�Q�G)");
			originStr = replaceStr(originStr,"��","(�Q�T)");
			originStr = replaceStr(originStr,"��","(�Q�|)");
			originStr = replaceStr(originStr,"��","(�Q��)");
			originStr = replaceStr(originStr,"��","(�Q��)");
			originStr = replaceStr(originStr,"��","(�Q�C)");
			originStr = replaceStr(originStr,"��","(�Q�K)");
			originStr = replaceStr(originStr,"��","(�Q�E)");
			originStr = replaceStr(originStr,"��","(�G�Q)");
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