//------------------------------------------------------------------
//���������function
//------------------------------------------------------------------


//=============================================
//function formatWDate(date)
//desc:		�N�������榡��  0900627�榡�Ʀ�090/06/27
//input:	value  ����r��
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
//desc:		�N�������榡��  090/06/27�榡�Ʀ�0900627
//input:	value  ����r��
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
//desc:		�N�褸����榡��  20030101�榡�Ʀ�2003/01/01
//input:	value  ����r��
//output:	str
//=============================================
//�N�褸����榡�ন2000/01/01
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
//desc:		�N�������榡�Ƭ��褸��� 0920101 --> 2003/01/01
//input:	date  ����r��
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
//desc:		�N�褸����榡�Ƭ������� 2003/01/01 --> 0920101
//input:	date  ����r��
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
//desc:		�N�C�X�������ର�褸��� 0920101 --> 20030101
//input:	cdate  ����r��
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
//desc:		�N�K�X�褸����ର������ 20030101 --> 0920101
//input:	cdate  ����r��
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
//desc:		�p��ۮt�X��A��e�᳣��A�榡��������0900340�C
//input:	sday -�_�l���
//        eday -�������
//output:	int   �ۮt�Ѽ�
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
	var millsDay = sDay.getTime() + 8 * 60 * 60 * 1000;//��L�ªv�ɶ��x�W�[8:00
	var milleDay = eDay.getTime() + 8 * 60 * 60 * 1000;
	var dtol = parseInt((milleDay - millsDay) / mills);
	if (dtol <= 0) {
		dtol = dtol - 1;
	}
	return dtol + 1;
}

//=============================================
//function cal_CDay(sday,eday)
//desc:		�p��ۮt�X��A��e�᳣��A�榡���褸���2004/03/17�C
//input:	sday -�_�l���
//        eday -�������
//output:	int   �ۮt�Ѽ�
//=============================================
function cal_CDay(sday, eday) {
	var sDay = new Date(sday);
	var eDay = new Date(eday);
	var mills = 24 * 60 * 60 * 1000;
	var millsDay = sDay.getTime() + 8 * 60 * 60 * 1000;//��L�ªv�ɶ��x�W�[8:00
	var milleDay = eDay.getTime() + 8 * 60 * 60 * 1000;
	var dtol = parseInt((milleDay - millsDay) / mills);
	if (dtol <= 0) {
		dtol = dtol - 1;
	}
	return dtol + 1;
}

//=============================================
//function add_CDate(datestr,days)
//desc:		�p��褸������Ѽƥ[��C�榡���褸��� 20030101�C
//input:	datestr -��l���
//          days    -�Ѽ�
//output:	str      �p�����
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
//desc:		�p��褸���������[��C�榡���褸��� 20030101 �C
//input:	datestr -��l���
//          month   -�W�[���
//output:	str      �p�����
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
//desc:		�p����������Ѽƥ[��C�榡�������� 0930101�C
//input:	datestr -��l���
//          days    -�Ѽ�
//output:	str      �p�����
//=============================================
function add_WDate(datestr, days) {
	return add_Date("d", datestr, days);
}
//=============================================
//function add_CMonth(datestr,month)
//desc:		�p�������������[��C�榡�������� 09230101 �C
//input:	datestr -��l���
//          month   -�W�[���
//output:	str      �p�����
//=============================================
function add_WMonth(datestr, month) {
	return add_Date("m", datestr, month);
}

//=============================================
//function cal_WDay(sday,eday)
//desc:		����[��A�榡��������0900340�C
//input:	type -�[����A�~�Τ�Τ�  'y' or 'm' or 'd'
//          date -��l���
//          num  -�[��ƶq
//output:	str   �p�����
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
//�N�褸����榡�ন2000/01/01
function cDate(date) {
	var year = getCyear(date);
	var month = date.getMonth() + 1;
	var day = date.getDate();
	return year + "/" + month + "/" + day;
}

//�N�褸����榡�A������~
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
//desc:		�ǤJ����~��^�Ǥ���~���(���T���멳���)
//input:	yyymm  ����r��
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

