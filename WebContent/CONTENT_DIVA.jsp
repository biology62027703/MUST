<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<%@include file="HEAD.jsp"%>
	<%@include file="FANCYBOX_REL.jsp"%>
	
	<body>
		<!-- Wrapper -->
		<div id="wrapper">
			<!-- Header -->
			<%@include file="TOP.jsp"%>
			<script>
				$(document).ready(function(){
					$("#text").click(function(){
						//alert(0);
						window.print();
					});
					var group="<%=group%>";
					$(document).ready(function(){
					 if("1234".indexOf(group)>-1) {
					 	$("#view").remove();
					 }
					
					})
				})
			</script>
			<!-- Main -->				
			<div id="main">
				<div class="inner">
					<h1>DIVA相關</h1>
					<p><a class="fancybox" href="DIVA_SESSIONKILL.jsp">DIVA SESSION KILL</a></p>
					<p><a class="fancybox" id="view" href="FRAME.jsp?mainUrl=DIVA/CROSS_SOP.htm">會員使用報酬交插比對結果 操作手冊</a></p>	
					<p><a class="fancybox" id="view" href="FRAME.jsp?mainUrl=DIVA/DIVAISWCCODE.html">DIVA系統撈出PERFORM LANGUAGE語言別的ISWC CODE</a></p>	
					<p><a class="fancybox" id="view" href="upload/E4F2_upload_diva_NEW.pdf">E4F2_UPLOAD_DIVA(文件說明)</a></p>	
					<!-- <p><a class="fancybox" href="C:\Users\James.huang\Desktop\DIVA\DIVA_RUN_UNMATCH.txt">DIVA跑無法辨識</a></p>	 -->								
				</div>
			</div>
		</div>			
	</body>
</html>