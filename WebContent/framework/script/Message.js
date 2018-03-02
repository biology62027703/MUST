/** 
 * @fileoverview 定義處理訊息的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義處理訊息的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */

function Message()
{
}

/**
 * 取得訊息字串
 * @param {String} msgId 訊息代碼
 * @return 訊息代碼所對應的訊息字串
 * @type String
 */
Message.getMessage=function(msgId)
{
	try
	{
		if (msgId.length != 3) return msgId;
		
		var $1_=MessageHashContent_.get(msgId);
		
		if($1_==null)
			return "系統發生未知錯誤!";
		else
			return $1_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Message.getMessage",arguments,ex));
	}
}

/**
 * 顯示訊息 (alert)
 * @param {String} msgId 訊息代碼
 * @type void
 */
Message.showMessage=function(msgId)
{
	try
	{
		Form.getDocument().parentWindow.alert(Message.getMessage(msgId));
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Message.showMessage",arguments,ex));
	}
}

/**
 * 顯示訊息確認視窗 (confirm)
 * @param {String} msgId 訊息代碼
 * @return true,如果使用者按了"是"; false,如果使用者按了"否"
 * @type Boolean
 */
Message.showConfirm=function(msgId)
{
	try
	{
		return Form.getDocument().parentWindow.confirm(this.getMessage(msgId));
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Message.showConfirm",arguments,ex));
	}
}