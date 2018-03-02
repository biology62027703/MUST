<script type="text/javascript" src="../jquery/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../jquery/ui/js/jquery-ui-1.9.2.custom.min.js"></script>

<link rel="stylesheet" href="../jquery/css/theme.blue.css" type="text/css">
<link rel="stylesheet" href="../jquery/ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css">

<script type="text/javascript" src="../jquery/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="../jquery/js/jquery.metadata.js"></script>
<script type="text/javascript" src="../jquery/js/jquery.tablesorter.widgets.min.js"></script>

<link rel="stylesheet" href="../jquery/addons/pager/jquery.tablesorter.pager.css" type="text/css">
<script type="text/javascript" src="../jquery/addons/pager/jquery.tablesorter.pager.min.js"></script>
<script type="text/javascript" src="../jquery/js/json2.js"></script>
<script type="text/javascript" src="../jquery/js/DialogUtil.js"></script>
<script type="text/javascript" src="../jquery/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {

		$("table")
		.tablesorter({
			theme:'blue',
			//sortList:[[0,0]],
			widgets:['zebra'],
			widthFixed:true
		});
	});
	
	/*
  	$(function() {
		$(":text").tooltip({
			delay: 0,
			track: false,
			position : {
				my: "left center", 
				at: "right+10 center"
			}
		});
	})
	*/;
	
	/*
 	$(function() {
		//$('#pretty').tooltip({
		$(':input').tooltip({ 
    		track: true, 
    		delay: 0, 
    		showURL: false, 
    		showBody: " - ", 
    		extraClass: "pretty", 
    		fixPNG: true, 
    		opacity: 0.95, 
    		left: -120 
		});
	})
	*/
	
</script>
<style type="text/css">
	#tooltip.pretty {
		font-family: Arial;
		border: none;
		width: 210px;
		padding:20px;
		height: 135px;
		opacity: 0.8;
		background: url('shadow.png');
	}
</style>