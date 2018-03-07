package com.sertek.form;

import java.util.HashMap;
import java.util.List;

import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.table.SUITKD;

public class SOLHelper {
	
	public static String DEFAULT_S_CAUSE = new String("訴願決定日期及字號：○○○機關○○年○月○日○○字第○○○號\n收受訴願決定日期：○○年○月○日\n原告因商標評定事件，提起行政訴訟：\n");
	public static String DEFAULT_S_STMT = new String("\n");
	public static String DEFAULT_S_FACT = new String("\n");
	public static String DEFAULT_S_EVD = new String("一、原處分影本X件。\n二、訴願決定書影本X件。\n");

	/**
	public static String getS_kdnm(String s_kd) {
		if ("S".equals(s_kd)) {
			return "起訴狀";
		} else if ("C".equals(s_kd)) {
			return "聲請狀";
		} else {
			return s_kd;
		}
	}
	*/
	
	public static String getS_kdnm(SqlDBUtility sqlDBUtility, String s_kd) {
		if ("S".equals(s_kd)) {
			return "起訴狀";
		} else if ("C".equals(s_kd)) {
			return "聲請狀";
		} else if ("AZ".equals(s_kd)) {
			return "上訴狀";
		} else {
			HashMap suitkdKey = new HashMap();
			suitkdKey.put("s_kd", s_kd);
			List suitkdList = sqlDBUtility.queryForList("SUITKD.queryByKey", suitkdKey);
			if (suitkdList.size() > 0) {
				SUITKD suitkd = (SUITKD) suitkdList.get(0);
				return suitkd.getSuit_nm();
			} else {
				return s_kd;
			}
		}
	}

	public static String getS_stmtnm(String s_kd) {
		if ("S".equals(s_kd) || "AZ".equals(s_kd)) {
			return "訴之聲明";
		} else {
			return "請求事項";
		}
	}
	
	public static String getC_statusnm(String c_status) {
		// 案件狀態：N/未遞狀 Y/完成遞狀 C/已分案 O/補充狀
		if ("N".equals(c_status)) {
			return "未遞狀";
		} else if ("Y".equals(c_status)) {
			return "完成遞狀";
		} else if ("C".equals(c_status)) {
			return "已分案";
		} else if ("O".equals(c_status)) {
			return "補充狀";
		} else {
			return c_status;
		}
	}
	
	public static String getP_statusnm(String p_status) {
		// 繳費狀態：N/待繳費(執行繳費) S/待繳費(虛擬帳號)  Y/完成繳費 空白/可再繳費(未繳費)
		if("".equals(p_status)){
			return "未繳費";
		} else  if ("N".equals(p_status)) {
			return "待繳費";
		} else if ("S".equals(p_status)) {
			return "待繳費";
		} else if ("Y".equals(p_status)) {
			return "完成繳費";
		} else {
			return p_status;
		}
	}
	
	public static String getCtype(String crtid) {
		if ("IPC".equals(crtid)) {
			return "ZIF";
		} else if (crtid.endsWith("B") || crtid.equals("TPA")) {
			return "Z01";
		} else if (crtid.endsWith("D") || crtid.endsWith("E") || crtid.endsWith("H") || crtid.equals("KSY") || crtid.equals("TPS")) {
			return "Z21";
		} else {
			return "ZIF";
		}
	}
	
	public static String getP_amt(String crtid, String s_kd) {
		if ("IPC".equals(crtid)) {
			if ("S".equals(s_kd)) {
				return "4000";
			} else if ("AZ".equals(s_kd)) {
				return "6000";
			} else if ("AY".equals(s_kd)) {
				return "1000";
			} else if ("C".equals(s_kd)) {
				return "2000";
			} else {
				return "4000";
			}
		} else if (crtid.endsWith("B")) {
			if ("S".equals(s_kd)) {
				return "4000";
			} else if ("AZ".equals(s_kd)) {
				return "6000";
			} else if ("AY".equals(s_kd)) {
				return "1000";
			} else if ("C".equals(s_kd)) {
				return "2000";
			} else {
				return "4000";
			}
		} else if (crtid.endsWith("D")) {
			if ("S".equals(s_kd)) {
				return "2000";
			} else if ("AZ".equals(s_kd)) {
				return "3000";
			} else if ("AY".equals(s_kd)) {
				return "500";
			} else if ("C".equals(s_kd)) {
				return "1000";
			} else {
				return "2000";
			}
		} else {
			return "4000";
		}
	}

	public static String getSexnm(String sex) {
		if ("M".equals(sex)) {
			return "男";
		} else if ("F".equals(sex)) {
			return "女";
		} else if ("C".equals(sex)) {
			return "法人";
		} else {
			return sex;
		}
	}
	
	public static String getUnreadCnt(String doc_cnt, String yesread) {
		int result = 0;
		try{
			if (!"".equals(doc_cnt)) {
				result = Integer.parseInt(doc_cnt);
			}
			if (!"".equals(yesread)) {
				result = result - Integer.parseInt(yesread);
			}
		} catch(Exception e){
		} finally{
			if (result < 0) {
				result = 0;
			}
		}
		return "" + result;
	}
	
	public static String getUnreadCnt(String doc_cnt, String r1, String r2, String r3, String r4, String r5) {
		int result = 0;
		try{
			if (!"".equals(doc_cnt)) {
				result = Integer.parseInt(doc_cnt);
			}
			if (!"".equals(r1)) {
				result = result - Integer.parseInt(r1);
			}
			if (!"".equals(r2)) {
				result = result - Integer.parseInt(r2);
			}
			if (!"".equals(r3)) {
				result = result - Integer.parseInt(r3);
			}
			if (!"".equals(r4)) {
				result = result - Integer.parseInt(r4);
			}
			if (!"".equals(r5)) {
				result = result - Integer.parseInt(r5);
			}
		} catch(Exception e){
		} finally{
			if (result < 0) {
				result = 0;
			}
		}
		return "" + result;
	}
}