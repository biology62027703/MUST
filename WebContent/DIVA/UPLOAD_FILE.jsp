<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../utility/PAGEINIT.jsp"%>
<%

%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=8,9,10,edge" />
<link rel="stylesheet" href="../css/CSS.css" type="text/css">
<%@include file="../utility/JQUERY_OLD.jsp"%>
<script type="text/javascript">
	var fileSize = 0; //檔案大小
    var SizeLimit = 50*1024*1024;  //上傳上限，單位:byte
	function iniForm()
	{
		//top.closeProcessBar();

		var msg = "<%=check.checkNull(request.getAttribute("errors"), "") %>";
		if(msg != ""){
			alert(msg);
		}
		

		if("upload" == method && "" == msg){
			if( form.s_ftype.value=="H")
				parent.window.opener.parent.frames[1].frames[0].doRefresh();
			else
				parent.window.opener.parent.frames[1].frames[0].doQueryC60_ATT("");
			top.close();
		}
	}
	
	function doUpload()
	{	

		if("undefined" == "" + parent.window.opener.parent.frames[1].frames[0].form.isUpload){
			top.close();
			return;	
		}
		
		if(form.s_file.value.toUpperCase().indexOf(".PDF")==-1 && form.s_file.value.toUpperCase().indexOf(".DOC")==-1 && form.s_file.value.toUpperCase().indexOf(".DOCX")==-1 && form.s_file.value.toUpperCase().indexOf(".XLS")==-1 && form.s_file.value.toUpperCase().indexOf(".XLSX")==-1 && form.s_file.value.toUpperCase().indexOf(".JPG")==-1) {
			//alert("不可上傳EXE執行檔!");
			alert("僅接受PDF,DOC,DOCX,XLS,XLSX,JPG檔案。");
			return false;
		}
	
		if("" == form.s_file.value){
			alert("請選擇上傳檔案!");
			return false;
		}
		
		if(!checkChinese(form.s_file.value)){
			alert("上傳之附件請以英數字命名!");
			return false;
		}
			
		
		
		 //檢查檔案大小    
		var f = document.getElementById("s_file");  
		if(navigator.userAgent.indexOf("MSIE")>-1){
            fileSize = f.fileSize;
        }else{
        	fileSize = f.files.item(0).size;
        }
		if (fileSize > SizeLimit) {
		    alert("您所選擇的檔案大小為 " + (fileSize / 1024 / 1024).toPrecision(4) + " MB\n已超過上傳上限 " + (SizeLimit / 1024 / 1024).toPrecision(2) + " MB\n不允許上傳！");
		    return false;
		}
	    
	
		form.method.value = "upload";
		form.action = "FSO1A03.do?action=uploadForm";
		form.target = "_self";
		form.submit();
		
		$("input", $(form)).attr("disabled", true);
		$("td[id=title]").html("上傳檔案 - 檔案上傳中，請稍候");
	}
    
    
    function checkChinese(filename) {
    	var result = true;
        for(var i = 0; i < filename.length; i++) {
            if(filename.charCodeAt(i) > 0x4E00 && filename.charCodeAt(i) < 0x9FA5) {
                result = false;
                break;
           }
        }
        return result;
   }

	
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" onload="iniForm()">
<form name="form" action="" target="" method="post" enctype="multipart/form-data" onsubmit="return false">

<div align="center">

<br>
<table width="480"><tr><td>

<!-- 上傳檔案 -->
<table width="100%" border="1" cellpadding="4" cellspacing="0">
<tr>
	<td class="title">
		<table width="100%">
		<tr>
			<td class="title" id="title">上傳檔案</td>
			<td align="right" class="title">
				<input type="button" name="btn_upload" title="確定" alt="確定" value="確定" onClick="doUpload();" class="btn2">
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

<table id="tablesorter" class="tablesorter-blue" border="0" cellpadding="0" cellspacing="1">

<tbody>

<tr class="odd">
	<td>檔案名稱</td>
	<td>
		 <input type="file" id="s_file" name="s_file" value="" size="40" class="txt">
	</td>
</tr>

</tbody>

</table>
<!-- 上傳檔案 -->
<font color="red">
1.僅接受PDF,DOC,DOCX,XLS,XLSX,JPG檔案。<br>
2.單一檔案限制檔案50MB以下。
</font>
</td></tr></table>

</div>
</form>
</body>

</html>