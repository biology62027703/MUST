<!doctype html>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/CFC.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/UiFrameset.js"></script>
<%
	if( session.getAttribute("User")==null )
		response.sendRedirect(request.getContextPath() + "/INDEX.jsp");
%>
<script>

var uiFrameset = null;
var showmessage = parent.showmessage;
var hidemessage = parent.hidemessage;
var strUtil = parent.strUtil;
var dateUtil = parent.dateUtil;

$(document).ready(function(){

	document.body.style.overflowX = 'hidden';
	document.body.style.overflowY = "hidden";
	
	$("body")
		.html("")
		.css("scroll", "no")
		.append("<div id='fdcFrameSetPanel' style='height:100%'></div>");
	
	uiFrameset = new UiFrameset($("div[id=fdcFrameSetPanel]"));
});


</script>