<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../utility/PAGEINIT.jsp"%>
<%

%>
<html>
<head>
<title>EMAIL</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/CSS.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/TEXT.css" type="text/css">
<%@include file="../utility/JQUERY_OLD.jsp"%>
<script language="javascript">

	$(document).ready(function(){
		doQuery();
		$(":input[id='showdetail']").click(function(){

			formUtil.submitTo({
				url: "GET_MEMBER_EMAIL.do?action=doQueryDetail",
				async: true,
				formObj: $(form),
				onSuccess: function(jsonData){

					$span = $("span[id=spandata]")
					$span.html(
							"<table width='100%' border='1' cellpadding='4' cellspacing='0'> "+
							"<tr>"+
								"<td class='title'>"+
									"<table width='100%'>"+
									"<tr>"+
									"<td>會員詳細資料</td>" +
									"</tr>"+
									"</table>"+
								"</td>"+
							"</tr>"+
							"</table>"+
							"<table id='tablesorter' class='tablesorter-blue' border='1' cellpadding='0' cellspacing='1' >" +
								"<thead>" +
									"<tr>" +
										"<th>編號</th>" +
										"<th>ser_no</br>ip_base_no</th>" +
										"<th>cust_name</th>" +
										"<th>cust_type</th>" +
										"<th>private_email</th>" +
										"<th>addr_no</th>" +
										"<th>addr_desc</th>" +
									"</tr>" +	
									
								"</thead>" +
							"<tbody>"+
							"</tbody>" +							
							"</table>"
						);
							
					$tbody = $("#tablesorter>tbody").html("");
				
					if( jsonData.data.DATA.length==0 ) {
						$tbody.html("<tr><td colspan='7'>查無資料</td></tr>"); 
					}else{
						pattern= "<td>@{SER_NO}<br>@{IP_BASE_NO}</td>";
						pattern+= "<td>@{CUST_NAME}</td>";
						pattern+= "<td>@{CUST_TYPE}</td>";
						pattern+= "<td>@{PRIVATE_EMAIL}</td>";
						pattern+= "<td>@{ADDR_NO}</td>";
						pattern+= "<td>@{ADDR_DESC}</td>";				
						for(var i=0;i<jsonData.data.DATA.length;i++) {
							$tr = $("<tr style='cursor:pointer'></tr>").data("json", jsonData.data.DATA[i]).appendTo($tbody);					
							$tr.append("<td>"+(i+1)+"</td>");		
							$tr.append(strUtil.tranPattern(pattern, jsonData.data.DATA[i], ""));
						}
						set_table();
					}
				}
			});
		})
		top.closeProcessBar();
	});
	
	var total_count="0";
	function doQuery()
	{
		formUtil.submitTo({
			url: "GET_MEMBER_EMAIL.do?action=doQuery",
			async: false,
			formObj: $(form),
			onSuccess: function(jsonData){
				
				for(i=0;i<jsonData.data.DATA.length;i++) {
					$("td[id='member']").append(jsonData.data.DATA[i].PRIVATE_EMAIL+";")
					if(jsonData.data.DATA[i].CUST_TYPE==1) {
						$("td[id='member_M']").append(jsonData.data.DATA[i].PRIVATE_EMAIL+";")
					}
					if(jsonData.data.DATA[i].CUST_TYPE==2) {
						$("td[id='member_U']").append(jsonData.data.DATA[i].PRIVATE_EMAIL+";")
					}
				}
				total_count=jsonData.data.DATA.length;
				doQuery_extra();
			}			
		});
		
	}
	
	function doQuery_extra(){
		formUtil.submitTo({
			url: "GET_MEMBER_EMAIL.do?action=doQuery_Extra",
			async: false,
			formObj: $(form),
			onSuccess: function(jsonData){
				var extra_member1_cnt=0;
				var extra_member4_cnt=0;
				for(i=0;i<jsonData.data.DATA.length;i++) {
					if(jsonData.data.DATA[i].EMAIL_KIND.indexOf("1")>-1) {
						$("span[id='extra_member1']").append(jsonData.data.DATA[i].EMAIL+";");
						extra_member1_cnt++;
					}
					if(jsonData.data.DATA[i].EMAIL_KIND.indexOf("4")>-1) {
						$("span[id='extra_member4']").append(jsonData.data.DATA[i].EMAIL+";");
						extra_member4_cnt++;
					}
				}
				$("td[id='end']").append("</br>總筆數:"+total_count);
				$("td[id='end']").append("</br>無法辨識清單額外發送筆數:"+extra_member1_cnt);
				$("td[id='end']").append("</br>會務新訊額外發送筆數:"+extra_member4_cnt);
			}
			
		});
	}
	
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" >
<form name="form" action="" method="post">
<table style=width:100%>
	<tr>	
		<td>
			<font color="red" size="4">
				-------------------會員EMAIL Start-------------------
			</font>
		</td>
	</tr>
	
	<tr>	
		<td>
			<font color="green">
				-------------------全體會員的EMAIL-------------------
			</font>
		</td>
	</tr>
	
	<tr>	
		<td id="member" style=width:100%>
			<input type="hidden" id="ret">
		</td>
	</tr>
	
	<tr>	
		<td>
			<font color="green">
				-------------------個人會員的EMAIL-------------------
			</font>
		</td>
	</tr>
	
	<tr>	
		<td id="member_M" style=width:100%>
		</td>
	</tr>
	
	<tr>	
		<td>
			<font color="green">
				-------------------團體會員的EMAIL-------------------
			</font>
		</td>
	</tr>
	
	<tr>	
		<td id="member_U" style=width:100%>
		</td>
	</tr>
	
	<tr>	
		<td>
			<font color="green">
				-------------------以下是無法辨識清單要額外發的EMAIL-------------------
			</font>
		</td>
	</tr>
	
	<tr>	
		<td style="width:100%;">
			<span id="extra_member1" style="color:blue;">
			</span>
		</td>
	</tr>
	
	<tr>	
		<td>
			<font color="green">
				-------------------以下是會務新訊要額外發的EMAIL-------------------
			</font>
		</td>
	</tr>
	
	<tr>	
		<td style="width:100%;">
			<span id="extra_member4" style="color:blue;">
			</span>
		</td>
	</tr>
	
	<tr>	
		<td id="end">
			<font color="red" size="4">
				-------------------會員EMAIL end-------------------
			</font>
		</td>
	</tr>
	
	<tr>	
		<td>
			<input type="button" id="showdetail" value="顯示詳細資料" class="btn1">
		</td>
	</tr>
</table>

<span id="spandata">

</span>
</form>
</body>

</html>