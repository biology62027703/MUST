/** 
 * @fileoverview 定義處理字元編碼的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */
 
/**
 * 定義處理字元編碼的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function EncodeUtil()
{
}

/**
 * 取得Big5字串所對應的Ascii編碼字串
 * @param {String} str 要處理的字串 
 * @return 轉換為Ascii編碼後的字串
 * @type String
 */
EncodeUtil.Big5ToAscii = function(str)
{
	try
	{
		var asciiStr = "";
		for(var i=0;i<str.length;i++)
		{
			var varasc = vbAsc(str.charAt(i));

			if(varasc<0)
			{  
				varasc += 65536;
			}
			if(varasc>255)
			{
				var varlow=varasc & 0xFF00;
				varlow=varlow>>8;
				var varhigh=varasc & 0xFF;
				asciiStr += String.fromCharCode(varlow)+String.fromCharCode(varhigh);
			}  
			else  
			{  
				asciiStr += String.fromCharCode(varasc);  
			}  
		}  
		return asciiStr;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("EncodeUtil.Big5ToAscii",arguments,ex));
	}
}