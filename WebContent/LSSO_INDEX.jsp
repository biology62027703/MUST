<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=MS950"%>
<%
CheckObject check = new CheckObject();
String sol_usrnm = check.checkNull(request.getParameter("sol_usrnm"), "").toString();
String sol_idno = check.checkNull(request.getParameter("sol_idno"), "").toString();
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="../css/CSS.css" type="text/css">
<script language="javascript">
	
	function iniForm()
	{
		form.submit();
	}
	
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" onload="iniForm()">
<form name="form" action="Login.do" method="post">
<input type="hidden" name="method" value="lsso">
<input type="hidden" name="sol_usrnm" value="<%=sol_usrnm%>">
<input type="hidden" name="sol_idno" value="<%=sol_idno%>">
</form>
</body>

</html>