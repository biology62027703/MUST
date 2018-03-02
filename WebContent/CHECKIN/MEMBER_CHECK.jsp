<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
CheckObject check = new CheckObject();
String member_no = (String)check.checkNull(request.getParameter("member_no"), "");
String chinese_name = (String)check.checkNull(request.getParameter("chinese_name"), "");
String situation = (String)check.checkNull(request.getParameter("situation"), "");
String ticket = (String)check.checkNull(request.getParameter("ticket"), "");
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
	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-109515871-1"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	
	  gtag('config', 'UA-109515871-1');
	  gtag('event', 'event_name', {
		  // Event parameters
		  'parameter_1': 'value_1',
		  'parameter_2': 'value_2',
		  // ...
		});
	</script>
	<script>
		var member_no = "<%=member_no%>";
		var ticket ="<%=ticket%>";
		var chinese_name = "<%=chinese_name%>";
		$(document).ready(function(){
			//ENTER鍵disabled
			$('#form').on('keyup keypress', function(e) {
			  var keyCode = e.keyCode || e.which;
			  if (keyCode === 13) { 
			    e.preventDefault();
			    return false;
			  }
			});
			$(":input[type='text']").keypress(function(e){
				var code =(e.keycode?e.keycode:e.which)
				if(code==13){
				if($("#agent").val().trim()=="") {
					swal("", "請輸入委託人!", "info");
				} else {									
					doQuery();
				}
				}
			})
			$("#print").hide();
			if("<%=situation%>"=="1") {
				$("#search").hide();
				formUtil.submitTo({
					url: "MEMBER_CHECK.do?action=doCheckin",
					async: true,
					formObj: $(form),
					onSuccess: function(jsonData){
						if(chinese_name!="" && (ticket=="" || ticket=="NULL" || ticket=="0")) {
							swal("", "準會員"+chinese_name+"報到成功", "success");
							//alert("準會員"+chinese_name+"報到成功");
							//window.open('','_self','');
							//window.close();
						} else {
							swal("", "報到成功", "success");
							$("#print").show();					
						}
					}
				});				
			}
			$(":input[value='確認']").click(function(){
				if($("#agent").val().trim()=="") {
					swal("", "請輸入委託人!", "info");
				} else {									
					doQuery();
				}
			})			
			
		})
		
		function doQuery(){
			//alert(0);
			formUtil.submitTo({
				url: "MEMBER_CHECK.do?action=doQuery",
				async: true,
				formObj: $(form),
				onSuccess: function(jsonData){
					if(jsonData.data.DATA.length==0){
						swal("", "查無此會員，請重新輸入!", "info");						
					} else if(!jsonData.data.MSG==""){
						swal("", jsonData.data.MSG, "info");
					} else {					
							swal({
								  title: "Are you sure?",
								  text: "確認受委託人為"+jsonData.data.DATA[0].MEMBER_NO+"  "+jsonData.data.DATA[0].CHINESE_NAME+"",
								  type: "warning",
								  showCancelButton: true,
								  confirmButtonColor: "#DD6B55",
								  confirmButtonText: "確認",
								  cancelButtonText: "取消",
								  closeOnConfirm: false,
								  closeOnCancel: false
								},
							function(isConfirm){
								if (isConfirm) {
									$("#agent").val(jsonData.data.DATA[0].CHINESE_NAME);
									$("#agent_memberno").val(jsonData.data.DATA[0].MEMBER_NO);
									formUtil.submitTo({
										url: "MEMBER_CHECK.do?action=doCheckin",
										async: true,
										formObj: $(form),
										onSuccess: function(jsonData){						
											swal("", "委託報到成功", "success");
											$("#print").show();			
											$(":input[value='確認']").hide();		
										}
									});	
								} else {
									swal("Cancelled", "使用者取消動作", "info");
								}
							});	
						//}
					}
				}			
			});
		}
		
		
	</script>
	<head>
		<title>MÜST會員大會</title>
	</head>
	
	<body>
		<!-- Wrapper -->
		<form name="form" id="form" action="ticket.jsp" method="post">
		<div id="wrapper">
			<!-- Header -->
			<!-- Main -->	
			<br>
			<br>	
			<div id="main" align="center" style="font-size:24px;">
				<div class="inner">
					<h2><%=member_no+chinese_name%>報到確認</h2>
					<div id="search">
						<p>受委託人身分證字號或統一編號或受委託人完整名稱或受委託人會員編號</p>
						<div class="field half first"><input type="text" id="agent" name="agent" ></div>
						<input type="button" class="button" value='確認'>
						<input type="hidden" id="agent_memberno" name="agent_memberno">
					</div>
					<div id="content">
					</div>
					<div id="print">
						<input type="submit" class="button" value='列印票數'>
					</div>
				</div>
			</div>
		</div>	
		<input type="hidden" name="member_no" id="member_no" value="<%=member_no%>">
		<input type="hidden" name="chinese_name" id="chinese_name"  value="<%=chinese_name%>">
		<input type="hidden" name="ticket" id="ticket"  value="<%=ticket%>">
		<input type="hidden" name="situation" id="ticket"  value="<%=situation%>">
		</form>		
	</body>
</html>