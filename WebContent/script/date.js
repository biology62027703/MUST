//------------------------------------------------------------------
//日期相關之function
//------------------------------------------------------------------


//=============================================
//function formatWDate(date)
//desc:		將民國日期格式化  0900627格式化成090/06/27
//input:	value  日期字串
//output:	str
//=============================================
function formatWDate(date) {
	if (date.charAt(0) == "-") {
		if (date.length == 7) {
			var str = date;
		} else {
			return "";
		}
	} else {
		var str = (date.length == 6) ? "0" + date : date;
	}
	str = str.substring(0, 3) + "/" + str.substring(3, 5) + "/" + str.substring(5, 7);
	return str;
}

//=============================================
//function formatWlDate(date)
//desc:		將民國日期格式化  090/06/27格式化成0900627
//input:	value  日期字串
//output:	str
//=============================================
function formatWlDate(date) {
	if (date.charAt(0) == "-") {
		if (date.length == 7) {
			var str = date;
		} else {
			return "";
		}
	} else {
		var str = (date.length == 6) ? "0" + date : date;
	}
	str = str.substring(0, 3) + str.substring(4, 6) + str.substring(7, 9);
	return str;
}

//=============================================
//function formatCDate(date)
//desc:		將西元日期格式化  20030101格式化成2003/01/01
//input:	value  日期字串
//output:	str
//=============================================
//將西元日期格式轉成2000/01/01
function formatCDate(datestr) {
	if (datestr.length == 8) {
		var str = datestr;
	} else {
		return datestr;
	}
	str = str.substring(0, 4) + "/" + str.substring(4, 6) + "/" + str.substring(6, 8);
	return str;
}


//=============================================
//function formatW2CDate(cdate)
//desc:		將民國日期格式化為西元日期 0920101 --> 2003/01/01
//input:	date  日期字串
//output:	str
//=============================================
function formatW2CDate(date) {
	var cdate = "";
	var year = "";
	if (date.charAt(0) == "-") {
		if (date.length == 7) {
			cdate = date;
			year = parseFloat(cdate.substring(0, 3)) + 1912;
		} else {
			return "";
		}
	} else {
		cdate = (date.value == "6") ? "0" + date : date;
		year = parseFloat(cdate.substring(0, 3)) + 1911;
	}
	var month = cdate.substring(3, 5);
	var day = cdate.substring(5, 7);
	var value = year + "/" + month + "/" + day;
	return value;
}

//=============================================
//function formatC2WDate(cdate)
//desc:		將西元日期格式化為民國日期 2003/01/01 --> 0920101
//input:	date  日期字串
//output:	str
//=============================================
function formatC2WDate(date) {
	var odate = new Date(date);
	var year = "";
	if (parseFloat(date.substring(0, 4)) >= 2000) {
		year = parseFloat(odate.getYear()) - 1911;
		year = ((year + "").length == 2) ? "0" + year : year + "";
	} else {
		return year;
		if (parseFloat(date.substring(0, 4)) >= 1900) {
			if (parseFloat(date.substring(0, 4)) > 1911) {
				year = parseFloat(odate.getYear()) - 11;
			} else {
				year = parseFloat(odate.getYear()) - 12;
			}
		} else {
			year = parseFloat(odate.getYear()) - 1911;
		}
		if (year > 0) {
			year = ((year + "").length == 1) ? "00" + year : ((year + "").length == 2) ? "0" + year : year + "";
		} else {
			year = ((-year + "").length == 1) ? "-0" + (-year) : "-" + (-year);
		}
	}
	//var year=(odate.getYear())>100?odate.getYear()-1911:odate.getYear()-11;
	var month = odate.getMonth() + 1;
	var day = odate.getDate();
	//year=((year+"").length==2)?"0"+year:year+"";
	month = ((month + "").length == 1) ? "0" + month : month + "";
	day = ((day + "").length == 1) ? "0" + day : day + "";
	return year + month + day;
}

//=============================================
//function ConvertW2CDate(cdate)
//desc:		將七碼民國日期轉為西元日期 0920101 --> 20030101
//input:	cdate  日期字串
//output:	str
//=============================================
function convertW2CDate(date) {
	var cdate = "";
	var year = "";
	if (date.charAt(0) == "-") {
		if (date.length == 7) {
			cdate = date;
			year = parseFloat(cdate.substring(0, 3)) + 1912;
		} else {
			return "";
		}
	} else {
		cdate = (date.value == "6") ? "0" + date : date;
		year = parseFloat(cdate.substring(0, 3)) + 1911;
	}
	var month = cdate.substring(3, 5);
	var day = cdate.substring(5, 7);
	var value = year + month + day;
	return value;
}

//=============================================
//function ConvertW2CDate(cdate)
//desc:		將八碼西元日期轉為民國日期 20030101 --> 0920101
//input:	cdate  日期字串
//output:	str
//=============================================
function convertC2WDate(date) {
	date = formatCDate(date);
	var odate = new Date(date);
	var year = "";
	if (parseFloat(date.substring(0, 4)) >= 2000) {
		year = parseFloat(odate.getYear()) - 1911;
		year = ((year + "").length == 2) ? "0" + year : year + "";
	} else {
		return year;
		if (parseFloat(date.substring(0, 4)) >= 1900) {
			if (parseFloat(date.substring(0, 4)) > 1911) {
				year = parseFloat(odate.getYear()) - 11;
			} else {
				year = parseFloat(odate.getYear()) - 12;
			}
		} else {
			year = parseFloat(odate.getYear()) - 1911;
		}
		if (year > 0) {
			year = ((year + "").length == 1) ? "00" + year : ((year + "").length == 2) ? "0" + year : year + "";
		} else {
			year = ((-year + "").length == 1) ? "-0" + (-year) : "-" + (-year);
		}
	}
	var month = odate.getMonth() + 1;
	var day = odate.getDate();
	month = ((month + "").length == 1) ? "0" + month : month + "";
	day = ((day + "").length == 1) ? "0" + day : day + "";
	return year + month + day;
}

//=============================================
//function cal_WDay(sday,eday)
//desc:		計算相差幾日，日前後都算，格式為民國日期0900340。
//input:	sday -起始日期
//        eday -結束日期
//output:	int   相差天數
//=============================================
function cal_WDay(sday, eday) {
	if (sday.charAt(0) != "-") {
		sday = (sday.length == 6) ? "0" + sday : sday;
	}
	if (eday.charAt(0) != "-") {
		eday = (eday.length == 6) ? "0" + eday : eday;
	}
	var csd = formatW2CDate(sday);
	var ced = formatW2CDate(eday);
	var sDay = new Date(csd);
	var eDay = new Date(ced);
	var mills = 24 * 60 * 60 * 1000;
	var millsDay = sDay.getTime() + 8 * 60 * 60 * 1000;//格林威治時間台灣加8:00
	var milleDay = eDay.getTime() + 8 * 60 * 60 * 1000;
	var dtol = parseInt((milleDay - millsDay) / mills);
	if (dtol <= 0) {
		dtol = dtol - 1;
	}
	return dtol + 1;
}

//=============================================
//function cal_CDay(sday,eday)
//desc:		計算相差幾日，日前後都算，格式為西元日期2004/03/17。
//input:	sday -起始日期
//        eday -結束日期
//output:	int   相差天數
//=============================================
function cal_CDay(sday, eday) {
	var sDay = new Date(sday);
	var eDay = new Date(eday);
	var mills = 24 * 60 * 60 * 1000;
	var millsDay = sDay.getTime() + 8 * 60 * 60 * 1000;//格林威治時間台灣加8:00
	var milleDay = eDay.getTime() + 8 * 60 * 60 * 1000;
	var dtol = parseInt((milleDay - millsDay) / mills);
	if (dtol <= 0) {
		dtol = dtol - 1;
	}
	return dtol + 1;
}

//=============================================
//function add_CDate(datestr,days)
//desc:		計算西元日期之天數加減。格式為西元日期 20030101。
//input:	datestr -原始日期
//          days    -天數
//output:	str      計算後日期
//=============================================
function add_CDate(datestr, days) {
	if (datestr.length != 8) {
		return datestr;
	}
	var yy = datestr.substring(0, 4);
	var mm = datestr.substring(4, 6);
	var dd = datestr.substring(6, 8);
	var newdate = new Date(yy, mm - 1, dd);
	newdate.setDate(newdate.getDate() + days);
	yy = newdate.getYear() + "";
	mm = newdate.getMonth() + 1 + "";
	if (mm.length == 1) {
		mm = "0" + mm.toString();
	}
	dd = newdate.getDate() + "";
	if (dd.length == 1) {
		dd = "0" + dd.toString();
	}
	return yy + mm + dd;
}

//=============================================
//function add_CMonth(datestr,month)
//desc:		計算西元日期之月份加減。格式為西元日期 20030101 。
//input:	datestr -原始日期
//          month   -增加月數
//output:	str      計算後日期
//=============================================
function add_CMonth(datestr, month) {
	if (datestr.length != 8) {
		return datestr;
	}
	var yy = datestr.substring(0, 4);
	var mm = datestr.substring(4, 6);
	var dd = datestr.substring(6, 8);
	var newdate = new Date(yy, mm - 1 + month, dd);
	yy = newdate.getYear() + "";
	mm = newdate.getMonth() + 1 + "";
	if (mm.length == 1) {
		mm = "0" + mm.toString();
	}
	dd = newdate.getDate() + "";
	if (dd.length == 1) {
		dd = "0" + dd.toString();
	}
	return yy + mm + dd;
}


//=============================================
//function add_CDate(datestr,days)
//desc:		計算民國日期之天數加減。格式為民國日期 0930101。
//input:	datestr -原始日期
//          days    -天數
//output:	str      計算後日期
//=============================================
function add_WDate(datestr, days) {
	return add_Date("d", datestr, days);
}
//=============================================
//function add_CMonth(datestr,month)
//desc:		計算民國日期之月份加減。格式為民國日期 09230101 。
//input:	datestr -原始日期
//          month   -增加月數
//output:	str      計算後日期
//=============================================
function add_WMonth(datestr, month) {
	return add_Date("m", datestr, month);
}

//=============================================
//function cal_WDay(sday,eday)
//desc:		日期加減，格式為民國日期0900340。
//input:	type -加減的單位，年或月或日  'y' or 'm' or 'd'
//          date -原始日期
//          num  -加減數量
//output:	str   計算後日期
//=============================================
function add_Date(type, date, num) {
	num = parseFloat(num);
	var cdate = "";
	if (date.charAt(0) == "-") {
		cdate = date;
	} else {
		cdate = (date.length == 6) ? "0" + date : date;
	}
	cdate = formatW2CDate(cdate);
	//alert(cdate);
	cdate = new Date(cdate);
	var year = getCyear(cdate);
	var month = cdate.getMonth();
	var day = cdate.getDate();
	//alert(year);
	switch (type) {
	  case "y":
		var rdate = new Date(year + num, month, day);
		break;
	  case "m":
		var rdate = new Date(year, month + num, day);
		for (var i = 1; i < 4; i++) {
			if (((month + num) % 12) == rdate.getMonth()) {
				break;
			} else {
				var nyear = getCyear(rdate);
				rdate = new Date(rdate.getYear(), rdate.getMonth(), rdate.getDate() - 1);
			}
		}
		break;
	  case "d":
		var rdate = new Date(year, month, day + num);
		break;
	}
	//alert(rdate);
	rdate = cDate(rdate);
	//alert(rdate);
	rdate = formatC2WDate(rdate);
	return rdate;
}
//將西元日期格式轉成2000/01/01
function cDate(date) {
	var year = getCyear(date);
	var month = date.getMonth() + 1;
	var day = date.getDate();
	return year + "/" + month + "/" + day;
}

//將西元日期格式，取中文年
function getCyear(date) {
	var year = "";
	if (date.getYear() >= 2000) {
		year = date.getYear();
	} else {
		if (date.getYear() < 100) {
			year = date.getYear() + 1900;
		} else {
			year = date.getYear();
		}
	}
	return year;
}
//=============================================
//function EndofMonth(yyymm)
//desc:		傳入中文年月回傳中文年月日(正確的月底日期)
//input:	yyymm  日期字串
//output:	str
//
//Exp:      09202 --> 0930228
//          09302 --> 0930229
//          09301 --> 0930131
//          09311 --> 0931130
//=============================================
function EndofMonth(yyymm) {
	if (yyymm.length < 4) {
		return false;
	}
	var year, month, day;
	if (yyymm.length == 4) {
		year = yyymm.substr(0, 2) * 1 + 1911;
		month = yyymm.substr(2, 2);
	} else {
		year = yyymm.substr(0, 3) * 1 + 1911;
		month = yyymm.substr(3, 2);
	}
	switch (month) {
	  case "01":
	  case "03":
	  case "05":
	  case "07":
	  case "08":
	  case "10":
	  case "12":
		day = 31;
		break;
	  case "04":
	  case "06":
	  case "09":
	  case "11":
		day = 30;
		break;
	  case "02":
		if (year % 4 != 0) {
			day = 28;
		} else {
			day = 29;
		}
		break;
	  default:
	}
	return yyymm + day;
}

