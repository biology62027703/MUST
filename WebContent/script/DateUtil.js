function initBirdtSelectOption(yyObj, mmObj, ddObj, yy, mm, dd)
{	
	var today=new Date();
	var year = today.getFullYear();
	for(var i = year; i > year - 120; i--){
		var option = document.createElement("option");
		
		var _y = "" + i;
		option.value = _y;
		option.text = _y;
		if(yy == _y){
			option.selected = true;
		}
		yyObj.add(option);
	}
	
	for(var i = 1; i <= 12; i++){
		var option = document.createElement("option");
		
		var _m = "" + i;
		if(_m.length == 1){
			_m = "0" + _m;
		}
		option.value = _m;
		option.text = _m;
		if(mm == _m){
			option.selected = true;
		}
		mmObj.add(option);
	}
	
	for(var i = 1; i <= 31; i++){
		var option = document.createElement("option");
		
		var _d = "" + i;
		if(_d.length == 1){
			_d = "0" + _d;
		}
		
		option.value = _d;
		option.text = _d;
		if(dd == _d){
			option.selected = true;
		}
		ddObj.add(option);
	}
}