/** 
 * @fileoverview 定義Hashtable型態物件
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義Hashtable型態物件Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function Hashtable()
{
	this.hashtable_=new Array();
}	

/**
 * 清空Hashtable內的所有值
 * @return 無
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
 * 判斷Hashtable內是否包含某鍵值
 * @param {Object} key 要判斷的鍵值
 * @return true,Hashtable內包含該鍵值; false,Hashtable內不包含該鍵值
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
 * 判斷Hashtable內是否包含某資料值
 * @param {Object} value 要判斷的資料值
 * @return true,Hashtable內包含該資料值; false,Hashtable內不包含該資料值
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
 * 取得Hashtable內對應某鍵值的資料值 
 * @param {Object} key 搜尋的鍵值
 * @return {Object} value 對應的資料值
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
 * 判斷Hashtable是否為空的
 * @return true,Hashtable為空的; false,Hashtable不為空的
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
 * 取得Hashtable內的鍵值陣列
 * @return 鍵值陣列
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
 * 將資料值放入Hashtable內對應的鍵值
 * @param {Object} key 
 * @param {Object} value
 * @return 無
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
 * 移除Hashtable內對應的鍵值資料 
 * @param {Object} key 
 * @return 被移除的資料值
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
 * 取得Hashtable的資料長度
 * @return Hashtable的資料長度
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
 * 將Hashtable內所有的資料轉為字串型態表示
 * @return 字串型態表示的Hashtable內容
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
 * 取得Hashtable內的資料值陣列
 * @return Hashtable內的資料值陣列
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
 * 複製Hashtable物件
 * @return 複製的Hashtable物件
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