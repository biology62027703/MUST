var iss = {
	color:["#CC0000","#FFCC33","#66CC00","#CCCCCC"],	
	text:["弱","中","強"],
	width:["70","140","210","10"],	
	reset:function(){		
		$("#PSWDCONTENT").width(iss.width[3]);		
		$("#PSWDCONTENT").css('background-color',iss.color[3]);
		document.getElementById("pswdstrong").innerHTML = "很弱";	
	},	
 	level0:function(){		
		$("#PSWDCONTENT").width(iss.width[0]);	
		$("#PSWDCONTENT").css('background-color',iss.color[0]);	
		document.getElementById("pswdstrong").innerHTML = "弱"; 	
	},
 	level1:function(){	
	 	$("#PSWDCONTENT").width(iss.width[1]);	
		$("#PSWDCONTENT").css('background-color',iss.color[1]);		
	 	document.getElementById("pswdstrong").innerHTML = "中"; 	
 	},
	level2:function(){		
		$("#PSWDCONTENT").width(iss.width[2]);	
		$("#PSWDCONTENT").css('background-color',iss.color[2]);
		document.getElementById("pswdstrong").innerHTML = "強";	
 	}	
}

function isSecurity(v){		
	if (v.length < 3) { 
		iss.reset();
		return;
	}
	var lv = -1;	
	if (v.match(/[a-z]/)){
		lv++;
	}
	if (v.match(/[A-Z]/)){
		lv++;	
	}
	if (v.match(/[0-9]/ig)){
		lv++;
	}
	if (v.match(/[^a-zA-Z0-9]/)){
		lv++;	
	}
	if (v.length < 8 && lv > 0){
		lv--;
	}
	iss.reset();

	switch (lv) { 		
		case 0:	
			iss.level0();
		break;
		case 1:	
			iss.level1();	
		break;			
	 	case 2:	
	 		iss.level2();	
	 	break;	
		case 3:	
			iss.level2();	
		break;	 
	 	default:	
	 		iss.reset();		
	}	
}
