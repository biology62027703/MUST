var levelZero = 0;
var levelLow = 1;
var levelMed = 2;
var levelHi = 3
function checkPassword(pwd, minLength){
	if(minLength == null){
		minLength = 6;
	}
	var objLow = document.getElementById("pwdLow");
	var objMed = document.getElementById("pwdMed");
	var objHi = document.getElementById("pwdHi");
	objLow.className = "pwd-strength-box";
	objMed.className = "pwd-strength-box";
	objHi.className = "pwd-strength-box";
	var level = 0;
	if(pwd.length < minLength){
		objLow.className = "pwd-strength-box-low";
	}else{
		var p1 = (pwd.search(/[a-zA-Z]/)!=-1) ? 1 : 0;
		var p2 = (pwd.search(/[0-9]/)!=-1) ? 1 : 0;
		var p3 = (pwd.search(/[^A-Za-z0-9_]/)!=-1) ? 1 : 0;
		level = p1 + p2 + p3;
		if(level == 1){
			objLow.className = "pwd-strength-box-low";
		}else if(level == 2){
			objMed.className = "pwd-strength-box-med";
		}else if(level == 3){
			objHi.className = "pwd-strength-box-hi";
		}
	}
	return level;
}