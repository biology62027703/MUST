<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.sys.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
String cookie_userid = "";
Cookie [] cookies = request.getCookies();

if (cookies!=null){
	 for(Cookie cookie: cookies){
         cookie.setMaxAge(0); 
         cookie.setPath("/"); 
         response.addCookie(cookie);   
	 }
 }

response.sendRedirect(request.getContextPath()+"/LOGIN.jsp");
%>