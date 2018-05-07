<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
CheckObject Check = new CheckObject();
String doc_no=(String)Check.checkNull(request.getParameter("doc_no"), "") ;
%>
<html>
<head>
<title>MÜST線上授權</title>
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

<script>
	var status="";
	$(document).ready(function(){	
		var doc_no="<%=doc_no%>";
		if(!doc_no==""){
			formUtil.submitTo({
				url: "LICENSE.do?action=doQueryDetail",
				async: false,
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
						$("#kind").children().each(function(){
							if($(this).val()==jsonData.data[i].KIND){
								$(this).prop("selected",true);
								changetitle($(this).val());
							}
						})
						$("input[name='nature']").each(function(){							
							if($(this).val()==jsonData.data[i].NATURE) {
								$(this).prop("checked",true);
							}
						});
						status=jsonData.data[i].STATUS;
					}
					
				}
			});
			setTimeout(function(){
				formUtil.submitTo({
					url: "LICENSE.do?action=doQueryMachine",
					async: false,
					formObj: $(form),
					onSuccess: function(jsonData){
						for(i=0;i<jsonData.data.length;i++){
							$("#use_machine").append(
							"<tr>"+
		 						"<td><input type='text' name='factory' value='"+jsonData.data[i].FACTORY+"' ></td><td><input type='text' name='machine' value='"+jsonData.data[i].MACHINE+"' ></td>"+
		 					"</tr>"		
							)
						}
						if(status=="1") {
							$(":input").each(function(){
								$(this).prop("disabled",true);
							});
							swal("", "您的資料已送出無須再修改", "info");
						}
						if(status=="2") {			
							$(":input").each(function(){
								$(this).prop("disabled",true);
							});
							swal("", "您的線上申請授權(流水號:"+doc_no+")已通過", "info");
						}
					}
				});				
			}, 200);			
			
		}
		
		
		$("button[name='add']").on("click",function(){
			$("table[id='use_machine']").append(
				"<tr>"+
					"<td>廠牌名稱</td><td>機號</td>"+
				"</tr>"+
				"<tr>"+
					"<td><input type='text' name='factory'></td><td><input type='text' name='machine'></td>"+
				"</tr>"	
			);
		});
		$("input[name='idno']").keyup(function(){
			$(this).val($(this).val().toUpperCase());
		});
		$("button[name='caddr']").on("click",function(){
			$("input[name='user_caddr']").val($("input[name='user_addr']").val());
			$("input[name='user_cpost']").val($("input[name='user_post']").val());
		});
		
		$("input[type='checkbox']").on("click",function(){
			if($("input[type='checkbox']").prop("checked")) {
				$("button[name='submit']").prop("disabled",false);
			} else {
				$("button[name='submit']").prop("disabled",true);
			}
		})
		//必填欄位驗證通過才會到這邊
		$("#form").submit(function () {	
			var check= true;
			if(!$("input[name='user_fax_1']").val()==""||!$("input[name='user_fax_2']").val()=="") {
				$("input[name^='user_fax_']").each(function(){
					if($(this).val()=="") {
						alert("請填寫完整的傳真號碼");
						$(this).focus();
						check = false;
					}
				});
			}
			if(!$("input[name='cnt2_fax_1']").val()==""||!$("input[name='cnt2_fax_2']").val()=="") {
				$("input[name^='cnt2_fax_']").each(function(){
					if($(this).val()=="") {
						alert("請填寫完整的傳真號碼");
						$(this).focus();
						check = false;
					}
				});
			}
			if($("input[name='cont_bdate_yy']").val()+$("input[name='cont_bdate_mm']").val()+$("input[name='cont_bdate_dd']").val()>=$("input[name='cont_edate_yy']").val()+$("input[name='cont_edate_mm']").val()+$("input[name='cont_edate_dd']").val()){
				alert("授權起日>=授權迄日，請重新確認授權日期!");
				check = false;
			}
			if(check) {
				formUtil.submitTo({
					url: "LICENSE.do?action=doSave",
					async: true,
					formObj: $(form),
					onSuccess: function(jsonData){
						if(jsonData.data.msg!=""){
							alert(jsonData.data.msg);
						} else {
							$("input[type='text']").each(function(){
								$(this).prop("disabled",true);
							});
							setTimeout(function(){
								swal("", "修改完成，資料已送出。", "success");
							}, 200);
						}
					}
				});
			}
            return false;
        });
		$( "input[name^='cont_']").focusout(function(){
			//alert($(this).attr("name"));
			if($(this).attr("name").indexOf("yy")>-1) {				
				if(!$(this).val()==""&& $(this).val().length==2){
					$(this).val("20"+$(this).val());
				}
				
			} else {
				if(!$(this).val()==""&&$(this).val().length<2){
					$(this).val("0"+$(this).val());
				}
			}
		});
		$("#kind").change(function(){
			$("#title_name").html($(this).val());
			changetitle($(this).val());
		})
		
	});
	function changetitle(value){
		$("#title_name").html(value);
		if(value=="電腦伴唱機音樂著作公開演出申請表") {
			$("li[id='word']").html("本會電腦伴唱機使用報酬率每台每一年度(12個月)為新台幣伍仟元整(含稅後為伍仟貳佰伍拾元整)，實際應支付本會新台幣伍仟貳佰伍拾元整(含稅)，期滿後須再續約。新增機台請提出證明文件確立使用起始時間，再依照月份比例支付音樂授權費用。");
		}
		if(value=="電腦伴唱機(公益且無營利)音樂著作公開演出申請表") {
			$("li[id='word']").html("本會為公益性等目的而利用電腦伴唱機且無涉及營利之使用報酬率每台每一年度(12個月)為新台幣叁仟貳佰元(含稅後為叁仟叁佰陸拾元整)，實際應支付本會新台幣叁仟叁佰陸拾元整(含稅)，期滿後須再續約。新增機台請提出證明文件確立使用起始時間，再依照月份比例支付音樂授權費用。");
		}
		if(value=="電腦伴唱機(文化教育)音樂著作公開演出申請表") {
			$("li[id='word']").html("本會為文化、教育或其他公益性之目的而利用電腦伴唱機之使用報酬率每台每一年度(12個月)為新台幣肆仟元整(含稅後為肆仟貳佰元整)，實際應支付本會新台幣肆仟貳佰元整(含稅)，期滿後須再續約。新增機台請提出證明文件確立使用起始時間，再依照月份比例支付音樂授權費用。");
		}
	}
	
</script>
<%@include file="../FANCYBOX_REL.jsp"%>
</head>

<body text="#000000" leftmargin="0" topmargin="0" >
<form name="form" id="form" action="" method="post" class="contact_form">
<img src="<%=request.getContextPath()%>/images/banner.jpg" style="width:100%">
<div  style="width:87%;margin: 0 auto;background-color:#FFFFFF;font-size:14px;">			
	 		<div align="center"><font size="5"><b><BR><font id="title_name">電腦伴唱機音樂著作公開演出申請表</font></b></font></div>
	 		<div style="margin-left:5%;margin-top:60px">	 		
			<div style="margin-left:5%;margin-top:60px">
		 		<div  class="required_notification" style="margin-left:6%">
		 		請選擇客戶類別:
		 		<div>
					<input type="radio" id="demo-priority-low" name="nature" value="1">
					<label for="demo-priority-low">新客戶</label>
				</div>
				<div>
					<input type="radio" id="demo-priority-normal" name="nature" value="2">
					<label for="demo-priority-normal">舊客戶(續約)</label>
				</div>
				<div>
					<input type="radio" id="demo-priority-high" name="nature" value="3">
					<label for="demo-priority-high">舊客戶(新增機台)</label>
				</div>	
			</div>
			<br>
			<br> 
			<div  class="required_notification" style="margin-left:6%">
				請選擇類型:
		 		<select id="kind" name="kind">
					  <option value="電腦伴唱機音樂著作公開演出申請表" selected>營利</option>
					  <option value="電腦伴唱機(公益且無營利)音樂著作公開演出申請表">公益且無營利</option>
					  <option value="電腦伴唱機(文化教育)音樂著作公開演出申請表">文化教育</option>
				</select>
			</div>
			<br>
	 		<h2>一、持證人或負責人資料：</h2>
	 		<div style="margin-left:5%">
	 			<div class="content">
	 				<font class="required_notification">負責人：</font>  <input type="text" name="user_copman" style="width:10%" required />  
	 				&nbsp&nbsp 身份證字號： <input type="text" name="idno" style="width:16%"/>    	 				
	 			</div>
	 			<div class="content">
	 				<font class="required_notification">電話：&nbsp</font><input type="text" name="cnt2_tel1_1" placeholder="" pattern="\d*" maxlength ="3" style="width:6%" required /> - <input type="text" name="cnt2_tel1_2" placeholder="" maxlength="8" style="width:12%" pattern="\d*" required />
        			&nbsp&nbsp傳真：<input type="text" name="cnt2_fax_1" placeholder="" pattern="\d*" maxlength ="3" style="width:6%" /> - <input type="text" name="cnt2_fax_2" placeholder="" maxlength="8" style="width:12%" pattern="\d*" />
        			<font class="required_notification">&nbsp&nbsp行動電話：</font><input type="text" name="cnt2_sphone" placeholder="" pattern="\d*" maxlength ="10" style="width:18%" required />  		            
	 			</div>	 		 
            </div>
          	  <h2>二、營業場所資料：</h2>
          	<div style="margin-left:5%">
	 			<div class="content">
	 				<font class="required_notification">營業場所名稱：</font>  <input type="text" name="user_cname" style="width:40%" maxlength="60" required />  
	 				&nbsp&nbsp <font class="required_notification">統編：</font> <input type="text" maxlength="8" style="width:16%"  name="user_copno" required />(若無統編請填無)   
	 			</div>
	 			<div class="content">
	 				<font class="required_notification">營業場所電話：&nbsp</font><input type="text" name="user_tel_1" placeholder="" pattern="\d*" maxlength ="3" style="width:6%" required /> - <input type="text" name="user_tel_2" placeholder="" maxlength="8" style="width:16%" pattern="\d*" required />
        			&nbsp&nbsp傳真:&nbsp<input type="text" name="user_fax_1" placeholder="" pattern="\d*" maxlength ="3" size='3'/> - <input type="text" name="user_fax_2" placeholder="" maxlength="8" style="width:16%" pattern="\d*" />(性質：餐廳、釣蝦場、卡拉OK......)  		            
	 			</div>
	 			<div class="content">
	 				<font class="required_notification">發票抬頭：&nbsp</font><input type="text" name="user_title" style="width:40%"  required />        			
	 			</div>
	 			<div class="content">
					<font class="required_notification">申請授權期間：</font>西元 <input type="text" name="cont_bdate_yy" style="width:8%" title="請輸入西元年共4碼數字" min="0" max="9999" pattern="[0-9]{4}" maxlength ="4" required/> 年 <input type="text" name="cont_bdate_mm" style="width:6%" pattern="\d*" maxlength ="2" required/> 月 <input type="text" name=cont_bdate_dd style="width:6%" pattern="\d*" maxlength ="2" required/> 日起至西元 <input type="text" name="cont_edate_yy" style="width:8%" title="請輸入西元年共4碼數字" min="0" max="9999" pattern="[0-9]{4}" maxlength ="4" required/> 年 <input type="text" name="cont_edate_mm" style="width:6%" pattern="\d*" maxlength ="2" required/> 月 <input type="text" name="cont_edate_dd" style="width:6%" pattern="\d*" maxlength ="2" required/> 日止	 			
				</div>
	 			<div class="content">
	 				<font class="required_notification">營業地址：</font><input type="text" name="user_post" placeholder="郵遞區號" pattern="\d*" maxlength ="3" style="width:6%" required /> <input type="text" name="user_addr" style="width:40%"  required />        			
	 			</div>
	 			<div class="content">
	 				<font class="required_notification">郵寄地址：</font><input type="text" name="user_cpost" placeholder="郵遞區號" pattern="\d*" maxlength ="3" style="width:6%" required /> <input type="text" name="user_caddr" style="width:40%"  required /><button name="caddr" class="submit" onclick="return false;">同營業地址 </button> 
	 			</div>
	 			<div class="content">
	 				<font class="required_notification">收件人或代辦：</font><input type="text" name="cnt1_name" style="width:30%"  required />        			
	 				<font class="required_notification">&nbsp&nbsp聯絡電話：</font><input type="text" name="cnt1_sphone" placeholder="" pattern="\d*" maxlength ="10" size='10' required />
	 				&nbsp&nbsp <font class="required_notification">E-MAIL：</font> <input type="text" name="cnt1_email" style="width:18%" required />  		              		            	 				
	 			</div>
	 			<div class="content">
	 				<font>使用機器：</font>
	 				<BR>
	 				<table id="use_machine" >
		 				<tr>
		 					<td class="required_notification">廠牌名稱</td><td class="required_notification">機號</td>
	 				</table>
	 			</div>	 		 
            </div>
          	</div>
          	<div align="center">
          		<button name="add" class="submit" onclick="return false">增加機器 </button>
          	</div>
          	<br><br>
          	<div style="margin-left:5%">
          	<h2>三、繳費方式：(※請勿自行內扣手續費)</h2>
          	<div style="margin-left:5%">
          	<ol>
			  <li>可至郵局劃撥（劃撥帳號：19321052，戶名：社團法人中華音樂著作權協會），另可銀行匯款至本協會銀行帳戶(帳戶名：社團法人中華音樂著作權協會)匯款銀行：台新國際商業銀行(銀行代號：812) 南京東路分行(分行代碼：0115) 帳號：2011-01-00021680  或開立7天內之即期支票 (檯頭：社團法人中華音樂著作權協會)。</li>
			  <li>備妥上述劃撥單收據影本、匯款收據影本或即期支票傳真或掛號寄至本協會。</li>
			  <li id='word'>本會電腦伴唱機使用報酬率每台每一年度(12個月)為新台幣伍仟元整(含稅後為伍仟貳佰伍拾元整)，實際應支付本會新台幣伍仟貳佰伍拾元整(含稅)，期滿後須再續約。新增機台請提出證明文件確立使用起始時間，再依照月份比例支付音樂授權費用。</li>
			  <li>契約存續期間內，如台端(貴單位)有新增或淘汰停用機台時，應於發生日起10日內以書面檢附相關證明文件通知本會，供本會更新授權資料及核算當年度應加計之授權金（詳申請表第2頁「概括授權契約書」第二點）。</li>
			</ol>
			</div>
			<h2>四、同意下述聲明：</h2>
			<div style="margin-left:5%">
          	<ol>
			  <li>如為辦理續約且經繳納全額使用報酬金額，視為同意原授權條件不變，但授權期間延續一年除外。</li>
			  <li>第二項申請授權期間，協會保有最後審核實際授權期間之權利，如有爭議，本人應提供相關文件以供核對，否則視為同意協會審核之期間。</li>
			  <li>本人承認所有填載之內容均為真實。</li>
			  <li>本人同意於契約年度到期前二個月內自動申請換發新證，過期未辦理仍繼續營業，願負法律責任。</li>
			</ol>
          	</div>
          	<div align="center">
          	<p><a class="fancybox" href="Kar2.html">公開演出概括授權契約書</a></p>
          	<input type="checkbox" class="w3-check" name="iagree"><font color="red">本人已閱覽並同意接受本申請表及條款內容，且願履行及遵守該等約定條款。</font>         	
          	<br><br><br>
          	<button class="submit" type="submit" name="submit" disabled>確認送出</button></div> <br> <br>
	</div>
</div>
<div class="footer" style="box-sizing: border-box;">
	<div class="footer_content">
		<div class="container">
		    <div class="copyright">© 2015 社團法人中華音樂著作權協會 MÜST All Rights Reserved.</div><!-- /.siteinfo -->
		    <div class="siteinfo">104台北市中山區南京東路二段71號4樓      TEL:02-2511-0869      FAX:02-2511-0759  </div>
		</div>
	</div>
</div>
<input type="hidden" name="user_class" value="KAR">
<input type="hidden" name="doc_no" value="<%=doc_no%>">
</form>
</body>

</html>