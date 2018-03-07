package com.sertek.cron;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.sertek.sys.Project;

public class CleanBackupLog implements StatefulJob {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("CleanBackupLog start");
		String logDir = Project.getLogDir();
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -60);
			Date baseDt = new Date(cal.getTimeInMillis());

			File dir = new File(logDir);
			File[] files = dir.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					File aFile = files[i];
					if (aFile.getName().startsWith("fileLog")) {
						Date logDt = new Date(aFile.lastModified());

						if (logDt.compareTo(baseDt) < 0) {
							System.out.println("CleanBackupLog " + aFile.getName());
							aFile.delete();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("CleanBackupLog finish");
	}
}
