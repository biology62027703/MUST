function openHelpWin(){
	var temp = window.parent.document.title.split(" ");
	url = "../utility/OPENHELP.jsp";
	url += "?title=" + temp[1];
	//openwin(url, '', 400, 100);
	openDialog(url, '', 400, 100);
}

function ShowDialog(htmlfile,width,height)
  {
   //功能:開啟Dialog視窗
   //輸入: htmlfile = Html 檔案名稱
   //       width = 寬度 (px)
   //      height = 高度 )px)
   var bver,style;
   bver = navigator.appVersion.split(";");
   if(width==null&&height==null)
   {
     width=screen.width;
     height=screen.availHeight;
   }	
   if(bver[1].match("5."))
     {
      style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0"	 
      rv=window.showModalDialog(htmlfile,null,style);
     } 
   else
     {
      style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0"
      rv=window.showModalDialog(htmlfile,null,style);
     }
    return rv;
}

String.prototype.startsWith = function(str) 
{return (this.match("^"+str)==str)}

function openwin(URL,arguments,width,height){
	if(URL.startsWith("../help/")){
		return openHelpWin();
	}
	//modify by HenryHou 20050506 修改開窗方式! 以防止在SP2環境下!窗開不到最大!
    //功能:開一個非獨佔的視窗
    //參數1.URL :連結的網頁
    //參數2.arguments :傳入網頁的參數
    var str = URL;
    if("" != "" + arguments && "undefined" != "" + arguments){
    	str += "?" + arguments;
    }
    //newWindow = top.open("","","maximize=no;toolbar=no,directories=no,status=yes,scrollbars=yes,hscrollbars=no,resize=no,menubar=no");

    if(width==null && height==null){
        width = screen.width;
        height = screen.availHeight;  
    }
    
    if(width==1024){
        //width = screen.width;
        //height = screen.availHeight;  
    }
   	
    //if ((width!=null)&&(height!=null)){
    //    //alert("1_width:->" + width + "<-height:->" + height + "<-")
    //    newWindow.resizeTo(width, height);
    //}else{     
    //    //alert("2_width:->" + width + "<-height:->" + height + "<-")
    //    newWindow.resizeTo(screen.width,screen.availHeight);
    //}
    
	newWindow = top.open(str,"","height=" + height + ",width=" + width + ",maximize=no;toolbar=no,directories=no,status=yes,scrollbars=yes,hscrollbars=no,resize=no,menubar=no");
	
	try{
		newWindow.moveTo((screen.width-width)/2, (screen.availHeight-height)/2);
	}catch(err){
	}
	//newWindow.location = str;
}

function openwin2(URL,arguments,width,height){ 
	//modify by HenryHou 20050506 修改開窗方式! 以防止在SP2環境下!窗開不到最大!
    //功能:開一個非獨佔的視窗
    //參數1.URL :連結的網頁
    //參數2.arguments :傳入網頁的參數
    var str = URL + "?" + arguments;
    //newWindow = top.open("","","maximize=no;toolbar=no,directories=no,status=yes,scrollbars=yes,hscrollbars=no,resize=no,menubar=no");

    if(width==null && height==null){
        width = screen.width;
        height = screen.availHeight;  
    }
    
    if(width==1024){
        width = screen.width;
        height = screen.availHeight;  
    }
   	
    //if ((width!=null)&&(height!=null)){
    //    //alert("1_width:->" + width + "<-height:->" + height + "<-")
    //    newWindow.resizeTo(width, height);
    //}else{     
    //    //alert("2_width:->" + width + "<-height:->" + height + "<-")
    //    newWindow.resizeTo(screen.width,screen.availHeight);
    //}
    
	newWindow = top.open(str,"","height=" + height + ",width=" + width + ",maximize=no;toolbar=no,directories=no,status=yes,scrollbars=yes,hscrollbars=no,resizable=yes,menubar=no");
   
	newWindow.moveTo((screen.width-width)/2, (screen.availHeight-height)/2);
	//newWindow.location = str;
}

function openPrintWin(URL,arguments,width,height){ 
	//modify by HenryHou 20050506 修改開窗方式! 以防止在SP2環境下!窗開不到最大!
    //功能:開一個非獨佔的視窗
    //參數1.URL :連結的網頁
    //參數2.arguments :傳入網頁的參數
    var str = URL + "?" + arguments;
    //newWindow = top.open("","","maximize=no;toolbar=no,directories=no,status=yes,scrollbars=yes,hscrollbars=no,resize=no,menubar=no");

    if(width==null && height==null){
        width = screen.width;
        height = screen.availHeight;  
    }
    
    if(width==1024){
        width = screen.width;
        height = screen.availHeight;  
    }
   	
    //if ((width!=null)&&(height!=null)){
    //    //alert("1_width:->" + width + "<-height:->" + height + "<-")
    //    newWindow.resizeTo(width, height);
    //}else{     
    //    //alert("2_width:->" + width + "<-height:->" + height + "<-")
    //    newWindow.resizeTo(screen.width,screen.availHeight);
    //}
    
	//newWindow = top.open(str,"","height=" + height + ",width=" + width + ",maximize=no;toolbar=no,directories=no,status=yes,scrollbars=yes,hscrollbars=no,resize=no,menubar=no");
	newWindow = top.open(str,"","height=" + height + ",width=" + width + ",maximize=no;toolbar=no,directories=no,status=yes,scrollbars=yes,hscrollbars=no,resize=no,menubar=yes");
   
	newWindow.moveTo((screen.width-width)/2, (screen.availHeight-height)/2);
	//newWindow.location = str;
}


function openfullwin(URL,arguments)
{ 
   //功能:開一個全螢幕的視窗
   //參數1.URL :連結的網頁
   //參數2.arguments :傳入網頁的參數
   var str = URL+"?"+arguments;
   newWindow=window.open("","","scrollbars=yes,hscrollbars=no,fullScreen=yes");
   newWindow.location=str;
}


function resizeWin(width,height){
   window.resizeTo(width,height);
   window.moveTo((screen.width-width)/2,(screen.height-height)/2);
}

function openDialog(htmlfile,arguments,width,height,scroll)
	{
	//功能:開啟Dialog視窗
	//輸入: htmlfile = Html 檔案名稱
	//       width = 寬度 (px)
	//      height = 高度 )px)
	var bver,style;
	bver = navigator.appVersion.split(";");
	if(bver[1].match("5.")){
		if(scroll+"" == "1")
			style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:1;help:0;status:0";	 
		else
			style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0";
		rv=window.showModalDialog(htmlfile,arguments,style);
	}else{
		if(scroll==1)
			style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:1;help:0;status:0";
		else
			style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0";
		rv=window.showModalDialog(htmlfile,arguments,style);
	}
	return rv;
}


/*DialogBack()函數：用於子視窗
  說明：將值傳回母視窗
  參數：form_name：from的element名稱

  DialogOpener()函數：用於母視窗
  說明：接收子視窗回傳值
  參數：file：檔案名稱  form_name：from的element名稱  x,y:dialog位置，width,height：視窗大小*/

function DialogBack(form_name){
    window.parent.returnValue = form_name.value;
	window.parent.close();
}

function DialogOpener(file,form_name,x,y,width,height){
	var style = "dialogTop:"+y+"px;dialogLeft:"+x+"px;dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0";
    var rv=window.showModalDialog(file,'',style);
    if (rv+""!="undefined")
	   form_name.value=rv;
}
function DialogOpener1(file,form_name,x,y,width,height){
	var style = "dialogTop:"+y+"px;dialogLeft:"+x+"px;dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0";
    var rv=window.showModalDialog(file,'',style);                 
    if (rv+""!="undefined")                                                              
    { 	   
	   //var temp=rv.split(" ");
	   //rv=temp[1]; 
	   form_name.value=form_name.value+rv;                                                                       
    } 	   
}                                                             


//開窗位置，回傳值
function dialogPosition(file,x,y,width,height){
    if(width==1024){
        width = screen.width;
        height = screen.availHeight;  
    }

	var style = "dialogTop:"+y+"px;dialogLeft:"+x+"px;dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:no";
	var rv = window.showModalDialog(file,'',style);
	
	if (rv + "" != "undefined")
		return rv;
}

//將回傳值分別填到兩個欄位
function splitValue(str,obj,obj1){
   var temp=str.split(" ");
   obj.value=temp[0];
   obj1.value=temp[1];
}


function SetAction(object,file){
   //功能:設定Submit File Name
   //輸入: object = Form object, file = submit file
   object.action = file;
}

function GoBackPage(){
   //功能:上一頁
   history.go(-1);
}

function SetSubmit(object,file) {
   //功能:設定Submit File Name 並且submit
   //輸入: object = Form object, file = submit file
   object.action = file;
   object.submit();
}

function SetNewWindow(object,file) {
   //功能:設定Submit File Name 並且submit , open new window
   //輸入: object = Form object, file = submit file
   object.action = file;
   object.target = "_blank"
   object.submit();
}

function AddFavorite(url,title){
   //將網址增加到我的最愛
   //輸入: url = 網址
   //       title = 抬頭
   window.external.AddFavorite(url,title)
}


function sort_tdc(tdc1){ 
   //功能:改變 TDC 元件排序 升幕/降幕
   //輸入:tcd 物件
   alert(tdc1.recordset.AbsolutePosition);
   if (tdc1.sortcolumn.substring(0,1)=="+")
     tdc1.sortcolumn="-"+window.event.srcElement.id;
   else
     tdc1.sortcolumn="+"+window.event.srcElement.id;
   tdc1.reset();
}

function Printer(){
   //呼叫印表機將目前頁面印出,IE5用
    window.print();
}


function disable_obj(forms_num,elements_num)
 {
  //功能: disable 元件
  //輸入 : forms_num : form 的名稱或 from 的position
  //       elements_num :元件的名稱或 元件的position	
	if (isNaN(forms_num))
	{
		if (isNaN(elements_num))
			return eval(forms_num + '.' + elements_num + '.disabled = true');
		else
			return eval(forms_num + '.elements[elements_num].disabled = true');
	}
	else
	{     
		if (isNaN(elements_num))
			return eval('window.document.forms['+forms_num+'].' + elements_num + '.disabled = true');
		else
			return window.document.forms[forms_num].elements[elements_num].disabled = true;
	}
 }

function enable_obj(forms_num,elements_num)
 {
   //功能: enable 元件
   //輸入 : forms_num : form 的名稱或 from 的position
   //       elements_num :元件的名稱或 元件的position
	if (isNaN(forms_num))
	{
		if (isNaN(elements_num))
			return eval( forms_num + '.' + elements_num + '.disabled = false');
		else
			return eval( forms_num + '.elements[elements_num].disabled = false');
	}
	else
	{
		if (isNaN(elements_num))
			return eval('window.document.forms[forms_num].' + elements_num + '.disabled = false');
		else
			return window.document.forms[forms_num].elements[elements_num].disabled = false;
	}
 }
 
 function disableAll_obj(forms_num)
 {
  //功能: disable 元件
  //輸入 : forms_num : form 的名稱或 from 的position
  //       elements_num :元件的名稱或 元件的position	
	if (isNaN(forms_num))
	{
		for(i=0;i<eval(forms_num + '.elements.length');i++){
		    eval(forms_num + '.' + 'elements['+i + '].disabled = true');
		}    
		
	}
	else
	{     
		for(i=0;i<eval('window.document.forms[' + forms_num + '].elements.length');i++){
		    eval('window.document.forms[' + forms_num + '].' + 'elements['+i + '].disabled = true');
		}    
	}
 }
 
 function enableAll_obj(forms_num)
 {
  //功能: disable 元件
  //輸入 : forms_num : form 的名稱或 from 的position
  //       elements_num :元件的名稱或 元件的position	
	if (isNaN(forms_num))
	{
		for(i=0;i<eval( forms_num + '.elements.length');i++){
		    eval(forms_num + '.' + 'elements['+i + '].disabled = false');
		}    
		
	}
	else
	{     
		for(i=0;i<eval('window.document.forms[' + forms_num + '].elements.length');i++){
		    eval('window.document.forms[' + forms_num + '].' + 'elements['+i + '].disabled = false');
		}    
	}
 }

/*
  load()函數 :
  說明 :網頁轉向
  參數
  target :要傳到那個Frame ，使用 all 表示要取代整個網頁，不再切割Frame 
  url :網頁名稱
*/
function load(target,url)
{
var tempString = '';
if (target.toLowerCase()!='all')
{
tempString = 'window.parent.' + target + '.location.href=\'' + url + '\'';
}
else
{
tempString = 'window.parent.location.href=\'' + url + '\'';
}

//alert(tempString);
eval(tempString);

} 