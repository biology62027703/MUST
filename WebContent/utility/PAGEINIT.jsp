<!DOCTYPE html>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="com.sertek.sys.sys_User"%>
<%@page import="com.sertek.sys.StaticObj"%>
<%@page import="com.sertek.sys.sys_Helper"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="org.apache.log4j.Logger"%>
<%Logger logger = Logger.getLogger(this.getClass()); %>
<%
	/* com.sertek.sys.sys_User User = (com.sertek.sys.sys_User)request.getSession().getAttribute("User"); */
	com.sertek.sys.sys_Helper Helper = (com.sertek.sys.sys_Helper)request.getSession().getAttribute("Helper");

	com.sertek.util.util_date ud = new com.sertek.util.util_date();
	com.sertek.util.CheckObject check = new com.sertek.util.CheckObject();
	String springAction = ( request.getParameter("action")==null ? "" : request.getParameter("action").toString() );
	
	String systemName = "MUST官網";
%>

<script language="javascript" src="<%=request.getContextPath()%>/script/CheckUtil.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/DateUtil.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/ERROR.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/WINDOW.js"></script>

<script>
	
	function loadmsg()
	{
		var msg = "<%=(String)check.checkNull(request.getAttribute("errors"), "")%>";
		if(msg != "")
			showInfoBar("INFO", msg);
	}
	
</script>

<%!
	public String getHtmlStr(String str){
		if("".equals(str)){
			return "&nbsp;";
		}else{
			return str;
		}
	}
	
	public String getSysnm(String syskd){
		return StaticObj.getPhrase("SYSKD", syskd);
	}
	
	public String replaceHtmlStr(String str){
		try{
			com.sertek.util.utility ut = new com.sertek.util.utility();
			str = ut.StrTran(str, " ", "&nbsp;&nbsp;");
			str = ut.StrTran(str, "\n", "<br>");
		}catch(Exception e){
		}
		return getHtmlStr(str);
	}
	
	private String formatFileSize(String bytes) {
		BigDecimal KB = new BigDecimal(1024);
		BigDecimal MB = new BigDecimal(1024 * 1024);
		int SCALE = new Integer(2).intValue();
		
		try {
			BigDecimal result = new BigDecimal(bytes).divide(KB, SCALE, BigDecimal.ROUND_HALF_UP);
			if (result.intValue() < 1000) {
				return result.toString() + "KB";
			} else {
				result = new BigDecimal(bytes).divide(MB, SCALE, BigDecimal.ROUND_HALF_UP);
				return result.toString() + "MB";
			}
		} catch (Exception e) {
			return bytes;
		}
	}
%>