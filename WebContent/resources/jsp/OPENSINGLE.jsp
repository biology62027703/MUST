<%@ page language="java" import="java.util.*,com.sertek.sys.*" pageEncoding="MS950"%>
<html>

<head>
<title>資料開窗</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
</head>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/CSS.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/FormUtil.js"></script>
<script language="javascript">
<!--

var dynUtil = null;
var option = null;

function getOpenerData() {
	//取得控制物件
	dynUtil = window.dialogArguments.dynUtil;
	option = dynUtil.dynOption;
}

function doQuery() {

	dynUtil.setSelectOption({
		dataset: option.dataset,
		url: option.url,
		paraObj: option.paraObj,
		sqlid: option.sqlid,
		sql: option.sql,
		targetObj: $("select[name=select1]"),	
		valuepattern: option.valuepattern,
		textpattern: option.textpattern		
	})
}

$(document).ready(function(){		
	
	getOpenerData();
	doQuery();	
	window.focus();
	
	$("select[name=select1]").click(function(){
		obj = $(this)[0];
		if( option.valuetarget!=null )
			formUtil.bindObjectData(option.valuetarget, obj.options[obj.selectedIndex].value);
		if( option.texttarget!=null )		
			formUtil.bindObjectData(option.texttarget, obj.options[obj.selectedIndex].text);
		window.parent.close();
	})
});
//-->
</script>
</HEAD>

<BODY height="100%">
<form name="form">
<select name="select1" size="14" style='width:97%;font-size:13pt'>
</select>
</form>
</BODY>
</HTML>

