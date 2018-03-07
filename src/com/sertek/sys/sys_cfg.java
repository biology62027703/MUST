package com.sertek.sys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Hashtable;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

/**
 * 存放系統設定資訊,將sys.cfg 檔案內容常駐在記憶體裏,方便經常使用
 */
public final class sys_cfg extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(sys_cfg.class);

	private static Hashtable table;
	private static String configFile = "";

	public void init() {
		configFile = Project.getConfigDir() + "sys.cfg";
	}

	/**
	 * sys_cfg Constructor 參數:無 傳回:無 說明:建立 sys_cfg 建構子
	 */
	private sys_cfg() {
	}

	static {
		table = new Hashtable();
		reload();
	}

	/**
	 * 參數:無 傳回:無 說明:重新讀取 sys.cfg 內容
	 */
	static public synchronized void reload() {
		File f = null;
		LineNumberReader reader = null;
		String fileEncode = "UTF-8";
		table.clear();
		try {
			configFile = Project.getConfigDir() + "sys.cfg";
			String FileSeperator = System.getProperty("file.separator");

			f = new File(configFile);
			if (!f.exists()) {
				throw new IOException("file " + configFile + " not exists");
			}
			if (FileSeperator.equals("\\"))
				fileEncode = "UTF-8";
			else
				fileEncode = System.getProperty("file.encoding");
			logger.info("編碼=" + fileEncode);
			reader = new LineNumberReader(new BufferedReader(new InputStreamReader(new FileInputStream(f), fileEncode)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				int idx = 0;
				if (line.length() == 0)
					continue;
				if (line.charAt(0) == ';')
					continue;
				if ((idx = line.indexOf("=")) > 0) {
					String head = line.substring(0, idx).trim();
					String value = line.substring(idx + 1).trim();
					table.put(head.toUpperCase(), value);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	/**
	 * 參數:String key:傳進要取得資料的KEY 傳回:String :value 說明:取得一sys.cfg 的value
	 */
	static public String getValue(String key) {
		if (key == null)
			return "";
		return (String) table.get(key.toUpperCase());
	}

	/**
	 * 參數:1.String key:傳進要取得資料的KEY 2.String defaultValue:找不到回傳的質 傳回:String
	 * :value 說明:取得一sys.cfg 的value
	 */
	static public String getValue(String key, String defaultValue) {
		String value = getValue(key);
		if (value == null)
			return defaultValue;
		return value;
	}
}