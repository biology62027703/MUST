/*********************error function start*****************************/
document.write("<div align='center' style='font-size:14pt; font-weight: bold;color: red;background-color:#E8F000;visibility:hidden; position:absolute; top:0px; left:0px;' id=errhome>錯誤訊息</div>");

document.write("<div align='center' style='font-size:14pt; font-weight: bold;color: blue;background-color:#E8F000;visibility:hidden; position:absolute; top:0px; left:0px;' id=dbhome>資料庫錯誤</div>");
/** 
 * @fileoverview 定義處理畫面效果的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

var __VAR_LOADING__	=	false;
var __VAR_TIMERID__	=	null;

var __href__ = location.href;
var __a  = __href__.split('/');
var webroot__ = '';
if (__a.length>3){ webroot__ = __a[3]};    
var __BasePath__ = '/' + webroot__ + '/';

document.writeln("<div id='__HELP_LOADINGBAR__' style='font:normal 12pt Arial;position:absolute; top: 0px; left: 0px; z-index: 100; width: 100%; height: 1px; padding:0px 0px 0px 0px; margin:0px 0px 0px 0px; background-color:#FFFF99; border: #000000 1px solid; overflow:hidden; filter: Alpha(Opacity=90); visibility:hidden;'>");
document.writeln("	<img src='"+ __BasePath__ +"image/loading.gif' style='width:32px; height: 32px; vertical-align:middle'>");
document.writeln("	<font style='font:bold 17pt 新細明體; vertical-align:baseline'>網頁讀取中, 請稍待...</font>");
document.writeln("</div>");

document.writeln("<div id='__HELP_INFOBAR__' style='font:normal 12pt Arial;position:absolute; top:0px; left:0px; z-index: 100; height: 35px; padding:0px 0px 0px 0px; margin:0px 0px 0px 0px; background-color:#FFFF99; border: #000000 1px solid; visibility:hidden;' nowrap onclick='closeInfoBar()'>");
document.writeln("	<img src='"+ __BasePath__ +"image/warn.gif' style='width:32px; height: 32px; vertical-align:middle'>");
document.writeln("	<font style='font:bold 17pt 新細明體; color: #996600; vertical-align:baseline'>尚未設定訊息</font>");
document.writeln("</div>");

document.writeln("<div id='__HELP_PROCESSBAR__' style='font:normal 12pt Arial;position:absolute; top: 0px; left: 0px; z-index: 1001; width: 100%; height: 35px; padding:0px 0px 0px 0px; margin:0px 0px 0px 0px; background-color:#FFFF99; border: #000000 1px solid; overflow:hidden; visibility:hidden'>")
document.writeln("	<img src='"+ __BasePath__ +"image/loading.gif' style='float:left;width:32px; height: 32px; vertical-align:middle'>");
document.writeln("	<font style='float:left; margin-top: 5px; font:bold 17pt 新細明體;'>資料處理中, 請稍待...</font>");
document.writeln("	<img src='"+ __BasePath__ +"image/cancelx.gif' alt='取消' style='float:right; cursor: pointer;width:32px; height: 32px; vertical-align:middle' onClick='closeProcessBar()'>");
document.writeln("</div>");

document.writeln("<div id='__HELP_WINDOWMASK__' style='position:absolute; top: 0px; left: 0px; z-index: 1000; width: 100%; height: 100%; padding:0px 0px 0px 0px; margin:0px 0px 0px 0px; background-color:#E8FCE4; filter: Alpha(Opacity=50); overflow: hidden; visibility:hidden'>")
document.writeln("	<iframe style='display:none;display:block;position:absolute;top:0px;left:0px;z-index:-1;filter:mask();width:1024px;height:768px;'></iframe>");
document.writeln("</div>");


/**
 * 顯示網頁讀取中的畫面
 * @return 無
 * @type void
 */
function showLoadingBar()
{
	try
	{
		var objLoadingBar = document.getElementById("__HELP_LOADINGBAR__");
		
		if (objLoadingBar == null)
		{
			return;
		}

		if (arguments.length < 2)
		{
			__VAR_LOADING__ = true;
			//objLoadingBar.style.visibility = 'visible';
			
			if (arguments.length == 0 || arguments[0] == null)
			{
				objLoadingBar.childNodes[2].innerText="網頁讀取中, 請稍待...";
			}
			else
			{
				objLoadingBar.childNodes[2].innerText=arguments[0];
			}
		}
	
		var objHeight = parseInt(objLoadingBar.style.height.replace(/px/g,""));

		if (__VAR_LOADING__)
		{
			if (objHeight < 35)
			{
				objLoadingBar.style.height = ((objHeight+2 > 35)?35:(objHeight+2));

				if (arguments.length > 1 && arguments[1] == false && objHeight > 20)
				{
					objLoadingBar.style.height = "1px";
					objLoadingBar.style.visibility = "visible";
					setTimeout("showLoadingBar(null,true)",10);
				}
				else if (arguments.length > 1 && arguments[1] == true)
				{
					setTimeout("showLoadingBar(null,true)",10);
				}
				else
				{
					setTimeout("showLoadingBar(null,false)",10);
				}
			}
			else
			{
				setTimeout("showLoadingBar(null,true)",50);
			}
		}
		else
		{
			objLoadingBar.style.visibility = "hidden";
			objLoadingBar.style.height = "1px";
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("showLoadingBar",arguments,ex));
	}
}

/**
 * 關閉網頁讀取中的畫面
 * @return 無
 * @type void
 */
function closeLoadingBar()
{
	__VAR_LOADING__ = false;
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
function showInfoBar()
{
	try
	{
		var objInfoBar = document.getElementById("__HELP_INFOBAR__");

		var strInfoType = (arguments.length > 0)?arguments[0].toString().toUpperCase():null;
		var strInfoText = (arguments.length > 1)?arguments[1].toString():null;
		var strInfoPosX = (arguments.length > 2)?parseInt(arguments[2].toString()):null;
		var strInfoPosY = (arguments.length > 3)?parseInt(arguments[3].toString()):null;
		var strInfoTime = (arguments.length > 4)?parseInt(arguments[4].toString()):null;
		if (objInfoBar == null || arguments.length < 2)
		{
			return;
		}
		if (strInfoType == "WARN")
		{
			//objInfoBar.childNodes[0].src = __BasePath__ +"image/warn.gif";
			//objInfoBar.childNodes[2].style.color ="#996600";
			objInfoBar.getElementsByTagName("img")[0].src = __BasePath__+"image/warn.gif";
			objInfoBar.getElementsByTagName("font")[0].style.color = "#996600";
		}
		else if (strInfoType == "DONE")
		{
			//objInfoBar.childNodes[0].src = __BasePath__+"image/done.gif";
			//objInfoBar.childNodes[2].style.color ="#006600";
			objInfoBar.getElementsByTagName("img")[0].src = __BasePath__+"image/done.gif";
			objInfoBar.getElementsByTagName("font")[0].style.color = "#006600";
		}
		else if (strInfoType == "FAIL")
		{
			//objInfoBar.childNodes[0].src = __BasePath__+"image/fail.gif";
			//objInfoBar.childNodes[2].style.color ="#FF0000";
			objInfoBar.getElementsByTagName("img")[0].src = __BasePath__+"image/fail.gif";
			objInfoBar.getElementsByTagName("font")[0].style.color = "#FF0000";
		}
		else
		{
			//objInfoBar.childNodes[0].src = __BasePath__+"image/info.gif";
			//objInfoBar.childNodes[2].style.color ="#003366";
			objInfoBar.getElementsByTagName("img")[0].src = __BasePath__+"image/info.gif";
			objInfoBar.getElementsByTagName("font")[0].style.color = "#003366";
		}
		//objInfoBar.childNodes[2].innerText = strInfoText;
		objInfoBar.getElementsByTagName("font")[0].innerHTML = strInfoText;
		objInfoBar.style.left = (strInfoPosX == null)?document.body.scrollLeft:(((strInfoPosX+objInfoBar.offsetWidth)>document.body.offsetWidth)?(document.body.offsetWidth-objInfoBar.offsetWidth):strInfoPosX);
		objInfoBar.style.top = (strInfoPosY == null)?document.body.scrollTop:strInfoPosY;
		objInfoBar.style.visibility = "visible";

		if (__VAR_TIMERID__ != null)
		{
			clearTimeout(__VAR_TIMERID__);
		}
	    __VAR_TIMERID__ = setTimeout("closeInfoBar()", (strInfoTime == null)?3500:strInfoTime);
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("showInfoBar",arguments,ex));
	}	
}

/**
 * 關閉提示訊息的畫面
 * @return 無
 * @type void
 */
function closeInfoBar()
{
	try
	{
		var objInfoBar = document.getElementById("__HELP_INFOBAR__");

		if (objInfoBar == null)
		{
			return;
		}

		if (__VAR_TIMERID__ != null)
		{
			clearTimeout(__VAR_TIMERID__);
			__VAR_TIMERID__ = null;
		}

		//objInfoBar.childNodes[2].innerText = "錯誤訊息";
		objInfoBar.getElementsByTagName("font")[0].innerHTML = "錯誤訊息";
		objInfoBar.style.visibility = "hidden";
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("closeInfoBar",arguments,ex));
	}	
}

/**
 * 顯示畫面的遮罩
 * @return 無
 * @type void
 */
function showWindowMask()
{
	try
	{
		var objMask = document.getElementById("__HELP_WINDOWMASK__");
		
		if (objMask == null)
		{
			return;
		}

		objMask.style.width = document.body.scrollWidth;
		objMask.style.height = document.body.scrollHeight;
		objMask.style.visibility = "visible";
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("showWindowMask",arguments,ex));
	}	
}

/**
 * 關閉畫面的遮罩
 * @return 無
 * @type void
 */
function closeWindowMask()
{
	try
	{
		var objMask = document.getElementById("__HELP_WINDOWMASK__");

		if (objMask == null)
		{
			return;
		}

		objMask.style.visibility = "hidden";
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("closeWindowMask",arguments,ex));
	}	
}

/**
 * 顯示資料處理中的畫面
 * @return 無
 * @type void
 */
function showProcessBar()
{
	try
	{
		var objProcessBar = document.getElementById("__HELP_PROCESSBAR__");
		
		if (objProcessBar == null)
		{
			return;
		}

		if (arguments.length == 0 || arguments[0] == null)
		{
			//objProcessBar.childNodes[2].innerText="資料處理中, 請稍待...";
			objProcessBar.getElementsByTagName("font")[0].innerHTML = "資料處理中, 請稍待...";
		}
		else
		{
			//objProcessBar.childNodes[2].innerText=arguments[0];
			objProcessBar.getElementsByTagName("font")[0].innerHTML = arguments[0];
		}
		showWindowMask();
		objProcessBar.style.visibility = "visible";
		objProcessBar.focus();
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("showProcessBar",arguments,ex));
	}	
}

/**
 * 關閉資料處理中的畫面
 * @return 無
 * @type void
 */
function closeProcessBar()
{
	try
	{
		var objProcessBar = document.getElementById("__HELP_PROCESSBAR__");
		
		if (objProcessBar == null)
		{
			return;
		}

		closeWindowMask();
		objProcessBar.style.visibility = "hidden";
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("closeProcessBar",arguments,ex));
	}	
}

/**
 * Sleep函式
 * @return 無
 * @type void
 * @ignore
 */
function Sleep(duration)
{
	var timeout = new Date(new Date().getTime() + duration);
	while (new Date() < timeout){}
}





function display(index,errstr) {//錯誤訊息
  if (index==0)
  {
  	showInfoBar('INFO',errstr);
  }
  else {
  	showInfoBar('INFO',errormsg(index));
  	/*
    errhome.innerText=;
    errhome.style.visibility = "visible";
    setTimeout("errhidden()", 3500);
    */
  }
   
}

//指定frame show 錯誤訊息
function assignErrmsg(frameName,index,dbstr)
{
	window.parent.frames[frameName].showInfoBar('INFO',dbstr);
	/*
	var str1="window.parent."+frameName+".document.all.dbhome.style.visibility='visible'";
	var str2="window.parent."+frameName+".document.all.dbhome.style.visibility='hidden'";
	if (index==0)
	{
		var str="window.parent."+frameName+".document.all.dbhome.innerText='"+dbstr+"'";
		eval(str);
		eval(str1);
		setTimeout(str2, 3500);
	}
	else 
	{
		var str="window.parent."+frameName+".document.all.dbhome.innerText='"+dbmsg(index,dbstr)+"'";
		eval(str);
		eval(str1);
		setTimeout(str2, 3500);
	}
	*/

}

function db_display(index,dbstr) {//資料庫錯誤
 
   
    showInfoBar('FAIL',dbstr);
  
   
}


function dbhidden(){
   dbhome.innerText="資料庫錯誤";
   dbhome.style.visibility = "hidden";
}

function errormsg(index){
  var first=index.substring(0,1);
  switch(first){
     case "1":return err1(index);break;
     case "2":return err2(index);break;
     case "3":return err3(index);break;
     case "4":return err4(index);break;
     case "5":return err5(index);break;
     case "6":return err6(index);break;
     case "7":return err7(index);break;
     default :alert("檢查大項"+first+"不存在");return "檢查大項不存在！";break;
  }
}

//數字類
function err1(index){
   switch(index){
      case "101":return "抱歉！請輸入半形數字！";break;
      default :alert("錯誤代碼"+index+"不存在");return "數字類錯誤！";break;
   }
}

//英文字類
function err2(index){
   switch(index){
      case "201":return "抱歉！請輸入英文字母！";break;
      default :alert("錯誤代碼"+index+"不存在");return "英文字母類錯誤！";break;
   }
}

//E-mail類
function err3(index){
   switch(index){
      case "301":return "抱歉！無效的Email格式！";break;
      default :alert("錯誤代碼"+index+"不存在");return "英文字母類錯誤！";break;
   }
}

//日期類
function err4(index){
   switch(index){
      case "401":return "抱歉！無效的日期格式！";break;
      case "402":return "抱歉！無效的時間格式！";break;
      default :alert("錯誤代碼"+index+"不存在");return "日期類錯誤！";break;
   }
}

//身份證類
function err5(index){
   switch(index){
      case "501":return "抱歉！您輸入的欄位長度不對！";break;
      case "502":return "抱歉！您的身分證號碼不對！";break;
      case "503":return "抱歉！您的公司統一編號不對！";break;
	  case "504":return "抱歉！您的身分證號碼或公司統一編號不對！";break;
      default :alert("錯誤代碼"+index+"不存在");return "身份證字號類錯誤！";break;
   }
}

//必要輸入欄位類
function err6(index){
   switch(index){
      case "601":return "抱歉！這欄位不允許空白！";break;
      default :alert("錯誤代碼"+index+"不存在");return "必要輸入欄位類錯誤！";break;
   }
}
//必要輸入欄位類
function err7(index){
   switch(index){
      case "701":return "抱歉！輸入字串長度過長！";break;
      default :alert("錯誤代碼"+index+"不存在");return "必要輸入欄位類錯誤！";break;
   }
}


function dbmsg(index,dbstr){
   var first=index.substring(0,1);
   switch(first){
     case "1":return db1(index,dbstr);break;
     case "2":return db2(index,dbstr);break;
	 case "3":return db3(index,dbstr);break;
	 case "4":return db4(index,dbstr);break;
     case "5":return db5(index,dbstr);break;
	 case "6":return db6(index,dbstr);break;
     default :alert("檢查大項"+index+"不存在");return "檢查大項不存在！";break;
  }
}

//新增成功
function db1(index,dbstr){
   switch(index){
      case "101":return "成功！新增"+dbstr+"資料庫成功！";break;
      default :alert("錯誤代碼"+index+"不存在");return "新增成功！";break;
   }
}
//新增失敗
function db2(index,dbstr){
   switch(index){
      case "201":return "抱歉！新增"+dbstr+"資料庫失敗！";break;
      default :alert("錯誤代碼"+index+"不存在");return "新增錯誤！";break;
   }
}

//刪除成功
function db3(index,dbstr){
   switch(index){
      case "301":return "成功！刪除"+dbstr+"資料庫成功！";break;
      default :alert("錯誤代碼"+index+"不存在");return "刪除成功！";break;
   }
}
//刪除失敗
function db4(index,dbstr){
   switch(index){
      case "401":return "抱歉！刪除"+dbstr+"資料庫失敗！";break;
      default :alert("錯誤代碼"+index+"不存在");return "刪除失敗！";break;
   }
}

//修改成功
function db5(index,dbstr){
   switch(index){
      case "501":return "成功！修改"+dbstr+"資料庫成功！";break;
      default :alert("錯誤代碼"+index+"不存在");return "修改成功！";break;
   }
}
//修改失敗
function db6(index,dbstr){
   switch(index){
      case "601":return "抱歉！修改"+dbstr+"資料庫失敗！";break;
      default :alert("錯誤代碼"+index+"不存在");return "修改失敗！";break;
   }
}
/*********************error function start*****************************/



