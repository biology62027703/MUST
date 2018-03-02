<%@page language="java" import="java.sql.SQLException,com.sertek.form.*,com.sertek.util.*,java.util.*,java.net.*,com.sertek.rest.*,com.sun.jersey.api.client.config.*,com.sun.jersey.api.client.*,com.sertek.db.*" contentType="text/html;charset=UTF-8"%>
<%@include file="/utility/PAGEINIT.jsp"%>
<!DOCTYPE html>
<head>
<title>MÜST內部休假公出行事曆</title>
<meta charset='utf-8' />
<link href='fullcalendar-3.4.0/fullcalendar-3.4.0/fullcalendar.min.css' rel='stylesheet' />
<link href='fullcalendar-3.4.0/fullcalendar-3.4.0/fullcalendar.print.min.css' rel='stylesheet' media='print' />
<style>

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		/*max-width: 900px;*/
		
		margin: 0 auto;
	}
	
	#top {
		background: #eee;
		border-bottom: 1px solid #ddd;
		padding: 0 10px;
		line-height: 40px;
		font-size: 12px;
	}

</style>
</head>

<script src='<%=request.getContextPath()%>/fullcalendar-3.4.0/fullcalendar-3.4.0/lib/moment.min.js'></script>
<script src='<%=request.getContextPath()%>/fullcalendar-3.4.0/fullcalendar-3.4.0/lib/jquery.min.js'></script>
<script src='<%=request.getContextPath()%>/fullcalendar-3.4.0/fullcalendar-3.4.0/fullcalendar.min.js'></script>
<script src='<%=request.getContextPath()%>/fullcalendar-3.4.0/fullcalendar-3.4.0/locale.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>

<script>

	$(document).ready(function() {
		var data;
		//var initialLocaleCode = "zh-tw";
		//var initialLocaleCode = "en";
		var show_mode="month,agendaWeek,agendaDay,listWeek,listDay";
		
		/* if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) { 
			//alert(navigator.userAgent);
			show_mode="month,agendaWeek,agendaDay,listDay";
		} */
		
		formUtil.submitTo({
			url: "CALENDAR.do?action=doQuery",
			async: true,
			formObj: $(form),
			onSuccess: function(jsonData){
				data = jsonData.data.DATA;	
				
				$('#calendar').fullCalendar({
					//height: "70%",
					//height: 'auto',
					header: {
						left: "prev,next today",
						center: "title",
						right: show_mode
					},
					views: {
						listDay: { buttonText: "list day" },
						listWeek: { buttonText: "list week" }
					},
					minTime: '09:00:00',
			        maxTime: '18:00:00',
					//defaultDate: '2017-05-12',
					allDaySlot: false,
					weekends: false,
					//locale: initialLocaleCode,
					defaultView: "listDay",
					navLinks: true, // can click day/week names to navigate views
					editable: false,
					eventLimit: true, // allow "more" link when too many events
					events: data,
					viewRender: function(view, element) { 
						//alert('new view: ' + view.name); 
						if(view.name=="listWeek"&&/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
							$('#calendar').fullCalendar('option', 'contentHeight', 1500);
						} else {
							if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
								$('#calendar').fullCalendar('option', 'contentHeight', 500);
							} else {
								$('#calendar').fullCalendar('option', 'contentHeight', '90%');
							}
						}
					}
				});
				
				//$('.fc-list-view .fc-scroller').css('height', 'auto');
			}
		});
		
		/* // build the locale selector's options
		$.each($.fullCalendar.locales, function(localeCode) {
			$('#locale-selector').append(
				$('<option/>')
					.attr('value', localeCode)
					.prop('selected', localeCode == initialLocaleCode)
					.text(localeCode)
			);
		});

		// when the selected option changes, dynamically change the calendar option
		$('#locale-selector').on('change', function() {
			if (this.value) {
				$('#calendar').fullCalendar('option', 'locale', this.value);
			}
		}); */
		
		$(":button[class='button']").click(function(){
			alert(0);
			alert(this.value);
		})
		
	});

</script>

<body>
<form name="form">
	<!-- <div id='top'>
		Locales:
		<select id='locale-selector'></select>
	</div> -->
	<div id='calendar'></div>
</form>
</body>
</html>
