package com.sertek.cron;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.sertek.sys.sys_cfg;

public class RunJsp implements StatefulJob {

	private Logger logger = Logger.getLogger(this.getClass());

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			logger.info(this.getClass() + " begin");

			JobDataMap dataMap = arg0.getJobDetail().getJobDataMap();
			String args[] = (String[]) dataMap.get("args");
			String jspnm = "";
			if (args != null) {
				jspnm = args[0];
			}
			logger.info("jspnm = " + jspnm);

			String default_url = "http://localhost/SOL/";
			
			String url = sys_cfg.getValue("root_url", default_url) + jspnm;
			logger.info("url = " + url);

			HttpClient httpClient = new HttpClient();
			PostMethod method = new PostMethod(url);

			httpClient.setConnectionTimeout( 10*1000 );
			httpClient.setTimeout(29*60*1000);
			
			int statusCode = httpClient.executeMethod(method);
			logger.info("statusText = " + HttpStatus.getStatusText(statusCode));

			logger.info(this.getClass() + " end");
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
}
