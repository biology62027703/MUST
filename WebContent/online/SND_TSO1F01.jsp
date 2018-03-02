<%@page language="java" import="java.net.*,java.util.*,com.sertek.sys.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=MS950"%>
<%
%>
<html>

<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<style type="text/css">
<!--
@import "../css/CSS.css";
-->
</style>
</head>
<script>
	
	function doCheck()
	{
		form.action = "../sot/TSO1F.do";
		form.target = "_self";
		form.submit();
	}
	
	function iniForm()
	{
	}
	
	function doReset()
	{
		form.reset();
		iniForm();
	}
	
</script>

<body onload="iniForm()">
<form name="form" method="post" action="" target="" enctype="multipart/form-data">

<div align="center">

<br>

<table width="800" align="center" border="4" bordercolor="#418282">
<tr>
    <td class="title" colspan="2">
	    <table width="100%" border="0">
	    <tr>
		    <td width="58%" class="title" id="funcName">審判當事人資料OK檔上傳</td>
			<td width="42%" align="right" class="title">
			    <input type="button" name="btn_submit" value="確定" onclick="doCheck()" class="button">
			    <input type="button" name="btn_reset" value="取消" onclick="doReset()" class="button">
			</td>
		</tr>
	    </table>
	</td>
</tr>

<tr>
	<td width="20%" class="head-l">附件</td>
	<td class="head-r">
		<input type="file" name="file" value="" size="50" >
	</td>
</tr>

</table>

</div>
</form>
</body>

</html>