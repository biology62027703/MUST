package com.sertek.commons.mail;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.sertek.commons.MailHelper;
import com.sertek.commons.mail.MAIL_TEMPLATE;
import com.sertek.sys.StaticObj;
import com.sertek.util.CryptoUtil;

public class MAIL_C60ATTPDF extends MAIL_TEMPLATE {

	
	
	public MAIL_C60ATTPDF(HashMap param) {
		super(param);
	}

	public String getMailSubject() {
		return "";
	}

	public String getMailContent() {
		String mail_content = "";
		try {
			List c60 = (List)param.get("C60");
			HashMap doc_no = new HashMap();
			HashMap att = new HashMap();
			if( c60.size()>0 )
				doc_no = (HashMap)c60.get(0);
			
			mail_content = MailHelper.getMailTemplate("C60ATTPDF.html");
			mail_content = ut.StrTran(mail_content, "@CRTNM@", check.checkNull(doc_no.get("CRTNM"), "").toString());
			mail_content = ut.StrTran(mail_content, "@SYSNM@", getSysnm(check.checkNull(doc_no.get("SYS"), "").toString()));
			mail_content = ut.StrTran(mail_content, "@DOC_NO@", check.checkNull(doc_no.get("DOC_NO"), "").toString());
			mail_content = ut.StrTran(mail_content, "@CRMYY@", check.checkNull(doc_no.get("CRMYY"), "").toString());
			mail_content = ut.StrTran(mail_content, "@CRMID@", check.checkNull(doc_no.get("CRMID"), "").toString());
			mail_content = ut.StrTran(mail_content, "@CRMNO@", check.checkNull(doc_no.get("CRMNO"), "").toString());
			
			String tables = "";
			String table = "";
			String trs = "";
			String tr = "";
			for(int i=0;i<c60.size();i++) {
				
				doc_no = (HashMap)c60.get(i);				
				table = MailHelper.getMailTemplate("C60ATTPDF_TABLE.html");
				table = ut.StrTran(table, "@IDAPTNM@", check.checkNull(doc_no.get("IDAPTNM"), "").toString());
				table = ut.StrTran(table, "@IDNM@", check.checkNull(doc_no.get("IDNM"), "").toString());
				table = ut.StrTran(table, "@USRNM@", check.checkNull(doc_no.get("USRNM"), "").toString());
				table = ut.StrTran(table, "@DOC_NO@", check.checkNull(doc_no.get("DOC_NO"), "").toString());
				table = ut.StrTran(table, "@RCVDT@", ud.formatCDate(check.checkNull(doc_no.get("RCVDT"), "").toString(), "/"));
				table = ut.StrTran(table, "@RCVTM@", ud.formatTime(check.checkNull(doc_no.get("RCVTM"), "").toString(), ":"));				
				table = ut.StrTran(table, "@S_KD@", StaticObj.getSuitkd(check.checkNull(doc_no.get("S_KD"), "").toString()));
				table = ut.StrTran(table, "@C_DT@", ud.formatCDate(check.checkNull(doc_no.get("C_DT"), "").toString(), "/"));
				trs = "";
				List c60_att = (List)doc_no.get("C60_ATT");
				for(int j=0;j<c60_att.size();j++) {
					att = (HashMap)c60_att.get(j);
					tr = MailHelper.getMailTemplate("C60ATTPDF_TABLE_TR.html");
					tr = ut.StrTran(tr, "@S_FTYPE@", getS_FTYPE(check.checkNull(att.get("S_FTYPE"), "").toString()));
					tr = ut.StrTran(tr, "@S_FILE@", check.checkNull(att.get("S_FILE"), "").toString());
					tr = ut.StrTran(tr, "@S_FILEDS@", check.checkNull(att.get("S_FILEDS"), "").toString());
					tr = ut.StrTran(tr, "@USRID@", check.checkNull(att.get("USRID"), "").toString());
					tr = ut.StrTran(tr, "@USRNM@", CryptoUtil.decrypt(check.checkNull(att.get("USRNM"), "").toString()));
					tr = ut.StrTran(tr, "@S_IP@", check.checkNull(att.get("S_IP"), "").toString());
					tr = ut.StrTran(tr, "@S_UPDT@", ud.formatCDate(check.checkNull(att.get("S_UPDT"), "").toString(), "/"));
					tr = ut.StrTran(tr, "@S_UPTM@", ud.formatTime(check.checkNull(att.get("S_UPTM"), "").toString(), ":"));
					tr = ut.StrTran(tr, "@S_FILESZ@", formatFileSize(check.checkNull(att.get("S_FILESZ"), "").toString()));
					if ((check.checkNull(att.get("S_SHA2"), "").toString()).equals("")) {
						tr = ut.StrTran(tr, "@S_CODEFILECONTENT@", "<b>"+check.checkNull(att.get("S_MD5"), "").toString()+"</b>");
					} else { 
						tr = ut.StrTran(tr, "@S_CODEFILECONTENT@", check.checkNull(att.get("S_SHA2"), "").toString());
					}
					trs += tr;
				} 
				
				table = ut.StrTran(table, "@TR@", trs);
				tables+=table;
			}
			mail_content = ut.StrTran(mail_content, "@TABLES@", tables);
		} catch (Exception e) {
			logger.error(e, e);
		}

		return mail_content;
	}
	
	protected String getSysnm(String sys) {
		String ret = "";
		if( sys.equals("H") )
			ret = "刑事";
		else if( sys.equals("V") )
			ret = "民事";
		else if( sys.equals("U") )
			ret = "家事";
		else if( sys.equals("I") )
			ret = "少年";
		else if( sys.equals("A") )
			ret = "行政";
		else if( sys.equals("K") )
			ret = "民執";
		else if( sys.equals("M") )
			ret = "社維";
		return ret;
	}
	
	protected String getS_FTYPE(String S_FTYPE) {
		String ret = "";
		if( S_FTYPE.equals("H") )
			ret = "主要訴狀檔";
		else
			ret = "其他附件檔";
		return ret;
	}
	
	private String formatFileSize(String bytes) {
		BigDecimal KB = new BigDecimal(1024);
		BigDecimal MB = new BigDecimal(1024 * 1024);
		int SCALE = new Integer(2).intValue();
		
		try {
			BigDecimal result = new BigDecimal(bytes).divide(KB, SCALE, BigDecimal.ROUND_HALF_UP);
			if (result.intValue() < 1000) {
				return result.toString() + "KB";
			} else {
				result = new BigDecimal(bytes).divide(MB, SCALE, BigDecimal.ROUND_HALF_UP);
				return result.toString() + "MB";
			}
		} catch (Exception e) {
			return bytes;
		}
	}
}