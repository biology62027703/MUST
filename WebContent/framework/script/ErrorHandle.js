/** 
 * @fileoverview 定義處理錯誤事件的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */
 
/**
 * 定義處理錯誤事件的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function ErrorHandle()
{
}

/**
 * @final
 * @private
 */
ErrorHandle.debugFlag_ = false;
/**
 * @final
 * @private
 */
ErrorHandle.errorLineStr_ = "<hr color=blue size=1>";

/**
 * @private
 */
ErrorHandle.myOnError = function($0_,$1_,$2_)
{
	var $3_='<b>Error Report</b><p>'+'<b>Error in file:</b> '+$1_+'<br>'+'<b>Line number:</b> '+$2_+'<br>'+'<b>Message:</b> '+$0_.replace(/Exception/g,"<font color=blue>Exception</font>").replace(/Arguments :/g,"<font color=blue>Arguments :</font>").replace(/Message :/g,"<font color=blue>Message :</font>")+'<p>'+"<center>"+"<input type=button value='關　　閉' id='closeBtn' onclick='top.close();'>"+"</center>";
	
	Assist.postRemoteData("/utility/errorlog.jsp","MESSAGE="+$3_);
	
	var $4_=WindowUtil.openExceptionWindow($3_);
	
	$4_.closeBtn.focus();
	
	return true;
}

/**
 * @private
 */
ErrorHandle.onerrorHandler = function()
{
	var report='<b>Error Report</b><p>'+'<b>Page URI:</b> '+escape(arguments[1])+'<br>'+'<b>Line number:</b> '+escape(arguments[2])+'<br>'+'<b>Message:</b> '+escape(arguments[0]).replace(/Exception/g,"<font color=blue>Exception</font>").replace(/Arguments :/g,"<font color=blue>Arguments :</font>").replace(/Message :/g,"<font color=blue>Message :</font>")+'</p>';
	
	WindowUtil.openExceptionDialog(report);
	
	return true;
}

/**
 * @private
 */
ErrorHandle.getFunctionName=function($5_)
{
	try
	{
		var $6_=new String($5_.callee);
		
		return $6_.substring(9,$6_.indexOf("("));
	}
	catch(e)
	{
		throw new Error("<br>"+ErrorHandle.errorLineStr_+"<br>/script/ErrorHandle.js.getFunctionName Exception:<br>Arguments :"+this.getArgsContent(arguments)+"<br>Message :"+e.name+" :"+e.description);
	}
}

/**
 * @private
 */
ErrorHandle.getArgsContent=function($7_)
{
	try
	{
		var $8_='';
		
		for(var i=0;i<$7_.length;i++)
			$8_+='<br><font color=red>args['+i+']:</font> -<font color=green>('+typeof($7_[i])+')</font> '+new String($7_[i]).replace(/</g,'<').replace(/</g,'>');
		return $8_;
	}
	catch(e)
	{
		throw new Error("<br>"+ErrorHandle.errorLineStr_+"<br>/script/ErrorHandle.js.getArgsContent Exception:<br>Arguments :"+$7_+"<br>Message :"+e.name+" :"+e.description);
	}
}

/**
 * @private
 */
ErrorHandle.traceObj=function($9_)
{
	try
	{
		var $10_='';
		
		for(var $10_ in $9_)
			document.write ($10_+':'+$9_[$10_]+"<br>");
	}
	catch(e)
	{
		throw new Error("<br>"+ErrorHandle.errorLineStr_+"<br>/script/ErrorHandle.js.traceObj Exception:<br>Arguments :"+this.getArgsContent(arguments)+"<br>Message :"+e.name+" :"+e.description);
	}
}

/**
 * @private
 */
function getExceptionStr($12_,$13_,$14_)
{
	var $15_="<br>"+ErrorHandle.errorLineStr_+"<br>"+$12_+" Exception:";
	var $16_=ErrorHandle.getArgsContent($13_);
	
	if($16_.length !=0)
		$15_+="<br>Arguments :"+$16_;
	if(($14_.name+$14_.description)!="")
		$15_+="<br>Message :<br><font color=red>[Err Name]</font> :"+$14_.name+"<br><font color=red>[Err Desc]</font> :"+$14_.description;

	return $15_;
}

/**
 * @private
 */
function getExceptionStr_($18_,$19_,$20_)
{
	var $21_="<br>"+ErrorHandle.errorLineStr_+"<br>"+$18_+" Exception:";
	var $22_=ErrorHandle.getArgsContent($19_);
	
	if($22_.length !=0)
		$21_+="<br>Arguments :"+$22_;
	$21_+="<br>Message :<br>"+$20_;
	
	return $21_;
}

window.onerror=ErrorHandle.onerrorHandler;