<%@page	language="java"	contentType="text/html;charset=UTF-8"%>
<script>

function closewin()
{
	window.opener=null;   
	window.open("","_self");   
	window.close(); 
	top.close;		
}

function showmessage(str, t) {
	if( typeof(t)=="undefined" )
		t = 3000;
	$("div.Message").text(str);	
	$("div.Message").css("top", $("table[id=tablehead]")[0].offsetHeight-15).css("left", "20px"); 
	$("div.Message").show();	
	setTimeout(function(){$("div.Message").hide()}, t);
}

function hidemessage() {
	$("div.Message").hide();
}

function getTableHeadHeight() {
	return $("table[id=tablehead]")[0].offsetHeight;
}

function doExit() {
	location.href = "Logout.do";
}

</script>
<table width="100%" bgcolor="#00BDCB" id="tablehead">
	<tr> 
		<td width="40">
			&nbsp; 
		</td>
		<td width="80">
			<img src="<%=request.getContextPath()%>/image/Judicial_Yuan.png" style="margin:6px" height="60px">
		</td>
		<td>
			<b><font size="5" color="#FFFFFF" id="title">整合性線上卷證閱覽系統</font></b>
		</td>
		<td align="right" width="*" style="color:#FFFFFF">
		<%if( session.getAttribute("User")!=null ) {
			com.sertek.sys.sys_User headUser = (com.sertek.sys.sys_User) session.getAttribute("User");
		%>
			<img src="<%=request.getContextPath()%>/image/account.gif" style="cursor:pointer" title="個人資料維護" alt="個人資料維護" onclick="showWLS01();">		
			<img src="<%=request.getContextPath()%>/image/logout.gif" style="cursor:pointer" title="離開" alt="離開" onclick="doExit()">
			<br>
			
		<%} %>
		</td>
		<td width="40">
			&nbsp;
		</td>
	</tr>
</table>

<div class="Message" style="display:none;z-index:9999;"></div>