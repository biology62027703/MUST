<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/fancybox-3.0/dist/jquery.fancybox.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/fancybox-3.0/dist/jquery.fancybox.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery/fancybox-3.0/dist/jquery.fancybox.min.css" media="screen" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/jquery/fancybox-3.0/dist/jquery.fancybox.css" />
<script type="text/javascript">
	$(document).ready(function(){
		$(".fancybox").fancybox({
			'height' 			: '95%',
			'width' 			: '95%',
			'autoScale'			: true,
			'transitionIn'		: 'elastic',
			'transitionOut'		: 'elastic',
			'type'				: 'iframe',
			'fullScreen' 		: false,
			'closeClick' 		: true
		});
		
	});
</script>