<!DOCTYPE> 
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.sys.*,com.sertek.util.Base64Decoder" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%  
	//由COOKIE取出上次登入時的帳號 
	
	String cookie_userid = "";
	Cookie [] cookie = request.getCookies();
	CheckObject check = new CheckObject();
	String url = (String)check.checkNull(request.getParameter("url"), "") ;
	if (cookie!=null){
		for(int i=0;i<cookie.length;i++){
			if("user_group".equals(cookie[i].getName())){
				System.out.println("使用者已登入");
				response.sendRedirect("INDEX.jsp");
			}
		}
	}
	System.out.println("-----login.jsp-----");
	String cnt = "";	
	String errmsg = "";
	String sol_valid =  "";
	String usridtxt =  "";
	String pswdtxt =  "";

	errmsg = "";
	usridtxt =  (String)check.checkNull(request.getParameter("usridtxt"), "");
	pswdtxt =  (String)check.checkNull(request.getParameter("pswdtxt"), "");
	if(!pswdtxt.equals("")){
		usridtxt=Base64Decoder.decode(usridtxt);
		pswdtxt=Base64Decoder.decode(pswdtxt);
		ADCHECK ADCHECK = new ADCHECK(usridtxt,pswdtxt);
		String group = ADCHECK.LDAP_AUTH_AD();
		System.out.println("權限:"+group);
		if(!group.equals("")) {
			if("12345".indexOf(group)>-1) {
				Cookie cookie1 = new Cookie("user_group", group);
				cookie1.setMaxAge(86400);
				cookie1.setPath("/"); 
				response.addCookie(cookie1);	
				cookie1 = new Cookie("user_name", usridtxt);
				cookie1.setMaxAge(86400);
				cookie1.setPath("/"); 
				response.addCookie(cookie1);
				if(url.equals("")) {
					response.sendRedirect("INDEX.jsp");	
				} else {
					response.sendRedirect(url);	
				}
			} else {
				errmsg = group;
			}
		}
	}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="icon" type="<%=request.getContextPath()%>/image/png" sizes="16x16" href="<%=request.getContextPath()%>/images/logo.png">
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" type="text/css" href="sweetalert-master/dist/sweetalert.css">
<script src="sweetalert-master/dist/sweetalert.min.js"></script>
<style>
	body { 
	    background-image: url('images/login_bg.jpg');
	    opacity:1;
	    background-size: cover;
	    background-repeat: no-repeat;
	    background-attachment: fixed;
	    background-position: center; 
	}
 
    a,h1 {
    	color: white;
	} 
	   

	</style>
</head>

<form name="form" action="LOGIN.jsp" method="post">
<input type="hidden" name="method" value="">
<input type="hidden" name="sol_valid" value="<%=sol_valid %>">
<input type="hidden" name="usrid" value="">
<input type="hidden" name="pswd" value="">
<input type="hidden" name="url" value="<%=url %>">
<BR>
<div id="main" align="center">
	<div class="field">
		<h1>MÜST LOGIN PAGE</h1>								
	</div>
</div>
<div align="center">

<font color="#CCFF99" size="3" face="新細明體">
	<%=errmsg %>
</font>

<div class="field">
	<input type="text" name="usridtxt" value="<%=usridtxt%>"  placeholder="請輸入AD帳號">
</div>
<div class="field">
	<input type="password" name="pswdtxt"  placeholder="請輸入AD密碼">
</div>
<div class="field">		
		<input type="submit" value="登入" onclick="login()" >
</div>

<br>
<br>


	
</div>
</form>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/BASE64.js"></script>
<script language="javascript">
	
	function login()
	{
		if(form.usridtxt.value == ""){
			alert("請輸入帳號");
			form.usridtxt.focus();
			return false;
		}else if(form.pswdtxt.value == ""){
			alert("請輸入密碼");
			form.pswdtxt.focus();
			return false;
		}
		form.method.value = "login";
		form.usridtxt.value=base64encode(form.usridtxt.value); 
		form.pswdtxt.value=base64encode(form.pswdtxt.value);
		form.action = "LOGIN.jsp"; 
		form.target = "_self";
		form.submit();
	}

</script>
</body>

</html>