package com.sertek.spring.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sertek.sys.sys_User;

public class AdminInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger(this.getClass());

	private String errorView = "";

	public String getErrorView() {
		return errorView;
	}

	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		sys_User User = (sys_User) request.getSession().getAttribute("User");

		if (User != null) {
			String sPriv = User.readString("S_PRIV");
			if (!"T".equals(sPriv)) {
				logger.warn("ip = " + request.getRemoteAddr() + ", usrid = " + User.readString("S_USRID") + ", priv = " + User.readString("S_PRIV"));
				logger.warn("AdminInterceptor!!!");
				ModelAndView modelAndView = new ModelAndView(this.getErrorView());
				modelAndView.addObject("errorMsg", "請輸入帳號與密碼!");
				throw new ModelAndViewDefiningException(modelAndView);
			}
		} else {
			ModelAndView modelAndView = new ModelAndView(this.getErrorView());
			modelAndView.addObject("errorMsg", "請輸入帳號與密碼!");
			throw new ModelAndViewDefiningException(modelAndView);
		}

		return true;
	}
}