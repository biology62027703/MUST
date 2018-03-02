<%@page language="java" contentType="text/html;charset=MS950"%>
<script>

var __COURTLIST = <%=new com.google.gson.Gson().toJson(com.sertek.sys.StaticObj.getCourtList())%>;
var __OCRTID = null;
var __CRTID = null;
var __SYSKD = "";

function setCrtidSelect(syskd, $ocrtid, $crtid, alltxt) {
		
	__SYSKD = syskd;
	__OCRTID = $ocrtid;
	__CRTID = $crtid;
	
	__OCRTID.html("");
	if( typeof(alltxt)=="string" ) {
		if (alltxt=="請選擇法院") {
			__OCRTID.append("<option value='no' style='color:red'>"+alltxt+"</option>");
		} else {
			__OCRTID.append("<option value=''>"+alltxt+"</option>");
		}
		
		
	}
	for(var i=0;i<__COURTLIST.length;i++) {
		if( __COURTLIST[i].SYSKD.indexOf(syskd)>-1 ) {
			__OCRTID.append("<option value='"+__COURTLIST[i].CRTID+"'>"+__COURTLIST[i].CRTNM+"</option>");
		}
	}
		
	__OCRTID.unbind("change");
	__OCRTID.change(function(){
		
		__CRTID.html("");
		for(var i=0;i<__COURTLIST.length;i++) {
			if( __COURTLIST[i].CRTID==this.value ) 
				__CRTID.append("<option value='"+__COURTLIST[i].CRTID+"'>"+__COURTLIST[i].CRTNM+"</option>");
			else if( __SYSKD=="V" && __COURTLIST[i].V2CRTID==this.value && this.value!="" && this.value.substr(2)=="D")
				__CRTID.append("<option value='"+__COURTLIST[i].CRTID+"'>"+__COURTLIST[i].CRTNM+"</option>");
		}
		if($("option", __CRTID).length<=1 )
			__CRTID.hide();
		else
			__CRTID.show();
		
		if(this.value=="no"){
			__OCRTID.css("color","red");
			$("option",this).css("color","#000");
		} else {
			__OCRTID.css("color","#000");
		}
		
	}).change();
}

function setCrtidValue(crtid) {

	if( crtid=="") {
		$("option:eq(0)", __OCRTID).attr("selected", "selected");
		__OCRTID.change();
	}else{
		if( __SYSKD != "V" ) {
			__OCRTID.val(crtid);
			__OCRTID.change();
		}else{
			for(var i=0;i<__COURTLIST.length;i++) {
				if( __COURTLIST[i].CRTID==crtid ) {
					if( __COURTLIST[i].CRTKD=="E" ) {
						__OCRTID.val(__COURTLIST[i].V2CRTID);
						__OCRTID.change();
						__CRTID.val(crtid);
					}else{
						__OCRTID.val(crtid);
						__OCRTID.change();
						__CRTID.val(crtid);
					}
					break;
				}
			}
		}
	}
}
	
</script>