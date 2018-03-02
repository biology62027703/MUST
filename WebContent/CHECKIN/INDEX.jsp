<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
CheckObject check = new CheckObject();
String member_no = (String)check.checkNull(request.getParameter("member_no"), "");
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
	<script>
		var member_no = "<%=member_no%>";
		$(document).ready(function(){
			//alert(member_no);
			if(!member_no=="") {				
				doQuery();
			} else {
				$("div[id='search']").show();
			}
			
			$(":input[value='確認']").click(function(){
				if($("#chinese_name_search").val().trim()=="" && $("#member_no_search").val().trim()=="" && $("#member_id_search").val().trim()=="") {
					swal("", "清輸入條件!", "info");
				} else {
					$(":input[id='member_no']").val($("#member_no_search").val().trim());
					$(":input[id='member_id']").val($("#member_id_search").val().trim());
					$(":input[id='chinese_name']").val($("#chinese_name_search").val().trim());					
					doQuery();
				}
			})

			$(":input[type='text']").keypress(function(e){
				var code =(e.keycode?e.keycode:e.which)
				if(code==13){
					if($("#chinese_name_search").val().trim()=="" && $("#member_no_search").val().trim()=="" && $("#member_id_search").val().trim()=="") {
						swal("", "清輸入條件!", "info");
					} else {
						$(":input[id='member_no']").val($("#member_no_search").val().trim());
						$(":input[id='member_id']").val($("#member_id_search").val().trim());
						$(":input[id='chinese_name']").val($("#chinese_name_search").val().trim());					
						doQuery();
					}
				}
			})
		})
		
		function doQuery(){
			formUtil.submitTo({
				url: "MEMBER.do?action=doQuery",
				async: true,
				formObj: $(form),
				onSuccess: function(jsonData){
					if(jsonData.data.DATA.length==0){
						swal("", "查無此會員!", "info");
						$("div[class='content']","div[id='main']").empty();
						$("div[id='search']").show();
					} else if(jsonData.data.DATA.length>1) {
						var Message = "輸入條件查詢到多筆資料，請重新確認! \n";
						var member="";			
						for (i=0;i<jsonData.data.DATA.length;i++) {
							member+=jsonData.data.DATA[i].MEMBER_NO+jsonData.data.DATA[i].CHINESE_NAME+jsonData.data.DATA[i].TICKET+"\n";
						}
						swal("", Message+member, "info");
						$("div[class='content']","div[id='main']").empty();
						$("div[id='search']").show();
					} else {
						$("div[id='content']","div[id='main']").empty();
						for (i=0;i<jsonData.data.DATA.length;i++) {	
							
							$("div[id='content']","div[id='main']").append("<p>"+jsonData.data.DATA[i].MEMBER_NO+"  "+jsonData.data.DATA[i].CHINESE_NAME+"</p>");
							if(jsonData.data.DATA[i].MEMBER_REF!="NULL" && jsonData.data.DATA[i].MEMBER_REF!="") {
								$("div[id='content']","div[id='main']").append("<p> 代表人:"+jsonData.data.DATA[i].MEMBER_REF+"</p>");
							}
							$("div[id='content']","div[id='main']").append("<p>票數:"+jsonData.data.DATA[i].TICKET+"</p>");
							$("div[id='content']","div[id='main']").append("<ul class='actions'><li><input type='button' class='button special' value='親自報到'></li><li><input type='button' class='button' value='委託報到'></li></ul>");	
							$("div[id='search']").hide();
							$("#ticket").val(jsonData.data.DATA[i].TICKET);
							$("#member_no").val(jsonData.data.DATA[i].MEMBER_NO);
							$("#chinese_name").val(jsonData.data.DATA[i].CHINESE_NAME);
							//form.member_no.value=jsonData.data.DATA[i].MEMBER_NO;
							
							$(":input[value='親自報到']").click(function(){
								$("#situation").val("1");				
								//alert(form.member_no.value);
								form.submit();
							});
							$(":input[value='委託報到']").click(function(){
								$("#situation").val("2");
								form.submit();
							});
							if (jsonData.data.DATA[i].SITUATION=="Y") {
								if (jsonData.data.DATA[i].AGENT!="NULL" && jsonData.data.DATA[i].AGENT!="" ) {
									swal("", "您已經委託"+jsonData.data.DATA[i].AGENT+"報到且已報到，無法重複報到", "info");
								} else {
									swal("", "您已經報到過，無法重複報到!", "info");
								}		
								$(":input[value='親自報到']").hide();
								$(":input[value='委託報到']").hide();
								return false;
							}
							if(jsonData.data.DATA[i].CHINESE_NAME!="" && (jsonData.data.DATA[i].TICKET=="" || jsonData.data.DATA[i].TICKET=="NULL" || jsonData.data.DATA[i].TICKET=="0")) {
								$("#situation").val("1");
								form.submit();
							}
						}	
						
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
		<form name="form" action="MEMBER_CHECK.jsp" method="post">
		<div id="wrapper">
			<!-- Header -->
			<!-- Main -->	
			<br>
			<br>	
			<div id="main" align="center" style="font-size:24px;">
				<div class="inner">
					<h1>會員報到</h1>
					<div id="search">
						<p>請輸入會員身份證字號(統一編號)或會員名稱或會員編號</p>
						<div class='field'><input type='text' id='member_id_search'  placeholder='會員身分證(統一編號)'></div>
						<div class='field half first'><input type='text' id='chinese_name_search'  placeholder='會員名稱(請輸入完整名稱)'></div>
						<div class='field half'><input type='text' id='member_no_search' placeholder='會員編號'></div>						
						<div class='field'><input type='button' value='確認'></div>
					</div>
					<div id="content">
					</div>
				</div>
			</div>
		</div>	
		<input type="hidden" name="member_no" id="member_no" value="<%=member_no%>">
		<input type="hidden" name="member_id" id="member_id">
		<input type="hidden" name="chinese_name" id="chinese_name" >
		<input type="hidden" name="ticket" id="ticket">
		<input type="hidden" name="situation" id="situation">
		</form>		
	</body>
</html>