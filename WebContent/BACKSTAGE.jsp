<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
			doQuery();
		})
		function doQuery(){
			formUtil.submitTo({
				url: "BACKSTAGE.do?action=doQuery",
				async: true,
				formObj: $(form),
				onSuccess: function(jsonData){						
													
				}
			});
		}
	</script>
	<!-- Wrapper -->	
	<body>
		<div id="wrapper">
			<!-- Header -->
			<%@include file="TOP.jsp"%>
			<!-- Main -->			
		<style>
			table{
				background-color:white;
			}		
		</style>
		<form name="form">
			<div id="main">
				<div class="inner">
					<h1>後台功能</h1>
					<section>
						
						<h3>Alternate</h3>
						<div class="table-wrapper">
							<table class="alt">
								<thead>
									<tr>
										<th>TYPE</th>
										<th>NAME</th>
										<th>HREF</th>
										<th>SEQ</th>
										<th>SHOW</th>
									</tr>
								</thead>
								<tbody id="content">
									<tr>
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
									</tr>
								</tbody>
								
							</table>
						</div>
					</section>
				</div>
			</div>
		</form>
		</div>		
	</body>
</html>