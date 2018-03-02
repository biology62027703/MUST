
/**
 * @description 所有關於form的操作皆使用此共用元件，包含了表單資料送出與表單資料繫結。
 * @class 表單工具
 * @author chkchk
 * @constructor
 */
function CheckUtil() {
}

CheckUtil.prototype.chkAlpha = function (obj) {
    for(i=0;i<obj.value.length;i++){
        var value=obj.value.charAt(i)
        if(value < "A" || value >"Z" && value < "a" || value > "z") {
            return false;
        }
    }
    return true;
};

CheckUtil.prototype.chkTime = function (obj) {
	var text = obj.value;	
	if( text=="" ) return true;
	if( text.length==4 )
		text = text + "00";
	text = text.substring(0,2)+":"+	text.substring(2,4)+":"+text.substring(4,6);
	text = text.replace(/[\:-]0?/g, ":");
	if (!text.match(/^\d{2}\:\d{1,2}\:\d{1,2}$/)) return false;
	var d = new Date("2011/01/01 " + text);
	return [d.getHours(), d.getMinutes(), d.getSeconds()].join(":") == text;
};

CheckUtil.prototype.chkDate = function (obj) {
	var text = obj.value;
	if( text=="" ) return true;
	if( text.length==5 )
		text = text + "01";
	if( text.length==7 )
		text = text*1+19110000+""; 
	text = text.substring(0,4)+"/"+	text.substring(4,6)+"/"+text.substring(6,8);
	text = text.replace(/[\/-]0?/g, "/");
	if (!text.match(/^\d{4}\/\d{1,2}\/\d{1,2}$/)) return false;
	var d = new Date(text);
	return [d.getFullYear(), d.getMonth() + 1, d.getDate()].join("/") == text;
};

CheckUtil.prototype.chkNum = function (obj) {
    if(isNaN(obj.value)){
        return false;
    }
    return true;
};

CheckUtil.prototype.chkEmail = function (obj) {
	if(!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(obj.value))){
        return false;
	}
	else
		return true;
};

CheckUtil.prototype.chkId = function (obj) {
    //建立字母分數陣列(A~Z)  
	if( obj.value=="")
		return true;
    var city = new Array(  
         1,10,19,28,37,46,55,64,39,73,82, 2,11,  
        20,48,29,38,47,56,65,74,83,21, 3,12,30  
    )  
    id = obj.value.toUpperCase();  
    // 使用「正規表達式」檢驗格式  
    if (id.search(/^[A-Z](1|2)\d{8}$/i) == -1) {
        return false;  
    } else {  
        //將字串分割為陣列(IE必需這麼做才不會出錯)  
        id = id.split('');  
        //計算總分  
        var total = city[id[0].charCodeAt(0)-65];  
        for(var i=1; i<=8; i++){  
            total += eval(id[i]) * (9 - i);  
        }  
        //補上檢查碼(最後一碼)  
        total += eval(id[9]);  
        //檢查比對碼(餘數應為0);  
        return ((total%10 == 0 ));  
    }  
};

CheckUtil.prototype.checkType = function (chkType, obj) {
	var ret = true;
	var msg = "";
	switch(chkType){
		case "F":
			ret = checkUtil.chkNum(obj);
			msg = "抱歉！請輸入半形數字！";				
			break;
		case "Y":
			ret = checkUtil.chkNum(obj);
			msg = "抱歉！請輸入半形數字！";				
			break;
		case "T":
			ret = checkUtil.chkTime(obj);
			msg = "抱歉！請輸入正確的時間格式！";				
			break;
		case "H":
			ret = checkUtil.chkTime(obj);
			msg = "抱歉！請輸入正確的時間格式！";				
			break;
		case "D":
			ret = checkUtil.chkDate(obj);
			msg = "抱歉！請輸入正確的日期格式！";				
			break;
		case "M":
			ret = checkUtil.chkDate(obj);
			msg = "抱歉！請輸入正確的日期格式！";				
			break;
		case "A":
			ret = checkUtil.chkAlpha(obj);
			msg = "抱歉！請輸入英文字母！";			
			break;
		case "E":
			ret = checkUtil.chkEmail(obj);
			msg = "抱歉！無效的Email格式！";
			break;
		case "I":
			ret = checkUtil.chkId(obj);
			msg = "抱歉！無效的身分證字號！"
			break;	
		case "U":
			break;
	}
	if( !ret ) {
		obj.focus();
		helpUtil.showInfoBar("INFO", msg);
	}
	return ret;
};

CheckUtil.prototype.formCheck = function (formObj) {
	
	ret = true;	
	
	$(":text", formObj).each(function(){		 
		if( ret && typeof($(this).attr("check"))=="string" ) {
			var obj = $(this)[0];
			var check = $(this).attr("check");	
			var display = typeof($(this).css("display"))=="string" ? $(this).css("display") : "";		
			var checkType = check.charAt(0);
			var allowNull = check.charAt(1);
			var addZero = check.charAt(2);
			var value = obj.value;
			var maxlength = obj.maxLength;
			var min = typeof($(this).attr("min"))=="string" ? $(this).attr("min") : "";		
			var max = typeof($(this).attr("max"))=="string" ? $(this).attr("max") : "";		
			
			if( addZero=="1" && obj.maxLength<100) {
				$(this).blur();
			}
			
			ret = checkUtil.checkType(checkType, obj);
			if( ret && allowNull=="0" && value=="" && display!="none") {
				obj.focus();
				helpUtil.showInfoBar("INFO", "抱歉！這欄位不允許空白！");
				ret = false;
			}
			
			if( ret && value.Blength()>maxlength ) {
				obj.focus(); 
				helpUtil.showInfoBar("INFO", "抱歉！此欄位輸入的資料超過"+maxlength+"bytes！");
				ret = false;
			}
			
			if( ret && checkType=="F" && min!="" && max!="" ) {
				if( value*1<min*1 || value*1>max*1) {
					obj.focus();
					helpUtil.showInfoBar("INFO", "抱歉！此數字欄位輸入範圍為: "+min+"~"+max+"！");
					ret = false;
				}
			}
		}
	});
	
	$("textarea", formObj).each(function(){		 
		if( ret && typeof($(this).attr("check"))=="string" ) {
			
			var obj = $(this)[0];
			var check = $(this).attr("check");		
			var checkType = check.charAt(0);
			var allowNull = check.charAt(1);
			var value = obj.value;
			var maxlength = $(this).attr("maxlength")*1;
			var display = typeof($(this).css("display"))=="string" ? $(this).css("display") : "";

			if( ret && allowNull=="0" && value=="" && display!="none") {
				obj.focus();
				helpUtil.showInfoBar("INFO", "抱歉！這欄位不允許空白！");
				ret = false;
			}
			
			if( ret && value.Blength()>maxlength ) {
				obj.focus(); 
				helpUtil.showInfoBar("INFO", "抱歉！此欄位輸入的資料超過"+maxlength+"bytes！");
				ret = false;
			}
		} 
	});
	
	$("select[check=U00]", formObj).each(function(){		 
		var obj = $(this)[0];
		var value = obj.value;
		var display = typeof($(this).css("display"))=="string" ? $(this).css("display") : "";
		if( ret && value=="" && display!="none") {
			obj.focus(); 
			helpUtil.showInfoBar("INFO", "抱歉！這欄位不允許空白！");
			ret = false;
		}
	});
	
	if( typeof(formObj.attr("compareValue"))=="string" ) {
		valueAry = formObj.attr("compareValue").split(",");
		for(var i=0;i<valueAry.length;i++) {
			v = valueAry[i];
			l = $(":text[compareValue="+v+"]").length;
			if( l%2==0 ){
				for(var j=0; j<l; j+=2) {
					sobj = $(":text[compareValue="+v+"]")[j];
					eobj = $(":text[compareValue="+v+"]")[j+1];
					field = v;
					
					if( sobj.value=="" && eobj.value!="" ){
						sobj.focus();
						helpUtil.showInfoBar("INFO","輸入「"+field+"」迄值時，起值不可為空白 ");
						return false;
					}
					if( sobj.value!="" && eobj.value=="" ){
						eobj.focus();
						helpUtil.showInfoBar("INFO","輸入「"+field+"」起值時，迄值不可為空白 ");
						return false;
					}
					if( sobj.value!="" && eobj.value!="" ){
						if( !(sobj.value*1 <= eobj.value*1) ){
							eobj.focus();
							helpUtil.showInfoBar("INFO","輸入「"+field+"」的起訖資料錯誤 ");
							return false;
						}				
					}
				}
			}
		}		
	}
	
	return ret;
	
};

CheckUtil.prototype.setFormEnterKey = function (formObj, submitButton) {
	
	$(":text", formObj).unbind('keypress');
	$(":text", formObj).keypress(function(){
		if( event.keyCode==13 ) {
			submitButton.click();
			event.keyCode = 0;
		}
	});	
	
	$(":password", formObj).unbind('keypress');
	$(":password", formObj).keypress(function(){
		if( event.keyCode==13 ) {
			submitButton.click();
			event.keyCode = 0;
		}
	});
	
	$(":text[check]", formObj).each(function(i){
		var check = $(this).attr("check");
		if( check.indexOf("Y")==0 || check.indexOf("F")==0 || check.indexOf("D")==0 ) {
			$(this).keypress(function(){
				event.keyCode = checkUtil.lockNum();
			})		
		}else if( check.indexOf("A")==0 ) {	
			$(this).keypress(function(){
				event.keyCode = checkUtil.lockAz();
			})
		}else if( check.indexOf("$")==0 ) {	
			$(this).keypress(function(){
				if( event.keyCode==46) {
					if( obj.value.indexOf(".")>-1 )
						event.keyCode = 0;
				}else
					event.keyCode = checkUtil.lockNum();
			})				
		}
	});
	
};

CheckUtil.prototype.iniObjectCondition = function ($object) {		
	 
	if( typeof($object.attr("check"))=="string" ) {
		var obj = $object[0];
		var check = $object.attr("check");
		
		if( typeof($object.attr("compareValue"))=="string" ) {
			if( typeof($form.attr("compareValue"))=="string" ) {
				if( $form.attr("compareValue").indexOf($object.attr("compareValue")) == -1 ) {
					if( $form.attr("compareValue") != "" )
						$form.attr("compareValue", $form.attr("compareValue")+",");
					$form.attr("compareValue", $form.attr("compareValue")+$object.attr("compareValue"));
				}
			}else
				$form.attr("compareValue", $object.attr("compareValue"));
		}
		
		if( check.indexOf("Y")==0 ) {
			var name = obj.name;	
			var id 	 = obj.id;
			$object.attr("size", "3");
			$object.attr("maxlength", "3");
			$object.keypress(function(){
				event.keyCode = checkUtil.lockNum();
			})			
			if( check.charAt(2)=="1" ) {
				$object.blur(function(){
					if( $(this).val() != "")
						$(this).val(checkUtil.addNum($(this).val(), this.maxLength, "0"));
				})
			}
			checkUtil.setYearFunction($object);
		}else if( check.indexOf("D")==0 ) {
			renderUtil.renderCalendar($object, "yyyMMdd");
		}else if( check.indexOf("M")==0 ) {					
			renderUtil.renderCalendar($object, "yyyMM");
		}else if( check.indexOf("T")==0 ) {					
			renderUtil.renderCalendar($object, "HHmmss");
		}else if( check.indexOf("H")==0 ) {
			renderUtil.renderCalendar($object, "HHmm");					
		}else if( check.indexOf("F")==0 ) {
			$object.keypress(function(){
				event.keyCode = checkUtil.lockNum();
			})
			if( check.charAt(2)=="1" && obj.maxLength<100) {
				$object.blur(function(){
					if( $(this).val() != "")
						$(this).val(checkUtil.addNum($(this).val(), this.maxLength, "0"));
				})
			}
		}else if( check.indexOf("A")==0 ) {	
			$object.keypress(function(){
				event.keyCode = checkUtil.lockAz();
			})
		}else if( check.indexOf("$")==0 ) {	
			$object.keypress(function(){
				if( event.keyCode==46) {
					if( obj.value.indexOf(".")>-1 )
						event.keyCode = 0;
				}else
					event.keyCode = checkUtil.lockNum();
			})				
		}
	}
	
}

CheckUtil.prototype.iniTextCondition = function () {
		
	$("form").each(function(){
		
		$form = $(this);
		$(":text[check]", $form).each(function(i){					
			checkUtil.iniObjectCondition($(this));
		});
		
		$("[radioGroup][type!=radio]", $form).each(function(i){
			$(this).attr("formname", $form.attr("name"));
			$(this).focus(function(){
				var formname = $(this).attr("formname");
				$(":radio[radioGroup="+$(this).attr("radioGroup")+"]", $("form[name="+formname+"]")).click();
			});
		});
		
		$("[checkboxGroup][type!=checkbox]", $form).each(function(i){
			$(this).attr("formname", $form.attr("name"));
			$(this).focus(function(){
				var formname = $(this).attr("formname");
				if( $(":checkbox[checkboxGroup="+$(this).attr("checkboxGroup")+"]", $("form[name="+formname+"]"))[0].checked == false )
					$(":checkbox[checkboxGroup="+$(this).attr("checkboxGroup")+"]", $("form[name="+formname+"]")).click();
			});
		});
		$(":radio[radioGroup]", $form).each(function(i){
			$(this).attr("formname", $form.attr("name"));
			$(this).click(function(){

				$this = $(this);
				var formname = $(this).attr("formname");					
				var name=$(this).attr("name");
				var radioGroup = (typeof($(this).attr("radioGroup")) == "string" ? $(this).attr("radioGroup") : "");
				var isclearitem = (typeof($(this).attr("isclearitem")) == "string" ? $(this).attr("isclearitem") : "");
				
				$(":radio[name="+name+"]", $("form[name="+formname+"]")).each(function(){					
					var checknull = (typeof($(this).attr("checknull"))=="string" ? $(this).attr("checknull") : "");
					var formname = $(this).attr("formname");					
					var radioGroup2 = (typeof($(this).attr("radioGroup")) == "string" ? $(this).attr("radioGroup") : "");
					//自己這個RADIO
					if( radioGroup == radioGroup2 ) {						
						$("[radioGroup="+radioGroup2+"][type!=radio]", $("form[name="+formname+"]")).each(function(){
							if( (typeof($(this).attr("check"))=="string") && (typeof($(this).attr("orgcheck"))=="undefined") )
								$(this).attr("orgcheck", $(this).attr("check"));
							if( checknull=="Y" ) {
								var check = (typeof($(this).attr("check")) == "string" ? $(this).attr("check") : "");
								if( check.length>1 ) {
									check = check.substring(0,1) + "0" + check.substring(2);
									$(this).attr("check", check);
								}
							}							
						})
					}else{
						$("[radioGroup="+radioGroup2+"][type!=radio]", $("form[name="+formname+"]")).each(function(){
							if( (typeof($(this).attr("orgcheck"))=="string") ) {
								$(this).attr("check", $(this).attr("orgcheck"));
								$(this).remove("orgcheck");
							}
							if( isclearitem="Y") {
								if( $(this).attr("type")=="text" || $(this).attr("type")=="password" )
									$(this).val("");
								else
									$(this).removeAttr("checked");
							}
						})
					}
				}); 
			});			
		});		
		
		$(":checkbox[checkboxGroup]", $form).each(function(i){
			$(this).attr("formname", $form.attr("name"));
			$(this).click(function(){
				$this = $(this);
				var formname = $(this).attr("formname");					
				var name=$(this).attr("name");
				var checkboxGroup = (typeof($(this).attr("checkboxGroup")) == "string" ? $(this).attr("checkboxGroup") : "");				
				if( !this.checked ) {
					$(":text[checkboxGroup="+checkboxGroup+"]", $("form[name="+formname+"]")).val("");
					$(":password[checkboxGroup="+checkboxGroup+"]", $("form[name="+formname+"]")).val("");
					$(":radio[checkboxGroup="+checkboxGroup+"]", $("form[name="+formname+"]")).removeAttr("checked");
				}
			});			
		});	
		
		$(":radio[radioGroup]:checked",  $form).click();
	});
}

CheckUtil.prototype.lockNum = function () {
	if (event.keyCode==13) 
		return event.keyCode;
	if ((event.keyCode < 48) || (event.keyCode > 58)){
		if (event.keyCode!=45)
			event.keyCode = 0;
	}
	return event.keyCode;
}

CheckUtil.prototype.lockAz = function () {
	if (event.keyCode==13) 
		return event.keyCode;
	if( event.keyCode>=97 && event.keyCode<=122 )
		return event.keyCode;
	if( event.keyCode>=65 && event.keyCode<=90 )
		return event.keyCode;
	return 0;
}

CheckUtil.prototype.fmoney = function (s) {
	if(s=="" || s=="NaN") 
		s="0";
	n = 0;
	var dot = s.indexOf(".")>-1 ? "."+s.split(".")[1] : "";
	s = s.split(".")[0];
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	var l = s.split(".")[0].split("").reverse(),  
	t = "";  
	for(i = 0; i < l.length; i ++ )
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	return t.split("").reverse().join("")+dot;  
}

CheckUtil.prototype.initYear = function () {
	var y=new Date().getFullYear()-1911;
	return checkUtil.addNum(y, 3, "0");
}

CheckUtil.prototype.addNum = function (v, n, c) {
	v = v + "";
	while(v.length<n) {
		v = c + v;
	}
	return v;
}

CheckUtil.prototype.setYearFunction = function (obj) {
	$("<span>　 </span>").insertAfter(obj);
	$div = $(
			"<div id='yearButton' style='position:absolute' targetname='"+obj.attr("name")+"'>" +
			"<table width='16' border='0'>" +
			"<tr><td width='4%' valign='bottom'><img src='../image/yearup.gif' width='16' id='up' style='cursor:hand' targetname='"+obj.attr("name")+"' height='14'></td></tr>" +
			"<tr><td width='4%' valign='top'><img src='../image/yeardown.gif' width='16' id='down' style='cursor:hand' targetname='"+obj.attr("name")+"' height='14'></td></tr>" +
			"</table>" +
			"</div>"
		).appendTo($("body"));	 
	 
	$("img[id=up]", $div).click(function(){
		targetname = $(this).attr("targetname");		
		var obj = $(":text[name="+targetname+"]")[0];
		if( obj.value=="")
			obj.value = checkUtil.initYear();
		else{			
			obj.value = checkUtil.addNum(obj.value*1+1, obj.maxLength, "0");
		}
	})
	 
	$("img[id=down]", $div).click(function(){
		targetname = $(this).attr("targetname");		 
		var obj = $(":text[name="+targetname+"]")[0];
		if( obj.value=="")
			obj.value = checkUtil.initYear();
		else{
			obj.value = checkUtil.addNum(obj.value*1-1, obj.maxLength, "0");
		}
	})
	
	$(window).resize(function() {
		function findPosX(obj)  
		 {  
		   var curleft = 0;  
		   if(obj.offsetParent)  
		       while(1)   
		       {  
		         curleft += obj.offsetLeft;  
		         if(!obj.offsetParent)  
		           break;  
		         obj = obj.offsetParent;  
		       }  
		   else if(obj.x)  
		       curleft += obj.x;  
		   return curleft;  
		 }  
		 
		 function findPosY(obj)  
		 {  
		   var curtop = 0;  
		   if(obj.offsetParent)  
		       while(1)  
		       {  
		         curtop += obj.offsetTop;  
		         if(!obj.offsetParent)  
		           break;  
		         obj = obj.offsetParent;  
		       }  
		   else if(obj.y)  
		       curtop += obj.y;  
		   return curtop;  
		 }
		 
		 $("div[id=yearButton]").each(function(){
			 targetname = $(this).attr("targetname");			 
			 var obj = $(":text[name="+targetname+"]")[0];
			 $(this).css("top", (findPosY(obj)-6) + "px");
			 $(this).css("left", (findPosX(obj) + obj.offsetWidth -2) + "px");
		 });
	}).resize(); 
}

String.prototype.Blength = function() {
    var arr = this.match(/[^\x00-\xff]/ig);
    return  arr == null ? this.length : this.length + arr.length;
}

$(document).ready(function(){	
	checkUtil.iniTextCondition();
});

//globe object
var checkUtil = new CheckUtil();