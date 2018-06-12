<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="COOKIE.jsp"%>
<header id="header">
	<div class="inner">
		<!-- Logo -->
			<a href="<%=request.getContextPath()%>/INDEX.jsp" class="logo">
				<span class="symbol"><img src="<%=request.getContextPath()%>/images/logo.svg" alt="" /></span><span class="title">MÜST內部作業(<%=user_name %>)</span>
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
		<li><a href="<%=request.getContextPath()%>/INDEX.jsp">首頁</a></li>
		<li><a href="<%=request.getContextPath()%>/INTER.jsp">MÜST各網站入口</a></li>			
		<li><a href="<%=request.getContextPath()%>/CONTENT_ERP.jsp">ERP相關</a></li>
		<li class="view2"><a href="<%=request.getContextPath()%>/CONTENT_DIVA.jsp">DIVA相關</a></li>	
		<li class="view"><a href="<%=request.getContextPath()%>/CONTENT_MUSTWEB.jsp">官網相關</a></li>
		<li class="view3"><a href="<%=request.getContextPath()%>/CONTENT_LAL.jsp">法務授權相關</a></li>	
		<li class="view"><a href="<%=request.getContextPath()%>/CONTENT_LIST.jsp">清單相關</a></li>
		<li class="view4"><a href="<%=request.getContextPath()%>/SOCIETY/SOCIETY_FILES_MOVE.jsp">海外協會檔案相關</a></li>
		<li class="view"><a href="<%=request.getContextPath()%>/CONTENT_OTHERS.jsp">其他需求</a></li>
		
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
			$(".view4").remove();
		}
	 	if("124".indexOf(group)>-1) {
			$(".view3").remove();
			$(".view4").remove();
		}
	 }
	
	})
</script>