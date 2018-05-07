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
					<h1>其他相關需求</h1>
					<p><a class="fancybox" href="http://192.168.1.51/song/song_frame.asp">之前51匯入資料相關http://192.168.1.51/song/song_frame.asp</a></p>	
					<p><a class="fancybox" href="/post/POST_RECORD.jsp">函文記錄</a></p>										
				</div>
			</div>
		</div>
	</body>
</html>