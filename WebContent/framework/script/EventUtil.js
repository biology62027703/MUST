/** 
 * @fileoverview �w�q�B�z�ƥ�&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 * @deprecated
 */

/**
 * �w�q�B�z�ƥ�&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 * @deprecated
 */
function EventUtil()
{
}

/**
 * �s�W�ƥ󪺳B�z�禡
 * @param {Event} szEvent �n�B�z���ƥ�
 * @param {Function} szAttachHandler �Өƥ󪺳B�z�禡
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
 * �R���ƥ󪺳B�z�禡
 * @param {Event} szEvent �n�B�z���ƥ�
 * @param {Function} szAttachHandler �Өƥ󪺳B�z�禡
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