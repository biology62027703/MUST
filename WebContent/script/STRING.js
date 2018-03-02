//檢查數字
function chkNum(obj){
	if(isNaN(obj.value)){
	    return false;
	}
	return true;
}


function numChk(obj){
   if (obj.value==""){
	  return true;
   }
   if (!chkNum(obj)){
	  display('101','');
	  obj.focus();
	  return false; 
   }
   return true;
}

//檢查英文字母
function chkAlpha(obj){
	for(i=0;i<obj.value.length;i++){
		var value=obj.value.charAt(i)
	    if(value < "A" || value >"Z"&&value < "a"||value > "z") {
		    return false;
		}
	}
	return true;
}


function alphaChk(obj){
   if (obj.value==""){
	  return true;
   }
   if (!chkAlpha(obj)){
	  display('201','');
	  obj.focus();
	  return false;
   }
   return true;
}


//檢查是否為正確日期，6碼前面會加0，允許輸入負號(民前)
function chkDate(obj){
	if (obj.value.charAt(0)=="-")
	{
		if (obj.value.length==7)
			var instring=obj.value;
		else
			return false;
	}
	else
	{
		if (obj.value.length==6)
		{
			obj.value="0"+obj.value;
			var instring=obj.value;
		}
		else
			var instring=obj.value;
	}
	var year=parseFloat(instring.substring(0,3))+1911;
	var month=parseFloat(instring.substring(3,5))-1;
	var day=parseFloat(instring.substring(5,7));
	var indate=new Date(year,month,day);
	if (!(indate.getMonth()==parseFloat(instring.substring(3,5))-1))
	   return false;

	return true;
}


function dateChk(obj){
   if (obj.value==""){
	  return true;
   }
   if (!chkDate(obj)){
	  display('401','');
	  obj.focus();
	  return false;
   }
   return true;
}


function hourChk(obj)
{
	if (obj.value=="") return true;
	if (obj.value.length!=4)
	{
		display('402','');
		obj.focus();
		return false;
	}
	else
	{
		var hour=parseFloat(obj.value.substring(0,2));
		var minute=parseFloat(obj.value.substring(2,4));
		if (hour<0 || hour>=24 || minute<0 || minute>=60)
		{
			display('402','');
			obj.focus();
			return false;
		}
	}
	return true;
}


//檢查E-mail
function chkEmail(obj){
    if((obj.value.indexOf("@")==-1|| obj.value.indexOf(".")==-1)){
	   return false;
	}
	return true;
}


function emailChk(obj){
   if (obj.value==""){
	  return true;
   }
   if (!chkEmail(obj)){
      display('301','');
	  obj.focus();
	  return false;
   }
   return true;
}


//檢查身份證及統一編號
function chkID(obj)
{
 
	var UserID=obj.value;
	var IDlen = UserID.length;
	if (IDlen!=8 && IDlen!=10)
	{
		/*
		var result=confirm("你的身分證字號錯誤，確定使用按確定，不使用按取消。");
		if (result)
			return true;
		else
			return false;*/
		return true;
	}

	if (IDlen==8)
	{	
		var li_v1, li_v2, li_v3, li_v4, li_v5, li_v6, li_v7, li_v8;
		var ls_v1, ls_v2, ls_v3, ls_v4, ls_v5, ls_v6, ls_v7, ls_v8;
		var lb_ret1,lb_ret2, lb_retval;
		//乘上權數 
		ls_v1=fillzero(parseInt(UserID.substring(0,1))*1,2);
		ls_v2=fillzero(parseInt(UserID.substring(1,2))*2,2);
		ls_v3=fillzero(parseInt(UserID.substring(2,3))*1,2);
		ls_v4=fillzero(parseInt(UserID.substring(3,4))*2,2);
		ls_v5=fillzero(parseInt(UserID.substring(4,5))*1,2);
		ls_v6=fillzero(parseInt(UserID.substring(5,6))*2,2);
		ls_v7=fillzero(parseInt(UserID.substring(6,7))*4,2);
		ls_v8=fillzero(parseInt(UserID.substring(7,8))*1,2);
   
		//計算乘積和
		li_v1=parseInt(ls_v1.substring(0,1))+parseInt(ls_v1.substring(1,2));
		li_v2=parseInt(ls_v2.substring(0,1))+parseInt(ls_v2.substring(1,2));
		li_v3=parseInt(ls_v3.substring(0,1))+parseInt(ls_v3.substring(1,2));
		li_v4=parseInt(ls_v4.substring(0,1))+parseInt(ls_v4.substring(1,2));
		li_v5=parseInt(ls_v5.substring(0,1))+parseInt(ls_v5.substring(1,2));
		li_v6=parseInt(ls_v6.substring(0,1))+parseInt(ls_v6.substring(1,2));
		li_v7=parseInt(ls_v7.substring(0,1))+parseInt(ls_v7.substring(1,2));
		li_v8=parseInt(ls_v8.substring(0,1))+parseInt(ls_v8.substring(1,2));
		if (((li_v1+li_v2+li_v3+li_v4+li_v5+li_v6+li_v7+li_v8 )%10)==0) 
			lb_ret1=true;
		else
			lb_ret1=false; 
   
		if (li_v7==10) 
		{
			if (((li_v1+li_v2+li_v3+li_v4+li_v5+li_v6+ 1 +li_v8)%10)==0) 
				lb_ret2=true;
			else
				lb_ret2=false;
		}
		else
		{
			lb_ret2=false;
		}
		lb_retval = (lb_ret1 || lb_ret2);
   
		
		if (!lb_retval)
		 {
			 var result1=confirm("你的統一編號錯誤，確定使用按確定，不使用按取消。");
			 if (result1)
				return true;
			 else
				return false;
		 }

		return lb_retval;
	}


	if (IDlen==10)
	{
		//功能 : 檢查身分證號碼
		var Weight = new Array(10);  //定義加權值
		var Location = new Array(26);  //區域值轉換表
		var CheckNum, Temp, CheckSum, Flag;
		var i, j;
		var Sex, Place, HTMLcode, UserID2;

		Flag = false;
		CheckSum=0;
		UserID2 = UserID;
		//設定加權值初值
		Weight[0] = 1;
		for (i=1; i<10; i++)
			Weight[i] = 10 - i;

		//定義區域表的轉換值，沒有I,O
		Location[0] = new Array("A", "台北市", "10");
		Location[1] = new Array("B", "台中市", "11");
		Location[2] = new Array("C", "基隆市", "12");
		Location[3] = new Array("D", "台南市", "13");
		Location[4] = new Array("E", "高雄市", "14");
		Location[5] = new Array("F", "台北縣", "15");
		Location[6] = new Array("G", "宜蘭縣", "16");
		Location[7] = new Array("H", "桃園縣", "17");
		Location[8] = new Array("J", "新竹縣", "18");
		Location[9] = new Array("K", "苗栗縣", "19");
		Location[10] = new Array("L", "台中縣", "20");
		Location[11] = new Array("M", "南投縣", "21");
		Location[12] = new Array("N", "彰化縣", "22");
		Location[13] = new Array("P", "雲林縣", "23");
		Location[14] = new Array("Q", "嘉義縣", "24");
		Location[15] = new Array("R", "台南縣", "25");
		Location[16] = new Array("S", "高雄縣", "26");
		Location[17] = new Array("T", "屏東縣", "27");
		Location[18] = new Array("U", "花蓮縣", "28");
		Location[19] = new Array("V", "台東縣", "29");
		Location[20] = new Array("W", "金門縣", "32");
		Location[21] = new Array("X", "澎湖縣", "30");
		Location[22] = new Array("Y", "陽明山", "31");
		Location[23] = new Array("Z", "馬祖", "33");
		Location[24] = new Array("I", "嘉義市", "34");
		Location[25] = new Array("O", "新竹市", "35");

		Temp = UserID.substring(1,2);
		Sex = (Temp == "1") ? "男生" : "女生";

		Temp = UserID.substring(0,1);  //取得英文字母與檢查碼
		Temp = Temp.toUpperCase();
		CheckNum = parseInt(UserID.substring(UserID.length-1, UserID.length));
		for(i=0; i<26; i++)
		{
			if (Temp == Location[i][0])
			{
				//轉換成完整數字串列
				UserID = Location[i][2] + UserID.substring(1,UserID.length);
				Place = Location[i][1];  //取得出生地
				id_num     = parseInt(UserID.substring(0,1))   +
                      parseInt(UserID.substring(1,2)) *9+
                      parseInt(UserID.substring(2,3)) *8+
                      parseInt(UserID.substring(3,4)) *7+            
                      parseInt(UserID.substring(4,5)) *6+
                      parseInt(UserID.substring(5,6)) *5+             
                      parseInt(UserID.substring(6,7)) *4+
                      parseInt(UserID.substring(7,8)) *3+             
                      parseInt(UserID.substring(8,9)) *2+             
                      parseInt(UserID.substring(9,10))  +
                      parseInt(UserID.substring(10,11));        
     
			if ((id_num % 10) == 0) 
			{
				// 是身份証字號
				Flag = true;
			}
		}
     }
     
     if (!Flag)
     {
	     var result=confirm("你的身分證字號錯誤，確定使用按確定，不使用按取消。");
	     if (result)
			return true;
	     else
		    return false;
     }
     return Flag;
   }   
}


function checkFull(id)
{
	var flag=false;
	var i=0;
	var s="";
	for (i=0;i<id.length;i++)
	{
		s = id.charCodeAt(i);
		if (s>127)
		{
			flag=true;
			break;
		}
	}
	return flag;
}

function IDChk(obj){
   if (obj.value==""){
	  return true;
   }
   /*因為大陸人士身分證90入出12345678，所以不檢核
   var flag=checkFull(obj.value);
   	if (flag)
	{
		display("0","請輸入半形數字。");
		obj.focus();
		return false;
	}*/

   if (!chkID(obj)){
	  display('504','');
	  obj.focus();
	  return false;
   }
   return true;
}


function fillzero(num,limit) {
	var s = '';
	var temp='';
	s = num+'';
	temp= s;
	if(s.length<limit) {
	   for(i=0;i<(limit - s.length);i++) {
		temp = '0'+temp;
	   } 
	}
	return temp;
	
}

function trim(str1){
   // 功能 : 去除空白
	while(''+str1.charAt(str1.length-1) ==' ')
		str1 = str1.substring(0,str1.length-1);
	return str1;
}


//去除all空白
function alltrim(str1){
	var value="";
	//instring=instring.toString();

	for(var i=0;i<str1.length;i++){
		if(str1.charAt(i)!=" "){
			value=value+str1.charAt(i);}
	}
	return value;
}

//補0
function addzero(obj,num){
   if (obj.value=="") return;
   if (isNaN(obj.value)) return;
   var value=obj.value;
   if (obj.value.length<num){
	  while(num!=value.length){
		 value="0"+value;
	  }
	  obj.value=value;
   }
}


//address 住址物件
//  type  M,F(男,女):一般          C:法人
function setAddress(address,type)

{
	SpecialAddress = new Array('看守所','監獄','訓練所','戒治所','觀護所');
	var add = address.value;

	type = type.toUpperCase();

	if (add !='')
	{
		

		var char = add.substring(0,1);
		var char1 = '';
		if (add.length>1)
			char1 = add.substring(0,2);
			
		for (var i=0;i<SpecialAddress.length;i++)
		{
			if (add.indexOf(SpecialAddress[i])!=-1)
			{
	
				if (char=='住' || char=='設')
				{
					address.value = '現於' + add.substring(1,add.length);
					return;
				}else if (char1=='現於')
				{
					address.value = add;
					return;
				}else
				{
					address.value = '現於' + add;
					return ;
				}	
				
			}
		}

		if (char!='住' && char!='設')
		{

			if (type==null || type=='undefined' || type=='' || type=='M' || type=='F')
				address.value = '住' + add;
			else
				address.value = '設' + add;

		}


	}

}



function strTran(value,oldStr,newStr)
{
	var rv="";
	var str=value.split(oldStr);
	rv=str.join(newStr);
	return rv;
}

/**************************** 欄位資料,轉換格式  ****************************/
var numberA=new Array(
['0','０'],
['1','一'],
['2','二'],
['3','三'],
['4','四'],
['5','五'],
['6','六'],
['7','七'],
['8','八'],
['9','九']
);

var numberB=new Array(
['0','零'],
['1','一'],
['2','二'],
['3','三'],
['4','四'],
['5','五'],
['6','六'],
['7','七'],
['8','八'],
['9','九']
);

var mathUnit=new Array(
['十'],
['百'],
['千'],
['萬'],
['億'],
['兆']
);

var alphaA=new Array(
['A','Ａ'],
['B','Ｂ'],
['C','Ｃ'],
['D','Ｄ'],
['E','Ｅ'],
['F','Ｆ'],
['G','Ｇ'],
['H','Ｈ'],
['I','Ｉ'],
['J','Ｊ'],
['K','Ｋ'],
['L','Ｌ'],
['M','Ｍ'],
['N','Ｎ'],
['O','Ｏ'],
['P','Ｐ'],
['Q','Ｑ'],
['R','Ｒ'],
['S','Ｓ'],
['T','Ｔ'],
['U','Ｕ'],
['V','Ｖ'],
['W','Ｗ'],
['X','Ｘ'],
['Y','Ｙ'],
['Z','Ｚ'],
['a','Ａ'],
['b','Ｂ'],
['c','Ｃ'],
['d','Ｄ'],
['e','Ｅ'],
['f','Ｆ'],
['g','Ｇ'],
['h','Ｈ'],
['i','Ｉ'],
['j','Ｊ'],
['k','Ｋ'],
['l','Ｌ'],
['m','Ｍ'],
['n','Ｎ'],
['o','Ｏ'],
['p','Ｐ'],
['q','Ｑ'],
['r','Ｒ'],
['s','Ｓ'],
['t','Ｔ'],
['u','Ｕ'],
['v','Ｖ'],
['w','Ｗ'],
['x','Ｘ'],
['y','Ｙ'],
['z','Ｚ']
);

var otherA="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

function IdTrans(obj)
{
	for(i=0;i<obj.value.length;i++)
	{
		for(j=0;j<numberA.length;j++)
		{
			if (obj.value.charAt(i)==numberA[j][1])
			{
				return "";
			}
		}
	}
	//if (chkID(obj) && obj.value!="")
	if (obj.value!="")
	{
		var str=obj.value;
		var rv="";
		for(i=0;i<str.length;i++)
		{
			//轉英文字母
			for(j=0;j<alphaA.length;j++)
			{
				if (alphaA[j][0]==str.charAt(i))
				{
					rv+=alphaA[j][1];
					break;
				}
			}

			//轉數字
			for(j=0;j<numberA.length;j++)
			{
				if (numberA[j][0]==str.charAt(i))
				{
					rv+=numberA[j][1];
					break;
				}
			}

			//其他不是英文字母，數字
			for(j=0;j<otherA.length;j++)
			{
				if (otherA.indexOf(str.charAt(i))==-1)
				{
					rv+=str.charAt(i);
					break;
				}
				else
					break;
			}

		}
		obj.value=rv;
	}
}




function dateTrans(obj)
{
	for(i=0;i<obj.value.length;i++)
	{
		for(j=0;j<numberB.length;j++)
		{
			if (obj.value.charAt(i)==numberB[j][1])
			{
				return "";
			}
		}
	}

	
	var str="";
	if (dateChk(obj) && obj.value!="")
	{
		str+=mathTrans(obj.value.substring(0,3),true,true)+"年";
		str+=mathTrans(obj.value.substring(3,5),true,true)+"月";
		str+=mathTrans(obj.value.substring(5,7),true,true)+"日";
	}
	obj.value=str;
}


function hourTrans(obj)
{
	for(i=0;i<obj.value.length;i++)
	{
		for(j=0;j<numberB.length;j++)
		{
			if (obj.value.charAt(i)==numberB[j][1])
			{
				return "";
			}
		}
	}

	var str="";
	if (hourChk(obj) && obj.value!="")
	{
		if (parseFloat(obj.value.substring(0,2))>=0 && parseFloat(obj.value.substring(0,2))<=11)
		{	
			str+="上午";
			str+=mathTrans(obj.value.substring(0,2),true,true)+"時";
		}
		else
		{
			str+="下午";
			if (obj.value.substring(0,2)=="12")
				str+=mathTrans(obj.value.substring(0,2),true,true)+"時";
			else
				str+=mathTrans((parseFloat(obj.value.substring(0,2))-12)+"",true,true)+"時";
		}
		//str+=mathTrans(obj.value.substring(0,2),true,true)+"時";
		if (parseFloat(obj.value.substring(2,4))!=0)
			str+=mathTrans(obj.value.substring(2,4),true,true)+"分";

	}
	obj.value=str;
}

function numberTrans(obj)
{
	for(i=0;i<obj.value.length;i++)
	{
		for(j=0;j<numberA.length;j++)
		{
			if (obj.value.charAt(i)==numberA[j][1])
			{
				return "";
			}
		}
	}

	var str="";
	if (numChk(obj) && obj.value!="")
	{
		for(i=0;i<obj.value.length;i++)
		{
			for(j=0;j<numberA.length;j++)
			{
				if (String.fromCharCode(obj.value.charCodeAt(i))==numberA[j][0])
				{
					str+=numberA[j][1];
					break;
				}
			}
		}
	}
	obj.value=str;
}


//以四位數作處理，兩位是時間用的，flag是否將一十二的一拿掉，flag1是否將前後零去掉
function mathTrans(value,flag,flag1)
{
	var str="";
	for (i=0;i<value.length;i++)
	{
		for (j=0;j<numberB.length;j++)
		{
			if (value.charAt(i)==numberB[j][0])
			{
				if (value.charAt(i)=="0")
					str+=numberB[j][1];
				else
				{
					str+=numberB[j][1];
					if ((i+1)<value.length)
						str+=mathUnit[value.length-i-2];
				}
				break;
			}
		}
	}

	if (flag1)
		str=zeroTrim(str);

	if (str.indexOf("百")==-1 && str.indexOf("千")==-1)
	{
		if (str.indexOf("十")!=-1)
		{
			if (str.substring(0,1)=="一" && flag)
				str=str.substring(1,str.length);
		}
	}
	return str;
}

//將前後零去掉
function zeroTrim(value)
{
	var str="";
	for (i=0;i<value.length;i++)
	{
		if (value.charAt(i)=="零")
			str=value.substring(i+1,value.length);
		else
			break;
	}

	
	if (str!="")
		value=str;


	str="";
	
	for (i=value.length-1;i>=0;i--)
	{
		if (value.charAt(i)=="零")
			str=value.substring(0,i);
		else
			break;
	}

	if (str=="")
		str=value;

	str=str.replace("零零","零");

	return str;
}


function zeroReplace(value)
{
	var flag=true;
	if (value.indexOf("零零")!=-1)
	{
		while(flag)
		{
			value=value.replace("零零","零");
			if (value.indexOf("零零")==-1)
				flag=false;
			else
				flag=true;
		}
	}
	return value;
}

function ConvertStr(sBeConvert)
{
	var Result="";
	for(i=sBeConvert.length-1;i>=0;i--)
		  Result=Result+sBeConvert.charAt(i);
	return Result;
}


function Num2CNum(obj,type)
{

	var dblArabic=obj.value;
	var _ChineseNumeric =  "零一二三四五六七八九";
	var _ChineseNumeric0 = "零一二三四五六七八九";
	var _ChineseNumeric1 = "零壹貳參肆伍陸柒捌玖";
	var _ChineseNumeric2 = "０一二三四五六七八九";
	var sArabic="";
	var sIntArabic="";
	var iPosOfDecimalPoint=0;
	var iDigit=0;
	var sSectionArabic="";
	var sSection="";
	var bInZero=true;
	var bMinus=true;

	var Result="";

	sArabic=dblArabic;

	if(type==0)
		_ChineseNumeric=_ChineseNumeric0;
	if(type==1)
		_ChineseNumeric=_ChineseNumeric1;
	if(type==2)
		_ChineseNumeric=_ChineseNumeric2;  

	for(i=0;i<obj.value.length;i++)
	{
		for(j=0;j<_ChineseNumeric.length;j++)
		{
			if (obj.value.charAt(i)==_ChineseNumeric.charAt(j))
			{
				return "";
			}
		}
	}


	if (sArabic.charAt(0)=='-')
	{
		bMinus = true;
		sArabic = sArabic.substring(1,sArabic.length);
	}
	else
		bMinus = false;

	iPosOfDecimalPoint = sArabic.indexOf(".");  /* 取得小數點的位置 */
	/* 先處理整數的部分 */
	if (iPosOfDecimalPoint <= 0)
		sIntArabic = ConvertStr(sArabic);
	else
		sIntArabic = ConvertStr(sArabic.substring(0, iPosOfDecimalPoint));


	/* 從個位數起以每四位數為一小節 */
	for(iSection=0;iSection<=((sIntArabic.length-1)/4);iSection++)
	{  
		if((iSection * 4 + 4)<sIntArabic.length)
			sSectionArabic = sIntArabic.substring(iSection * 4 , iSection * 4 + 4);
		else
			sSectionArabic = sIntArabic.substring(iSection * 4 , sIntArabic.length); 
		sSection = "";
		/* 以下的 i 控制: 個十百千位四個位數 */
		for(i= 0; i<sSectionArabic.length;i++)
		{
			iDigit = parseInt(sSectionArabic.substring(i,i+1));
			//alert("iDigit="+iDigit);
			if (iDigit == 0) 
			{
		
				// 1. 避免 '零' 的重覆出現  
				// 2. 個位數的 0 不必轉成 '零' 
				if ((!bInZero) && (i != 0))
					sSection = "零" + sSection;
				bInZero = true;
			}
			else
			{
				if(type==1)  
                {
                   switch(i)
                   {
                       case 1 : sSection='拾' + sSection;break;
                       case 2 : sSection='佰' + sSection;break;
                       case 3 : sSection='仟' + sSection;break;
                   } 
                }
                else
                { 
                   switch(i)
                   {
                       case 1 : sSection='十' + sSection;break;
                       case 2 : sSection='百' + sSection;break;
                       case 3 : sSection='千' + sSection;break;
                   } 
                }
				
				sSection = _ChineseNumeric.substring(iDigit,iDigit+1) +sSection;

				bInZero = false;
			}
		}

		/* 加上該小節的位數 */
		if (sSection.length== 0)
		{
			if ((Result.length> 0) && Result.substring(0,1)=="零")
				Result = "零" + Result;
		}
		else
		{
			switch(iSection)
			{
				case 0: Result = sSection;break;
				case 1: Result = sSection + "萬" + Result;break;
				case 2: Result = sSection + "億" + Result;break;
				case 3: Result = sSection + "兆" + Result;break;
			}
		}
	}
	/* 處理小數點右邊的部分 */
	if (iPosOfDecimalPoint > 0)
	{
		Result=Result+ "點";
		for(i=iPosOfDecimalPoint;i<sArabic.length-1;i++)
		{
			iDigit =Integer.parseInt(sArabic.substring(i+1,i+2)); 
			Result=Result+ _ChineseNumeric.substring(iDigit,iDigit+1);
		}
	}

	/* 其他例外狀況的處理 */
	if (Result.length == 0)  Result= "零";
	if ((Result.length>=2) && (Result.substring(0,2)=="一十" || Result.substring(0,2)=="壹十")) 
		Result = Result.substring(1,Result.length);

	if (Result.substring(0,1)=="點")   
		Result = "零" + Result;

	/* 是否為負數 */
	if (bMinus) Result= "負" + Result;

	obj.value=Result;
}