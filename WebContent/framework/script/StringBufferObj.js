/** 
 * @fileoverview �w�qStringBuffer���A����
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�qStringBuffer���A����Class
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
 * ���[�r��
 * @param {String} str ���[���r��
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
 * ���J�r��
 * @param {String} str ���J���r��
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
 * �M��StringBuffer
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
 * ���oStringBuffer������ 
 * @return StringBuffer������
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
 * ���oStringBuffer���r���
 * @return StringBuffer���r���
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