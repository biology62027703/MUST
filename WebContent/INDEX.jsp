<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.sys.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>	
	<%@include file="HEAD.jsp"%>	
	<body>	
		<!-- Wrapper -->
			<div id="wrapper">
				<!-- Header -->
				<%@include file="TOP.jsp"%>	
				<!-- Main -->
				<div id="main">
					<div class="inner">
						<header>
							<!-- <h1>本網站僅限MÜST內部使用</h1> -->
							<p></p>
						</header>
						<section class="tiles">
							<article class="style9">
								<span class="image">
									<img src="images/pic06.jpg" alt="" />
								</span>
								<a href="INTER.jsp">
									<h2>MÜST各網站入口</h2>
									<div class="content">
										<p>官網前台、後台、清單、授權</p>
									</div>
								</a>
							</article>
							<article class="style1">
								<span class="image">
									<img src="images/ERP.jpg" alt="" />
								</span>
								<a href="CONTENT_ERP.jsp">
									<h2>ERP相關</h2>
									<div class="content">
										<p>公司同事休假行事曆..</p>
									</div>
								</a>
							</article>
							<article class="style2">
								<span class="image">
									<img src="images/diva.jpg" alt="" />
								</span>
								<a href="CONTENT_DIVA.jsp">
									<h2>DIVA相關</h2>
									<div class="content">
										<p>DIVA相關</p>
									</div>
								</a>
							</article>
							<article class="style3">
								<span class="image">
									<img src="images/g2.png" alt="" />
								</span>
								<a href="CONTENT_MUSTWEB.jsp">
									<h2>官網相關</h2>
									<div class="content">
										<p>資訊人員官網相關需求處理</p>
									</div>
								</a>
							</article>
							<article class="style4">
								<span class="image">
									<img src="images/g5.jpg" alt="" />
								</span>
								<a href="CONTENT_LAL.jsp">
									<h2>法務授權系統相關</h2>
									<div class="content">
										<p>資訊人員法務授權系統需求相關處理</p>
									</div>
								</a>
							</article>
							<article class="style5">
								<span class="image">
									<img src="images/list.png" alt="" />
								</span>
								<a href="CONTENT_LIST.jsp">
									<h2>清單系統</h2>
									<div class="content">
										<p>資訊人員清單系統相關需求處理</p>
									</div>
								</a>
							</article>
							<article class="style10">
								<span class="image">
									<img src="images/pic10.png" alt="" />
								</span>
								<a href="SEND_STATEMENT_EMAIL.jsp">
									<h2>寄信相關</h2>
									<div class="content">
										<p>寄信需求</p>
									</div>
								</a>
							</article>
							<article class="style6">
								<span class="image">
									<img src="images/others.jpg" alt="" />
								</span>
								<a href="CONTENT_OTHERS.jsp">
									<h2>其他需求</h2>
									<div class="content">
										<p>之前放在192.168.1.51 的SOP步驟</p>
									</div>
								</a>
							</article>
							<article class="style7">
								<span class="image">
									<img src="images/function.jpg" alt="" />
								</span>
								<a href="CONTENT_TECH.jsp">
									<h2>功能開發測試</h2>
									<div class="content">
										<p>技術測試</p>
									</div>
								</a>
							</article>
							<article class="style8">
								<span class="image">
									<img src="images/backstage.jpg" alt="" />
								</span>
								<a href="BACKSTAGE.jsp">
									<h2>內網後台</h2>
									<div class="content">
										<p>後台管理</p>
									</div>
								</a>
							</article>							
						</section>
					</div>
				</div>
			</div>
			<script>
			 var group="<%=group%>";
			 $(document).ready(function(){
				 if("14".indexOf(group)>-1) {
				 	 $(".style2").remove();
			 		 $(".style3").remove();
			 		 $(".style4").remove();
			 		 $(".style5").remove();
			 		 $(".style6").remove();
			 		 $(".style7").remove();
			 		 $(".style8").remove();
			 		 $(".style10").remove();
				 }
				 if("2".indexOf(group)>-1) {
					 $(".style3").remove();
				 	 $(".style4").remove();
				 	 $(".style5").remove();
				 	 $(".style6").remove();
				 	 $(".style7").remove();
				 	 $(".style8").remove();
				 	 $(".style10").remove();
				 }
				 if("3".indexOf(group)>-1) {
				 	 $(".style2").remove();
			 		 $(".style3").remove();
			 		 $(".style5").remove();
			 		 $(".style6").remove();
			 		 $(".style7").remove();
			 		 $(".style8").remove();
			 		 $(".style10").remove();
				 }
			 })
			</script>
	</body>
</html>