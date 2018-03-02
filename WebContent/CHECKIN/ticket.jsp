<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
CheckObject check = new CheckObject();
String ticket = (String)check.checkNull(request.getParameter("ticket"), "");
%>
<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=Generator content="Microsoft Word 14 (filtered)">
<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="WebBrowser3" width="0" VIEWASTEXT></OBJECT>
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:新細明體;
	panose-1:2 2 5 0 0 0 0 0 0 0;}
@font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
	{font-family:Calibri;
	panose-1:2 15 5 2 2 2 4 3 2 4;}
@font-face
	{font-family:新細明體;
	panose-1:3 0 5 9 0 0 0 0 0 0;}
@font-face
	{font-family:新細明體;
	panose-1:2 2 5 0 0 0 0 0 0 0;}
@font-face
	{font-family:新細明體;
	panose-1:3 0 5 9 0 0 0 0 0 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin:0cm;
	margin-bottom:.0001pt;
	font-size:12.0pt;
	font-family:"Calibri","sans-serif";}
 /* Page Definitions */
 @page WordSection1
	{size:595.3pt 841.9pt;
	margin:72.0pt 90.0pt 72.0pt 90.0pt;
	layout-grid:18.0pt;}
div.WordSection1
	{page:WordSection1;}
@media print { 
 /* All your print styles go here */
 #header, #footer, #nav { display: none !important; } 
}
-->
</style>

</head>

<%@include file="../utility/JQUERY.jsp"%>
<script>
	$(document).ready(function(){
		var ticket = "<%=ticket%>";
		if(ticket!=null) {
			
			window.print();
			
		}
	})
</script>
<body>
<form>
<div class=WordSection1 style='layout-grid:18.0pt'>

<table class=MsoTableGrid border=0 cellspacing=0 cellpadding=0 align=left
 width=756 style='width:567.05pt;border:none;
 margin-left:0pt;margin-right:6.75pt'>
 
 <tr style='height:332.55pt'>
  <td width=371 style='width:278.2pt;padding:
  0cm 4.4pt 0.55cm 0pt;height:332.55pt'>
  <p class=MsoNormal align=center style='margin-left:0pt;margin-bottom:10pt;text-align:center'><span
  lang=EN-US style='font-size:48.0pt;font-family:標楷體'><br><br><%=ticket%>票</span></p>
  </td>
  <td width=385 style='width:288.85pt;border-left:
  none;padding:0cm 0pt 0.55cm 8.4pt;height:332.55pt'>
  <p class=MsoNormal align=center style='margin-left:12.1pt;margin-bottom:10pt;text-align:center'><span
  lang=EN-US style='font-size:48.0pt;font-family:標楷體'><br><br><%=ticket%>票</span></p>
  </td>
 </tr>
 
 <tr style='height:339.4pt'>
  <td style='padding:0cm 4.4pt 0cm 0pt;'>
  <p class=MsoNormal align=center style='margin-left:0pt;text-align:center'><span
  lang=EN-US style='font-size:48.0pt;font-family:新細明體'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='margin-left:0pt;text-align:center'><span
  lang=EN-US style='font-size:48.0pt;font-family:新細明體'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='margin-left:0pt;text-align:center'><span
  lang=EN-US style='font-size:48.0pt;font-family:新細明體'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='margin-left:0pt;text-align:center'><span
  lang=EN-US style='font-size:48.0pt;font-family:標楷體'><br><br><%=ticket%>票</span></p>
  </td>
  <td style='padding:0cm 0pt 0cm 8.4pt;'>
  <p class=MsoNormal align=center style='margin-left:12.1pt;text-align:center'>
  <span lang=EN-US style='font-size:48.0pt;font-family:新細明體'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='margin-left:12.1pt;text-align:center'>
  <span lang=EN-US style='font-size:48.0pt;font-family:新細明體'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='margin-left:12.1pt;text-align:center'>
  <span lang=EN-US style='font-size:48.0pt;font-family:新細明體'>&nbsp;</span></p>
  <p class=MsoNormal align=center style='margin-left:12.1pt;text-align:center'>
  <span lang=EN-US style='font-size:48.0pt;font-family:標楷體'><br><br><%=ticket%>票</span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><span>&nbsp;</span></p>

</div>
</form>
</body>

</html>
