/*
=======================================================================================================
File Name     : Calendar.js
Language      :JavaScript
Last Modify   :2004/03/01
CopyingRight:Ho Yi-Lin
Descrition		: 日期選擇
Functional: new calobj ,event onPickdate();
=======================================================================================================
*/
Date.prototype.calendarHTML=calendarHTML;

function calobj(){
	//private
	var today=new Date();
	var thisYear=(today.getYear()<100)?today.getYear()+1900:today.getYear();
	var thisMonth=today.getMonth()+1;
	var thisDay=today.getDate();
	//public
	this.today=thisYear+"/"+thisMonth+"/"+thisDay;
}

function calendarHTML(yy,mm,dd)
{

	var Year=(this.getYear()<100)?this.getYear()+1900:this.getYear();
	var Month=this.getMonth();
	var today=new Date(yy,mm,dd);
	var thisYear=(today.getYear()<100)?today.getYear()+1900:today.getYear();
	var thisMonth=today.getMonth();
	var thisDay=today.getDate();
	//	Bug #4985 - TPH0MJ0990001, modify by frank, 年度選擇改成當年度加減五年
	var startYear = thisYear - 5;
	var endYear=thisYear+5;
	
	var dow   = new Array("日","一","二","三","四","五","六");
	var days  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	
	
	var CalHTML="<FORM NAME='calendarForm'>";
	//	Bug #4985 - TPH0MJ0990001, modify by frank
    CalHTML+="<TABLE width=\"100%\"><TR style=\"width:300;\">&nbsp;&nbsp;民國";
	CalHTML+="<input type='button' value='-' style='width:16;height:23' onClick='decreYear(this.form);' />";
	
	  //年份的SelectBox
	
     CalHTML+="<SELECT name='Year' onChange='changeYear(this.form);'>";

	 for (year=startYear; year<=endYear; year++){         
		 var yearFlag=(year == Year)?"SELECTED":"";
		 CalHTML+="<OPTION VALUE='"+year+"' "+yearFlag+">"+eval(year-1911)+"</OPTION>";
     }
	 //	Bug #4985 - TPH0MJ0990001, modify by frank
	 CalHTML+="<input type='button' value='+' style='width:16;height:23' onClick='increYear(this.form);'/>";
     CalHTML+="</SELECT>年";

	 CalHTML+="<input type='button' value='-' style='width:16;height:23' onClick='decreMonth(this.form);' />";

	  //月份的SelectBox
	 CalHTML+="<SELECT name='Month' onChange='changeMonth(this.form);'>";

     for (month=0; month<12; month++) {
         var monthFlag=(month == Month)?"SELECTED":null;
		 CalHTML+="<OPTION VALUE='"+month+"' "+monthFlag+">"+eval(month+1)+"</OPTION>";
     }
     CalHTML+="</SELECT>";
     
	 CalHTML+="<input type='button' value='+' style='width:16;height:23' onClick='increMonth(this.form);'/>";
     
     CalHTML+="月</TR><TR><TD>";
	 var firstDay = new Date(Year,Month,1);
     var startDay = firstDay.getDay();

     //如果閏年則二月為二十九日
	 if (((Year % 4 == 0) && (Year % 100 != 0)) || (Year % 400 == 0)) days[1] = 29; 
  
     CalHTML+="<TABLE align='center' border bordercolor='white'><TR>";
	
	//印出星期
     for (i=0; i<7; i++){
          CalHTML+="<TH>"+dow[i]+"</TH>";
     }

     CalHTML+="</TR><TR>";
	
	//計算行數
	var column=0

     //印出上個月的日期
     var lastMonth = Month - 1;
	 if (lastMonth == -1)  lastMonth = 11;

     for (i=0; i<startDay; i++){
		  CalHTML+="<TD class='pastMonth' onclick='pickDate(this);' bordercolorlight='white' bordercolordark='white' onmouseover='tableOver(this)' onmouseout='tableOut(this)'>"+eval(days[lastMonth]-startDay+i+1)+"</TD>";
          column++;
     }
	
	//印出這個月日期
     for (i=1; i<=days[Month]; i++){
	
          //今天
		  if ((i == thisDay)  && (Month == thisMonth) && (Year == thisYear)){
               CalHTML+="<TD bordercolorlight='white' bordercolordark='white' onclick='pickDate(this)' onmouseover='tableOver(this)' onmouseout='tableOut(this)'><B>"+i+"</B></TD>";
		  }
          else{
				var weekColor=(column==0)?"red":"green"
				var weekFlag=(column==6||column==0)?"style='color:"+weekColor+"'":"";
				CalHTML+="<TD class='nowMonth' "+weekFlag+" onclick='pickDate(this);' bordercolorlight='white' bordercolordark='white' onmouseover='tableOver(this)' onmouseout='tableOut(this)'>"+i+"</TD>";
			}
		
		column++;

          if (column == 7){CalHTML+="</TR>";column = 0;}
     }

	//印出下個月日期
     if (column > 0){
          for (i=1; column<7; i++){
               CalHTML+="<TD class='nextMonth' onclick='pickDate(this);' bordercolorlight='white' bordercolordark='white' onmouseover='tableOver(this)' onmouseout='tableOut(this)'>"+i+"</TD>";
               column++;
          }
     }

     CalHTML+="</TR></TABLE>";
     CalHTML+="</TD></TR></TABLE></Form>";

	 return CalHTML;
}


function changeMonth (getForm) {
       Year =getForm.Year.value;
	   Month =getForm.Month.options[getForm.Month.selectedIndex].value;
		var cYear=new Date(Year,Month,1);
	   getForm.outerHTML=cYear.calendarHTML(Year,Month,1);
}
//	Bug #4985 - TPH0MJ0990001, add by frank
function increMonth (getForm) {
       Year =getForm.Year.value;
	   Month = eval( getForm.Month.options[getForm.Month.selectedIndex].value ) + 1;
	   var cYear=new Date(Year,Month,1);
	   getForm.outerHTML=cYear.calendarHTML(Year,Month,1);
}
//	Bug #4985 - TPH0MJ0990001, add by frank
function decreMonth (getForm) {
       Year =getForm.Year.value;
	   Month = eval( getForm.Month.options[getForm.Month.selectedIndex].value ) - 1;
		var cYear=new Date(Year,Month,1);
	   getForm.outerHTML=cYear.calendarHTML(Year,Month,1);
}

//	Bug #4985 - TPH0MJ0990001, add by frank
function increYear(getForm){
	Year = eval( getForm.Year.options[getForm.Year.selectedIndex].value ) + 1;
    Month = getForm.Month.value;
    var cYear=new Date(Year,Month,1);
    getForm.outerHTML=cYear.calendarHTML(Year,Month,1);
}
//	Bug #4985 - TPH0MJ0990001, add by frank
function decreYear(getForm){
	Year = eval( getForm.Year.options[getForm.Year.selectedIndex].value ) - 1;
    Month = getForm.Month.value;
    var cYear=new Date(Year,Month,1);
    getForm.outerHTML=cYear.calendarHTML(Year,Month,1);
}

function changeYear (getForm) {
       Year = eval(getForm.Year.options[getForm.Year.selectedIndex].value);
	   Month = getForm.Month.value;
	   var cYear=new Date(Year,Month,1);
	   getForm.outerHTML=cYear.calendarHTML(Year,Month,1);
}

function pickDate(td){
	var gap=1;
	//選取到上個月或下個月的日期
	if(td.className=='pastMonth'||td.className=='nextMonth') gap=(td.className=='nextMonth')?2:0;
	var Year=document.all.calendarForm.Year.value;
	var Month=parseInt(document.all.calendarForm.Month.value)+gap;
	Year = Year - 1911;
	//Month = Month -1;
	var Day=td.innerText;
	//2006.11.06 modified by balasom  [Bug #772 - KSYCJ0950001]
	if ((Year+'').length==2)
		Year = '0'+Year;
	else
		Year = ''+Year;
	if ((Month+'').length==1)
		Month = '0'+Month;	
	if ((Day+'').length==1)
		Day = '0'+Day;	
			
	pickDay= Year +Month+ Day;
	calobj.prototype.pickDate=pickDay;
    onPickDate();
}

function tableOver(td){
	td.borderColorDark='gray'
    td.borderColorLight='gray'
}

function tableOut(td){
	td.borderColorDark='white'
    td.borderColorLight='white'
}

function popDate(obj){

	web = window.location.pathname;
	if( web.indexOf("/") != 0 ) 
		web = "/"+web;
	web = web.substring(0, web.indexOf("/", 1)+1);
	
	var features="help=no;status=no;border=thin;dialogWidth=270px;dialogHeight=260px;";
	var result=showModalDialog(web + "resources/jsp/CALENDAR.jsp","",features);
	if(result){
		var dateObject=new Date(result);
		obj.value=result;
	}
}
