<%@page language="java" contentType="text/html;charset=MS950"%>
<%@include file="/framework/include/charset.jsp"%>
<%@include file="/framework/include/nspageinit.jsp"%>
<html>
<head>
	<title><%=Utility.checkNull(this_requestMap.get("title"))%></title>
	<meta http-equiv='Content-Type' content='text/html; charset=Big5' />
	<script language="JavaScript" src='/jud_p/framework/include/baseinit.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/Assist.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/StringBufferObj.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/HashtableObj.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/VectorObj.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/DateUtil.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/StrUtil.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/EncodeUtil.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/ArrayUtil.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/CheckUtil.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/FormEvent.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/Form.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/Message.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/MessageContent.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/JavaInvoke.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/Base64.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/Md5.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/Sha1.js'></script>
	<script language="JavaScript" src='/jud_p/framework/script/InputHelp.js'></script>
	<script language="VBScript" src='/jud_p/framework/script/VBFunction.vbs'></script>
	<% if (System.getProperty("file.separator").equals("\\")) { %>
	<script language="JavaScript" src='/jud_p/framework/script/URLUnicodeEncoding.js'></script>
	<% } else { %>
	<script language="JavaScript" src='/jud_p/framework/script/URLAsciiEncoding.js'></script>
	<% } %>

	<script language="JavaScript">		
		function onloadHandler()
		{
			top.showProcessBar();
			if (self.dialogArguments && !isNaN(self.dialogArguments.length))
			{
				Form.setInput('LOADER','loaderParam',self.dialogArguments[1]);
			}
			Form.doSubmit('LOADER', "<%=Utility.isEmpty(this_requestMap.get("src"))?"":(this_requestMap.get("src").toString().startsWith("/")?"":"../../")+this_requestMap.get("src")%>", 'POST', 'loaderFrame');

			if (self.dialogArguments)
			{
				//document.getElementById("sessionFrame").src = "../../KEEPSESSION.jsp";
			}
		}
		
		function setTitle()
		{
			if (!self.dialogArguments)
			{
				top.document.title = document.frames["loaderFrame"].document.title;
			}
		}
	</script>
</head>
<body scroll='no' style='margin: 0px;'>
	<form name="LOADER">
		<input type='hidden' name='loaderParam'>
	</form>
	<iframe id='loaderFrame' name='loaderFrame' width='100%' height='100%' src="" frameborder='0' marginwidth='0' marginheight='0' onload="setTitle();"></iframe>
	<iframe id='sessionFrame' width='100%' height='0' src='' frameborder='0' marginwidth='0' marginheight='0'></iframe>
</body>
</html>
<%@include file="/framework/include/nspageend.jsp"%>