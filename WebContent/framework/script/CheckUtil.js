/** 
 * @fileoverview 定義檢查資料的&#x5171;用函式
 *
 * @author Welkin Fan
 * @version 1.0
 */

/**
 * 定義檢查資料的&#x5171;用函式Class
 * @constructor
 * @author Welkin Fan
 * @version 1.0
 */
function CheckUtil()
{
}

/**
 * 檢查統&#x4E00;編號格式
 * @param {String} str 處理的字串 
 * @return true,字串為統&#x4E00;編號格式; false,字串非統&#x4E00;編號格式
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
 * 檢查身份証字號格式
 * @param {String} str 處理的字串
 * @return true,字串為身份證字號格式; false,字串為非身份證字號格式
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
		
		$22_[0]=new Array("A","台北市","10");
		$22_[1]=new Array("B","台中市","11");
		$22_[2]=new Array("C","基隆市","12");
		$22_[3]=new Array("D","台南市","13");
		$22_[4]=new Array("E","高雄市","14");
		$22_[5]=new Array("F","台北縣","15");
		$22_[6]=new Array("G","宜蘭縣","16");
		$22_[7]=new Array("H","桃園縣","17");
		$22_[8]=new Array("J","新竹縣","18");
		$22_[9]=new Array("K","苗栗縣","19");
		$22_[10]=new Array("L","台中縣","20");
		$22_[11]=new Array("M","南投縣","21");
		$22_[12]=new Array("N","彰化縣","22");
		$22_[13]=new Array("P","雲林縣","23");
		$22_[14]=new Array("Q","嘉義縣","24");
		$22_[15]=new Array("R","台南縣","25");
		$22_[16]=new Array("S","高雄縣","26");
		$22_[17]=new Array("T","屏東縣","27");
		$22_[18]=new Array("U","花蓮縣","28");
		$22_[19]=new Array("V","台東縣","29");
		$22_[20]=new Array("W","金門縣","32");
		$22_[21]=new Array("X","澎湖縣","30");
		$22_[22]=new Array("Y","陽明山","31");
		$22_[23]=new Array("Z","馬祖","33");
		
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
 * 檢查民國日期格式
 * @param {String} str 處理的字串
 * @return true,字串為民國日期格式(YYYMMDD); false,字串非民國日期格式
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
 * 檢查西元日期格式
 * @param {String} str 處理的字串
 * @return true,字串為西元日期格式(YYYYMMDD); false,字串非西元日期格式
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
 * 檢查時間格式 
 * @param {String} str 處理的字串
 * @return true,字串為時間格式(HHMMSS); false,字串非時間格式
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
 * 檢查字串裡是否包含中文字
 * @param {String} str 字串 
 * @return true,字串包含中文字; false,字串不包含中文字
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