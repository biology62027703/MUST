<%@page language="java" contentType="text/html;charset=MS950" import="java.lang.*,java.util.*,java.text.*"%>
<%
/*
----------------------------------------------------------------------------------------
���D�渹�GBug #772 - KSYCJ0950001
�ק�K�n�G����K���}�����
��s�����Gv9512
�ק�H���Gbalasom                                
�ק����G95.11.06             
----------------------------------------------------------------------------------------
*/
	Calendar rightNow = Calendar.getInstance();

%>
<HTML>
<HEAD></HEAD>
<TITLE>�п�J���</TITLE>

<style>
	#Calendar TD TD{cursor:hand;width:20;height:25;FONT-FAMILY: Arial;TEXT-ALIGN: center}
	#Calendar SELECT{FONT-FAMILY: Arial}
	#Calendar TH{color:purple;	border-style:none;FONT-FAMILY: �з���;font-size:14pt;}
	#Calendar .pastMonth,.nextMonth{font:Arial 11pt;color:gray}
	#Calendar .nowMonth{color:blue;}
</style>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/CALENDAR.js"></script>
<script language="javaScript">
<!--
var cal=new calobj();

function initialsize(){
	var today  = new Date(<%=rightNow.get(Calendar.YEAR)%>,<%=rightNow.get(Calendar.MONTH)%>,<%=rightNow.get(Calendar.DATE)%>);
	document.all.Calendar.innerHTML=today.calendarHTML(<%=rightNow.get(Calendar.YEAR)%>,<%=rightNow.get(Calendar.MONTH)%>,<%=rightNow.get(Calendar.DATE)%>);
}

function onPickDate(){
	var today=new Date(<%=rightNow.get(Calendar.YEAR)%>,<%=rightNow.get(Calendar.MONTH)%>,<%=rightNow.get(Calendar.DATE)%>);
	var pickDate=new Date(cal.pickDate);
	//2006.11.06 modified by balasom  [Bug #772 - KSYCJ0950001 ]
	var year=cal.pickDate.substr(0,3);
	var month=cal.pickDate.substr(3,2);
	var date=cal.pickDate.substr(5,2);

	if(month =='13'){
		year=eval(year)+1;
		if ((year+'').length==2)
			year = '0'+year;
		month='01';
	}
	if(month =='00'){
		year=eval(year)-1;
		if ((year+'').length==2)
			year = '0'+year;
		month='12';
	}
	window.returnValue=year+month+date;
	window.close();

}
-->
</script>

<BODY onload='initialsize();'>
<DIV ID='Calendar' style="position:absolute;left:20px;top:15px"></DIV>
</BODY>
</HTML>
