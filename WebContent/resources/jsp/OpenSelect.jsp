<%@ page language="java" import="java.util.*,com.sertek.sys.*" pageEncoding="MS950"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="Head.jsp"%>
<html>

<head>
<title>資料開窗</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
</head>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/CSS.css" />
<script>

var openType = "window";
var $tablegrid = null;
var $tr = null;
var dynUtil = null;
var formUtil = null;
var renderUtil = null;
var data = null;
var option = null;


function getOpenerData() {
	//取得控制物件
	$tablegrid = $("#context");
	try {
		dynUtil = top.opener.dynUtil;
		formUtil = top.opener.formUtil;
		renderUtil = top.opener.renderUtil;
		openType = "window";
	} catch(e) {
		dynUtil = parent.dynUtil;
		formUtil = parent.formUtil;
		renderUtil = parent.renderUtil;
		openType = "iframe";
	}	
	option = dynUtil.dynOption;
	data = dynUtil.jsonData.data;
}

function setSingleSelectEvent() {
	
	$(":button:not(:button[name=submitbtn])").css("display", "none");
	
	$("#trdata", $tablegrid).click(function(){
		var json = $.parseJSON($(this).attr("json"));	
		
		//沒有指定放的格式，則用欄位決定
		if( option.bindDataFields.length == 0 ) {
			if( option.targetForm == null)
				formUtil.bindData(json);
			else
				formUtil.bindFormData(dynOption.targetForm, json);
		}else{
			for(var i=0;i<option.bindDataFields.length;i++) {
				formUtil.bindObjectData(
						option.bindDataFields[i].obj, 
						dynUtil.tranPattern(option.bindDataFields[i].pattern, json)
					);
			}
		}
		if( openType=="window")
			window.parent.close();
		else
			window.style.display = "none";
	});
}

function setMultipleSelectEvent() {
	
	//
	$("tr[class=head] td:nth-child(1)", $tablegrid).each(function(){
		$("<td align='center'>&nbsp;</td>").insertBefore($(this));
	});
	
	//增加checkbox	
	$("#trdata td:nth-child(1)", $tablegrid).each(function(){
		$("<td align='center'><input type='checkbox'></td>").insertBefore($(this));
	});
	
	$("#trdata", $tablegrid).click(function(){
		
		var type = (typeof $(this).attr("checked"));
		if( type=="undefined") {
			$(this).addClass("tr-4").attr("checked", "checked");
			$("input[type=checkbox]", $(this)).attr("checked", "checked");
		} else {
			$(this).removeClass("tr-4").removeAttr("checked");
			$("input[type=checkbox]", $(this)).removeAttr("checked");
		}
	});	

	$("input[id=__btn1]").click(function(){		
		for(var i=0;i<option.bindDataFields.length;i++) {			
			var value = "";			
			$("input[type=checkbox][checked]", $tablegrid).each(function(){	
				if( $(this).parent().parent().attr("class") != "head" ) {
					var json = $.parseJSON($(this).parent().parent().attr("json"));				
					if( value!="") value += option.splitStr;
					value += dynUtil.tranPattern(option.bindDataFields[i].pattern, json);
				}
			});			
			formUtil.bindObjectData(option.bindDataFields[i].obj, value);
		}
		if( openType=="window")
			window.parent.close();
		else
			window.style.display = "none";
	});
	
	$("input[id=__btn2]").click(function(){
		$("input[type=checkbox]", $tablegrid).attr("checked", "checked");
		$("#trdata", $tablegrid).attr("checked", "checked").addClass("tr-4");
		
	});
	
	$("input[id=__btn3]").click(function(){
		$("input[type=checkbox]", $tablegrid).removeAttr("checked");	
		$("#trdata", $tablegrid).removeAttr("checked").removeClass("tr-4");	
	});
}

function getIniColumnOptions() {
	var options = {
		  head_l: null
		, head_r: null
		, type: null
		, name: null
		, size: 1
		, options: null
		, sqlid: null
		, valuepattern: ''
		, textpattern: ''
		, haveall: false
	};
	return options;
}

function setQueryForm(){
	
	$queryTable = $("#queryTable");
	if( option.queryColumns.length==0 || (typeof option.queryColumns.length)=="undefined") 
		$queryTable.css("display", "none");
	
	for(var i=0;i<option.queryColumns.length;i++) {
		
		var Column = $.extend({}, dynUtil.getIniColumnOptions(), option.queryColumns[i]);
		if( i%2==0 )
			$tr = $("<tr></tr>").appendTo($queryTable);
				
		$tr.append("<td width='15%' class='head-l'>"+Column.head_l+"</td>");
		$td = $("<td width='35%' class='head-r' colspan='"+Column.colspan+"'></td>").appendTo($tr);
		$span = $("<span></span>").appendTo($td);
		
		if( Column.head_r != null ) {
			$td.append(Column.head_r);
		}else{
			if( Column.type=="select") {				
				$newObj = $("<select id='"+Column.name+"' name='"+Column.name+"'></select>").appendTo($span);
			}else{
				$newObj = $("<input type='"+Column.type+"'>").attr("id", Column.name).attr("name", Column.name).attr("size", Column.size).appendTo($span);
			}
			
			if( Column.type=="select" || Column.type=="checkbox" || Column.type=="radio") {
				if( Column.options != null ) {
					renderUtil.renderObject({
						targetObj: $newObj,
						valuepattern: '@{v}',
						textpattern: '@{t}',
						haveall: false,
						data: Column.options
					});
				}			
				if(Column.sqlid != null ) {
					dynUtil.setSelectOption({
						sqlid: Column.sqlid,			
						isClear: true,					
						haveall: Column.haveall,					
						targetObj: $newObj,
						valuepattern: Column.valuepattern,
						textpattern: Column.textpattern
					});
				}	
			}
		}
	}
	
	for (var attrName in option.paraObj) {
		if( $("#"+attrName, $queryTable).length==0 ) {
			var type = (typeof option.paraObj[attrName])
			if( type=="string" || type=="number" )
				$queryTable.append("<input type='hidden' id='"+attrName+"' name='"+attrName+"' value='"+option.paraObj[attrName]+"'>");
			else {
				for(var i=0;i<option.paraObj[attrName].length;i++) {
					$queryTable.append("<input type='hidden' id='"+attrName+"' name='"+attrName+"' value='"+option.paraObj[attrName][i]+"'>");
				}
			}
		}else{
			formUtil.bindObjectData($("#"+attrName, $queryTable), option.paraObj[attrName]);
		}
	}
}

function lockNum()
{
	if (event.keyCode==13) return;
	if ((event.keyCode < 48) || (event.keyCode > 58)) 
	{
		if (event.keyCode!=45)
			event.keyCode = 0;
	}
}

function setEvent() {
	
	if( option.isSelectModule ) {
		$("#trdata", $tablegrid).mouseover(function(){$(this).css("cursor", "hand");});		
		if( !option.isOpenMultiple ) {		
			setSingleSelectEvent();
		}else{		
			setMultipleSelectEvent();
		}		
		
	} else {
		$(":button:not(:button[name=submitbtn])").css("display", "none");
	}	
	
}

function doQuery() {	
	
	helpUtil.showProcessBar();
	option.paraObj = formUtil.Form2QueryString($("#queryForm"));
	
	dynUtil.getData(option, function(jsonData){		
		
		uiTableGrid.render({
			formObj : $("#queryForm"),
			gridObj : $("#context"),
			tableTitle: option.openSelectTitle,
			tableTitleButtonInnerText: "<input type='button' id='__btn2' class='button' value='全選'>&nbsp;<input type='button' class='button' id='__btn3' value='全不選'>&nbsp;<input type='button' id='__btn1' class='button' value='確定'>",
			data: jsonData.data,
			columnMeta: option.resultColumnMeta,
			queryFunction: doQuery,
			pageInfo : jsonData.pageInfo
		});		
		
		setEvent();
		helpUtil.closeProcessBar();
		
		$("*").css("font-size", "10pt");
	});	
}

$(document).ready(function(){
	
	
	$("input[name=submitbtn]").click(function(){
		$("input[name=pageNum]", $("#queryForm")).val("1");
		doQuery();
	});
		
	getOpenerData();	
	setQueryForm();	
	doQuery();
	
	window.focus();
});

</script>

<body>
<center>

<form id="queryForm" name="queryForm">
<table id="queryTable" width="95%" align="center" border="4" bordercolor="#418282">
	<tr> 
	    <td class="title" colspan="4"> 
		    <table width="100%" border="0">
		    <tr> 
			    <td width="58%" class="title">輸入欲查詢條件</td>
				<td width="42%" align="right" class="title"> 
				    <input type="button" name="submitbtn" value="查詢" class="button">
				</td>
			</tr>
		    </table>
		</td>
	</tr>
</table>
</form>


<form id="resultForm" name="resultForm">
<table id="context" width="95%" align="center">
</table>
</form>

</center>
</form>
</body>

</html>