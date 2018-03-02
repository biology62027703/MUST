/** 
 * @fileoverview 定義StringBuffer型態物件
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義StringBuffer型態物件Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function StringBuffer()
{
	try
	{
		/**
		 * @private
		 */
		this.buffer_=[];
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StringBuffer.StringBuffer",arguments,ex));
	}
}

/**
 * 附加字串
 * @param {String} str 附加的字串
 * @type void
 */
StringBuffer.prototype.append=function(str)
{
	try
	{
		this.buffer_[this.buffer_.length]=str;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StringBuffer.append",arguments,ex));
	}
}

/**
 * 插入字串
 * @param {String} str 插入的字串
 * @type void
 */
StringBuffer.prototype.insert=function(str)
{
	try
	{
		for(var i=this.buffer_.length;i > 0;i--)
		{
			this.buffer_[i]=this.buffer_[i - 1];
		}
		this.buffer_[0]=str;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StringBuffer.insert",arguments,ex));
	}
}

/**
 * 清空StringBuffer
 * @type void
 */
StringBuffer.prototype.clear=function()
{
	try
	{
		this.buffer_.length=0;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StringBuffer.clear",arguments,ex));
	}
}

/**
 * 取得StringBuffer的長度 
 * @return StringBuffer的長度
 * @type Number
 */
StringBuffer.prototype.length=function()
{
	try
	{
		return this.buffer_.join('').length;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StringBuffer.length",arguments,ex));
	}
}

/**
 * 取得StringBuffer的字串值
 * @return StringBuffer的字串值
 * @type String
 */
StringBuffer.prototype.toString=function()
{
	try
	{
		return this.buffer_.join('');
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("StringBuffer.toString",arguments,ex));
	}
}