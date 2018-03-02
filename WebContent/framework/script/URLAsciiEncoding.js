/** 
 * @fileoverview 定義處理ASCII型態的URL編碼&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 將字串編碼成URL格式
 * @param {String} url 要編碼的字串
 * @return 編碼後的字串
 * @type String
 */
function encodeURL(url)
{
	var ret="";
	var strSpecial="!\"#$%&'()*+,/:;<=>?[]^`{|}~%";
	
	for(var i=0;i<url.length;i++)
	{
		var varasc = vbAsc(url.charAt(i));

		if(varasc<0)
		{  
			varasc += 65536;
		}
		if(varasc>255)
		{
			var varlow=varasc & 0xFF00;
			varlow=varlow>>8;
			var varhigh=varasc & 0xFF;
			ret += "%" + varhigh.toString(16).toUpperCase() + "%" + varlow.toString(16).toUpperCase();
		}  
		else  
		{  
			if(url.charAt(i) == " ")
			{
				ret += "+";
			}
			else if(strSpecial.indexOf(url.charAt(i)) != -1)
			{
				ret += "%"+varasc.toString(16);
    		}
			else
			{
				ret += url.charAt(i);
    		}
		}  
	}  
	return ret;
}

/**
 * 將編碼的URL字串還原
 * @param {String} url 要解碼的字串
 * @return 解碼後的字串
 * @type String
 */
function decodeURL(url)
{
	var ret="";
	
	for(var i=0;i<url.length;i++)
	{
		var chr = url.charAt(i);

		if (chr == "+")
		{
			ret+=" ";
		}
		else if (chr == "%")
		{
			var asc = url.substring(i+1,i+3);
			
			if(parseInt("0x"+asc)>0x7f)
			{
				ret += String.fromCharCode(parseInt("0x"+asc+url.substring(i+4,i+6)));
      			i+=5;
			}
			else
			{
				ret += String.fromCharCode(parseInt("0x"+asc));
				i+=2;
			}
    	}
    	else
    	{
			ret += chr;
    	}
  	}
	return ret;
}