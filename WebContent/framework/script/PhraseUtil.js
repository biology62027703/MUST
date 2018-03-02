/** 
 * @fileoverview �w�q�B�z���y�ΥN�X��&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q�B�z���y�ΥN�X��&#x5171;�Ψ禡Class
 * @constructor
 */
function PhraseUtil()
{
}

/**
 * ���y�}��
 * @param {Array} phraseArray ���y���w�q�}�C
 * @param {Object} inputObj ��J�j�M���y����쪫��
 * @param {Array} valueObj �s��^�Ǥ��y����쪫��}�C
 * @type void
 */
PhraseUtil.openPhraseDialog=function(phraseArray, inputObj, valueObj)
{
	try
	{
		var	dialogResult = WindowUtil.openDialog(WindowUtil.getBasePath()+"framework/utility/PhraseDialog.html", 400, 400, new Array(phraseArray,inputObj,valueObj));
		
		if (dialogResult == undefined) return;

		if (dialogResult != '')
		{
			var	phrasePair = dialogResult.split("--");
	
			inputObj.value = (phrasePair.length > 1)?phrasePair[1]:"";
			if (valueObj != null) valueObj.value = (phrasePair.length > 0)?phrasePair[0]:"";
		}
		else
		{
			inputObj.value = "";
			if (valueObj != null) valueObj.value = "";
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.openPhraseDialog",arguments,ex));
	}
}

/**
 * �h���N�X�}��
 * @param {Array} phraseArray ���y���w�q�}�C
 * @param {Object} inputObj ��J�j�M���y����쪫��
 * @param {Array} valueObj �s��^�Ǥ��y����쪫��}�C
 * @param {String} filterString �L�o���y����r��
 * @param {String} delimeterString ���y�����j�r��
 * @param {Boolean} appendFlag ���[�Ҧ�, �w�]�Ȭ�false
 * @type void
 */
PhraseUtil.openPhrasesDialog=function(phraseArray, inputObj, valueObj, filterString, delimeterString, appendFlag)
{
	try
	{
		if (appendFlag == null)
		{
			appendFlag = false;
		}
		else
		{
			appendFlag = eval(appendFlag);
		}

		var	dialogResult = WindowUtil.openDialog(WindowUtil.getBasePath()+"framework/utility/PhrasesDialog.html", 400, 400, new Array(phraseArray,inputObj,filterString,delimeterString));
		
		if (dialogResult == undefined) return;

		if (delimeterString == null)
		{
			delimeterString = "";
		}

		if (!appendFlag)
		{
			inputObj.value = "";
			if (valueObj != null) valueObj.value = "";
		}

		if (dialogResult != '')
		{
			var resultArray = dialogResult.split(delimeterString);
			
			for (var a = 0 ; a < resultArray.length ; a++)
			{			
				var	phrasePair = resultArray[a].split("--");

				inputObj.value += ((inputObj.value == "")?"":delimeterString) + ((phrasePair.length > 1)?phrasePair[1]:"");
				if (valueObj != null) valueObj.value += ((valueObj.value == "")?"":delimeterString) + ((phrasePair.length > 0)?phrasePair[0]:"");
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.openPhrasesDialog",arguments,ex));
	}
}

/**
 * ���o��J�j�M���y���ҹ��������y
 * @param {Array} phraseArray ���y���w�q�}�C
 * @param {Object} inputObj ��J�j�M���y����쪫��
 * @param {Array} valueObj �s��^�Ǥ��y����쪫��}�C
 * @param {Boolean} searchFlag �O�_�M����y����, �w�]�Ȭ�true
 * @param {Boolean} clearFlag ��䤣����y�ɬO�_�M����J���P�^�����, �w�]�Ȭ�true
 * @param {Boolean} warnFlag ��䤣����y�ɬO�_��ܴ��ܰT��, �w�]�Ȭ�true
 * @type void
 */
PhraseUtil.getPhrase=function(phraseArray, inputObj, valueObj, searchFlag, clearFlag, warnFlag)
{
	try
	{
		if (searchFlag == null)
		{
			searchFlag = true;
		}
		else
		{
			searchFlag = eval(searchFlag);
		}

		if (clearFlag == null)
		{
			clearFlag = true;
		}
		else
		{
			clearFlag = eval(clearFlag);
		}

		if (warnFlag == null)
		{
			warnFlag = true;
		}
		else
		{
			warnFlag = eval(warnFlag);
		}

		if (phraseArray == null)
		{
			if (inputObj.document.parentWindow.showInfoBar)
			{
				inputObj.document.parentWindow.showInfoBar("WARN","�|���]�w���y",WindowUtil.getClientX(inputObj),WindowUtil.getClientY(inputObj)+inputObj.offsetHeight);
			}
			return;
		}
		
		if (inputObj.value == '' || inputObj.readOnly)
		{
			if (clearFlag)
			{
				if (valueObj != null) valueObj.value = "";
			}
			return;
		}

		if (searchFlag)
		{
			for (var i = 2; i < phraseArray.length; i+=2)
			{
				if (phraseArray[i] == inputObj.value || phraseArray[i + 1] == inputObj.value)
				{
					inputObj.value = phraseArray[i + 1];
					if (valueObj != null) valueObj.value = phraseArray[i];
					return;
				}
			}
			
			for (var i = 2; i < phraseArray.length; i+=2)
			{
				if (phraseArray[i].indexOf(inputObj.value) != -1 || phraseArray[i + 1].indexOf(inputObj.value) != -1)
				{
					inputObj.value = phraseArray[i + 1];
					if (valueObj != null) valueObj.value = phraseArray[i];
					return;
				}
			}
		}
		else
		{
			for (var i = 2; i < phraseArray.length; i+=2)
			{
				if (phraseArray[i] == inputObj.value)
				{
					inputObj.value = phraseArray[i + 1];
					if (valueObj != null) valueObj.value = phraseArray[i];
					return;
				}
			}
			
			for (var i = 2; i < phraseArray.length; i+=2)
			{
				if (phraseArray[i].indexOf(inputObj.value) != -1)
				{
					inputObj.value = phraseArray[i + 1];
					if (valueObj != null) valueObj.value = phraseArray[i];
					return;
				}
			}
		}

		if (clearFlag)
		{
			inputObj.value = "";
			if (valueObj != null) valueObj.value = "";
			inputObj.focus();
		}

		if (warnFlag)
		{
			if (inputObj.document.parentWindow.showInfoBar)
			{
				inputObj.document.parentWindow.showInfoBar("WARN","���y���s�b",WindowUtil.getClientX(inputObj),WindowUtil.getClientY(inputObj)+inputObj.offsetHeight);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.getPhrase",arguments,ex));
	}
}

/**
 * �N�X�}��
 * @param {Array} codeArray �N�X���w�q�}�C
 * @param {Object} inputObj ��J�j�M�N�X����쪫��
 * @param {Array} valueObj �s��^�ǥN�X����쪫��}�C
 * @param {String} filterString �L�o�N�X����r��
 * @type void
 */
PhraseUtil.openCodeDialog=function(codeArray, inputObj, valueObj, filterString)
{
	try
	{
		var	dialogResult = WindowUtil.openDialog(WindowUtil.getBasePath()+"framework/utility/CodeDialog.html", 400, 400, new Array(codeArray,inputObj,filterString));
		
		if (dialogResult != undefined)
		{
			if (dialogResult != '')
			{
				var	codePair = dialogResult.split("--");
		
				for (var i = 0; i < valueObj.length; i++)
				{
					if (valueObj[i] != null)
					{
						valueObj[i].value = codePair[i];
					}
				}
				return true;
			}
			else
			{
				for (var i = 0; i < valueObj.length; i++)
				{
					if (valueObj[i] != null)
					{
						valueObj[i].value = "";
					}
				}
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.openCodeDialog",arguments,ex));
	}
}

/**
 * ���o��J�j�M�N�X���ҹ������N�X
 * @param {Array} codeArray �N�X���w�q�}�C
 * @param {Object} inputObj ��J�j�M�N�X����쪫��
 * @param {Array} valueObj �s��^�ǥN�X����쪫��}�C
 * @param {String} filterString �L�o�N�X����r��
 * @param {Boolean} clearFlag ��䤣��N�X�ɬO�_�M����J���P�^�����, �w�]�Ȭ�true
 * @param {Boolean} warnFlag ��䤣����y�ɬO�_��ܴ��ܰT��, �w�]�Ȭ�true
 * @type void
 */
PhraseUtil.getCode=function(codeArray, inputObj, valueObj, filterString, clearFlag, warnFlag)
{
	try
	{
		if (clearFlag == null)
		{
			clearFlag = true;
		}
		else
		{
			clearFlag = eval(clearFlag);
		}

		if (warnFlag == null)
		{
			warnFlag = true;
		}
		else
		{
			warnFlag = eval(warnFlag);
		}

		if (codeArray == null)
		{
			if (inputObj.document.parentWindow.showInfoBar)
			{
				inputObj.document.parentWindow.showInfoBar("WARN","�|���]�w�N�X",WindowUtil.getClientX(inputObj),WindowUtil.getClientY(inputObj)+inputObj.offsetHeight);
			}
			return;
		}
		
		var inputValue = inputObj.value;
		
		if (inputObj.value == '' || inputObj.readOnly)
		{
			if (clearFlag)
			{
				for (var i = 0; i < valueObj.length; i++)
				{
					if (valueObj[i] != null)
					{
						valueObj[i].value = "";
					}
				}
			}
			return;
		}

		var codeArrayLength = parseInt(codeArray[1]);
		var showIndex = codeArray[2].split(',');
		var valueIndex = codeArray[3].split(',');
		var searchIndex = codeArray[4].split(',');

		for (var i = 5; i < codeArray.length; i += codeArrayLength)
		{
			if (filterString != null && filterString.replace(/(^\s*)|(\s*$)/g,"") != "")
			{
				var filterCondition = filterString;
				
				for (var f = 0; f < codeArrayLength; f++)
				{
					filterCondition = filterCondition.replace(eval("/@"+(f+1)+"/g"),"codeArray["+(i+f)+"]");
				}

				if (!eval(filterCondition))
				{
					continue;
				}
			}

			for (var s = 0; s < searchIndex.length; s++)
			{
				if (codeArray[i+parseInt(searchIndex[s])-1] == inputValue)
				{
					for (var o = 0; o < valueObj.length; o++)
					{
						if (valueObj[o] != null)
						{
							valueObj[o].value = codeArray[i+parseInt(valueIndex[o])-1];
						}
					}					
					return;
				}
			}
		}
		
		for (var i = 5; i < codeArray.length; i += codeArrayLength)
		{
			if (filterString != null && filterString.replace(/(^\s*)|(\s*$)/g,"") != "")
			{
				var filterCondition = filterString;
				
				for (var f = 0; f < codeArrayLength; f++)
				{
					filterCondition = filterCondition.replace(eval("/@"+(f+1)+"/g"),"codeArray["+(i+f)+"]");
				}

				if (!eval(filterCondition))
				{
					continue;
				}
			}

			for (var s = 0; s < searchIndex.length; s++)
			{
				if (codeArray[i+parseInt(searchIndex[s])-1].indexOf(inputValue) != -1)
				{
					for (var o = 0; o < valueObj.length; o++)
					{
						if (valueObj[o] != null)
						{
							valueObj[o].value = codeArray[i+parseInt(valueIndex[o])-1];
						}
					}
					return;
				}
			}
		}

		if (clearFlag)
		{
			inputObj.value = "";
			for (var i = 0; i < valueObj.length; i++)
			{
				if (valueObj[i] != null)
				{
					valueObj[i].value = "";
				}
			}
			inputObj.focus();
		}

		if (warnFlag)
		{
			if (inputObj.document.parentWindow.showInfoBar)
			{
				inputObj.document.parentWindow.showInfoBar("WARN","�N�X���s�b",WindowUtil.getClientX(inputObj),WindowUtil.getClientY(inputObj)+inputObj.offsetHeight);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.getCode",arguments,ex));
	}
}

/**
 * ���ͥN�X���U�Ԧ����
 * @param {Array} codeArray �N�X���w�q�}�C
 * @param {Object} selectObj �U�Ԧ���檺��쪫��
 * @param {String} filterString �L�o�N�X����r��
 * @param {Boolean} resetFlag ���ͫe���M���Ҧ������
 * @type void
 */
PhraseUtil.genCodeOption=function(codeArray, selectObj, filterString, resetFlag)
{
	try
	{
		if (resetFlag == null)
		{
			resetFlag = true;
		}
		else
		{
			resetFlag = eval(resetFlag);
		}

		if (codeArray == null)
		{
			if (selectObj.document.parentWindow.showInfoBar)
			{
				selectObj.document.parentWindow.showInfoBar("WARN","�|���]�w�N�X",WindowUtil.getClientX(inputObj),WindowUtil.getClientY(inputObj)+inputObj.offsetHeight);
			}
			return;
		}
		
		if (resetFlag)
		{
			selectObj.length = 0;
		}
		
		var codeArrayLength = parseInt(codeArray[1]);
		var showIndex = codeArray[2].split(',');
		var valueIndex = codeArray[3].split(',');

		for (var i = 5; i < codeArray.length; i += codeArrayLength)
		{
			if (filterString != null && filterString.replace(/(^\s*)|(\s*$)/g,"") != "")
			{
				var filterCondition = filterString;
				
				for (var f = 0; f < codeArrayLength; f++)
				{
					filterCondition = filterCondition.replace(eval("/@"+(f+1)+"/g"),"codeArray["+(i+f)+"]");
				}

				if (!eval(filterCondition))
				{
					continue;
				}
			}

			var	optionText	= '';
			var	optionValue	= '';

			for (var s = 0; s < showIndex.length; s++)
			{
				optionText += ((optionText == '')?'':'  ')+codeArray[i+parseInt(showIndex[s])-1];
			}

			for (var v = 0; v < valueIndex.length; v++)
			{
				optionValue += ((optionValue == '')?'':'--')+codeArray[i+parseInt(valueIndex[v])-1];
			}
			
			var newOption = selectObj.document.createElement("OPTION");
			newOption.text = optionText;
			newOption.value = optionValue;
			selectObj.add(newOption);
		}
		
		if (selectObj.length > 0)
		{
			selectObj.selectedIndex = 0;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.genCodeOption",arguments,ex));
	}
}

/**
 * ���ͥN�X���U�Ԧ���������ȻP��ܦr�ꤣ�P
 * @param {Array} codeArray �N�X���w�q�}�C
 * @param {Object} selectObj �U�Ԧ���檺��쪫��
 * @param {String} filterString �L�o�N�X����r��
 * @param {Boolean} resetFlag ���ͫe���M���Ҧ������
 * @type void
 * @author Evan Chen
 */
PhraseUtil.genCodeOptionWithDiffValues=function(codeArray, selectObj, filterString, resetFlag)
{
	try
	{
		if (resetFlag == null)
		{
			resetFlag = true;
		}
		else
		{
			resetFlag = eval(resetFlag);
		}

		if (codeArray == null)
		{
			if (selectObj.document.parentWindow.showInfoBar)
			{
				selectObj.document.parentWindow.showInfoBar("WARN","�|���]�w�N�X",WindowUtil.getClientX(inputObj),WindowUtil.getClientY(inputObj)+inputObj.offsetHeight);
			}
			return;
		}
		
		if (resetFlag)
		{
			selectObj.length = 0;
		}
		
		var codeArrayLength = parseInt(codeArray[1]);
		var showIndex = codeArray[2].split(',');
		var valueIndex = codeArray[3].split(',');

		for (var i = 5; i < codeArray.length; i += codeArrayLength)
		{
			if (filterString != null && filterString.replace(/(^\s*)|(\s*$)/g,"") != "")
			{
				var filterCondition = filterString;
				
				for (var f = 0; f < codeArrayLength; f++)
				{
					filterCondition = filterCondition.replace(eval("/@"+(f+1)+"/g"),"codeArray["+(i+f)+"]");
				}

				if (!eval(filterCondition))
				{
					continue;
				}
			}

			var	optionText	= '';
			var	optionValue	= '';

			for (var s = 0; s < showIndex.length; s++)
			{
				var ots = codeArray[i+parseInt(showIndex[s])-1].split("{STRING.SEPARATOR}");

				// [0]{OPTION_TEXT} + {STRING.SEPARATOR} + [1]{OPTION_VALUE}
				optionText += ((optionText == '')?'':'  ')+ots[0];
			}

			for (var v = 0; v < valueIndex.length; v++)
			{
				var ovs = codeArray[i+parseInt(valueIndex[v])-1].split("{STRING.SEPARATOR}");

				// [0]{OPTION_TEXT} + {STRING.SEPARATOR} + [1]{OPTION_VALUE}
				optionValue += ((optionValue == '')?'':'--')+ovs[1];
			}
			// //alert(optionText + " : " + optionValue);
			
			var newOption = selectObj.document.createElement("OPTION");
			newOption.text = optionText;
			newOption.value = optionValue;
			selectObj.add(newOption);
		}
		
		if (selectObj.length > 0)
		{
			selectObj.selectedIndex = 0;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.genCodeOption",arguments,ex));
	}
}

/**
 * �h���N�X�}��
 * @param {Array} codeArray �N�X���w�q�}�C
 * @param {Object} inputObj ��J�j�M�N�X����쪫��
 * @param {Array} valueObj �s��^�ǥN�X����쪫��}�C
 * @param {String} filterString �L�o�N�X����r��
 * @param {String} delimeterString �N�X�����j�r��
 * @param {Boolean} appendFlag ���[�Ҧ�, �w�]�Ȭ�false
 * @type void
 */
PhraseUtil.openCodesDialog=function(codeArray, inputObj, valueObj, filterString, delimeterString, appendFlag)
{
	try
	{
		if (appendFlag == null)
		{
			appendFlag = false;
		}
		else
		{
			appendFlag = eval(appendFlag);
		}

		var	dialogResult = WindowUtil.openDialog(WindowUtil.getBasePath()+"framework/utility/CodesDialog.html", 400, 400, new Array(codeArray,inputObj,filterString,delimeterString));
		
		if (dialogResult == undefined) return;

		if (delimeterString == null)
		{
			delimeterString = "";
		}

		if (!appendFlag)
		{
			for (var i = 0; i < valueObj.length; i++)
			{
				valueObj[i].value = "";
			}
		}

		if (dialogResult != '')
		{
			var resultArray = dialogResult.split(delimeterString);
			
			for (var a = 0 ; a < resultArray.length ; a++)
			{
				var	codePair = resultArray[a].split("--");
				
				for (var i = 0; i < valueObj.length; i++)
				{
					var isRepeat = false;
					var valueObjAry = valueObj[i].value.split(delimeterString);
					for (var v = 0; v < valueObjAry.length; v++)
					{
						if(valueObjAry[v] == codePair[i]){
							isRepeat = true;
							break;
						}
					}
					
					if(!isRepeat)
					{
						valueObj[i].value += ((valueObj[i].value == "")?"":delimeterString) + codePair[i];
					}
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.openCodesDialog",arguments,ex));
	}
}

/**
 * ���o�h����J�j�M�N�X���ҹ������N�X
 * @param {Array} codeArray �N�X���w�q�}�C
 * @param {Object} inputObj ��J�j�M�N�X����쪫��
 * @param {Array} valueObj �s��^�ǥN�X����쪫��}�C
 * @param {String} filterString �L�o�N�X����r��
 * @param {String} delimeterString �N�X�����j�r��
 * @param {Boolean} clearFlag ��䤣��N�X�ɬO�_�M����J���P�^�����, �w�]�Ȭ�true
 * @param {Boolean} warnFlag ��䤣����y�ɬO�_��ܴ��ܰT��, �w�]�Ȭ�true
 * @type void
 */
PhraseUtil.getCodes=function(codeArray, inputObj, valueObj, filterString, delimeterString, clearFlag, warnFlag)
{
	try
	{
		if (clearFlag == null)
		{
			clearFlag = true;
		}
		else
		{
			clearFlag = eval(clearFlag);
		}

		if (warnFlag == null)
		{
			warnFlag = true;
		}
		else
		{
			warnFlag = eval(warnFlag);
		}

		if (delimeterString == null)
		{
			delimeterString = "";
		}

		if (codeArray == null)
		{
			if (inputObj.document.parentWindow.showInfoBar)
			{
				inputObj.document.parentWindow.showInfoBar("WARN","�|���]�w�N�X",WindowUtil.getClientX(inputObj),WindowUtil.getClientY(inputObj)+inputObj.offsetHeight);
			}
			return;
		}
		
		var inputArray = inputObj.value.split(delimeterString);

		if (inputObj.value == '' || inputObj.readOnly)
		{
			for (var i = 0; i < valueObj.length; i++)
			{
				valueObj[i].value = "";
			}
			return;
		}
		else
		{
			for (var i = 0; i < valueObj.length; i++)
			{
				valueObj[i].value = "";
			}
		}

		var codeArrayLength = parseInt(codeArray[1]);
		var showIndex = codeArray[2].split(',');
		var valueIndex = codeArray[3].split(',');
		var searchIndex = codeArray[4].split(',');

		for (var a = 0 ; a < inputArray.length ; a++)
		{
			inputArray[a] = inputArray[a].replace(/(^\s*)|(\s*$)/g,"");
			
			if (inputArray[a] == "") continue;
						
			var inputFound = false;

			for (var i = 5; i < codeArray.length; i += codeArrayLength)
			{
				if (filterString != null && filterString.replace(/(^\s*)|(\s*$)/g,"") != "")
				{
					var filterCondition = filterString;
					
					for (var f = 0; f < codeArrayLength; f++)
					{
						filterCondition = filterCondition.replace(eval("/@"+(f+1)+"/g"),"codeArray["+(i+f)+"]");
					}
	
					if (!eval(filterCondition))
					{
						continue;
					}
				}
				
				for (var s = 0; s < searchIndex.length; s++)
				{
					if (codeArray[i+parseInt(searchIndex[s])-1] == inputArray[a])
					{
						for (var o = 0; o < valueObj.length; o++)
						{
							valueObj[o].value += ((valueObj[o].value == "")?"":delimeterString) + codeArray[i+parseInt(valueIndex[o])-1];
						}
						inputFound = true;
						break;
					}
				}

				if (inputFound) break;
			}
			
			for (var i = 5; i < codeArray.length && !inputFound; i += codeArrayLength)
			{
				if (filterString != null && filterString.replace(/(^\s*)|(\s*$)/g,"") != "")
				{
					var filterCondition = filterString;
					
					for (var f = 0; f < codeArrayLength; f++)
					{
						filterCondition = filterCondition.replace(eval("/@"+(f+1)+"/g"),"codeArray["+(i+f)+"]");
					}
	
					if (!eval(filterCondition))
					{
						continue;
					}
				}
				
				for (var s = 0; s < searchIndex.length; s++)
				{
					if (codeArray[i+parseInt(searchIndex[s])-1].indexOf(inputArray[a]) != -1)
					{
						for (var o = 0; o < valueObj.length; o++)
						{
							valueObj[o].value += ((valueObj[o].value == "")?"":delimeterString) + codeArray[i+parseInt(valueIndex[o])-1];
						}
						inputFound = true;
						break;
					}
				}
	
				if (inputFound) break;				
			}

			if (!inputFound)
			{
				if (!clearFlag)
				{
					inputObj.value += ((inputObj.value == "")?"":delimeterString) + inputArray[a];
					/*
					for (var o = 0; o < valueObj.length; o++)
					{
						valueObj[o].value += ((valueObj[o].value == "")?"":delimeterString) + inputArray[a];
					}
					*/
				}

				if (warnFlag)
				{
					if (inputObj.document.parentWindow.showInfoBar)
					{
						inputObj.document.parentWindow.showInfoBar("WARN","�����N�X���s�b",WindowUtil.getClientX(inputObj),WindowUtil.getClientY(inputObj)+inputObj.offsetHeight);
					}
				}
			}
		}		
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.getCode",arguments,ex));
	}
}

/**
 * �i��ƶ��}��
 * @param {Array} mainArray �i��ƶ����w�q�}�C
 * @param {Array} subArray �u&#x4F5C;���ت��w�q�}�C
 * @param {Array} valueObj �s��^�ǥN�X����쪫��}�C
 * @param {String} filterString �L�o���ت�����r��
 * @param {String} defaultMain �i��ƶ����w�]������إN�X
 * @param {String} defaultSub  �u&#x4F5C;���ت��w�]������إN�X
 * @type void
 */
PhraseUtil.openTaskDialog=function(mainArray, subArray, valueObj, filterString, defaultMain, defaultSub)
{
	try
	{
		var	dialogResult = WindowUtil.openDialog(WindowUtil.getBasePath()+"framework/utility/TaskDialog.html", 900, 400, new Array(mainArray,subArray,filterString,defaultMain,defaultSub));
		
		if (dialogResult != undefined)
		{
			if (valueObj != null)
			{
				for (var i = 0; i < valueObj.length; i++)
				{
					valueObj[i].value = "";
				}
				if (dialogResult != '')
				{
					var	codePair = dialogResult.split("--");
			
					for (var i = 0; i < codePair.length; i++)
					{
						valueObj[i].value = codePair[i];
					}
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.openTaskDialog",arguments,ex));
	}
}

/**
 * ���o���y�}�C
 * @param {String} code ���y�N�X
 * @param {String} param[0..N] ���y�Ѽ�
 * @type void
 */
PhraseUtil.getCodeArray=function(code)
{
	try
	{
		var param = "code="+encodeURIComponent(Base64.encode(code));
		
		for (i = 1 ; i < arguments.length ; ++i)
		{
			if (arguments[i].indexOf("=") != -1)
			{
				param += "&"+arguments[i].substring(0,arguments[i].indexOf("=")+1)+encodeURIComponent(Base64.encode(arguments[i].substring(arguments[i].indexOf("=")+1)));
			}
		}

		var data = Assist.postRemoteData('./framework/utility/FetchCode.jsp',param);
		if(!StrUtil.isEmpty(data))
		{
			eval(data);
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("PhraseUtil.getCodeArray",arguments,ex));
	}
}