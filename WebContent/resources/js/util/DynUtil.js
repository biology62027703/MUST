
function DynUtil() {
		
}

DynUtil.prototype.dynOption = null;
DynUtil.prototype.jsonData = {};

DynUtil.prototype.getWebRoot = function() {
	web = window.location.pathname;
	if( web.indexOf("/") != 0 ) web = "/"+web;
	web = web.substring(0, web.indexOf("/", 1)+1);	
	return web;
};

DynUtil.prototype.getData = function (dynOption, onSuccess) {

	url = (dynOption.url==null ? dynUtil.getWebRoot() + "DynController.do?action=getJSonData" : dynOption.url);
	//url = (dynOption.url==null ? dynUtil.getWebRoot() + "resources/jsp/DYNController.jsp?action=getJSonData" : dynOption.url);
	
	if( dynOption.dataset==null ) {
		formUtil.submitTo({
			url: url,
			formMethod: dynOption.formMethod,
			dataObj: dynOption.paraObj,
			async: dynOption.async,
			onError: function(jsonData) {
		    },
		    onSuccess: function(jsonData) {
		    	onSuccess(jsonData);
		    }
		});
	}else{
		var jsonData = {data: dynOption.dataset};
		onSuccess(jsonData);
	}
};

DynUtil.prototype.getDynOption = function() { 
	var dynOptions = {
		  targetForm: null			//�����w�A�u�|�B�z�o��form�U���F��
		, paraObj : {}				//�n�ǵ���ݪ��Ѽ�		
		, sqlid : 'UTIL.doSqlSelect'//�n���檺SQL��ID
		, sql : ''					//�i�z�LUtil.doSqlSelect����SQL
		, formMethod : 'POST'		//��ƶǻ��覡
		, async: false				//�P�B�D�P�B

		//�ݩ� setSelectOption �b�Ϊ��Ѽ�
		, targetObj : null			//���w���@��select����
		, isClear: true				//�O�_�M����l�����
		, haveall : false			//�O�_�󬰴�0�W�[�u�����v�ﶵ
		, valuepattern: '@{VALUE}'	//value��ܪ��˪O
		, textpattern: '@{TEXT}'	//text��ܪ��˪O
		, attrpattern: ""
		, defaultValue: null		//�w�]�ȬO����
		
		//�ݩ� bindDataByQuery �b�Ϊ��Ѽ�
		, toLowerCase: false
		, targetObjs : []			//�q��ݨ���ƫ�A���w�������A�|������data[i]�P�W�٪����
		, bindDataFields : []		//�榡��{obj:$obj, pattern:"pattern"}�A�|�Nobj��value�]�� pattern�ഫ�᪺�r��
		
		//�ݩ�}���ɩҨϥΪ��Ѽ�
		, isSelectModule: true		//�դ��O��ܼҦ�
		, isOpenMultiple: false		//�դ��O�h��Ҧ�
		, openWidth: screen.availWidth	//�}�ҵ������e��(�w�]�e���e��)
		, openHeight: screen.availHeight//�}�ҵ���������(�w�]�e������)
		, resultColumnMeta: []		//��ܸ�ƪ����e(�w�q�覡���PUiTableGrid�̪����w�q)
		, queryColumns : []			//�}�����d�߱������(�w�q���e�� getIniColumnOptions �̪����)
		, queryPageSize: 0			//�D�s�h�|�N�d�ߵ��G����
		, queryPageNum: 0
		, openSelectTitle: "�п�ܸ��"	//�}����TITLE
		, openSelectDataTxt: null		//�H�r�����檺�}���r��
		, splitStr: ','				//�h��a�^�ɤw�ƻ�ָ��Ϲj
			
		//���²�檺�}���h��γ��Ҩϥ�
		, texttarget: null			//�}�����Φh��text��r�]�w���s���m
		, valuetarget: null			//�}�����Φh��value��r�]�w���s���m
		, url: null					//�ѭ���url���o��Ʀ^��( �h�Ťj�� sqlid )
		, dataset: null				//�ѫe�ݫ��w����ƶ�( �h�Ťj�� url )
		, filter: null
	};
	
	return dynOptions;
};

//�}������ܪ��d�߰ȼC
DynUtil.prototype.getIniColumnOptions = function() { 
	var options = {
			  head_l: null			//�r�ꤺ�e
			, head_r: null			//�r�ꤺ�e ( �h�Ťj�� name���w�q)
			
			, type: null			//head_r ���������C
			, name: null
			, size: 1
			, options: null			//�A�Ω� select checkbox radio �����e (�榡�� {v:"v", t:"t"}) �����R�A��JSON���
			
			, sqlid: null			//�A�Ω� select checkbox radio �����e  �����R�A��JSON���
			, valuepattern: ''		//���P getDynOption.valuepattern
			, textpattern: ''		//���P getDynOption.textpattern
			, haveall: false		//���P getDynOption.haveall
			, allText: "����"
		};
	return options;
}

DynUtil.prototype.setSelectOption = function(options) { 

	var dynOption = $.extend({}, dynUtil.getDynOption(), options);
	
	if( dynOption.targetObj != null && dynOption.sqlid != "") {

		if( (typeof dynOption.paraObj)=="object" ) {
			dynOption.paraObj["sqlid"] = dynOption.sqlid;
			if( dynOption.paraObj["sql"]==null )
				dynOption.paraObj["sql"] = encodeURIComponent(dynOption.sql);
		}else{
			if( dynOption.paraObj != "" ) dynOption.paraObj+="&";
			dynOption.paraObj += "sqlid="+dynOption.sqlid;
			if( dynOption.paraObj != "" ) dynOption.paraObj+="&";
			dynOption.paraObj += "sql="+encodeURIComponent(dynOption.sql);
		}
		
		dynUtil.getData(dynOption, function(jsonData){
			renderUtil.renderObject({
				targetObj: dynOption.targetObj,
				valuepattern: dynOption.valuepattern,
				textpattern: dynOption.textpattern,
				attrpattern: dynOption.attrpattern,
				data: jsonData.data,
				haveall: dynOption.haveall,
				defaultValue: dynOption.defaultValue,
				allText: dynOption.allText,
				filter: dynOption.filter
			});
		});
	}
};

DynUtil.prototype.bindDataByQuery = function(options) { 

	var dynOption = $.extend({}, dynUtil.getDynOption(), options);

	if(dynOption.sqlid != "") {
		
		var selObj = dynOption.targetObj;
		dynOption.paraObj["sqlid"] = dynOption.sqlid;
		if( dynOption.paraObj["sql"]==null )
			dynOption.paraObj["sql"] = encodeURIComponent(dynOption.sql);
		
		dynUtil.getData(dynOption, function(jsonData){			
			if( jsonData.data.length>0 ) {
				//�O�_�H�p�g�B�z
				if( dynOption.toLowerCase)		
					jsonData.data[0] = formUtil.convertToLowCaseKey(jsonData.data[0]);
				
				//�S���]�w���w�������
				if( dynOption.targetObjs.length==0 && dynOption.bindDataFields.length==0 ) {
					if( dynOption.targetForm == null) {
						formUtil.bindData(jsonData.data[0]);
					} else {
						formUtil.bindFormData(dynOption.targetForm, jsonData.data[0]);
					}
				}else{
					for(var i=0;i<dynOption.targetObjs.length;i++) {
						var obj = dynOption.targetObjs[i];
						formUtil.bindObjectData(obj, jsonData.data[0][obj.attr("name")]);
					}
					for(var i=0;i<dynOption.bindDataFields.length;i++) {
						formUtil.bindObjectData(
								dynOption.bindDataFields[i].obj, 
								strUtil.tranPattern(dynOption.bindDataFields[i].pattern, jsonData.data[0])
							);
					}
				}
			} else {
				for(var i=0;i<dynOption.targetObjs.length;i++) {
					var obj = dynOption.targetObjs[i];
					formUtil.bindObjectData(obj, "");
				}
				for(var i=0;i<dynOption.bindDataFields.length;i++) {
					formUtil.bindObjectData(
							dynOption.bindDataFields[i].obj, 
							""
						);
				}
			}
		});
	}
};

DynUtil.prototype.openSelect = function(options) { 
	
	var dynOption = $.extend({}, dynUtil.getDynOption(), options);
	
	dynUtil.dynOption = dynOption;

	if( !dynOption.isOpenMultiple || dynOption.bindDataFields.length>0 ) {
		if(dynOption.sqlid != "") {
			
			var selObj = dynOption.targetObj;		
			dynOption.paraObj["sqlid"] = dynOption.sqlid;
			if( dynOption.paraObj["sql"]==null )
				dynOption.paraObj["sql"] = encodeURIComponent(dynOption.sql);
			if( dynOption.queryPageSize!=0 )
				dynOption.paraObj["pageSize"] = dynOption.queryPageSize;
			if( dynOption.queryPageNum!=0 )
				dynOption.paraObj["pageNum"] = dynOption.queryPageNum;
			
			openwin(dynUtil.getWebRoot() + "resources/jsp/OpenSelect.jsp", dynOption.openWidth, dynOption.openHeight);
		}
	} else {
		alert("���]�w�}���h���Ʀs��]�w");
	}
	
	function openwin(URL, width, height){ 
		newWindow = top.open(URL,"AAA","height=" + height + ",width=" + width + ",maximize=no;toolbar=no,directories=no,status=yes,scrollbars=yes,hscrollbars=no,resize=no,menubar=no",'true');
		newWindow.moveTo((screen.width-width)/2, (screen.availHeight-height)/2);
	}
};

DynUtil.prototype.getStrToDataSet = function(str) {
	var dataset = [];
	var ary = str.split(",");
	for( var i=0;i<ary.length;i++) {
		dataset[dataset.length] = {VALUE:ary[i]};
	}
	return dataset;
}

DynUtil.prototype.openSingleSelect = function(options) { 
	
	var dynOption = $.extend({}, dynUtil.getDynOption(), options);
	
	dynUtil.dynOption = dynOption;

	if(dynOption.sqlid != "") {
		
		if( dynOption.openWidth==screen.availWidth )
			dynOption.openWidth = 250;
		if( dynOption.openHeight==screen.availHeight )
			dynOption.openHeight = 320;
		dynOption.paraObj["sqlid"] = dynOption.sqlid;
		if( dynOption.paraObj["sql"]==null )
			dynOption.paraObj["sql"] = encodeURIComponent(dynOption.sql);
		
		if( dynOption.openSelectDataTxt != null && dynOption.openSelectDataTxt != "") {
			dynOption.dataset = dynOption.openSelectDataTxt;
		}
				
		ShowDialog(dynUtil.getWebRoot() + "resources/jsp/OPENSINGLE.jsp", dynOption.openWidth, dynOption.openHeight);
	}

	//�}����m�A�^�ǭ�
	function ShowDialog(htmlfile,width,height) {
	   var style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0"
	   window.showModalDialog(htmlfile,self,style);
	}
};

DynUtil.prototype.openMultipleSelect = function(options) { 
	
	var dynOption = $.extend({}, dynUtil.getDynOption(), options);
	
	dynUtil.dynOption = dynOption;

	if(dynOption.sqlid != "") {

		if( dynOption.openWidth==screen.availWidth )
			dynOption.openWidth = 250;
		if( dynOption.openHeight==screen.availHeight )
			dynOption.openHeight = 320;
		dynOption.paraObj["sqlid"] = dynOption.sqlid;
		if( dynOption.paraObj["sql"]==null )
			dynOption.paraObj["sql"] = encodeURIComponent(dynOption.sql);
		
		if( dynOption.openSelectDataTxt != null && dynOption.openSelectDataTxt != "") {
			dynOption.dataset = dynUtil.getStrToDataSet(dynOption.openSelectDataTxt);
			dynOption.valuepattern = "@{VALUE}";
			dynOption.textpattern = "@{VALUE}";
		}
		
		ShowDialog(dynUtil.getWebRoot() + "resources/jsp/OPENMULTIPLE.jsp", dynOption.openWidth, dynOption.openHeight);
	}

	
	//�}����m�A�^�ǭ�
	function ShowDialog(htmlfile,width,height) {
	   var style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0"
	   window.showModalDialog(htmlfile,self,style);
	}
};

//�H�U����ƻݷf�tSPRING�[�c��MVC�~��ϥ�
//�i�����b�e�ݤUSQL
DynUtil.prototype.queryForList = function(sqlid, paraObj) { 

	var dynOption = dynUtil.getDynOption();	
	if( paraObj!=null)
		dynOption.paraObj = paraObj;
	
	if( (typeof dynOption.paraObj)=="object" ) {
		dynOption.paraObj["sqlid"] = sqlid;
		dynOption.paraObj["isEncode"] = "y";
	}else{
		if( dynOption.paraObj != "" ) dynOption.paraObj+="&";
		dynOption.paraObj += "sqlid="+sqlid+"&isEncode=y";
	}
	var data = [];

	dynUtil.getData(dynOption, function(jsonData){
		data = jsonData.data; 
	});
	return data;
};

DynUtil.prototype.doSqlSelect = function(sql) { 
	
	var dynOption = dynUtil.getDynOption();
	
	dynOption.paraObj["sqlid"] = "UTIL.doSqlSelect";
	dynOption.paraObj["sql"] = encodeURIComponent(sql);
	dynOption.paraObj["isEncode"] = "y";
	
	var data = [];
	dynUtil.getData(dynOption, function(jsonData){
		data = jsonData.data; 
	});
	return data;
};

DynUtil.prototype.doSqlUpdate = function(sql) { 
	
	var dynOption = dynUtil.getDynOption();
	
	dynOption.paraObj["sqlid"] = "UTIL.doSqlUpdate";
	dynOption.paraObj["sql"] = encodeURIComponent(sql);
	dynOption.paraObj["isEncode"] = "y";
	
	var data = 0;
	dynUtil.getData(dynOption, function(jsonData){
		data = jsonData.data; 
	});
	return data;
};

DynUtil.prototype.doSqlInsert = function(sql) { 
	
	var dynOption = dynUtil.getDynOption();
	
	dynOption.paraObj["sqlid"] = "UTIL.doSqlInsert";
	dynOption.paraObj["sql"] = encodeURIComponent(sql);
	dynOption.paraObj["isEncode"] = "y";
	
	var data = 0;
	dynUtil.getData(dynOption, function(jsonData){
		data = jsonData.data; 
	});
	return data;
};

DynUtil.prototype.doSqlDelete = function(sql) { 
	
	var dynOption = dynUtil.getDynOption();
	
	dynOption.paraObj["sqlid"] = "UTIL.doSqlDelete";
	dynOption.paraObj["sql"] = encodeURIComponent(sql);
	dynOption.paraObj["isEncode"] = "y";
	
	var data = 0;
	dynUtil.getData(dynOption, function(jsonData){
		data = jsonData.data; 
	});
	return data;
};

//globe object
var dynUtil = new DynUtil();