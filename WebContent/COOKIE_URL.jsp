<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%
Cookie [] cookie = request.getCookies();
String CHECK="";
String group="";
String user_name="";
if (cookie!=null){
	for(int i=0;i<cookie.length;i++){
		System.out.println("-----:"+cookie[i].getName());
		if("user_group".equals(cookie[i].getName())){
			System.out.println("使用者已登入");
			CHECK="Y";
			group=cookie[i].getValue();
		} 
		if("user_name".equals(cookie[i].getName())) {
			user_name=cookie[i].getValue();
		}
	}
} 
/* if(!CHECK.equals("Y")) {
	response.sendRedirect("LOGIN.jsp");
} */
%>
<script type="text/javascript">
$(document).ready(function(){
	var url = location.href;
	var check="<%=CHECK%>";
	if(check=="Y"){
		
	} else {
		window.location.href="LOGIN.jsp?url="+url;
	}
});
</script>
