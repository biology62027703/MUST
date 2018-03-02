
/**
 * @description ï¿½Ò¦ï¿½ï¿½ï¿½ï¿½ï¿½formï¿½ï¿½ï¿½Þ§@ï¿½Ò¨Ï¥Î¦ï¿½ï¿½@ï¿½Î¤ï¿½ï¿½ï¿½Aï¿½]ï¿½tï¿½Fï¿½ï¿½ï¿½ï¿½Æ°eï¿½Xï¿½Pï¿½ï¿½ï¿½ï¿½ï¿½Ã´ï¿½ï¿½ï¿½C
 * @class ï¿½ï¿½ï¿½uï¿½ï¿½
 * @author chkchk
 * @constructor
 */
function DateUtil() {
}

DateUtil.prototype.getMonthFirstDay=function(month)
{
	return month+"01";
}

DateUtil.prototype.getMonthLastDay=function(month)
{	
	return dateUtil.calDays(dateUtil.addMonth(month)+"01", -1);
}

DateUtil.prototype.calDays=function(day, i)
{
	date = dateUtil.str2date(day);
	date.setDate(date.getDate() + i);
	return dateUtil.date2str(date);
}

DateUtil.prototype.addMonth=function(month)
{
	date = dateUtil.str2date(month+"01");
	date.setMonth(date.getMonth() + 1);
	return dateUtil.date2str(date).substring(0,5);
}

DateUtil.prototype.subMonth=function(month)
{
	date = dateUtil.str2date(month+"01");
	date.setMonth(date.getMonth() - 1);
	return dateUtil.date2str(date).substring(0,5);
}

DateUtil.prototype.getSundayOfDay=function(str){
	while( dateUtil.getWeekOfDay(str)!=0 ) {
		str = dateUtil.calDays(str, -1);
	}
	return str;
}

DateUtil.prototype.getSaturdayOfDay=function(str){
	while( dateUtil.getWeekOfDay(str)!=6 ) {
		str = dateUtil.calDays(str, 1);
	}
	return str;
}

DateUtil.prototype.getWeekOfDayCnm=function(str)
{
	day = dateUtil.str2date(str).getDay();
	if( day=="0")
		return "¤é";
	else if( day=="1")
		return "¤@";
	else if( day=="2")
		return "¤G";
	else if( day=="3")
		return "¤T";
	else if( day=="4")
		return "¥|";
	else if( day=="5")
		return "¤­";
	else if( day=="6")
		return "¤»";	
}

DateUtil.prototype.getWeekOfDay=function(str)
{
	return dateUtil.str2date(str).getDay();
}

DateUtil.prototype.str2date=function(str)
{	
	if( str.length==7 ) 
		str = (str*1 + 19110000)+"";
	return new Date(str.substring(0,4) + "/" + str.substring(4,6) + "/" + str.substring(6,8) );
}

DateUtil.prototype.date2str=function(date)
{ 
	return date.toCDateStr();
}

DateUtil.prototype.getNowDate=function()
{
	return new Date().toDateStr();
}

DateUtil.prototype.getNowCDate=function()
{
	return new Date().toCDateStr();
}

DateUtil.prototype.getNowTime=function()
{
	return new Date().toTimeStr();
}

DateUtil.prototype.DateDiff=function(str1, str2)
{		
	oDate1 = dateUtil.str2date(str1);
	oDate2 = dateUtil.str2date(str2); 
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24);
	return iDays
}

DateUtil.prototype.showCalendarDt=function(str)
{		
	
	if( str.substring(5,7)=="01" )
		return str.substring(3,5)*1+"¤ë1¤é";
	else
		return str.substring(5,7)*1;
}

//globe object
Date.prototype.toDateStr = function() {	
	year = (this.getYear() < 1000) ? (this.getYear()+1900) : this.getYear();
	while((year+"").length<3)
		year = "0"+year;
	month = this.getMonth()+1;
	while((month+"").length<2)
		month = "0"+month;
	day = this.getDate(); 
	while((day+"").length<2)
		day = "0"+day;	
	return year+""+month+""+day;
}

//globe object
Date.prototype.toCDateStr = function() {	
	year = (this.getYear() < 1000) ? (this.getYear()+1900) : this.getYear();
	year -= 1911;
	while((year+"").length<3)
		year = "0"+year;
	month = this.getMonth()+1;
	while((month+"").length<2)
		month = "0"+month;
	day = this.getDate(); 
	while((day+"").length<2)
		day = "0"+day;	
	return year+""+month+""+day;
}

//globe object
Date.prototype.toTimeStr = function() {	
	hours = this.getHours();
	while((hours+"").length<2)
		hours = "0"+hours;
	minutes = this.getMinutes()+1;
	while((minutes+"").length<2)
		minutes = "0"+minutes;
	seconds = this.getSeconds(); 
	while((seconds+"").length<2)
		seconds = "0"+seconds;	
	return hours+""+minutes+""+seconds;
}

var dateUtil = new DateUtil();