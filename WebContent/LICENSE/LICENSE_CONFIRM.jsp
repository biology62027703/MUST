<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
CheckObject Check = new CheckObject();
String doc_no=(String)Check.checkNull(request.getParameter("doc_no"), "") ;
String edit=(String)Check.checkNull(request.getParameter("edit"), "") ;
%>
<html>
<head>
<title>MÜST內網</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mustweb/base.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mustweb/style.css" type="text/css">
<link rel="icon" type="<%=request.getContextPath()%>/image/png" sizes="16x16" href="<%=request.getContextPath()%>/images/license_logo.PNG">
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/mustweb/base_rwd.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mustweb/noMediaQuery.css" type="text/css"> --%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/TEXT.css" type="text/css">
<style type="text/css">
.content{margin:20px;border:0;padding:0;}
</style>
<script src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.tablesorter.widgets.min.js"></script>
<script src="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sweetalert-master/dist/sweetalert.css">
<%@include file="../FANCYBOX_REL.jsp"%>
<%@include file="../utility/tablesorder.jsp"%>
<%@include file="../COOKIE_URL.jsp"%>
<script>
	$(document).ready(function(){
		var doc_no = "<%=doc_no%>";
		if(!doc_no==""){
			formUtil.submitTo({ 
				url: "LICENSE.do?action=doQueryDetail",
				async: true,
				formObj: $(form),
				onSuccess: function(jsonData){
					for(i=0;i<jsonData.data.length;i++){
						$("input[name='user_copman']").val(jsonData.data[i].USER_COPMAN);
						$("input[name='idno']").val(jsonData.data[i].IDNO);
						$("input[name='cnt2_tel1_1']").val(jsonData.data[i].CNT2_TEL1.split("-")[0]);
						$("input[name='cnt2_tel1_2']").val(jsonData.data[i].CNT2_TEL1.split("-")[1]);
						$("input[name='cnt2_sphone']").val(jsonData.data[i].CNT2_SPHONE);
						$("input[name='cnt2_fax_1']").val(jsonData.data[i].CNT2_FAX.split("-")[0]);
						$("input[name='cnt2_fax_2']").val(jsonData.data[i].CNT2_FAX.split("-")[1]);
						$("input[name='user_title']").val(jsonData.data[i].USER_TITLE);
						$("input[name='user_cname']").val(jsonData.data[i].USER_CNAME);
						$("input[name='user_copno']").val(jsonData.data[i].USER_COPNO);
						$("input[name='user_tel_1']").val(jsonData.data[i].USER_TEL.split("-")[0]);
						$("input[name='user_tel_2']").val(jsonData.data[i].USER_TEL.split("-")[1]);
						$("input[name='user_fax_1']").val(jsonData.data[i].USER_FAX.split("-")[0]);
						$("input[name='user_fax_2']").val(jsonData.data[i].USER_FAX.split("-")[1]);
						$("input[name='user_post']").val(jsonData.data[i].USER_POST);
						$("input[name='user_addr']").val(jsonData.data[i].USER_ADDR);
						$("input[name='user_cpost']").val(jsonData.data[i].USER_CPOST);
						$("input[name='user_caddr']").val(jsonData.data[i].USER_CADDR);
						$("input[name='cnt1_name']").val(jsonData.data[i].CNT1_NAME);
						$("input[name='cnt1_sphone']").val(jsonData.data[i].CNT1_SPHONE);
						$("input[name='cnt1_email']").val(jsonData.data[i].CNT1_EMAIL);
						$("input[name='cont_bdate_yy']").val(jsonData.data[i].CONT_BDATE.substring(0,4));
						$("input[name='cont_bdate_mm']").val(jsonData.data[i].CONT_BDATE.substring(4,6));
						$("input[name='cont_bdate_dd']").val(jsonData.data[i].CONT_BDATE.substring(6,8));
						$("input[name='cont_edate_yy']").val(jsonData.data[i].CONT_EDATE.substring(0,4));
						$("input[name='cont_edate_mm']").val(jsonData.data[i].CONT_EDATE.substring(4,6));
						$("input[name='cont_edate_dd']").val(jsonData.data[i].CONT_EDATE.substring(6,8));
						$("input[name='user_class']").val(jsonData.data[i].USER_CLASS);						
						$("input[name='kind']").val(jsonData.data[i].KIND);
						$("#title_name").html(jsonData.data[i].KIND);
						$("input[name='nature']").each(function(){							
							if($(this).val()==jsonData.data[i].NATURE) {
								$(this).prop("checked",true);
							}
						});
					}
					
				}
			});
			setTimeout(function(){
				formUtil.submitTo({
					url: "LICENSE.do?action=doQueryMachine",
					async: true,
					formObj: $(form),
					onSuccess: function(jsonData){
						for(i=0;i<jsonData.data.length;i++){
							$("#use_machine").append(
								"<tr>"+
			 						"<td><input type='text' name='factory' value='"+jsonData.data[i].FACTORY+"' disabled></td><td><input type='text' name='machine' value='"+jsonData.data[i].MACHINE+"' disabled></td>"+
			 					"</tr>"		
							)
						}
						
					}
				});
				$(":input").each(function(){
					$(this).prop("disabled",true);
				});
				//授權期間可修正
				$( "input[name^='cont_']").each(function(){
					$(this).prop("disabled",false);
				});
				//新客戶、新增機台、舊客戶續約可修正
				$( "input[name='nature']").each(function(){
					$(this).prop("disabled",false);
				});
				
				//alert($("input[name='nature']:checked").val());
				
				$("input[name='user_no']").prop("disabled",false);

				$("#msg").append(
					"<textarea name='msg' id='message' cols='40' rows='6' placeholder='退回原因(同意如有改授權期間也建議填寫)'  ></textarea>"	
				)
				$("#confirm").append(
					"<button class='submit' onclick='return false' name='back'><font color='red'>退回</font></button>&nbsp&nbsp&nbsp&nbsp"+
					"<button class='submit' onclick='return false' name='submit' >同意</button>"
				)
				
				$("button[name='back']").on("click",function(){
					if($("#message").val()==""){
						swal("", "請填寫退回原因", "info");
					} else {
						$("input[name='status']").val("0");
						formUtil.submitTo({
							url: "LICENSE.do?action=doUpdate",
							async: true,
							formObj: $(form),
							onSuccess: function(jsonData){
								swal("", "退回成功", "info");
								setTimeout(function(){ window.location.href="LICENSE_LIST.jsp" }, 2000);								
							}
						});
						
					}
				});
				$("button[name='submit']").on("click",function(){
					if($("input[name='nature']").val()==""){
						alert("請選擇客戶類別!");
						check = false;
					}
					if($("input[name='nature']:checked").val()=='2'||$("input[name='nature']:checked").val()=='3') {
						if($("input[name='user_no']").val().trim()=='') {
							swal("", "請填寫客戶編號", "info");
							return false;
						}
					}
					if($("input[name='nature']:checked").val()=="1"){
						if($("input[name='user_no']").val().trim()!='') {
							swal("", "新客戶請勿填寫客戶編號", "info");
							return false;
						}
					}
					$("input[name='status']").val("2");
					
					formUtil.submitTo({
						url: "LICENSE.do?action=doUpdate",
						async: true,
						formObj: $(form),
						onSuccess: function(jsonData){							
							swal("", jsonData.data.MSG , "info");
							setTimeout(function(){ window.location.href="LICENSE_LIST.jsp" }, 2000);								
						}
					});
				});
				
				if("<%=edit%>"=="none") {

					$(":input").each(function(){
						$(this).prop("disabled",true);
					});

				}
			}, 200);
			
		}
		
	});
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" >
<form name="form" id="form" action="" method="post" class="contact_form">
<div  style="width:87%;margin: 0 auto;background-color:#FFFFFF;font-size:14px;">			
	 <div align="center"><font size="5"><b><BR><font id="title_name">電腦伴唱機音樂著作公開演出申請表</font></b></font></div>
 		<div style="margin-left:5%;margin-top:60px">
 		客戶類別:
 		<div>
			<input type="radio" id="demo-priority-low" name="nature" value="1" >
			<label for="demo-priority-low">新客戶</label>
		</div>
		<div>
			<input type="radio" id="demo-priority-normal" name="nature" value="2" >
			<label for="demo-priority-normal">舊客戶(續約)</label>
		</div>
		<div>
			<input type="radio" id="demo-priority-high" name="nature" value="3" >
			<label for="demo-priority-high">舊客戶(新增機台)</label>
		</div>
		<br>
		<br>
 		<h2>一、持證人或負責人資料：</h2>
 		<div style="margin-left:5%">
 			<div class="content">
 				<font>負責人：</font>  <input type="text" name="user_copman" style="width:10%"  />  
 				&nbsp&nbsp 身份證字號： <input type="text" name="idno" style="width:16%"/>    	 				
 			</div>
 			<div class="content">
 				<font>電話：&nbsp</font><input type="text" name="cnt2_tel1_1" placeholder="" pattern="\d*" maxlength ="3" style="width:6%"  /> - <input type="text" name="cnt2_tel1_2" placeholder="" maxlength="8" style="width:12%" pattern="\d*"  />
       			&nbsp&nbsp傳真：<input type="text" name="cnt2_fax_1" placeholder="" pattern="\d*" maxlength ="3" style="width:6%" /> - <input type="text" name="cnt2_fax_2" placeholder="" maxlength="8" style="width:12%" pattern="\d*" />
       			<font>&nbsp&nbsp行動電話：</font><input type="text" name="cnt2_sphone" placeholder="" pattern="\d*" maxlength ="10" style="width:18%"  />  		            
 			</div>	 		 
           </div>
         	  <h2>二、營業場所資料：</h2>
         	<div style="margin-left:5%">
 			<div class="content">
 				<font>營業場所名稱：</font>  <input type="text" name="user_cname" style="width:40%" maxlength="60"  />  
 				&nbsp&nbsp <font>統編：</font> <input type="text" maxlength="8" style="width:16%" pattern="\d*"  name="user_copno"  />    
 			</div>
 			<div class="content">
 				<font>營業場所電話：&nbsp</font><input type="text" name="user_tel_1" placeholder="" pattern="\d*" maxlength ="3" style="width:6%"  /> - <input type="text" name="user_tel_2" placeholder="" maxlength="8" style="width:16%" pattern="\d*"  />
       			&nbsp&nbsp傳真:&nbsp<input type="text" name="user_fax_1" placeholder="" pattern="\d*" maxlength ="3" size='3'/> - <input type="text" name="user_fax_2" placeholder="" maxlength="8" style="width:16%" pattern="\d*" />(性質：餐廳、釣蝦場、卡拉OK......)  		            
 			</div>
 			<div class="content">
 				<font>發票抬頭：&nbsp</font><input type="text" name="user_title" style="width:40%"   />        			
 			</div>
 			<div class="content">
 				<font>申請授權期間：</font>西元 <input type="text" name="cont_bdate_yy" style="width:8%" pattern="\d*" maxlength ="4" /> 年 <input type="text" name="cont_bdate_mm" style="width:6%" pattern="\d*" maxlength ="2" /> 月 <input type="text" name=cont_bdate_dd style="width:6%" pattern="\d*" maxlength ="2" /> 日起至西元 <input type="text" name="cont_edate_yy" style="width:8%" pattern="\d*" maxlength ="4" /> 年 <input type="text" name="cont_edate_mm" style="width:6%" pattern="\d*" maxlength ="2" /> 月 <input type="text" name="cont_edate_dd" style="width:6%" pattern="\d*" maxlength ="2" /> 日止        			
 			</div>
 			<div class="content">
 				<font>營業地址：</font><input type="text" name="user_post" placeholder="郵遞區號" pattern="\d*" maxlength ="3" style="width:6%"  /> <input type="text" name="user_addr" style="width:40%"   />        			
 			</div>
 			<div class="content">
 				<font>郵寄地址：</font><input type="text" name="user_cpost" placeholder="郵遞區號" pattern="\d*" maxlength ="3" style="width:6%"  /> <input type="text" name="user_caddr" style="width:40%"   />
 			</div>
 			<div class="content">
 				<font>收件人或代辦：</font><input type="text" name="cnt1_name" style="width:30%"   />        			
 				<font>&nbsp&nbsp聯絡電話：</font><input type="text" name="cnt1_sphone" placeholder="" pattern="\d*" maxlength ="10" size='10'  />
 				&nbsp&nbsp <font>E-MAIL：</font> <input type="text" name="cnt1_email" style="width:18%"  />  		              		            	 				
 			</div>
 			<div class="content">
 				<font>使用機器：</font>
 				<BR>
 				<table id="use_machine" >
	 				<tr>
	 					<td>廠牌名稱</td><td>機號</td>
	 				</tr>
	 				
 				</table>
 			</div>
 			<div class="content">
 				<font>客戶編號：</font>
 				<input type="text" name="user_no" style="width:40%" >
 			</div>
 			<div id="msg" name="msg" class="content">
 				
 			</div>
 			<div id="confirm" class="content">
 				
 			</div>	 		 
           </div>
         </div>
</div>
<input type="hidden" name="doc_no" value="<%=doc_no%>">
<input type="hidden" name="status" value="">
<input type="hidden" name="user_class" value="">
<input type="hidden" name="kind" value="">
</form>
</body>

</html>