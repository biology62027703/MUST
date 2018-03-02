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
DialogUtil.prototype.orgHelpUtil = null;		//�������� HelpUtil�A�����l�����ɭn�ٵ�������
DialogUtil.prototype.openDialogOption = null;
DialogUtil.prototype.openDialog2Option = null;
var __openDailogReturnValue__ = {};		//openDailog ���^�ǭȰѼ�(json�榡)
var __openDailog2ReturnValue__ = {};	//openDailog2 ���^�ǭȰѼ�(json�榡)
/**
 * �r��M���ť�
 * @param a 
*/
DialogUtil.prototype.trim =function trim(a){  
    return a.replace(/(^\s*)|(\s*$)/g, "");  
};

//�ˬdid �O�_�O�n��ܪ����
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
			open:function(){	//�}��Dialog �ɡA�I�s iniSelect ��l��
				$(this).dialog({ position: 'center' }); //�m��
			},
			close:function(){	//����Dialog �ɩI�s 
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
			open:function(){	//�}��Dialog �ɡA�n��ԣ
				$(this).dialog({ position: 'center' }); //�m��
				$(this).dialog( { height : dialogUtil.openDialogOption.height} );
				$(this).dialog( {width : dialogUtil.openDialogOption.width} );
				$(this).dialog( {title : dialogUtil.openDialogOption.title} );
				dialogUtil.post(dialogUtil.openDialogOption.src,"__myOpenDialogiFrame__" ,dialogUtil.openDialogOption.jsonData);
				// title :'Dialog'	
			},
			close:function(){	//����Dialog �ɩI�s
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
			open:function(){	//�}��Dialog �ɡA�n��ԣ
				$(this).dialog({ position: 'center' }); //�m��
				$(this).dialog( { height : dialogUtil.openDialog2Option.height} );
				$(this).dialog( {width : dialogUtil.openDialog2Option.width} );
				$(this).dialog( {title : dialogUtil.openDialog2Option.title} );
				dialogUtil.post(dialogUtil.openDialog2Option.src,"__myOpenDialogiFrame2__" ,dialogUtil.openDialog2Option.jsonData);
				// title :'Dialog'	
			},
			close:function(){	//����Dialog �ɩI�s
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
			
			open:function(){	//�}��Dialog �ɡA�I�s iniSelect ��l��
				
				$(this).dialog({ position: 'center' }); //�m��
				$(this).dialog( { height : dialogUtil.openOption.height} );
				$(this).dialog( {width : dialogUtil.openOption.width} );
				iniSelect(false);
			},
			close:function(){	//����Dialog ��
				if ( $("#__mySingledialog__Xicon").val()!="0"){	//�ϥΪ̪����� Dialog X�A�h�I�sfail
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
			open:function(){	//�}��Dialog �ɡA�I�s iniSelect ��l��
				$(this).dialog({ position: 'center' }); //�m��
				$(this).dialog( { height : dialogUtil.openOption.height} );
				$(this).dialog( {width : dialogUtil.openOption.width} );
				iniSelect(true);
			},
			close:function(){	//����Dialog ��
				
			}
	 
	});
	
});


/**
 * @isMutil �O�_���h��
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
					selectText += a[showColumn[j]] +"�@";
			}
				
			//1.�W�[�@�� option
	  		$option = $("<option></option>")
	  					.html(selectText)
	  					.data("json", a)
	  					.val(selectValue);

	  		
	  		//2.�B�z option �O�_�n�w�]���
  			for ( var id in  myDefaultSelected){	//��X �Ҧ� id	
  				var find = false;
  				
				if (myDefaultSelected.hasOwnProperty(id)){
						if( typeof(myDefaultSelected[id])=="string" ){
							if (myDefaultSelected[id]==a[id]){
								$option.prop("selected",true);
								find = true;
							}
						}else{		//�}�C
							
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

	  		//3.�N option �[�� select1 ����
  			if (isMutil){
  				$option.appendTo($("#__myMutildialog__mutilselect1"));
  			}else{
  				$option.appendTo($("#__mySingledialog__select1"));
  			}  		
		}
	}

}

var dialogUtil_jsonParam;

//����������
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
			//�ϥΪ̨S�������ơA�^�I fail �禡
			if (typeof(dialogUtil_jsonParam.fail)=="function"){
				jsonParam.fail();
			}
		}
		$("#__mySingledialog__").dialog("close");
	}
	
};

//�����h�����
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
		//�ϥΪ̨S�������ơA�^�I fail �禡
		if (typeof(jsonParam.fail)=="function"){
			jsonParam.fail();
		}
	}
	
	$("#__myMutildialog__").dialog("close");
};

DialogUtil.prototype.openListDialog = function(jsonParam){
	
	//��l�� option
	dialogUtil.openOption = $.extend({}, dialogUtil.getOpenListOption(), jsonParam);
	
	dialogUtil_jsonParam = jsonParam;
	
	if (dialogUtil.openOption.multiSelect){
		$("#__myMutildialog__").dialog("open");
	}else{
		$("#__mySingledialog__").dialog("open");
	}	

};

/**
 * �}�Ҥ@�� Dialog����
 * @param jsonParam
 */
DialogUtil.prototype.openDialog = function(jsonParam){
	//��l�� option
	dialogUtil.openDialogOption = $.extend({}, dialogUtil.getOpenDialogOption(), jsonParam);	
	//dialogUtil.orgHelpUtil = helpUtil;	//�N helpUtil ���� dialog����	
	dialogUtil.setReturnValue({});	//�}�ҵ����e���M�Ŧ^�ǰѼ�	
	$("#__myOpenDialog__").dialog("open");		//�}�� dialog

};

/**
 * �}�Ҥ@�� Dialog2���� (�кɶq��openDialog �A�ŨϥΥ��禡 )
 * @param jsonParam
 */
DialogUtil.prototype.openDialog2 = function(jsonParam){
	//��l�� option
	dialogUtil.openDialog2Option = $.extend({}, dialogUtil.getOpenDialog2Option(), jsonParam);	
	//dialogUtil.orgHelpUtil = helpUtil;	//�N helpUtil ���� dialog����	
	dialogUtil.setReturnValue2({});	//�}�ҵ����e���M�Ŧ^�ǰѼ�	
	$("#__myOpenDialog2__").dialog("open");		//�}�� dialog

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
*�}�� ListDialog 
*
*
* @jsonData: json����,
* @showColumn: �n��ܪ����W�١A�h���ɡA�ϥ� ,���j
* @filterColumn: �n�L�o�����A�d ��{"SYS","V"}��ܥu��X SYS=V �������
* @windth:
* @height:
* @param fail				//�ϥΪ̨S����ܮɡA����n��ԣ��B�z
* @defaultSelected : 	���Ǹ�ƭn�w���Q��
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
*�}�� Dialog 
* @jsonData: json����,
* @src: �n�}���@���{��
* @title:  Dialog �����Y
* @windth:
* @height:
* @callback fail				//�ϥΪ̨S����ܮɡA����n��ԣ��B�z
* @
*/
DialogUtil.prototype.getOpenDialogOption = function() { 
	var options = {
		jsonData : null   						
		, src : ''		//���}
		, width : 700						
		, height : 600	
		//, fail :null
		, title :'Dialog'
		, returnValue : null		//�^�ǭ�
		, callback: function (__openDailogReturnValue__) {}
	};	
	return options;
};

DialogUtil.prototype.getOpenDialog2Option = function() { 
	var options = {
		jsonData : null   						
		, src : ''		//���}
		, width : 700						
		, height : 600	
		//, fail :null
		, title :'Dialog'
		, returnValue : null		//�^�ǭ�
		, callback: function (__openDailog2ReturnValue__) {}
	};	
	return options;
};

DialogUtil.prototype.getOpenDialog2Option = function() { 
	var options = {
		jsonData : null   						
		, src : ''		//���}
		, width : 700						
		, height : 600	
		//, fail :null
		, title :'Dialog'
		, returnValue : null		//�^�ǭ�
		, callback: function (__openDailog2ReturnValue__) {}
	};	
	return options;
};


/**
 * 
 * @param jsonParam json����A�ѼƦp�U:
 * 
 * @param searchValue 		//����M������r
 * @param jsonData 			//json ����
 * @param searchColumn 	//�w�墨�X�����h��M
 * @param callback        	//���ȫ�A�n��ԣ���
 * @param fail				//�ϥΥΨS����ܮɡA�n��ԣ��
 * @param filterColumn       // �n�L�o�����A�d ��{"SYS","V"}��ܥu��X SYS=V �������
 * @param showErr				//�䤣��Ȫ��ɭԡA�O�_�n��ܿ��~�A
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
		if (find==false && showErr) alert("�L���������");//helpUtil.showInfoBar('INFO','�L���������');
		if (typeof(jsonParam.fail)=='function'){
			jsonParam.fail();
		}
	}
};

/*
 * �ন�Ʀr
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
 * �]�w openDailog ������A�^�Ǫ��Ѽ�
 * �i�� callback(param) ����
 */
DialogUtil.prototype.setReturnValue = function(param){
	__openDailogReturnValue__ = param;
};

/**
 * �]�w openDailog2 ������A�^�Ǫ��Ѽ�
 * �i�� callback(param) ����
 */
DialogUtil.prototype.setReturnValue2 = function(param){
	__openDailog2ReturnValue__ = param;
};

/**
 * ���� openDailog ����
 */
DialogUtil.prototype.closeDialog = function(){
	$("#__myOpenDialog__").dialog("close");
};


/**
 * ���� openDailog2 ����
 */
DialogUtil.prototype.closeDialog2 = function(){
	$("#__myOpenDialog2__").dialog("close");
};


/**
*�ˬd�O�_�n�L�o
*@param a �L�o����
*@param filterColumn 
*@returns ���i�L�h�^�� true ,�����   �h�^�� false
*/
DialogUtil.prototype.checkFilter = function(a,filterColumn){
	var retVal = false;
	//informations
	var keyCount = 0;		// filterJson �o�� json ���󦳦h�� key �� �A�p�G�O 0 �A��ܨϥΪ̨S���ǹL�� �A�ά��ŭ�
	
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
 * �}�ҫ��O�X���~�^��������(showModalDialog) 
 * @param {String} report �^�������~�T��
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
document.writeln("<div id='__mySingledialog__' title='����ܤ��' style='display:none'>");
document.writeln("	<select size='10' name='__mySingledialog__select1' id='__mySingledialog__select1' onClick='$(\"#__mySingledialog__Xicon\").val(\"0\");dialogUtil.close(this)' style='width:100%;height:95%;font-size:11pt'></select>");
document.writeln("	<input type='hidden' id='__mySingledialog__Xicon'>");
document.writeln("</div>");
document.close();

document.open();
document.writeln("<div id='__myMutildialog__' title='�h���ܤ��' style='display:none'>");
document.writeln("	<select size='7'  multiple name='__myMutildialog__mutilselect1' id='__myMutildialog__mutilselect1'  style='width:100%;height:95%;font-size:11pt'></select>");
document.writeln("	<BR /><label>1.����CTRL�䤣��P�ɰt�X�ƹ������I��Y�i�D�s��ƿ�</label>");
document.writeln("	<BR /><label>2.����SHIFT�䤣��P�ɰt�X�ƹ������I��Y�i�s��ƿ�</label>");
document.writeln("	<BR /><a href='#' class='button style1 r small' onClick='dialogUtil.closeMutil(this)'>�T�w</a>");
document.writeln("</div>");
document.close();

document.open();
document.writeln("<div id='__myExceptiondialog__' title='���~����' style='display:none'>");
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
