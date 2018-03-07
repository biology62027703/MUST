package com.sertek.commons;

import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sertek.sys.Project;
import com.sertek.util.CheckObject;
import com.sertek.util.HttpObj;
import com.sertek.util.util_File;

public class SendPayData {
	
	private Logger logger = Logger.getLogger(this.getClass());

	private CheckObject check = new CheckObject();

	private HashMap param = new HashMap();

	public SendPayData(HashMap param) {
		this.param = param;
		run();
	}

	public void run() {
		try {

			createXmlFile();
			
			String url = check.checkNull(param.get("http_url"), "").toString();
			String url2 = check.checkNull(param.get("http_url2"), "").toString();
			
			String responseStr = httpUpload(url);
			
			// 第一組IP失敗再做第二次
			if ("".equals(responseStr)) {
				responseStr = httpUpload(url2);
			}
			
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
	
	private void createXmlFile(){
		String xmlFilenm = Project.getPayDataDir() + check.checkNull(param.get("p_payid"), "") + ".xml";
		logger.info("xmlFilenm = " + xmlFilenm);
		param.put("xmlFilenm", xmlFilenm);
		util_File xmlFile = null;
		
		try {
			xmlFile = new util_File(xmlFilenm, "w");
			
			xmlFile.writeln("<?xml version=\"1.0\" encoding=\"BIG5\"?>");
			xmlFile.writeln("<Profile>");
			xmlFile.writeln("	<crtid>" + check.checkNull(param.get("crtid"), "") + "</crtid>");
			xmlFile.writeln("	<sys>" + check.checkNull(param.get("sys"), "") + "</sys>");
			xmlFile.writeln("	<doc_no>" + check.checkNull(param.get("doc_no"), "") + "</doc_no>");
			xmlFile.writeln("	<p_amt>" + check.checkNull(param.get("p_amt"), "") + "</p_amt>");
			xmlFile.writeln("	<p_payid>" + check.checkNull(param.get("p_payid"), "") + "</p_payid>");
			xmlFile.writeln("	<p_dt>" + check.checkNull(param.get("p_dt"), "") + "</p_dt>");
			xmlFile.writeln("	<p_tm>" + check.checkNull(param.get("p_tm"), "") + "</p_tm>");
			xmlFile.writeln("	<comdt>" + check.checkNull(param.get("comdt"), "") + "</comdt>");
			xmlFile.writeln("	<comtm>" + check.checkNull(param.get("comtm"), "") + "</comtm>");
			xmlFile.writeln("	<acctype>" + check.checkNull(param.get("acctype"), "") + "</acctype>");
			xmlFile.writeln("	<trntype>" + check.checkNull(param.get("trntype"), "") + "</trntype>");
			xmlFile.writeln("	<cddt>" + check.checkNull(param.get("cddt"), "") + "</cddt>");
			xmlFile.writeln("	<p_status>" + check.checkNull(param.get("p_status"), "") + "</p_status>");
			xmlFile.writeln("</Profile>");
			
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			try {
				if (xmlFile != null) {
					xmlFile.close();
				}
			} catch (Exception ignored) {
			}
		}
	}
	
	private String httpUpload(String url) throws Exception {
		String responseStr = "";
		//String url = check.checkNull(param.get("http_url"), "").toString();
		String filenm = check.checkNull(param.get("xmlFilenm"), "").toString();
		
		if (!"".equals(url) && !"".equals(filenm)) {
			logger.info("url = " + url);
			HttpObj client = new HttpObj();
			client.addFile("filenm", new File(filenm));
			if (client.fileUpload(url)) {
				responseStr = client.getRequest();
				if ("".equals(responseStr)) {
					responseStr = "OK";
				}
			} else {
				logger.info("error = " + client.getLastErr());
			}
		} else {
			logger.error("url = " + url);
			logger.error("filenm" + filenm);
		}

		logger.info("responseStr = " + responseStr);
		return responseStr;
	}
}