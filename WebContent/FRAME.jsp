<%@page language="java" contentType="text/html;charset=MS950"%>
<%@page import="com.sertek.util.*" %>
<%
CheckObject check = new CheckObject();
String mainUrl = check.checkNull(request.getParameter("mainUrl"), "").toString();
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">

</head>

<frameset id="topframe" rows="100%,0,0,0,0,0" frameborder="no" border="0" framespacing="0">
<frame name="mainFrame" src="<%=mainUrl %>">
<frame name="queryFrame" src="">
<frame name="viewFrame" src="">
<frame name="editFrame" src="">
<frame name="downloadFrame">
<frame name="hideFrame">
</html>