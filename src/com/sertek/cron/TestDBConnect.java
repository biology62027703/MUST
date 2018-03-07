package com.sertek.cron;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.sertek.db.DBUtility;
import com.sertek.util.FormUtil;
public class TestDBConnect implements StatefulJob {

	private Logger logger = Logger.getLogger(this.getClass());

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DBUtility db = null;
		StringBuffer sql = new StringBuffer();
		String tblnms[] = { "C60", "C69", "C6A", "C6V", "C6U", "J60" };
		try {
			db = FormUtil.getDBConnection();

			for (int i = 0; i < tblnms.length; i++) {
				sql.setLength(0);
				sql.append("select count(1) from lsso.." + tblnms[i]);
				logger.info(sql);
				Vector vt = db.doSqlSelect(sql.toString(), 1, false);
				if (vt.size() > 0) {
					logger.info("cnt = " + vt.get(0));
				}
			}
			
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			try {
				db.closeConnection();
			} catch (Exception ignored) {
			}
		}
	}
}