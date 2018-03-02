/** 
 * @fileoverview 定義處理陣列的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義處理陣列的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function ArrayUtil()
{
}

/**
 * 取得字串在陣列中的索引值,若字串不存在於陣列中則傳回-1
 * @param {Array} array 要搜尋的陣列 
 * @param {String} str 要搜尋的字串 
 * @return 字串在陣列中的索引值
 * @type Number
 */
ArrayUtil.getIndex=function (array,str)
{
	try
	{
		for(var i=0;i<array.length;i++)
		{
			if(array[i]==str)
			{
				return i;
			}
		}
		return -1;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("ArrayUtil.getIndex",arguments,ex));
	}
}

/**
 * 取得字串在二維陣列中的索引值,若字串不存在於陣列中則傳回-1
 * @param {Array} array 要搜尋的陣列 
 * @param {Object} item 指定搜尋二維陣列的某個項目
 * @param {String} str 要搜尋的字串  
 * @return 字串在陣列中的索引值
 * @type Number
 */
ArrayUtil.getIndex2D=function (array,item,str)
{
	try
	{
		for(var i=0;i<array.length;++i)
		{
			if(array[i][item]==str)
			{
				return i;
			}
		}
		return -1;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("ArrayUtil.getIndex2D",arguments,ex));
	}
}

/**
 * 去除陣列內容左邊的空白字元
 * @param {Array} array 要處理的陣列 
 * @return 去除左邊空白後的陣列 
 * @type Array
 */
ArrayUtil.lTrim=function (array)
{
	try
	{
		for(var i=0;i<array.length;i++)
		{
			array[i]=StrUtil.lTrim(array[i]);
		}
		return array;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("ArrayUtil.lTrim",arguments,ex));
	}
}

/**
 * 去除陣列內容右邊的空白字元
 * @param {Array} array 要處理的陣列 
 * @return 去除右邊空白後的陣列
 * @type Array
 */
ArrayUtil.rTrim=function (array)
{
	try
	{
		for(var i=0;i<array.length;i++)
		{
			array[i]=StrUtil.rTrim(array[i]);
		}
		return array;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("ArrayUtil.rTrim",arguments,ex));
	}
}

/**
 * 去除陣列內容左右邊的空白字元
 * @param {Array} array 要處理的陣列 
 * @return 去除左右邊空白後的陣列 
 * @type Array
 */
ArrayUtil.trim=function (array)
{
	try
	{
		for(var i=0;i<array.length;i++)
		{
			array[i]=StrUtil.trim(array[i]);
		}
		return array;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("ArrayUtil.trim",arguments,ex));
	}
}

/**
 * 將二維陣列根據的某個項目進行排序 
 * @param {Array} array 要處理的陣列  
 * @param {Number} sortIndex 排序項目的索引值
 * @return 完成排序後的二維陣列
 * @type Number
 */
ArrayUtil.sort2D=function(array,sortIndex)
{
	try
	{
		var $55_=array;
		for(var i=$55_.length - 1;i >=0;i--)
		{
			for(var j=0;j<i;j++)
			{
				if($55_[j+1][sortIndex]<$55_[j][sortIndex])
				{
					var $56_=$55_[j];
					$55_[j]=$55_[j+1];
					$55_[j+1]=$56_;
				}
			}
		}
		return $55_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("ArrayUtil.2DSort",arguments,ex));
	}
}

/**
 * 刪除陣列中指定索引值的項目
 * @param {Array} array 要處理的陣列 
 * @param {Number} deleteIndex 要被刪除項目的索引值
 * @return 完成刪除後的陣列
 * @type Array
 */
ArrayUtil.remove=function(array,deleteIndex)
{
	try
	{
		var $62_=new Array();
		
		for(var i=0;i<array.length;i++)
		{
			if (i != deleteIndex)
			{
				$62_.push(array[i]);
			}
		}
		return $62_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("ArrayUtil.remove",arguments,ex));
	}
}

/**
 * 將陣列中指定索引值的項目對調
 * @param {Array} array - 要處理的陣列 
 * @param {Number} sourceIndex 來源項目索引值
 * @param {Number} targetIndex 目的項目索引值
 * @return 完成對調後的陣列 
 * @type Array
 */
ArrayUtil.move=function(array,sourceIndex,targetIndex)
{
	try
	{
		var $67_=array;
		var $68_=$67_[targetIndex];
		$67_[targetIndex]=$67_[sourceIndex];
		$67_[sourceIndex]=$68_;
		return $67_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("ArrayUtil.move",arguments,ex));
	}
}

/**
 * 將陣列轉為字串
 * @param {Array} array 要處理的陣列 
 * @param {Number} delimiter 分隔符號,預設為逗號
 * @return 陣列的轉出字串
 * @type String
 */
ArrayUtil.toString=function (array,delimiter)
{
	try
	{
		if (delimiter==null) delimiter=",";
		var $46_=new StringBuffer();
		for(var i=0;i<array.length;i++)
		{
			$46_.append((i==0)?array[i]:delimiter+array[i]);
		}
		return $46_.toString();
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("ArrayUtil.toString",arguments,ex));
	}
}