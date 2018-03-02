/** 
 * @fileoverview �w�q�B�z�����&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q�B�z�����&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function DateUtil()
{
}

/**
 * �榡�Ʀ褸���
 * @param {String} date ����r�� yyyymmdd
 * @return �褸��� yyyy/mm/dd 
 * @type String
 */
DateUtil.formatDate=function(date)
{
	try
	{
		var $1_=StrUtil.fillZero(new String(date.getYear(),10),4);
		var $2_=StrUtil.fillZero(new String(date.getMonth()+1,10),2);
		var $3_=StrUtil.fillZero(new String(date.getDate(),10),2);
		
		return $1_+"/"+$2_+"/"+$3_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.formatDate",arguments,ex));
	}
}

/**
 * ���o�褸�t�Τ��
 * @return �褸��� yyyy/mm/dd 
 * @type String
 */
DateUtil.getNowDate=function()
{
	try
	{
		return DateUtil.formatDate(new Date());
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.getNowDate",arguments,ex));
	}
}

/**
 * ���o����t�Τ��
 * @return ������ yyymmdd
 * @type String
 */
DateUtil.getNowCDate=function()
{
	try
	{
		var $4_=new Date();
		var $5_=StrUtil.fillZero(new String($4_.getYear()- 1911,10),3);
		var $6_=StrUtil.fillZero(new String($4_.getMonth()+1,10),2);
		var $7_=StrUtil.fillZero(new String($4_.getDate(),10),2);
		
		return $5_+$6_+$7_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.getNowCDate",arguments,ex));
	}
}

/**
 * ���o����t�Φ~��
 * @return ����~�� yyy
 * @type String
 */
DateUtil.getNowCYear=function()
{
	try
	{
		var $4_=new Date();
		var $5_=StrUtil.fillZero(new String($4_.getYear()- 1911,10),3);
		
		return $5_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.getNowCYear",arguments,ex));
	}
}

/**
 * �榡�Ƥ�����
 * @param {String} date ����r�� yyymmdd
 * @return ������ yyy/MM/dd
 * @type String
 */
DateUtil.formatCDate=function(date)
{
	try
	{
		var $9_=StrUtil.fillZero(date,7);
		
		return $9_.substring(0,3)+"/"+$9_.substring(3,5)+"/"+$9_.substring(5,7);
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.formatCDate",arguments,ex));
	}
}

/**
 * �榡�Ʀ褸�����������
 * @param {String} date �褸����r�� yyyy/mm/dd
 * @return ������ yyymmdd
 * @type String
 */
DateUtil.convert2CDate=function(date)
{
	try
	{
		var $11_=StrUtil.fillZero(date,7).split('/');
		var $12_=StrUtil.fillZero(new String(parseInt($11_[0])-1911),3);
		var $13_=$11_[1];
		var $14_=$11_[2];
		
		return $12_+$13_+$14_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.convert2CDate",arguments,ex));
	}
}

/**
 * �榡�Ƥ��������褸���
 * @param {String} date ����r�� yyymmdd 
 * @return �褸��� yyyy/mm/dd
 * @type String
 */
DateUtil.convert2Date=function(date)
{
	try
	{
		var $16_="";
		var $17_="";
		
		if(date.charAt(0)=="-")
		{
			if(date.length==7)
			{
				$16_=date;
				$17_=parseInt($16_.substring(0,3),10)+1912;
			}
			else
			{
				return "";
			}
		}
		else
		{
			$16_=((date.length==6)?"0"+date:date);
			$17_=(parseInt($16_.substring(0,3),10)+1911).toString();
		}
		
		var $18_=$16_.substring(3,5);
		var $19_=$16_.substring(5,7);
		var $20_=$17_+"/"+$18_+"/"+$19_;
		
		return $20_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.convert2Date",arguments,ex));
	}
}

/**
 * �p�⤤�����۶Z�Ѽ�
 * @param {String} sday �_�l��� yyyymmdd
 * @param {String} eday ������� yyyymmdd
 * @return �۶Z�Ѽ�
 * @type int
 */
DateUtil.getDayDiff=function(sday,eday)
{
	try
	{
		var $23_=new Date(DateUtil.convert2Date(sday));
		var $24_=new Date(DateUtil.convert2Date(eday));
		var $25_=24*60*60*1000;
		var $26_=$23_.getTime()+8*60*60*1000;
		var $27_=$24_.getTime()+8*60*60*1000;
		
		return parseInt(($26_ - $27_)/ $25_);
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.getDayDiff",arguments,ex));
	}
}

/**
 * �[�����
 *
 * <pre>
 * �ϥνd��: DateUtil.DateAdd("M", 1, "0930101"); //0930201
 * </pre>
 * @param {String} type �[����,�~('Y')/��('M')/��('D')
 * @param {Number} num �[��ƶq 
 * @param {String} date �p�⪺��� yyymmdd
 * @return �p����� yyymmdd
 * @type String
 */
DateUtil.DateAdd=function(type,num,date)
{
	try
	{
		var $31_=new Date(this.convert2Date(date));
		var $32_=$31_.getYear();
		var $33_=$31_.getMonth();
		var $34_=$31_.getDate();
		var $35_=null;
		
		switch (type.toUpperCase())
		{
			case "Y":
				$35_=new Date($32_+parseInt(num),$33_,$34_);
				break;
			case "M":
				$35_=new Date($32_,$33_+parseInt(num),$34_);
				break;
			case "D":
				$35_=new Date($32_,$33_,$34_+parseInt(num));
				break;
		}
		
		return DateUtil.convert2CDate(DateUtil.formatDate($35_));
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.DateAdd",arguments,ex));
	}
}

/**
 * ���o����~�몺�멳���
 *
 * <pre>
 * �ϥνd��: DateUtil.getEndOfMonth("09202"); //0920228
 * </pre>
 * @param {String} yyymm ����~�� yyymm
 * @return ������ yyymmdd
 * @type String
 */
DateUtil.getEndOfMonth=function(yyymm)
{
	try
	{
		var $37_,$38_,$39_;
		
		$37_=yyymm.substr(0,3)*1+1911;
		$38_=yyymm.substr(3,2);
		
		switch ($38_)
		{
			case "01":
			case "03":
			case "05":
			case "07":
			case "08":
			case "10":
			case "12":
				$39_=31;
				break;
			case "04":
			case "06":
			case "09":
			case "11":
				$39_=30;
				break;
			case "02":
				if($37_ % 4 !=0)
					$39_=28;
				else
					$39_=29;
				break;
			default :
		}
		
		return yyymm+$39_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.getEndOfMonth",arguments,ex));
	}
}

/**
 * ���o�۱q����1970�~1��1��ܤ���&#x6BEB;���
 * @return &#x6BEB;���
 * @type Number
 */
DateUtil.getTime=function()
{
	try
	{
		return (new Date()).getTime();
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.getTime",arguments,ex));
	}
}

/**
 * ���o�t�ήɶ�
 * @return �t�ήɶ� HHMM
 * @type String
 */
DateUtil.getNowTime=function()
{
	try
	{
		var $4_=new Date();
		var $5_=StrUtil.fillZero(new String($4_.getHours(),10),2)+StrUtil.fillZero(new String($4_.getMinutes(),10),2);
		
		return $5_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("DateUtil.getNowTime",arguments,ex));
	}
}