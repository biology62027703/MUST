/** 
 * @fileoverview 定義字串的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義字串的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function StrUtil()
{
}

/**
 * 去除字串左邊的特定字元,預設是空白
 * @param {String} str 要處理的字串  
 * @param {String} trim 要去除的字元,以正規語言表示
 * @return 去除左邊特定字元後的字串
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
 * 去除字串右邊的特定字元,預設是空白
 * @param {String} str 要處理的字串 
 * @param {String} trim 要去除的字元,以正規語言表示
 * @return 去除右邊特定字元後的字串
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
 * 去除字串前後的特定字元,預設是空白
 * @param {String} str 要處理的字串  
 * @param {String} trim 要去除的字元,以正規語言表示
 * @return 去前後特定字元後的字串
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
 * 當傳入的字串長度不足時,於前方補足指定的字串
 * @param {String} str 要處理的字串 
 * @param {Number} len 要補足的長度 
 * @param {String} fill 於前方補足長度的指定字串
 * @return 補足長度後的字串
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
 * 當傳入的字串長度不足時,於前方補零至指定的長度
 * @param {String} str 要處理的字串 
 * @param {Number} len 指定的長度 
 * @return 補滿零後的字串
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
 * 當傳入的字串長度不足時,於後方補足指定的字串 
 * @param {String} str 要處理的字串
 * @param {Number} len 要補足的長度
 * @param {String } fill 於後方補足長度的指定字串 
 * @return 補足長度後的字串
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
 * 當傳入的字串長度不足時,於後方補零至指定的長度 
 * @param {String} str 要處理的字串 
 * @param {Number} len 指定的長度 
 * @return 補滿零後的字串
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
 * 取得字串的實際位元數  
 * @param {String} str 要處理的字串  
 * @return 實際的位元數
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
 * 取得處理字串中包含搜尋字串的次數
 * @param {String} str 要處理的字串 
 * @param {String} search 要搜尋的字串
 * @param {Boolean} mode 搜尋時是否區分大小寫, 預設值為true
 * @return 出現的次數
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
 * 傳回指定字元數的字串(中文為兩碼)
 * @param {String} str 要處理的字串 
 * @param {Number} len 截取的字元數
 * @return 指定字元數的字串
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
 * 將字串反向
 * @param {String} str 要處理的字串 
 * @return 反向後的字串
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
 * 檢查傳入的物件是否為null, 是則傳回取代字串, 否則傳回物件的字串值
 * @param {Object} obj 要檢查的物件 
 * @param {String} str 要取代的字串 
 * @return 物件的字串值
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
 * 檢查物件是否為空字串 
 * @param {Object} obj 要檢查的物件
 * @return true, 空字串; false, 非空字串
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
 * 將字串轉換成寫入資料庫的格式
 * @param {String} str 要處理的字串 
 * @return 將單引號'換成兩個單引號''後的字串
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
 * 將字串轉換成用於可置於""或''中的格式
 * @param {String} str 要處理的字串 
 * @return 將單引號'與雙引號"分別換成\'與\"
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
 * 僅將字串中特殊符號編碼&#x4F5C;為URL
 * @param {String} str 要處理的字串 
 * @return 編碼後的字串
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