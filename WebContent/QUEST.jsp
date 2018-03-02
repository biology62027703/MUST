<%@page language="java" contentType="text/html;charset=MS950"%>
<%@page import="com.sertek.util.*" %>
<%@page import="java.net.*" %>
<%@include file="./framework/include/charset.jsp"%>
<%
CheckObject check = new CheckObject();
String mainUrl = check.checkNull(request.getAttribute("mainUrl"), "").toString();
String src = "FRAME.jsp";
src += "?mainUrl=" + URLEncoder.encode("sos/SSO9A.do");
%>
<html>
<head>
	<title>線上起訴</title>
	<meta http-equiv='Content-Type' content='text/html; charset=Big5' />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<script language="JavaScript" src='./framework/script/ErrorHandle.js'></script>
	<script language="JavaScript" src='./framework/script/WindowUtil.js'></script>
	<script language="JavaScript" src='./framework/script/HelpUtil.js'></script>
	<script language="JavaScript" src='./framework/script/Form.js'></script>
	<script language="JavaScript">
		
	</script>
</head>
<body style='overflow-x: hidden; overflow-y: hidden; margin: 0px;'>
	<iframe id='loaderFrame' name='loaderFrame' src="<%=src %>" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0"></iframe>
	<iframe id='sessionFrame' width='100%' height='0' src='' frameborder='0' marginwidth='0' marginheight='0'></iframe>
</body>
</html>