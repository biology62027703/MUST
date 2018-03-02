<%@page language="java" contentType="text/html;charset=MS950" import="com.sertek.util.*,java.io.*"%>
<%
try{
CheckObject check = new CheckObject();
String filenm = check.checkNull(request.getParameter("filenm"), "").toString();
System.out.println("filenm = " + filenm);
%>
<html>
<head>
<title>預覽視窗</title>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<style type="text/css">
<!--
@import "../css/CSS.css";
-->
</style>
<script language="JavaScript">
<!--

//-->
</script>
</head>
<body leftMargin="0" topMargin="0">
<form name="form">
	<table width="100%" border="0">
<%
	System.out.println(filenm);
	StringBuffer sb = new StringBuffer();
	String aLine = "";
	File file = new File(filenm);
	if(file.exists()){
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((aLine = br.readLine()) != null) {
			sb.append(aLine + "\n");
		}
		br.close();
	}else{
		sb.append("檔案不存在！");
	}
%>
		<tr>
			<td>
				<font size="6"><textarea name="content" cols="76" rows="20" wrap="off"><%=sb.toString()%></textarea></font>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
<%
} catch(Exception e) {
	out.println(e);
}
%>