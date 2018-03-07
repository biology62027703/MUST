package com.sertek.commons;

import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.sertek.file.fileUtil;
import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.util.CheckObject;
import com.sertek.util.HttpObj;
import com.sertek.util.util_date;

public class SendCaseData  {

	private Logger logger = Logger.getLogger(this.getClass());	
	private CheckObject check = new CheckObject();	
	private util_date ud = new util_date();
	private SqlDBUtility sqlDBUtility = null;	
	private HashMap param = new HashMap();
	private fileUtil fu = new fileUtil();

	public SendCaseData(SqlDBUtility sqlDBUtility, HashMap param) {
		this.sqlDBUtility = sqlDBUtility;
		this.param = param;
		run();
	}

	public void run() {
		try {
			// 新增sndSO2Clog
			if (!"Y".equals(check.checkNull(param.get("resend"), ""))) {
				insertSndSO2Clog();
			}

			// 傳送檔案取回xml
			String url = check.checkNull(param.get("http_url"), "").toString();
			String url2 = check.checkNull(param.get("http_url2"), "").toString();
			
			String xmldata = httpUpload(url);			
			// 第一組IP失敗再做第二次
			if ( "".equals(xmldata) && url2.indexOf("http://")>-1 ) {
				xmldata = httpUpload(url2);
			}

			// 更新sndSO2Clog
			updateSndSO2Clog(xmldata);
		} catch (Exception e) {
			logger.error(e, e);

			HashMap logMap = new HashMap();
			logMap.put("crtid", check.checkNull(param.get("crtid"), ""));
			logMap.put("sys", check.checkNull(param.get("sys"), ""));
			logMap.put("doc_no", check.checkNull(param.get("doc_no"), ""));
			logMap.put("sendkd", "N");
			sqlDBUtility.update("FSO1A04.updateSndSO2Clog", logMap);
		}
	}
	
	private String httpUpload(String url) throws Exception {
		
		String xmldata = "";
		String filenm = toRealFileNm(check.checkNull(param.get("encZipFilenm"), "").toString());
		
		if (!"".equals(url) && !"".equals(filenm)) {
			logger.info("url = " + url);
			logger.info("filenm = " + filenm);
			HttpObj client = new HttpObj();
			client.setReadTimeOut(10*60);
			client.addFile("filenm", new File(filenm));
			if (client.fileUpload(url)) {
				xmldata = client.getRequest();
			} else {
				logger.info("error = " + client.getLastErr());
			}
		} else {
			logger.error("url = " + url);
			logger.error("filenm" + filenm);
		}

		return xmldata;
	}
	
	private String toRealFileNm(String filenm) {
		
		String crtid = check.checkNull(param.get("crtid"), "").toString();
		String sys = check.checkNull(param.get("sys"), "").toString();
		String doc_no = check.checkNull(param.get("doc_no"), "").toString();
		
		if( "IPCTPBTCBKSB".indexOf(crtid)==-1 ) {
			String newfilenm = getFilePath(filenm) + crtid + (sys.equals("V") ? "3" : "1") + sys + doc_no + ".ok";
			
			File file1 = new File(filenm);
			File file2 = new File(newfilenm);
			
			if( fu.checkFile(filenm) ) {
				if( !file2.exists() ) {
					file1.renameTo(file2);
				}
			}
			filenm = newfilenm;
		}		
		return filenm;
	}
	
	private String getFilePath(String filenm) {
		File file = new File(filenm);
		return file.getPath().replaceAll(file.getName(), "");		
	}
	
	private void insertSndSO2Clog() throws Exception {
		HashMap logMap = new HashMap();
		logMap.put("crtid", check.checkNull(param.get("crtid"), ""));
		logMap.put("sys", check.checkNull(param.get("sys"), ""));
		logMap.put("s_kd", check.checkNull(param.get("s_kd"), ""));
		logMap.put("doc_no", check.checkNull(param.get("doc_no"), ""));
		logMap.put("s_updt", ud.nowCDate());
		logMap.put("s_uptm", ud.nowTime());
		logMap.put("sendkd", "S");
		sqlDBUtility.insert("sndSO2Clog.insertSelective", logMap);
	}
	
	private void updateSndSO2Clog(String xmldata) throws Exception {
		try {
			String[] tags = { "crtid", "sys", "doc_no", "c_status", "comdt", "comtm" };

			Document doc = XMLHelper.getXmlDoc(xmldata);

			HashMap logMap = new HashMap();
			for (int i = 0; i < tags.length; i++) {
				String key = tags[i];
				String value = XMLHelper.getText(doc, tags[i]);
				logMap.put(key, value);
			}

			// 重傳成功
			logMap.put("sendkd", "I");
			if ("Y".equals(check.checkNull(param.get("resend"), ""))) {
				logMap.put("sendkd", "B");
			}			
			// 傳送失敗
			if (!"Y".equals(check.checkNull(logMap.get("c_status"), ""))) {
				logMap.put("sendkd", "N");
			}

			logMap.put("crtid", check.checkNull(param.get("crtid"), ""));
			logMap.put("sys", check.checkNull(param.get("sys"), ""));
			logMap.put("doc_no", check.checkNull(param.get("doc_no"), ""));
			sqlDBUtility.update("FSO1A04.updateSndSO2Clog", logMap);
		} catch (Exception e) {
			logger.info(e.getMessage());

			HashMap logMap = new HashMap();
			logMap.put("crtid", check.checkNull(param.get("crtid"), ""));
			logMap.put("sys", check.checkNull(param.get("sys"), ""));
			logMap.put("doc_no", check.checkNull(param.get("doc_no"), ""));
			logMap.put("sendkd", "N");
			sqlDBUtility.update("FSO1A04.updateSndSO2Clog", logMap);
		}
	}
}