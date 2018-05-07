
function StrUtil() {
}

StrUtil.prototype.getFormatStr = function(text, dataFormat) {
	dataFormat = dataFormat.toLowerCase();
	if( dataFormat=="DT" || dataFormat=="dt" ) {
		text = strUtil.formatDate(text);
	}else if( dataFormat=="TM" || dataFormat=="tm" ) {
		text = strUtil.formatTime(text);
	}else if( dataFormat=="UPPERCASE" || dataFormat=="uppercase" ) {
		text = text.toUpperCase();
	}else if( dataFormat=="LOWERCASE" || dataFormat=="lowercase" ) {
		text = text.toLowerCase();
	}else if( dataFormat=="DTTM" || dataFormat=="dttm" ) {
		if( text.length==7 || text.length==8 ) {
			text = strUtil.formatDate(text);
		}else if( text.length==11 ) {
			text = text.substring(0, 3)+"/"+text.substring(3, 5)+"/"+text.substring(5, 7) + " " + text.substring(7, 9)+":"+text.substring(9, 11);
		}else if( text.length==12 ) {
			text = text.substring(0, 4)+"/"+text.substring(4, 6)+"/"+text.substring(6, 8) + " " + text.substring(8, 10)+":"+text.substring(10, 12);
		}else if( text.length==13 ) {
			text = text.substring(0, 3)+"/"+text.substring(3, 5)+"/"+text.substring(5, 7) + " " + text.substring(7, 9)+":"+text.substring(9, 11)+":"+text.substring(11, 13);
		}else if( text.length==14 ) {
			text = text.substring(0, 4)+"/"+text.substring(4, 6)+"/"+text.substring(6, 8) + " " + text.substring(8, 10)+":"+text.substring(10, 12)+":"+text.substring(12, 14);
		}
	}else if( dataFormat=="FILESIZE" || dataFormat=="filesize" ) {
		text = strUtil.formatFileSize(text);
	}else if( dataFormat=="MONEY" ||  dataFormat=="money" ) {
		text = strUtil.formatMoney(text);
	}
	return text;
}

StrUtil.prototype.formatDate=function(date, keep)
{
	if( date.length==8 )
		return date.substr(0,4)+"/"+date.substr(4,2)+"/"+date.substr(6,2);
	else if( date.length==7 )
		return date.substr(0,3)+"/"+date.substr(3,2)+"/"+date.substr(5,2);
	else if(keep!=null && keep)
		return date;
	else
		return "";
}

StrUtil.prototype.formatTime=function(time)
{	
	if( time.length==6 )
		return time.substr(0,2)+":"+time.substr(2,2)+":"+time.substr(4,2);
	else if( time.length==4 )
		return time.substr(0,2)+":"+time.substr(2,2);
	else
		return "";
}

StrUtil.prototype.formatFileSize=function(size)
{	
	try {		
		if( size != "" ) {
			unit = "B";
			size = size*1;
			if( size>1024 ) {
				unit = "KB";
				size = size/1024;
				if( size>1024 ) {
					unit = "MB";
					size = size/1024;
				}
			}
			size = Math.round(size*10)/10;
			size = size + unit;
		}
	} catch(e) {}
	return size;
}

StrUtil.prototype.formatMoney=function(money)
{	
	if(money=="" || money=="NaN") 
		money="0";
	money = parseFloat((money + "").replace(/[^\d\.-]/g, "")).toFixed(0) + "";  
	var l = money.split(".")[0].money("").reverse(), t = "";  
	for(k = 0; k < l.length; k ++ )
		t += l[k] + ((k + 1) % 3 == 0 && (k + 1) != l.length ? "," : "");
	money = t.split("").reverse().join(""); 
	return money;
}

StrUtil.prototype.getMappingData = function(datamapping, map, key) {
	text = ""; 
	if( typeof(map)=="string" ) {
		if( typeof(datamapping[map][key])=="string" )
			text = datamapping[map][key];
		else if( typeof(datamapping[map]["_other"])=="string" )
			text = datamapping[map]["_other"];
	}
	return text;
}

StrUtil.prototype.tranPattern = function(pattern, para, datamapping) {
	while( getKey(pattern) != "" ){
		var Key = getKey(pattern);
		if( Key.indexOf(":")==-1 && Key.indexOf("-")==-1 )
			pattern = pattern.replace("@{"+Key+"}", para[Key]);
		else if( Key.indexOf(":")>-1 ){
			format = Key.split(":")[1];
			Key = Key.split(":")[0];
			pattern = pattern.replace("@{"+Key+":"+format+"}", strUtil.getFormatStr(para[Key], format));
		}else if( Key.indexOf("-")>-1 ){
			if( typeof(datamapping)!="object") {
				map = Key.split("-")[1];
				Key = Key.split("-")[0];
				pattern = pattern.replace("@{"+Key+"-"+map+"}", para[Key]);
			}else{
				map = Key.split("-")[1];
				Key = Key.split("-")[0];
				pattern = pattern.replace("@{"+Key+"-"+map+"}", strUtil.getMappingData(datamapping, map, para[Key]));
			}
		}
	}
		
	function getKey(pattern){
		var k = "";
		if( pattern.indexOf("@{") > -1 ) {
			k = pattern;
			k = k.substr(k.indexOf("@{")+2);
			if( k.indexOf("}") > -1)
				k = k.substr(0, k.indexOf("}"))
			else 
				k = "";
		}
		return k;
	}
	
	return pattern;	
}

//globe object
var strUtil = new StrUtil();