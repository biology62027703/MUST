/**
 * $Log$
 * $Id$
 * 20030421 by Jack Y.T. Chou
 *   + Add function trimFrontZero.
 *   + Add function hhd_change_dpt, let dpt name can be displayed by dpt cd in alphabet.
 * 20030421 by Jack Y.T. Chou
 * 	 + Add fimctopm move_item, find_location.
 * 20030519 by Jack Y.T. Chou
 *	+ add_front_zero().
 *
 * 2005.02.21 Peric 修正 hhd_show_dpt() hhd_show_dpt_wlcd() 比對代碼字串沒有兩邊都 .toUpperCase() 轉大寫問題
 * todo:
 * 	1. By using function hhd_show_dpt_wlcd() try to move out from HHD1A02 to here, for common use purpose.
*/
/**
 * Add zeor front of any string.
*/
function add_front_zero(obj, limit) {
	var s = obj.value;
	var temp = '';
	if (s != '')
	{
		for (i=0; i<(limit-parseInt(s.length)); i++) temp = temp + '0';
		obj.value = temp + s;	
	}
}

/**
 * Moving selected items from source to target.
*/
function move_item(source_obj, target_obj, is_order) {
  	for(var i=source_obj.length - 1; i>=0; i--) {    
		var n = source_obj.selectedIndex;
		if (n != -1) {
			var value = source_obj.options[n].value;
			var obj = document.createElement('OPTION');
			obj.text = source_obj.options[n].text;
			obj.value = value;
			if (is_order == 'true')  {
				var j = find_location(target_obj, value);
				target_obj.add(obj, j);
			} else if(is_order == '1') {
				target_obj.add(obj, obj.length); 
			} else {
				target_obj.add(obj, value+1); 
			} 	
			source_obj.remove(n);
		}
    }
}
/**
 * Finding insertion location.
*/
function find_location(obj, value)
{
	if (obj.length == 0) return 0;
	for (var i=0; i<obj.length; i++ ) {
  		if (isNaN(value) || isNaN(obj[i].value)) {
			if (value < obj.options[i].value) return i;			
  		} else if (parseFloat(value)<parseFloat(obj[i].value)) {
	  		return i;
    	}
  	}
  	return i;
}
function trimFrontZero(s){
	while(s.indexOf('0') == 0) 
		s = s.substring(1, s.length);
	return s;
}
/**
 * by Jack Y.T. Chou 20030421
 * tempArray should be two-dimemsiion array with dptcd and dptname
*/
function hhd_show_dpt(text,tempArray,input1,input2){
	var a = text.value;
	if (a=="") return true;
	if (a.indexOf(' ')!=-1)
		a = a.substring(0,a.indexOf(' '));
	a = trimFrontZero(a.toUpperCase());
	var retVal ="";
	if (isNaN(a)) {
		for (var i=0;i<tempArray.length;i++) {
			if (trimFrontZero(tempArray[i][0].toUpperCase())==a) {
				retVal = tempArray[i][1];
				if (input1!='')
					input1.value = tempArray[i][0];
				if (input2!='')
					input2.value = tempArray[i][1];
				break;
			} else if (tempArray[i][1] == a) {
				retVal = tempArray[i][1];
				break;
			}
		}
	} else {
		for (var i=0;i<tempArray.length;i++) {
			if (parseFloat(tempArray[i][0])==parseFloat(a)) {
				retVal = tempArray[i][1];
				if (input1!='')
					input1.value = tempArray[i][0];
				if (input2!='')
					input2.value = tempArray[i][1];
				break;
			}
		}
	}
	if (retVal=='')	{
		display('0','無此代碼或字串');
		text.focus();
		return false;
	} else {
		text.value = retVal;
		return true;
	}
}
/**
 * by Jack Y.T. Chou 20030421
 * ~Enable to Show dpt information by alphabet code.
 * by Jack Y.T. Chou 20030429
 * +tempArray should be three-dimemsiion array with wlcd, dptcd and dptname
*/
function hhd_show_dpt_wlcd(text,dptcd,wlcd,tempArray,input1,input2){
	var a = text.value;
	if (a=="") return true;
	if (a.indexOf(' ')!=-1)
		a = a.substring(0,a.indexOf(' '));
	a = trimFrontZero(a.toUpperCase());
	var retVal ="";
	if (isNaN(a)) {
		for (var i=0;i<tempArray.length;i++) {
			if (tempArray[i][0] == wlcd.value && trimFrontZero(tempArray[i][1].toUpperCase())==a) {
				retVal = tempArray[i][2];
				input1.value = tempArray[i][1];
				input2.value = tempArray[i][2];
				dptcd.value  = tempArray[i][2];
				return true;
				break;
			} else if (tempArray[i][2].toUpperCase() == a) {
				retVal = a;
				input1.value = tempArray[i][1];
				input2.value = tempArray[i][2];
				dptcd.value  = tempArray[i][2];
				return true;
				break;
			}
		}
	} else {
		for (var i=0;i<tempArray.length;i++) {
			if (parseFloat(tempArray[i][1])==parseFloat(a)) {
				retVal = tempArray[i][2];
				input1.value = tempArray[i][1];
				input2.value = tempArray[i][2];
				dptcd.value = tempArray[i][2];
				return true;
				break;
			}
		}
	}
	if (retVal=='')	{
		display('0','無此代碼或字串');
		text.focus();
		return false;
	} else	{
		text.value = retVal;
		return true;
	}
}// end of hhd_show_dpt_wlcd()
function change_pattern_plus(text,tempArray,input1,input2,num)
{
	var a = (text.value);
	//	var a = (text.value).toUpperCase();
	if (a.indexOf(' ')!=-1)
		a = a.substring(0,a.indexOf(' '));
	var temp = '';
	if (a=="") return true;
	if (num!='')
	{
		for (i=0;i<(num-parseInt(a.length));i++){
				temp = temp + '0';
		}	
 	    a = temp + a;
	}
	var retVal ="";
	for (var i=0;i<tempArray.length;i++)
	{
		if (tempArray[i][0]==a)
		{
			retVal = tempArray[i][0];
			if (input1!='') {
				input1.value = tempArray[i][0];
		    }
			if (input2!='') {
				input2.value = tempArray[i][1];
			}
			break;
		}
	} 
	if (retVal=='')
	{
		display('0','無此代碼。');
		return false;
	}
	else
	{
		text.value = retVal;
		return true;
	} 
} // end of change_pattern_plus()
function HHD_DialogOpener(path,obj,width,height) {

	var w = 300;

	var h = 350;

	if(width != '') {

		w = parseFloat(width);	

	}

	if(height != '') {

		h = parseFloat(height);	

	}

	DialogOpener(path,obj,'','',w,h);	

}// end of HHD_DialogOpener()

//說明:在Text物件裡不管你輸入 ID 或 內容 ，會幫你轉成 內容 主要用於tempArray 是由 C16取得的股別
//2004.07.12 Peric 增加處理判斷 股符 與 傳入是否回傳新值的
//2004.10.19 Peric JUDCH0930005 ->代碼不區分大小寫
function change_patternDpt1(text,tempArray,input1,input2,bIsReturnNewValue){
	//alert('wlcd=' + form1.wlcd.value);
	var a = text.value;
	var b = '';
	if (a=="") return true;
	if (a.indexOf(' ')!=-1)
	{	
		b = a.substring(a.indexOf(' ')+1);
		a = a.substring(0,a.indexOf(' '));
	}//end of if
	//alert('a = ' + a);	
	//alert('b = ' + b);	
	var retVal ="";

	//if (isNaN(a))
	//{
		//alert("wlcd = " + form1.wlcd.value);
		for (var i=0;i<tempArray.length;i++)
		{ //2004.07.12 Peric 增加處理判斷 股符 2004.10.19 Peric JUDCH0930005 ->代碼不區分大小寫
			if (tempArray[i][0]==a ||( trimFrontZero((tempArray[i][0]).toUpperCase())==trimFrontZero(a.toUpperCase())) )
			{ // alert(" >> "+trimFrontZero(tempArray[i][0].toUpperCase()));
				if (b=='' || (b!='' && b==tempArray[i][1]))
			  {
					retVal = tempArray[i][1];
					if (input1!='')
						input1.value = tempArray[i][0];
					if (input2!='')
						input2.value = tempArray[i][1];
					break;
				}//end of if	
			}//end of if
		}//end of for
	//}
	//else
	//{
	//alert('retval = ' + retVal);
	if (retVal=='') {
		//alert('111');
		for (var i=0;i<tempArray.length;i++)
		{
		  //2004.07.12 Peric 增加處理判斷 股符
			if (tempArray[i][1]==a )
			{ // 
				if (b=='' || (b!='' && b==tempArray[i][0]))
			  {
			  	retVal = tempArray[i][1];
			  	if (input1!='')
			  		input1.value = tempArray[i][0];
			  	if (input2!='')
			  		input2.value = tempArray[i][1];
			  	break;
			  }//end of if
		  }//end of if
		}// end of for
	}//end of if
	if (retVal=='')
	{
		display('0','無此代碼或字串');
		text.focus();
		return false;

	}
	else
	{ // 2004.07.12 Peric 增加傳入是否回傳新值參數
		// alert("0 bIsReturnNewValue="+bIsReturnNewValue); 
		if ( bIsReturnNewValue==undefined || bIsReturnNewValue!=false)
		{  // alert("1 bIsReturnNewValue="+bIsReturnNewValue); 
		   text.value = retVal;
	  }//end of if
		return true;
	}

}//end of change_patternDpt1()

//2004.10.19 由 HHD1A02.jsp 移到這邊 主要用於tempArray 是由 C18取得的股別
// 2003.08.25 Peric 增加判斷 form1.wlcd.value 
function change_patternDpt(text,tempArray,input1,input2,bIsSetValueWhenEmpty){
	var a = text.value;
	if (a=="") return true;
	if (a.indexOf(' ')!=-1)
		a = a.substring(0,a.indexOf(' '));
	a = trimFrontZero(a.toUpperCase());
	var retVal ="";
	if (isNaN(a)) {
		for (var i=0;i<tempArray.length;i++) {
			if (tempArray[i][0] == form1.wlcd.value && trimFrontZero(tempArray[i][1])==a) {
				retVal = tempArray[i][2];
                if (input1!='' || ( input1=='' && (bIsSetValueWhenEmpty==undefined || bIsSetValueWhenEmpty!=false)) )
				  input1.value = tempArray[i][1];
                if (input2!='' || ( input2=='' && (bIsSetValueWhenEmpty==undefined || bIsSetValueWhenEmpty!=false)) )
				  input2.value = tempArray[i][2];
                text.value = retVal;
				// form1.dptcd_dpt.value  = tempArray[i][2];
				return true;
				break;
			} else if (tempArray[i][2] == a  ){
			    retVal = a;
                if (input1!='' || ( input1=='' && (bIsSetValueWhenEmpty==undefined || bIsSetValueWhenEmpty!=false)) )
			      input1.value = tempArray[i][1];
                if (input2!='' || ( input2=='' && (bIsSetValueWhenEmpty==undefined || bIsSetValueWhenEmpty!=false)) )
			      input2.value = tempArray[i][2];
                text.value = retVal;
			    // form1.dptcd_dpt.value  = tempArray[i][2];
			    return true;
			    break;
			}
		}
	} else {
		for (var i=0;i<tempArray.length;i++) {
			//2003.08.25 Peric 增加判斷 tempArray[i][0] == form1.wlcd.value && 
			//if (parseFloat(tempArray[i][1])==parseFloat(a)) {
			if (tempArray[i][0] == form1.wlcd.value && parseFloat(tempArray[i][1])==parseFloat(a)) {
				retVal = tempArray[i][2];
                if (input1!='' || ( input1=='' && (bIsSetValueWhenEmpty==undefined || bIsSetValueWhenEmpty!=false)) )
				  input1.value = tempArray[i][1];
                if (input2!='' || ( input2=='' && (bIsSetValueWhenEmpty==undefined || bIsSetValueWhenEmpty!=false)) )
				  input2.value = tempArray[i][2];
                text.value = retVal;
				//form1.dptcd_dpt.value = tempArray[i][2];
				return true;
				break;
			}
		}
	}
	if (retVal=='')	{
		display('0','無此代碼或字串');
		text.focus();
		return false;

	} else	{
		text.value = retVal;
		return true;
	}

}//end of change_patternDpt()
