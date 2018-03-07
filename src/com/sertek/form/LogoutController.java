package com.sertek.form;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class LogoutController extends AbstractCommandController {
	
	private String logoutPage = "REDIRECTINDEX.jsp";

	public LogoutController() {
		setCommandClass(HashMap.class);
	}
	
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object form, BindException arg3) throws Exception {
		request.getSession().removeAttribute("User");
		request.getSession().removeAttribute("Helper");
		return new ModelAndView(logoutPage);
	}
}