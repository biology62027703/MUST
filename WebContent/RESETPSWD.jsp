<!DOCTYPE> 
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.sys.*" contentType="text/html;charset=MS950"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="./utility/ERRORPAGE.jsp"%>
<%  
	String cnt = "";
	CheckObject check = new CheckObject();
	String errmsg = "";
	String usrid =  "";
	HashMap form = (HashMap)request.getAttribute("form");
	if(form != null){
		errmsg = check.checkNull(form.get("errmsg"), "").toString();
		usrid =  check.checkNull(form.get("usrid"), "").toString();
	}
	System.out.println("usrid:"+usrid);
%>
<html>
<head>
<title>�u�W�_�D�ήѪ��ǰe�@�~���x</title>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="css/CSS.css" type="text/css">
<script type="text/javascript" src="jquery/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="jquery/ui/js/jquery-ui-1.9.2.custom.min.js"></script>
<link rel="stylesheet" href="jquery/ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css">
<script type="text/javascript" src="jquery/js/json2.js"></script>
<script type="text/javascript" src="jquery/js/DialogUtil.js"></script>
<script type="text/javascript" src="jquery/js/FormUtil.js"></script>
<script language="javascript" src="script/WINDOW.js"></script>
<script language="javascript" src="script/BASE64.js"></script>
<script language="javascript">
	function updatepswd() {
		if(form.pswdtxt.value == ""){
			alert("�п�J�K�X");
			form.pswdtxt.focus();
			return false;
		}else if(form.againpswdtxt.value == ""){
			alert("�ЦA����J�K�X");
			form.againpswdtxt.focus();
			return false;
		}else if(form.pswdtxt.value != form.againpswdtxt.value){			
			alert("�⦸��J���Ȥ��ۦP�A�Э��s��J");
			form.againpswdtxt.focus();
			return false;			
		}
			
		form.info.value = "updatepswd";
		form.pswd.value = form.pswdtxt.value;
		form.action = "RESETPSWD.do";
		form.target = "_self";
		form.submit();
	}
	/* var $ = function(v){
		if(v=="B" || v=="pswdstrong"){	
			return document.getElementById(v);
		} else {
			return "" ;
		}
	} */
	
	function isSecurity(v){	
		
		if (v.length < 3) { 
			iss.reset();
			return;
		}
		var lv = -1;	
		if (v.match(/[a-z]/)){
			lv++;
		}
		if (v.match(/[A-Z]/)){
			lv++;	
		}
		if (v.match(/[0-9]/ig)){
			lv++;
		}
		if (v.match(/[^a-zA-Z0-9]/)){
			lv++;	
		}
		if (v.length < 8 && lv > 0){
			lv--;
		}
		iss.reset();
	
		switch (lv) { 		
			case 0:	
			iss.level0();
			break;
		
			case 1:	
			iss.level1();	
			break;
				
		 	case 2:	
		 	iss.level2();	
		 	break;
		
			case 3:	
			iss.level2();	
			break;
		 
		 	default:
		
		 	iss.reset();		
		}	
	}
	
	var iss = {
		color:["CC0000","FFCC33","66CC00","CCCCCC"],	
		text:["�z","��","�j"],
		width:["70","140","210","10"],	
		reset:function(){	
			document.getElementById("B").style.backgroundColor = iss.color[3];	
			document.getElementById("B").style.width = iss.width[3];
			document.getElementById("pswdstrong").innerHTML = "�ܮz";	
		},	
	 	level0:function(){
			document.getElementById("B").style.backgroundColor = iss.color[0];		
			document.getElementById("B").style.width = iss.width[0];		
			document.getElementById("pswdstrong").innerHTML = "�z"; 	
		},
	
	 	level1:function(){	
		 	document.getElementById("B").style.backgroundColor = iss.color[1];		
		 	document.getElementById("B").style.width = iss.width[1];		
		 	document.getElementById("pswdstrong").innerHTML = "��"; 	
	 	},
	
		level2:function(){		
			document.getElementById("B").style.backgroundColor = iss.color[2];		
			document.getElementById("B").style.width = iss.width[2];		
			document.getElementById("pswdstrong").innerHTML = "�j";	
	 	}	
	}
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" >
<form name="form" action="" method="post">
<input type="hidden" name="method" value="">
<input type="hidden" name="usrid" value="<%=usrid%>">
<input type="hidden" name="pswd" value="">
<input type="hidden" name="info" value="">
<div align="center">

<table width="100%" height="60px" bgcolor="#00BDCB">
<tr>
	<td align="center">
	
	<table width="960">
	<tr>
		<td width="40">
			&nbsp;
		</td>
		<td width="80">
			<img src="./image/Judicial_Yuan.png" height="60">
		</td>
		<td align="left">
			<b><font size="5" color="#FFFFFF">�q�k�|</font><font size="5" color="#FFFFFF">�u�W�_�D�ήѪ��ǰe�@�~���x</font></b>
		</td>
		<td align="right">
			&nbsp;
		</td>
		<td width="40">
			&nbsp;
		</td>
	</tr>
	</table>

	</td>
</tr>
</table>

<br>
<br>

<table width="800" style="display:none">
<tr id="showmsg">
    <td height="30" id="ErrMsg" align="center">
	    <font color="#FF0000" size="3" face="�s�ө���">
	    	<%=errmsg %>
	    </font>
    </td>
</tr>
</table>

<br>
<br>

<!-- <table class="border_login" cellpadding="10" style="display:none"> -->
<table class="border_login" cellpadding="10">
<tr>
	<td colspan="2" align="right">
		&nbsp;
	</td>
</tr>
<tr>
	<td width="40%" align="right">
		�s	�K	�X�G
	</td>
	<td align="left">
		<input type="password" name="pswdtxt" tabindex="1" size="32" maxlength="32" value="" class="txt" onkeyup="isSecurity(this.value);">		
	</td>
</tr>

<tr>
	
	<td width="40%" align="right">
		�K�X�j��:
	</td>		
	<td align="left">
		<font class="red" id="pswdstrong"></font>
		<table height="8" border="1" align="left" cellpadding="0" cellspacing="0" bordercolor="#EEEEEE" style="border-collapse:collapse;">
			
		<td bgcolor="#EEEEEE" width="1" align="center" height="20" valign="middle" id="B"></td>
			
		</table>
	</td>
</tr> 

<tr>
	<td align="right">
		�A����J�s�K�X�G
	</td>
	<td align="left">
		<input type="password" name="againpswdtxt" tabindex="2" size="32" maxlength="32" value="" class="txt">
	</td>
</tr>
<tr>
	<td colspan="2" align="center">
		<input type="button" name="btn_login" tabindex="3" title="�T�{" alt="�T�{" value="�T�{" onclick="updatepswd()" class="btn1"> 
	</td>
</tr>
</table>

<br>
<br>

<%
util_date ud = new util_date();
String judMail = "efiling@judicial.gov.tw";
%>
		
<br>

<table width="500" border="0" cellspacing="0" cellpadding="10" align="center">

<tr><td align="center">
<%StaticObj.setIndexCntAdd1();%>
�ѳX�H�ơG<%=StaticObj.getIndexCnt()%>
</td></tr>

</table>

<table width="500" border="0" cellspacing="0" cellpadding="10">

<tr><td>
�p�������ĳ�A���I��i<a href='mailto:<%=judMail %>' target='_new'>���D�ϬM</a>�j�q���ڭ̡A�w��h�[�ϥΡI <br>

�p�����f�G�q�k�|��T�B�@���ª� (02)23618577��400 <br>
�@�@�@�@�@�@�@�@�@�@�@�@�L�ɪY (02)23618577��403 <br>
�@�@�@�@�@�@�@�@�@�@�@�@���@�� (02)23618577��293

</td></tr>

</table>

	
</div>
</form>
</body>

</html>