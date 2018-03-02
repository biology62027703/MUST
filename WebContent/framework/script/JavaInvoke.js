/** 
 * @fileoverview 定義處理呼叫JAVA的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義處理呼叫JAVA的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function JavaInvoke()
{
}

/**
 * 呼叫JAVA的 Static Method
 * @param className 呼叫的 Class ex: com.acer.util.Utility 
 * @param methodName 呼叫的 Function ex: transCharsetToDB 
 * @param metArgsType 傳入的參數型態, 以"┼"分隔, 目前支援的有 [String, Object, Integer, Double, Long] ex: String┼String 
 * @param metArgsValue 傳入的參數值, 以"┼"分隔 ex: select * from foo where bar = 'foobar' 
 * @return 回傳的處理結果
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