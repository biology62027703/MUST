function ShowDialog(htmlfile,width,height)

  {

   //功能:開啟Dialog視窗

   //輸入: htmlfile = Html 檔案名稱

   //       width = 寬度 (px)

   //      height = 高度 )px)

   var bver,style;

   bver = navigator.appVersion.split(";");

   if(bver[1].match("5."))

     {

      style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0"	 

      rv=window.showModalDialog(htmlfile,null,style);

     } 

   else

     {

      style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0"

      rv=window.showModalDialog(htmlfile,null,style);

     }

    return rv;

}





function openwin(URL,arguments,width,height)

{ 

   //功能:開一個非獨佔的視窗

   //參數1.URL :連結的網頁

   //參數2.arguments :傳入網頁的參數

   var str = URL+"?"+arguments;

   newWindow=top.open("","","maximize=no;toolbar=no,directories=no,status=yes,scrollbars=yes,hscrollbars=no,resize=no,menubar=no");

   if ((width!=null)&&(height!=null))

      newWindow.resizeTo(width,height);

   else     

      newWindow.resizeTo(screen.width,screen.availHeight);

   newWindow.moveTo((screen.width-width)/2,(screen.availHeight-height)/2);

   newWindow.location=str;

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





function openDialog(htmlfile,arguments,width,height)

  {

   //功能:開啟Dialog視窗

   //輸入: htmlfile = Html 檔案名稱

   //       width = 寬度 (px)

   //      height = 高度 )px)

   var bver,style;

   bver = navigator.appVersion.split(";");

   if(bver[1].match("5."))

     {

      style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0"	 

      rv=window.showModalDialog(htmlfile,arguments,style);

     } 

   else

     {

      style = "dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0"

      rv=window.showModalDialog(htmlfile,arguments,style);

     }

    return rv;

}





/*DialogBack()函數：用於子視窗

  說明：將值傳回母視窗

  參數：form_name：from的element名稱



  DialogOpener()函數：用於母視窗

  說明：接收子視窗回傳值

  參數：file：檔案名稱  form_name：from的element名稱  width,height：視窗大小*/



function DialogBack(form_name){

    window.parent.returnValue = form_name.value;

	window.parent.close();

}



function DialogOpener(file,form_name,width,height){

	var style = "dialogTop:"+(event.clientY+40+height/2)+"px;dialogLeft:"+(event.clientX-width/2)+"px;dialogWidth:" +  width + "px;dialogHeight:" + height + "px;center:1;scroll:0;help:0;status:0";

    var rv=window.showModalDialog(file,'',style);

    if (rv+""!="undefined")

	   form_name.value=rv;

}