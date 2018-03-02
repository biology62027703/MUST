<%@page language="java" contentType="text/html;charset=MS950"%>
<script language="javascript" type="text/javascript">
<!--

	function doPrivacy()
	{
		window.open("http://www.judicial.gov.tw/privacy.asp");
	}
		
//-->
</script>  

	<table width="800" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center">
			
<a href="#" onclick="doAboutme();" class="link">關於本站</a>
<%if(request.getSession().getAttribute("User") != null){ %>
｜
<a href="#" onclick="doFeedback();" class="link">問題反映</a>
｜
<a href="#" onclick="doLink();" class="link">好站連結</a>
<%} %>
<br><br>
<a href="#" onclick="doPrivacy();">司法院全球資訊網 版權宣告 / 隱私權保護 / 網站安全政策</a>
<br><br>
本服務為司法院委託宏碁公司建置營運
<br>
Copyright(c) 2013 Acer Inc. All Rights Reserved.
			</td>
			<td align="right" valign="top">
<%if(request.getSession().getAttribute("User") != null){ %>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>客服專線　02-27841057</td>
</tr>
<tr>
	<td>周一至周五：9:00~18:00</td>
</tr>
</table>
<br>
<%} %>
			</td>
		</tr>
	</table>