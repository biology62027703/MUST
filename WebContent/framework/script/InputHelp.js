/** 
 * @fileoverview �w�q�B�z���U����J��&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q�B�z���U����J��&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function InputHelp()
{
}

/**
 * �b���k�贡�J����~��(YYY)����J�u��
 * @param {Object} inputObj �s�񤤤�~������쪫��
 * @return �L
 * @type void
 */
InputHelp.insertYearControl=function(inputObj)
{
	try
	{
		var html = "<span style=\"width: 20px;height:32px;vertical-align: top;overflow: hidden;margin:0px 5px 0px 5px;\">"+
					"<div style=\"width: 20px; height: 16px; overflow: hidden; text-align: center; vertical-align: middle; font:normal normal 11pt '�ө���'; background-color: #D4D0C8; border-top:solid 1px #FFFFFF; border-left:solid 1px #FFFFFF; border-right:solid 1px #404040; border-bottom:solid 1px #404040;cursor: pointer\" onmousedown=\"this.style.borderColor='#808080'\" onmouseup=\"this.style.borderLeftColor='#FFFFFF';this.style.borderTopColor='#FFFFFF';this.style.borderRightColor='#404040';this.style.borderBottomColor='#404040';\" onclick=\"InputHelp.modifyYearControl(this,1);\">��</div>"+
					"<div style=\"width: 20px; height: 16px; overflow: hidden; text-align: center; vertical-align: middle; font:normal normal 11pt '�ө���'; background-color: #D4D0C8; border-top:solid 1px #FFFFFF; border-left:solid 1px #FFFFFF; border-right:solid 1px #404040; border-bottom:solid 1px #404040;cursor: pointer\" onmousedown=\"this.style.borderColor='#808080'\" onmouseup=\"this.style.borderLeftColor='#FFFFFF';this.style.borderTopColor='#FFFFFF';this.style.borderRightColor='#404040';this.style.borderBottomColor='#404040';\" onclick=\"InputHelp.modifyYearControl(this,-1);\">��</div>"+
					"</span>";
		inputObj.insertAdjacentHTML("afterEnd",html);

		var year = new Date().getYear()-1911;
		inputObj.value = "000".substr(0,3-year.toString().length)+year.toString();
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("InputHelp.addYearControl",arguments,ex))
	}
}

/**
 * @private
 */
InputHelp.modifyYearControl=function(controlObj,modifyAmount)
{
	try
	{
		var year = parseInt(controlObj.parentNode.previousSibling.value.replace(/^0*/g,''));

		year = isNaN(year)?(new Date().getYear()-1911):(year + parseInt(modifyAmount));

		year = (year < 0)?0:year;

		controlObj.parentNode.previousSibling.value = "000".substr(0,3-year.toString().length)+year.toString();
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("InputHelp.modifyYearControl",arguments,ex))
	}
}

/**
 * �b���k�贡�J�������J�u��
 * @param {Object} inputObj �s��������쪫��
 * @param {String} dateFormat �^�Ǫ�����榡
 * @return �L
 * @type void
 */
InputHelp.insertDateControl=function(inputObj,dateFormat)
{
	try
	{
		var html = "<img src='"+WindowUtil.getBasePath()+"/image/calendar.gif' style=\"cursor:pointer;width:32px;height:32px;vertical-align:middle;\" onclick=\"InputHelp.openCalendarDialog(this,'"+dateFormat+"');\">";
		inputObj.insertAdjacentHTML("afterEnd",html);
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("InputHelp.insertDateControl",arguments,ex))
	}
}

/**
 * @private
 */
InputHelp.openCalendarDialog=function(controlObj,dateFormat)
{
	try
	{
		var inputObj = controlObj.previousSibling;

		var iLeft = (((WindowUtil.getClientX(inputObj)+240) > inputObj.document.body.offsetWidth)?(inputObj.document.body.offsetWidth-240):WindowUtil.getClientX(inputObj));
		var iTop = inputObj.document.parentWindow.screenTop+WindowUtil.getClientY(inputObj)+inputObj.offsetHeight;

		var	dialogResult = inputObj.document.parentWindow.showModalDialog(WindowUtil.getBasePath()+"framework/utility/Calendar.html",(StrUtil.isEmpty(inputObj.value)?null:DateUtil.convert2Date(inputObj.value).replace(/\//g,"")),"dialogWidth:240px;dialogHeight:300px;dialogLeft:"+iLeft.toString()+"px;dialogTop:"+iTop.toString()+"px;center:0;scroll:0;help:0;status:0;");
		
		if (dialogResult == undefined) return;

		if (dialogResult != '')
		{
			var	DateObj = dialogResult.split("--");
	
			dateFormat = dateFormat.replace("YYYY",StrUtil.fillStr(DateObj[0],4,'0'));
			dateFormat = dateFormat.replace("yyyy",DateObj[0]);
			dateFormat = dateFormat.replace("YYY",StrUtil.fillStr((parseInt(DateObj[0],10)-1911).toString(),3,'0'));
			dateFormat = dateFormat.replace("yyy",(parseInt(DateObj[0],10)-1911).toString());
			dateFormat = dateFormat.replace("MM",StrUtil.fillStr(DateObj[1],2,'0'));
			dateFormat = dateFormat.replace("mm",DateObj[1]);
			dateFormat = dateFormat.replace("DD",StrUtil.fillStr(DateObj[2],2,'0'));
			dateFormat = dateFormat.replace("dd",DateObj[2]);

			inputObj.value = dateFormat;
		}
		else
		{
			inputObj.value = "";
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("InputHelp.openCalendarDialog",arguments,ex));
	}
}

/**
 * �b���k�贡�J�ɶ�����J�u��
 * @param {Object} inputObj �s��ɶ�����쪫��
 * @param {String} timeFormat �^�Ǫ��ɶ��榡
 * @return �L
 * @type void
 */
InputHelp.insertTimeControl=function(inputObj,timeFormat)
{
	try
	{
		var html = "<img src='"+WindowUtil.getBasePath()+"/image/clock.gif' style=\"cursor:pointer;width:32px;height:32px;vertical-align:middle;\" onclick=\"InputHelp.openClockDialog(this,'"+timeFormat+"');\">";
		inputObj.insertAdjacentHTML("afterEnd",html);
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("InputHelp.insertTimeControl",arguments,ex))
	}
}

/**
 * @private
 */
InputHelp.openClockDialog=function(controlObj,timeFormat)
{
	try
	{
		var inputObj = controlObj.previousSibling;

		var iLeft = (((WindowUtil.getClientX(inputObj)+240) > inputObj.document.body.offsetWidth)?(inputObj.document.body.offsetWidth-240):WindowUtil.getClientX(inputObj));
		var iTop = inputObj.document.parentWindow.screenTop+WindowUtil.getClientY(inputObj)+inputObj.offsetHeight;

		var	dialogResult = inputObj.document.parentWindow.showModalDialog(WindowUtil.getBasePath()+"framework/utility/Clock.html",(StrUtil.isEmpty(inputObj.value)?null:inputObj.value),"dialogWidth:240px;dialogHeight:150px;dialogLeft:"+iLeft.toString()+"px;dialogTop:"+iTop.toString()+"px;center:0;scroll:0;help:0;status:0;");
		
		if (dialogResult == undefined) return;

		if (dialogResult != '')
		{
			var	DateObj = dialogResult.split("--");
	
			timeFormat = timeFormat.replace("HH",StrUtil.fillStr(DateObj[0],2,'0'));
			timeFormat = timeFormat.replace("hh",DateObj[0]);
			timeFormat = timeFormat.replace("MM",StrUtil.fillStr(DateObj[1],2,'0'));
			timeFormat = timeFormat.replace("mm",DateObj[1]);

			inputObj.value = timeFormat;
		}
		else
		{
			inputObj.value = "";
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("InputHelp.openClockDialog",arguments,ex));
	}
}