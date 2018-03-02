<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
%>
<html>
<%@include file="../utility/JQUERY_OLD.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>
<style type="text/css">


#DIV1{

border:2px green solid;
width:100%;
position: absolute;
top: 25%;
left: 0%;
}

</style>
	<script>
		$(document).ready(function(){												
			doQuery();		
			setInterval(function() {
				$("tr[id='val']").remove();
				doQuery();
			}, 10000);
		})
		
		function doQuery(){
			//alert(0);
			formUtil.submitTo({
				url: "MEMBERCHECK_SITUATION.do?action=doQuery2",
				async: false,
				formObj: $(form),
				onSuccess: function(jsonData){
					for(i=0;i<jsonData.data.length;i++) {
						checkrate=0;
						ticketrate=0;
						if(jsonData.data[i].TOTAL_CHECKIN!="0") {
							checkrate=(jsonData.data[i].CHECKIN_RATE).substring(0,5);
							ticketrate=jsonData.data[i].TICKET_RATE.substring(0,6); 
						}
						$("#count").append(
							"<tr id='val'>"+
								"<td>"+jsonData.data[i].TOTAL_MEMBER+"</td>"+
								"<td>"+jsonData.data[i].SELF_CHECKIN+"</td>"+
								"<td>"+jsonData.data[i].AGENT_CHECKIN+"</td>"+
								"<td>"+jsonData.data[i].TOTAL_CHECKIN+"</td>"+
								"<td>"+checkrate+"%</td>"+
							"</tr>"
						);
						var enter="否";
						if(parseFloat(jsonData.data[i].TICKET_RATE)>=50.0 || parseFloat(checkrate)>=50.0) {
							enter="可";
						}
						$("#ticket").append(										
							"<tr id='val'>"+
								"<td>"+jsonData.data[i].TOTAL_TICKET+"</td>"+								
								"<td>"+jsonData.data[i].SELF_TICKET+"</td>"+
								"<td>"+jsonData.data[i].AGENT_TICKET+"</td>"+
								"<td>"+jsonData.data[i].CHECKIN_TICKET+"</td>"+
								"<td>"+ticketrate+"%</td>"+								
							"</tr>"
						);
						$("#enter").append(										
							"<tr id='val'>"+
								"<td colspan='5' align='center'> <font color='red'>"+enter+"</font></td>"+							
							"</tr>"
						);
					}

				}			
			});
		}
		
		
	</script>
	<head>
		<title>MÜST會員大會-會員報到狀況</title>
	</head>
	
	<body>
		<!-- Wrapper -->
		<form name="form" action="_self" method="post">
			<div id="DIV1" class="tableCenter"  style="font-size:50px">
				<table id="count" border="1" width="100%">					
					<tr>
						<td>會員總計</td>
						<td>親領人數小計</td>
						<td>委託人數小計</td>
						<td>出席人數總計</td>
						<td>人數出席率</td>
					</tr>
				</table>
				<table id="ticket" border="1" width="100%">					
					<tr>
						<td>票數總計</td>
						<td>親領票數小計</td>
						<td>委託票數小計</td>
						<td>出席票數總計</td>
						<td>票數出席率</td>						
					</tr>
				</table>
				<table id="enter" border="1" width="100%" >					
					<tr>
						<td align="center">可否開會</td>								
					</tr>
				</table>
			</div>
		</form>		
	</body>
</html>