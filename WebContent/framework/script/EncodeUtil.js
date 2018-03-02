/** 
 * @fileoverview �w�q�B�z�r���s�X��&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */
 
/**
 * �w�q�B�z�r���s�X��&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function EncodeUtil()
{
}

/**
 * ���oBig5�r��ҹ�����Ascii�s�X�r��
 * @param {String} str �n�B�z���r�� 
 * @return �ഫ��Ascii�s�X�᪺�r��
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