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
			var checked;
			doQuery();		
			$("#case_cont").on("click",function(){
				//有公同費率的話再把MARK拿掉
				//$("input[name='cont']:checked").each(function() {
					checked=true;
				//});
				if(checked) {
					checked=false;
					$("input[name='chk']:checked").each(function() {
				    	$(":input[name='user_no']").val($(":input[name='user_no']").val()+$(this).attr("id")+",");
				    	$(":input[name='user_cname']").val($(":input[name='user_cname']").val()+$(this).attr("user_cname")+",");
				    	$(":input[name='user_title']").val($(":input[name='user_title']").val()+$(this).attr("user_title")+",");
				    	$(":input[name='doc_no']").val($(":input[name='doc_no']").val()+$(this).attr("doc_no")+",");
				    	$(":input[name='cont_bdate']").val($(":input[name='cont_bdate']").val()+$(this).attr("cont_bdate")+",");
				    	$(":input[name='cont_edate']").val($(":input[name='cont_edate']").val()+$(this).attr("cont_edate")+",");
				    	$(":input[name='kind']").val($(":input[name='kind']").val()+$(this).attr("kind")+",");
				    	$(":input[name='rec_dprice']").val($(":input[name='rec_dprice']").val()+$(this).attr("rec_dprice")+",");
				    	$(":input[name='nature']").val($(":input[name='nature']").val()+$(this).attr("nature")+",");
				    	checked=true;
				    });
					
					if(checked) {
						//doInsert();
						formUtil.submitTo({
							url: "LICENSE.do?action=InsertCaseCont",
							async: true,
							formObj: $(form),
							onSuccess: function(jsonData){
								swal("", jsonData.data.MSG , "info");
								doQuery();
							}
						});
					} else {
						swal("", "請勾選要填入個案編號及合約編號的資料!", "error");
					}
				} else {
					swal("", "請選擇合約類型!", "error");
				}
				
			})
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
										"<th style='width:25%'>營業場所名稱</th>"+
										"<th style='width:10%'>負責人</th>"+
										"<th style='width:12%'>授權日期區間</th>"+
										"<th style='width:12%'>授權系統客戶編號</th>"+
										"<th style='width:6%'>金額</th>"+
										"<td style='width:6%'><div class='6u$ 12u$(small)'><input type='checkbox' id='clickAll' name='clickAll' ><label for='clickAll'>選取</label></div></td>"+
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
						pattern = "<td id='click'>@{DOC_NO}<BR><font color='red'>@{NATURE-nature}</font></td>";
						pattern+= "<td id='click'>@{USER_CNAME}</td>";
						pattern+= "<td id='click'>@{USER_COPMAN}</td>";
						pattern+= "<td id='click'>@{CONT_BDATE}~@{CONT_EDATE}</td>";
						pattern+= "<td id='click'>@{USER_NO}</td>";		
						pattern+= "<td id='click'>@{REC_DPRICE}</td>";	
						
						for(var i=0;i<jsonData.data.length;i++) {
							$tr = $("<tr id='go' class='fancybox' doc_no='"+jsonData.data[i].DOC_NO+"' style='cursor:pointer'></tr>").data("json", jsonData.data[i]).appendTo($tbody);					
							$tr.append("<td>"+(i+1)+"</td>");		
							$tr.append(strUtil.tranPattern(pattern, jsonData.data[i], datamapping));
							$tr.append("<td><div class='6u$ 12u$(small)'><input type='checkbox' id='"+jsonData.data[i].USER_NO+"' name='chk' doc_no='"+jsonData.data[i].DOC_NO+"' cont_bdate='"+jsonData.data[i].CONT_BDATE+"' cont_edate='"+jsonData.data[i].CONT_EDATE+"' kind='"+jsonData.data[i].KIND+"' rec_dprice='"+jsonData.data[i].REC_DPRICE+"' user_cname='"+jsonData.data[i].USER_CNAME+"' user_title='"+jsonData.data[i].USER_TITLE+"' nature='"+jsonData.data[i].NATURE+"' ><label for='"+jsonData.data[i].USER_NO+"'></label>&nbsp</div></td>");
						}
						$("td[id='click']").on("click",function(){
							window.open("<%=request.getContextPath()%>/LICENSE/LICENSE_CONFIRM.jsp?doc_no="+$(this).parent().attr("doc_no")+"&edit=none");
						})
						set_table();
						$("#clickAll").click(function() {
							if($("#clickAll").prop("checked")) {
								$("input[name='chk']").each(function() {
							    	$(this).prop("checked", true);
							    });
							} else {
							    $("input[name='chk']").each(function() {
							        $(this).prop("checked", false);
							    });           
							}
						});
					}	
					
				}
			});
			checked=false;
		}
	</script>
	<body>
		<form name="form">
		
		<div id="wrapper">
			<div id="main">
				<div class="inner">				
					<section>	
						<h2></h2>					
						<h1 align="center">線上申請授權待填入個案、合約編號清單</h1>
				
						<div class="table-wrapper">
							<div  align="left">		
							<b><font color="white" size="4">合約備註:<input type="text"  name="cont_note" maxlength="150"></font></b>														

							</div>
							<div  align="right">																			
								<ul class="actions">								
									<!-- <b><font color="white">請選擇合約類型:</font></b>	
									<li><input type="radio" id="demo-priority-low" name="cont" value="L" checked>
										<label for="demo-priority-low"><b><font color="white">一般</font></b></label>
									</li>
									<li><input type="radio" id="demo-priority-normal" name="cont" value="P">
										<label for="demo-priority-normal"><b><font color="white">共同費率(未來有再勾吧!!!)</font></b></label>
									</li> -->		
									<li><input type="button" id="case_cont" value="產生個案&合約編號" class="special" /></li>
								</ul>
							</div>
							<span id="spandata">
								
							</span>						
						</div>
					</section>
				</div>
			</div>
		
		</div>	
		<input type="hidden" name="status" value="2">		
		<input type="hidden" name="cont_kind" value="L">
		<input type="hidden" name="login_id" value="<%=user_name%>">
		<input type="hidden" name="user_no" value="">
		<input type="hidden" name="user_cname" value="">
		<input type="hidden" name="user_title" value="">
		<input type="hidden" name="doc_no" value="">
		<input type="hidden" name="cont_bdate" value="">
		<input type="hidden" name="cont_edate" value="">
		<input type="hidden" name="kind" value="">
		<input type="hidden" name="rec_dprice" value="">
		<input type="hidden" name="nature" value="">
		</form>
			
	</body>
</html>