/** 
 * @fileoverview �w�q�B�z�I�sJAVA��&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q�B�z�I�sJAVA��&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function JavaInvoke()
{
}

/**
 * �I�sJAVA�� Static Method
 * @param className �I�s�� Class ex: com.acer.util.Utility 
 * @param methodName �I�s�� Function ex: transCharsetToDB 
 * @param metArgsType �ǤJ���Ѽƫ��A, �H"�q"���j, �ثe�䴩���� [String, Object, Integer, Double, Long] ex: String�qString 
 * @param metArgsValue �ǤJ���Ѽƭ�, �H"�q"���j ex: select * from foo where bar = 'foobar' 
 * @return �^�Ǫ��B�z���G
 * @type String
 */
JavaInvoke.invoke=function(className,methodName,metArgsType,metArgsValue)
{
	try
	{
		var $4_="./framework/utility/InvokeJava.jsp";
		var $5_="";
		var $6_="";
		
		if(metArgsType !=null)
		{
			$5_="ClassName="+className+"&MethodName="+methodName+"&MetArgsType="+metArgsType.toString()+"&MetArgsValue="+metArgsValue.toString();
		}
		else
		{
			$5_="ClassName="+className+"&MethodName="+methodName;
		}
		$6_=Assist.postRemoteData($4_,$5_);
		return $6_.substring(0,$6_.length - 1);
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("JavaInvoke.invoke",arguments,ex));
	}
}