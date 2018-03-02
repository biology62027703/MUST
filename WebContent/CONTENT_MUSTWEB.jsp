<!--  --><!DOCTYPE HTML>
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
				<!-- Main -->				
					<div id="main">
						<div class="inner">
							<h1>官網相關</h1>
							<p><a class="fancybox" href="FRAME.jsp?mainUrl=GET_MEMBER_EMAIL.jsp">會員EMAIL及明細</a></p>	
							<p><a class="fancybox" href="FRAME.jsp?mainUrl=DIVA/SOCIETY_NUMATCH_MAIL_PREPARE.jsp">無法辨識資料放在FTP並發函給海外聯會認領</a></p>	
							<p><a class="fancybox" href="FRAME.jsp?mainUrl=MUST_WEB/NOTICE_MAIL_SAMPLE.jsp">請款通知信+解款(分發)通知信SAMPLE</a></p>		
							<!-- <p><a class="fancybox" href="FRAME.jsp?mainUrl=MUST_WEB/NOTICE_MAIL_SAMPLE.jsp">解款(分發)通知信前置作業</a></p> -->					
						</div>
					</div>				

			</div>
	</body>
</html>