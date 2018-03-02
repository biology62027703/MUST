<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%

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
		
		$(document).ready(function(){
			$(":input[value='查詢']").on("click",function(){
				doquery();
			});
		})
		
		function doQuery(){
			//alert(0);
			formUtil.submitTo({
				url: "MEMBER_CHECK.do?action=doQuery",
				async: true,
				formObj: $(form),
				onSuccess: function(jsonData){
					
				}			
			});
		}
		
		
	</script>
	<head>
		<title>MÜST後台管理</title>
	</head>
	
	<body>
		<!-- Wrapper -->
		<form name="form" action="" method="post">
		<div id="wrapper">
			<!-- Header -->
			<!-- Main -->	
			<br>
			<br>	
			<div id="main" align="center" style="font-size:24px;">
				<h2>額外發信EMAIL清單</h2>
				<div id="search" style="width:85%">
					<p>請輸入EMAIL或IP_BASE_NO</p>
					<div class='field half first'><input type='text' id='EMAIL_search'  placeholder='EMAIL(請輸入EMAIL)'></div>
					<div class='field half'><input type='text' id='IP_BASE_NO_search' placeholder='IP_BASE_NO'></div>

					
					<ul class="actions">
					<li>
						<input type="button" class="button special" value="查詢">
					</li>
					<li>
						<input type="button" class="button" value="新增清單">
					</li>
					</ul>
						<div style="border-width:3px;border-style:dashed;border-color:#FFAC55;padding:5px;">
							<input class='field half first' type='text' id='EMAIL_search'  placeholder='EMAIL(請輸入EMAIL)'>
							<input  class='field half' type='text' id='IP_BASE_NO_search' placeholder='IP_BASE_NO'>
						</div>
					<ul class="actions">
					</ul>
				</div>
				
				<section>	
					<h3></h3>
					<div class="table-wrapper">
						<table class="alt" style="width:95%">
							<thead>
								<tr>
									<th>公司名稱</th>
									<th>EMAIL</th>
									<th>IP_BASE_NO</th>
									<th>發信類別</th>
								</tr>
							</thead>
							<tbody id="content">
								<!-- <tr>
									<td>Item One</td>
									<td>Ante turpis integer aliquet porttitor.</td>
									<td>29.99</td>
								</tr>
								<tr>
									<td>Item Two</td>
									<td>Vis ac commodo adipiscing arcu aliquet.</td>
									<td>19.99</td>
								</tr>
								<tr>
									<td>Item Three</td>
									<td> Morbi faucibus arcu accumsan lorem.</td>
									<td>29.99</td>
								</tr>
								<tr>
									<td>Item Four</td>
									<td>Vitae integer tempus condimentum.</td>
									<td>19.99</td>
								</tr>
								<tr>
									<td>Item Five</td>
									<td>Ante turpis integer aliquet porttitor.</td>
									<td>29.99</td>
								</tr> -->
							</tbody>
							<tfoot>
								<!-- <tr>
									<td colspan="2"></td>
									<td>100.00</td>
								</tr> -->
							</tfoot>
						</table>
					</div>
				</section>
			</div>
		</div>	
		</form>		
	</body>
</html>