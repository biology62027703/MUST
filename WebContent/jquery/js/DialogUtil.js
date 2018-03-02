/**
 * @description
 * @class Dialog
 * @author chkchk
 * @constructor
 */
function DialogUtil() {
}

DialogUtil.prototype.openOption = null;
DialogUtil.prototype.codeOption = null;
DialogUtil.prototype.orgHelpUtil = null;		//母視窗的 HelpUtil，關閉子視窗時要還給母視窗
DialogUtil.prototype.openDialogOption = null;
DialogUtil.prototype.openDialog2Option = null;
var __openDailogReturnValue__ = {};		//openDailog 的回傳值參數(json格式)
var __openDailog2ReturnValue__ = {};	//openDailog2 的回傳值參數(json格式)
/**
 * 字串清掉空白
 * @param a 
*/
DialogUtil.prototype.trim =function trim(a){  
    return a.replace(/(^\s*)|(\s*$)/g, "");  
};

//檢查id 是否是要顯示的欄位
DialogUtil.prototype.checkShow = function(ColumnArray,id){
	var retVal = false;
	for(var i=0;i<ColumnArray.length;i++){
		
		if ( dialogUtil.trim(ColumnArray[i].toUpperCase())==id.toUpperCase() ){
			retVal = true;
			break;
		}
	}
	return retVal;
		
};

$(document).ready(function(){
	 
	 $("#__myExceptiondialog__").dialog({
		    diaggable:false,
			autoOpen: false,
			height: 250,
			width: 400,
			modal: true,
			position: 'center',
			open:function(){	//開啟Dialog 時，呼叫 iniSelect 初始化
				$(this).dialog({ position: 'center' }); //置中
			},
			close:function(){	//關閉Dialog 時呼叫 
			},
			buttons: {
				close: function() {
					$(this).dialog( "close" );
					
				}
			}
	});
	 
	 $("#__myOpenDialog__").dialog({
			autoOpen: false,
			height: 600,
			width: 800,
			modal: true,
			position: 'center',
			open:function(){	//開啟Dialog 時，要做啥
				$(this).dialog({ position: 'center' }); //置中
				$(this).dialog( { height : dialogUtil.openDialogOption.height} );
				$(this).dialog( {width : dialogUtil.openDialogOption.width} );
				$(this).dialog( {title : dialogUtil.openDialogOption.title} );
				dialogUtil.post(dialogUtil.openDialogOption.src,"__myOpenDialogiFrame__" ,dialogUtil.openDialogOption.jsonData);
				// title :'Dialog'	
			},
			close:function(){	//關閉Dialog 時呼叫
				helpUtil = dialogUtil.orgHelpUtil;
				if (typeof(dialogUtil.openDialogOption.callback)=="function"){
					dialogUtil.openDialogOption.callback(__openDailogReturnValue__);
				}
				$("#__myOpenDialogiFrame__").prop("src","about:blank");
				
			}

	});
	 
	 $("#__myOpenDialog2__").dialog({
			autoOpen: false,
			height: 600,
			width: 800,
			modal: true,
			position: 'center',
			open:function(){	//開啟Dialog 時，要做啥
				$(this).dialog({ position: 'center' }); //置中
				$(this).dialog( { height : dialogUtil.openDialog2Option.height} );
				$(this).dialog( {width : dialogUtil.openDialog2Option.width} );
				$(this).dialog( {title : dialogUtil.openDialog2Option.title} );
				dialogUtil.post(dialogUtil.openDialog2Option.src,"__myOpenDialogiFrame2__" ,dialogUtil.openDialog2Option.jsonData);
				// title :'Dialog'	
			},
			close:function(){	//關閉Dialog 時呼叫
				helpUtil = dialogUtil.orgHelpUtil;
				if (typeof(dialogUtil.openDialog2Option.callback)=="function"){
					dialogUtil.openDialog2Option.callback(__openDailog2ReturnValue__);
				}
				$("#__myOpenDialogiFrame2__").prop("src","about:blank");
			}

	});

	 $("#__mySingledialog__").dialog({
		 diaggable:false,
			autoOpen: false,
			height: 250,
			width: 400,
			modal: true,
			position: 'center',
			
			open:function(){	//開啟Dialog 時，呼叫 iniSelect 初始化
				
				$(this).dialog({ position: 'center' }); //置中
				$(this).dialog( { height : dialogUtil.openOption.height} );
				$(this).dialog( {width : dialogUtil.openOption.width} );
				iniSelect(false);
			},
			close:function(){	//關閉Dialog 時
				if ( $("#__mySingledialog__Xicon").val()!="0"){	//使用者直接按 Dialog X，則呼叫fail
					if (typeof(dialogUtil_jsonParam.fail)=="function"){
						dialogUtil_jsonParam.fail();
					}
				}
				$("#__mySingledialog__Xicon").val("");
			}
	});
	 
	 $("#__myMutildialog__").dialog({
		 diaggable:false,
			autoOpen: false,
			height: 250,
			width: 400,
			modal: true,
			position: 'center',
			open:function(){	//開啟Dialog 時，呼叫 iniSelect 初始化
				$(this).dialog({ position: 'center' }); //置中
				$(this).dialog( { height : dialogUtil.openOption.height} );
				$(this).dialog( {width : dialogUtil.openOption.width} );
				iniSelect(true);
			},
			close:function(){	//關閉Dialog 時
				
			}
	 
	});
	
});


/**
 * @isMutil 是否為多選
 */
function iniSelect(isMutil){	
	
	var option = dialogUtil.openOption;		
	var	jsonData	= option.jsonData;
	var	showColumn	= option.showColumn;
	var filterJson = option.filterColumn;	
	//var showColumnArray = showColumn.split(",");	
	var jsonArray;
	
	var myDefaultSelected = option.defaultSelected;
	
	
	if (typeof(jsonData)=='object'){
		jsonArray = jsonData;
	}else if (typeof(jsonData)=='string'){
		jsonArray=$.parseJSON(jsonData);	
	}
	
	$("#__mySingledialog__select1").empty();
	$("#__myMutildialog__mutilselect1").empty();
	
	for(var i=0;i< jsonArray.length;i++ ){
		var a = jsonArray[i];
	
		var selectValue = JSON.stringify(a);
		
		var selectText = '';
		
		if (dialogUtil.checkFilter(a,filterJson)){
			 
			for(var j=0;j<showColumn.length;j++) {
				if( typeof(a[showColumn[j]])=="string" )
					selectText += a[showColumn[j]] +"　";
			}
				
			//1.增加一個 option
	  		$option = $("<option></option>")
	  					.html(selectText)
	  					.data("json", a)
	  					.val(selectValue);

	  		
	  		//2.處理 option 是否要預設選取
  			for ( var id in  myDefaultSelected){	//找出 所有 id	
  				var find = false;
  				
				if (myDefaultSelected.hasOwnProperty(id)){
						if( typeof(myDefaultSelected[id])=="string" ){
							if (myDefaultSelected[id]==a[id]){
								$option.prop("selected",true);
								find = true;
							}
						}else{		//陣列
							
							for(var j=0;j<myDefaultSelected[id].length;j++){
								if (myDefaultSelected[id][j]==a[id]){
									$option.prop("selected",true);
									find = true;
				  					break;
								}
							}
						}
					
				}
				if (find==true) break;
			}

	  		//3.將 option 加到 select1 物件
  			if (isMutil){
  				$option.appendTo($("#__myMutildialog__mutilselect1"));
  			}else{
  				$option.appendTo($("#__mySingledialog__select1"));
  			}  		
		}
	}

}

var dialogUtil_jsonParam;

//關閉單選視窗
DialogUtil.prototype.close = function(result){
	
	var obj = $("option:selected", $(result)).data("json");

	if (obj!=null){
		
		if( typeof(obj)=="object" ) {
			if (typeof(dialogUtil_jsonParam.callback) =='function'){
				dialogUtil_jsonParam.callback(obj);
			}
		}else if( typeof(obj)=="string" ) {
			var jsonobj = $.parseJSON(obj);
			if (typeof(dialogUtil_jsonParam.callback) =='function'){
				dialogUtil_jsonParam.callback(jsonobj);
			}
		}else{
			//使用者沒有選取資料，回呼 fail 函式
			if (typeof(dialogUtil_jsonParam.fail)=="function"){
				jsonParam.fail();
			}
		}
		$("#__mySingledialog__").dialog("close");
	}
	
};

//關閉多選視窗
DialogUtil.prototype.closeMutil = function(result){

	var obj = $.parseJSON("[]");
	$("select[name=__myMutildialog__mutilselect1] option:selected").each(
		function(){
			obj.push($(this).data("json"));
		
		}
	);
	
	if( typeof(obj)=="object" ) {
		if (typeof(dialogUtil_jsonParam.callback) =='function'){
			dialogUtil_jsonParam.callback(obj);
			
		}
	}else if( typeof(obj)=="string" ) {
		var jsonobj = $.parseJSON(obj);
		if (typeof(dialogUtil_jsonParam.callback) =='function'){
			dialogUtil_jsonParam.callback(jsonobj);
		}
	}else{
		//使用者沒有選取資料，回呼 fail 函式
		if (typeof(jsonParam.fail)=="function"){
			jsonParam.fail();
		}
	}
	
	$("#__myMutildialog__").dialog("close");
};

DialogUtil.prototype.openListDialog = function(jsonParam){
	
	//初始化 option
	dialogUtil.openOption = $.extend({}, dialogUtil.getOpenListOption(), jsonParam);
	
	dialogUtil_jsonParam = jsonParam;
	
	if (dialogUtil.openOption.multiSelect){
		$("#__myMutildialog__").dialog("open");
	}else{
		$("#__mySingledialog__").dialog("open");
	}	

};

/**
 * 開啟一個 Dialog視窗
 * @param jsonParam
 */
DialogUtil.prototype.openDialog = function(jsonParam){
	//初始化 option
	dialogUtil.openDialogOption = $.extend({}, dialogUtil.getOpenDialogOption(), jsonParam);	
	//dialogUtil.orgHelpUtil = helpUtil;	//將 helpUtil 指給 dialog視窗	
	dialogUtil.setReturnValue({});	//開啟視窗前先清空回傳參數	
	$("#__myOpenDialog__").dialog("open");		//開啟 dialog

};

/**
 * 開啟一個 Dialog2視窗 (請盡量用openDialog ，勿使用本函式 )
 * @param jsonParam
 */
DialogUtil.prototype.openDialog2 = function(jsonParam){
	//初始化 option
	dialogUtil.openDialog2Option = $.extend({}, dialogUtil.getOpenDialog2Option(), jsonParam);	
	//dialogUtil.orgHelpUtil = helpUtil;	//將 helpUtil 指給 dialog視窗	
	dialogUtil.setReturnValue2({});	//開啟視窗前先清空回傳參數	
	$("#__myOpenDialog2__").dialog("open");		//開啟 dialog

};

DialogUtil.prototype.post = function(path, target,params) {
    method = "post"; // Set method to post by default if not specified.
    //alert(params);
    // The rest of this code assufmes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);
    form.setAttribute("target", target);
    if (typeof(params)=="object"){
    	for(var key in params) {
	        if(params.hasOwnProperty(key)) {
	            var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
	            hiddenField.setAttribute("name", key);
	            hiddenField.setAttribute("value", params[key]);
	
	            form.appendChild(hiddenField);
	         }
	    }
    }else if (typeof(params)=="string"){
    	var list = params.split("&");
    	for(var i=0;i<list.length;i++){
    		var akeyValue = list[i];
    		var keyValue = akeyValue.split("=");
    		if (keyValue.length==2){
    			var hiddenField = document.createElement("input");
 	            hiddenField.setAttribute("type", "hidden");
 	            hiddenField.setAttribute("name", keyValue[0]);
 	            hiddenField.setAttribute("value", keyValue[1]);
 	            form.appendChild(hiddenField);
    		}
    	}
    	
    }
    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form);
    form = null;
};

/**
*開啟 ListDialog 
*
*
* @jsonData: json物件,
* @showColumn: 要顯示的欄位名稱，多欄位時，使用 ,分隔
* @filterColumn: 要過濾的欄位，範 例{"SYS","V"}表示只找出 SYS=V 的欄位資料
* @windth:
* @height:
* @param fail				//使用者沒有選擇時，後續要做啥麼處理
* @defaultSelected : 	那些資料要預先被選
*
*/
DialogUtil.prototype.getOpenListOption = function() { 
	var options = {
		jsonData : null   						
		, showColumn : []
		, defaultSelected : {}
		, filterColumn : {}
	    , multiSelect : false
		, width : 400						
		, height : 250	
		, fail :null
		, callback: function (jsonResult) {}
	};	
	return options;
};

/**
*開啟 Dialog 
* @jsonData: json物件,
* @src: 要開哪一隻程式
* @title:  Dialog 的抬頭
* @windth:
* @height:
* @callback fail				//使用者沒有選擇時，後續要做啥麼處理
* @
*/
DialogUtil.prototype.getOpenDialogOption = function() { 
	var options = {
		jsonData : null   						
		, src : ''		//網址
		, width : 700						
		, height : 600	
		//, fail :null
		, title :'Dialog'
		, returnValue : null		//回傳值
		, callback: function (__openDailogReturnValue__) {}
	};	
	return options;
};

DialogUtil.prototype.getOpenDialog2Option = function() { 
	var options = {
		jsonData : null   						
		, src : ''		//網址
		, width : 700						
		, height : 600	
		//, fail :null
		, title :'Dialog'
		, returnValue : null		//回傳值
		, callback: function (__openDailog2ReturnValue__) {}
	};	
	return options;
};

DialogUtil.prototype.getOpenDialog2Option = function() { 
	var options = {
		jsonData : null   						
		, src : ''		//網址
		, width : 700						
		, height : 600	
		//, fail :null
		, title :'Dialog'
		, returnValue : null		//回傳值
		, callback: function (__openDailog2ReturnValue__) {}
	};	
	return options;
};


/**
 * 
 * @param jsonParam json物件，參數如下:
 * 
 * @param searchValue 		//欲找尋的關鍵字
 * @param jsonData 			//json 物件
 * @param searchColumn 	//針對那幾個欄位去找尋
 * @param callback        	//找到值後，要做啥麼事
 * @param fail				//使用用沒有選擇時，要做啥麼
 * @param filterColumn       // 要過濾的欄位，範 例{"SYS","V"}表示只找出 SYS=V 的欄位資料
 * @param showErr				//找不到值的時候，是否要顯示錯誤，
 */
DialogUtil.prototype.getCode = function(jsonParam){
	
	dialogUtil.codeOption = $.extend({}, dialogUtil.getCodeOption(), jsonParam);
	var option = dialogUtil.codeOption;		
	var	jsonData	= option.jsonData;
	var searchValue = option.searchValue;
	var	searchColumn	= option.searchColumn;
	var filterColumn = option.filterColumn;
	var showErr = option.showErr;
	var result;
	var jsonArray ;
	
	
	if (typeof(jsonData)=='object'){
		jsonArray = jsonData;
	}else if (typeof(jsonData)=='string'){
		jsonArray = $.parseJSON(jsonData);
	}	
	
	var find = false;
	for( var j=0;j< jsonArray.length;j++){
		var a = jsonArray[j];
		if (dialogUtil.checkFilter(a,filterColumn)){
			for(var i=0;i<searchColumn.length;i++){	
				if (  dialogUtil.trimInteger(a[searchColumn[i]]) == searchValue ){
					result = a;
					find = true;
					break;
				}
			} 
		}
		if (find==true){
			break;
		}
	}
	
	if (typeof(result)=='object'){
		jsonParam.callback(result);
	}else{
		if (find==false && showErr) alert("無對應的資料");//helpUtil.showInfoBar('INFO','無對應的資料');
		if (typeof(jsonParam.fail)=='function'){
			jsonParam.fail();
		}
	}
};

/*
 * 轉成數字
 */
DialogUtil.prototype.trimInteger= function( str ){
	var retVal = str;
	try{
		if (isNaN(str)==false){
			retVal = parseInt(str,10);
		}
	}catch(e){
	}
	return retVal;
};
/**
 * 設定 openDailog 關閉後，回傳的參數
 * 可於 callback(param) 收到
 */
DialogUtil.prototype.setReturnValue = function(param){
	__openDailogReturnValue__ = param;
};

/**
 * 設定 openDailog2 關閉後，回傳的參數
 * 可於 callback(param) 收到
 */
DialogUtil.prototype.setReturnValue2 = function(param){
	__openDailog2ReturnValue__ = param;
};

/**
 * 關閉 openDailog 視窗
 */
DialogUtil.prototype.closeDialog = function(){
	$("#__myOpenDialog__").dialog("close");
};


/**
 * 關閉 openDailog2 視窗
 */
DialogUtil.prototype.closeDialog2 = function(){
	$("#__myOpenDialog2__").dialog("close");
};


/**
*檢查是否要過濾
*@param a 過濾的值
*@param filterColumn 
*@returns 放行可過則回傳 true ,不放行   則回傳 false
*/
DialogUtil.prototype.checkFilter = function(a,filterColumn){
	var retVal = false;
	//informations
	var keyCount = 0;		// filterJson 這個 json 物件有多少 key 值 ，如果是 0 ，表示使用者沒有傳過來 ，或為空值
	
	for (var id in filterColumn){
			if (filterColumn.hasOwnProperty(id)){
				keyCount ++;
			}
			for(var ids in a){
				if (id==ids){
					if (filterColumn[id]==a[ids]){
						retVal = true;
						break;
					}
				}
			}
			if (retVal==true){
				break;
			}	
		}	
	if (keyCount==0){
		
		retVal = true;
	}
	return retVal;
};

/**
 * 開啟指令碼錯誤回報的視窗(showModalDialog) 
 * @param {String} report 回報的錯誤訊息
 * @type void
 */
DialogUtil.prototype.openExceptionDialog=function(report){
	try{
		$("#__myExceptiondialogTextArea__").html(report);
		$("#__myExceptiondialog__").dialog("open").dialog("moveToTop");
	}catch(ex){
		alert(ex);
	}
};

DialogUtil.prototype.getCodeOption = function() { 
	var options = {
		searchValue : null,
		jsonData : null ,
		 showColumn :'',
		callback : null,
		filterColumn : {},
		searchColumn:[],
		showErr:false,
		fail : null
	};	
	return options;
};

//
document.open();
document.writeln("<div id='__mySingledialog__' title='單選對話方框' style='display:none'>");
document.writeln("	<select size='10' name='__mySingledialog__select1' id='__mySingledialog__select1' onClick='$(\"#__mySingledialog__Xicon\").val(\"0\");dialogUtil.close(this)' style='width:100%;height:95%;font-size:11pt'></select>");
document.writeln("	<input type='hidden' id='__mySingledialog__Xicon'>");
document.writeln("</div>");
document.close();

document.open();
document.writeln("<div id='__myMutildialog__' title='多選對話方框' style='display:none'>");
document.writeln("	<select size='7'  multiple name='__myMutildialog__mutilselect1' id='__myMutildialog__mutilselect1'  style='width:100%;height:95%;font-size:11pt'></select>");
document.writeln("	<BR /><label>1.按住CTRL鍵不放同時配合滑鼠左鍵點選即可非連續複選</label>");
document.writeln("	<BR /><label>2.按住SHIFT鍵不放同時配合滑鼠左鍵點選即可連續複選</label>");
document.writeln("	<BR /><a href='#' class='button style1 r small' onClick='dialogUtil.closeMutil(this)'>確定</a>");
document.writeln("</div>");
document.close();

document.open();
document.writeln("<div id='__myExceptiondialog__' title='錯誤視窗' style='display:none'>");
document.writeln("<span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span>");
document.writeln("	<div id='__myExceptiondialogTextArea__' ></div>");
document.writeln("</div>");
document.close();

document.open();
document.writeln("<div id='__myOpenDialog__' style='margin: 0px;padding:0px' title='Dialog' style='display:none'>");
document.writeln("	<iframe src='' id='__myOpenDialogiFrame__' name='__myOpenDialogiFrame__' width='100%' height='99%' frameborder='0'></iframe>");
document.writeln("</div>");
document.close();

document.open();
document.writeln("<div id='__myOpenDialog2__' style='margin: 0px;padding:0px' title='Dialog' style='display:none'>");
document.writeln("	<iframe src='' id='__myOpenDialogiFrame2__' name='__myOpenDialogiFrame2__' width='100%' height='99%' frameborder='0'></iframe>");
document.writeln("</div>");
document.close();

//globe object
var dialogUtil = new DialogUtil();
