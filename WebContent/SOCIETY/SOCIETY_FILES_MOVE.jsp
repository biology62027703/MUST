<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<%@include file="../HEAD.jsp"%>	
	<%@include file="../TOP.jsp"%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/DATAMAPPING.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.min.js"></script>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.css">
	<script>
		$(document).ready(function(){	
	
			$(":input[value='GO']").on("click",function(){
				$.blockUI({
                    message: "檔案處理中"
                });
	           	formUtil.submitTo({
					url: "SOCIETY_UNMATCH_FILE.do?action="+$("#method").val(),
					async: true,
					formObj: $(form),
					onSuccess: function(jsonData){
						$.unblockUI();
						swal("", jsonData.data , "info");						
					}
				}); 
			});
				
		})
		
		
	</script>
	
	<body>
		<!-- Wrapper -->
			
		<form name="form" id="form" method="post">
			<div id="wrapper">
				<div class="inner">				
					<section>	
						<h2></h2>	
						<h1 align="center">海外協會FTP檔案整批處理功能</h1>	
					<div class="row uniform">																
						<div class="6u 12u$(xsmall)">
							<b><font color="white" size="5">Unidentified or Royxxxxx : </font></b><input type="text" name="kind" id="kind" value="Unidentified" placeholder=" key in Unidentified or Royxxxxx" required/>
						</div>
						<div class="6u$ 12u$(xsmall)">
						<b><font color="white" size="5">insert or delete : </font></b>　	<input type="text"  name="method" id="method" value="insert" placeholder="insert or delete" />
						</div>
						
						<div class="field">		
							<input type="button" value="GO" >
						</div>					
						
					</div>
					</section>
				</div>
			</div>		
		</form>		
	</body>
</html>