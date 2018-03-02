<%@page language="java" import="java.util.*,com.sertek.form.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/utility/PAGEINIT.jsp"%>
<%
	HashMap form = (HashMap)request.getAttribute("form");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="../css/CSS.css" type="text/css">
<%@include file="/utility/JQUERY_OLD.jsp"%>
<script type="text/javascript">

var formobj = <%=new com.google.gson.Gson().toJson(form)%>

$(document).ready(function(){
	
	//parent.showEditFrame();
	top.closeProcessBar();
		
	renderUtil.renderObject({
		targetObj: $(":checkbox[name=owners]"),
		data: [{TEXT:"移民署",VALUE:"GSO"},{TEXT:"線上起訴",VALUE:"SOL"},{TEXT:"後台",VALUE:"BOS"}],
		defaultCheckAll: true,
		isClear: true,
		attrpattern: "class='checkbox'", 
		textpattern: "@{TEXT}",
		valuepattern: "@{VALUE}"
	})
	
	$("textarea[name=msg]").keyup(function(){
		doView();
	});
	
	$(":button[value=取消]").click(function(){
		doReset();
	});
	
	$(":button[value=確定]").click(function(){ 
		doSave();
	});
	
	getEditIniData();
})

function doView() {
	
	$("span[id=view]").html(form.msg.value);
	
	$("a[file]").css("color", "red").css("cursor","pointer")
		.click(function(){			
			if( $("iframe[name=downiframe]").length==0 ) {
				$("<iframe style='display:none' name='downiframe'></iframe").appendTo($("body"));
			}
			if( $("form[name=downform]").length==0 ) {
				$("<form style='display:none' name='downform' target='downiframe' action='/BOS/DownLoad.do'><input type='text' name='action' value='S0BATT'><input type='text' name='filenm'></form>").appendTo($("body"));
			}
			var file = $(this).attr("file");
			$form = $("form[name=downform]");
			$(":text[name=filenm]", $form).val(file);
			$form[0].submit();
		});
}

function getEditIniData() {

	if( formobj.rowid == "" ) {
		return;
	}
	
	form.rowid.value = formobj.rowid;

	/* formUtil.submitTo({
		dataObj: formobj,
		url: "SBSO1L.do?action=getEditIniData",
		onSuccess: function(jsonData) {
			$(":checkbox[name=owners]").removeAttr("checked");
			formUtil.bindFormData($(form), formUtil.convertToLowCaseKey(jsonData.data[0]));
			
			renderUtil.renderObject({
				targetObj: $(":checkbox[name=owners]"),
				data: [{TEXT:"移民署",VALUE:"GSO"},{TEXT:"線上起訴",VALUE:"SOL"},{TEXT:"後台",VALUE:"BOS"}],
				defaultValue: jsonData.data[0].OWNERS,
				isClear: true,
				attrpattern: "class='checkbox'", 
				textpattern: "@{TEXT}",
				valuepattern: "@{VALUE}"
			})
		}		
	})  */
	doView();
}

function doBack()
{
	parent.showMainFrame();
	parent.mainFrame.doRefresh();
}

function doReset() {
	getEditIniData();
}

function checkDt(obj, f) {
	
	var ret = true;
	if( obj.value=="" ) {
		obj.focus();			
		alert("「"+f+"」不可為空白");
		ret = false
	}else if( obj.value!="" && !chkDate(obj) ) {
		obj.focus();	
		alert("請輸入正確的「"+f+"」");
		ret = false
	}
	return ret;
}

function doSave() {
	
	if( !checkDt(form.postdt, "發布日期") ) {
		return;
	}
	if( !checkDt(form.sdt, "上架起日") ) {
		return;
	}
	if( !checkDt(form.edt, "上架迄日") ) {
		return;
	}
	if( $(":checkbox[name=owners]:checked").length==0) {		
		alert("請選擇系統別");
		return;			
	}

	top.showProcessBar();
	
	formUtil.submitTo({
		formObj: $(form),
		url: "SBSO1L.do?action=doSave"
	});
	
	parent.hideFrame.location.href = "../RELOADALLSTATIC.jsp?owner=GSO,BOS,SOL";
	top.closeProcessBar();
	doBack()

}

function doRed() {
	
	var SelTxt = getSelectionTxt();
	if( SelTxt!="" ) {
		obj.value = obj.value.replace(SelTxt, "<font color='red'>"+SelTxt+"</font>");
	}
	doView();
	
}

function doB() {
	
	var SelTxt = getSelectionTxt();
	if( SelTxt!="" ) {
		obj.value = obj.value.replace(SelTxt, "<b>"+SelTxt+"</b>");
	}
	doView();
	
}

function doRedB() {
	
	var SelTxt = getSelectionTxt();
	if( SelTxt!="" ) {
		obj.value = obj.value.replace(SelTxt, "<font color='red'><b>"+SelTxt+"</b></font>");
	}
	doView();
	
}

function doBr() {	
	
	var SelTxt = getSelectionTxt();
	if( SelTxt=="" ) {
		InsertContent("<br>"); 
		doView();
	}
}

function getSelectionTxt() {
	var SelTxt = "";
	obj = form.msg;
	if ( document.selection ) {
		obj.focus ();
		var Sel = document.selection.createRange ();	
		SelTxt = Sel.text;
	}else{  
		var start = obj.selectionStart;
        var end = obj.selectionEnd;
        SelTxt = obj.value.substring(start, end);
	}
	return SelTxt;
}

function InsertContent(Content)
{
    var myArea = form.msg;
    if (document.selection) {
      myArea.focus();
      var mySelection =document.selection.createRange();
      mySelection.text = Content;
   }else{
     var myPrefix = myArea.value.substring(0, myArea.selectionStart);
     var mySuffix = myArea.value.substring(myArea.selectionEnd);
     myArea.value = myPrefix + Content + mySuffix;
   }
}

function doMailTo() {
	
	dialogUtil.openDialog({
		src: "SBSO1L.do?action=mailtoForm",
		title: "增加MAILTO",
		width:550,
		height:300,
		callback: function(param){
			if( typeof(param.html)=="string" ) {
				InsertContent(param.html);
				doView();
			}
		}
	});
	
}

function doUpload(){
	
	dialogUtil.openDialog({
		src: "SBSO1L.do?action=uploadForm",
		title: "上傳檔案",
		width:550,
		height:300,
		callback: function(param){
			if( typeof(param.html)=="string" ) {		
				if( form.msg.value.indexOf("附件：")==-1 ) {
					form.msg.value = form.msg.value + "<br>\r\n附件：";
				}
				form.msg.value = form.msg.value + "\r\n<br>\r\n" + param.html;
				doView();
			}
		}
	});
	
}

function doReset() {
	if( form.rowid.value=="")
		form.reset();
	else
		getEditIniData();
}

</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0">

<form name="form" action="" method="post">
<input type="hidden" name="rowid">
<div align="center">


<br>

<table width="960"><tr><td>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td align="left">首頁 > 線上起訴後台管理系統 > 最新消息維護</td>
</tr>
</table>

<br>

<!-- 使用者帳號查詢 -->
<table width="100%" border="1" cellpadding="4" cellspacing="0">
<tr>
	<td class="title">
		<table width="100%">
		<tr>
			<td align="left" class="title">最新消息維護</td>
			<td align="right" class="title">
				<input type="button" name="btn_back" title="回前頁" alt="回前頁" value="回前頁" onclick="doBack();" class="btn2">
				<input type="button" name="btn_submit" title="確定" alt="確定" value="確定" class="btn2">
				<input type="button" name="btn_reset" title="取消" alt="取消" value="取消" class="btn2">
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

<table class="tablesorter-blue" border="0" cellpadding="0" cellspacing="1">

<tbody>

<tr class="even">
	<td width="20%">系統別<font color="red">*</font></td>
	<td colspan="3">
		<input type="checkbox" class="checkbox" name="owners">		
	</td>
</tr>

<tr class="odd">
	<td width="20%">發布日期<font color="red">*</font></td>
	<td colspan="3">
		<input type="text" name="postdt" size="7" maxlength="7" class="txt" value="<%=ud.nowCDate()%>">
	</td>
</tr>

<tr class="even">
	<td width="20%">上架起日<font color="red">*</font></td>
	<td width="30%">
		<input type="text" name="sdt" size="7" maxlength="7" class="txt" value="<%=ud.nowCDate()%>">
	</td>
	<td width="20%">上架迄日<font color="red">*</font></td>
	<td width="30%">
		<input type="text" name="edt" size="7" maxlength="7" class="txt" value="<%=ud.nowCDate()%>">
	</td>
</tr>

<tr class="odd">
	<td width="20%">消息內容<font color="red">*</font></td>
	<td colspan="3">
		<input type='button' value="紅色" onclick="doRed()">
		<input type='button' value="粗體" onclick="doB()">
		<input type='button' value="紅色&粗體" onclick="doRedB()">
		<input type='button' value="換行" onclick="doBr()">
		<input type='button' value="上傳檔案" onclick="doUpload()">
		<input type='button' value="增加MAILTO" onclick="doMailTo()">
		<textarea rows="20" style="width:99%" name="msg"></textarea>
	</td>
</tr>

<tr class="even">
	<td width="20%">即時預覽</td>
	<td colspan="3">
		<span id="view">
		</span>
		&nbsp;
	</td>
</tr>

</tbody>

</table>
<!-- 使用者帳號查詢 -->

</td></tr></table>

</div>
</form>
</body>

</html>
