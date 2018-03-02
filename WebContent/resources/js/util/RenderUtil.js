
/**
 * @description 屬於給予select checkbox radio內容的工具
 * @class 表單工具
 * @author sorge
 * @constructor
 */
function RenderUtil() {
}

RenderUtil.prototype.getRenderOption = function() { 
	var renderOptions = {		
		  targetObj: null		//哪一個物件(select checkbox radio)
		, valuepattern : ''		//value顯示的樣板
		, textpattern: ''		//text顯示的樣板
		, attrpattern: ''		//屬姓(如 align='center' )
		, data : {}				//JSON格式的資料集
		, isClear: true			//是否先清除在重畫資料
		, haveall : false		//是否有 value=空白的項目
		, allText : "全部"		//(適用 targetObj=select時使用) value=空白那筆的顯示字串
		, defaultValue: null	//資料重畫好之後，預設值是什麼
		, numOfLine: 0			//美航幾個(0表是不設)
		, filter: null
	};
		
	return renderOptions;
};

RenderUtil.prototype.renderObject = function(options) {	
	var renderOption = $.extend({}, renderUtil.getRenderOption(), options);
	
	if( renderOption.targetObj != null ) {
		
		if( typeof(renderOption.data)=="string" ) {
			renderOption.valuepattern = "@{TXT}";
			renderOption.textpattern = "@{TXT}";
			var ary = renderOption.data.split(",");			
			renderOption.data = [];
			for(var i=0;i<ary.length;i++) {
				renderOption.data[i] = {"TXT":ary[i]};
			} 
		}
		
		if( renderOption.targetObj[0].type=="checkbox" )
			renderUtil.renderCheckBoxHtml(renderOption);
		else if( renderOption.targetObj[0].type=="radio" )
			renderUtil.renderRadioHtml(renderOption);
		else if( renderOption.targetObj[0].type=="select-one" || renderOption.targetObj[0].type=="select-multi" || renderOption.targetObj[0].type=="select-multiple")
			renderUtil.renderOptionHtml(renderOption);
	}
}

RenderUtil.prototype.renderCheckBoxHtml = function(options) { 
	var renderOption = $.extend({}, renderUtil.getRenderOption(), options);

	if( renderOption.targetObj != null ) {
		
		var name = renderOption.targetObj[0].name;
		var id = renderOption.targetObj[0].id;
				
		if( $("span[id=render_"+name+"]").has(renderOption.targetObj).length==1 ) {
			$span = $("span[id=render_"+name+"]").has(renderOption.targetObj);
			$span.html("");
		}else{
			$span = $("<span id='render_"+name+"'></span>").insertBefore($(renderOption.targetObj[0]));
			renderOption.targetObj.remove();
		}
		
		for(var i=0;i<renderOption.data.length;i++) {
			
			if( renderOption.filter == null || renderUtil.checkFilter(renderOption.data[i], renderOption.filter) ) {		
				var value = strUtil.tranPattern(renderOption.valuepattern, renderOption.data[i]);
				var text = strUtil.tranPattern(renderOption.textpattern, renderOption.data[i]);		
				var attr = strUtil.tranPattern(renderOption.attrpattern, renderOption.data[i]);			
				
				$checkbox = $("<input type='checkbox' id='"+id+"' name='"+name+"' "+attr+">").attr("value", value);
				if( typeof(options.numOfLine)=="undefined" || options.numOfLine==0)
					$span.append($checkbox).append(text+" &nbsp;");
				else{
					var persent = Math.floor(98/options.numOfLine);
					$div = $("<div style='float:left;width:"+persent+"%'></div>").append($checkbox).append(text+" &nbsp;");
					$span.append($div);
				}
			}
			
		}
		
		if( renderOption.defaultValue!=null )
			formUtil.bindObjectData($(":checkbox[name="+name+"]", $span), renderOption.defaultValue);
	}
}

RenderUtil.prototype.renderRadioHtml = function(options) { 
	
	var renderOption = $.extend({}, renderUtil.getRenderOption(), options);

	if( renderOption.targetObj != null ) {

		var name = renderOption.targetObj[0].name;
		var id = renderOption.targetObj[0].id;
				
		if( $("span[id=render_"+name+"]").has(renderOption.targetObj).length==1 ) {
			$span = $("span[id=render_"+name+"]").has(renderOption.targetObj);
			$span.html("");
		}else{
			$span = $("<span id='render_"+name+"'></span>").insertBefore($(renderOption.targetObj[0]));
			renderOption.targetObj.remove();
		}

		if( renderOption.haveall )
			$span.append("<input type='radio' id='"+id+"' name='"+name+"' value=''>&nbsp;全部 &nbsp;");
		
		for(var i=0;i<renderOption.data.length;i++) {
			
			if( renderOption.filter == null || renderUtil.checkFilter(renderOption.data[i], renderOption.filter) ) {		
				var value = strUtil.tranPattern(renderOption.valuepattern, renderOption.data[i]);
				var text = strUtil.tranPattern(renderOption.textpattern, renderOption.data[i]);
				var attr = strUtil.tranPattern(renderOption.attrpattern, renderOption.data[i]);
				
				$radio = "<input type='radio' id='"+id+"' name='"+name+"' value='"+value+"' "+attr+">";			
				if( typeof(options.numOfLine)=="undefined" || options.numOfLine==0)
					$span.append($radio).append(text+" &nbsp;");
				else{
					var persent = Math.floor(98/options.numOfLine);
					$div = $("<div style='float:left;width:"+persent+"%'></div>").append($radio).append(text+" &nbsp;");
					$span.append($div);
				}
			}
			
		}

		if( renderOption.defaultValue!=null )
			formUtil.bindObjectData($(":radio[name="+name+"]", $span), renderOption.defaultValue);
	}
}

RenderUtil.prototype.renderOptionHtml = function(options) { 
	var renderOption = $.extend({}, renderUtil.getRenderOption(), options);
	
	if( renderOption.targetObj != null ) {
		
		var obj = renderOption.targetObj;
		obj.hide();
		
		if(renderOption.isClear)
			obj.children().remove();

		if( obj.children().length==0 && renderOption.haveall )
			$("<option value=''>"+renderOption.allText+"</option>").appendTo(obj);

		for(var i=0;i<renderOption.data.length;i++) {	
			
			if( renderOption.filter == null || renderUtil.checkFilter(renderOption.data[i], renderOption.filter) ) {			
				var value = strUtil.tranPattern(renderOption.valuepattern, renderOption.data[i]);
				var text = strUtil.tranPattern(renderOption.textpattern, renderOption.data[i]);
				var attr = strUtil.tranPattern(renderOption.attrpattern, renderOption.data[i]);
				
				var option = "<option value='"+value+"' "+attr+">"+text+"</option>";
				obj.append(option);				
			}
			
		}
		
		if( renderOption.defaultValue!=null )
			formUtil.bindObjectData(renderOption.targetObj, renderOption.defaultValue);
		obj.show();
	}
}

RenderUtil.prototype.checkFilter = function(data, filter) {
	var ret = true;
	if( filter != null ) {
		if( typeof(filter)=="object" ) {
			for (var key in filter) {
				if( typeof(data[key])!="string" || data[key] != filter[key] ) {
					ret = false;
					break;
				}
			}
		}else if( typeof(filter)=="string" ) {
			ret = eval( strUtil.tranPattern(filter,data) );
		}
	}else
		ret = false;
	return ret;
}
  
RenderUtil.prototype.renderCalendar = function(object, dateFmt) {
	
	if( typeof(object.attr("id")) == "undefined" ) {
		var i = $(":text[id^=WDatePicker]").length;
		object.attr("id", "WDatePicker" + i);
	}
	
	if( dateFmt==null || dateFmt=="" )
		dateFmt = "yyyyMMdd";	
	object.attr("size", dateFmt.length);
	object.attr("maxlength", dateFmt.length); 
	object.attr("dateFmt", dateFmt); 
	
	if( object.next().length>0 ) {
		if( object.next().attr("value") == "日曆" && object.attr("id")==object.next().attr("targetid") ) {
			object.next().remove();
		}
	}
	object.keypress(function(){
		event.keyCode = checkUtil.lockNum();
	})
	
	if( typeof(object.attr("check")) == "string" ) {		
		if( object.attr("check").charAt(2)=="1" ) {
			object.blur(function(){
				if( $(this).val() != "")
					$(this).val(checkUtil.addNum($(this).val(), $(this).attr("maxlength"), "0"));
			})
		}
	}
	
	object.focus(function(){
		WdatePicker({dateFmt : $(this).attr("dateFmt"), lang   : 'zh-tw'});
	})
	
}

//globe object
var renderUtil = new RenderUtil();