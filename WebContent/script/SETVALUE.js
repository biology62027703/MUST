function SetRadioBoxSelected(RadioBox,value){

   //�\��:�j�MRadioBox�۲ŦX����,�ó]�w��checked

   //��J: object = radiobox ����

   //      value = �j�M����

   //�Ǧ^: true = ��� , false = �S���

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

   //�\��:�j�MSelectBox�۲ŦX����,�ó]�w��checked

   //��J: SelectBox = SelectBox ����

   //      value = �j�M����

   //�Ǧ^: true = ��� , false = �S���

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

   //�\��:�]�wtext �����

   //��J: TextBox = text,areatext ����

   //      value = �]�w����

   //�Ǧ^: true = ���\ , false = ����

	var flag=true;

   TextBox.value = value;

   return flag;

  }



function SetButtonBoxValue(ButtonBox,value)

  {

   //�\��:�]�wButtonBox �����

   //��J: ButtonBox = ButtonBox ����

   //      value = �]�w����

   //�Ǧ^: true = ���\ , false = ����

	var flag=true;

   ButtonBox.value = value;

   return flag;

  }



function SetCheckBoxSelected(CheckBox,value){

   //�\��:�j�MChecekBox�۲ŦX����,�ó]�w��checked

   //��J: CheckBox = checkbox ����

   //      value = �j�M����

   //�Ǧ^: true = ��� , false = �S���	   

   if(CheckBox.value==value){

      CheckBox.checked=true;

   }

}





/**

    ���ͨt�Τ��(����~)

    �Ѽ�:field        form.���

         separator    ��������j�Ÿ�'' or '/'

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

    �N�ǤJ�����e������ɡA�󤺮e�e��ɨ����w���r

    �Ѽ�:value        �n�ˮ֪��ת����e

         limit        �n�ˮ֪�����

         fillcahr     ������ɡA��e��ɨ����ת����w�r' ' or 0

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





//�ˬdSelectBox�O�_����A�p�G����w�]����

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



  //�ˮ֤���O���O�����T�Τ���O�_�b�osys_date���e��diff�~�d�򤺡C

 //�Ѽ�:field �n�ˮ֪�form���

 //     sys_date �t�Τ��(���ȨϥΪ̧�ۤw����client�ݪ�����A�G����ݨt�Τ��

 //     diff     �n�ˮ֪��e��X�~�d��

 //     ex: checkDateRange(form1.disdt,sys_date,1)

 //�^�� : True/False

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

          display('0','��J������o�W�L�ثe�t�Τ�����e��@�~���ɶ�');

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

		display("0","�бN�֨��������");

		return false;

	}

	return true;

}





function checkMultiCheckboxChecked(formObj,nameObj,name)

{

	

	var limit = formObj.length;

	var j=0;

	//�Y�}�C�u���@��

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

	//�Y�}�C�u���@��

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

