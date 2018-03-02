<%@page language="java" contentType="text/html;charset=MS950"%>
<%@page import="com.sertek.sys.sys_User"%>
<%
String sPriv = "";
String sOwner = "";
String sScdnm = "";
String sUsrnm = "";
String sUsrid = "";
String sViptype = "";
{
	com.sertek.sys.sys_User nowUser = (com.sertek.sys.sys_User)request.getSession().getAttribute("User");
	if(nowUser != null){
		sPriv = nowUser.readString("S_PRIV");
		sOwner = nowUser.readString("S_OWNER");
		sScdnm = nowUser.readString("S_SCDNM");
		sUsrnm = nowUser.readString("S_USRNM");
		sUsrid = nowUser.readString("S_USRID");
		sViptype = nowUser.readString("S_VIP_TYPE");
	}
}
boolean isGuest = sUsrid.equals("__GUEST__");
System.out.println("sUsrid:"+sUsrid  +"isGuest:"+isGuest);
%>
<script language="javascript" type="text/javascript">
<!--

	function doLogin()
	{
		form.action = "<%=request.getContextPath()%>/INDEX.jsp";
		form.target = "mainFrame";
		form.submit();
	}
	
	function doHome()
	{
		top.showProcessBar(); 
		
		<%if( sViptype.equals("L") ){%>
			form.action = "<%=request.getContextPath()%>/sof/FSOMain.do?action=viewForm&init=Y&check_viptype=Y";
		<%}else{%>		
			if("T" == "<%=sPriv %>"){
				form.action = "<%=request.getContextPath()%>/sos/SSOMain.do?action=viewForm";
			} else {
				form.action = "<%=request.getContextPath()%>/sof/FSOMain.do?action=viewForm&init=Y&check_viptype=Y";
			}
		<%}%>
		form.target = "mainFrame";
		form.submit();
	}
	
	function doSOLAccount()
	{
		top.showProcessBar();
		
		form.action = "<%=request.getContextPath()%>/sof/FSO3A.do?action=viewForm";
		form.target = "mainFrame";
		form.submit();
	}
	
	function doBOSAccount()
	{
		top.showProcessBar();
		
		form.action = "<%=request.getContextPath()%>/sof/FSO3B.do?action=viewForm";
		form.target = "mainFrame";
		form.submit();
	}
	
	function doLogout()
	{
		//if(confirm("登出前請先儲存編輯中資料\r\n是否確定登出?")){
		var saveObj = document.getElementById("save_prgid");
		//alert("saveObj = " + saveObj);
		
		if(saveObj == null){
			doLogout2();
		} else {
			if("FSO1A02" == saveObj.value || "FSO1A03" == saveObj.value){
				doSaveBeforeLogout();	
			} else {
				doLogout2();	
			}
		}
	}
	
	function doLogout2()
	{
		top.showProcessBar();
			
		form.action = "<%=request.getContextPath()%>/Logout.do";
		form.target = "_self";
		form.submit();
	}
	
//-->
</script>  

<table width="100%" height="60px" bgcolor="#00BDCB">
<tr>
	<td align="center">
	
	<table width="960">
	<tr>
		<td width="40">
			&nbsp;
		</td>
		<td width="80">
			<img src="<%=request.getContextPath()%>/image/Judicial_Yuan.png" height="60">
		</td>
		<td align="left">
			<b><font size="5" color="#FFFFFF">司法院<br>線上起訴及書狀傳送作業平台</font></b>
		</td>
		<td align="right">
		<%if(request.getSession().getAttribute("User") == null){ %>
			<img src="<%=request.getContextPath()%>/image/home.gif" onmouseover="this.style.cursor='pointer'" title="回首頁" alt="回首頁" onclick="doLogin();">
		<%}else if(!isGuest){ %>
			<img src="<%=request.getContextPath()%>/image/home.gif" onmouseover="this.style.cursor='pointer'" title="回首頁" alt="回首頁" onclick="doHome();">
			<%if(!"BOS".equals(sOwner)){ %>
			<img src="<%=request.getContextPath()%>/image/account.gif" onmouseover="this.style.cursor='pointer'" title="個人資料維護" alt="個人資料維護" onclick="doSOLAccount();">
			<%}else{ %>
			<img src="<%=request.getContextPath()%>/image/account.gif" onmouseover="this.style.cursor='pointer'" title="個人資料維護" alt="個人資料維護" onclick="doBOSAccount();">
			<%} %>
			<img src="<%=request.getContextPath()%>/image/logout.gif" onmouseover="this.style.cursor='pointer'" title="登出" alt="登出" onclick="doLogout();">
			<br>
			<%=sScdnm + "  " + sUsrid + "  " + sUsrnm + "  " + (sViptype.equals("C")?"君":com.sertek.sys.StaticObj.getPhrase("VIP_TYPE", sViptype)) + "&nbsp;&nbsp;"%>
		<%} %>
		</td>
		<td width="40">
			&nbsp;
		</td>
	</tr>
	</table>

	</td>
</tr>
</table>