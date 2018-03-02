function ChangeSelect(TEXT,SELECT) {
  //功能:改變SELECT內容帶出TEXT的代碼
  //輸入:TEXT :左邊TEXT件
  //     SELECT :右邊SELECT元件
  TEXT.value = SELECT.options(SELECT.selectedIndex).value;
}

function ChangeText(TEXT,SELECT) {
  //功能:輸入代號帶出SELECT內的內容
  //輸入:TEXT :左邊TEXT件
  //     SELECT :右邊SELECT元件
  Str=SELECT.value;
  found=false;
  for(ctr=0;ctr<SELECT.length;ctr++) 
  {
   if (TEXT.value == SELECT.options(ctr).value) 
   {
    SELECT.options(ctr).selected=true;
    found=true;
    break;
   }
  }
  if(found==false)
  {
    alert("無此代碼");
    TEXT.value=Str; 
  }
} 

function AssignTo(SELECTX,value)
{
  if (SELECTX.length==0)
  {
    return 0;
  }
  for (var j=0;j<SELECTX.length;j++ )
  {
    if (parseFloat(value)<parseFloat(SELECTX.options[j].value))
    {
	  return j;
    }
  }
  return j+1;
}

function LefttoRight(SELECT1,SELECT2,is_order)
{
  //功能:將select內的data 向右移
  //輸入:SELECT1 :左邊SELECT元件
  //     SELECT2 :右邊SELECT元件
  var counter = SELECT1.length ;
  for(var i = 0;i < counter;i++)
  {
    
    var index = SELECT1.selectedIndex;
    if(index != -1)
    {
      var value = SELECT1.options[index].value;
      var text = SELECT1.options[index].text;
      selection2 = document.createElement("OPTION");
      selection2.text = text;
      selection2.value = value;
      //alert('value = ' + value);
      if (is_order=="true")
      {
        var j=AssignTo(SELECT2,value);
        SELECT2.add(selection2,j);
      } else if(is_order == '1') {
      	SELECT2.add(selection2,selection2.length); 
      } else
      {
      	//alert('value = ' + value);
        SELECT2.add(selection2,value+1); 
        //SELECT2.add(selection2,selection2.length); 
      } 	
      //alert('000');
      //SELECT2.options[0].selected = true;
      SELECT1.remove(index);
    }
  } 
}
function RighttoLeft(SELECT1,SELECT2,is_order)
{
  //功能:將select內的data 向左移
  //輸入:SELECT1 :左邊SELECT元件
  //     SELECT2 :右邊SELECT元件
  var counter = SELECT2.length ;
  for(var i = 0;i < counter;i++)
  {
    var index = SELECT2.selectedIndex;
    if(index != -1)
    {
      var value = SELECT2.options[index].value;
      var text = SELECT2.options[index].text;
      selection1 = document.createElement("OPTION");
      selection1.text = text;
      selection1.value = value;
      if (is_order=="true")
      {
        var j=AssignTo(SELECT1,value);
        SELECT1.add(selection1,j);
      } else if(is_order == '1') {
      	SELECT1.add(selection1,selection1.length);	
      } else {
      	//alert('1222');
        SELECT1.add(selection1,value+1);
        
      }	 
      //SELECT1.options[0].selected = true;
      SELECT2.remove(index);
    }
  }  
}

function selectcheck(s) {
  var count = s.length;
  for(i=0;i<count;i++) {
   s.options[i].selected = true;
  }
  //alert("test");
 }


function Getzip(TEXT,cAddr)
{
  
  var nCity=0,_cCity="",zip="";
  var nArea=0, cArea="";
  // 取出區鄉鎮市
  cAddr=alltrim(cAddr);
  // 去除第一中文字 "設","住"
  if((cAddr.substring(0,1)=="設")||( cAddr.substring(0,1)=="住"))
    cAddr=cAddr.substring(1,cAddr.length);
  
  for (i=1;i<=4;i++)
  {
   switch (i)
   {   
     case 1 :nCity=cAddr.indexOf("區");break;
     case 2 :nCity=cAddr.indexOf("鄉");break;      
     case 3 :nCity=cAddr.indexOf("鎮");break;
     case 4 :nCity=cAddr.indexOf("市");break;
   }
   if (nCity > 0)
      break;
  }
  if(nCity<0)
     return ;
  cCity=cAddr.substring(0,nCity+1);
  
  for (i=1;i<=3;i++)
  {
   switch (i)
   {   
     case 1 :nArea=cAddr.indexOf("段");break;
     case 2 :nArea=cAddr.indexOf("路");break;      
     case 3 :nArea=cAddr.indexOf("街");break;
   }
   if (nArea > 0)
      break;
  }
  if(nArea<0)
    cArea="";
  else
    cArea=cAddr.substring(nCity+1,nArea+1);  
  var found=true;
  
  for (i=0;i<=zipcode.length-1;i++)
  {
      
      if(zipcode[i][2].indexOf(cCity)>=0)
      { 
         if(alltrim(zipcode[i][3])!="")
         {
           zip=zipcode[i][3];
           if (found==true)
           {   
              TEXT.value=zip;
              return zip;
           }
           else   
           {   
              break;
           }   
         }
         else
         {
           zip=zipcode[i][3];
           found=false;
         }  
              
      }
  } 
  
  if ((found==false)&&(alltrim(cArea)!=""))
  {
    if(alltrim(zip)!="") 
    { 
      zip.substring(nCity+1,nArea+1)
      var dbf_name=eval("R"+zip.substring(0,1)+"00A");
      for (i=0;i<=dbf_name.length-1;i++)
      {
        if(dbf_name[i][0].indexOf(cArea)>=0)
        {
          zip=dbf_name[i][1];  
          TEXT.value=zip;
          return  zip;   
        }  
      }
    }  	  
  	
  }	
  TEXT.value="";
}


function addnum(this1,limit) {
	s = this1.value;
	var temp = '';
	if(!isInt(s)) {
		//if(confirm("請輸入數字型態值")) this1.focus();
		alert("請輸入數字型態值");
		//指定反回區城
		this1.value = '001';
		//document.form1.code.focus();
	} else {
		
		if (s != '')
		{
		for (i=0;i<(limit-parseInt(s.length));i++){
				temp = temp + '0';
			}
		}							
		this1.value = temp + s;
		
	}
}

function isInt(num)
 {
  //功能: 判斷是否為數字
  //輸入 : num : text
  var flag=true;   
  flag=isNaN(num);
  return !flag;
 }

//myCal = new cal(parameter);	myCal為變數，請自取
function cal(parameter)
{
	this.year = parameter.substring(0,(parameter.length)-4);					//變數
	this.month = parameter.substring((parameter.length)-4,(parameter.length)-2);			//變數
	this.day = parameter.substring((parameter.length)-2,(parameter.length));			//變數	
	this.getYear=getYear;		//函數 
	this.getMonth=getMonth;		//函數 
	this.getDay=getDay;		//函數 
	this.isCorrectDate=isCorrectDate;		//函數 
}

function isCorrectDate(){
	if (this.year==undefined)
		return false;
	inDate = new Date(parseFloat(this.year)+1911,parseFloat(this.month)-1,parseFloat(this.day));		//變數	
	
	
	if (!(inDate.getMonth()==parseFloat(this.month)-1))
		return false;
	else
		return true;
}

function getYear(){
	return this.year;
}
function getMonth(){
	return this.month;
}
function getDay(){
	return this.day;
}

//說明:在Text物件裡不管你輸入 ID 或 內容 ，會幫你轉成 內容 ? 
function change_pattern(text,tempArray,input1,input2,parameter){
	var a = text.value;
	if (a=="") return true;
	if (a.indexOf(' ')!=-1)
	a = a.substring(0,a.indexOf(' '));
	var retVal ="";
	if (isNaN(a)){
		
		for (var i=0;i<tempArray.length;i++){
			if (tempArray[i][1]==a){
				retVal = tempArray[i][1];
				if (input1!='')
					input1.value = tempArray[i][0];
				if (input2!='')
					input2.value = tempArray[i][1];
				break;
			}
		} 
	}else{
		for (var i=0;i<tempArray.length;i++){
			if (parseFloat(tempArray[i][0])==parseFloat(a)){
				retVal = tempArray[i][1];
				if (input1!='')
					input1.value = tempArray[i][0];
				if (input2!='')
					input2.value = tempArray[i][1];
				break;
			}
		} 
	}
	
	if (retVal==''){
		if (parameter !=false){
			display('0','無此代碼或字串');
		}
		return false;
	}else{
		text.value = retVal;
		return true;
	} 
}



//說明:在Text物件裡你輸入 ID  ，會幫你轉成 內容
function change_pattern2(text,tempArray,input1,input2){
   var a = text.value;
   if (a=='')
       return false;
 
   if (a.indexOf(' ')!=-1)
      a = a.substring(0,a.indexOf(' '));

   var retVal =""; 
   if (isNaN(a)){
      retVal= false;
   }
   else{
      for (var i=0;i<tempArray.length;i++){
          if (parseFloat(tempArray[i][0])==parseFloat(a))
		  {
             retVal = tempArray[i][0] + ' ' + tempArray[i][1];
             text.value = tempArray[i][1];
			 if (input1!=''){
                input1.value = tempArray[i][0];
             }
            if (input2!=''){
                input2.value = tempArray[i][1];

            }
            break;
         }
     } 
   }
}

//說明:在Text物件裡不管你輸入 ID 或 內容 ，會幫你轉成 內容 ? 
function change_pattern3(text,tempArray,input1,input2,parameter)
{

	var a = text.value;
	if (a=="") return true;
	if (a.indexOf(' ')!=-1)
	a = a.substring(0,a.indexOf(' '));
	var retVal ="";
	if (isNaN(a) && (!isId(a)))
	{
		for (var i=0;i<tempArray.length;i++)
		{
			if (tempArray[i][1]==a)
			{
				retVal = tempArray[i][1];
				if (input1!='')
					input1.value = tempArray[i][0];
				if (input2!='')
					input2.value = tempArray[i][1];
				break;
			}
		} 
	}
	else
	{
		for (var i=0;i<tempArray.length;i++)
		{
			var a_str=tempArray[i][0].toLowerCase();
			var b_str=a.toLowerCase();
			if (a_str==b_str)
			{
				retVal = tempArray[i][1];
				if (input1!='')
					input1.value = tempArray[i][0];
				if (input2!='')
					input2.value = tempArray[i][1];
				break;
			}
		} 
	}
	if (retVal=='')
	{
		if (parameter !=false)
		{
			display('0','無此代碼或字串');
		}


		return false;

	}
	else
	{
		text.value = retVal;
		return true;
	} 

} 

//說明:在Text物件裡不管你輸入 ID 或 內容 ，會幫你轉成 內容 ? ，for 稱謂代碼使用

function change_pattern5(text,tempArray,input1,input2,parameter)
{
	
	var a = (text.value).toUpperCase();
	var b = '1';
	if (a=="") return true;
	if (a.indexOf(' ')!=-1)
		a = a.substring(0,a.indexOf(' '));
	var retVal ="";
	
	b = checkNumOrTag(a);
	if (b=='2')
	{
		for (var i=0;i<tempArray.length;i++)
		{
			if (tempArray[i][1]==a)
			{
				retVal = tempArray[i][1];
				if (input1!='')
					input1.value = tempArray[i][0];
				if (input2!='')
					input2.value = tempArray[i][1];
				break;
			}
		} 
	}
	else
	{
		for (var i=0;i<tempArray.length;i++)
		{
			if (tempArray[i][0]==a)
			{
				retVal = tempArray[i][1];
				if (input1!='')
					input1.value = tempArray[i][0];
				if (input2!='')
					input2.value = tempArray[i][1];
				break;
			}
		} 
	}
	if (retVal=='')
	{
		if (parameter !=false)
		{
			display('0','無此代碼或字串');
		}


		return false;

	}
	else
	{
		text.value = retVal;
		return true;
	} 

}
//是代碼則回傳1，內容則回傳2
function checkNumOrTag(text)
{
	var retVal = '1';	
	var a= _getLength(text);
	//alert('checkNumOrTag=' + a);
	if (a!=5)
	{
		retVal = '2';
	}
	else
	{
		if (_CheckBig5(text))
			retVal = '2';
	}
	return retVal;	
		
		
}
//傳回字串長度，有分中英文哦
function _getLength(object_text)
{
		
	var s,i=0,char_count=0,result=true;
	
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
//檢查字串裡是否有中文
function _CheckBig5(object_text)
{
	var retVal = false;
	var i = 0;
	while (i++<object_text.length)
	{
		s = object_text.charCodeAt(i-1);
		if (s>127)
		{
			retVal = true;	
			break;
		}	
	}
	return retVal;
}
function initYear(obj) {
  if(obj.value == '') {
   var y = new Date().getYear();
   obj.value = (y - 1911);
   addnum(obj,3);
  }
}
//前面補字串
function fillfront(obj,chr,i){
	var j = (obj.value).length;
	var retVal = obj.value;
	var temp = '';
	if (j!=0){
		var k = i-j;
		for (var r=0;r<k;r++){
			temp = temp + chr;
		}
		retVal = temp + retVal;
	}
	obj.value = retVal;
}
/*
change_pattern 加強版 ，可以處理代碼為英文
text : text 物件
tempArray : 要比對之陣列
input1:代碼
input2:內容
showErr	:無字代碼時是否要顯示錯誤訊息
idLeng : 代碼長度，無此變數則預設為3碼
注意:此函式會將代碼轉成大寫再比較
*/
function change_patternXP(text,tempArray,input1,input2,showErr,idLeng)
{

	var a = text.value;
	a=a.toUpperCase();
	if (a=="") return true;
	
	if (idLeng=='' || (idLeng + '')=='undefined')
		idLeng = 3;
		
	if (a.indexOf(' ')!=-1)
	a = a.substring(0,a.indexOf(' '));
	var retVal ="";
	if ( _CheckBig5(a))
	{
		for (var i=0;i<tempArray.length;i++)
		{
			if (tempArray[i][1]==a)
			{
				retVal = tempArray[i][1];
				if (input1!='')
					input1.value = tempArray[i][0];
				if (input2!='')
					input2.value = tempArray[i][1];
				break;
			}
		} 
	}
	else
	{
		for (var i=0;i<tempArray.length;i++)
		{
			
			if (isNaN(a))
			{
				a = addnumForCP(a,idLeng);
				if (tempArray[i][0]==a)
				{
					retVal = tempArray[i][1];
					if (input1!='')
						input1.value = tempArray[i][0];
					if (input2!='')
						input2.value = tempArray[i][1];
					break;
				}
			}
			else
			{
				if (parseFloat(tempArray[i][0])==parseFloat(a))
				{
					retVal = tempArray[i][1];
					if (input1!='')
						input1.value = tempArray[i][0];
					if (input2!='')
						input2.value = tempArray[i][1];
					break;
				}
			}	
		} 
	}
	if (retVal=='')
	{
		if (showErr !=false)
		{
			display('0','無此代碼或字串');
		}


		return false;

	}
	else
	{
		text.value = retVal;
		return true;
	} 

}

function addnumForCP(value,limit) {
	var retVal = value;
	var temp = '';
	
		
		if (retVal != '')
		{
		for (i=0;i<(limit-parseInt(retVal.length));i++){
				temp = temp + '0';
			}
		}							
		retVal = temp + retVal;
		return retVal;
	
}
