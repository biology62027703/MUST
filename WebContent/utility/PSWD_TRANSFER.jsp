<%@page language="java" contentType="text/html;charset=MS950"%>
<%@page import="com.sertek.util.CryptoUtil"%>
<%
String cleartext = "";
String ciphertext = "";
String mode = "";
if(request.getParameter("cleartext") != null){
	cleartext = request.getParameter("cleartext");
}
if(request.getParameter("ciphertext") != null){
	ciphertext = request.getParameter("ciphertext");
}
if(request.getParameter("mode") != null){
	mode = request.getParameter("mode");
}
if("encrypt".equals(mode)){
	ciphertext = CryptoUtil.encrypt(cleartext);
}else if("decrypt".equals(mode)){
	cleartext = CryptoUtil.decrypt(ciphertext);
}
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=MS950">
<style type="text/css">
<!--
@import "../css/CHD.css";
-->
</style>
<script language="javascript" src="../script/AUTOCOLOR.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

function transfer(mode){
	form.mode.value = mode;
	form.submit();
}

//-->
</SCRIPT>
</head>

<body onload="autochange()">
<form name="form" method="post" action="PSWD_TRANSFER.jsp">
<input type="hidden" name="mode" value="">
<table width="80%" align="center" border="0">
<tr align="center">
	<td>明碼</td>
	<td>&nbsp;</td>
	<td>密碼</td>
</tr>
<tr align="center">
	<td rowspan="2"><input type="text" name="cleartext" value="<%=cleartext%>" size="30" maxlength="40"></td>
	<td><input type="button" name="enc" value="加密 >>" class="button" onclick="transfer('encrypt');"></td>
	<td rowspan="2"><input type="text" name="ciphertext" value="<%=ciphertext%>" size="30" maxlength="40"></td>
</tr>
<tr align="center">
	<td><input type="button" name="dec" value="解密 <<" class="button" onclick="transfer('decrypt');"></td>
</tr>
</table>
</form>
</body>
</html>
