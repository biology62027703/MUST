function ShowDialog(htmlfile,width,height)

  {

   //�\��:�}��Dialog����

   //��J: htmlfile = Html �ɮצW��

   //       width = �e�� (px)

   //      height = ���� )px)

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

   //�\��:�}�@�ӫD�W��������

   //�Ѽ�1.URL :�s��������

   //�Ѽ�2.arguments :�ǤJ�������Ѽ�

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

   //�\��:�}�@�ӥ��ù�������

   //�Ѽ�1.URL :�s��������

   //�Ѽ�2.arguments :�ǤJ�������Ѽ�

   var str = URL+"?"+arguments;

   newWindow=window.open("","","scrollbars=yes,hscrollbars=no,fullScreen=yes");

   newWindow.location=str;

}





function openDialog(htmlfile,arguments,width,height)

  {

   //�\��:�}��Dialog����

   //��J: htmlfile = Html �ɮצW��

   //       width = �e�� (px)

   //      height = ���� )px)

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





/*DialogBack()��ơG�Ω�l����

  �����G�N�ȶǦ^������

  �ѼơGform_name�Gfrom��element�W��



  DialogOpener()��ơG�Ω������

  �����G�����l�����^�ǭ�

  �ѼơGfile�G�ɮצW��  form_name�Gfrom��element�W��  width,height�G�����j�p*/



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