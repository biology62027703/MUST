/** 
 * @fileoverview 定義處理事件的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 * @deprecated
 */

/**
 * 定義處理事件的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 * @deprecated
 */
function EventUtil()
{
}

/**
 * 新增事件的處理函式
 * @param {Event} szEvent 要處理的事件
 * @param {Function} szAttachHandler 該事件的處理函式
 * @type void
 * @deprecated
 */
EventUtil.attachEvent = function(szEvent, szAttachHandler)
{
	var szOldHandler;
	var szNewHandler;

	eval("if (" + szEvent + ") { szOldHandler = " + szEvent + ".toString();} else { szOldHandler = \"\"; }");

	szAttachHandler = szAttachHandler.replace(/[\s;]*$/g,"");

	if (szOldHandler != "")
	{
		// element.event = handler;
		szOldHandler = szOldHandler.replace(/[\n\r\t]/g,"").replace(/^function ([^\(]+\(\))(\{.*)/g,"$1");
		// attachEventEx(element.event, handler);
		szOldHandler = szOldHandler.replace(/^\s*function\s*\(\s*\)\s*{(.*)}\s*/g,"$1");
		szOldHandler = szOldHandler.replace(/[\s;]*$/g,"");
		szNewHandler = "function(){" + szOldHandler + ";" + szAttachHandler + ";}";
	}
	else
	{
		szNewHandler = "function(){" + szAttachHandler + ";}";
	}

	eval(szEvent + " = " + szNewHandler + ";");
}

/**
 * 刪除事件的處理函式
 * @param {Event} szEvent 要處理的事件
 * @param {Function} szAttachHandler 該事件的處理函式
 * @type void
 * @deprecated
 */
EventUtil.detachEvent = function(szEvent, szDetachHandler)
{
	var szOldHandler;
	var szNewHandler;

	eval("if (" + szEvent + ") { szOldHandler = " + szEvent + ".toString();} else { szOldHandler = \"\"; }");

	if (szOldHandler != "")
	{
		szDetachHandler = szDetachHandler.replace(/[\s;]*$/g,"");
		
		eval("szNewHandler = szOldHandler.replace(/" + szDetachHandler.replace(/\(/g,"\\(").replace(/\)/g,"\\)") + ";/g,\"\");");

		if (szNewHandler.replace(/\s/g,"") == "function(){}")
		{
			szNewHandler = null;
		}

		eval(szEvent + " = " + szNewHandler + ";");
	}
}