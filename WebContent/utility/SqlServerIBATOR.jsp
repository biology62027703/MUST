<%@ page language="java" import="java.util.*,com.sertek.db.*,com.sertek.table.*,com.sertek.util.*,com.sertek.sys.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%Logger logger = Logger.getLogger(this.getClass()); %>
<%@page import="com.sertek.ibator.*"%>
<%
DBUtility db = new DBUtility();
db.openConnection();
try{
String owner = "SO.";
if(request.getParameter("owner") != null){
	owner = request.getParameter("owner");
}
SqlServerIbator ibator = new SqlServerIbator(db, owner);
List tableList = ibator.getTables();

String[] tableNames = request.getParameterValues("tableNames");
if(tableNames != null){
	for(int i = 0; i < tableNames.length; i++){
		ibator.doGenerateTable(tableNames[i]);
	}
	out.println("<script language='javascript'>");
	out.println("alert('產生成功');");
	out.println("</script>");
}
%>
<html>

<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<style type="text/css">
<!--
@import "../css/CSS.css";
-->
</style>
</head>

<script language="javascript" src="../script/AUTOCOLOR.js"></script>
<script language="javascript" src="../script/SETVALUE.js"></script>
<script>

	function iniForm()
	{
		autochange();
	}
	
</script>

<body text="#000000" onload="iniForm()">
<form name="result" method="post">
<!--//	設定現在的情況-->

<table width="95%" border="0" id="content">
	<tr> 
		<td colspan="2">
			OWNER：
			<select name="owner" onchange="result.submit();" class="select">
				<option value="SO." <%="SO.".equals(owner)?"selected":"" %>>SO</option>
				<option value="BOS." <%="BOS.".equals(owner)?"selected":"" %>>BOS</option>
			</select>
		</td>
	</tr>
	<tr> 
		<td colspan="2">
			<table border="0" width="100%" cellspacing="0"  cellpadding="1">
				<tr class="title" height="31"> 
					<td align="left">TABLE LIST</td>
					<td align="right">
						<input type="button" name="btn_selectAll" value="全選" onclick="checkAllCheckBox('true')" class="button">
						<input type="button" name="btn_cancelAll" value="全不選" onclick="checkAllCheckBox('false')" class="button">
						<input type="submit" name="btn_submit" value="確定" class="button">
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr class="head" height="31"> 
		<td width="10%">勾選</td>
		<td width="*">TABLE_NAME</td>
	</tr>
<%
    for(int i=0; i<tableList.size();i++)
	{
		String tableName = tableList.get(i).toString();
%>
	<tr class="tr-<%=i%2+1%>" height="30"> 
		<td align="left"><input type="checkbox" name="tableNames" value="<%=tableName%>" class=""></td>
		<td align="left"><%=tableName%></td>
	</tr>
<%
    }
%>
</table>

</form>
</body>
</html>
<%
} catch(Exception e) {
	out.println(e);
	logger.error(e, e);
} finally {
	if(null != db && !db.isClosed()) {
	  try { db.closeConnection(); db=null; } catch(Exception e){}
	}
}
%>