/** 
 * @fileoverview �w�qHashtable���A����
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�qHashtable���A����Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function Hashtable()
{
	this.hashtable_=new Array();
}	

/**
 * �M��Hashtable�����Ҧ���
 * @return �L
 * @type void
 */
Hashtable.prototype.clear=function()
{
	try
	{
		this.hashtable_=new Array();
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.clear",arguments,ex));
	}
}

/**
 * �P�_Hashtable���O�_�]�t�Y���
 * @param {Object} key �n�P�_�����
 * @return true,Hashtable���]�t�����; false,Hashtable�����]�t�����
 * @type Boolean 
 */
Hashtable.prototype.containsKey=function(key)
{
	try
	{
		var $8_=false;
		
		for(var i in this.hashtable_)
		{
			if(i==key&&this.hashtable_[i] !=null)
			{
				$8_=true;
				
				break;
			}
		}
		
		return $8_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.containsKey",arguments,ex));
	}
}

/**
 * �P�_Hashtable���O�_�]�t�Y��ƭ�
 * @param {Object} value �n�P�_����ƭ�
 * @return true,Hashtable���]�t�Ӹ�ƭ�; false,Hashtable�����]�t�Ӹ�ƭ�
 * @type Boolean
 */
Hashtable.prototype.containsValue=function($9_)
{
	try
	{
		var $10_=false;
		
		if($9_ !=null)
		{
			for(var i in this.hashtable_)
			{
				if(this.hashtable_[i]==$9_)
				{
					$10_=true;
					
					break;
				}
			}
		}
		
		return $10_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.containsValue",arguments,ex));
	}
}

/**
 * ���oHashtable�������Y��Ȫ���ƭ� 
 * @param {Object} key �j�M�����
 * @return {Object} value ��������ƭ�
 * @type String
 */
Hashtable.prototype.get=function(key)
{
	try
	{
		return this.hashtable_[key];
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.get",arguments,ex));
	}
}

/**
 * �P�_Hashtable�O�_���Ū�
 * @return true,Hashtable���Ū�; false,Hashtable�����Ū�
 * @type Boolean
 */
Hashtable.prototype.isEmpty=function()
{
	try
	{
		return (parseInt(this.size())==0)? true :false;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.isEmpty",arguments,ex));
	}
}

/**
 * ���oHashtable������Ȱ}�C
 * @return ��Ȱ}�C
 * @type Array
 */
Hashtable.prototype.keys=function()
{
	try
	{
		var $12_=new Array();
		
		for(var i in this.hashtable_)
		{
			if(this.hashtable_[i] !=null)
				$12_.push(i);
		}
		
		return $12_;
	}		
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.keys",arguments,ex));
	}
}

/**
 * �N��ƭȩ�JHashtable�����������
 * @param {Object} key 
 * @param {Object} value
 * @return �L
 * @type void
 */
Hashtable.prototype.put=function(key,value)
{
	try
	{
		if (key==null||value==null)
			throw new Error(getExceptionStr_("Hashtable.put",arguments,"NullPointerException{"+key+"};,{"+value+"};"));
		else
			this.hashtable_[key]=value;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.put",arguments,ex));
	}
}

/**
 * ����Hashtable����������ȸ�� 
 * @param {Object} key 
 * @return �Q��������ƭ�
 * @type Object
 */
Hashtable.prototype.remove=function(key)
{
	try
	{
		var $16_=this.hashtable_[key];
		
		this.hashtable_[key]=null;
		
		return $16_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.remove",arguments,ex));
	}
}

/**
 * ���oHashtable����ƪ���
 * @return Hashtable����ƪ���
 * @type Number
 */
Hashtable.prototype.size=function()
{
	try
	{
		var $17_=0;
		
		for(var i in this.hashtable_)
		{
			if(this.hashtable_[i] !=null)
				$17_++;
		}
		
		return $17_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.size",arguments,ex));
	}
}

/**
 * �NHashtable���Ҧ�������ର�r�ꫬ�A���
 * @return �r�ꫬ�A��ܪ�Hashtable���e
 * @type String
 */
Hashtable.prototype.toString=function()
{
	try
	{
		var $18_="";
		
		for(var i in this.hashtable_)
		{
			if(this.hashtable_[i] !=null)
				$18_+="{"+i+"};,{"+this.hashtable_[i].toString()+"};\n";
		}
		
		return $18_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.toString",arguments,ex));
	}
}

/**
 * ���oHashtable������ƭȰ}�C
 * @return Hashtable������ƭȰ}�C
 * @type Array
 */
Hashtable.prototype.values=function()
{
	try
	{
		var $19_=new Array();
		
		for(var i in this.hashtable_)
		{
			if(this.hashtable_[i] !=null)
				$19_.push(this.hashtable_[i]);
		}
		
		return $19_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.values",arguments,ex));
	}
}

/**
 * �ƻsHashtable����
 * @return �ƻs��Hashtable����
 * @type Hashtable
 */
Hashtable.prototype.clone=function()
{
	try
	{
		var $20_=new Hashtable();
		
		for(var i in this.hashtable_)
		{
			$20_.put(i,this.hashtable_[i]);
		}
		
		return $20_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Hashtable.clone",arguments,ex));
	}
}