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
width:32%;
border:2px green solid;
float:left;
}

#DIV2{
width:38%;
border:2px green solid;
float:left;
}

#DIV3{
width:26%;
border:2px green solid;
float:left;
}
</style>
	<script>
		$(document).ready(function(){												
			doQuery();		
			$(":input[type='button']").click(function(){
				if($(this).attr("id")=="all") {
					$("#DIV1").show();
					$("#DIV2").show();
					$("#DIV3").show();
					$("#DIV1").width("32%");
					$("#DIV2").width("38%");
					$("#DIV3").width("26%");
					
				} 
				if($(this).attr("id")=="checked") {
					$("#DIV1").show();
					$("#DIV2").show();
					$("#DIV1").width("50%");
					$("#DIV2").width("48%");
					$("#DIV3").hide();
					
				}
				if($(this).attr("id")=="unchecked") {
					$("#DIV1").hide();
					$("#DIV2").hide();
					$("#DIV3").show();
				}				
			});
			setInterval(function() {
				$("tr[id='trdata']").remove();
				$("div[id='total']").empty();
				$("td[id='self_title']").empty();
				$("td[id='agent_title']").empty();
				doQuery();
			}, 10000);
		})
		
		function doQuery(){
			//alert(0);
			formUtil.submitTo({
				url: "MEMBERCHECK_SITUATION.do?action=doQuery",
				async: false,
				formObj: $(form),
				onSuccess: function(jsonData){
					var agent_checkin=0;
					var agent_checkin_count=0;
					var self_checkin=0;
					var self_checkin_count=0;
					for (i=0;i<jsonData.data.TOTAL.length;i++) {
						$("div[id='total']").append("總票數:"+jsonData.data.TOTAL[i].TOTAL_TICKET+"<BR>已報到票數:"+jsonData.data.TOTAL[i].CHECKIN_TICKET+"<br><br>");
					}
					for(i=0;i<jsonData.data.DATA.length;i++) {
						//未報到
						if(jsonData.data.DATA[i].SITUATION==""){
							$("#uncheckin").append(
								"<tr id='trdata'>"+
									"<td>"+jsonData.data.DATA[i].MEMBER_NO+"</td>"+
									"<td>"+jsonData.data.DATA[i].CHINESE_NAME+"</td>"+
									"<td>"+jsonData.data.DATA[i].TICKET+"</td>"+
								"</tr>"
							);
						} else {
							//alert(jsonData.data.DATA[i].AGENT);
							if( jsonData.data.DATA[i].AGENT!="") {
								 agent_checkin=agent_checkin+parseInt(jsonData.data.DATA[i].TICKET);
								 agent_checkin_count++;
								$("#agent_checkin").append(
									"<tr id='trdata'>"+
										"<td>"+agent_checkin_count+"</td>"+
										"<td>"+jsonData.data.DATA[i].MEMBER_NO+"</td>"+
										"<td>"+jsonData.data.DATA[i].CHINESE_NAME+"</td>"+
										"<td>"+jsonData.data.DATA[i].TICKET+"</td>"+
										"<td>"+jsonData.data.DATA[i].AGENT_MEMBERNO+jsonData.data.DATA[i].AGENT+"</td>"+
									"</tr>"
								);
							} else {
								self_checkin=self_checkin + parseInt(jsonData.data.DATA[i].TICKET);
								self_checkin_count++;
								$("#self_checkin").append(										
										"<tr id='trdata'>"+
											"<td>"+self_checkin_count+"</td>"+
											"<td>"+jsonData.data.DATA[i].MEMBER_NO+"</td>"+
											"<td>"+jsonData.data.DATA[i].CHINESE_NAME+"</td>"+
											"<td>"+jsonData.data.DATA[i].TICKET+"</td>"+
											"<td>"+jsonData.data.DATA[i].CHECK_AGENT+"</td>"+
										"</tr>"
									);
							}
						}						
					}
					$("td[id='self_title']").append("※報到人數:"+self_checkin_count+"    報到票數:"+self_checkin);
					$("td[id='agent_title']").append("※報到人數:"+agent_checkin_count+"    報到票數:"+agent_checkin);
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
		<div id="total" align="center" style="font-size:48px"></div>
		<div>
		<input type="button" id="all" value="全部顯示">
		<input type="button" id="checked" value="已報到">
		<input type="button" id="unchecked" value="未報到">
		</div>
		<br>
		<div align="center">
			<div id="DIV1">
				<table id="self_checkin" border="1" width="100%">
					<tr>
						<td id="self_title" colspan="5">親自報到</td>
					</tr>
					<tr>
						<td>流水號</td>
						<td>會員編號</td>
						<td>會員姓名</td>
						<td>會員票數</td>
						<td>是否被委託</td>
					</tr>
				</table>
			</div>
			<div id="DIV2">
				<table id="agent_checkin" border="1" width="100%">
					<tr>
						<td id="agent_title" colspan="5">委託報到</td>
					</tr>
					<tr>
						<td>流水號</td>
						<td>會員編號</td>
						<td>會員姓名</td>
						<td>會員票數</td>
						<td>受委託人</td>
					</tr>
				</table>
			</div>
			<div id="DIV3">
				<table id="uncheckin" border="1" width="100%">
					<tr>
						<td colspan="3">未報到</td>
					</tr>
					<tr>
						<td>會員編號</td>
						<td>會員姓名</td>
						<td>會員票數</td>
					</tr>
				</table>
			</div>
		</div>
		</form>		
	</body>
</html>