package com.sertek.spring.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sertek.sys.sys_User;


public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

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
			// modelAndView.addObject("signonForwardAction", url + "?" + query);
		} else {
			ModelAndView modelAndView = new ModelAndView(this.getErrorView());
			modelAndView.addObject("errorMsg", "請輸入帳號與密碼!");
			throw new ModelAndViewDefiningException(modelAndView);
		}
		return true;
	}
}