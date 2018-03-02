<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*, java.net.*, com.acer.log.*, com.acer.form.*"%>
<% 
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");

	HashMap	requestMap	=	FormUtil.getRequest(request);
	String	ip			=	"";
	
	if (request.getHeader("HTTP_X_FORWARDED_FOR") == null)
	{
		ip	=	request.getRemoteAddr();
	}
	else
	{
		ip	=	request.getHeader("HTTP_X_FORWARDED_FOR");
	}

	ClientLog.log(URLDecoder.decode(requestMap.get("MESSAGE").toString()), ip);
%>