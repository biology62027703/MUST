/** 
 * @fileoverview �w�q�ˬd��ƪ�&#x5171;�Ψ禡
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * �w�q�ˬd��ƪ�&#x5171;�Ψ禡Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function CheckUtil()
{
}

/**
 * �ˬd��&#x4E00;�s���榡
 * @param {String} str �B�z���r�� 
 * @return true,�r�ꬰ��&#x4E00;�s���榡; false,�r��D��&#x4E00;�s���榡
 * @type Boolean
 */
CheckUtil.isTaxID=function(str)
{
	try
	{
		if(str.length !=8) return false;
		var $2_,$3_,$4_,$5_,$6_,$7_,$8_,$9_;
		var $10_,$11_,$12_,$13_,$14_,$15_,$16_,$17_;
		var $18_,$19_,$20_;
		
		$10_=StrUtil.fillZero((str.substring(0,1)*1)+'',2);
		$11_=StrUtil.fillZero((str.substring(1,2)*2)+'',2);
		$12_=StrUtil.fillZero((str.substring(2,3)*1)+'',2);
		$13_=StrUtil.fillZero((str.substring(3,4)*2)+'',2);
		$14_=StrUtil.fillZero((str.substring(4,5)*1)+'',2);
		$15_=StrUtil.fillZero((str.substring(5,6)*2)+'',2);
		$16_=StrUtil.fillZero((str.substring(6,7)*4)+'',2);
		$17_=StrUtil.fillZero((str.substring(7,8)*1)+'',2);
		$2_=parseInt($10_.substring(0,1))+parseInt($10_.substring(1,2));
		$3_=parseInt($11_.substring(0,1))+parseInt($11_.substring(1,2));
		$4_=parseInt($12_.substring(0,1))+parseInt($12_.substring(1,2));
		$5_=parseInt($13_.substring(0,1))+parseInt($13_.substring(1,2));
		$6_=parseInt($14_.substring(0,1))+parseInt($14_.substring(1,2));
		$7_=parseInt($15_.substring(0,1))+parseInt($15_.substring(1,2));
		$8_=parseInt($16_.substring(0,1))+parseInt($16_.substring(1,2));
		$9_=parseInt($17_.substring(0,1))+parseInt($17_.substring(1,2));
		if((($2_+$3_+$4_+$5_+$6_+$7_+$8_+$9_)% 10)==0)
			$18_=true;else $18_=false;
		if($8_==10)
		{
			if((($2_+$3_+$4_+$5_+$6_+$7_+1+$9_)% 10)==0)
				$19_=true;
			else
				$19_=false;
		}
		else
			$19_=false;
		$20_=($18_||$19_);
		return $20_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("CheckUtil.isTaxID",arguments,ex));
	}
}

/**
 * �ˬd�������r���榡
 * @param {String} str �B�z���r��
 * @return true,�r�ꬰ�����Ҧr���榡; false,�r�ꬰ�D�����Ҧr���榡
 * @type Boolean
 */
CheckUtil.isID=function(str)
{
	try
	{
		if(str.length !=10) return false;
		
		var $22_=new Array(24);
		var $23_=false;
		var $24_="";
		
		$22_[0]=new Array("A","�x�_��","10");
		$22_[1]=new Array("B","�x����","11");
		$22_[2]=new Array("C","�򶩥�","12");
		$22_[3]=new Array("D","�x�n��","13");
		$22_[4]=new Array("E","������","14");
		$22_[5]=new Array("F","�x�_��","15");
		$22_[6]=new Array("G","�y����","16");
		$22_[7]=new Array("H","��鿤","17");
		$22_[8]=new Array("J","�s�˿�","18");
		$22_[9]=new Array("K","�]�߿�","19");
		$22_[10]=new Array("L","�x����","20");
		$22_[11]=new Array("M","�n�뿤","21");
		$22_[12]=new Array("N","���ƿ�","22");
		$22_[13]=new Array("P","���L��","23");
		$22_[14]=new Array("Q","�Ÿq��","24");
		$22_[15]=new Array("R","�x�n��","25");
		$22_[16]=new Array("S","������","26");
		$22_[17]=new Array("T","�̪F��","27");
		$22_[18]=new Array("U","�Ὤ��","28");
		$22_[19]=new Array("V","�x�F��","29");
		$22_[20]=new Array("W","������","32");
		$22_[21]=new Array("X","���","30");
		$22_[22]=new Array("Y","�����s","31");
		$22_[23]=new Array("Z","����","33");
		
		for(var i=0;i<24;i++)
		{
			if(str.substring(0,1).toUpperCase()==$22_[i][0])
			{
				str=$22_[i][2]+str.substring(1,str.length);
				$24_=parseInt(str.substring(0,1))+parseInt(str.substring(1,2))*9+parseInt(str.substring(2,3))*8+parseInt(str.substring(3,4))*7+parseInt(str.substring(4,5))*6+parseInt(str.substring(5,6))*5+parseInt(str.substring(6,7))*4+parseInt(str.substring(7,8))*3+parseInt(str.substring(8,9))*2+parseInt(str.substring(9,10))+parseInt(str.substring(10,11));
				
				if(($24_ % 10)==0)
					$23_=true;
			}
		}
		
		return $23_;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("CheckUtil.isID",arguments,ex));		
	}
}

/**
 * �ˬd�������榡
 * @param {String} str �B�z���r��
 * @return true,�r�ꬰ�������榡(YYYMMDD); false,�r��D�������榡
 * @type Boolean
 */
CheckUtil.isCDate=function($25_)
{
	try
	{
		if($25_.length !=7) return false;
		
		var $26_=$25_.substring(0,3)*1+1911;
		var $27_=$25_.substring(3,5);
		var $28_=$25_.substring(5,7);		
		var $29_=""+$26_+$27_+$28_;
		
		return CheckUtil.isDate($29_);
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("CheckUtil.isCDate",arguments,ex));
	}
}

/**
 * �ˬd�褸����榡
 * @param {String} str �B�z���r��
 * @return true,�r�ꬰ�褸����榡(YYYYMMDD); false,�r��D�褸����榡
 * @type Boolean
 */
CheckUtil.isDate=function(str)
{
	try
	{
		if(str.length !=8) return false;
		
		var $31_=str.substring(0,4)*1;
		var $32_=str.substring(4,6)*1;
		var $33_=str.substring(6,8)*1;
		
		if($32_< 1||$32_ > 12) return false;
		if($33_< 1||$33_ > 31) return false;
		if(($32_==4||$32_==6||$32_==9||$32_==11)&&$33_==31) return false;
		if($32_==2)
		{
			var $34_=($31_ % 4==0&&($31_ % 100 !=0||$31_ % 400==0));

			if($33_ > 29||($33_==29&&!$34_)) return false;
		}
		return true;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("CheckUtil.isDate",arguments,ex));
	}
}

/**
 * �ˬd�ɶ��榡 
 * @param {String} str �B�z���r��
 * @return true,�r�ꬰ�ɶ��榡(HHMMSS); false,�r��D�ɶ��榡
 * @type Boolean
 */
CheckUtil.isTime=function(str)
{
	try
	{
		if (str.length != 4 && str.length != 6) return false;
		else if (str.length == 4)
		{
			var $36_=str.substring(0,2)*1;
			var $37_=str.substring(2,4)*1;
			if($36_ < 0||$36_ >=24) return false;
			if($37_ < 0||$37_ >=60) return false;
		}
		else
		{
			var $36_=str.substring(0,2)*1;
			var $37_=str.substring(2,4)*1;
			var $38_=str.substring(4,6)*1;
			
			if($36_ < 0||$36_ >=24) return false;
			if($37_ < 0||$37_ >=60) return false;
			if($38_ < 0||$38_ >=60) return false;
		}
		return true;
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("CheckUtil.isTime",arguments,ex));
	}
}

/**
 * �ˬd�r��̬O�_�]�t����r
 * @param {String} str �r�� 
 * @return true,�r��]�t����r; false,�r�ꤣ�]�t����r
 * @type Boolean
 */
CheckUtil.isBig5=function(str)
{
	try
	{
		if(StrUtil.getBLen(str) != str.length)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	catch(ex)
	{
		throw new Error(getExceptionStr("CheckUtil.isBig5",arguments,ex));
	}
}