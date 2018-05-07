<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<%@include file="HEAD.jsp"%>
	<%@include file="FANCYBOX_REL.jsp"%>
	<!-- Wrapper -->
	<body>
		<div id="wrapper">
			<!-- Header -->
			<%@include file="TOP.jsp"%>
			<!-- Main -->			
			<div id="main">
				<div class="inner">
					<h1>法務授權需求</h1>
					<p class="view_lal"><a class="fancybox" href="FRAME.jsp?mainUrl=LAL/LAL_INSERT_CONTRACT_CASE_PREPARE.jsp">法務授權門市續約繳款單匯入</a></p>	
					<p><a class="fancybox" href="FRAME.jsp?mainUrl=LICENSE/LICENSE_LIST.jsp">線上授權待確認清單</a></p>
					<p><a class="fancybox" href="FRAME.jsp?mainUrl=LICENSE/LICENSE_APPLYLIST.jsp">線上授權已確認清單</a></p>	
					<p><a class="fancybox" href="FRAME.jsp?mainUrl=LICENSE/LICENSE_CLOSELIST.jsp">線上授權已結案清單</a></p>									
				</div>
			</div>
		</div>
	</body>
	<script>
	var group="<%=group%>";
	$(document).ready(function(){
	 if("5".indexOf(group)==-1) {
	 	$(".view_lal").remove();	 	
	 }
	
	})
	</script>
</html>