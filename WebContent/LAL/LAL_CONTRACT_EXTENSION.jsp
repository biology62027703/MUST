<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
CheckObject Check = new CheckObject();
String user_no=(String)Check.checkNull(request.getParameter("user_no"), "") ;
String case_no=(String)Check.checkNull(request.getParameter("case_no"), "") ;
String cont_name=(String)Check.checkNull(request.getParameter("cont_name"), "") ;
%>
<html>
	<%@include file="../HEAD.jsp"%>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>
	<script src="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.min.js"></script>
	<%@include file="../utility/tablesorder.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.css">
	<style>
		table{
			background-color:white;
		}		
	</style>
	<script>
		$(document).ready(function(){	
			if("<%=case_no%>"=="") {
				alert("缺少個案編號，系統將返回前畫面!");
				history.go(-1);
			} else {
				formUtil.submitTo({
					url: "LAL_CONTRACT_EXTENSIONController.do?action=doInsert",
					async: true,
					formObj: $(form),
					onSuccess: function(jsonData){
						alert(jsonData.data.MSG);
						window.location.href="http://win2k.must.org.tw/mis1/lal/contract/contract.asp?cont_NO="+jsonData.data.CONT_NO+"&USER_NO=<%=user_no%>&CASE_NO=<%=case_no%>";
					}
				});
			}
		})

	</script>
	<body>
		<form name="form" id="form" action="" method="post" >		
			<input type="hidden" name="user_no" value="<%=user_no%>">
			<input type="hidden" name="case_no" value="<%=case_no%>">
			<input type="hidden" name="cont_name" value="<%=cont_name%>">		
		</form>			
	</body>
</html>