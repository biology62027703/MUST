<!DOCTYPE HTML>
<%@page language="java" import="com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.sys.*" contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>

<head>
	<title>MÜST內網首頁</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
	<link rel="stylesheet" href="../../assets/css/main.css" />
	<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
	<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
	<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
	<script>
		!window.jQuery && document.write('<script src="jquery-1.4.3.min.js"><\/script>');
	</script> -->
	<!-- <script type="text/javascript" src="./fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="./fancybox/jquery.fancybox-1.3.4.pack.js"></script>
	<link rel="stylesheet" type="text/css" href="./fancybox/jquery.fancybox-1.3.4.css" media="screen" />
 	<link rel="stylesheet" href="style.css" /> -->
 	<script src="//code.jquery.com/jquery-3.1.1.min.js"></script>
 	<script src="./dist/jqueryfancybox.js"></script>
 	<script src="./dist/jqueryfancyboxmin.js"></script>
 	<link rel="stylesheet" href="./dist/jqueryfancybox.css" />
 	<link rel="stylesheet" href="./dist/jqueryfancyboxmin.css" />
	<script src="../../assets/js/skel.min.js"></script>
	<script src="../../assets/js/util.js"></script>
	<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
	<script src="../../assets/js/jquery.min.js"></script>
	 <script src="../../assets/js/main.js"> 
	 
	 <script type="text/javascript">
		$(document).ready(function() {
			/*
			*   Examples - images
			*/

			$("a#example1").fancybox({
				'width'				: '100%',
				'height'			: '100%',
				'autoScale'			: true,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'type'				: 'iframe'
			});

			$("a#example2").fancybox({
				'overlayShow'	: false,
				'transitionIn'	: 'elastic',
				'transitionOut'	: 'elastic'
			});

			$("a#example3").fancybox({
				'transitionIn'	: 'none',
				'transitionOut'	: 'none'	
			});

			$("a#example4").fancybox({
				'opacity'		: true,
				'overlayShow'	: false,
				'transitionIn'	: 'elastic',
				'transitionOut'	: 'none'
			});

			$("a#example5").fancybox();

			$("a#example6").fancybox({
				'titlePosition'		: 'outside',
				'overlayColor'		: '#000',
				'overlayOpacity'	: 0.9
			});

			$("a#example7").fancybox({
				'titlePosition'	: 'inside'
			});

			$("a#example8").fancybox({
				'titlePosition'	: 'over'
			});

			$("a[rel=example_group]").fancybox({
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'titlePosition' 	: 'over',
				'titleFormat'		: function(title, currentArray, currentIndex, currentOpts) {
					return '<span id="fancybox-title-over">Image ' + (currentIndex + 1) + ' / ' + currentArray.length + (title.length ? ' &nbsp; ' + title : '') + '</span>';
				}
			});

			/*
			*   Examples - various
			*/

			$("#various1").fancybox({
				'titlePosition'		: 'inside',
				'transitionIn'		: 'none',
				'transitionOut'		: 'none'
			});

			$("#various2").fancybox();

			$("#various3").fancybox({
				'width'				: '90%',
				'height'			: '90%',
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'type'				: 'iframe'
			});

			$("#various4").fancybox({
				'padding'			: 0,
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none'
			});
		});
	</script>
</head>
<body>
<div id="wrapper">
				<!-- Header -->
					<header id="header">
						<div class="inner">
							<!-- Logo -->
								<a href="index.html" class="logo">
									<span class="symbol"><img src="../../images/logo.svg" alt="" /></span><span class="title">MÜST內部作業</span>
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
							<li><a href="index.jsp">首頁</a></li>
							<li><a href="generic.html">看要放什麼1</a></li>
							<li><a href="generic.html">看要放什麼2</a></li>
							<li><a href="generic.html">看要放什麼3</a></li>
							<li><a href="elements.html">ETC.......</a></li>
						</ul>
					</nav>
				<!-- Main -->
					<div id="main">
						<div class="inner">
							<header>
								<h1>本網站僅限MÜST內部使用</h1>
								<p></p>
							</header>
							<section class="tiles">
								<article class="style1">
									<span class="image">
										<img src="../../images/pic01.jpg" alt="" />
									</span>
									<a href="generic.html"  id="example1">
										<h2>ERP相關</h2>
										<div class="content">
											<p>資訊人員ERP相關需求處理</p>
										</div>
									</a>
								</article>
								<article class="style2">
									<span class="image">
										<img src="../../images/pic02.jpg" alt="" />
									</span>
									<!-- <a href="generic.html">
										<h2>DIVA</h2>
										<div class="content">
											<p>資訊人員DIVA相關需求處理</p>
										</div>
									</a> -->
									
									<h2>DIVA_SESSION_KILL</h2>
									<div class="content">
										<a href="DIVA_SESSION_KILL.jsp"><p>踢掉DIVA使用者</p></a>
									</div>
								</article>
								<article class="style3">
									<span class="image">
										<img src="../../images/pic03.jpg" alt="" />
									</span>
									<a href="generic.html">
										<h2>官網相關</h2>
										<div class="content">
											<p>資訊人員官網相關需求處理</p>
										</div>
									</a>
								</article>
								<article class="style4">
									<span class="image">
										<img src="../../images/pic04.jpg" alt="" />
									</span>
									<a href="generic.html">
										<h2>法務授權系統相關</h2>
										<div class="content">
											<p>資訊人員法務授權系統需求相關處理</p>
										</div>
									</a>
								</article>
								<article class="style5">
									<span class="image">
										<img src="../../images/pic05.jpg" alt="" />
									</span>
									<a href="generic.html">
										<h2>清單系統</h2>
										<div class="content">
											<p>資訊人員清單系統相關需求處理</p>
										</div>
									</a>
								</article>
								<article class="style6">
									<span class="image">
										<img src="../../images/pic06.jpg" alt="" />
									</span>
									<a href="generic.html">
										<h2>其他需求處理</h2>
										<div class="content">
											<p>其他需求放在這</p>
										</div>
									</a>
								</article>
								
			</div>
<div id=comtent>
	<h1>fancybox <span>v1.3.4</span></h1>

	<p>This is a demonstration. <a href="http://fancybox.net">Home page</a></p>

	<hr />

	<p>
		Different animations<br />

		<a href="./example/1_b.jpg"><img alt="example1" src="./example/1_s.jpg" /></a>

		<a id="example2" href="./example/2_b.jpg"><img alt="example2" src="./example/2_s.jpg" /></a>

		<a id="example3" href="./example/3_b.jpg"><img alt="example3" src="./example/3_s.jpg" /></a>
		
		<a id="example4" href="./example/4_b.jpg"><img class="last" alt="example4" src="./example/4_s.jpg" /></a>
	</p>

	<p>
		Different title positions<br />

		<a id="example5" href="./example/5_b.jpg" title="Lorem ipsum dolor sit amet, consectetur adipiscing elit."><img alt="example4" src="./example/5_s.jpg" /></a>
		
		<a id="example6" href="./example/6_b.jpg" title="Etiam quis mi eu elit tempor facilisis id et neque. Nulla sit amet sem sapien. Vestibulum imperdiet porta ante ac ornare. Vivamus fringilla congue laoreet."><img alt="example5" src="./example/6_s.jpg" /></a>

		<a id="example7" href="./example/7_b.jpg" title="Cras neque mi, semper at interdum id, dapibus in leo. Suspendisse nunc leo, eleifend sit amet iaculis et, cursus sed turpis."><img alt="example6" src="./example/7_s.jpg" /></a>

		<a id="example8" href="./example/8_b.jpg" title="Sed vel sapien vel sem tempus placerat eu ut tortor. Nulla facilisi. Sed adipiscing, turpis ut cursus molestie, sem eros viverra mauris, quis sollicitudin sapien enim nec est. ras pulvinar placerat diam eu consectetur."><img class="last" alt="example7" src="./example/8_s.jpg" /></a>
	</p>

	<p>
		Image gallery (ps, try using mouse scroll wheel)<br />

		<a rel="example_group" href="./example/9_b.jpg" title="Lorem ipsum dolor sit amet"><img alt="" src="./example/9_s.jpg" /></a>

		<a rel="example_group" href="./example/10_b.jpg" title=""><img alt="" src="./example/10_s.jpg" /></a>

		<a rel="example_group" href="./example/11_b.jpg" title=""><img alt="" src="./example/11_s.jpg" /></a>
		
		<a rel="example_group" href="./example/12_b.jpg" title=""><img class="last" alt="" src="./example/12_s.jpg" /></a>
	</p>

	<p>
		Various examples
	</p>

	<ul>
		<li><a id="various1" href="#inline1" title="Lorem ipsum dolor sit amet">Inline</a></li>
		<li><a id="various2" href="ajax.txt">Ajax</a></li>
		<li><a id="various3" href="http://google.ca">Iframe</a></li>
		<li><a id="various4" href="http://www.adobe.com/jp/events/cs3_web_edition_tour/swfs/perform.swf">Swf</a></li>
	</ul>

	<div style="display: none;">
		<div id="inline1" style="width:400px;height:100px;overflow:auto;">
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam quis mi eu elit tempor facilisis id et neque. Nulla sit amet sem sapien. Vestibulum imperdiet porta ante ac ornare. Nulla et lorem eu nibh adipiscing ultricies nec at lacus. Cras laoreet ultricies sem, at blandit mi eleifend aliquam. Nunc enim ipsum, vehicula non pretium varius, cursus ac tortor. Vivamus fringilla congue laoreet. Quisque ultrices sodales orci, quis rhoncus justo auctor in. Phasellus dui eros, bibendum eu feugiat ornare, faucibus eu mi. Nunc aliquet tempus sem, id aliquam diam varius ac. Maecenas nisl nunc, molestie vitae eleifend vel, iaculis sed magna. Aenean tempus lacus vitae orci posuere porttitor eget non felis. Donec lectus elit, aliquam nec eleifend sit amet, vestibulum sed nunc.
		</div>
	</div>

	<p>
		Ajax example will not run from your local computer and requires a server to run.
	</p>
	<p>
		Photo Credit: <a href="http://www.flickr.com/people/kharied/">Katie Harris</a>
	</p>
</div>
</body>
</html>