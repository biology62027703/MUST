/** 
 * @fileoverview �w�q�B�z�}�C��&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q�B�z�}�C��&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function ArrayUtil()
{
}

/**
 * ���o�r��b�}�C�������ޭ�,�Y�r�ꤣ�s�b��}�C���h�Ǧ^-1
 * @param {Array} array �n�j�M���}�C 
 * @param {String} str �n�j�M���r�� 
 * @return �r��b�}�C�������ޭ�
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
 * ���o�r��b�G���}�C�������ޭ�,�Y�r�ꤣ�s�b��}�C���h�Ǧ^-1
 * @param {Array} array �n�j�M���}�C 
 * @param {Object} item ���w�j�M�G���}�C���Y�Ӷ���
 * @param {String} str �n�j�M���r��  
 * @return �r��b�}�C�������ޭ�
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
 * �h���}�C���e���䪺�ťզr��
 * @param {Array} array �n�B�z���}�C 
 * @return �h������ťի᪺�}�C 
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
 * �h���}�C���e�k�䪺�ťզr��
 * @param {Array} array �n�B�z���}�C 
 * @return �h���k��ťի᪺�}�C
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
 * �h���}�C���e���k�䪺�ťզr��
 * @param {Array} array �n�B�z���}�C 
 * @return �h�����k��ťի᪺�}�C 
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
 * �N�G���}�C�ھڪ��Y�Ӷ��ضi��Ƨ� 
 * @param {Array} array �n�B�z���}�C  
 * @param {Number} sortIndex �ƧǶ��ت����ޭ�
 * @return �����Ƨǫ᪺�G���}�C
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
 * �R���}�C�����w���ޭȪ�����
 * @param {Array} array �n�B�z���}�C 
 * @param {Number} deleteIndex �n�Q�R�����ت����ޭ�
 * @return �����R���᪺�}�C
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
 * �N�}�C�����w���ޭȪ����ع��
 * @param {Array} array - �n�B�z���}�C 
 * @param {Number} sourceIndex �ӷ����د��ޭ�
 * @param {Number} targetIndex �ت����د��ޭ�
 * @return ������ի᪺�}�C 
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
 * �N�}�C�ର�r��
 * @param {Array} array �n�B�z���}�C 
 * @param {Number} delimiter ���j�Ÿ�,�w�]���r��
 * @return �}�C����X�r��
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