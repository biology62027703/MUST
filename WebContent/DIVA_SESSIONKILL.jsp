<%@page language="java" import="java.sql.SQLException,com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%	
CheckObject Check = new CheckObject();
String USER_NAME=(String)Check.checkNull(request.getParameter("USER_NAME"), "");
DBUtility db = com.sertek.util.FormUtil.getDBConnection();
String msg ="";
String icon = "success";
if(!USER_NAME.equals("")) {	
	try {
		int fields = 7;
		String sql = " SELECT S.SID, S.SERIAL#, S.USERNAME, S.OSUSER , S.MACHINE, S.PROGRAM , P.SPID " +
				" FROM  V$SESSION S ,  V$PROCESS P " +
				" WHERE S.PADDR = P.ADDR  AND S.TYPE !='BACKGROUND' AND S.OSUSER !='oracle' " +
				" and s.USERNAME='"+USER_NAME+"'  ";
		Vector vt = db.doSqlSelect(sql, fields, false);
		System.out.println("SQL statement:"+sql+" <BR>");
		System.out.println("SQL Vector:"+vt+" <BR>");
		if(vt.size()>0){
			StringBuffer sql_alter =new StringBuffer();
			for(int i=0;i<vt.size()/fields;i++) {	
				String sid = (String)vt.get(i*fields);
				String SERIAL = (String)vt.get(i*fields+1);
				sql_alter.append("ALTER SYSTEM KILL SESSION '"+sid+","+SERIAL+"' IMMEDIATE");
				System.out.println("kill session SQL statement:"+sql_alter.toString());
				db.execute(sql_alter.toString());
				sql_alter.setLength(0);
			} 
			msg = "刪除SESSION成功";
		} else {
			icon = "info";
			msg = "無此使用者相關SESSION被咬住";
		}
	}catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("系統發生錯誤，錯誤訊息:"+e);
		e.printStackTrace();
	} finally {
		try {
			if(!db.isClosed()) {
				db.closeConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("系統發生錯誤，錯誤訊息:"+e);
			e.printStackTrace();
		}
	}
} else {
	//out.print("參數輸入錯誤!");
}
%>
<!DOCTYPE> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- <link rel="shortcut icon" href="favicon.ico" />
<link rel="stylesheet" href="css/CSS.css" type="text/css">
<link rel="stylesheet" href="jquery/css/theme.ice.css" type="text/css">
<link rel="stylesheet" href="jquery/css/theme.blue.css" type="text/css"> -->
<link rel="stylesheet" href="assets/css/main.css" />
<script src="sweetalert-master/dist/sweetalert.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/skel.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/util.js"></script>
<link rel="stylesheet" type="text/css" href="sweetalert-master/dist/sweetalert.css">
</head>
<body>
<%@include file="/utility/JQUERY.jsp"%>
<script language="javascript">
	$(document).ready(function(){
		var msg = "<%=msg%>";
		var icon = "<%=icon%>";
		if(msg!="") {
			swal("", msg, icon);
		}
		
		$(":input[name='USER_NAME']").keyup(function(e){
			var str=$(this).val();
	        str=str.toUpperCase();
	        $(this).val(str);
		});
		
		$(":input[value='清除使用者SESSION']").click(function(){
			
			if(form.USER_NAME.value==""){
				//alertify.alert("此欄位為必填欄位!");
				swal("USER_NAME", "此欄位為必填欄位!", "error");
				form.USER_NAME.focus();
				return false;
			}
			form.submit();
		})
		$("select").change(function(){
			if($(this).val()!=""){
				$(":input[name='USER_NAME']").val($(this).val());
			}
		});
		$("#unlockuser").click(function(){
			
		});
	})
</script>

<div id="wrapper" style="height:600px">
<!-- Header -->
<div id="main">
	<div class="inner" >
		<h1>DIVA SESSION KILL</h1>								
	</div>
</div>

<form method="post" action="DIVA_SESSIONKILL.jsp" name="form">
	
	<div class="field half first">
		<input type="text" name="USER_NAME" value="<%=USER_NAME%>"  placeholder="請輸入USER_NAME">
	</div>
	<div class="field half">	
		<div class="select-wrapper">
		<select>
				<option value="" selected>常用清單</option>
				<option value="MUST620">1.MUST620</option>				
				<option value="MUST625">2.MUST625</option>
				<option value="MUST635">3.MUST635</option>
				<option value="MUST660">4.MUST660</option>
				<option value="MUST680">5.MUST680</option>
				<option value="KRISTINW">6.KRISTINW</option>
				<option value="LILYC">7.LILYC</option>
			  </select>
		</div>	
	</div>
	<div class="field">		
		<input type="button" value="清除使用者SESSION" >
	</div>
	<div class="field">		
		<input type="button" id="unlockuser" value="解除使用者登入鎖定狀態">
	</div>
	<div class="field half first">
		<input type="text" id="changepassword"  placeholder="密碼長度至少六碼且必須英文大寫+數字">
	</div>
	<div class="field">		
		<input type="button" value="更改密碼">
	</div>
	
</form>
</div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
<script src="assets/js/main.js"></script>
</body>
</html>