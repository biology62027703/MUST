package com.sertek.form;

import java.util.HashMap;

public class ResponseBean {

	public enum Status {
		SUCCESS, WARN, ERROR, FAIL, IGNORE, NULL
	}
	//一般網頁或 使用 ajax
	public enum Type {
		GOTOURL, AJAX, TXT
	}
	
	private Status status = Status.SUCCESS;
	private Type type = Type.GOTOURL;
	private String action = "";
	private String gotoUrl = "";
	private String retTxt = "";
	private Object data;

	public void setStatus( Status status ){
		this.status = status;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Status getStatus(){
		return status;
	}
	
	public String getAction() {
		return action;
	}
	
	public Type getType() {
		return type;
	}

	public String getGotoUrl() {
		return gotoUrl;
	}
	
	public String getRetTxt() {
		return retTxt;
	}
	
	public void setRetTxt(String retTxt) {
		this.type = Type.TXT;
		this.retTxt = retTxt;
	}

	/**
	 * 設定回傳 url
	 * @param gotoUrl
	 */
	public void setGotoUrl(String gotoUrl) {
		this.type = Type.GOTOURL;
		this.gotoUrl = gotoUrl;
	}
	
	/**
	 * 設定 使用 ajax
	 * @param data
	 */
	public void setAjaxData(Object data) {
		this.type = Type.AJAX;
		this.status = Status.SUCCESS;
		this.data = data;
	}
	
	public static ResponseBean newInstance() {
		return new ResponseBean();
	}

}