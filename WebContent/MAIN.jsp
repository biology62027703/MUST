<%@page language="java" contentType="text/html;charset=MS950"%>
<%@page import="com.sertek.util.*" %>
<%@page import="java.net.*" %>
<%@include file="./framework/include/charset.jsp"%>
<%
CheckObject check = new CheckObject();
String mainUrl = check.checkNull(request.getAttribute("mainUrl"), "").toString();
String alertmsg = check.checkNull(request.getAttribute("alertmsg"), "").toString();
String src = "FRAME.jsp";
src += "?mainUrl=" + URLEncoder.encode(mainUrl);
System.out.println("MAIN.jsp   src:"+src);
%>
<html>
<head>
<title>線上起訴及書狀傳送作業平台</title>
<meta http-equiv='Content-Type' content='text/html; charset=Big5' />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<script language="JavaScript" src='./framework/script/ErrorHandle.js'></script>
<script language="JavaScript" src='./framework/script/WindowUtil.js'></script>
<script language="JavaScript" src='./framework/script/HelpUtil.js'></script>
<script language="JavaScript" src='./framework/script/Form.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.10.2.min.js"></script>
<script language="JavaScript">
var alertmsg = "<%=alertmsg%>";
$(document).ready(function(){
	$("html,body").css("height", "100%");
	document.body.style.overflowX = 'hidden';
	document.body.style.overflowY = "hidden";	
	if (alertmsg!="") {
		alert(alertmsg);
	}	
})	
</script>
</head>
<body style='overflow-x: hidden; overflow-y: hidden; margin: 0px;'>
	<iframe id='loaderFrame' name='loaderFrame' src="<%=src %>" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0"></iframe>
	<iframe id='sessionFrame' width='100%' height='0' src='' frameborder='0' marginwidth='0' marginheight='0'></iframe>
</body>
</html>