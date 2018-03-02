<%@page language="java" import="com.sertek.form.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../utility/PAGEINIT.jsp"%>
<%

%>
<html>
<head>
<title>線上起訴</title>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
</head>
<link href="css/CSS.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="script/BASE64.js"></script>
<script language="javascript" type="text/javascript">
<!--
	
	function iniForm()
	{
		top.closeProcessBar();
		alert('<c:out value="${form.errmsg }" />');
	}
	
//-->
</script>

<body text="#000000" leftmargin="0" topmargin="0" onload="iniForm()">
<form name="form" action="" method="post">
<center>

<table width="100%" height="60px" bgcolor="#00BDCB">
<tr>
	<td align="center">
	
	<table width="1024px">
	<tr>
		<td width="80">
			<img src="../image/Judicial_Yuan.png" height="60">
		</td>
		<td>
			<b><font size="5" color="#FFFFFF">司法院委外</font><br><font size="5" color="#FFFFFF">線上起訴系統</font></b>
		</td>
		<td align="right">
			&nbsp;
		</td>
	</tr>
	</table>

	</td>
</tr>
</table>

<br>
<br>

<table width="800">
<tr id="showmsg">
    <td height="30" id="ErrMsg" align="center">
	    <font color="#FF0000" size="3" face="新細明體">
	    	<c:out value="${form.errmsg }" />
	    </font>
    </td>
</tr>
</table>

</center>
</form>
</body>

</html>