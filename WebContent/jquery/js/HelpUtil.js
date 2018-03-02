/** 
 * @fileoverview 定義處理畫面效果的共用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

var __VAR_TIMERID__	=	null;


function HelpUtil() {
}

/**
 * 顯示提示訊息的畫面
 * @param {String} strInfoType 提示訊息的型態 ('WARN','DONE','FAIL','INFO')
 * @param {String} strInfoText 提示訊息的內容
 * @param {Number} strInfoPosX 提示訊息出現的X軸位置,預設值為畫面最左方
 * @param {Number} strInfoPosY 提示訊息出現的Y軸位置,預設值為畫面最上方
 * @param {Number} strInfoTime 提示訊息停留的時間,預設值為3500ms
 * @return 無
 * @type void
 */

HelpUtil.prototype.getWebRoot = function() {
	web = window.location.pathname;
	if( web.indexOf("/") != 0 ) web = "/"+web;
	web = web.substring(0, web.indexOf("/", 1)+1);	
	return web;
};

HelpUtil.prototype.showInfoBar = function() 
{
	$objInfoBar = $("#___HELP_INFOBAR___");
	var strInfoType = (arguments.length > 0)?arguments[0].toString().toUpperCase():null;
	var strInfoText = (arguments.length > 1)?arguments[1].toString():null;
	var strInfoTime = (arguments.length > 2)?parseInt(arguments[2].toString()):null;
	
	if ($objInfoBar.length==0 || arguments.length < 2)
		return;
	
	$("#img1", $objInfoBar).attr("src", helpUtil.getWebRoot()+"image/"+strInfoType.toLowerCase()+".gif");
	$("#font1", $objInfoBar).html(strInfoText);

	$objInfoBar.css("top", document.body.scrollTop);
	
	$("#___HELP_INFOBAR___").css("visibility", "");
	
	if (__VAR_TIMERID__ != null) {
		clearTimeout(__VAR_TIMERID__);
	}
    __VAR_TIMERID__ = setTimeout("helpUtil.closeInfoBar()", (strInfoTime == null)?2000:strInfoTime);
};

/**
 * 關閉提示訊息的畫面
 * @return 無
 * @type void
 */
HelpUtil.prototype.closeInfoBar = function()
{
	$objInfoBar = $("#___HELP_INFOBAR___");

	if ($objInfoBar.length==0 )
		return;

	if (__VAR_TIMERID__ != null) {
		clearTimeout(__VAR_TIMERID__);
		__VAR_TIMERID__ = null;
	}
	$("#___HELP_INFOBAR___").css("visibility", "hidden");
};

/**
 * 顯示資料處理中的畫面
 * @return 無
 * @type void
 */
HelpUtil.prototype.showProcessBar = function() 
{
	$objProcessBar = $("#___HELP_PROCESSBAR___");
	$objInfoBar = $("#___HELP_INFOBAR___");
	
	$("#img2", $objProcessBar).attr("src", helpUtil.getWebRoot()+"image/loading.gif");
	$("#img3", $objProcessBar).attr("src", helpUtil.getWebRoot()+"image/cancel.gif");
	
	if ($objProcessBar.length==0)
		return;
	
	var strInfoText = (arguments.length > 0)?arguments[0].toString():"資料處理中, 請稍待...";
	$("#font2", $objProcessBar).text(strInfoText);
	
	helpUtil.showWindowMask();
	$("#___HELP_PROCESSBAR___").css("visibility", "");
};

/**
 * 關閉資料處理中的畫面
 * @return 無
 * @type void
 */
HelpUtil.prototype.closeProcessBar = function()
{
	$objProcessBar = $("#___HELP_PROCESSBAR___");
	
	if ($objProcessBar.length==0)
		return;

	helpUtil.closeWindowMask();
	$("#___HELP_PROCESSBAR___").css("visibility", "hidden");
};

/**
 * 顯示畫面的遮罩
 * @return 無
 * @type void
 */
HelpUtil.prototype.showWindowMask = function()
{
	$("#___HELP_WINDOWMASK___").css("visibility", "");
};

/**
 * 關閉畫面的遮罩
 * @return 無
 * @type void
 */
HelpUtil.prototype.closeWindowMask = function()
{
	$("#___HELP_WINDOWMASK___").css("visibility", "hidden");
};

$(document).ready(function(){	

	if(!$.prop)
		  $.fn.prop = $.fn.attr;
	if(!$.attr)
		  $.fn.attr = $.fn.prop;
	if(!$.removeProp)
		  $.fn.removeProp = $.fn.removeAttr;
	if(!$.removeAttr)
		  $.fn.removeAttr = $.fn.removeProp;
	
});

//globe object
var helpUtil = new HelpUtil();
document.open();
document.writeln("<div id='___HELP_INFOBAR___' style='font:normal 12pt Arial;position:absolute; top:0px; left:0px; z-index: 100; width:100%; padding:0px 0px 0px 0px; margin:0px 0px 0px 0px; background-color:#FFFF99; border: #000000 1px solid;visibility:hidden' nowrap>");
document.writeln("	<table width='100%'><tr>");
document.writeln("	<td style='width:32px'><img id='img1' src='"+helpUtil.getWebRoot()+"image/warn.gif' style='width:20px; height: 20px; vertical-align:middle'></td>");
document.writeln("	<td><font id='font1' style='font:bold 14pt 新細明體; color: #996600'>111</font></td>");
document.writeln("	<td style='width:32px'><img src='"+helpUtil.getWebRoot()+"image/cancelx.gif' alt='取消' style='float:right; cursor: pointer;width:20px; height: 20px; vertical-align:middle' onClick='helpUtil.closeInfoBar()'></td>");
document.writeln("	</tr></table>");
document.writeln("</div>");
document.writeln("<div id='___HELP_PROCESSBAR___' style='font:normal 12pt Arial;position:absolute; top: 0px; left: 0px; z-index: 1001; width: 100%; height: 35px; padding:0px 0px 0px 0px; margin:0px 0px 0px 0px; background-color:#FFFF99; border: #000000 1px solid; overflow:hidden;visibility:hidden'>");
document.writeln("	<img id='img2' src='"+helpUtil.getWebRoot()+"image/loading.gif' style='float:left;width:20px; height: 20px; vertical-align:middle'>");
document.writeln("	<font id='font2' style='float:left; margin-top: 5px; font:bold 14pt 新細明體;'>資料處理中, 請稍待...</font>");
document.writeln("	<img id='img3' src='"+helpUtil.getWebRoot()+"image/cancelx.gif' alt='取消' style='float:right; cursor: pointer;width:20px; height: 20px; vertical-align:middle' onClick='helpUtil.closeProcessBar()'>");
document.writeln("</div>");
document.writeln("<div id='___HELP_WINDOWMASK___' style='position:absolute; top: 0px; left: 0px; z-index: 1000; width: 100%; height: 100%; padding:0px 0px 0px 0px; margin:0px 0px 0px 0px; background-color:#DCDCDC;opacity: 0.5; overflow: hidden;visibility:hidden'>");
document.writeln("	<iframe style='position:absolute;top:0px;left:0px;z-index:-1;filter:mask();width:100%;height:100%;'></iframe>");
document.writeln("</div>");
document.close();
