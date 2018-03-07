package com.sertek.cron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.sertek.util.utility;

public class Crontab extends HttpServlet {
	private Logger logger = Logger.getLogger(this.getClass());
	private String iniPath = "";
	private SchedulerFactory sf = null;
	private Scheduler sched = null;
	private Vector run_job = new Vector();// �s�b�ثe���h�� job �b�]

	class cron_list {
		private utility ut = new utility();
		private String time = "";
		private String type = "";
		private String command = "";
		private int row = -1;
		private BufferedReader br = null;
		Vector list = new Vector();

		public cron_list(File f) {
			try {
				logger.info("���J " + f.getCanonicalPath() + " ��...");
				br = new BufferedReader(new FileReader(f));
				String line = "";
				while ((line = br.readLine()) != null) {
					line = line.trim();
					if (line.indexOf("#") != 0) {
						String[] lineArray = ut.toStringArray(line, "!");
						if (lineArray != null && lineArray.length == 3) {
							Hashtable h = new Hashtable();
							h.put("time", lineArray[0]);
							h.put("type", lineArray[1]);
							h.put("command", lineArray[2]);
							list.add(h);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (Exception e) {

				}
			}

		}

		public int getCount() {
			return list.size();
		}

		public boolean next() {
			row++;
			boolean retVal = false;
			if (row < list.size()) {
				this.time = ((String) ((Hashtable) list.get(row)).get("time")).trim();
				this.type = ((String) ((Hashtable) list.get(row)).get("type")).trim();
				this.command = ((String) ((Hashtable) list.get(row)).get("command")).trim();
				retVal = true;
			} else {
				row--;
			}
			return retVal;
		}

		public String getTime() {
			return this.time;
		}

		public String getType() {
			return this.type;
		}

		public String getCommand() {
			return this.command;
		}
	}

	public void init() {
		logger.info("sys_Crontab.init() ...");

		sf = new StdSchedulerFactory();

		try {
			sched = sf.getScheduler(); // ���oscheduler��reference
			int jobflag = 0;
			iniPath = getServletConfig().getServletContext().getRealPath("/") + File.separator + "config" + File.separator + "crontab.ini";
			File cronIni = new File(iniPath);
			if (cronIni.exists()) {

				cron_list cronList = new cron_list(cronIni);

				while (cronList.next()) {
					jobflag++;
					// JobDetail job2 = new JobDetail("job2", "group1",
					// SayHelloJob.class);
					// getServletConfig().getServletContext().

					// job1.getJobDataMap().put("url",url);
					// job1.getJobDataMap().put(MisfireJob.EXECUTION_DELAY,
					// 10000L);
					// job2.getJobDataMap().put("name","����_2");
					if (cronList.getType().equals("2")) {
						String[] commands = cronList.getCommand().split(" ");
						String className = commands[0];
						String[] args = null;
						if (commands.length > 1) {
							args = new String[commands.length - 1];
							for (int i = 0; i < args.length; i++) {
								args[i] = commands[i + 1];
							}
						}
						JobDetail job = new JobDetail("job" + jobflag, Scheduler.DEFAULT_GROUP, Class.forName(className));
						job.getJobDataMap().put("args", args);
						CronTrigger cTrigger = new CronTrigger("trigg" + jobflag, Scheduler.DEFAULT_GROUP);
						cTrigger.setCronExpression(cronList.getTime());

						Date ft1 = sched.scheduleJob(job, cTrigger);
						run_job.add(job.getName());
						logger.info("class " + cronList.getCommand() + " start at:" + ft1); // �O����ɶ}�l����u�@
					} else if (cronList.getType().equals("1")) {
						JobDetail job = new JobDetail("job" + jobflag, Scheduler.DEFAULT_GROUP, com.sertek.cron.RunCommand.class);
						job.getJobDataMap().put("command", cronList.getCommand());
						CronTrigger cTrigger = new CronTrigger("trigg" + jobflag, Scheduler.DEFAULT_GROUP);
						cTrigger.setCronExpression(cronList.getTime());
						Date ft1 = sched.scheduleJob(job, cTrigger);
						run_job.add(job.getName());
						logger.info("command " + cronList.getCommand() + " start at:" + ft1); // �O����ɶ}�l����u�@
					}
				}
				cronList = null;

				sched.start();
			} else {
				logger.warn(iniPath + " ���s�b..�A����Ƶ{");
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	protected void doGet(HttpServletRequest arg0, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doHead(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO �۰ʲ��ͤ�k Stub
		super.doHead(arg0, arg1);
	}

	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO �۰ʲ��ͤ�k Stub
		super.doPost(arg0, arg1);
	}

	protected void doPut(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO �۰ʲ��ͤ�k Stub
		super.doPut(arg0, arg1);

	}

	protected void service(HttpServletRequest arg0, HttpServletResponse response) throws ServletException, IOException {
		// TODO �۰ʲ��ͤ�k Stub
		super.service(arg0, response);
	}

	public void service(ServletRequest arg0, ServletResponse response) throws ServletException, IOException {
		// TODO �۰ʲ��ͤ�k Stub
		logger.info("sys_Crontab.doGet start....");

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		out.println("<html><body>");
		try {
			for (int i = 0; i < run_job.size(); i++) {
				try {
					sched.deleteJob((String) run_job.get(i), Scheduler.DEFAULT_GROUP);
					logger.info("�R�� " + (String) run_job.get(i));
				} catch (SchedulerException e) {
					logger.error(e, e);
				}
			}
			run_job.clear();
			int jobflag = 0;
			File cronIni = new File(iniPath);
			if (cronIni.exists()) {

				cron_list cronList = new cron_list(cronIni);
				if (cronList.getCount() > 0) {
					while (cronList.next()) {
						jobflag++;
						if (cronList.getType().equals("2")) {
							String[] commands = cronList.getCommand().split(" ");
							String className = commands[0];
							String[] args = null;
							if (commands.length > 1) {
								args = new String[commands.length - 1];
								for (int i = 0; i < args.length; i++) {
									args[i] = commands[i + 1];
								}
							}
							JobDetail job = new JobDetail("job" + jobflag, Scheduler.DEFAULT_GROUP, Class.forName(className));
							job.getJobDataMap().put("args", args);
							CronTrigger cTrigger = new CronTrigger("trigg" + jobflag, Scheduler.DEFAULT_GROUP);
							cTrigger.setCronExpression(cronList.getTime());
							Date ft1 = sched.scheduleJob(job, cTrigger);

							run_job.add(job.getName());
							out.println("���J " + cronList.getCommand() + " start at:" + ft1 + "<BR>");
							logger.info("class " + cronList.getCommand() + " start at:" + ft1); // �O����ɶ}�l����u�@
						} else if (cronList.getType().equals("1")) {
							JobDetail job = new JobDetail("job" + jobflag, Scheduler.DEFAULT_GROUP, com.sertek.cron.RunCommand.class);
							job.getJobDataMap().put("command", cronList.getCommand());
							CronTrigger cTrigger = new CronTrigger("trigg" + jobflag, Scheduler.DEFAULT_GROUP);
							cTrigger.setCronExpression(cronList.getTime());
							Date ft1 = sched.scheduleJob(job, cTrigger);
							run_job.add(job.getName());
							out.println("���J " + cronList.getCommand() + " start at:" + ft1 + "<BR>");
							logger.info("command " + cronList.getCommand() + " start at:" + ft1); // �O����ɶ}�l����u�@
						}
					}
				} else {
					out.println("�S������Ƶ{���J<BR>");
				}
				cronList = null;

				sched.start();

			} else {
				logger.warn(iniPath + " ���s�b..�A����Ƶ{");
				out.println(iniPath + " ���s�b..�A����Ƶ{<BR>");
			}

		} catch (Exception e) {
			logger.error(e, e);
			out.println("�B�z�Ƶ{�o�Ϳ��~�A���~�T���G" + e.getMessage());
		}
		out.println("</html></body>");

	}

	protected void doDelete(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO �۰ʲ��ͤ�k Stub
		logger.info("�Q�R��..");
	}

	public void destroy() {
		// TODO �۰ʲ��ͤ�k Stub
		try {
			if (!sched.isShutdown()) {
				for (int i = 0; i < run_job.size(); i++) {
					try {
						sched.deleteJob((String) run_job.get(i), Scheduler.DEFAULT_GROUP);
						logger.info("�R�� " + (String) run_job.get(i));
					} catch (SchedulerException ex) {
						ex.printStackTrace();
					}
				}
				sched.shutdown(true);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}

		super.destroy();
	}

}
