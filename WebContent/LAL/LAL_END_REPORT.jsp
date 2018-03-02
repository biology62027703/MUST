<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../utility/PAGEINIT.jsp"%>
<%

%>
<!DOCTYPE html>
<html>
<head>
<title>LAL/報表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/CSS.css" type="text/css">
<%@include file="../utility/JQUERY_OLD.jsp"%>
<script>

	$(document).ready(function(){
		//form.INONE_SOURCE.value="END_REPORT3";
		//doQuery();
		top.closeProcessBar();
		$("button").on("click",function(){
			
			doQuery($(this).attr("id"));
			//alert(1);
			return false;
		}); 
	});
	
	//var total_count="0";
	function doQuery(method)
	{
		formUtil.submitTo({
			url: "LAL_END_REPORT.do?action="+method+"",
			async: true,
			method: "post",
			formObj: $(form),
			onSuccess: function(jsonData){
				alert(jsonData.data.MSG);
			}			
		});
		//alert(0);
	}
	
	
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" >
<form name="form" method="post">
<table style=width:100%>

</table>
<div>
	送件來源:<input type="text" id="INONE_SOURCE" name="INONE_SOURCE" size="20">
	<button id="END_REPORT3" name="END_REPORT3" >產出貼證報表</button>
	<button id="END_REPORT2" name="END_REPORT2" disabled>產出結算報表</button>
	<button id="END_REPORT1" name="END_REPORT1" disabled>產出結案報表</button>
</div>
<span id="spandata">
	
</span>
</form>
</body>

</html>