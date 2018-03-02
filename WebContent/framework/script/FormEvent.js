/** 
 * @fileoverview 定義處理表單事件的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義處理表單事件的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function FormEvent()
{
	/**
	 * @private
	 */
	this.s_=" 1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`~!@#$%^&*()_+|\=-.,/?';\":<>[]};{";
	/**
	 * @private
	 */
	this.t_="　１２３４５６７８９０ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ‵～！＠＃＄％︿＆＊（）＿＋｜＝－。，╱？’；”：＜＞〔〕｝｛";
}

/**
 * 只允許數字型態 (onkeypress)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.onlyAllowNum=function(e)
{
	try
	{
		var obj=e.srcElement;
		var key=e.keyCode;
		
		if((key < 48||key > 57) && key!=13 && (key!=45 || (key==45 && StrUtil.trim(obj.value).length > 0)) && (key!=46 || (key==46 && (obj.value.indexOf(".") != -1 || StrUtil.trim(obj.value).length == 0))) )
		{			
			e.returnValue=false;
			if (obj.document.parentWindow.showInfoBar)
			{
				obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('103'),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.onlyAllowNum",arguments,ex))
	}
}

/**
 * 只允許正數字型態 (onkeypress)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.onlyAllowPostiveNum=function(e)
{
	try
	{
		var obj=e.srcElement;
		var key=e.keyCode;

		if((key < 48||key > 57) && key!=13 && (key!=46 || (key==46 && (obj.value.indexOf(".") != -1 || StrUtil.trim(obj.value).length == 0))) )
		{
			e.returnValue=false;
			if (obj.document.parentWindow.showInfoBar)
			{
				obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('104'),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.onlyAllowNum",arguments,ex))
	}
}

/**
 * 只允許整數型態 (onkeypress)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.onlyAllowNum1=function(e)
{
	try
	{
		var obj=e.srcElement;
		var key=e.keyCode;
		
		if((key < 48||key > 57) && key !=13 && key!=45)
		{
			e.returnValue = false;
			if (obj.document.parentWindow.showInfoBar)
			{
				obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('105'),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
			}
		}
		else if (key == 45)
		{
			if (obj.value.replace(/(^\s*)|(\s*$)/g,"").length > 0)
			{
				e.returnValue=false;
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.onlyAllowNum1",arguments,ex));
	}
}

/**
  *只允許正整數型態 (onkeypress)
  *@return 無
  *@type void
  */
FormEvent.onlyAllowPostiveNum1=function($0_)
{
	try
	{
		var obj=$0_.srcElement;
		var key=$0_.keyCode;
		
		if((key < 48||key > 57) && key !=13)
		{
			$0_.returnValue = false;
			if (obj.document.parentWindow.showInfoBar)
			{
				obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('106'),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.onlyAllowPostiveNum1",arguments,ex));
	}
}

/**
 * 只能輸入英文字母,數字,空白及底線 (onkeydown)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.lockAlphaNum=function(e)
{
	try
	{
		var obj = e.srcElement;
		var key = e.keyCode;
		if(key!=8&&key!=9&&key!=13&&key!=16&&key!=17&&key!=18&&key!=20&&key!=32&&key!=35&&key!=36&&key!=37&&key!=38&&key!=39&&key!=40&&key!=46)
		{
			if( (!e.shiftKey&&key>=48&&key<=57) || (e.shiftKey&&key==189) || (key>=65&&key<=90) || (key>=96&&key<=105))
			{
				return;
			}
			else
			{
				e.returnValue=false;
				if (obj.document.parentWindow.showInfoBar)
				{
					obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('116'),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.lockAlphaNum",arguments,ex));
	}
}

/**
 * 只能貼上英文字母,數字,空白及底線 (onbeforepaste)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.checkClipBoardAlphaNum=function(e)
{
	try
	{
		var obj = e.srcElement;

		if(clipboardData.getData('text').match(/[^a-zA-Z0-9\s_\n\r\t]+/g))
		{
			clipboardData.setData('text', clipboardData.getData('text').replace(/[^a-zA-Z0-9\n\r\t]+/g,""));
			if (obj.document.parentWindow.showInfoBar)
			{
				obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('116'),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.checkClipBoardAlphaNum",arguments,ex));
	}
}

/**
 * 只能輸入英文字母,數字及某些字元 (onkeydown)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.lockAlphaNum1=function(e)
{
	try
	{
		var $50_=e.keyCode;
		
		if($50_ !=9&&$50_ !=8&&$50_ !=46&&$50_ !=35&&$50_ !=36&&$50_ !=37&&$50_ !=39&&$50_ !=13)
		{
			if(e.shiftKey)
			{
				if($50_ !=189&&$50_ !=56&&$50_ !=187)
					e.returnValue=false;
			}
			if(($50_==106||$50_==107||$50_==109||$50_==189||$50_==187)||($50_ >=48&&$50_<=57)||($50_ >=65&&$50_<=90)||($50_ >=96&&$50_<=105))
				return;
			e.returnValue=false;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.lockAlphaNum1",arguments,ex))
	}
}

/**
 * 將值轉換為小寫 (onkeyup)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.lowerCase=function(e)
{
	try
	{
		var key=e.keyCode;
		var obj=e.srcElement;
		
		if(key!=8 && key!=35 && key!=36 && key!=37 && key!=38 && key!=39 && key!=40 && key!=46)
		{
			obj.value=obj.value.toLowerCase();
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.lowerCase",arguments,ex))
	}
}

/**
 * 將值轉換為大寫 (onkeyup)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.upperCase=function(e)
{
	try
	{
		var key = e.keyCode;
		var obj = e.srcElement;
		
		if(key!=8 && key!=35 && key!=36 && key!=37 && key!=38 && key!=39 && key!=40 && key!=46)
		{
			obj.value=obj.value.toUpperCase();
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.upperCase",arguments,ex));
	}
}

/**
 * 依輸入欄位的設定檢查字元數,超過時會予以提示 (onkeypress,onblur) 
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.chkTextMaxLength=function(e)
{
	try
	{
		var obj = e.srcElement;
		
		if(obj.value.replace(/[^\x00-\xff]/ig,"“”").length > obj.maxLength)
		{
			e.returnValue = false;
			if (obj.document.parentWindow.showInfoBar)
			{
				obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('111').replace("@",obj.maxLength),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
			}
			else
			{
				Message.showMessage(Message.getMessage('111').replace("@",obj.maxLength));
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.chkTextMaxLength",arguments,ex));
	}
}

/**
 * 依輸入欄位的設定檢查字元數,超過時會被截掉 (onkeypress,onblur) 
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.setTextMaxLength=function(e)
{
	try
	{
		var obj = e.srcElement;
		
		if(obj.value.replace(/[^\x00-\xff]/ig,"“”").length > obj.maxLength)
		{
			obj.value = obj.value.substr(0,obj.value.length - obj.value.replace(/[^\x00-\xff]/ig,"“”").substring(obj.maxLength).replace(/“”/ig,"字").length);
			e.returnValue = false;
			if (obj.document.parentWindow.showInfoBar)
			{
				//2007.08.31 modified by lewiswang [#148]
				obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('112').replace("@",obj.maxLength/2), WindowUtil.getClientX(obj), WindowUtil.getClientY(obj)+obj.offsetHeight);
			}
		}
		else if (obj.value.replace(/[^\x00-\xff]/ig,"“”").length == obj.maxLength)
		{

			if (e.type == "keypress" && e.keyCode != 13 && obj.document.selection.type == "none")
			{
				e.returnValue = false;
				if (obj.document.parentWindow.showInfoBar)
				{
					//2007.08.31 modified by lewiswang [#148]
					obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('112').replace("@",obj.maxLength/2), WindowUtil.getClientX(obj), WindowUtil.getClientY(obj)+obj.offsetHeight);
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.setTextMaxLength",arguments,ex));
	}
}

/**
 * 依輸入欄位的設定限制能貼上的字元數 (onbeforepaste)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.setClipBoardMaxLength=function(e)
{
	try
	{
		var obj = e.srcElement;
		
		if (clipboardData.getData('text').length != null && clipboardData.getData('text').replace(/[^\x00-\xff]/ig,"“”").length > obj.maxLength - obj.value.replace(/[^\x00-\xff]/ig,"“”").length && obj.document.selection.type == "none")
		{
			clipboardData.setData('text', clipboardData.getData('text').substr(0,clipboardData.getData('text').length - clipboardData.getData('text').replace(/[^\x00-\xff]/ig,"“”").substring(obj.maxLength - obj.value.replace(/[^\x00-\xff]/ig,"“”").length).replace(/“”/ig,"字").length));
			if (obj.document.parentWindow.showInfoBar)
			{
				obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('112').replace("@",obj.maxLength),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.setClipBoardMaxLength",arguments,ex));
	}
}

/**
 * 自動將游標移往下&#x4E00;個欄位 (onkeyup)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.autoTab=function(e)
{
	try
	{
		var obj=e.srcElement;
		var key=e.keyCode;

		if (Form.isButoon(obj) || key==8 || key==9 || key==13 || key==16 || key==35 || key==36 || key==37 || key==38 || key==39 || key==40 || key==46)
		{
			return;
		}
		else
		{
			if(obj.value.replace(/[^\x00-\xff]/ig,"“”").length >= obj.maxLength)
			{
				var $14_ = obj.document.forms;
				var bFocus = false;
	
				for(var i=0;i<$14_.length;i++)
				{
					for(var j=0;j<$14_[i].length;j++)
					{
						if (bFocus == false)
						{
							if($14_[i].elements[j] == obj)
							{
								bFocus = true;
							}
						}
						else
						{
							if (($14_[i].elements[j].getAttribute("tabIndex") == null || $14_[i].elements[j].getAttribute("tabIndex") != "-1") && Form.canFocus($14_[i].elements[j]))
							{
								return;
							}
						}
					}
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.autoTab",arguments,ex));
	}
}

/**
 * 檢核日期格式 (onblur)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.chkDate=function(e)
{
	try
	{
		var obj=e.srcElement;
		
		if(obj.value.replace(/(^\s*)|(\s*$)/g,"").length == 0)
		{
			return;
		}
		else
		{
			if(!CheckUtil.isCDate(StrUtil.fillZero(obj.value,7)) && !CheckUtil.isDate(obj.value))
			{
				if (obj.document.parentWindow.showInfoBar)
				{
					obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('107'),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
				}
				else
				{
					Message.showMessage('107');
				}
				obj.focus();
			}
			else
			{
				if (!CheckUtil.isDate(obj.value))
				{
					obj.value=StrUtil.fillZero(obj.value,7);
				}				
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.chkDate",arguments,ex));
	}
}

/**
 * 檢核時間格式 (onblur)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.chkTime=function(e)
{
	try
	{
		var obj=e.srcElement;
		
		if(obj.value.replace(/(^\s*)|(\s*$)/g,"").length == 0)
		{
			return;
		}
		else
		{
			if(!CheckUtil.isTime(obj.value))
			{
				if (obj.document.parentWindow.showInfoBar)
				{
					obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('108'),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
				}
				else
				{
					Message.showMessage('108');
				}
				obj.focus();
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.chkTime",arguments,ex));
	}
}

/**
 * 依輸入欄位設定的長度,不足長度時在資料前補零 (onblur)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.fillZero=function(e)
{
	try
	{
		var $23_=e.srcElement;
		
		if($23_.value.replace(/(^\s*)|(\s*$)/g,"").length == 0)
		{
			return;
		}
		else
		{
			$23_.value=StrUtil.fillZero($23_.value,$23_.fillZero);
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.fillZero",arguments,ex));
	}
}

/**
 * 轉全形 (onblur)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.toFullStr=function(e)
{
	try
	{
		var $27_=e.srcElement;
		var $28_="";
		
		for(var i=0;i<$27_.value.length;i++)
		{
			var $29_=$27_.value.substr(i,1);
			var $30_=(new FormEvent()).s_.indexOf($29_);
			
			if($30_ !=-1)
			{
				$28_+=(new FormEvent()).t_.substr($30_,1);
			}
			else
			{
				$28_+=$29_;
			}
		}
		
		$27_.value=$28_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.toFullStr",arguments,ex));
	}
}

/**
 * 檢查浮點數型態 (onkeyup)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.checkDecimal=function(e)
{
	try
	{
		var $32_=e.srcElement;
		var $33_=parseInt($32_.decmalLength.split(".")[0]);
		var $34_=parseInt($32_.decmalLength.split(".")[1]);
		var $35_=$32_.value.split(".")[0].length;
		var $36_=($32_.value.indexOf(".")==-1)?0:$32_.value.split(".")[1].length;
		
		if($32_.value.indexOf(".")!=-1)
		{
			if($35_ > $33_)
			{
				$32_.value=$32_.value.split(".")[0].substring(0,$33_)+"."+$32_.value.split(".")[1];
			}
			if($36_ > $34_)
			{
				$32_.value=$32_.value.split(".")[0]+"."+$32_.value.split(".")[1].substring(0,$34_);
			}
		}
		else
		{
			if ($35_ > $33_)
			{
				$32_.value = $32_.value.substring(0,$33_);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.checkDecimal",arguments,ex));
	}
}

/**
 * 檢查數字區間 (onblur)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.numberRngeCheck=function(e)
{
	try
	{
		var obj = e.srcElement;
		
		if(obj.numberRange=="")
			throw new Error(getExceptionStr_("FormEvent.numberRngeCheck",arguments,"尚未設定區間範圍"));
		if(obj.numberRange.indexOf("-")==-1)
			throw new Error(getExceptionStr_("FormEvent.numberRngeCheck",arguments,"區間設定格式錯誤"));
		if(obj.value=="")
			return;

		var $39_=obj.numberRange.split("-");var $40_=($39_[0]=="")?"0":$39_[0];		
		var $41_=($39_[1]=="")?"9999999999999":$39_[1];
		
		if(parseInt(obj.value,10)<$40_||parseInt(obj.value,10)> $41_)
		{
			Message.showMessage(Message.getMessage('117')+" "+$40_+" - "+$39_[1]);
			Form.canFocus(obj);
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.numberRngeCheck",arguments,ex));
	}
}

/**
 * 檢查中文名地型態,有中文字就不轉全形,無就全轉中文 (onblur)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.toFullStrCheck=function(e)
{
	try
	{
		var $43_=e.srcElement;
		
		if(CheckUtil.isBig5($43_.value))
		{
			var $44_="";
			
			for(var i=0;i<$43_.value.length;i++)
			{
				var $45_=$43_.value.substr(i,1);
				var $46_=(new FormEvent()).s_.indexOf($45_);
				
				if($46_ !=-1)
					$44_+=(new FormEvent()).t_.substr($46_,1);
				else $44_+=$45_;
			}
			
			$43_.value=$44_;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.toFullStrCheck",arguments,ex));
	}
}

/**
 * 檢核身分證字號 (onblur)
 * @param {Event} e 被觸發的事件物件
 * @type void
 */
FormEvent.checkID=function(e)
{
	try
	{
		var obj = e.srcElement;
		
		if(obj.value.replace(/(^\s*)|(\s*$)/g,"").length == 0)
		{
			return;
		}
		else
		{
			if(!CheckUtil.isID(obj.value))
			{
				if (obj.document.parentWindow.showInfoBar)
				{
					obj.document.parentWindow.showInfoBar("WARN",Message.getMessage('109'),WindowUtil.getClientX(obj),WindowUtil.getClientY(obj)+obj.offsetHeight);
				}
				else
				{
					Message.showMessage('109');
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("FormEvent.checkID",arguments,ex));
	}
}