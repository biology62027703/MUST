function lengthCheck()
{
	var inputObj = document.getElementsByTagName('input');
	for(var i = 0; i < inputObj.length; i++){
		if(inputObj[i].type == "text" && !inputObj[i].disabled){
			var result = checkLength(inputObj[i]);
			if (!result){
				return false;
			}
		}
	}
	
	return true;
}

function chkDate(obj){
	var text = obj.value;
	if( text=="" ) 
		return true;
	if( text.length==5 )
		text = text + "01";
	if( text.length==7 )
		text = text*1+19110000+""; 
	text = text.substring(0,4)+"/"+	text.substring(4,6)+"/"+text.substring(6,8);
	text = text.replace(/[\/-]0?/g, "/");
	if (!text.match(/^\d{4}\/\d{1,2}\/\d{1,2}$/)) return false;
	var d = new Date(text);
	return [d.getFullYear(), d.getMonth() + 1, d.getDate()].join("/") == text;
}

//�ˬdtext���r�����סAmaxLength��L�n�j�g�A��]�����D
function checkLength(object)
{
	var s,i=0,char_count=0,result=true;
	object_width=object.maxLength;
	//alert(object_width);
        object_text=object.value; 
	
	while (i++<object_text.length)
	{
		s = object_text.charCodeAt(i-1);
		//alert(s);
		if (s<127)
			char_count++;
		else
			char_count+=2;
                /*
		if (char_count%object_width==0 && char_count!=0)
		{	
			result=false;
			object.value=object_text.substring(0,i);
			break;
		}*/
		//alert(char_count);
		//if (char_count%(object_width+1)==0 && char_count!=0)
		if (char_count>object_width)
		{	
			result=false;
			//object.value=object_text.substring(0,i-1);
			break;
		}
	}
	if (result==false)
	{
		alert("��J�r����׹L���I");
	    //display('701','');
	    object.focus();
	    return false;
	}   
        return true;	
}

//**************************************
//�x�W�������ˬd²�u�� for Javascript
//**************************************
function checkTwID(id){
 //�إߦr�����ư}�C(A~Z)
 var city = new Array(
      1,10,19,28,37,46,55,64,39,73,82, 2,11,
     20,48,29,38,47,56,65,74,83,21, 3,12,30
 )
 id = id.toUpperCase();
 // �ϥΡu���W��F���v����榡
 if (id.search(/^[A-Z](1|2)\d{8}$/i) == -1) {
     //alert('�򥻮榡���~');
     return false;
 } else {
     //�N�r����ά��}�C(IE���ݳo�򰵤~���|�X��)
     id = id.split('');
     //�p���`��
     var total = city[id[0].charCodeAt(0)-65];
     for(var i=1; i<=8; i++){
         total += eval(id[i]) * (9 - i);
     }
     //�ɤW�ˬd�X(�̫�@�X)
     total += eval(id[9]);
     //�ˬd���X(�l������0);
     return ((total%10 == 0 ));
 }
}

//***************************
//�x�W�����Ҳ���²�u�� javascript ��
//***************************
function getTwID(){
//�إߦr�����ư}�C(A~Z)
var city = new Array(
   1,10,19,28,37,46,55,64,39,73,82, 2,11,
  20,48,29,38,47,56,65,74,83,21, 3,12,30
)
//�إ��H�������ҽX
var id = new Array();
id[0] = String.fromCharCode(Math.floor(Math.random() * (26)) + 65);
id[1] = Math.floor(Math.random() * (2)) + 1;
for(var i=2; i<9; i++){
  id[i] = Math.floor(Math.random() * (9)) + 0;
}
//�p���`��
var total = city[id[0].charCodeAt(0)-65];
for(var i=1; i<=8; i++){
  total += eval(id[i]) * (9 - i);
}
//�p��̧��X
var total_arr = (total+'').split('');
var lastChar = eval(10-total_arr[total_arr.length-1]);
var lastChar_arr = (lastChar+'').split('');
//�ɤW�̫��ˬd�X
id[id.length++] = lastChar_arr[lastChar_arr.length-1];
//�^�ǵ��G
return id.join('');
}

function checkEmail(email) { 
 var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
 return re.test(email);
}

function chkEmail(obj){
    if((obj.value.indexOf("@")==-1|| obj.value.indexOf(".")==-1)){
	   return false;
	}
	return true;
}

function lockNum()
{
	if (event.keyCode==13) return;
	if ((event.keyCode < 48) || (event.keyCode > 58)) 
	{
		if (event.keyCode!=45)
			event.keyCode = 0;
	}
}

function addzero(obj,num){
	if (obj.value=="") return;
	if (isNaN(obj.value)) return;
	var value=obj.value;
	if (obj.value.length<num){
		while(num!=value.length){
			value="0"+value;
		}
		obj.value=value;
	}
}

function chkCompanyNo(idvalue) {
	   var tmp = new String("12121241");
	   var sum = 0;
	   re = /^\d{8}$/;
	   if (!re.test(idvalue)) {
	       //alert("�榡����I");
	       return false;
	    }
	   for (i=0; i< 8; i++) {
	     s1 = parseInt(idvalue.substr(i,1));
	     s2 = parseInt(tmp.substr(i,1));
	     sum += cal(s1*s2);
	   }
	   if (!valid(sum)) {
	      if (idvalue.substr(6,1)=="7") return(valid(sum+1));
	   }  
	   return(valid(sum));
	}	 

	function valid(n) {
	   return (n%10 == 0)?true:false;
	}	 

	function cal(n) {
	   var sum=0;
	   while (n!=0) {
	      sum += (n % 10);
	      n = (n - n%10) / 10;  // �����
	     }
	   return sum;
	}