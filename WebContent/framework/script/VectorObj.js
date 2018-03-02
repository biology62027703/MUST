/** 
 * @fileoverview �w�qVector���A����
 *
 * @author Welkin Fan
 * @version 1.0
 */
 
/**
 * �w�qVector���A����Class
 * @constructor
 * @param {Number} initialCapacity Vector�e�q����l��, �w�]�Ȭ�100
 * @param {Number} capacityIncrement Vector�e�q��������, �w�]�Ȭ�100
 * @author Welkin Fan
 * @version 1.0
 */
function Vector(initialCapacity, capacityIncrement)
{
	try
	{
		/**
		 * @private
		 */
		this.capacity_=(initialCapacity == null)?100:initialCapacity;
		/**
		 * @private
		 */
		this.increment_=(capacityIncrement == null)?100:capacityIncrement;
		/**
		 * @private
		 */
		this.data_=new Array(this.capacity_);
		/**
		 * @private
		 */
		this.size_=0;
		/**
		 * @private
		 */
		this.resize=function()
		{
			var $1_=new Array(this.data_.length+this.increment_);
			
			for(var i=0;i<this.data_.length;i++)
			{
				$1_[i]=this.data_[i];
			}
			this.data_=$1_;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.Vector",arguments,ex));
	}
}

/**
 * �bVector�����w��m�W�[&#x4E00;�Ӫ���
 * @param {Object} obj �s�W������
 * @param {Number} index �s�W���󪺦�m, �w�]�Ȭ�Vector������
 * @type void
 */
Vector.prototype.add=function(obj,index)
{
	try
	{
		if(this.size() >= this.data_.length)
		{
			this.resize();
		}
		
		if (isNaN(index) || index>this.size())
		{
			index = this.size();
		}

		for(var i=this.size();i > index;i--)
		{
			this.data_[i]=this.data_[i - 1];
		}
		
		this.data_[index]=obj;
		this.size_++;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.add",arguments,ex));
	}
}

/**
 * �bVector�����ݼW�[&#x4E00;�Ӫ���
 * @param {Object} obj �s�W������
 * @type void
 */
Vector.prototype.addElement=function(obj)
{
	try
	{
		if(this.size() >= this.data_.length)
		{
			this.resize();
		}
		
		this.data_[this.size_++]=obj;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.addElement",arguments,ex));
	}
}

/**
 * ���oVector�ثe���e�q
 * @return Vector�ثe���e�q
 * @type Number
 */
Vector.prototype.capacity=function()
{
	try
	{
		return this.data_.length;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.capacity",arguments,ex));
	}
}

/**
 * �M��Vector���Ҧ�������
 * @type void
 */
Vector.prototype.clear=function()
{
	try
	{
		this.data_=new Array(this.capacity_);
		this.size_=0;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.clear",arguments,ex));
	}
}

/**
 * �ƻs&#x4E00;��Vector����
 * @return �ƻs�᪺Vector����
 * @type {@link Vector}
 */
Vector.prototype.clone=function()
{
	try
	{
		var $13_=new Vector(this.size_);
		
		for(var i=0;i<this.size_;i++)
		{
			$13_.add(this.data_[i]);
		}
		return $13_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.clone",arguments,ex));
	}
}

/**
 * �P�_����O�_�s�bVector��
 * @param {Object} obj �j�M������
 * @return true,�p�G����s�bVector��; false,�p�G���󤣦bVector��
 * @type Boolean
 */
Vector.prototype.contains=function(obj)
{
	try
	{
		for(var i=0;i<this.size();i++)
		{
			if(this.data_[i]==obj)
			{
				return true;
			}
		}
		return false;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.contains",arguments,ex));
	}
}

/**
 * ���oVector���w��m������
 * @param {Number} index ���o���󪺦�m
 * @return ���w��m������
 * @type Object
 */
Vector.prototype.elementAt=function(index)
{
	try
	{
		if (isNaN(index) || index >= this.size())
		{
			throw new RangeError();
		}
		else
		{
			return this.data_[index];
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.elementAt",arguments,ex));
	}
}

/**
 * ���oVector������&#x4E00;�Ӫ���
 * @return Vector������&#x4E00;�Ӫ���
 * @type Object
 */
Vector.prototype.firstElement=function()
{
	try
	{
		return this.data_[0];
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.firstElement",arguments,ex));
	}
}

/**
 * ���oVector���w��m������
 * @param {Number} index ���o���󪺦�m
 * @return ���w��m������
 * @type Object
 */
Vector.prototype.get=function(index)
{
	try
	{
		if (isNaN(index) || index >= this.size())
		{
			throw new RangeError();
		}
		else
		{
			return this.data_[index];
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.get",arguments,ex));
	}
}

/**
 * �q���w����m�}�l�j�M���󭺦��X�{�bVector������m
 * @param {Object} obj �j�M������
 * @param {Number} index �j�M���_�l��m, �w�]�Ȭ�0
 * @return �p�G�䤣�쪫��h�Ǧ^-1, �_�h�Ǧ^���󭺦��X�{����m
 * @type Number
 */
Vector.prototype.indexOf=function(obj, index)
{
	try
	{
		for(var i=(isNaN(index) || index < 0)?0:index;i<this.size();i++)
		{
			if (this.data_[i]==obj)
			{
				return i;
			}
		}
		return -1;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.indexOf",arguments,ex));
	}
}

/**
 * �bVector�����w��m�W�[&#x4E00;��Element
 * @param {Object} obj �s�W������
 * @param {Number} index �s�W���󪺦�m
 * @type void
 */
Vector.prototype.insertElementAt=function(obj,index)
{
	try
	{
		if (isNaN(index))
		{
			throw new RangeError();
		}
		else
		{
			if(this.size() >= this.data_.length)
			{
				this.resize();
			}
			
			for(var i=this.size();i > index;i--)
			{
				this.data_[i]=this.data_[i - 1];
			}
			
			this.data_[(index > this.size())?this.size():index] = obj;
			this.size_++;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.insertElementAt",arguments,ex));
	}
}

/**
 * �ˬdVector���O�_�S������
 * @return true,�p�GVector�O�Ū�; false,�p�GVector��������
 * @type Boolean
 */
Vector.prototype.isEmpty=function()
{
	try
	{
		return (this.size()<=0);
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.isEmpty",arguments,ex));
	}
}

/**
 * ���oVector�����̫�&#x4E00;�Ӫ���
 * @return Vector�����̫�&#x4E00;�Ӫ���
 * @type Object
 */
Vector.prototype.lastElement=function()
{
	try
	{
		return this.data_[this.size()- 1];
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.lastElement",arguments,ex));
	}
}

/**
 * �q���w����m�}�l�j�M����̫�X�{�bVector������m
 * @param {Object} obj �j�M������
 * @param {Number} index �j�M���_�l��m, �w�]�Ȭ�0
 * @return �p�G�䤣�쪫��h�Ǧ^-1, �_�h�Ǧ^����̫�X�{����m
 * @type Number
 */
Vector.prototype.lastIndexOf=function(obj, index)
{
	try
	{
		var foundIndex = -1;
		
		for(var i=(isNaN(index) || index < 0)?0:index;i<this.size();i++)
		{
			if (this.data_[i]==obj)
			{
				foundIndex = i;
			}
		}
		return foundIndex;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.lastIndexOf",arguments,ex));
	}
}

/**
 * �M��Vector���Ҧ�������
 * @type void
 */
Vector.prototype.removeAllElements=function()
{
	try
	{
		this.data_=new Array(this.capacity_);
		this.size_=0;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.removeAllElements",arguments,ex));
	}
}

/**
 * ���������X�{�bVector��������
 * @param {Object} obj ����������
 * @return true, �p�G�Ӫ���X�{�bVector��; false, �p�G�䤣��Ӫ���
 * @type Boolean
 */
Vector.prototype.remove=function(obj)
{
	try
	{
		var removeIndex = this.indexOf(obj);

		if (removeIndex >= 0)
		{
			this.data_.splice(removeIndex,1);
			this.size_--;
			
			return true;
		}
		return false;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.remove",arguments,ex));
	}
}

/**
 * �����bVector�����w��m������
 * @param {Number} index �Q�������󪺦�m
 * @return �Q����������
 * @type Object
 */
Vector.prototype.removeElementAt=function(index)
{
	try
	{
		if (isNaN(index) || index >= this.size())
		{
			throw new RangeError();
		}
		else
		{
			this.size_--;
			return this.data_.splice(index,1);
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.removeElementAt",arguments,ex));
	}
}

/**
 * �]�wVector�����w��m������
 * @param {Object} obj �]�w������
 * @param {Number} index �]�w���󪺦�m
 * @type void
 */
Vector.prototype.setElementAt=function(obj, index)
{
	try
	{
		if (isNaN(index) || index >= this.size())
		{
			throw new RangeError();
		}
		else
		{
			this.data_[index] = obj;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.setElementAt",arguments,ex));
	}
}

/**
 * ���o�ثeVector�s�񪺪���ƶq
 * @return �ثeVector�s�񪺪���ƶq
 * @type Number
 */
Vector.prototype.size=function()
{
	try
	{
		return this.size_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.size",arguments,ex));
	}
}

/**
 * ���o�ثeVector���Ҧ����󪺦r���T
 * @return �ثeVector���Ҧ����󪺦r���T
 * @type String
 */
Vector.prototype.toString=function()
{
	try
	{
		var $14_="Vector Object properties:\n"+"Increment:"+this.increment_+"\n"+"Size:"+this.size_+"\n"+"Elements:\n";
		
		for(var i=0;i<this.size();i++)
		{
			$14_+="\t"+this.data_[i]+"\n";
		}
		
		return $14_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.toString",arguments,ex));
	}
}

/**
 * �NVector���e�q�Y��ܥثe�Ҧs�񪫥󪺼ƶq
 * @type void
 */
Vector.prototype.trimToSize=function()
{
	try
	{
		var $12_=new Array(this.size());
		
		for(var i=0;i<this.size();i++)
		{
			$12_[i]=this.data_[i];
		}
		
		this.data_=$12_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("Vector.trimToSize",arguments,ex));
	}
}
