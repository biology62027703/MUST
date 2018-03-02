
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
		  targetForm: null			//有指定，只會處理這個form下的東西
		, paraObj : {}				//要傳給後端的參數		
		, sqlid : 'UTIL.doSqlSelect'//要執行的SQL的ID
		, sql : ''					//可透過Util.doSqlSelect執行SQL
		, formMethod : 'POST'		//資料傳遞方式
		, async: false				//同步非同步

		//屬於 setSelectOption 在用的參數
		, targetObj : null			//指定哪一個select物件
		, isClear: true				//是否清除原始的資料
		, haveall : false			//是否於為智0增加「全部」選項
		, valuepattern: '@{VALUE}'	//value顯示的樣板
		, textpattern: '@{TEXT}'	//text顯示的樣板
		, attrpattern: ""
		, defaultValue: null		//預設值是什麼
		
		//屬於 bindDataByQuery 在用的參數
		, toLowerCase: false
		, targetObjs : []			//從後端取資料後，指定哪些欄位，會直接拿data[i]同名稱的欄位
		, bindDataFields : []		//格式為{obj:$obj, pattern:"pattern"}，會將obj的value設為 pattern轉換後的字串
		
		//屬於開窗時所使用的參數
		, isSelectModule: true		//試不是選擇模式
		, isOpenMultiple: false		//試不是多選模式
		, openWidth: screen.availWidth	//開啟視窗的寬度(預設畫面寬度)
		, openHeight: screen.availHeight//開啟視窗的高度(預設畫面高度)
		, resultColumnMeta: []		//顯示資料的內容(定義方式等同UiTableGrid裡的欄位定義)
		, queryColumns : []			//開窗的查詢條件欄位(定義內容為 getIniColumnOptions 裡的欄位)
		, queryPageSize: 0			//非零則會將查詢結果分頁
		, queryPageNum: 0
		, openSelectTitle: "請選擇資料"	//開窗的TITLE
		, openSelectDataTxt: null		//以逗號分格的開窗字串
		, splitStr: ','				//多選帶回時已甚麼福號區隔
			
		//比較簡單的開窗多選或單選所使用
		, texttarget: null			//開窗單選或多選text文字設定的存放位置
		, valuetarget: null			//開窗單選或多選value文字設定的存放位置
		, url: null					//由哪個url取得資料回來( 層級大於 sqlid )
		, dataset: null				//由前端指定的資料集( 層級大於 url )
		, filter: null
	};
	
	return dynOptions;
};

//開頁面選擇的查詢務劍
DynUtil.prototype.getIniColumnOptions = function() { 
	var options = {
			  head_l: null			//字串內容
			, head_r: null			//字串內容 ( 層級大於 name的定義)
			
			, type: null			//head_r 部分的物劍
			, name: null
			, size: 1
			, options: null			//適用於 select checkbox radio 的內容 (格式為 {v:"v", t:"t"}) 此為靜態的JSON資料
			
			, sqlid: null			//適用於 select checkbox radio 的內容  此為靜態的JSON資料
			, valuepattern: ''		//等同 getDynOption.valuepattern
			, textpattern: ''		//等同 getDynOption.textpattern
			, haveall: false		//等同 getDynOption.haveall
			, allText: "全部"
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
				//是否以小寫處理
				if( dynOption.toLowerCase)		
					jsonData.data[0] = formUtil.convertToLowCaseKey(jsonData.data[0]);
				
				//沒有設定指定的物件時
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
		alert("未設定開窗多選資料存放設定");
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

	//開窗位置，回傳值
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

	
	//開窗位置，回傳值
	function ShowDialog(htmlfile,width,height) {
	   var style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0"
	   window.showModalDialog(htmlfile,self,style);
	}
};

//以下的資料需搭配SPRING架構的MVC才能使用
//可直接在前端下SQL
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