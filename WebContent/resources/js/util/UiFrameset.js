/**
 * @description 所有關於頁框的操作皆使用此共用元件，包含了表單資料送出與表單資料繫結。
 * @class 頁框工具
 * @author chkchk
 * @constructor
 */
function UiFrameset(framesetObj) {
	this._framesetObj = framesetObj;
	this._pname = "";
	this._nname = "";
}

/**
 * @ignore 增加頁框
 * */
UiFrameset.prototype.addFrame = function(options) {
	var _frameSetObj = this._framesetObj;
	try {
		var defWidth = "100%";
		var defHeight = "100%";
		$.each(options, function(index, element){
			var _newFrame = $("<iframe id='"+element.frameName+"' name='"+element.frameName+"' src='"+element.frameSrc+"'></iframe>").appendTo(_frameSetObj);
			_newFrame.attr('marginwidth', 0);
			_newFrame.attr('frameborder', 0); 
			_newFrame.attr('scrolling', 'auto'); 
			_newFrame.attr('width', defWidth);
			_newFrame.attr('height', defHeight);
			_newFrame.css('border-bottom', '1px solid');
		});
	} catch(e) {
		alert(e);
	}
}; 

/**
 * @description 移除頁框
 * @param {string} name 欲移除的頁框名稱
 * @example 
 * 	uiFrameset.removeFrame('v2');
 * */
UiFrameset.prototype.removeFrame = function(name) {
	var _frameSetObj = this._framesetObj;
	$('iframe[name=' + name + ']', _frameSetObj).remove();
};

/**
 * @description 切換頁框
 * @param {string} name 欲切換的頁框名稱
 * @example 
 * 	uiFrameset.switchFrame('v2');
 * */
UiFrameset.prototype.switchFrame = function(name) {
	var _frameSetObj = this._framesetObj;
	$('iframe[name=' + name + ']', _frameSetObj).show();
	$('iframe[name!=' + name + ']', _frameSetObj).hide();
};

/**
 * @description 切換頁框後執行某函式
 * @param {string} name 欲切換的頁框名稱
 * @param {string} action 切換頁框後所要執行的函式
 * @param {map} [param=null] 函式的參數
 * @example 
 *  要切換至v2後執行processEdit則在v2必須定義processEdit函式
 * 	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult); 
 * */ 
UiFrameset.prototype.switchFrameWithExecute = function(name, action, param) {
	if (action != null) {
		try {
			if (param == null) {
				eval("frames['"+name+"']." + action + "()");
			} else {
				eval("frames['"+name+"']." + action + "(param)");
			}
		} catch(ignore) {
			alert(ignore);
		}
	}
	
	this.switchFrame(name);
};

/**
 * @description 執行某頁框的某函式
 * @param {string} name 欲切換的頁框名稱
 * @param {string} action 切換頁框後所要執行的函式
 * @param {map} [param=null] 函式的參數
 * @example 
 *  要切換至v2後執行processEdit則在v2必須定義processEdit函式
 * 	uiFrameset.FrameWithExecute('v2', 'processEdit', jsonResult); 
 * */ 
UiFrameset.prototype.executeFrameFunction = function(name, action, param) {
	if (action != null) {
		try {
			if (param == null) {
				eval("frames['"+name+"']." + action + "()");
			} else {
				eval("frames['"+name+"']." + action + "(param)");
			}
		} catch(ignore) {
			alert(ignore);
		}
	}
};

var _uiFrameset = new UiFrameset();