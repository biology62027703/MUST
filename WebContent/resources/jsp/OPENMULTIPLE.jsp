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
	splitStr = option.splitStr;
}

function doQuery() {
	
	dynUtil.setSelectOption({
		dataset: option.dataset,
		url: option.url,
		sqlid: option.sqlid,
		sql: option.sql,
		paraObj: option.paraObj,
		targetObj: $("select[name=select1]"),	
		valuepattern: option.valuepattern,
		textpattern: option.textpattern,
		defaultValue: option.valuetarget.val()
	})
	
	$("select[name=select1]")[0].scrollTop = 0;
}

$(document).ready(function(){		
	getOpenerData();
	doQuery();	
	window.focus();
	
	$("input[name=btn1]").click(function(){
		var text = "";
		var value = "";
		if( $("option:selected", $("select[name=select1]")).length >0 ) {
			$("option:selected", $("select[name=select1]")).each(function(){
				if(text!="") text+=splitStr;
				if(value!="") value+=splitStr;			
				text+=$(this)[0].text;	
				value+=$(this)[0].value;
				if( option.valuetarget!=null )
					formUtil.bindObjectData(option.valuetarget, value);
				if( option.texttarget!=null )
					formUtil.bindObjectData(option.texttarget, text);
				window.parent.close();
			})
		}else{
			if( option.valuetarget!=null )
				formUtil.bindObjectData(option.valuetarget, "");
			if( option.texttarget!=null )
				formUtil.bindObjectData(option.texttarget, "");
			window.parent.close();
		}
	});
	
	$("input[name=btn2]").click(function(){
		$("option", $("select[name=select1]")).attr("selected", true);		
	});
	
	$("input[name=btn3]").click(function(){
		$("option", $("select[name=select1]")).attr("selected", false);	
	});
});
//-->
</script>
</HEAD>

<BODY>
<form name="form">
<select name="select1" size="10" multiple style='width:97%;font-size:13pt'>
</select>
<center>
<input type="button" name="btn1" value="確定">&nbsp;
<input type="button" name="btn2" value="全選">&nbsp;
<input type="button" name="btn3" value="全不選">
</center>
<font color="red"><b>
1. 按住CTRL鍵不放同時配合滑鼠左鍵點選即可非連續選擇<br>
2. 按住SHIFT鍵不放同時配合滑鼠左鍵點選即可連續複選
<b></font>
</form>
</BODY>
</HTML>

