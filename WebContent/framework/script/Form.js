/** 
 * @fileoverview 定義處理表單的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */
 
/**
 * 定義處理表單的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function Form()
{
}

/**
 * @private
 */
Form.document_=document;
/**
 * @private
 */
Form.errMessage=null;
/**
 * 定義當欄位檢核未通過時的背景顏色
 * @type String
 */
Form.bgcolorError="#FFCCCC";
/**
 * 定義當欄位檢核通過時的背景顏色
 * @type String
 */
Form.bgcolorNormal="#FFFFFF";

/**
 * 設定處理表單所屬的document
 * @param {document} doc 要進行表單處理的document物件, i.e. parent.queryFrame.document
 * @type void
 */
Form.setDocument=function(doc)
{
	Form.document_ = doc;
}

/**
 * 取得目前處理表單所屬的document
 * @return 目前處理表單所屬的document物件
 * @type document
 */
Form.getDocument=function()
{
	return Form.document_;
}

/**
 * 累計表單的檢核錯誤訊息 
 * @param {String} message 錯誤訊息
 * @type void
 */
Form.errAppend=function(message)
{
	try
	{
		if (Form.errMessage==null)
		{
			Form.errMessage=new StringBuffer();
		}
		Form.errMessage.append(message+"<br>");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.errAppend",arguments,ex));
	}
}

/**
 * 顯示表單的累計錯誤訊息視窗
 * @type void
 */
Form.showErrMessage=function()
{
	try
	{
		var objWindow = (Form.document_ == null || Form.document_.parentWindow == null)?self:Form.document_.parentWindow;
		objWindow.WindowUtil.openFormcheckDialog(escape(Form.errMessage.toString()));
		Form.errMessage=new StringBuffer();
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.showErrMessage",arguments,ex));
	}
}

/**
  *判斷是否游標能停在此 Input, 若可以, 將游標停在此物件上
  *@param {Object} obj 欄位元件 
  *@return boolean
  *@type boolean
  */
Form.canFocus=function(obj)
{
	try
	{
		if(obj.readOnly || obj.disabled || obj.type=='hidden')
		{
			return false;
		}
		else
		{
			obj.focus();
			obj.select();
			return true;
		}
	}
	catch(ex)
	{
		return false;
	}
}

/**
 * 判斷是否為按鈕物件 
 * @param {Object} obj 欄位物件
 * @return true,如果物件的型態為button,submit或cancel; false,如果物件的型態非上述所列
 * @type Boolean
 */
Form.isButoon=function(obj)
{
	try
	{
		if(obj.type=='button' || obj.type=='submit' || obj.type=='cencel')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.isButoon",arguments,ex));
	}
}

/**
 * 取得欄位的值
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @param {String/Number} elementIndex 欄位的名稱或是索引值
 * @return 欄位的字串值
 * @type String
 */
Form.getInput=function(formIndex,elementIndex)
{
	try
	{
		var $8_=Form.getDocument().forms[formIndex].elements[elementIndex];

		if(isNaN($8_.length))
		{
			if($8_.type=='checkbox'||$8_.type=='radio')
			{
				if($8_.checked)
				{
					return $8_.value;
				}
			}
			else
			{
				return $8_.value;
			}
		}
		else
		{
			if ($8_.type=='select-one' || $8_.type=='select-multiple')
			{
				for(var i=0;i<$8_.length;i++)
				{
					if($8_[i].selected)
					{
						return $8_[i].value;
					}
				}
			}
			else
			{
				for(var i=0;i<$8_.length;i++)
				{
					if($8_[i].type=='checkbox'||$8_[i].type=='radio')
					{
						if($8_[i].checked)
						{
							return $8_[i].value;
						}
					}
					else
					{
						return $8_[i].value;
					}
				}
			}
		}
		return "";
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.getInput",arguments,ex));
	}
}

/**
 * 取得相同欄位名稱的值
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @param {String/Number} elementIndex 欄位的名稱或是索引值
 * @param {Number} valueIndex 欄位值的索引值
 * @return 欄位的字串值
 * @type String
 */
Form.getInputs=function(formIndex,elementIndex,valueIndex)
{
	try
	{
		var $12_=Form.getDocument().forms[formIndex].elements[elementIndex];

		if(isNaN($12_.length))
		{
			return $12_.value;
		}
		else
		{
			return $12_[valueIndex].value;
		}		
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.getInputs",arguments,ex));
	}
}

/**
 * 設定欄位的值
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @param {String/Number} elementIndex 欄位的名稱或是索引值
 * @param {String} str 要設定的值
 * @return 無
 * @type void
 */
Form.setInput=function(formIndex,elementIndex,str)
{
	alert("f");
	try
	{
		var $8_=Form.getDocument().forms[(formIndex==null)?0:formIndex].elements[elementIndex];

		if(isNaN($8_.length) || $8_.type=='select-one' || $8_.type=='select-multiple')
		{
			if ($8_.type=='checkbox' || $8_.type=='radio')
			{
				if(str.indexOf($8_.value) != -1)
				{
					$8_.checked = true;
				}
				else
				{
					$8_.checked = false;
				}
			}
			else if ($8_.type == 'select-multiple')
			{
				for(var i=0 ; i < $8_.length ; i++)
				{
					if(str.indexOf($8_.options[i].value) != -1)
					{
						$8_.options[i].selected = true;
					}
					else
					{
						$8_.options[i].selected = false;
					}
				}
			}
			else
			{
				$8_.value = str;
			}
		}
		else
		{
			for(var i=0 ; i < $8_.length ; i++)
			{
				if ($8_[i].type=='checkbox' || $8_[i].type=='radio')
				{
					if(str.indexOf($8_[i].value) != -1)
					{
						$8_[i].checked = true;
					}
					else
					{
						$8_[i].checked = false;
					}
				}
				else
				{
					$8_[i].value = str;
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.setInput",arguments,ex));
	}
}

/**
 * 檢核表單欄位,並將錯誤訊息累計至 Form.errMessage
 *
 * <pre>
 *
 * 檢核參數說明:
 * AC(3) --> 自訂欄位檢查函式 ['AC',doCheckFunction,'錯誤訊息']
 * MC    --> 檢查欄位是否超過設定的最大長度
 * E     --> 檢查欄位是否為空值
 * I     --> 檢查欄位是否符合身分證字號格式
 * N     --> 檢查欄位是否為數值型態
 * N+    --> 檢查欄位是否為正數值型態
 * DT    --> 檢查欄位是否符合中文日期(YYYMMDD)格式
 * N1    --> 檢查欄位是否為整數值型態
 * N1+   --> 檢查欄位是否為正整數值型態
 * NE(2) --> 檢查是否至少有&#x4E00;個欄位不為空值 ['NE',new Array('FIELD1','FIELD2')]
 * TM    --> 檢查欄位是否符合時間(HHMM)格式
 * YM    --> 檢查欄位是否符合中文年月(YYYMM)格式
 * YY    --> 檢查欄位是否符合中文年份(YYY)格式
 * F     --> 檢查欄位時將欄位值左方補滿0至設定的長度
 *
 * 使用範例: Form.chkForm('form1', 'rcvdate', '接收日期', 'DT','MC'); //檢核總長度及日期格式
 * </pre>
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @param {String/Number} elementIndex 欄位的名稱或是索引值
 * @param {String} elementName 欄位的名稱描述
 * @param {String} parameter 欄位檢核的參數
 * @return 無
 * @type void
 */
Form.chkForm=function(formIndex,elementIndex,elementName,parameter)
{
	try
	{
		var $20_= Form.getInput(formIndex,elementIndex);
		var obj = Form.getDocument().forms[formIndex].elements[elementIndex];
		var bCheckPassed = true;

		for(var i=3;i<arguments.length;i++)
		{			
			switch((arguments[i]+'').toUpperCase())
			{
				case 'AC':
					if (arguments[i+1] && !arguments[i+1]())
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+arguments[i+2]);
					}
					i = i + 2;
					break;
				case 'MC':
					if(StrUtil.getBLen($20_)> obj.maxLength)
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('111').replace("@",obj.maxLength));
					}
					break;
				case 'E':
					if(StrUtil.isEmpty($20_))
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('101'));
					}
					break;
				case 'F':
					if(StrUtil.isEmpty(obj.fillZero))
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')參數設定錯誤');
					}
					else
					{
						if (!StrUtil.isEmpty(obj.value))
						{
							obj.value=StrUtil.fillZero(obj.value,obj.fillZero);
						}
					}
					break;
				case 'I':
					if(!StrUtil.isEmpty($20_) && !CheckUtil.isID($20_))
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('110'));
					}
					break;
				case 'N':
					if(isNaN($20_))
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('103'));
					}
					break;
				case 'N+':
					if(isNaN($20_) || $20_ < 0)
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('104'));
					}
					break;
				case 'N1':
					if(isNaN($20_) || $20_.indexOf(".") != -1)
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('105'));
					}
					break;
				case 'N1+':
					if(isNaN($20_) || $20_ < 0 || $20_.indexOf(".") != -1)
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('106'));
					}
					break;
				case 'NE':
					do
					{
						var bEmpty = true;
						obj = new Array();
						for (var f = 0 ; f < arguments[i+1].length ; f++)
						{
							obj.push(Form.getDocument().forms[formIndex].elements[arguments[i+1][f]]);
							if(!StrUtil.isEmpty(Form.getInput(formIndex,arguments[i+1][f])))
							{
								bEmpty = false;
								break;
							}
						}
						if (bEmpty)
						{
							bCheckPassed = false;
							Form.errAppend('('+elementName+')'+arguments[i+2]);
						}
					} while (false);
					i += 2;
					break;
				case 'DT':
					if(!StrUtil.isEmpty($20_) && !CheckUtil.isCDate(StrUtil.fillZero(StrUtil.trim($20_),7)))
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('107'));
					}
					break;
				case 'TM':
					if(!StrUtil.isEmpty($20_) && !CheckUtil.isTime(StrUtil.trim($20_)))
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('108'));
					}
					break;
				case 'YM':
					if(!StrUtil.isEmpty($20_) && !CheckUtil.isCDate(StrUtil.fillZero(StrUtil.trim($20_),5)+'01'))
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('119'));
					}
					break;
				case 'YY':
					if(!StrUtil.isEmpty($20_) && !CheckUtil.isCDate(StrUtil.fillZero(StrUtil.trim($20_),3)+'0101'))
					{
						bCheckPassed = false;
						Form.errAppend('('+elementName+')'+Message.getMessage('120'));
					}
					break;
				default:
					break;
			}
		}

		if (bCheckPassed)
		{
			if (isNaN(obj.length))
			{
				if (obj.type=='radio' || obj.type=='checkbox')
					obj.style.background=document.body.style.background;
				else
					obj.style.background=Form.bgcolorNormal;
			}
			else
			{
				if (obj.type=='select-one' || obj.type=='select-multiple')
				{
					for(var i=0 ; i < ((obj.size>0)?obj.size:1) && i < obj.length; ++i)
					{
						obj[i].style.background=Form.bgcolorNormal;
					}
				}
				else
				{
					for(var i=0 ; i<obj.length ; ++i)
					{
						if (obj[i].type=='radio' || obj[i].type=='checkbox')				
							obj[i].style.background=document.body.style.background;
						else
							obj[i].style.background=Form.bgcolorNormal;
					}
				}
			}
		}
		else
		{
			if (isNaN(obj.length))
			{
				obj.style.background=Form.bgcolorError;
			}
			else
			{
				if (obj.type=='select-one' || obj.type=='select-multiple')
				{
					for(var i=0 ; i < ((obj.size>0)?obj.size:1) && i < obj.length; ++i)
					{
						obj[i].style.background=Form.bgcolorError;
					}
				}
				else
				{
					for(var i=0 ; i<obj.length ; ++i)
					{
						obj[i].style.background=Form.bgcolorError;
					}
				}
			}
		}

		return true;
	}
	catch(ex)
	{
		throw new Error(top.getExceptionStr("Form.chkForm",arguments,ex));
	}
}

/**
 * 初始化表單欄位
 *
 * <pre>
 * 初始化參數說明:
 *
 * A     --> AutoTab           R(2)   --> ReadOnly(boolean)    D(2)  --> Disable(boolean)
 * M(2)  --> SetMaxLength(num) MC(2)  --> CheckMaxLength(num)  S(2)  --> Size(num)
 * N     --> Is Number         N+     --> Is Positive Number   W(2)  --> Style width(num)
 * N1    --> Is Integer        N1+    --> Is positive Integer  FC    --> focus
 * V(2)  --> Value(str or num) U      --> toUpperCase          L	 --> toLowerCase
 * KV    --> Keep Value        F(2)   --> FillZero(int)        FS    --> 半形轉全形
 * DT    --> Is Date           TM     --> Is Time              I 	 --> 身份證字號檢核
 * EN    --> English & Number  SE     --> English & Number+    DC(2) --> Decimal Format(4.2)
 * BC(2) --> BG Color(color)   FTC(2) --> Font Color(color)    CS(2) --> Style Classname				
 * CA    --> Chinese Address   NR(2)  --> Number Range (1-1000)
 * AA(3) --> Add Attribute     EMA(3) --> Event Manager Attach(event, method)
 *
 * 使用範例: Form.iniFormSet('rcvdate', 'M', 7, 'S', 7, 'DT', 'V', '0920702', 'A'); //輸入的條件為長度最多七碼、為日期型態、大小為7、預設值為0920702並可自動切換
 * </pre>
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @param {String/Number} elementIndex 欄位的名稱或是索引值
 * @param {String} parameter 初始化的參數
 * @type void
 */
Form.iniFormSet=function(formIndex,elementIndex,parameter)
{
	try
	{
		var $26_=0;
		for(var i=2;i<arguments.length;i++)
		{
			if($26_ > 0)
			{
				$26_--;
				continue;
			}
			var $27_=Form.getDocument().forms[formIndex].elements[elementIndex];
			
			switch((arguments[i]+'').toUpperCase())
			{
				case 'A':
					if(isNaN($27_.length))
					{
						$27_.attachEvent("onkeyup",FormEvent.autoTab);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].attachEvent("onkeyup",FormEvent.autoTab);
						}
					}
					$26_=1;
					break;
				case 'AA':
					if(isNaN($27_.length))
						eval("$27_."+arguments[i+1]+"='"+arguments[i+2]+"'");
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
							eval("$27_["+i_+"]."+arguments[i+1]+"='"+arguments[i+2]+"'");
					}
					$26_=2;
					break;
				case 'BC':
					if(isNaN($27_.length))
						$27_.style.backgroundColor=arguments[i+1];
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
							$27_[i_].style.backgroundColor=arguments[i+1];
					}
					$26_=1;
					break;
				case 'CA':
					if(isNaN($27_.length))
					{
						$27_.attachEvent("onblur",FormEvent.toFullStrCheck);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].attachEvent("onblur",FormEvent.toFullStrCheck);
						}
					}
					$26_=0;
					break;
				case 'CS':
					if(isNaN($27_.length))
						$27_.className=arguments[i+1];
					else 
					{
						for(var i_=0;i_<$27_.length;i_++)
							$27_[i_].className=arguments[i+1];
					}
					$26_=1;
					break;
				case 'D':
					if(isNaN($27_.length)||$27_.type=='select-one'||$27_.type=='select-multiple')
					{
						$27_.disabled=eval(arguments[i+1]);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].disabled=eval(arguments[i+1]);
						}
					}
					$26_=1;
					break;
				case 'DC':
					if(isNaN($27_.length))
					{
						$27_.style.imeMode="disabled";
						$27_.decmalLength=arguments[i+1];
						$27_.attachEvent("onkeyup",FormEvent.checkDecimal);
						$27_.attachEvent("onkeypress",FormEvent.onlyAllowNum);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].style.imeMode="disabled";
							$27_[i_].decmalLength=arguments[i+1];
							$27_[i_].attachEvent("onkeyup",FormEvent.checkDecimal);
							$27_[i_].attachEvent("onkeypress",FormEvent.onlyAllowNum);
						}
					}
					$26_=1;
					break;
				case 'DT':
					if(isNaN($27_.length))
					{
						$27_.attachEvent("onblur",FormEvent.chkDate);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].attachEvent("onblur",FormEvent.chkDate);
						}
					}
					$26_=0;
					break;
				case 'EMA':
					if(isNaN($27_.length))
					{
						$27_.attachEvent(arguments[i+1],arguments[i+2]);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].attachEvent(arguments[i+1],arguments[i+2]);
						}
					}
					$26_=2;
					break;
				case 'EN':
					if(isNaN($27_.length))
					{
						$27_.style.imeMode="disabled";
						$27_.attachEvent("onkeydown",FormEvent.lockAlphaNum);
						$27_.attachEvent("onbeforepaste",FormEvent.checkClipBoardAlphaNum);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].style.imeMode="disabled";
							$27_[i_].attachEvent("onkeydown",FormEvent.lockAlphaNum);
							$27_[i_].attachEvent("onbeforepaste",FormEvent.checkClipBoardAlphaNum);
						}
					}
					$26_=0;
					break;
				case 'F':
					if(isNaN($27_.length))
					{
						$27_.fillZero=arguments[i+1];
						$27_.attachEvent("onblur",FormEvent.fillZero);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].fillZero=arguments[i+1];
							$27_[i_].attachEvent("onblur",FormEvent.fillZero);
						}
					}
					$26_=1;
					break;
				case 'FC':
					Form.canFocus($27_);
					$26_=0;
					break;
				case 'FS':
					if(isNaN($27_.length))
						$27_.attachEvent("onblur",FormEvent.toFullStr);
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
							$27_[i_].attachEvent("onblur",FormEvent.toFullStr);
					}
					$26_=0;
					break;
				case 'FTC':
					if(isNaN($27_.length))
						$27_.style.color=arguments[i+1];
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
							$27_[i_].style.color=arguments[i+1];
						}
						$26_=1;
						break;
				case 'I':
					if(isNaN($27_.length))
					{
						$27_.attachEvent("onblur",FormEvent.checkID);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].attachEvent("onblur",FormEvent.checkID);
						}
					}
					$26_=0;
					break;
				case 'KV':
					if(isNaN($27_.length)||$27_.type=='select-one'||$27_.type=='select-multiple')
					{
						if ($27_.type=='checkbox' || $27_.type=='radio')
						{
							if(arguments[i+1].indexOf($27_.value) != -1)
							{
								$27_.checked = true;
								$27_.keepValue = "1";
							}
							else
							{
								$27_.checked = false;
								$27_.keepValue = "0";
							}
						}
						else if ($27_.type == 'select-multiple')
						{
							for(var i_=0 ; i_ < $27_.length ; i_++)
							{
								if(arguments[i+1].indexOf($27_.options[i_].value) != -1)
								{
									$27_.options[i_].selected = true;
									$27_.options[i_].keepValue = "1";
								}
								else
								{
									$27_.options[i_].selected = false;
									$27_.options[i_].keepValue = "0";
								}
							}
						}
						else
						{
							$27_.value=arguments[i+1];
							$27_.keepValue=arguments[i+1];
						}
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							if ($27_[i_].type=='checkbox' || $27_[i_].type=='radio')
							{
								if(arguments[i+1].indexOf($27_[i_].value) != -1)
								{
									$27_[i_].checked = true;
									$27_[i_].keepValue = "1";
								}
								else
								{
									$27_[i_].checked = false;
									$27_[i_].keepValue = "0";
								}
							}							
							else
							{
								$27_[i_].value = arguments[i+1];
								$27_[i_].keepValue = arguments[i+1];
							}
						}
					}
					$26_=1;
					break;
				case 'L':
					if(isNaN($27_.length))
					{
						$27_.attachEvent("onkeyup",FormEvent.lowerCase);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].attachEvent("onkeyup",FormEvent.lowerCase);
						}
					}
					$26_=0;
					break;
				case 'M':
					if(isNaN($27_.length))
					{
						$27_.maxLength=arguments[i+1];
						$27_.attachEvent("onkeypress",FormEvent.setTextMaxLength);
						$27_.attachEvent("onblur",FormEvent.setTextMaxLength);
						$27_.attachEvent("onbeforepaste",FormEvent.setClipBoardMaxLength);
					}
					$26_=1;
					break;
				case 'MC':
					if(isNaN($27_.length))
					{
						$27_.maxLength=arguments[i+1];
						$27_.attachEvent("onkeypress",FormEvent.chkTextMaxLength);
						$27_.attachEvent("onblur",FormEvent.chkTextMaxLength);
					}
					$26_=1;
					break;
				case 'N':
					if(isNaN($27_.length))
					{
						$27_.style.imeMode="disabled";
						$27_.attachEvent("onkeypress",FormEvent.onlyAllowNum);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].style.imeMode="disabled";
							$27_[i_].attachEvent("onkeypress",FormEvent.onlyAllowNum);
						}
					}
					$26_=0;
					break;
				case 'N+':
					if(isNaN($27_.length))
					{
						$27_.style.imeMode="disabled";
						$27_.attachEvent("onkeypress",FormEvent.onlyAllowPostiveNum);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].style.imeMode="disabled";
							$27_[i_].attachEvent("onkeypress",FormEvent.onlyAllowPostiveNum);
						}
					}
					$26_=0;
					break;
				case 'N1':
					if(isNaN($27_.length))
					{
						$27_.style.imeMode="disabled";
						$27_.attachEvent("onkeypress",FormEvent.onlyAllowNum1);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].style.imeMode="disabled";
							$27_[i_].attachEvent("onkeypress",FormEvent.onlyAllowNum1);
						}
					}
					$26_=0;
					break;
				case 'N1+':
					if(isNaN($27_.length))
					{
						$27_.style.imeMode="disabled";
						$27_.attachEvent("onkeypress",FormEvent.onlyAllowPostiveNum1);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].style.imeMode="disabled";
							$27_[i_].attachEvent("onkeypress",FormEvent.onlyAllowPostiveNum1);
						}
					}
					$26_=0;
					break;
				case 'NR':
					if(isNaN($27_.length))
					{
						$27_.numberRange=arguments[i+1];
						$27_.attachEvent("onblur",FormEvent.numberRngeCheck);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].numberRange=arguments[i+1];
							$27_[i_].attachEvent("onblur",FormEvent.numberRngeCheck);
						}
					}
					$26_=0;
					break;
				case 'R':
					if(isNaN($27_.length))
					{
						$27_.readOnly=eval(arguments[i+1]);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].readOnly=eval(arguments[i+1]);
						}
					}
					$26_=1;
					break;
				case 'S':
					if(isNaN($27_.length))
					{
						$27_.size=arguments[i+1];
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].size=arguments[i+1];
						}
					}
					$26_=1;
					break;
				case 'SE':
					if(isNaN($27_.length))
					{
						$27_.style.imeMode="disabled";
						$27_.attachEvent("onkeydown",FormEvent.lockAlphaNum1);
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].style.imeMode="disabled";
							$27_[i_].attachEvent("onkeydown",FormEvent.lockAlphaNum1);
						}
					}
					$26_=0;
					break;
				case 'TM':
					if(isNaN($27_.length))
						$27_.attachEvent("onblur",FormEvent.chkTime);
					else 
					{
						for(var i_=0;i_<$27_.length;i_++)
							$27_[i_].attachEvent("onblur",FormEvent.chkTime);
					}
					$26_=0;
					break;
				case 'U':
					if(isNaN($27_.length))
						$27_.attachEvent("onkeyup",FormEvent.upperCase);
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
							$27_[i_].attachEvent("onkeyup",FormEvent.upperCase);
					}
					$26_=0;
					break;
				case 'V':
					if(isNaN($27_.length)||$27_.type=='select-one')
					{
						if ($27_.type=='checkbox' || $27_.type=='radio')
						{
							if(arguments[i+1].indexOf($27_.value) != -1)
							{
								$27_.checked = true;
							}
							else
							{
								$27_.checked = false;
							}
						}
						else
						{
							$27_.value=arguments[i+1];
						}
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							if(arguments[i+1].indexOf($27_[i_].value) != -1)
							{
								if ($27_.type=='select-multiple')
								{
									$27_[i_].selected = true;
								}
								else
								{
									$27_[i_].checked = true;
								}
							}
							else
							{
								if ($27_.type=='select-multiple')
								{									
									$27_[i_].selected = false;
								}
								else
								{
									$27_[i_].checked = false;
								}
							}
						}
					}
					$26_=1;
					break;
				case 'W':
					if(isNaN($27_.length))
					{
						$27_.style.width=arguments[i+1];
					}
					else
					{
						for(var i_=0;i_<$27_.length;i_++)
						{
							$27_[i_].style.width=arguments[i+1];
						}
					}
					$26_=1;
					break;
				default:
					break;
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.iniFormSet",arguments,ex));
	}
}

/**
 * 初始化所有表單的顏色
 * @type void
 */
Form.iniFormColor=function()
{
	try
	{
		var $28_=Form.getDocument().forms;
		for(var i=0;i<$28_.length;i++)
		{
			for(var j=0;j<$28_[i].elements.length;j++)
			{
				var $29_=$28_[i].elements[j];
				$29_.style.backgroundColor="";
				$29_.style.color="";
				if($29_.readOnly||$29_.disabled)
				{
					$29_.style.backgroundColor="#E0F8F8";
					$29_.style.color="#000000";
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.iniFormColor",arguments,ex));
	}
}

/**
 * 禁止包含特定名稱的欄位
 *
 * <pre>
 * 使用範例: Form.disableField('field'); //&lt;input name='field1'&gt;&lt;input name='field2'&gt; 皆會被禁止
 * </pre>
 * 
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @param {String} fieldName 欄位的名稱
 * @type void
 */
Form.disableField=function(formIndex,fieldName)
{
	try
	{
		var $32_=Form.getDocument().forms[formIndex];

		for(var i=0;i<$32_.length;i++)
		{
			var $33_=$32_.elements[i];

			if($33_.name.toUpperCase().indexOf(fieldName.toUpperCase()) != -1)
			{
				if($33_.type=='text'||$33_.type=='textarea')
				{
					$33_.readOnly=true;
				}
				else
				{
					$33_.disabled=true;
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.disableField",arguments,ex));
	}
}

/**
 * 設定禁止表單的所有物件
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @param {Boolean} disabled 是否禁止
 * @type void
 */
Form.disableAll=function(formIndex,disabled)
{
	try
	{
		var $36_=Form.getDocument().forms[formIndex];

		for(var i=0;i<$36_.length;i++)
		{
			var $37_=$36_.elements[i];

			if($37_.type=='hidden')
			{
				continue;
			}
			else if($37_.type=='text'||$37_.type=='textarea')
			{
				$37_.readOnly=eval(disabled);
			}
			else
			{
				$37_.disabled=eval(disabled);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.disableAll",arguments,ex));
	}
}

/**
 * 檢查某名稱的CheckBox是否已有勾選或還有未勾選項目
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @param {String/Number} elementIndex 欄位的名稱或是索引值
 * @param {String} checkMode 檢查已有勾選或還有未勾選, 預設值為檢查已有勾選
 * @return true,至少有&#x4E00;項的CheckBox已被勾選; false,尚未勾選任何CheckBox的項目
 * @type Boolean
 */
Form.chkCheckBoxForName=function(formIndex,elementIndex,checkMode)
{
	try
	{
		var $40_=Form.getDocument().forms[formIndex].elements[elementIndex];

		if ($40_ == null)
		{
			return true;
		}
		else
		{
			if(isNaN($40_.length))
			{
				if($40_.checked)
				{
					if (checkMode == null)
					{
						return true;
					}
				}
				else
				{
					if (checkMode != null)
					{
						return true;
					}
				}
			}
			else
			{
				for(var i=0;i<$40_.length;i++)
				{
					if($40_[i].checked)
					{
						if (checkMode == null)
						{
							return true;
						}
					}
					else
					{
						if (checkMode != null)
						{
							return true;
						}
					}
				}
			}
		}

		return false;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.chkCheckBoxForName",arguments,ex));
	}
}

/**
 * 送出指定的表單
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @param {String} url 處理的頁面URL
 * @param {String} method 送出的方法Get/Post 
 * @param {String} target 送到那個分頁, 傳預值為空白
 * @type void
 */
Form.doSubmit=function(formIndex,url,method,target)
{
	try
	{
		var $46_=Form.getDocument().forms[formIndex];
		$46_.action=url;
		$46_.method=method;
		$46_.target=(target == null)?"":target;
		$46_.submit();
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.doSubmit",arguments,ex));
	}
}

/**
 * 設定表單所有CheckBox物件的勾選狀態
 *
 * <pre>
 * 使用範例: Form.setCheckBox(0);         // 畫面上全部的都不勾選
 *           Form.setCheckBox(0,1);       // 畫面上的第&#x4E00;個表單內的都不勾選
 *           Form.setCheckBox(0,1,"chk"); // 畫面上的第&#x4E00;個表單內名稱為chk的都不勾選
 * </pre>
 *
 * @param {Boolean} checked 設定勾選的狀態
 * @param {String/Number} formIndex 表單的名稱或是索引值, 預設值為全部的表單
 * @param {String/Number} elementIndex 欄位的名稱或是索引值, 預設值為全部的CheckBox 
 * @type void
 */
Form.setCheckBox=function(setValue,formIndex,elementIndex)
{
	try
	{
		var $50_=Form.getDocument().forms;

		for(var i=0 ; i<$50_.length ; ++i)
		{
			if(!StrUtil.isEmpty(formIndex) && $50_[formIndex] != $50_[i])
			{
				continue;
			}
			
			if (StrUtil.isEmpty(elementIndex))
			{
				for(var j=0 ; j < $50_[i].length ; ++j)
				{
					var $51_=$50_[i].elements[j];
					
					if ($51_.type == "checkbox" && !$51_.disabled)
					{
						$51_.checked = (eval(setValue))?true:false;
					}
				}
			}
			else
			{
				var $51_=$50_[i].elements[elementIndex];

				if ($51_ == null) return;
				
				if (isNaN($51_.length))
				{
					if (setValue == -1)
					{
						$51_.checked = ($51_.checked)?false:true;
					}
					else
					{
						$51_.checked = (eval(setValue))?true:false;
					}
				}
				else
				{
					for(var j=0 ; j < $51_.length ; ++j)				
					{
						if ($51_[j].type == "checkbox" && !$51_[j].disabled)
						{
							if (setValue == -1)
							{
								$51_[j].checked = ($51_[j].checked)?false:true;
							}
							else
							{
								$51_[j].checked = (eval(setValue))?true:false;
							}
						}
					}
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.setCheckBox",arguments,ex));
	}
}

/** 
 * 清除表單所有的欄位, 並還原回透過iniFormSet()所設定的KV項目值
 * @param {String/Number} formIndex 表單的名稱或是索引值, 預設值為全部的表單
 * @type void
 */
Form.clear=function(formIndex)
{
	try
	{
		var $53_=Form.getDocument().forms;

		for(var i=0;i<$53_.length;i++)
		{
			if (!StrUtil.isEmpty(formIndex) && $53_[formIndex] != $53_[i])
			{
				continue;
			}
			else
			{
				$53_[i].reset();

				for(var j=0;j<$53_[i].length;j++)
				{
					if($53_[i].elements[j].type=='checkbox'||$53_[i].elements[j].type=='radio')
					{
						if ($53_[i].elements[j].keepValue != null)
						{
							$53_[i].elements[j].checked = eval($53_[i].elements[j].keepValue);
						}
					}
					else if($53_[i].elements[j].type=='select-multiple')
					{
						for (var i_ = 0 ; i_ < $53_[i].elements[j].length ; ++i_)
						{
							if ($53_[i].elements[j][i_].keepValue != null)
							{
								$53_[i].elements[j][i_].selected = eval($53_[i].elements[j][i_].keepValue);
							}
						}
					}
					else
					{
						if ($53_[i].elements[j].keepValue != null)
						{
							$53_[i].elements[j].value=$53_[i].elements[j].keepValue;
						}
					}
				}
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.clear",arguments,ex));
	}
}

/**
 * 依據欄位設定的chkForm屬性檢核表單
 * @param {String/Number} formIndex 表單的名稱或是索引值
 * @type void
 */
Form.startChkForm=function(formIndex)
{
	try
	{
		var $55_=Form.getDocument().forms[formIndex];
		var $56_=null;
		for(var i=0;i<$55_.length;i++)
		{
			$56_=$55_.elements[i];
			if($56_.chkForm !=null)
			{
				var $57_=$56_.chkForm.split(',');
				var $58_='';
				for(var j=1;j<$57_.length;j++)
				{
					if(j==1)
						$58_+=$57_[j];
					else
						$58_+=","+$57_[j];
				}
				Form.chkForm($55_.name,$56_.name,$57_[0],$58_);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.startChkForm",arguments,ex));
	}
}

/**
 * 透過SQL指令取得下拉選單的項目
 * @param {Object} obj 下拉選單的物件
 * @param {String} sql 產生下拉選單的SQL字串
 * @param {String} text 下拉選單text欄位的名稱
 * @param {String} value 下拉選單value欄位的名稱
 * @param {Boolean} empty 是否預留&#x4E00;個空白項, 預設值為true
 * @type void
 */
Form.genSQLSelect=function(obj,sql,text,value,empty)
{
	try
	{
		obj.options.length=0;
		var $66_=Assist.getRemoteData(sql);
		if ($66_!=null)
		{
			empty = ((empty==null)?true:eval(empty));
			if(empty)
			{
				var newOption = obj.document.createElement("OPTION");
				newOption.text = "";
				newOption.value = "";

				obj.add(newOption);
			}
			for(var i=0;i<$66_.length;i++)
			{
				var $68_=$66_[i].get(text);
				var $69_=$66_[i].get(value);
				var newOption = obj.document.createElement("OPTION");
				newOption.text = $68_;
				newOption.value = $69_;

				obj.add(newOption);
			}
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Form.genSQLSelect",arguments,ex));
	}
}