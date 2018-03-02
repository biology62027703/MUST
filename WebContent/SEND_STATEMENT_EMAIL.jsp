<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%

%>
<html>
<%@include file="HEAD.jsp"%>
<style>
body { 
	    background-image: url('images/login_bg.jpg');
	    opacity:1;
	    background-size: cover;
	    background-repeat: no-repeat;
	    background-attachment: fixed;
	    background-position: center; 
	}
 
    a,h1 {
    	color: white;
	} 
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>
<script src="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.css">
<%@include file="TOP.jsp"%>
<script>
	
	$(document).ready(function(){
		
		$(":input[value='開始寄信']").on("click",function(){	
			var check = checknull();
			if(check){
				var excel = $("#file").val();
		        if(excel == null || excel==""){
		            alert("未選擇上傳檔案");
		        }else{
		        	var reg = /^.*\.(?:xlsx)$/i;//檔名可以帶空格
	                if (!reg.test(excel)) {//不通過
	                    alert("請上傳xlsx格式的文件!");
	                    return false;
	                } else {
	                	formUtil.submitTo({
	    					url: "SEND_STATEMENT_EMAIL.do?action=doPost",
	    					async: false,
	    					formObj: $(form),
	    					contentType: 'multipart/form-data',
	    					onSuccess: function(jsonData){
	    					}
	    				});
	                	
	                }
		        }
			}
		});
	})
	
	function doQuery(){
		//alert(0);
		/* formUtil.submitTo({
			url: "MEMBER_CHECK.do?action=doQuery",
			async: true,
			formObj: $(form),
			onSuccess: function(jsonData){
				
			}			
		}); */
	}
	function checknull(){
		var ret=true;
		$(":text[check='S00']").each(function(){
			if($(this).val().trim()=="") {
				alert("抱歉、此欄位不允許空白!");
				$(this).focus();
				ret = false;
				return false;
			}
		});
		return ret;
	}
	
	
</script>

	<head>
		<title>MÜST會員群體發信功能</title>
	</head>
	
	<body>
		<!-- Wrapper -->
		<form name="form" id="form" method="post" enctype="multipart/form-data">
		<div id="wrapper">
			<!-- Header -->
			<!-- Main -->	
			<br>
			<br>	
				<div class="row uniform">													
					<div class="6u 12u$(xsmall)">
						<input type="text" check="S00" name="payment_no" id="payment_no" value="" placeholder="ROY代號" required/>
					</div>
					<div class="6u$ 12u$(xsmall)">
						<input type="text" check="S00" name="payment_date" id="payment_date" value="" placeholder="解款日期(範例格式 :2018.01.01)" />
					</div>
					
					<div id="content">
						<input type="file" name="FILE" id="file" data-id="fileUpload" />
					</div>
					
				    <div class="field">		
						<input type="button" value="寄給自己測試" >
					</div>
					
					<div class="field">		
						<input type="button" value="開始寄信" >
					</div>					
					
				</div>
			</div>
		
		</form>		
	</body>
</html>