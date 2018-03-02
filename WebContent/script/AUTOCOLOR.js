function autochange(){
	
   var trobj=document.all.tags("tr");
   
   for (var i=0;i<trobj.length;i++)
   {
	  if (trobj[i].className=='tr-1' || trobj[i].className=='tr-2' || trobj[i].className=='tr-3' || trobj[i].className=='tr-11' || trobj[i].className=='tr-22')
		 trobj[i].onmouseover=trover;

	  if (trobj[i].className=='tr-1' || trobj[i].className=='tr-11')
		 trobj[i].onmouseout=trout1;

	  if (trobj[i].className=='tr-2' || trobj[i].className=='tr-22')
		 trobj[i].onmouseout=trout2;

	  if (trobj[i].className=='tr-3')
		 trobj[i].onmouseout=trout3;

	  if (trobj[i].className=='tr-1b' || trobj[i].className=='tr-2b')
		 trobj[i].onmouseover=troverb;

	  if (trobj[i].className=='tr-1b')
		 trobj[i].onmouseout=trout1b;

	  if (trobj[i].className=='tr-2b')
		 trobj[i].onmouseout=trout2b;

	  if (trobj[i].className=='tr-1CHD' || trobj[i].className=='tr-2CHD')
		 trobj[i].onmouseover=troverCHD;

	  if (trobj[i].className=='tr-1CHD')
		 trobj[i].onmouseout=trout1CHD;

	  if (trobj[i].className=='tr-2CHD')
		 trobj[i].onmouseout=trout2CHD;
   }
   
   
   var inputobj=document.all.tags("input");
   for (var i=0;i<inputobj.length;i++)
   {
	   if(inputobj[i].type=="text" && inputobj[i].readOnly)
	   {
		   inputobj[i].className="readonly";
	   }
	   else
	   {
		   inputobj[i].className="";
	   }
   }
}

function trover(){
   this.className='trcolor';
}

function troverb(){
   this.className='trcolorb';
}

function troverCHD(){
   this.className='trcolorCHD';
}

function trout1(){
   this.className='tr-1';
}

function trout2(){
   this.className='tr-2';
}

function trout3(){
   this.className='tr-3';
}

function trout1b(){
   this.className='tr-1b';
}

function trout2b(){
   this.className='tr-2b';
}

function trout1CHD(){
   this.className='tr-1CHD';
}

function trout2CHD(){
   this.className='tr-2CHD';
}