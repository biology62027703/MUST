//將0900627格式化成090/06/27
function fDate(value)
{
	if (value.charAt(0)=="-")
	{
		if (value.length==7)
			var str=value;
		else
			return "";
	}
	else
		var str=(value.length==6)?"0"+value:value;

	str=str.substring(0,3)+"/"+str.substring(3,5)+"/"+str.substring(5,7);
	return str;
}


//將西元日期格式轉成2000/01/01
function cDate(date)
{
	var year=getCyear(date);
	var month=date.getMonth()+1;
	var day=date.getDate();
	return year+"/"+month+"/"+day;
}

//將西元日期格式，取中文年
function getCyear(date)
{
	var year="";
	if (date.getYear()>=2000)
		year=date.getYear();
	else
	{
		if (date.getYear()<100)
			year=date.getYear()+1900;
		else
			year=date.getYear();
	}
	return year;
}

//格式化小數後幾位，str數字字串，num小數點幾位。
function numFormat(str,num)
{
	var n=Math.pow(10,num);
	var value=Math.round(parseFloat(str)*n)/n;
	return value;
}


//七碼民國日0900524回傳西元日期格式
function dateType(date)
{
	var cdate="";
	var year="";
	if (date.charAt(0)=="-")
	{
		if (date.length==7)
		{
			cdate=date;
			year=parseFloat(cdate.substring(0,3))+1912;
		}
		else
			return "";
	}
	else
	{
		cdate=(date.value=="6")?"0"+date:date;
		year=parseFloat(cdate.substring(0,3))+1911;
	}

	var month=cdate.substring(3,5);
	var day=cdate.substring(5,7);
	var value=year+"/"+month+"/"+day;
	return value;
}

//將西元日期轉七碼民國日0900524
function dateType1(date)
{
	var odate=new Date(date);
	var year="";
	if (parseFloat(date.substring(0,4))>=2000)
	{
		year=odate.getYear()-1911;
		year=((year+"").length==2)?"0"+year:year+"";
	}
	else
	{
		if (parseFloat(date.substring(0,4))>=1900)
		{
			if (parseFloat(date.substring(0,4))>1911)
				year=odate.getYear()-11;
			else
				year=odate.getYear()-12;
		}
		else
			year=odate.getYear()-1911;

		if (year>0)
			year=((year+"").length==1)?"00"+year:((year+"").length==2)?"0"+year:year+"";
		else
			year=((-year+"").length==1)?"-0"+(-year):"-"+(-year);
	}
		
	//var year=(odate.getYear())>100?odate.getYear()-1911:odate.getYear()-11;
	var month=odate.getMonth()+1;
	var day=odate.getDate();
	//year=((year+"").length==2)?"0"+year:year+"";
	month=((month+"").length==1)?"0"+month:month+"";
	day=((day+"").length==1)?"0"+day:day+"";
	return year+month+day;
}


//計算相差幾日，日前後都算。sday起算日，eday終止日，格式為0900340。
function many_day(sday,eday)
{
	
	if (sday.charAt(0)!="-")
		sday=(sday.length==6)?"0"+sday:sday;

	if (eday.charAt(0)!="-")
		eday=(eday.length==6)?"0"+eday:eday;

	var csd=dateType(sday);
	var ced=dateType(eday);
	var sDay=new Date(csd);
	var eDay=new Date(ced);
	var mills=24*60*60*1000;
	var millsDay=sDay.getTime()+8*60*60*1000;//格林威治時間台灣加8:00
	var milleDay=eDay.getTime()+8*60*60*1000;
	var dtol=parseInt((milleDay-millsDay)/mills);
	if (dtol<0)
		dtol=dtol-1;

	return dtol+1;
    
}

//計算相差幾日，日前後都算。sday起算日，eday終止日，格式為0900340。
function many_day1(sday,eday)
{
	
	if (sday.charAt(0)!="-")
		sday=(sday.length==6)?"0"+sday:sday;

	if (eday.charAt(0)!="-")
		eday=(eday.length==6)?"0"+eday:eday;

	var csd=dateType(sday);
	var ced=dateType(eday);
	var sDay=new Date(csd);
	var eDay=new Date(ced);
	var mills=24*60*60*1000;
	var millsDay=sDay.getTime()+8*60*60*1000;//格林威治時間台灣加8:00
	var milleDay=eDay.getTime()+8*60*60*1000;
	var dtol=parseInt((milleDay-millsDay)/mills);

	return dtol;
    
}


//日期加減type=y or m or d，date格式為0900340。
function dateAdd(type,date,num)
{
	num=parseFloat(num);
	var cdate="";
	if (date.charAt(0)=="-")
		cdate=date
	else
		cdate=(date.length==6)?"0"+date:date;
	cdate=dateType(cdate);
	//alert(cdate);
	cdate=new Date(cdate);
	var year=getCyear(cdate);
	var month=cdate.getMonth();
	var day=cdate.getDate();
	//alert(year);
	switch (type)
	{
		case "y":	var rdate=new Date(year+num,month,day);
					break;
		case "m":	var rdate=new Date(year,month+num,day);
					for (var i=1;i<4;i++)
					{
						if (((month+num)%12)==rdate.getMonth())
							break;
						else
						{
							var nyear=getCyear(rdate);
							rdate=new Date(rdate.getYear(),rdate.getMonth(),rdate.getDate()-1);
						}
					}
					break;
		case "d":	var rdate=new Date(year,month,day+num);
					break;
					
	}
	//alert(rdate);
	rdate=cDate(rdate);
	//alert(rdate);
	rdate=dateType1(rdate);
	return rdate;
}


//type=y or m，sday起始日，eday終止日格式為0900340，本金money，利率rate，flag true 為不進位。
function interest(type,sday,eday,money,rate,flag){
	var csd=(sday.length==6)?"0"+sday:sday;
	var ced=(eday.length==6)?"0"+eday:eday;
	var value=money*many_day(csd,ced);
	if (flag=="true")
	{
		switch (type){
			case 'y':return ((value*rate)/365);break;
			case 'm':return ((value*rate)/30);break;
		}
	}
	else
	{
		switch (type){
			case 'y':return numFormat((value*rate)/365,0);break;
			case 'm':return numFormat((value*rate)/30,0);break;
		}
	}
}


//移除全部option項目，obj是form.select
function removeAll(obj)
{
	while(obj.options.length!=0)
	{
		obj.remove(0);
	}
}



//新增option項目，obj是form.select，name是陣列名稱，可選擇value及option的值，divide分隔回傳值及文字
function addOption(obj,name,type,limit,divide){
    if (divide==null)
		divide=" ";
	for(i in name)
	{
		if ((type==4 || type==5 || type==6 || type==7) && name[i][0]==limit)
			var item=document.createElement("OPTION");
		if (type==0 || type==1 || type==2 || type==3 || type==8 || type==9 || type==10 || type==11 || type==12) 
			var item=document.createElement("OPTION");
		if ((type==13) && name[i][0].substring(0,1)==limit)
			var item=document.createElement("OPTION");
		switch(type)
		{
			case 0 : item.value=name[i][1];
					 item.text=name[i][1];break;
			case 1 : item.value=name[i][0];
	                 item.text=name[i][1];break;
			case 2 : item.value=name[i][0];
	                 item.text=name[i][0]+divide+name[i][1];break;
			case 3 : item.value=name[i][0]+divide+name[i][1];
	                 item.text=name[i][0]+divide+name[i][1];break;
			case 4 : if (name[i][0]==limit)
					{
						item.value=name[i][1];
						item.text=name[i][1]+divide+name[i][2];break;
					}
					break;
			case 5 : if (name[i][0]==limit)
					{
						item.value=name[i][1]+divide+name[i][2]+divide+name[i][3];
						item.text=name[i][2];break;
					}
					break;
			case 6 : if (name[i][0]==limit)
					{
						item.value=name[i][1]+divide+name[i][2]+divide+name[i][3]+divide+name[i][6];
						item.text=name[i][1]+divide+name[i][2];break;
					}
					break;

			case 7 : if (name[i][0]==limit)
					{
						item.value=name[i][1]+divide+name[i][2];
						item.text=name[i][1]+divide+name[i][2];break;
					}
					break;
			case 8 : item.value=name[i][0]+divide+name[i][1]+divide+name[i][2];
					 item.text=name[i][0]+divide+name[i][1];break;
			case 9 : item.value=name[i][0]+divide+name[i][1]+divide+name[i][2];
	                 item.text=name[i][0];break;
			case 10 : item.value=name[i][0];
	                  item.text=name[i][0];break;
			case 11 : item.value=name[i][0]+divide+name[i][1];
	                  item.text=name[i][1];break;
			case 12 : item.value=name[i][1];
	                  item.text=name[i][0]+divide+name[i][1];break;
			case 13 : if (name[i][0].substring(0,1)==limit)
					{
						item.value=name[i][0];
						item.text=name[i][0]+divide+name[i][1];break;
					}
					break;

		}
		if ((type==4 || type==5 || type==6 || type==7) && name[i][0]==limit)
			obj.add(item);
		if (type==0 || type==1 || type==2 || type==3 || type==8 || type==9 || type==10 || type==11 || type==12)  
			obj.add(item);
		if ((type==13) && name[i][0].substring(0,1)==limit)
			obj.add(item);
	}
}



function reaction(formname,filename,method)
{
	var str="document."+formname+".method='"+method+"';";
	var str1="document."+formname+".action='"+filename+"';";
	var str2=formname+".submit();";
	str;
	eval(str1);
	eval(str2);
}

//將一組分數相加，簡化成分數
function gcdFun(inp,out)
{
	var temp=(inp.value).split("+");
	var numerator=0;
	var denominator=1;
	var flag=true;
	for (i=0;i<temp.length;i++){
		var tmp=temp[i].split("/");
		denominator=tmp[1]*denominator;
	}
	for (i=0;i<temp.length;i++){
		var tmp=temp[i].split("/");
		numerator=numerator+(tmp[0]*denominator/tmp[1]);
	}
	var result=gcd(numerator,denominator);
	numerator=numerator/result;
	denominator=denominator/result;
	out.value=numerator+"/"+denominator;
}


//取兩數之最大公因數
function gcd(x,y)
{
	var FindAnswer=false;
	var result=0;
	while(!FindAnswer)
	{
		if (x>y)
		{
			x=x % y;
			if (x==0)
			{
				result=y;
				FindAnswer=true;
			}
		}
		else
		{
			y=y % x;
			if (y==0)
			{
				result=x;
				FindAnswer=true;
			}
		}
	}
	return result;
}


function hiddenFrame(frameName,formName,nameArray,valueArray)
{
	var temp="";
	for(i=0;i<nameArray.length;i++)
	{
		eval("window.parent."+frameName+"."+formName+"."+nameArray[i]+".value='"+valueArray[i]+"';");
	}
	eval("window.parent."+frameName+"."+formName+".submit();");
}



//研考，科長，庭長，書記官，法官畫面權限判斷
//案件查詢專用，flag若有值，default帶出兼股，否則帶本股
function sysidInit(IdPriv,sys,dv,agentStr,flag,dptcd,dpt)
{
	removeAll(form.sysid);
	addOption(form.sysid,S10,2);

	if (IdPriv=="A") 
	{
		form.sysid.disabled=false;
		//SetSelectBoxSelected(form.sysid,getSysidByOwner(owner));
		SetSelectBoxSelected(form.sysid,sys);
		if (flag=="")
		{
			form.dptcd.value=dptcd;
			form.dpt.value=dpt;
		}
		else
		{
			//研考專用
			if (sys=="R")
				addTextValue(form.dptcd,form.dpt,C16G,1,form.sysid.value);
			else
				addTextValue(form.dptcd,form.dpt,C16,1,form.sysid.value);
		}
	}
	if (IdPriv=="B") 
	{
		SetSelectBoxSelected(form.sysid,getSysidByOwner(sys));
		form.sysid.disabled=true;
		if (flag=="")
		{
			form.dptcd.value=dptcd;
			form.dpt.value=dpt;
		}
		else
			addTextValue(form.dptcd,form.dpt,C16,1,sys);
	}
	if (IdPriv=="C") 
	{
		SetSelectBoxSelected(form.sysid,getSysidByOwner(sys));
		form.sysid.disabled=true;
		if (flag=="")
		{
			form.dptcd.value=dptcd;
			form.dpt.value=dpt;
		}
		else
			addTextValue(form.dptcd,form.dpt,C16DV,1,sys+dv);
	}
	if (IdPriv=="D" || IdPriv=="E") 
	{
		SetSelectBoxSelected(form.sysid,getSysidByOwner(sys));
		form.sysid.disabled=true;
		if (flag=="")
		{
			form.dptcd.value=dptcd;
			form.dpt.value=dpt;
		}
		else
		{
			var temp=agentStr.split("@");
			form.dptcd.value=temp[0];
			form.dpt.value=temp[1];
		}
	}	
}

function addTextValue(obj,obj1,name,type,limit)
{
	var value="";
	var text="";
	for(i in name)
	{
		switch(type)
		{
			case 1 : if (name[i][0]==limit)
					{
						value=value+name[i][1]+",";
						obj.value=value.substring(0,value.length-1);
						text=text+name[i][2]+",";
						obj1.value=text.substring(0,text.length-1);
						break;
					}	
		}
	}
}

//決定院長研考的owner
function c_owner(inputobj,outobj)
{
  	var sysid=inputobj.value;
  	for(i=0;i<S10.length;i++)
  	{
     	if (S10[i][0]==sysid)
     	{
	    	outobj.value=S10[i][2];
	 	}
  	}
} 

//根據科室別，秀不同股別
//案件查詢專用，flag若有值，default帶出兼股
function sysidOnchange(IdPriv,sys,dv,agentStr,flag)
{
	//alert("sysidOnchange="+sys);
	form.dptcd.value="";
	form.dpt.value="";

	if (IdPriv=="A") 
	{
		//研考專用
		if (sys=="R")
			addTextValue(form.dptcd,form.dpt,C16G,1,form.sysid.value);
		else
			addTextValue(form.dptcd,form.dpt,C16,1,form.sysid.value);
	}
	if (IdPriv=="B") 
	{
		SetSelectBoxSelected(form.sysid,getSysidByOwner(sys));
		form.sysid.disabled=true;
		addTextValue(form.dptcd,form.dpt,C16,1,sys);
	}
	if (IdPriv=="C") 
	{
		SetSelectBoxSelected(form.sysid,getSysidByOwner(sys));
		form.sysid.disabled=true;
		addTextValue(form.dptcd,form.dpt,C16DV,1,sys+dv);
	}
}


//字別開窗
function crmidOpen(obj,IdPriv,sys,x,y,width,height)
{
   	//alert(sys);
	var file="";
	//if (IdPriv=="A") sys=getOwnerBySysid(form.sysid.value);
	if (IdPriv=="A") sys=form.sysid.value;
   
   	switch (sys)
   	{
      	case "H" : file="../html/HCRMID.html";break;
	  	case "I" : file="../html/ICRMID.html";break;
	  	case "V" : file="../html/VCRMID.html";break;
		case "U" : file="../html/UCRMID.html";break;
	  	case "O" : file="../html/OCRMID.html";break;
	  	case "K" : file="../html/KCRMID.html";break;
   	}
   	
   	if (width==null) width=250;
   	if (height==null) height=300;
   	if (x==null) x=(screen.width-width)/2;
   	if (y==null) y=(screen.height-height)/2;
   	var temp=dialogPosition(file,x,y,width,height);
   	if (temp+""!="undefined")
   	{
       	var str=temp.split(" ");
       	obj.value=str[1];
  	}
   
}

function crmidOnblur(obj,IdPriv,sys)
{
   	if(obj.value=="") return;
   	var name=new Array();
   	var value=obj.value;
   	//if (IdPriv=="A") sys=getOwnerBySysid(form.sysid.value);
	if (IdPriv=="A") sys=form.sysid.value;
   	
   	switch (sys)
   	{
      	case "H" : name=HCRMID;break;
	  	case "I" : name=ICRMID;break;
	  	case "V" : name=VCRMID;break;
	  	case "U" : name=UCRMID;break;
		case "O" : name=OCRMID;break;
	  	case "K" : name=KCRMID;break;
   	}   	
 
   	if (!isNaN(obj.value))
   	{
      	value=(value.length==1)?"000"+value:(value.length==2)?"00"+value:(value.length==3)?"0"+value:value;
	  	for(i=0;i<name.length;i++)
	  	{
         	if(name[i][0]==value)
         	{
	        	obj.value=name[i][1];
				return;
	     	}
      	}
	  	display(0,"無此代碼");
   	}
   	else
   	{
      	for(i=0;i<name.length;i++)
      	{
         	if(name[i][1]==value)
         	{
	        	obj.value=name[i][1];
				return;
	     	}
      	}
	  	display(0,"無此字串");
   	}
   
}

function getSysidByOwner(sys,array)
{
	var sysid=sys;
	return sysid;
}

function getOwnerBySysid(sysid,array)
{
	var owner="";
	var name=S10;
	if (array!=null) name=array;
	for(i=0;i<name.length;i++)
	{
		if(sysid==name[i][0])
		{
			owner=name[i][2];
			break;
		} 
	}
	return owner
}

//type是single表示只能挑選一股
function dptClick(IdPriv,sys,dv,agentStr,x,y,width,height,type)
{
	var limit="";
	var file="";
	if (IdPriv=="A" || IdPriv=="B") 
	{
		limit=form.sysid.value;
		//研考專用
		if (sys=="R")
			file="C16G";
		else
			file="C16";
	}
	if (IdPriv=="C") 
	{
		limit=sys+dv;
		file="C16DV";
	}

   	if (width==null || width=='') width=250;
   	if (height==null || height=='') height=380;
   	if (x==null || x=='') x=(screen.width-width)/2;
   	if (y==null || y=='') y=(screen.height-height)/2;

	if (IdPriv=="A" || IdPriv=="B" || IdPriv=="C") 
	{
		if (type=="single")
			var temp=dialogPosition("../utility/OPENSINGLE.jsp?file="+file+"&type=7&limit="+limit,x,y,width,height);
		else
			var temp=dialogPosition("../utility/OPENMULTIPLE.jsp?file="+file+"&type=7&limit="+limit,x,y,width,height);
		
		if ((temp+"")!="undefined")
		{
			var str=temp.split(",");
			var dptcd="";
			var dpt="";
			for(i=0;i<str.length;i++)
			{
				var dpt_t=str[i].split(" ");
				dpt=dpt+dpt_t[1]+",";
				dptcd=dptcd+dpt_t[0]+",";
			}
			dpt=dpt.substring(0,dpt.length-1);
			dptcd=dptcd.substring(0,dptcd.length-1);
			form.dptcd.value=dptcd;
			form.dpt.value=dpt;
		}
	}

	if (IdPriv=="D" || IdPriv=="E") 
	{
		var temp=dialogPosition("OPEN.jsp?args="+agentStr+"&type="+type,x,y,width,height);
		 if ((temp+"")!="undefined"){
		    var str=temp.split(",");
		    var dptcd="";
		    var dpt="";
		    for(i=0;i<str.length;i++){
		       var dpt_t=str[i].split(" ");
			   dpt=dpt+dpt_t[1]+",";
			   dptcd=dptcd+dpt_t[0]+",";
		    }
		    dpt=dpt.substring(0,dpt.length-1);
		    dptcd=dptcd.substring(0,dptcd.length-1);
		    form.dptcd.value=dptcd;
	        form.dpt.value=dpt;
		 }
	}
}


function c_sysdate()
{
	for(i=0;i<R06.length;i++)
	{
		if(form.sysid.value==R06[i][0]){
			form.sysdate.value=R06[i][1];
			break;
		} 
	}
}

function disabledAll(flag)
{
	if (flag=="true")
	{
		for(i=0;i<document.forms[0].elements.length;i++)
		{
			if (document.forms[0].elements[i].type!="hidden")
				document.forms[0].elements[i].disabled=true;
		}
		/*
		var inputobj=document.all.tags("input");
		for(var j=0;j<inputobj.length;j++)
		{
			if (inputobj[j].type=="image")
			{
				var cursorObj=new cursorType(0,inputobj[j]);
				inputobj[j].onmouseover=cursorObj;
				inputobj[j].disabled=true;
			}
		}*/
	}
}


function cursorType(type,obj)
{
	switch (type)
	{
		case 0 :obj.style.cursor="default";break;
		case 1 :obj.style.cursor="hand";break;
		case 2 :obj.style.cursor="text";break;
		case 3 :obj.style.cursor="n-resize";break;
		case 4 :obj.style.cursor="wait";break;
		case 5 :obj.style.cursor="help";break;
	}
}


/*
function remarkReverse(str)
{
	var rv="";
	if (str!="")
	{
		var index=str.indexOf("`");
		if (index!=-1)
		{
			var temp=str.split("`");
			for (i=0;i<temp.length;i++)
			{
				if (temp[i].length!=0)
				{
					rv=rv+temp[i]+"\n";
				}
			}
			return rv;
		}
		else
		{
			return str;
		}
	}
	else
	{
		return str;
	}
}*/


function checkCheckboxChecked(formObj)
{
	
	var limit = formObj.length;
	var j=0;
	//若陣列只有一筆
	if (limit+""=="undefined")
	{
		if (formObj.checked)
			j++;
	}
	else
	{
		for(i=0;i<limit;i++) 
		{
			if(formObj.elements[i].type == 'checkbox' &&   formObj.elements[i].checked) 
				j=j+1;
		}
	}

	if (j==0) 
	{
		display("0","請將核取方塊打勾");
		return false;
	}
	else
		return true;
}


function selectedCheckbox(formObj,flag)
{
	var limit = formObj.length;
	//若陣列只有一筆
	if (limit+""=="undefined")
	{
		if (formObj.checked)
			formObj.checked=flag;
	}
	else
	{
		for(i=0;i<limit;i++) 
		{
			if(formObj.elements[i].type=="checkbox")
			{
				formObj.elements[i].checked=flag;
			}
		}
	}
}

function buttonDisabled(obj,second)
{
	var str=obj+".disabled=true";
	eval(str);
	str=obj+".disabled=false";
	window.setTimeout(str,second);
}
