/** 
 * @fileoverview 定義Vector型態物件
 *
 * @author Welkin Fan
 * @version 1.0
 */
 
/**
 * 定義Vector型態物件Class
 * @constructor
 * @param {Number} initialCapacity Vector容量的初始值, 預設值為100
 * @param {Number} capacityIncrement Vector容量的成長值, 預設值為100
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
 * 在Vector的指定位置增加&#x4E00;個物件
 * @param {Object} obj 新增的物件
 * @param {Number} index 新增物件的位置, 預設值為Vector的尾端
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
 * 在Vector的尾端增加&#x4E00;個物件
 * @param {Object} obj 新增的物件
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
 * 取得Vector目前的容量
 * @return Vector目前的容量
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
 * 清空Vector內所有的物件
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
 * 複製&#x4E00;個Vector物件
 * @return 複製後的Vector物件
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
 * 判斷物件是否存在Vector中
 * @param {Object} obj 搜尋的物件
 * @return true,如果物件存在Vector中; false,如果物件不在Vector中
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
 * 取得Vector指定位置的物件
 * @param {Number} index 取得物件的位置
 * @return 指定位置的物件
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
 * 取得Vector內的第&#x4E00;個物件
 * @return Vector內的第&#x4E00;個物件
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
 * 取得Vector指定位置的物件
 * @param {Number} index 取得物件的位置
 * @return 指定位置的物件
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
 * 從指定的位置開始搜尋物件首次出現在Vector中的位置
 * @param {Object} obj 搜尋的物件
 * @param {Number} index 搜尋的起始位置, 預設值為0
 * @return 如果找不到物件則傳回-1, 否則傳回物件首次出現的位置
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
 * 在Vector的指定位置增加&#x4E00;個Element
 * @param {Object} obj 新增的物件
 * @param {Number} index 新增物件的位置
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
 * 檢查Vector內是否沒有物件
 * @return true,如果Vector是空的; false,如果Vector內有物件
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
 * 取得Vector內的最後&#x4E00;個物件
 * @return Vector內的最後&#x4E00;個物件
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
 * 從指定的位置開始搜尋物件最後出現在Vector中的位置
 * @param {Object} obj 搜尋的物件
 * @param {Number} index 搜尋的起始位置, 預設值為0
 * @return 如果找不到物件則傳回-1, 否則傳回物件最後出現的位置
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
 * 清空Vector內所有的物件
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
 * 移除首次出現在Vector中的物件
 * @param {Object} obj 移除的物件
 * @return true, 如果該物件出現在Vector中; false, 如果找不到該物件
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
 * 移除在Vector中指定位置的物件
 * @param {Number} index 被移除物件的位置
 * @return 被移除的物件
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
 * 設定Vector中指定位置的物件
 * @param {Object} obj 設定的物件
 * @param {Number} index 設定物件的位置
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
 * 取得目前Vector存放的物件數量
 * @return 目前Vector存放的物件數量
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
 * 取得目前Vector中所有物件的字串資訊
 * @return 目前Vector中所有物件的字串資訊
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
 * 將Vector的容量縮減至目前所存放物件的數量
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
