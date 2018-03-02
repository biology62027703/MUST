/** 
 * @fileoverview �w�q�B�z�ۿ���쪺&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q�B�z�ۿ���쪺&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function FieldUtil()
{
}

/**
 * �ۿ����}��
 * @param {Array} fieldArray �ۿ���쪺�w�q�}�C
 * @param {Object} valueObj �s��w������ۿ���쪺��쪫��
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