<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=MS950"%>
<%
HashMap form = (HashMap)request.getAttribute("form");

%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<link rel="stylesheet" href="../css/CSS.css" type="text/css">
<script type="text/javascript" src="jquery/js/jquery-1.10.2.min.js"></script>
<script language="javascript">
	
	var dialogUtil = parent.dialogUtil;
	
	function iniForm()
	{
	}
	
	function doLogin(){
		var valid_code = document.getElementById("valid_code").value;
		if("" == valid_code){
			alert("�п�J���ҽX");
			document.getElementById("valid_code").focus();
			return false;
		} else {
			dialogUtil.setReturnValue({valid_code:valid_code});
			dialogUtil.closeDialog();
			//window.returnValue = valid_code;
			//window.close();
		}
	}
	
	function doReset(){
		dialogUtil.closeDialog();
	}
	
	function doRefresh(){
		document.getElementById("captcha").src = document.getElementById("captcha").src + "?reload=" + new Date().getTime();
	}
	
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" onload="iniForm()">
<div align="center">

<br>
�K�X��J���~�T���A�п�J�ϧ����ҽX
<table width="500" border="0" cellpadding="4">
<tr>
	<td width="30%" align="right">
		���ҽX
	</td>
	<td align="left">
		<input type="text" id="valid_code" name="valid_code" size="10" maxlength="10" value="" class="txt">
				<img src="../genCaptcha" id="captcha" border="0" onclick="doRefresh();">
		<a href="#" onclick="doRefresh()">���s����</a>
	</td>
</tr>
	<td colspan="2" align="center">

	</td>
<tr>
</tr>
<tr>
	<td colspan="2" align="center">
		<input type="button" name="btn_submit" title="�T�w" alt="�T�w" value="�T�w" onclick="doLogin()" class="btn1">
		&nbsp;&nbsp;
		<input type="button" name="btn_cancel" title="����" alt="����" value="����" onclick="doReset()" class="btn1">
	</td>
</tr>
</table>

</div>
</body>

</html>