<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%

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
<%@include file="FANCYBOX_REL.jsp"%>
<script>
	$(document).ready(function(){
		
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
		$("button[name='title']").on("click",function(){
			$("input[name='user_title']").val($("input[name='user_cname']").val());
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
			formUtil.submitTo({
				url: "LICENSE.do?action=doSave",
				async: true,
				formObj: $(form),
				onSuccess: function(jsonData){
					swal({
						  title: "資料已送出",
						  text: "是否保留收件人或代辦、聯絡電話、EMAIL資料?",
						  type: "warning",
						  showCancelButton: true,
						  confirmButtonColor: "#DD6B55",
						  confirmButtonText: "確認",
						  cancelButtonText: "取消",
						  closeOnConfirm: false,
						  closeOnCancel: false
						},
					function(isConfirm){
						if (isConfirm) {
							$("input[type='text']").each(function(){
								if(!($(this).attr("name")=="cnt1_name"||$(this).attr("name")=="cnt1_sphone"||$(this).attr("name")=="cnt1_email")){
									$(this).val("");
								}
							});
							swal("", "資料保留成功", "success");
						} else {
							$("input[type='text']").each(function(){
								$(this).val("");
							});
							swal("", "完成填寫", "success");
						}
					});	
				}
			});
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
			if($(this).val()=="電腦伴唱機音樂著作公開演出申請表") {
				$("li[id='word']").html("本會電腦伴唱機使用報酬率每台每一年度(12個月)為新台幣伍仟元整(含稅後為伍仟貳佰伍拾元整)，實際應支付本會新台幣伍仟貳佰伍拾元整(含稅)，期滿後須再續約。新增機台請提出證明文件確立使用起始時間，再依照月份比例支付音樂授權費用。");
			}
			if($(this).val()=="電腦伴唱機(公益且無營利)音樂著作公開演出申請表") {
				$("li[id='word']").html("本會為公益性等目的而利用電腦伴唱機且無涉及營利之使用報酬率每台每一年度(12個月)為新台幣叁仟貳佰元(含稅後為叁仟叁佰陸拾元整)，實際應支付本會新台幣叁仟叁佰陸拾元整(含稅)，期滿後須再續約。新增機台請提出證明文件確立使用起始時間，再依照月份比例支付音樂授權費用。");
			}
			if($(this).val()=="電腦伴唱機(文化教育)音樂著作公開演出申請表") {
				$("li[id='word']").html("本會為文化、教育或其他公益性之目的而利用電腦伴唱機之使用報酬率每台每一年度(12個月)為新台幣肆仟元整(含稅後為肆仟貳佰元整)，實際應支付本會新台幣肆仟貳佰元整(含稅)，期滿後須再續約。新增機台請提出證明文件確立使用起始時間，再依照月份比例支付音樂授權費用。");
			}
		});
	});
	
	
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" >
<form name="form" id="form" action="" method="post" class="contact_form">
<img src="<%=request.getContextPath()%>/images/banner.jpg" style="width:100%">
<div  style="width:87%;margin: 0 auto;background-color:#FFFFFF;font-size:14px;">			
	 		<div align="center"><font size="5"><b><BR><font id="title_name">電腦伴唱機音樂著作公開演出申請表</font></b></font></div>
	 		<div style="margin-left:5%;margin-top:60px">	 		
			<div  class="required_notification" style="margin-left:6%">
				請選擇類型:
		 		<select id="kind" name="kind">
					  <option value="電腦伴唱機音樂著作公開演出申請表" selected>一般</option>
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
	 				<font class="required_notification">發票抬頭：&nbsp</font><input type="text" name="user_title" style="width:40%"  required />  <button name="title" class="submit" onclick="return false;">同營業場所名稱 </button>       			
	 			</div>
	 			<div class="content">
	 				<font class="required_notification">申請授權期間：</font>西元 <input type="text" name="cont_bdate_yy" style="width:8%" title="請輸入西元年共4碼數字" min="0" max="9999" pattern="[0-9]{4}" maxlength ="4" required/> 年 <input type="text" name="cont_bdate_mm" style="width:6%" pattern="\d*" maxlength ="2" required/> 月 <input type="text" name=cont_bdate_dd style="width:6%" pattern="\d*" maxlength ="2" required/> 日起至西元 <input type="text" name="cont_edate_yy" style="width:8%" title="請輸入西元年共4碼數字" min="0" max="9999" pattern="[0-9]{4}" maxlength ="4" required/> 年 <input type="text" name="cont_edate_mm" style="width:6%" pattern="\d*" maxlength ="2" required/> 月 <input type="text" name="cont_edate_dd" style="width:6%" pattern="\d*" maxlength ="2" required/> 日止        			
	 			</div>
	 			<div class="content">
	 				<font class="required_notification">營業地址：</font><input type="text" name="user_post" placeholder="郵遞區號" title="請輸3碼數字" min="0" max="999" pattern="[0-9]{3}" maxlength ="3" style="width:6%" required /> <input type="text" name="user_addr" style="width:40%"  required />        			
	 			</div>
	 			<div class="content">
	 				<font class="required_notification">郵寄地址：</font><input type="text" name="user_cpost" placeholder="郵遞區號" title="請輸3碼數字" min="0" max="999" pattern="[0-9]{3}" maxlength ="3" style="width:6%" required /> <input type="text" name="user_caddr" style="width:40%"  required /><button name="caddr" class="submit" onclick="return false;">同營業地址 </button> 
	 			</div>
	 			<div class="content">
	 				<font class="required_notification">收件人或代辦：</font><input type="text" name="cnt1_name" style="width:15%"  required />        			
	 				<font class="required_notification">&nbsp&nbsp聯絡電話：</font><input type="text" name="cnt1_sphone" placeholder="" pattern="\d*" maxlength ="10" size='10' required />
	 				&nbsp&nbsp <font class="required_notification">E-MAIL：</font> <input type="text" name="cnt1_email" style="width:18%" required />  		              		            	 				
	 			</div>
	 			<div class="content">
	 				<font>使用機器：</font>
	 				<BR>
	 				<table id="use_machine" >
		 				<tr>
		 					<td class="required_notification">廠牌名稱</td><td class="required_notification">機號</td>
		 				</tr>
		 				<tr>
		 					<td><input type="text" name="factory" required></td><td><input type="text" name="machine" required></td>
		 				</tr>
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
          	<p><a class="fancybox" href="Kar2.html">公開演出概括授權契約書(點此進一步了解)</a></p>
          	<input type="checkbox" class="w3-check" name="iagree"><font color="red">本人已閱覽並同意接受本申請表之條款內容，且願履行及遵守該等約定條款。</font>         	
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
</form>
</body>

</html>