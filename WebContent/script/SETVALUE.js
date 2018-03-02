function SetRadioBoxSelected(RadioBox,value){

   //功能:搜尋RadioBox相符合之值,並設定為checked

   //輸入: object = radiobox 物件

   //      value = 搜尋的值

   //傳回: true = 找到 , false = 沒找到

   var flag=false;

   var i = 0;

   var RadioBox_value;

   for (i=0;i<RadioBox.length;i++){

	  // alert(RadioBox[i].value);

      RadioBox_value = RadioBox[i].value;

      if (RadioBox_value == value){

		 RadioBox[i].checked = true;

         flag=true;

		 return flag;

	  }

   }

}

 



function SetSelectBoxSelected(SelectBox,value){

   //功能:搜尋SelectBox相符合之值,並設定為checked

   //輸入: SelectBox = SelectBox 物件

   //      value = 搜尋的值

   //傳回: true = 找到 , false = 沒找到

   var flag=false;

   var i = 0;

   var SelectBox_value;

   for (i=0;i<SelectBox.length;i++) {

      SelectBox_value = SelectBox[i].value;

      if (SelectBox_value == value){

		 SelectBox.options[i].selected = true;

		 flag=true;

		 return flag;

	  }

    }

  }



function SetTextBoxValue(TextBox,value)

  {

   //功能:設定text 物件值

   //輸入: TextBox = text,areatext 物件

   //      value = 設定的值

   //傳回: true = 成功 , false = 失敗

	var flag=true;

   TextBox.value = value;

   return flag;

  }



function SetButtonBoxValue(ButtonBox,value)

  {

   //功能:設定ButtonBox 物件值

   //輸入: ButtonBox = ButtonBox 物件

   //      value = 設定的值

   //傳回: true = 成功 , false = 失敗

	var flag=true;

   ButtonBox.value = value;

   return flag;

  }



function SetCheckBoxSelected(CheckBox,value){

   //功能:搜尋ChecekBox相符合之值,並設定為checked

   //輸入: CheckBox = checkbox 物件

   //      value = 搜尋的值

   //傳回: true = 找到 , false = 沒找到	   

   if(CheckBox.value==value){

      CheckBox.checked=true;

   }

}





/**

    產生系統日期(民國年)

    參數:field        form.欄位

         separator    日期的分隔符號'' or '/'

*/

function initDate(field,separator) {

    var d = new Date();

    var y = (d.getYear() - 1911);

    sep = new String(separator);

    if(sep.length == 0) sep = "";

    //alert("sep = " + sep);

    field.value = fillChar(y,3,0) + sep + fillChar((d.getMonth()+1),2,0) + sep + fillChar(d.getDate(),2,0);

  }





  /**

    將傳入的內容不足位時，於內容前方補足指定的字

    參數:value        要檢核長度的內容

         limit        要檢核的長度

         fillcahr     不足位時，於前方補足長度的指定字' ' or 0

*/

  function fillChar(value,limit,fillchar) {

    var result = "";

    s = new String(value);

    if(s.length < limit) {

     for(i=0;i<(limit - s.length);i++) {

       result = result + "" + fillchar; 

     }

     result = result + "" + value;

    } else {

      result = value;

    }

    return result;

  }





function checkAllCheckBox(flag) {
	limit = document.forms[0].length;
	for(var i = 0; i < limit; i++) {
		if(document.forms[0].elements[i].type == 'checkbox') {
			if(document.forms[0].elements[i].id != '-1') {
				if(!document.forms[0].elements[i].disabled) {
					document.forms[0].elements[i].checked = flag;
				}
			}
		}
	}
}





function selectAllSelectBox(SelectBox,flag){

	for (i=0;i<SelectBox.length;i++) {

		SelectBox.options[i].selected = flag;

	}

}





//檢查SelectBox是否有選，如果不選預設全部

function checkSelectBox(SelectBox)

{

	var flag=true;

	for (i=0;i<SelectBox.length;i++) {

		if(SelectBox.options[i].selected){

			flag=false;

			break;

		}

	}

	if (flag)

		SelectBox.options[0].selected=true;

}



  //檢核日期是不是不正確及日期是否在這sys_date的前後diff年範圍內。

 //參數:field 要檢核的form欄位

 //     sys_date 系統日期(為怕使用者改自已本身client端的日期，故取後端系統日期

 //     diff     要檢核的前後幾年範圍

 //     ex: checkDateRange(form1.disdt,sys_date,1)

 //回傳 : True/False

 function checkDateRange(field,sys_date,diff) {

    //alert("sys_date = " + sys_date);

    //alert(new String(sys_date).substring(0,3));

    var year = sys_date.substring(0,3);

    //alert("year = " + year);

    var stdt = fillChar((parseInt(year,10) - diff),3,0) + "0101";

    var enddt = fillChar((parseInt(year,10) + diff),3,0) + "1231";

    var disdt = field.value;

    //alert("stdt = " + stdt);

    //alert("enddt = " + enddt);

    myDate = new cal(field.value); 

    if(myDate.isCorrectDate() == false) {

       display('401','');

       return false;

    } 

    else {

       //alert("stdt = " + stdt);

       //alert("enddt = " + enddt);

       if(disdt < stdt || disdt > enddt) {

          display('0','輸入日期不得超過目前系統日期的前後一年的時間');

          return false;

       }

    }

    return true;

 }



function checkCheckboxSelected(){

	var limit = document.forms[0].length;

	var j=0;

	for(i=0;i<limit;i++) {

		if(document.forms[0].elements[i].type == 'checkbox' &&   document.forms[0].elements[i].checked) j=j+1;

	}

	if (j==0) {

		display("0","請將核取方塊打勾");

		return false;

	}

	return true;

}





function checkMultiCheckboxChecked(formObj,nameObj,name)

{

	

	var limit = formObj.length;

	var j=0;

	//若陣列只有一筆

	if (limit+""=="undefined" && nameObj.name==name)

	{

		if (formObj.checked)

			j++;

	}

	else

	{

		for(i=0;i<limit;i++) 

		{

			if(formObj.elements[i].type == 'checkbox' &&   formObj.elements[i].checked && formObj.elements[i].name==name) 

				j=j+1;

		}

	}



	if (j==0) 

		return false;

	else

		return true;

}





function selectedMultiCheckbox(formObj,flag,nameObj,name)

{

	var limit = formObj.length;

	//若陣列只有一筆

	if (limit+""=="undefined")

	{

		if (formObj.checked && nameObj.name==name)

			formObj.checked=flag;

	}

	else

	{

		for(i=0;i<limit;i++) 

		{

			if(formObj.elements[i].type=="checkbox" && formObj.elements[i].name==name)

			{

				formObj.elements[i].checked=flag;

			}

		}

	}

}

