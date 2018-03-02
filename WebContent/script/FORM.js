String.prototype.isEmpty=isEmpty;
String.prototype.trimSpace=trimSpace;
String.prototype.LRtrim=LRtrim;
/*********************form onload start*****************************/
function formObjectAddon()
{
	eventSet();
}

//
function eventSet()
{
	document.forms[0].onsubmit=submitCheck;
}

//TextBox �� constructor
function textBox(obj)
{
	//properties
	this.checkType=obj.check.charAt(0);
	this.allowNull=obj.check.charAt(1);
	this.minLength=obj.check.substring(2);
	this.value=obj.value.trimSpace();
	//method
	this.focus=obj.focus;
}


//form�e�^server �e��Check
function submitCheck(flag)
{
	var inputobj=document.all.tags("input");
	for(var j=0;j<inputobj.length;j++)
	{
		if( (inputobj[j].type=="text" || inputobj[j].type=="password") && inputobj[j].check)
		{   
			var boxobj=new textBox(inputobj[j]);
			
			//�N�ȥh�ťզ^�Ǧ^�h			
			if (flag)
				inputobj[j].value=inputobj[j].value.trimSpace();
			else
				inputobj[j].value=inputobj[j].value.LRtrim();

			if(boxobj.allowNull==0 && boxobj.value.isEmpty() && inputobj[j].disabled==false)
			{
				display('601','');
				boxobj.focus();
 				return false;
			}
		}
	}
	
	var textareaobj=document.all.tags("textarea");
	for(var j=0;j<textareaobj.length;j++)
	{
		if(textareaobj[j].check &&
         textareaobj[j].disabled != true)
		{   
			var boxobj=new textBox(textareaobj[j]);

			if(boxobj.allowNull==0 && boxobj.value.isEmpty())
			{
				display('601','');
				boxobj.focus();
 				return false;
			}
		}
	}
	return true;
}

//disabled submit��
function disabled(flag)
{
	for(i=0;i<document.forms[0].elements.length;i++)
	{
		if (document.forms[0].elements[i].type=="submit")
		{
			if (flag)
				document.forms[0].elements[i].disabled=true;
			else 
				document.forms[0].elements[i].disabled=false;	
			break;
		}
	}
}

function disabledText(flag)
{
	for(i=0;i<document.forms[0].elements.length;i++)
		if (document.forms[0].elements[i].type=="text")
			document.forms[0].elements[i].disabled = flag;
}

//��ư϶����ˮ�
function checkBetween(sobj, eobj)
{
	if( sobj.value*1 <= eobj.value*1 )
		return true;
	else
		return false;
}

function checkSE(sobj, eobj, field)
{
	if( sobj.value=="" && eobj.value!="" )
	{
		showInfoBar("INFO","��J"+field+"���ȮɡA�_�Ȥ��i���ť� ");
		sobj.focus();
		return false;
	}
	if( sobj.value!="" && eobj.value=="" )
	{
		showInfoBar("INFO","��J"+field+"�_�ȮɡA���Ȥ��i���ť� ");
		eobj.focus();
		return false;
	}
	if( sobj.value!="" && eobj.value!="" )
	{
		if( !checkBetween(sobj, eobj) )
		{
			showInfoBar("INFO","��J"+field+"���W�餣�o�p��_��");
			eobj.focus();
			return false;
		}				
	}
	return true;
}

function lengthCheck()
{
	var inputObj = document.getElementsByTagName('input');
	for(var i = 0; i < inputObj.length; i++){
		if(inputObj[i].type == "text" && !inputObj[i].disabled){
			var result = checkLength(inputObj[i]);
			if (!result){
				return false;
			}
		}
	}
	
	return true;
}

function onblurCheck(){



	var inputobj=document.all.tags("input");



	for(var j=0;j<inputobj.length;j++){



		//if(inputobj[j].type=="text" && inputobj[j].check){



		if(inputobj[j].type=="text" && inputobj[j].check && inputobj[j].disabled != true){



			var boxobj=new textBox(inputobj[j]);



			var result=checkLength(inputobj[j]);



			if (!result) {

			

			return false;

			}

			result=checkType(boxobj.checkType,inputobj[j]);



			if (!result) {

			

			return false;

			}



		}



	}

	

	return true;



}
//�ˬdtext���r�����סAmaxLength��L�n�j�g�A��]�����D
function checkLength(object)
{
	var s,i=0,char_count=0,result=true;
	object_width=object.maxLength;
	//alert(object_width);
        object_text=object.value; 
	
	while (i++<object_text.length)
	{
		s = object_text.charCodeAt(i-1);
		//alert(s);
		if (s<127)
			char_count++;
		else
			char_count+=2;
                /*
		if (char_count%object_width==0 && char_count!=0)
		{	
			result=false;
			object.value=object_text.substring(0,i);
			break;
		}*/
		//alert(char_count);
		//if (char_count%(object_width+1)==0 && char_count!=0)
		if (char_count>object_width)
		{	
			result=false;
			//object.value=object_text.substring(0,i-1);
			break;
		}
	}
	if (result==false)
	{
	    display('701','');
	    object.focus();
	    return false;
	}   
        return true;	
}



//�ˬdtextarea���r�����סAmaxlength��l�n�p�g�A��]�����D
function checkAreaLength(object,value)
{
	var s,i=0,char_count=0,result=true;
	object_width=object.maxlength;
	//alert(object_width);
    object_text=value; 
	
	while (i++<object_text.length)
	{
		s = object_text.charCodeAt(i-1);
		//alert(s);
		if (s<127)
			char_count++;
		else
			char_count+=2;
		//alert("char_count="+char_count);
		//alert("object_width="+object_width);
		if (char_count>object_width)
		{	
			result=false;
			//object.value=object_text.substring(0,i-1);
			break;
		}
	}
	if (result==false)
	{
		display("0","��J�r�ꤣ��"+object_width/2+"�W�L�Ӥ���r�C");
	    object.focus();
	    return false;
	}   
	return true;	
}



//�U�خ榡�ˬd
function checkType(chkType,boxobj)
{
	switch(chkType)
	{
		case "F":return numChk(boxobj);break;
		case "A":return alphaChk(boxobj);break;
		case "E":return emailChk(boxobj);break;
		case "I":return IDChk(boxobj);break;
		case "D":return dateChk(boxobj);break;
		case "H":return hourChk(boxobj);break;
		case "U":return true;break;
	}
}

//onkeydown ��enter��
function enter()
{
	if(document.forms[0] != null){
		for(i=0;i<document.forms[0].elements.length;i++)
		{
			if (document.forms[0].elements[i].type=="text" && document.forms[0].elements[i].check)
				document.forms[0].elements[i].onkeydown=down;
		}
	}
}
//focus����submit��
function down()
{
	var is_find=false; //�P�_���S�����submit����,�䤣��h��button�� 
	for(i=0;i<document.forms[0].elements.length;i++)
	{
		if (document.forms[0].elements[i].type=="submit")
		{
			is_find=true;
			break;
		}
	}
	if(is_find==false)
	{
		for(i=0;i<document.forms[0].elements.length;i++)
		{
			if (document.forms[0].elements[i].type=="button") break;
		}
	}   
   
	if (event.keyCode==13) document.forms[0].elements[i].focus();
} 


function initTrim()
{
	for(i=0;i<document.forms[0].elements.length;i++)
	{
		if (document.forms[0].elements[i].type=="text")
		{
			document.forms[0].elements[i].value=document.forms[0].elements[i].value.LRtrim();
		}
	}
}


function appendSpace()
{
	var inputobj=document.all.tags("input");
	for(var j=0;j<inputobj.length;j++)
	{
		if(inputobj[j].type=="text" && inputobj[j].check)
		{
			var boxobj=new textBox(inputobj[j]);
			if (boxobj.allowNull=="1" && boxobj.minLength!="0" && inputobj[j].value.LRtrim().length==0)
			{
				//alert(inputobj[j].value.length);
				var num=getLengthBy(boxobj.minLength);
				var temp="";
				for (i=0;i<num;i++)
				{
					temp=temp+" ";
				}
				inputobj[j].value=temp;
			}
		}
	}
}
/*********************form onload end*****************************/


/*********************check function start*****************************/
//�h���ť�
function trimSpace()
{
	var instring=this.toString();
	var value="";
	for(var i=0;i<instring.length;i++)
	{
		if(instring.charAt(i)!=" ")	value=value+instring.charAt(i);
	}
	return value;
}


function LRtrim()
{
	var instring=this.toString();
	while(instring.charAt(0)==" ")
	{
		instring=instring.substring(1,instring.length);
	}
	while(instring.charAt(instring.length-1)==" ")
	{
		instring=instring.substring(0,instring.length-1);
	}
	return instring;
}

//�ˬd�O�_�ť�
function isEmpty()
{
	var instring=this.toString()
	return ( instring=="" || instring==null )?true:false;
}
/*********************check function end*****************************/


/*********************��Lfunction start*****************************/
//�Ʀr��w(�ϥ�KeyPressĲ�o)�A���\��J�t��
function lockNum()
{
	if (event.keyCode==13) return;
	if ((event.keyCode < 48) || (event.keyCode > 58)) 
	{
		if (event.keyCode!=45)
			event.keyCode = 0;
	}
}

//�Ʀr��w(�ϥ�KeyPressĲ�o)�A���\��J�r��
function lockSpecChar()
{
	if (event.keyCode==13) return;
	if ((event.keyCode < 48) || (event.keyCode > 58)) 
	{
		if (event.keyCode!=45 && event.keyCode!=44)
			event.keyCode = 0;
	}
}

//�B�I�Ʀr��w(�ϥ�KeyPressĲ�o)
function lockFloat()
{
	if (event.keyCode==13) return;
	if ((event.keyCode < 46) || (event.keyCode > 58)) 
		event.keyCode = 0;
}

//�^��r����w(�ϥ�KeyPressĲ�o)
function lockAlpha()
{
	if (event.keyCode==13) return;
	if ((event.keyCode < 65) || (event.keyCode > 90&&event.keyCode <97 ||event.keyCode >122 ))
		event.keyCode = 0;
}

//�^��r����w�j�g�A��l�r�����ˬd(�ϥ�KeyPressĲ�o)
function lockUpper()
{
	if (event.keyCode==13) return;
	if ((event.keyCode >= 97) && (event.keyCode <= 122))
		event.keyCode = event.keyCode-32;
}


/*********************��Lfunction end*****************************/
//�Ǧ^�r����סA�������^��@
function getLength(object)
{
		
	var s,i=0,char_count=0,result=true;
	object_width=object.maxlength
        object_text=object.value; 
	
	while (i++<object_text.length)
	{
		s = object_text.charCodeAt(i-1);
		if (s<127)
			char_count++;
		else
			char_count+=2;
	}
	return char_count;
}
//���_�I���r��A�Ҧp���11�Ӧr�ɡA�|�I���e10�Ӧr�A���text1�A�өҦ����r�|����text2	
//�ϥΤ覡   onfocus="timer1=setInterval('cutLine(form1.text1,form1.text2,20)', 200);" onblur="clearInterval(timer1);"		
function cutLine(text1,text2,len)
{
	
	if ((text1.value + '') !='undefined')
	{
		
	
		var len1 = getLength(text1);
		if ((len1)>len)
		{
			text2.focus();	
			text2.value = text1.value;
			text1.value = getString(text1,len);
			
		}
	
	}		
}
//�Ǧ^�n�I�����r��A�������^��@(�@�Ӥ���r 2�Ӫ��סA�@�ӭ^��r 1�Ӫ���) 
function getString(object,len)
{
	var s,i=0,char_count=0;
        object_text=object.value; 
	var retVal = '';
	while (i++<object_text.length)
	{
		
			s = object_text.charCodeAt(i-1);
		if (s<127)
			char_count++;
		else
			char_count+=2;
		if (char_count<=len)
		{
			retVal = retVal + object_text.charAt(i-1);
		}	
	}
	return retVal;
}

function cutStringByByte(str,bytes)
{
	var s="";
	var i=0;
	if (str.length!=0)
	{
		i=cutStringPosition(str,bytes);
		s=str.substring(0,i);
	}
	return s;
}


function cutStringPosition(str,bytes)
{
	var count=0;
	var i=0;
	var s=0;
	while (i++<str.length)
	{
		s=str.charCodeAt(i-1);
		if (s<127)
			count++;
		else
			count+=2;
		
		if (count>bytes) break;
	}
	return i-1;
}

function getLengthBy(value)
{
	var rv=0;
	if (value.charCodeAt(0)>=65 && value.charCodeAt(0)<=90)
		rv=value.charCodeAt(0)-55;
	else
		rv=value;
	return rv;
}

function checkboxChecked(objstr)
{
	var str=objstr+".checked";
	if (eval(str))
		eval(str+"=false");
	else
		eval(str+"=true");
}

/**
 * Case1: �� toObj�ťըåB fromObj���Ȯ�, Set toObj.value = fromObj.value;<br>
 * Case2: �� fromObj�ťըåB toObj���Ȯ�, Set fromObj.value = toObj.value;<br>
 *
 * @param fromObj �_��ƪ���, ��: �`����_�� 
 * @param toObj ����ƪ���, ��: �`���娴��
 */
function fillUpBoundaries(fromObj, toObj)
{
	if (! fromObj) 
	{
		return "Undefined From Object!";
	}
	
	if (! toObj) 
	{
		return "Undefined To Object!";
	}
	
	var fromObjValue = fromObj.value;
	var toObjValue = toObj.value;
	
	if (toObjValue.isEmpty() && ! fromObjValue.isEmpty()) 
	{
		toObj.value = fromObj.value;
	}
	else if (fromObjValue.isEmpty() && ! toObjValue.isEmpty()) 
	{
		fromObj.value = toObj.value;
	}
	
}