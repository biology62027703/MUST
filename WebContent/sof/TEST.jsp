<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*,com.sertek.sys.*" contentType="text/html;charset=MS950"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" href="../css/CSS.css" type="text/css">
<script type="text/javascript">
function close(){
dialogUtil.closeDialog();
}
	
</script>
</head>

<body text="#000000" leftmargin="0" topmargin="0" >
<form name="form" action="" method="post">

<div align="center">

<%@include file="../utility/TOP.jsp"%>

<br>

<table width="600"><tr><td>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td align="left">�q�k�|�u�W�_�D�ήѪ��ǰe�@�~���x > �ӤH��ƺ��@</td>
</tr>
</table>

<br>

<!-- �ӤH��� -->
<table width="100%" border="1" cellpadding="4" cellspacing="0">
<tr>
	<td class="title">
		<table width="100%">
		<tr>
			<td align="left" class="title">�ӤH��ƺ��@</td>
			<td align="right" class="title">
				<button class="btn2" id="openbox" onclick="close()">����</button>
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

<!-- �ӤH��� -->

</div>
</form>
</body>
</html>