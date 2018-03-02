/** 
 * @fileoverview �w�q�B�z�T����&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q�B�z�T����&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */

function Message()
{
}

/**
 * ���o�T���r��
 * @param {String} msgId �T���N�X
 * @return �T���N�X�ҹ������T���r��
 * @type String
 */
Message.getMessage=function(msgId)
{
	try
	{
		if (msgId.length != 3) return msgId;
		
		var $1_=MessageHashContent_.get(msgId);
		
		if($1_==null)
			return "�t�εo�ͥ������~!";
		else
			return $1_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Message.getMessage",arguments,ex));
	}
}

/**
 * ��ܰT�� (alert)
 * @param {String} msgId �T���N�X
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
 * ��ܰT���T�{���� (confirm)
 * @param {String} msgId �T���N�X
 * @return true,�p�G�ϥΪ̫��F"�O"; false,�p�G�ϥΪ̫��F"�_"
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