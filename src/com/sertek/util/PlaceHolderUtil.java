package com.sertek.util;

import org.apache.log4j.Logger;

/**
 * @author AminLA
 *
 */
public class PlaceHolderUtil {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private String str;
	private String placeHolder = "?";
	
	public PlaceHolderUtil(String str) {
		this.str = str;
	}
	public PlaceHolderUtil(String str, String placeHolder) {
		this.str = str;
		this.placeHolder = placeHolder;
	}	
	
	public void add(Object val) {
		String tmp = val == null ? "" : (String)val;
		this.str = replaceFirst(this.str, this.placeHolder, "'" + tmp + "'");
	}
	public void add(String val) {
		String tmp = val == null ? "" : val;
		this.str = replaceFirst(this.str, this.placeHolder, "'" + tmp + "'");
	}
	
	public void addNum(Object val) {
		String tmp = val == null ? "" : (String)val;
		this.str = replaceFirst(this.str, this.placeHolder, tmp);
	}
	public void addNum(String val) {
		String tmp = val == null ? "" : val;
		this.str = replaceFirst(this.str, this.placeHolder, tmp);
	}	
	
	/**
	 * <pre> e.g. 
	 * 	set("crmyy=", val, " trim(crmyy) is null ")
	 * 	如果 val == null 則使用 nullArg 
	 * </pre>
	 * @param col
	 * @param val
	 * @param nullArg
	 */
	public void set(String col, String val, String nullArg) {
		if(val == null) {
			this.str = replaceFirst(this.str, this.placeHolder, nullArg);
		} else {
			this.str = replaceFirst(this.str, this.placeHolder, col + "'" + val + "'");
		}
	}	
	public void set(String col, Object val, String nullArg) {
		set(col, (String)val, nullArg);
	}
	
	public String toString() {
		logger.debug(this.str);
		return this.str;
	}
	
	private String replaceFirst(String str, String oldStr, String newStr) {
		StringBuffer ret = new StringBuffer();
		try {
			int idx = str.indexOf(oldStr); 
			if(idx != -1) {
				ret.append(str.substring(0, idx));
				ret.append(newStr);
				ret.append(str.substring(idx + oldStr.length()));
			} else {
				ret.append(str);
			}
		} catch(Exception err) {
			logger.warn("can not find " + oldStr + " from " + str + " \tnewStr:" + newStr);
			ret.setLength(0);
			ret.append(str);
		}
		if(ret.length() > 0) {
			this.str = ret.toString();
		}
		return ret.toString();
	}	
}
