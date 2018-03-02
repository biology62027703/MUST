<%@page language="java" import="java.sql.SQLException,com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@include file="/utility/PAGEINIT.jsp"%>
<!DOCTYPE html>
<head>
<meta charset='utf-8' />
<link href='../fullcalendar.min.css' rel='stylesheet' />
<link href='../fullcalendar.print.min.css' rel='stylesheet' media='print' />
<script src='../lib/moment.min.js'></script>
<script src='../lib/jquery.min.js'></script>
<script src='../fullcalendar.min.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>
<script>

	$(document).ready(function() {
		var data;
		formUtil.submitTo({
			url: "CALENDER.do?action=doQuery",
			async: true,
			//formObj: $(form),
			onSuccess: function(jsonData){						
				data = jsonData.data.DATA;	
			}
		});
		
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,basicWeek,basicDay'
			},
			theme: true,
			weekMode: liquid,
			height: 'auto',
			//height: 90%,
			//contentHeight: 2500,
			//defaultDate: '2017-05-12',
			navLinks: true, // can click day/week names to navigate views
			editable: false,
			eventLimit: true, // allow "more" link when too many events
			events: data
		});
		
	});

</script>
<style>

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		/* max-width: 900px; */
		margin: 0 auto;
		/*scrollbar: true;
		height:2000px;*/
	}

</style>
</head>
<body>

	<div id='calendar'></div>

</body>
</html>
