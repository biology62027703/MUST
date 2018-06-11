<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<%@include file="../HEAD.jsp"%>	
	<%@include file="../FANCYBOX_REL.jsp"%>
	<%@include file="../COOKIE_URL.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/DATAMAPPING.js"></script>
	<script src="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.min.js"></script>
	<%@include file="../utility/tablesorder.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.css">
	<style>
		table{
			background-color:white;
		}		
	</style>
	<script>
		$(document).ready(function(){	
			
			doQuery();		
			
		})
		function doQuery(){
			formUtil.submitTo({
				url: "LICENSE.do?action=doQuery",
				async: true,
				formObj: $(form),
				onSuccess: function(jsonData){
					$span = $("span[id=spandata]")
					$span.html(
							"<table id='tablesorter' class='tablesorter-blue' border='1' cellpadding='0' cellspacing='1' >" +
								"<thead>" +
									"<tr>" +
										"<th style='width:4%'>序號</th>"+
										"<th style='width:10%'>線上申請流水號<BR>申請類型</th>"+
										"<th style='width:25%'>營業場所名稱<br>負責人</th>"+
										"<th style='width:12%'>授權日期區間</th>"+
										"<th style='width:12%'>授權系統客戶編號</th>"+
										"<th style='width:12%'>個案編號</th>"+
										"<th style='width:12%'>合約編號</th>"+
								"</tr>" +									
								"</thead>" +
							"<tbody>"+
							"</tbody>"+							
							"</table>"
						);
					$tbody = $("#tablesorter>tbody").html("");
					if( jsonData.data.length==0 ) {
						$tbody.html("<tr><td colspan='7'>查無資料</td></tr>"); 
					}else{
						pattern = "<td>@{DOC_NO}<BR><font color='red'>@{NATURE-nature}</font></td>";
						pattern+= "<td>@{USER_CNAME}<br>@{USER_COPMAN}</td>";
						pattern+= "<td>@{CONT_BDATE}~@{CONT_EDATE}</td>";
						pattern+= "<td>@{USER_NO}</td>";		
						pattern+= "<td>@{CASE_NO}";
						pattern+= "<td>@{CONT_NO}</td>";						
						for(var i=0;i<jsonData.data.length;i++) {
							$tr = $("<tr id='go' class='fancybox' doc_no='"+jsonData.data[i].DOC_NO+"' style='cursor:pointer'></tr>").data("json", jsonData.data[i]).appendTo($tbody);					
							$tr.append("<td>"+(i+1)+"</td>");		
							$tr.append(strUtil.tranPattern(pattern, jsonData.data[i], datamapping));							
						}
						set_table();
					}	
					
				}
			});
			
		}
	</script>
	<body>
		<form name="form">
		
		<div id="wrapper">
			<div id="main">
				<div class="inner">				
					<section>	
						<h2></h2>					
						<h1 align="center">線上申請授權結案清單</h1>
				
						<div class="table-wrapper">						
							<span id="spandata">
								
							</span>						
						</div>
					</section>
				</div>
			</div>
		
		</div>	
		<input type="hidden" name="status" value="3">
		<input type="hidden" name="login_id" value="<%=user_name%>">
		<input type="hidden" name="user_no" value="">
		<input type="hidden" name="user_cname" value="">
		<input type="hidden" name="user_title" value="">
		<input type="hidden" name="doc_no" value="">
		<input type="hidden" name="cont_bdate" value="">
		<input type="hidden" name="cont_edate" value="">
		<input type="hidden" name="kind" value="">
		<input type="hidden" name="rec_dprice" value="">
		</form>
			
	</body>
</html>