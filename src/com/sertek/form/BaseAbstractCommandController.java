package com.sertek.form;

import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.google.gson.Gson;
import com.sertek.ibatis.util.SqlDBUtility_MUST_WEB;
import com.sertek.ibatis.util.SqlDBUtility_ERP;
import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.ibatis.util.SqlDBUtility_51;
import com.sertek.ibatis.util.SqlDBUtility_LICENSE;
import com.sertek.sys.sys_User;
import com.sertek.util.CheckObject;
import com.sertek.util.FormUtil;

public class BaseAbstractCommandController extends AbstractCommandController {

	protected Logger logger = Logger.getLogger(this.getClass());
	protected SqlDBUtility_MUST_WEB SqlDBUtility_MUST_WEB = null;
	protected SqlDBUtility_51 SqlDBUtility_51 = null;
	protected SqlDBUtility_ERP SqlDBUtility_ERP = null;
	protected SqlDBUtility SqlDBUtility = null;
	protected SqlDBUtility_LICENSE SqlDBUtility_LICENSE = null;
	
	protected CheckObject check = new CheckObject();
	protected com.sertek.file.fileUtil fu = new com.sertek.file.fileUtil();
	protected com.sertek.util.util_date ud = new com.sertek.util.util_date();
	protected com.sertek.util.utility ut = new com.sertek.util.utility();
	protected com.sertek.file.FileUpload fileUpload = null;
	
	protected String action = "";
	protected String method = "";
	
	public SqlDBUtility_LICENSE getSqlDBUtility_LICENSE() {
		return SqlDBUtility_LICENSE;
	}

	public void setSqlDBUtility_LICENSE(SqlDBUtility_LICENSE sqlDBUtility_LICENSE) {
		SqlDBUtility_LICENSE = sqlDBUtility_LICENSE;
	}

	public SqlDBUtility getSqlDBUtility_DIVA() {
		return SqlDBUtility;
	}

	public void setSqlDBUtility_DIVA(SqlDBUtility sqlDBUtility_DIVA) {
		SqlDBUtility = sqlDBUtility_DIVA;
	}

	public SqlDBUtility_ERP getSqlDBUtility_ERP() {
		return SqlDBUtility_ERP;
	}

	public void setSqlDBUtility_ERP(SqlDBUtility_ERP sqlDBUtility_ERP) {
		SqlDBUtility_ERP = sqlDBUtility_ERP;
	}
	
	public SqlDBUtility_51 getSqlDBUtility_51() {
		return SqlDBUtility_51;
	}

	public void setSqlDBUtility_51(SqlDBUtility_51 sqlDBUtility_51) {
		SqlDBUtility_51 = sqlDBUtility_51;
	}
	
	public SqlDBUtility_MUST_WEB getSqlDBUtility_MUST_WEB() {
		return SqlDBUtility_MUST_WEB;
	}

	public void setSqlDBUtility_MUST_WEB(SqlDBUtility_MUST_WEB sqlDBUtility_MUST_WEB) {
		this.SqlDBUtility_MUST_WEB = sqlDBUtility_MUST_WEB;
	}

	
	public BaseAbstractCommandController() {
		synchronized (this) {
		}
		;
		setCommandClass(HashMap.class);
	}

	public sys_User getUser(HttpServletRequest request) {
		if (request.getSession().getAttribute("User") != null) {
			return (sys_User) request.getSession().getAttribute("User");
		}else{
			return null;
		}
	}

	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object obj, BindException arg3) throws Exception {
		
		action = check.checkNull(request.getParameter("action"), "").toString();
		method = check.checkNull(request.getParameter("method"), "").toString();
		
		String logInfoPrefix = "(" + this.getClass().getName() + "." + action + ") - ";
		logger.info(logInfoPrefix + "start");
		
		HashMap form = buildParameterForm(request);
		ResponseBean responseBean = ResponseBean.newInstance();
		
		if(action.equals(""))
			action = "handle";

		try {
			Class[] paramType = { HttpServletRequest.class, HttpServletResponse.class, HashMap.class, ResponseBean.class };
			Method setMethod = this.getClass().getMethod(action, paramType);
			Object[] paramValue = { request, response, form, responseBean };
			setMethod.invoke(this, paramValue);
		} catch (Exception e) {
			logger.error(logInfoPrefix + "throws Exception");
			logger.error(e, e);
		}
		logger.info(logInfoPrefix + "finish");

		if( responseBean.getType().equals(ResponseBean.Type.AJAX) ) {
			String ret = new Gson().toJson(responseBean);
			response.setContentType("text/html; charset=UTF-8");
			response.getOutputStream().write(ret.getBytes("UTF-8"));
			response.getOutputStream().close();
			return null;
		} else if ( responseBean.getType().equals(ResponseBean.Type.GOTOURL) ) {
			logger.info(logInfoPrefix + "gotoUrl = " + responseBean.getGotoUrl());
			return new ModelAndView(responseBean.getGotoUrl(), "form", form);
		} else if ( responseBean.getType().equals(ResponseBean.Type.TXT) ) {
			String ret = responseBean.getRetTxt();
			response.setContentType("text/html; charset=UTF-8");
			response.getOutputStream().write(ret.getBytes("UTF-8"));
			response.getOutputStream().close();
			return null;
		} else {
			return null;
		}
	}
	
	protected HashMap buildParameterForm(HttpServletRequest request) throws Exception {
		
		HashMap form = null;
		if( org.apache.commons.fileupload.FileUpload.isMultipartContent(request) ) {
			System.out.println("isMultipartContent");
			fileUpload = new com.sertek.file.FileUpload();
			fileUpload.isChangeFilenameByTimeRule = false;
			form = fileUpload.RequestToHashMap(request);		
        }else{
			System.out.println("isNotMultipartContent");
        	form = FormUtil.RequetToHashMap(request);
        }
		logger.info("parameter = " + form.toString());
		return form;
	}
	
	public String getIpAddr(HttpServletRequest request) {   
		String ip = request.getHeader("x-forwarded-for");   
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("Proxy-Client-IP");  
		}   
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("WL-Proxy-Client-IP");  
		}   
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getRemoteAddr();  
		}  
		return ip.split("[;,]")[0]; 
	} 
	
}