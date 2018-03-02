/** 
 * @fileoverview 定義處理自選欄位的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義處理自選欄位的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function FieldUtil()
{
}

/**
 * 自選欄位開窗
 * @param {Array} fieldArray 自選欄位的定義陣列
 * @param {Object} valueObj 存放已選取之自選欄位的欄位物件
 * @type void
 */
FieldUtil.openFieldDialog=function(fieldArray, valueObj)
{
	try
	{
		var	dialogResult = WindowUtil.openDialog(WindowUtil.getBasePath()+"framework/utility/FieldDialog.html", 600, 400, fieldArray);

		if (!StrUtil.isEmpty(dialogResult) && valueObj != null)
		{
			if (valueObj != null) valueObj.value = dialogResult;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FieldUtil.openFieldDialog",arguments,ex));
	}
}