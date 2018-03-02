<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=MS950"%>
<%@include file="../utility/PAGEINIT.jsp"%>
<%
HashMap form = (HashMap)request.getAttribute("form");
session.removeAttribute("FROM_FSO4A");
session.removeAttribute("FSO4A_USRID");
session.removeAttribute("FSO4A_USRNM");
session.removeAttribute("FSO4A_WDAY");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="../css/CSS.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.10.2.min.js"></script>
<script language="javascript">
	
function iniForm()
{
	parent.showMainFrame();
	top.closeProcessBar();
	loadmsg();
	

	len = menu.length;
	while(len%4 != 0)
		len++;
	len=len/4;
	for(var i=0;i<len;i++) {
		$("table[id=tablemenu]").append("<tr align='center' valign='center' height='180' id='menu'><td width='25%'></td><td width='25%'></td><td width='25%'></td><td width='25%'></td></tr>");
	}
	
	for(var i=0;i<menu.length;i++) {
		$("table[id=tablemenu] td:eq("+i+")").append("<img src='"+menu[i].FUN_IMG+"' width='200' height='150' onclick='"+menu[i].FUN_CLICK+"(\""+menu[i].FUN_URL+"\")' style='cursor:pointer' title='"+menu[i].FUN_NM+"'>")
	}
}

function doAddNew(action)
{
	form.action = action;
	form.target = "mainFrame";
	form.submit();
}

function doQuery(action)
{
	form.action = action;
	form.target = "queryFrame";
	form.submit();
}

function doAssignment(action)
{
	form.action = action;
	form.target = "queryFrame";
	form.submit();
}

function doOpenBl(){
	window.open("https://www.ezlawyer.com.tw/eb/");
}

function doDownLoadMD5CHECK(){
	form.action = "../utility/Download.jsp?guide=6";
	form.target = "downloadFrame";
	form.submit();
}
	
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" onload="iniForm()">
<form name="form" action="" method="post">
<div align="center">

<%@include file="../utility/TOP.jsp"%>

<br>

<table width="960" border="0" cellspacing="0" cellpadding="10">

<tr>
	<td width="100%" align="center" valign="top">
	<table width="100%" border="0" cellspacing="0" cellpadding="4" id="tablemenu">
	</table>
	</td>
</tr>

</table>

<table width="500" border="0" cellspacing="0" cellpadding="10" align="center">

<tr><td align="center">
參訪人數：<%=StaticObj.getMenuCnt()%>
</td></tr>

</table>
	
</div>
</form>
</body>

</html>