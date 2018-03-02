<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%

%>
<html>
<%@include file="../HEAD.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>
<script src="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.css">
	<script>
		
		$(document).ready(function(){
			//alert(0);
			formUtil.submitTo({
				url: "MEMBER_CHECK.do?action=DO_NOTE",
				async: false,
				//formObj: $(form),
				onSuccess: function(jsonData){
					$("#content").append("檔案產生完畢");
				}
			});	
			
		})
		
		
	</script>
	<head>
		<title>MÜST會員大會</title>
	</head>
	
	<body>
		<div id="content">
			
		</div>
				
	</body>
</html>