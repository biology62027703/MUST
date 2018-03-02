<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/ui/js/jquery-ui-1.9.2.custom.min.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/jquery/css/theme.blue.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/jquery/ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.tablesorter.widgets.min.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/jquery/addons/pager/jquery.tablesorter.pager.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/addons/pager/jquery.tablesorter.pager.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/DialogUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/CheckUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/HelpUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/DATAMAPPING.js"></script> 
<script type="text/javascript">
String.prototype.Blength = function() {
    var arr = this.match(/[^\x00-\xff]/ig);
    return  arr == null ? this.length : this.length + arr.length;
}
$(function() {
	set_table();
});

function set_table(){
	$("table")
	.tablesorter({
		theme:'blue',
		//sortList:[[0,0]],
		widgets:['zebra'],
		widthFixed:true
	})
	/* .tablesorterPager({
		container: $(".pager"),
		output: '{startRow} to {endRow} ({totalRows})',
		updateArrows: false,
		page: 0,
		size: 100,
		fixedHeight: false,
		removeRows: false,
		cssNext: '.next',
		cssPrev: '.prev',
		cssFirst: '.first',
		cssLast: '.last',
		cssGoto: '.gotoPage',
		cssPageDisplay: '.pagedisplay',
		cssPageSize: '.pagesize',
		cssDisabled: 'disabled',
		cssErrorRow: 'tablesorter-errorRow'
	}); */
	
	$("table[id=tablesorter]").css("margin", "0px 0px 10px 0px");
	$("table[id=tablesorter] tr").removeClass("odd").removeClass("even")
	$("table[id=tablesorter] tr:odd").addClass("odd");
	$("table[id=tablesorter] tr:even").addClass("even");
}

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