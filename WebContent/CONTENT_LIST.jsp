<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<%@include file="HEAD.jsp"%>
	<%@include file="FANCYBOX_REL.jsp"%>
	<script>
		$(document).ready(function(){
			//alert(0);
			$("#text").click(function(){
				//alert(0);
				window.print();
			})
		})
	</script>
	<body>
		<!-- Wrapper -->
		<div id="wrapper">
			<!-- Header -->
			<%@include file="TOP.jsp"%>
			<!-- Main -->				
			<div id="main">
				<div class="inner">
					<h1>清單相關</h1>
					<p><a class="fancybox" href="FRAME.jsp?mainUrl=LIST/LIST_RULE.jsp">清單系統規則</a></p>	
				</div>
			</div>
		</div>			
	</body>
</html>