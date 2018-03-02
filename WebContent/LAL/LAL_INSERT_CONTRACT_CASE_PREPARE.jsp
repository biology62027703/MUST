<%@page language="java" import="java.util.*,com.sertek.table.*,com.sertek.util.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
</head>
<body>

<div align="center" style="font-size:18px">門市續約繳款單匯入法務授權系統</div><br>
<div align="left" style="font-size:14px">
1. USER提供的EXCEL通常發票抬頭跟簽約單位是相同的<br>
2. 把EXCEL檔案放到\\192.168.1.51\song\底下<br>
3. 修改\\192.168.1.51\song\INSERT_CONTRACT_CASE.asp如下圖:<br>
<input type="image" src="images\image001.png">
<br>
Source改成提供的檔案名稱<br>
<input type="image" src="images\image002.PNG"><br>
FYEAR="17"      ==> 要改成匯入當天的年度<br>
4.  執行\\192.168.1.51\song\INSERT_CONTRACT_CASE.asp  <input type="button" value="GOGOGO" onclick="javascript:window.open('http://192.168.1.51/song/insert_contract_case.asp')"><br>
5.  確認檔案正確匯入後，<br>
SELECT *   FROM [LAL].[dbo].[CONTRACT]  where CONT_KDATE='88888888'<br>
SELECT *   FROM [LAL].[dbo].[CASE]  where CASE_KDATE='88888888'<br>
SELECT *   FROM [LAL].[dbo].[RECEIVE]  where REC_DATE='88888888'<br>

以上資料[CONTRACT].CONT_KDATE、[CASE].CASE_KDATE、[RECEIVE].REC_DATE要改成匯入當天日期，update語法如下:<br>

update [LAL].[dbo].[CONTRACT]  set  CONT_KDATE='<font color="red">匯入當天日期</font>'  where  CONT_KDATE='88888888'<br>
update [LAL].[dbo].[CASE] set  CASE_KDATE='<font color="red">匯入當天日期</font>'  where CASE_KDATE='88888888' <br>
update [LAL].[dbo].[RECEIVE] set  REC_DATE='<font color="red">匯入當天日期</font>' where REC_DATE='88888888'<br>

</div>

</body>

</html>
