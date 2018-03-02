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
<title>EMAIL</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/CSS.css" type="text/css">
<%@include file="../utility/JQUERY_OLD.jsp"%>
<script>

	$(document).ready(function(){
		doQuery();
		top.closeProcessBar();
	});
	
	//var total_count="0";
	function doQuery()
	{
		formUtil.submitTo({
			url: "LAL_INSERT_POST.do?action=dopost",
			async: false,
			formObj: $(form),
			onSuccess: function(jsonData){				
				alert(jsonData.data.MSG);
			}			
		});
		
	}
	
	
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" >
<form name="form" action="" method="post">
<table style=width:100%>

</table>

<span id="spandata">
	
</span>
</form>
</body>

</html>