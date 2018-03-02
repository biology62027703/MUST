/** 
 * @fileoverview �w�q�B�z��檺&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */
 
/**
 * �w�q�B�z��檺&#x5171;�Ψ禡Class
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
 * �w�q������ˮ֥��q�L�ɪ��I���C��
 * @type String
 */
Form.bgcolorError="#FFCCCC";
/**
 * �w�q������ˮֳq�L�ɪ��I���C��
 * @type String
 */
Form.bgcolorNormal="#FFFFFF";

/**
 * �]�w�B�z�����ݪ�document
 * @param {document} doc �n�i����B�z��document����, i.e. parent.queryFrame.document
 * @type void
 */
Form.setDocument=function(doc)
{
	Form.document_ = doc;
}

/**
 * ���o�ثe�B�z�����ݪ�document
 * @return �ثe�B�z�����ݪ�document����
 * @type document
 */
Form.getDocument=function()
{
	return Form.document_;
}

/**
 * �֭p��檺�ˮֿ��~�T�� 
 * @param {String} message ���~�T��
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
 * ��ܪ�檺�֭p���~�T������
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
  *�P�_�O�_��Яఱ�b�� Input, �Y�i�H, �N��а��b������W
  *@param {Object} obj ��줸�� 
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
 * �P�_�O�_�����s���� 
 * @param {Object} obj ��쪫��
 * @return true,�p�G���󪺫��A��button,submit��cancel; false,�p�G���󪺫��A�D�W�z�ҦC
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
 * ���o��쪺��
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
 * @param {String/Number} elementIndex ��쪺�W�٩άO���ޭ�
 * @return ��쪺�r���
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
 * ���o�ۦP���W�٪���
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
 * @param {String/Number} elementIndex ��쪺�W�٩άO���ޭ�
 * @param {Number} valueIndex ���Ȫ����ޭ�
 * @return ��쪺�r���
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
 * �]�w��쪺��
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
 * @param {String/Number} elementIndex ��쪺�W�٩άO���ޭ�
 * @param {String} str �n�]�w����
 * @return �L
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
 * �ˮ֪�����,�ñN���~�T���֭p�� Form.errMessage
 *
 * <pre>
 *
 * �ˮְѼƻ���:
 * AC(3) --> �ۭq����ˬd�禡 ['AC',doCheckFunction,'���~�T��']
 * MC    --> �ˬd���O�_�W�L�]�w���̤j����
 * E     --> �ˬd���O�_���ŭ�
 * I     --> �ˬd���O�_�ŦX�����Ҧr���榡
 * N     --> �ˬd���O�_���ƭȫ��A
 * N+    --> �ˬd���O�_�����ƭȫ��A
 * DT    --> �ˬd���O�_�ŦX������(YYYMMDD)�榡
 * N1    --> �ˬd���O�_����ƭȫ��A
 * N1+   --> �ˬd���O�_������ƭȫ��A
 * NE(2) --> �ˬd�O�_�ܤ֦�&#x4E00;����줣���ŭ� ['NE',new Array('FIELD1','FIELD2')]
 * TM    --> �ˬd���O�_�ŦX�ɶ�(HHMM)�榡
 * YM    --> �ˬd���O�_�ŦX����~��(YYYMM)�榡
 * YY    --> �ˬd���O�_�ŦX����~��(YYY)�榡
 * F     --> �ˬd���ɱN���ȥ���ɺ�0�ܳ]�w������
 *
 * �ϥνd��: Form.chkForm('form1', 'rcvdate', '�������', 'DT','MC'); //�ˮ��`���פΤ���榡
 * </pre>
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
 * @param {String/Number} elementIndex ��쪺�W�٩άO���ޭ�
 * @param {String} elementName ��쪺�W�ٴy�z
 * @param {String} parameter ����ˮ֪��Ѽ�
 * @return �L
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
						Form.errAppend('('+elementName+')�ѼƳ]�w���~');
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
 * ��l�ƪ�����
 *
 * <pre>
 * ��l�ưѼƻ���:
 *
 * A     --> AutoTab           R(2)   --> ReadOnly(boolean)    D(2)  --> Disable(boolean)
 * M(2)  --> SetMaxLength(num) MC(2)  --> CheckMaxLength(num)  S(2)  --> Size(num)
 * N     --> Is Number         N+     --> Is Positive Number   W(2)  --> Style width(num)
 * N1    --> Is Integer        N1+    --> Is positive Integer  FC    --> focus
 * V(2)  --> Value(str or num) U      --> toUpperCase          L	 --> toLowerCase
 * KV    --> Keep Value        F(2)   --> FillZero(int)        FS    --> �b�������
 * DT    --> Is Date           TM     --> Is Time              I 	 --> �����Ҧr���ˮ�
 * EN    --> English & Number  SE     --> English & Number+    DC(2) --> Decimal Format(4.2)
 * BC(2) --> BG Color(color)   FTC(2) --> Font Color(color)    CS(2) --> Style Classname				
 * CA    --> Chinese Address   NR(2)  --> Number Range (1-1000)
 * AA(3) --> Add Attribute     EMA(3) --> Event Manager Attach(event, method)
 *
 * �ϥνd��: Form.iniFormSet('rcvdate', 'M', 7, 'S', 7, 'DT', 'V', '0920702', 'A'); //��J�����󬰪��׳̦h�C�X�B��������A�B�j�p��7�B�w�]�Ȭ�0920702�åi�۰ʤ���
 * </pre>
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
 * @param {String/Number} elementIndex ��쪺�W�٩άO���ޭ�
 * @param {String} parameter ��l�ƪ��Ѽ�
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
 * ��l�ƩҦ���檺�C��
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
 * �T��]�t�S�w�W�٪����
 *
 * <pre>
 * �ϥνd��: Form.disableField('field'); //&lt;input name='field1'&gt;&lt;input name='field2'&gt; �ҷ|�Q�T��
 * </pre>
 * 
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
 * @param {String} fieldName ��쪺�W��
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
 * �]�w�T���檺�Ҧ�����
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
 * @param {Boolean} disabled �O�_�T��
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
 * �ˬd�Y�W�٪�CheckBox�O�_�w���Ŀ���٦����Ŀﶵ��
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
 * @param {String/Number} elementIndex ��쪺�W�٩άO���ޭ�
 * @param {String} checkMode �ˬd�w���Ŀ���٦����Ŀ�, �w�]�Ȭ��ˬd�w���Ŀ�
 * @return true,�ܤ֦�&#x4E00;����CheckBox�w�Q�Ŀ�; false,�|���Ŀ����CheckBox������
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
 * �e�X���w�����
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
 * @param {String} url �B�z������URL
 * @param {String} method �e�X����kGet/Post 
 * @param {String} target �e�쨺�Ӥ���, �ǹw�Ȭ��ť�
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
 * �]�w���Ҧ�CheckBox���󪺤Ŀ窱�A
 *
 * <pre>
 * �ϥνd��: Form.setCheckBox(0);         // �e���W�����������Ŀ�
 *           Form.setCheckBox(0,1);       // �e���W����&#x4E00;�Ӫ�椺�������Ŀ�
 *           Form.setCheckBox(0,1,"chk"); // �e���W����&#x4E00;�Ӫ�椺�W�٬�chk�������Ŀ�
 * </pre>
 *
 * @param {Boolean} checked �]�w�Ŀ諸���A
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�, �w�]�Ȭ����������
 * @param {String/Number} elementIndex ��쪺�W�٩άO���ޭ�, �w�]�Ȭ�������CheckBox 
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
 * �M�����Ҧ������, ���٭�^�z�LiniFormSet()�ҳ]�w��KV���ح�
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�, �w�]�Ȭ����������
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
 * �̾����]�w��chkForm�ݩ��ˮ֪��
 * @param {String/Number} formIndex ��檺�W�٩άO���ޭ�
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
 * �z�LSQL���O���o�U�Կ�檺����
 * @param {Object} obj �U�Կ�檺����
 * @param {String} sql ���ͤU�Կ�檺SQL�r��
 * @param {String} text �U�Կ��text��쪺�W��
 * @param {String} value �U�Կ��value��쪺�W��
 * @param {Boolean} empty �O�_�w�d&#x4E00;�Ӫťն�, �w�]�Ȭ�true
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