/** 
 * @fileoverview 定義視窗的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義視窗的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function WindowUtil()
{
}


var __href__ = location.href;
var __a  = __href__.split('/');
var webroot__ = '';
if (__a.length>3){ webroot__ = __a[3]};   
var __BasePath__ = '/' + webroot__ + '/';
if("SOL" != webroot__){
	__BasePath__ = '/';
}
//var __BasePath__ = window.location.pathname.replace(/(^\/([^\/]+\/)*).*/g,"$1");

/**
 * @private
 */
WindowUtil.basePath = __BasePath__ ;
//WindowUtil.basePath = window.location.pathname.replace(/(^\/([^\/]+\/)*).*/g,"$1");
//alert(WindowUtil.basePath);
/**
 * 取得網頁基底的路徑
 * @return 結尾包含"/"的網頁基底路徑
 * @type String
 */
WindowUtil.getBasePath=function()
{
	try
	{
		return WindowUtil.basePath;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getBasePath",arguments,ex));
	}
}

/**
 * 取得目前視窗開啟的網頁URL
 * @param {window} win 視窗物件, 預設值為 self 物件
 * @return 以http://開頭的URL
 * @type String
 */
WindowUtil.getBaseUrl=function(win)
{
	try
	{
		var url = ((win == null)?self:win).location.href;
		
		if(url.indexOf('?')==-1)
		{
			return url;
		}
		else
		{
			return url.substr(0,url.indexOf('?'));
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getBaseUrl",arguments,ex));
	}
}

/**
 * 取得目前視窗開啟的網頁檔名
 * @param {window} win 視窗物件, 預設值為 self 物件
 * @return 檔案名稱
 * @type String
 */
WindowUtil.getCurrentPage=function(win)
{
	try
	{
		var file = WindowUtil.getBaseUrl(win);
		
		return file.split('/')[file.split('/').length - 1];
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getCurrentPage",arguments,ex));
	}
}

/**
 * 開啟列印用的視窗(window.open 全螢幕)
 * @param {String} url 開啟網頁的URL字串
 * @param {String} name 開啟視窗的名稱 
 * @return 開啟視窗的物件
 * @type window
 */
WindowUtil.openPrintWindow=function(url,name)
{
	try
	{
		var $2_="width="+screen.availWidth+",height="+screen.availHeight+",left=0,top=0,scrollbars=1,status=no,resizable=no,titlebar=no";
		
		win_=window.open(url,name,$2_);

		win_.resizeTo(screen.availWidth,screen.availHeight);
		win_.moveTo(0,0);
		return win_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openPrintWindow",arguments,ex));
	}
}

/**
 * 開啟固定樣式的視窗(window.open)
 * @param {String} url 開啟網頁的URL字串
 * @param {String} name 開啟視窗的名稱 
 * @param {Number} width 開啟的視窗寬度, 預設值為螢幕的寬度 
 * @param {Number} height 開啟的視窗高度, 預設值為螢幕的高度 
 * @return 開啟視窗的物件
 * @type window
 */
WindowUtil.openWindow=function(url,name)
{
	try
	{
		var $2_=((arguments[2]==null)?screen.availWidth:arguments[2]);
		var $3_=((arguments[3]==null)?screen.availHeight:arguments[3]);
		var $4_="width="+$2_+",height="+$3_+",left=0,top=0,scrollbars=1,status=no,resizable=no,titlebar=no";
		
		win_=window.open(url,name,$4_);

		win_.resizeTo($2_,$3_);
		win_.moveTo(0,0);
		return win_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openWindow",arguments,ex));
	}
}

/**
 * 開啟固定樣式的視窗(showModalDialog)
 * @param {String} url 開啟網頁的URL字串
 * @param {Number} width 開啟的視窗寬度, 預設值為螢幕的寬度 
 * @param {Number} height 開啟的視窗高度, 預設值為螢幕的高度 
 * @param {Object} obj 要傳遞給開啟視窗的物件, 預設值為呼叫視窗的 self 物件
 * @return showModalDialog() 的回傳值
 * @type Object
 */
WindowUtil.openDialog=function(url,width,height,obj)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?self:Form.document_.parentWindow;
		return showModalDialog(url,(arguments[3] == null)?self:arguments[3],"dialogWidth:"+((arguments[1]==null)?screen.availWidth:arguments[1])+"px;dialogHeight:"+((arguments[2]==null)?screen.availHeight:arguments[2])+"px;center:1;scroll:1;help:0;status:0;");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openDialog",arguments,ex));
	}
}

/**
 * 開啟自訂傳遞物件的視窗(showModalDialog)
 * @param {String} url 開啟網頁的URL字串
 * @param {Number} width 開啟的視窗寬度, 預設值為螢幕的寬度 
 * @param {Number} height 開啟的視窗高度, 預設值為螢幕的高度 
 * @param {Object} obj 要傳遞給開啟視窗的物件
 * @return showModalDialog() 的回傳值
 * @type Object
 */
WindowUtil.openObjDialog=function(url, obj, width, height)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;
		return showModalDialog(url,obj,"dialogWidth:"+((arguments[2]==null)?screen.availWidth:arguments[2])+"px;dialogHeight:"+((arguments[3]==null)?screen.availHeight:arguments[3])+"px;center:1;scroll:1;help:0;status:0;");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openObjDialog",arguments,ex));
	}
}

/**
 * 開啟指令碼錯誤回報的視窗(window.open)
 * @deprecated
 * @param {String} report 回報的錯誤訊息
 * @param {Number} width 開啟的視窗寬度, 預設值為500px
 * @param {Number} height 開啟的視窗高度, 預設值為600px
 * @return 開啟視窗的物件
 * @type window
 */
WindowUtil.openExceptionWindow=function(report,width,height)
{
	try
	{
		if(width==null)
			width=500;
		if(height==null)
			height=600;
		
		var $21_=(screen.width)? (screen.width - width)/ 2 :0;
		var $22_=(screen.height)? (screen.height - height)/ 2 :0;
		var $23_=window.open('','errWin','height='+height+',width='+width+',scrollbars=yes');
		$23_.document.writeln(report);
		$23_.document.close();
		$23_.focus();
		$23_.moveTo($21_,$22_);
		$23_.document.body.scrollTop='10000000';
		$23_.document.title='Script Error Report';
		return $23_;
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openExceptionWindow",arguments,ex));
	}
}

/**
 * 開啟指令碼錯誤回報的視窗(showModalDialog) 
 * @param {String} report 回報的錯誤訊息
 * @type void
 */
WindowUtil.openExceptionDialog=function(report)
{
	try
	{
		var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;		
		objWindow.showModalDialog(WindowUtil.getBasePath()+"framework/utility/ErrorReport.html",report,"dialogWidth:500px;dialogHeight:400px;center:1;scroll:0;help:0;status:0;resizable:0");
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openExceptionDialog",arguments,ex));
	}
}

/**
 * 開啟表單檢查結果的視窗(showModalDialog) 
 * @param {String} report 表單的檢查結果
 * @type void
 */
WindowUtil.openFormcheckDialog=function(report)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;

		showModalDialog(WindowUtil.getBasePath()+"framework/utility/FormCheck.html",report,"dialogWidth:500px;dialogHeight:360px;center:1;scroll:0;help:0;status:0;resizable:0");
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openFormcheckDialog",arguments,ex));
	}
}

/**
 * 載入分頁的視窗(showModalDialog) 
 * @param {String} url 開啟分頁的URL字串, 以絕對路徑或相對於基底路徑表示 
 * @param {String} title 開啟視窗的標題
 * @param {Number} width 開啟的視窗寬度, 預設值為螢幕的寬度 
 * @param {Number} height 開啟的視窗高度, 預設值為螢幕的高度 
 * @param {String} param 傳遞給開啟視窗的參數
 * @return showModalDialog() 的回傳值
 * @type void
 */
WindowUtil.openFrameDialog=function(url,title)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;

		if (StrUtil.isEmpty(title))
		{
			var postSrc = url.split("?")[0];
			var postData = "fetchTitle=Y"; // (url.split("?").length > 1)?url.split("?")[1]+"&fetchTitle=Y":
			title = Assist.postRemoteData(postSrc,postData).replace(/[\n\r]/g,"");
		}

		return showModalDialog(WindowUtil.getBasePath()+"framework/utility/FrameLoader.jsp?src="+StrUtil.encodeURIComponent(url)+"&title="+StrUtil.encodeURIComponent(title),(arguments[4])?new Array(self,arguments[4]):self,"dialogWidth:"+((arguments[2]==null)?screen.availWidth:arguments[2])+"px;dialogHeight:"+((arguments[3]==null)?screen.availHeight:arguments[3])+"px;center:1;scroll:0;help:0;status:0;");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openFrameDialog",arguments,ex));
	}
}

/**
 * 載入分頁的視窗(showModalDialog) 
 * @param {String} ip 開啟分頁的IP字串, 必須以http://開頭表示
 * @param {String} url 開啟分頁的URL字串, 以絕對路徑或相對於基底路徑表示 
 * @param {String} title 開啟視窗的標題
 * @param {Number} width 開啟的視窗寬度, 預設值為螢幕的寬度 
 * @param {Number} height 開啟的視窗高度, 預設值為螢幕的高度 
 * @param {String} param 傳遞給開啟視窗的參數
 * @return showModalDialog() 的回傳值
 * @type void
 */
WindowUtil.openPageDialog=function(ip,url,title)
{
	try
	{
		if (StrUtil.isEmpty(title))
		{
			var postSrc = ip+WindowUtil.getBasePath()+url.split("?")[0];
			var postData = "fetchTitle=Y";
			title = Assist.postRemoteData(postSrc,postData).replace(/[\n\r]/g,"");
		}

		return showModalDialog(ip+WindowUtil.getBasePath()+"framework/utility/PageLoader.jsp?src="+StrUtil.encodeURIComponent(url)+"&title="+StrUtil.encodeURIComponent(title),(arguments[5])?new Array(self,arguments[5]):self,"dialogWidth:"+((arguments[3]==null)?screen.availWidth:arguments[3])+"px;dialogHeight:"+((arguments[4]==null)?screen.availHeight:arguments[4])+"px;center:1;scroll:0;help:0;status:0;");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openPageDialog",arguments,ex));
	}
}

/**
 * 載入分頁的視窗(window.open) 
 * @param {String} url 開啟分頁的URL字串, 以絕對路徑或相對於基底路徑表示 
 * @param {String} title 開啟視窗的標題
 * @param {Number} width 開啟的視窗寬度, 預設值為螢幕的寬度 
 * @param {Number} height 開啟的視窗高度, 預設值為螢幕的高度 
 * @type void
 */
WindowUtil.openFrameWindow=function(url,title)
{
	try
	{
		//var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?window:Form.document_.parentWindow;

		window.open(WindowUtil.getBasePath()+"framework/utility/FrameLoader.jsp?src="+StrUtil.encodeURIComponent(url)+"&title="+StrUtil.encodeURIComponent(title),"","width="+((arguments[2]==null)?screen.availWidth:arguments[2])+",height="+((arguments[3]==null)?screen.availHeight:arguments[3])+",left=0,top=0,status=no,location=no,menubar=no,scrollbars=no,resizable=no,toolbar=no");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.openFrameWindow",arguments,ex));
	}
}

/**
 * 取得傳入物件的X軸位置
 * @param {Object} srcObj 要取得位置的物件 
 * @return X軸位置(pixel)
 * @type Number
 */
WindowUtil.getClientX=function(srcObj)
{
	try
	{
		var offsetX = 0;
		var obj = srcObj;
		
		while (obj != null)
		{
			offsetX += obj.offsetLeft;
			obj = obj.offsetParent;
		}
		
		return offsetX;
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getClientX",arguments,ex));
	}
}

/**
 * 取得傳入物件的Y軸位置
 * @param {Object} srcObj 要取得位置的物件 
 * @return Y軸位置(pixel)
 * @type Number
 */
WindowUtil.getClientY=function(srcObj)
{
	try
	{
		var offsetY = 0;
		var obj = srcObj;
		
		while (obj != null)
		{
			offsetY += obj.offsetTop;
			obj = obj.offsetParent;
		}
		
		return offsetY;
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("WindowUtil.getClientY",arguments,ex));
	}
}