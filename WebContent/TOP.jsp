<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="COOKIE.jsp"%>
<header id="header">
	<div class="inner">
		<!-- Logo -->
			<a href="INDEX.jsp" class="logo">
				<span class="symbol"><img src="images/logo.svg" alt="" /></span><span class="title">MÜST內部作業(<%=user_name %>)</span>
			</a>
		<!-- Nav -->
			<nav>
				<ul>
					<li><a href="#menu">Menu</a></li>
				</ul>
			</nav>
	</div>
</header>
<!-- Menu -->
<nav id="menu">
	<h2>Menu</h2>
	<ul>
		<li><a href="INDEX.jsp">首頁</a></li>
		<li><a href="INTER.jsp">MÜST各網站入口</a></li>			
		<li><a href="CONTENT_ERP.jsp">ERP相關</a></li>
		<li class="view2"><a href="CONTENT_DIVA.jsp">DIVA相關</a></li>	
		<li class="view"><a href="CONTENT_MUSTWEB.jsp">官網相關</a></li>
		<li class="view3"><a href="CONTENT_LAL.jsp">法務授權相關</a></li>	
		<li class="view"><a href="CONTENT_LIST.jsp">清單相關</a></li>
		<li class="view"><a href="CONTENT_OTHERS.jsp">其他需求</a></li>
		<li><a href="LOGOUT.jsp">登出</a></li>
	</ul>
</nav>
<script>
	var group="<%=group%>";
	$(document).ready(function(){
	 if("1234".indexOf(group)>-1) {
	 	$(".view").remove();
	 	if("134".indexOf(group)>-1) {
			$(".view2").remove();
		}
	 	if("124".indexOf(group)>-1) {
			$(".view3").remove();
		}
	 }
	
	})
</script>