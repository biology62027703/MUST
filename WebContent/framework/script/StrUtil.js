/** 
 * @fileoverview �w�q�r�ꪺ&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q�r�ꪺ&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function StrUtil()
{
}

/**
 * �h���r�ꥪ�䪺�S�w�r��,�w�]�O�ť�
 * @param {String} str �n�B�z���r��  
 * @param {String} trim �n�h�����r��,�H���W�y�����
 * @return �h������S�w�r���᪺�r��
 * @type String
 */
StrUtil.lTrim=function(str,trim)
{
	try
	{
		if (trim == null || trim.replace(/(\s*$)/g,"") == "")
		{
			trim = "\\s";
		}
		return str.replace(eval("/(^["+trim+"]*)/g"),"");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.lTrim",arguments,ex));
	}
}

/**
 * �h���r��k�䪺�S�w�r��,�w�]�O�ť�
 * @param {String} str �n�B�z���r�� 
 * @param {String} trim �n�h�����r��,�H���W�y�����
 * @return �h���k��S�w�r���᪺�r��
 * @type String
 */
StrUtil.rTrim=function(str,trim)
{
	try
	{
		if (trim == null || trim.replace(/(\s*$)/g,"") == "")
		{
			trim = "\\s";
		}
		return str.replace(eval("/(["+trim+"]*$)/g"),"");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.rTrim",arguments,ex));
	}
}

/**
 * �h���r��e�᪺�S�w�r��,�w�]�O�ť�
 * @param {String} str �n�B�z���r��  
 * @param {String} trim �n�h�����r��,�H���W�y�����
 * @return �h�e��S�w�r���᪺�r��
 * @type String
 */
StrUtil.trim=function(str,trim)
{
	try
	{
		if (trim == null || trim.replace(/(\s*$)/g,"") == "")
		{
			trim = "\\s";
		}
		return str.replace(eval("/(^["+trim+"]*)|(["+trim+"]*$)/g"),"");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.trim",arguments,ex));
	}
}

/**
 * ��ǤJ���r����פ�����,��e��ɨ����w���r��
 * @param {String} str �n�B�z���r�� 
 * @param {Number} len �n�ɨ������� 
 * @param {String} fill ��e��ɨ����ת����w�r��
 * @return �ɨ����׫᪺�r��
 * @type String
 */
StrUtil.fillStr=function(str,len,fill)
{
	try
	{
		if(str.length >= len)
		{
			return str;
		}
		else
		{		
			var $6_ = str;
			while($6_.length < len)
			{
				$6_ = ''+fill+$6_;
			}
			return $6_;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.fillStr",arguments,ex));
	}
}

/**
 * ��ǤJ���r����פ�����,��e��ɹs�ܫ��w������
 * @param {String} str �n�B�z���r�� 
 * @param {Number} len ���w������ 
 * @return �ɺ��s�᪺�r��
 * @type String
 */
StrUtil.fillZero=function(str,len)
{
	try
	{
		return StrUtil.fillStr(str,len,'0');
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.fillZero",arguments,ex));
	}
}

/**
 * ��ǤJ���r����פ�����,����ɨ����w���r�� 
 * @param {String} str �n�B�z���r��
 * @param {Number} len �n�ɨ�������
 * @param {String } fill ����ɨ����ת����w�r�� 
 * @return �ɨ����׫᪺�r��
 * @type String
 */
StrUtil.fillBackStr=function(str,len,fill)
{
	try
	{
		if (str.length >= len)
		{
			return str;
		}
		else
		{
			var $12_=str;
			while($12_.length < len)
			{
				$12_ = ''+$12_+fill;
			}
			return $12_;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.fillBackStr",arguments,ex));
	}
}

/**
 * ��ǤJ���r����פ�����,����ɹs�ܫ��w������ 
 * @param {String} str �n�B�z���r�� 
 * @param {Number} len ���w������ 
 * @return �ɺ��s�᪺�r��
 * @type String
 */
StrUtil.fillBackZero=function(str,len)
{
	try
	{
		return StrUtil.fillBackStr(str,len,'0');
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.fillBackZero",arguments,ex));
	}
}

/**
 * ���o�r�ꪺ��ڦ줸��  
 * @param {String} str �n�B�z���r��  
 * @return ��ڪ��줸��
 * @type Number
 */
StrUtil.getBLen=function(str)
{
	try
	{
		return str.replace(/[^\x00-\xff]/ig,"oo").length;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.getBLen",arguments,ex));
	}
}

/**
 * ���o�B�z�r�ꤤ�]�t�j�M�r�ꪺ����
 * @param {String} str �n�B�z���r�� 
 * @param {String} search �n�j�M���r��
 * @param {Boolean} mode �j�M�ɬO�_�Ϥ��j�p�g, �w�]�Ȭ�true
 * @return �X�{������
 * @type Number
 */
StrUtil.getCount=function(str,search,mode)
{
	try
	{
		return eval("str.match(/("+search+")/g"+(mode?"i":"")+").length");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.getCount",arguments,ex));
	}
}

/**
 * �Ǧ^���w�r���ƪ��r��(���嬰��X)
 * @param {String} str �n�B�z���r�� 
 * @param {Number} len �I�����r����
 * @return ���w�r���ƪ��r��
 * @type String
 */
StrUtil.getBStr=function(str,len)
{
	try
	{
		var $22_="";
		var $23_=0;
		for(var i=0;i<str.length;i++)
		{
			if(str.charCodeAt(i)==13||str.charCodeAt(i)==10)
			{
				$22_=$22_+str.substr(i,1);
				continue;
			}
			if(str.charCodeAt(i)>=128||str.charCodeAt(i)<=-128)
				$23_=$23_+2;
			else
				$23_++;
			if($23_ > len)
				return $22_;
			else
				$22_=$22_+str.substr(i,1);
		}
		return $22_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.getBStr",arguments,ex));
	}
}

/**
 * �N�r��ϦV
 * @param {String} str �n�B�z���r�� 
 * @return �ϦV�᪺�r��
 * @type String
 */
StrUtil.convertStr=function(str)
{
	try
	{
		var $34_="";
		
		for(i=str.length - 1;i >=0;i--)
			$34_=$34_+str.charAt(i);
		return $34_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.convertStr",arguments,ex));
	}
}

/**
 * �ˬd�ǤJ������O�_��null, �O�h�Ǧ^���N�r��, �_�h�Ǧ^���󪺦r���
 * @param {Object} obj �n�ˬd������ 
 * @param {String} str �n���N���r�� 
 * @return ���󪺦r���
 * @type String
 */
StrUtil.checkNull=function(obj,str)
{
	try
	{
		return (obj == null)?((arguments.length > 1)?str.toString():""):obj.toString();
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.checkNull",arguments,ex));
	}	
}

/**
 * �ˬd����O�_���Ŧr�� 
 * @param {Object} obj �n�ˬd������
 * @return true, �Ŧr��; false, �D�Ŧr��
 * @type Boolean
 */
StrUtil.isEmpty=function(obj)
{
	try
	{
		return (StrUtil.trim(StrUtil.checkNull(obj,"")).length <= 0);
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.isEmpty",arguments,ex));
	}	
}

/**
 * �N�r���ഫ���g�J��Ʈw���榡
 * @param {String} str �n�B�z���r�� 
 * @return �N��޸�'������ӳ�޸�''�᪺�r��
 * @type String
 */
StrUtil.dbStr=function(str)
{
	try
	{
		return StrUtil.checkNull(str).replace(/\'/g,"''");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.dbStr",arguments,ex));
	}
}

/**
 * �N�r���ഫ���Ω�i�m��""��''�����榡
 * @param {String} str �n�B�z���r�� 
 * @return �N��޸�'�P���޸�"���O����\'�P\"
 * @type String
 */
StrUtil.evalStr=function(str)
{
	try
	{
		return StrUtil.checkNull(str).replace(/\\/g,"\\\\").replace(/\"/g,"\\\"").replace(/\'/g,"\\\'");
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.dbStr",arguments,ex));
	}
}

/**
 * �ȱN�r�ꤤ�S��Ÿ��s�X&#x4F5C;��URL
 * @param {String} str �n�B�z���r�� 
 * @return �s�X�᪺�r��
 * @type String
 */
StrUtil.encodeURIComponent=function(str)
{
	try
	{
		var uri = "";
		
		if (str != null)
		{
			for (var i = 0; i < str.length ; i++)
			{
				if (StrUtil.getBLen(str.charAt(i)) == 1)
				{
					uri += encodeURIComponent(str.charAt(i));
				}
				else
				{
					uri += str.charAt(i);
				}
			}
		}
				
		return uri;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StrUtil.encodeURIComponent",arguments,ex));
	}
}