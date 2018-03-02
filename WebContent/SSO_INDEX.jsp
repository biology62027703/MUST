<%@page language="java" import="java.util.*,com.sertek.util.*,com.sertek.sys.*" contentType="text/html;charset=MS950"%>
<%
CheckObject check = new CheckObject();
String sol_usrnm = check.checkNull(request.getParameter("sol_usrnm"), "").toString();
String sol_idno = check.checkNull(request.getParameter("sol_idno"), "").toString();
String REFERER = (String)check.checkNull(request.getHeader("REFERER"), "");

System.out.println(REFERER);

boolean canLogin = false;
String[] allow = split(StaticObj.getS08("SSO_INDEX", "REFERER"));
for(int i=0;i<allow.length;i++) {
	if( REFERER.equals(allow[i]) ) 
		canLogin = true;
}
%>

<%if( canLogin ) { %>
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
<input type="hidden" name="method" value="sso">
<input type="hidden" name="sol_usrnm" value="<%=sol_usrnm%>">
<input type="hidden" name="sol_idno" value="<%=sol_idno%>">
</form>
</body>

</html>

<%}else{%>
<html>
<head>
<title></title>
<body text="#000000" leftmargin="0" topmargin="0">
請由民眾認證系統連至本系統
</body>

</html>
<%}%>

<%!
	public String[] split(String str) {
		utility ut = new utility();
		String[] ret = null;
		try {
			ret = str.split("[；;,]"); // 分號或逗號
			for (int i = 0; i < ret.length; i++) {
				ret[i] = ut.Ltrim(ret[i].trim());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return ret;
	}
%>