//�ˬd�Ʀr
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

//�ˬd�^��r��
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


//�ˬd�O�_�����T����A6�X�e���|�[0�A���\��J�t��(���e)
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


//�ˬdE-mail
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


//�ˬd�����ҤβΤ@�s��
function chkID(obj)
{
 
	var UserID=obj.value;
	var IDlen = UserID.length;
	if (IDlen!=8 && IDlen!=10)
	{
		/*
		var result=confirm("�A�������Ҧr�����~�A�T�w�ϥΫ��T�w�A���ϥΫ������C");
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
		//���W�v�� 
		ls_v1=fillzero(parseInt(UserID.substring(0,1))*1,2);
		ls_v2=fillzero(parseInt(UserID.substring(1,2))*2,2);
		ls_v3=fillzero(parseInt(UserID.substring(2,3))*1,2);
		ls_v4=fillzero(parseInt(UserID.substring(3,4))*2,2);
		ls_v5=fillzero(parseInt(UserID.substring(4,5))*1,2);
		ls_v6=fillzero(parseInt(UserID.substring(5,6))*2,2);
		ls_v7=fillzero(parseInt(UserID.substring(6,7))*4,2);
		ls_v8=fillzero(parseInt(UserID.substring(7,8))*1,2);
   
		//�p�⭼�n�M
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
			 var result1=confirm("�A���Τ@�s�����~�A�T�w�ϥΫ��T�w�A���ϥΫ������C");
			 if (result1)
				return true;
			 else
				return false;
		 }

		return lb_retval;
	}


	if (IDlen==10)
	{
		//�\�� : �ˬd�����Ҹ��X
		var Weight = new Array(10);  //�w�q�[�v��
		var Location = new Array(26);  //�ϰ���ഫ��
		var CheckNum, Temp, CheckSum, Flag;
		var i, j;
		var Sex, Place, HTMLcode, UserID2;

		Flag = false;
		CheckSum=0;
		UserID2 = UserID;
		//�]�w�[�v�Ȫ��
		Weight[0] = 1;
		for (i=1; i<10; i++)
			Weight[i] = 10 - i;

		//�w�q�ϰ���ഫ�ȡA�S��I,O
		Location[0] = new Array("A", "�x�_��", "10");
		Location[1] = new Array("B", "�x����", "11");
		Location[2] = new Array("C", "�򶩥�", "12");
		Location[3] = new Array("D", "�x�n��", "13");
		Location[4] = new Array("E", "������", "14");
		Location[5] = new Array("F", "�x�_��", "15");
		Location[6] = new Array("G", "�y����", "16");
		Location[7] = new Array("H", "��鿤", "17");
		Location[8] = new Array("J", "�s�˿�", "18");
		Location[9] = new Array("K", "�]�߿�", "19");
		Location[10] = new Array("L", "�x����", "20");
		Location[11] = new Array("M", "�n�뿤", "21");
		Location[12] = new Array("N", "���ƿ�", "22");
		Location[13] = new Array("P", "���L��", "23");
		Location[14] = new Array("Q", "�Ÿq��", "24");
		Location[15] = new Array("R", "�x�n��", "25");
		Location[16] = new Array("S", "������", "26");
		Location[17] = new Array("T", "�̪F��", "27");
		Location[18] = new Array("U", "�Ὤ��", "28");
		Location[19] = new Array("V", "�x�F��", "29");
		Location[20] = new Array("W", "������", "32");
		Location[21] = new Array("X", "���", "30");
		Location[22] = new Array("Y", "�����s", "31");
		Location[23] = new Array("Z", "����", "33");
		Location[24] = new Array("I", "�Ÿq��", "34");
		Location[25] = new Array("O", "�s�˥�", "35");

		Temp = UserID.substring(1,2);
		Sex = (Temp == "1") ? "�k��" : "�k��";

		Temp = UserID.substring(0,1);  //���o�^��r���P�ˬd�X
		Temp = Temp.toUpperCase();
		CheckNum = parseInt(UserID.substring(UserID.length-1, UserID.length));
		for(i=0; i<26; i++)
		{
			if (Temp == Location[i][0])
			{
				//�ഫ������Ʀr��C
				UserID = Location[i][2] + UserID.substring(1,UserID.length);
				Place = Location[i][1];  //���o�X�ͦa
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
				// �O�������r��
				Flag = true;
			}
		}
     }
     
     if (!Flag)
     {
	     var result=confirm("�A�������Ҧr�����~�A�T�w�ϥΫ��T�w�A���ϥΫ������C");
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
   /*�]���j���H�h������90�J�X12345678�A�ҥH���ˮ�
   var flag=checkFull(obj.value);
   	if (flag)
	{
		display("0","�п�J�b�μƦr�C");
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
   // �\�� : �h���ť�
	while(''+str1.charAt(str1.length-1) ==' ')
		str1 = str1.substring(0,str1.length-1);
	return str1;
}


//�h��all�ť�
function alltrim(str1){
	var value="";
	//instring=instring.toString();

	for(var i=0;i<str1.length;i++){
		if(str1.charAt(i)!=" "){
			value=value+str1.charAt(i);}
	}
	return value;
}

//��0
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


//address ��}����
//  type  M,F(�k,�k):�@��          C:�k�H
function setAddress(address,type)

{
	SpecialAddress = new Array('�ݦu��','�ʺ�','�V�m��','�٪v��','�[�@��');
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
	
				if (char=='��' || char=='�]')
				{
					address.value = '�{��' + add.substring(1,add.length);
					return;
				}else if (char1=='�{��')
				{
					address.value = add;
					return;
				}else
				{
					address.value = '�{��' + add;
					return ;
				}	
				
			}
		}

		if (char!='��' && char!='�]')
		{

			if (type==null || type=='undefined' || type=='' || type=='M' || type=='F')
				address.value = '��' + add;
			else
				address.value = '�]' + add;

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

/**************************** �����,�ഫ�榡  ****************************/
var numberA=new Array(
['0','��'],
['1','�@'],
['2','�G'],
['3','�T'],
['4','�|'],
['5','��'],
['6','��'],
['7','�C'],
['8','�K'],
['9','�E']
);

var numberB=new Array(
['0','�s'],
['1','�@'],
['2','�G'],
['3','�T'],
['4','�|'],
['5','��'],
['6','��'],
['7','�C'],
['8','�K'],
['9','�E']
);

var mathUnit=new Array(
['�Q'],
['��'],
['�d'],
['�U'],
['��'],
['��']
);

var alphaA=new Array(
['A','��'],
['B','��'],
['C','��'],
['D','��'],
['E','��'],
['F','��'],
['G','��'],
['H','��'],
['I','��'],
['J','��'],
['K','��'],
['L','��'],
['M','��'],
['N','��'],
['O','��'],
['P','��'],
['Q','��'],
['R','��'],
['S','��'],
['T','��'],
['U','��'],
['V','��'],
['W','��'],
['X','��'],
['Y','��'],
['Z','��'],
['a','��'],
['b','��'],
['c','��'],
['d','��'],
['e','��'],
['f','��'],
['g','��'],
['h','��'],
['i','��'],
['j','��'],
['k','��'],
['l','��'],
['m','��'],
['n','��'],
['o','��'],
['p','��'],
['q','��'],
['r','��'],
['s','��'],
['t','��'],
['u','��'],
['v','��'],
['w','��'],
['x','��'],
['y','��'],
['z','��']
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
			//��^��r��
			for(j=0;j<alphaA.length;j++)
			{
				if (alphaA[j][0]==str.charAt(i))
				{
					rv+=alphaA[j][1];
					break;
				}
			}

			//��Ʀr
			for(j=0;j<numberA.length;j++)
			{
				if (numberA[j][0]==str.charAt(i))
				{
					rv+=numberA[j][1];
					break;
				}
			}

			//��L���O�^��r���A�Ʀr
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
		str+=mathTrans(obj.value.substring(0,3),true,true)+"�~";
		str+=mathTrans(obj.value.substring(3,5),true,true)+"��";
		str+=mathTrans(obj.value.substring(5,7),true,true)+"��";
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
			str+="�W��";
			str+=mathTrans(obj.value.substring(0,2),true,true)+"��";
		}
		else
		{
			str+="�U��";
			if (obj.value.substring(0,2)=="12")
				str+=mathTrans(obj.value.substring(0,2),true,true)+"��";
			else
				str+=mathTrans((parseFloat(obj.value.substring(0,2))-12)+"",true,true)+"��";
		}
		//str+=mathTrans(obj.value.substring(0,2),true,true)+"��";
		if (parseFloat(obj.value.substring(2,4))!=0)
			str+=mathTrans(obj.value.substring(2,4),true,true)+"��";

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


//�H�|��Ƨ@�B�z�A���O�ɶ��Ϊ��Aflag�O�_�N�@�Q�G���@�����Aflag1�O�_�N�e��s�h��
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

	if (str.indexOf("��")==-1 && str.indexOf("�d")==-1)
	{
		if (str.indexOf("�Q")!=-1)
		{
			if (str.substring(0,1)=="�@" && flag)
				str=str.substring(1,str.length);
		}
	}
	return str;
}

//�N�e��s�h��
function zeroTrim(value)
{
	var str="";
	for (i=0;i<value.length;i++)
	{
		if (value.charAt(i)=="�s")
			str=value.substring(i+1,value.length);
		else
			break;
	}

	
	if (str!="")
		value=str;


	str="";
	
	for (i=value.length-1;i>=0;i--)
	{
		if (value.charAt(i)=="�s")
			str=value.substring(0,i);
		else
			break;
	}

	if (str=="")
		str=value;

	str=str.replace("�s�s","�s");

	return str;
}


function zeroReplace(value)
{
	var flag=true;
	if (value.indexOf("�s�s")!=-1)
	{
		while(flag)
		{
			value=value.replace("�s�s","�s");
			if (value.indexOf("�s�s")==-1)
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
	var _ChineseNumeric =  "�s�@�G�T�|�����C�K�E";
	var _ChineseNumeric0 = "�s�@�G�T�|�����C�K�E";
	var _ChineseNumeric1 = "�s���L�Ѹv��m�èh";
	var _ChineseNumeric2 = "���@�G�T�|�����C�K�E";
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

	iPosOfDecimalPoint = sArabic.indexOf(".");  /* ���o�p���I����m */
	/* ���B�z��ƪ����� */
	if (iPosOfDecimalPoint <= 0)
		sIntArabic = ConvertStr(sArabic);
	else
		sIntArabic = ConvertStr(sArabic.substring(0, iPosOfDecimalPoint));


	/* �q�Ӧ�ư_�H�C�|��Ƭ��@�p�` */
	for(iSection=0;iSection<=((sIntArabic.length-1)/4);iSection++)
	{  
		if((iSection * 4 + 4)<sIntArabic.length)
			sSectionArabic = sIntArabic.substring(iSection * 4 , iSection * 4 + 4);
		else
			sSectionArabic = sIntArabic.substring(iSection * 4 , sIntArabic.length); 
		sSection = "";
		/* �H�U�� i ����: �ӤQ�ʤd��|�Ӧ�� */
		for(i= 0; i<sSectionArabic.length;i++)
		{
			iDigit = parseInt(sSectionArabic.substring(i,i+1));
			//alert("iDigit="+iDigit);
			if (iDigit == 0) 
			{
		
				// 1. �קK '�s' �����ХX�{  
				// 2. �Ӧ�ƪ� 0 �����ন '�s' 
				if ((!bInZero) && (i != 0))
					sSection = "�s" + sSection;
				bInZero = true;
			}
			else
			{
				if(type==1)  
                {
                   switch(i)
                   {
                       case 1 : sSection='�B' + sSection;break;
                       case 2 : sSection='��' + sSection;break;
                       case 3 : sSection='�a' + sSection;break;
                   } 
                }
                else
                { 
                   switch(i)
                   {
                       case 1 : sSection='�Q' + sSection;break;
                       case 2 : sSection='��' + sSection;break;
                       case 3 : sSection='�d' + sSection;break;
                   } 
                }
				
				sSection = _ChineseNumeric.substring(iDigit,iDigit+1) +sSection;

				bInZero = false;
			}
		}

		/* �[�W�Ӥp�`����� */
		if (sSection.length== 0)
		{
			if ((Result.length> 0) && Result.substring(0,1)=="�s")
				Result = "�s" + Result;
		}
		else
		{
			switch(iSection)
			{
				case 0: Result = sSection;break;
				case 1: Result = sSection + "�U" + Result;break;
				case 2: Result = sSection + "��" + Result;break;
				case 3: Result = sSection + "��" + Result;break;
			}
		}
	}
	/* �B�z�p���I�k�䪺���� */
	if (iPosOfDecimalPoint > 0)
	{
		Result=Result+ "�I";
		for(i=iPosOfDecimalPoint;i<sArabic.length-1;i++)
		{
			iDigit =Integer.parseInt(sArabic.substring(i+1,i+2)); 
			Result=Result+ _ChineseNumeric.substring(iDigit,iDigit+1);
		}
	}

	/* ��L�ҥ~���p���B�z */
	if (Result.length == 0)  Result= "�s";
	if ((Result.length>=2) && (Result.substring(0,2)=="�@�Q" || Result.substring(0,2)=="���Q")) 
		Result = Result.substring(1,Result.length);

	if (Result.substring(0,1)=="�I")   
		Result = "�s" + Result;

	/* �O�_���t�� */
	if (bMinus) Result= "�t" + Result;

	obj.value=Result;
}