<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=MS950"%>
<%@include file="../utility/PAGEINIT.jsp"%>
<%
HashMap form = (HashMap)request.getAttribute("form");
String usrid = "";
{
	com.sertek.sys.sys_User nowUser = (com.sertek.sys.sys_User)request.getSession().getAttribute("User");
	if(nowUser != null){
		usrid = nowUser.readString("S_USRID");
	}
}
boolean Guest = usrid.equals("__GUEST__");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="../css/CSS.css" type="text/css">
<style type="text/css">
body {
    margin:0;
    padding:0;
    background: url(../image/background-3.jpg) center fixed no-repeat;
    -moz-background-size: cover;
    background-size: 100% 96%;
    background-position: 0 50px;
    background-padding: 60px;
    //background-height: 50%;
}
</style>

<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.10.2.min.js"></script>
<script language="javascript">
	
	function iniForm()
	{
		parent.showMainFrame();
		top.closeProcessBar();
		loadmsg();
	}
	
	function doSysKdI(){
		doSyskd("A", "I");
	}
	
	function doSysKdT(){
		doSyskd("A", "T");
	}
	
	function doSysKdV() {
		doSyskd("V", "V");
	}
	
	function doSyskd(sys, syskd)
	{
		top.showProcessBar();

		form.sys.value = sys;
		form.syskd.value = syskd;
		form.action = "../sof/FSOMain.do?action=viewForm&init=Y";
		form.target = "mainFrame";
		form.submit();
	}
	
	function doFSO4A(){
		top.showProcessBar();
		
		form.action = "../sof/FSO4A.do?action=viewForm";
		form.target = "mainFrame";
		form.submit();
	}
	
	function doLogout()
	{
		form.action = "<%=request.getContextPath()%>/Logout.do";
		form.target = "mainFrame";
		//alert(0);
		form.submit();
	}
	
	function drawS0B() {
		var s0b = <%=new com.google.gson.Gson().toJson(com.sertek.sys.StaticObj.getS0B())%>;
		
		$table = $("table:contains(最新消息)");
				
		for(var i=0;i<s0b.length;i++) {
			$tr = $("<tr class='border_content'></tr>")
				.append("<td width='80' align='left' valign='top'>"+s0b[i].POSTDT+"</td>")
				.append("<td align='left' valign='top'>"+s0b[i].MSG+"</td>")
				.appendTo($table);
		}
		
		$("a[file]").css("color", "red").css("cursor","pointer")
			.click(function(){			
				if( $("iframe[name=downiframe]").length==0 ) {
					$("<iframe style='display:none' name='downiframe'></iframe").appendTo($("body"));
				}
				if( $("form[name=downform]").length==0 ) {
					$("<form style='display:none' name='downform' target='downiframe' action='/BOS/DownLoad.do'><input type='text' name='action' value='S0BATT'><input type='text' name='filenm'></form>").appendTo($("body"));
				}
				var file = $(this).attr("file");
				$form = $("form[name=downform]");
				$(":text[name=filenm]", $form).val(file);
				$form[0].submit();
			});
	}
	
</script>
</head>

<body text="#000000" background="../image/background-3.jpg" leftmargin="0" topmargin="0" onload="iniForm()">
<form name="form" action="" method="post">
<input type="hidden" name="sys" value="">
<input type="hidden" name="syskd" value="">
<div align="center">

<%@include file="../utility/TOP.jsp"%>

<br>

<table width="960" border="0" cellspacing="0" cellpadding="10">



<tr>
	<td width="100%" align="center" valign="top">
	<table width="100%" border="0" cellspacing="0" cellpadding="4">
		<tr align="center" valign="center" height="180" id="menu">
			<td width="25%"></td>
			<td width="25%"></td>
			<td width="25%"></td>
			<td width="25%"></td>
		</tr>
	</table>
	</td>
</tr>
</table>

<table width="500" border="0" cellspacing="0" cellpadding="10" align="center">

</table>
</div>

</form>
</body>

</html>