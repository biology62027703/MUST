<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
String serverIP = InetAddress.getLocalHost().getHostAddress(); 
String ip = "";
if (request.getHeader("HTTP_X_FORWARDED_FOR") == null) {
    ip = request.getRemoteAddr();
} else {
    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
}
String in_out="out";
if(ip.indexOf("192.168.")>-1){
	in_out="in";
}
%>
<html>
	<%@include file="HEAD.jsp"%>
	<script>
		$(document).ready(function(){
			var check="<%=in_out%>";
			if(check=="out"){
				$(".inter_view").remove();
			}
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
					<!-- <p><a class="fancybox" href="CALENDAR_INDEX.jsp">DIVA SESSION KILL</a></p> -->
					<p><a href="https://www.must.org.tw/">MÜST官網</a></p>	
					<p class="inter_view"><a href="https://www.must.org.tw/admin/INDEX.aspx">MÜST官網後台</a></p>
					<p class="inter_view"><a href="http://list.must.org.tw/list_login.asp">清單系統</a></p>
					<p class="inter_view"><a href="http://win2k.must.org.tw/mis1/lal/lal_login.asp">法務暨授權業務管理資訊系統</a></p>
					<p><a href="https://msa.must.org.tw">WEB MAIL</a></p>									
				</div>
			</div>
		</div>			
	</body>
</html>