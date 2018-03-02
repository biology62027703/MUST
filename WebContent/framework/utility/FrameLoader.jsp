<%@page language="java" contentType="text/html;charset=UTF-8" import="com.sertek.util.*,com.sertek.form.*" pageEncoding="UTF-8"%>
<%@include file="/framework/include/charset.jsp"%>

<%
	CheckObject Utility = new CheckObject();
	
	String src = (String)Utility.checkNull(request.getParameter("src"),"");
	String title = (String)Utility.checkNull(request.getParameter("title"),"");
%>
<html>
<head>
	<title><%=title %></title>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
	<script language="JavaScript" src='../script/ErrorHandle.js'></script>
	<script language="JavaScript" src='../script/WindowUtil.js'></script>
	<script language="JavaScript" src='../script/HelpUtil.js'></script>
	<script language="JavaScript" src='../script/Form.js'></script>
	<script language="JavaScript">
	
		function onloadHandler()
		{
			top.showProcessBar();

			if (self.dialogArguments && !isNaN(self.dialogArguments.length))
			{
				Form.setInput('LOADER','loaderParam',self.dialogArguments[1]);
			}
			Form.doSubmit('LOADER', WindowUtil.getBasePath() + "<%=src%>", 'POST', 'loaderFrame');
		}
		
		function setTitle()
		{
			if (!self.dialogArguments)
			{
				//top.document.title = document.frames["loaderFrame"].document.title;
			}
		}
		
		
	</script>
</head>
<body scroll='no' style='margin: 0px;' onload="onloadHandler()">
	<form name="LOADER">
		<input type='hidden' name='loaderParam'>
	</form>
	<iframe id='loaderFrame' name='loaderFrame' width='100%' height='100%' frameborder='0' marginwidth='0' marginheight='0' onload="setTitle();"></iframe>
	<iframe id='sessionFrame' width='100%' height='0' src='' frameborder='0' marginwidth='0' marginheight='0'></iframe>
</body>
</html>