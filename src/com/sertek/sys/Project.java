package com.sertek.sys;

import java.io.File;

/**
 * 記錄本專案的一些參數
 * 
 * @author Administrator
 * 
 */
public class Project {

	public final static String WEBROOT_KEY = new String("DIVA_WEBROOT");
	//public static String WEBROOT_KEY = "";
	public static String getFSRoot() {
		String fs_root_dir = sys_cfg.getValue("fs_root_dir");
		return fs_root_dir;
	}

	public static String getAttDir() {
		return Project.getFSRoot() + "att" + File.separator;
	}

	public static String getCaseDataDir() {
		return Project.getFSRoot() + "fso1a04" + File.separator;
	}

	public static String getCaseDataPdfDir() {
		return Project.getFSRoot() + "fso1a04_pdf" + File.separator;
	}

	public static String getPayDataDir() {
		return Project.getFSRoot() + "fso1a05" + File.separator;
	}
	
	public static String getY6XDataDir() {
		return Project.getFSRoot() + "tso1f01" + File.separator;
	}

	/**
	 * 取得 Web 根目錄
	 * 
	 * @return
	 */
	public static String getWebRoot() {
		return System.getProperty(Project.WEBROOT_KEY);
	}
	
	public static String getConfigDir() {
		return System.getProperty(Project.WEBROOT_KEY) + "config" + File.separator;
	}
	
	public static String getRptiniDir() {
		return System.getProperty(Project.WEBROOT_KEY) + "rptini" + File.separator;
	}
	
	public static String getMailTemplateDir() {
		return System.getProperty(Project.WEBROOT_KEY) + "mail" + File.separator;
	}

	/**
	 * 取得 Web Temp 目錄
	 * 
	 * @return
	 */
	public static String getWebTempDir() {
		return System.getProperty(Project.WEBROOT_KEY) + "temp" + File.separator;
	}
	
	/**
	 * 取得JS路徑目錄
	 * 
	 * @return
	 */
	public static String getScriptDir() {
		return System.getProperty(Project.WEBROOT_KEY) + "script" + File.separator;
	}

	/**
	 * 取得LOG路徑目錄
	 * 
	 * @return
	 */
	public static String getLogDir() {
		return System.getProperty(Project.WEBROOT_KEY) + "logs" + File.separator;
	}

	/**
	 * 取得 sys.cfg 完整路徑
	 * 
	 * @return
	 */
	public static String getSysCfg() {
		return System.getProperty(Project.WEBROOT_KEY) + "config" + File.separator + "sys.cfg";
	}

	/**
	 * 取得 DataSource.xml 完整路徑
	 * 
	 * @return
	 */
	public static String getDataSourceXmlPath() {
		return System.getProperty(Project.WEBROOT_KEY) + "WEB-INF" + File.separator + "dataSource.xml";
	}
	/**
	 * 取得 DataSource.xml 完整路徑
	 * 
	 * @return
	 */
	public static String getDataApplicationContextPath() {
		return System.getProperty(Project.WEBROOT_KEY) + "WEB-INF" + File.separator + "applicationContext.xml";
	}

	public static String getMailErrTxtDir() {
		String fs_root_dir = sys_cfg.getValue("mail_err_txt_dir");
		if( !fs_root_dir.equals("") && !fs_root_dir.endsWith(File.separator) )
			fs_root_dir += File.separator;
		return fs_root_dir;
	}
}
