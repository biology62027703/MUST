function openHelpWin(){
	var temp = window.parent.document.title.split(" ");
	url = "../utility/OPENHELP.jsp";
	url += "?title=" + temp[1];
	//openwin(url, '', 400, 100);
	openDialog(url, '', 400, 100);
}

function ShowDialog(htmlfile,width,height)
  {
   //�\��:�}��Dialog����
   //��J: htmlfile = Html �ɮצW��
   //       width = �e�� (px)
   //      height = ���� )px)
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
	//modify by HenryHou 20050506 �ק�}���覡! �H����bSP2���ҤU!���}����̤j!
    //�\��:�}�@�ӫD�W��������
    //�Ѽ�1.URL :�s��������
    //�Ѽ�2.arguments :�ǤJ�������Ѽ�
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
	//modify by HenryHou 20050506 �ק�}���覡! �H����bSP2���ҤU!���}����̤j!
    //�\��:�}�@�ӫD�W��������
    //�Ѽ�1.URL :�s��������
    //�Ѽ�2.arguments :�ǤJ�������Ѽ�
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
	//modify by HenryHou 20050506 �ק�}���覡! �H����bSP2���ҤU!���}����̤j!
    //�\��:�}�@�ӫD�W��������
    //�Ѽ�1.URL :�s��������
    //�Ѽ�2.arguments :�ǤJ�������Ѽ�
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
   //�\��:�}�@�ӥ��ù�������
   //�Ѽ�1.URL :�s��������
   //�Ѽ�2.arguments :�ǤJ�������Ѽ�
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
	//�\��:�}��Dialog����
	//��J: htmlfile = Html �ɮצW��
	//       width = �e�� (px)
	//      height = ���� )px)
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


/*DialogBack()��ơG�Ω�l����
  �����G�N�ȶǦ^������
  �ѼơGform_name�Gfrom��element�W��

  DialogOpener()��ơG�Ω������
  �����G�����l�����^�ǭ�
  �ѼơGfile�G�ɮצW��  form_name�Gfrom��element�W��  x,y:dialog��m�Awidth,height�G�����j�p*/

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


//�}����m�A�^�ǭ�
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

//�N�^�ǭȤ��O��������
function splitValue(str,obj,obj1){
   var temp=str.split(" ");
   obj.value=temp[0];
   obj1.value=temp[1];
}


function SetAction(object,file){
   //�\��:�]�wSubmit File Name
   //��J: object = Form object, file = submit file
   object.action = file;
}

function GoBackPage(){
   //�\��:�W�@��
   history.go(-1);
}

function SetSubmit(object,file) {
   //�\��:�]�wSubmit File Name �åBsubmit
   //��J: object = Form object, file = submit file
   object.action = file;
   object.submit();
}

function SetNewWindow(object,file) {
   //�\��:�]�wSubmit File Name �åBsubmit , open new window
   //��J: object = Form object, file = submit file
   object.action = file;
   object.target = "_blank"
   object.submit();
}

function AddFavorite(url,title){
   //�N���}�W�[��ڪ��̷R
   //��J: url = ���}
   //       title = ���Y
   window.external.AddFavorite(url,title)
}


function sort_tdc(tdc1){ 
   //�\��:���� TDC ����Ƨ� �ɹ�/����
   //��J:tcd ����
   alert(tdc1.recordset.AbsolutePosition);
   if (tdc1.sortcolumn.substring(0,1)=="+")
     tdc1.sortcolumn="-"+window.event.srcElement.id;
   else
     tdc1.sortcolumn="+"+window.event.srcElement.id;
   tdc1.reset();
}

function Printer(){
   //�I�s�L����N�ثe�����L�X,IE5��
    window.print();
}


function disable_obj(forms_num,elements_num)
 {
  //�\��: disable ����
  //��J : forms_num : form ���W�٩� from ��position
  //       elements_num :���󪺦W�٩� ����position	
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
   //�\��: enable ����
   //��J : forms_num : form ���W�٩� from ��position
   //       elements_num :���󪺦W�٩� ����position
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
  //�\��: disable ����
  //��J : forms_num : form ���W�٩� from ��position
  //       elements_num :���󪺦W�٩� ����position	
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
  //�\��: disable ����
  //��J : forms_num : form ���W�٩� from ��position
  //       elements_num :���󪺦W�٩� ����position	
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
  load()��� :
  ���� :������V
  �Ѽ�
  target :�n�Ǩ쨺��Frame �A�ϥ� all ��ܭn���N��Ӻ����A���A����Frame 
  url :�����W��
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