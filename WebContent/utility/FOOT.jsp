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
			
<a href="#" onclick="doAboutme();" class="link">���󥻯�</a>
<%if(request.getSession().getAttribute("User") != null){ %>
�U
<a href="#" onclick="doFeedback();" class="link">���D�ϬM</a>
�U
<a href="#" onclick="doLink();" class="link">�n���s��</a>
<%} %>
<br><br>
<a href="#" onclick="doPrivacy();">�q�k�|���y��T�� ���v�ŧi / ���p�v�O�@ / �����w���F��</a>
<br><br>
���A�Ȭ��q�k�|�e�U���֤��q�ظm��B
<br>
Copyright(c) 2013 Acer Inc. All Rights Reserved.
			</td>
			<td align="right" valign="top">
<%if(request.getSession().getAttribute("User") != null){ %>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>�ȪA�M�u�@02-27841057</td>
</tr>
<tr>
	<td>�P�@�ܩP���G9:00~18:00</td>
</tr>
</table>
<br>
<%} %>
			</td>
		</tr>
	</table>