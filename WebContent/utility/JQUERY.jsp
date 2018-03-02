<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/ui/js/jquery-ui-1.9.2.custom.min.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/jquery/ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/jquery.metadata.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/FormUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/RenderUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/js/StrUtil.js"></script>

<script type="text/javascript">
String.prototype.Blength = function() {
    var arr = this.match(/[^\x00-\xff]/ig);
    return  arr == null ? this.length : this.length + arr.length;
}
$(function() {

});	
</script>

<!-- <style type="text/css">
	#tooltip.pretty {
		font-family: Arial;
		border: none;
		width: 210px;
		padding:20px;
		height: 135px;
		opacity: 0.8;
		background: url('shadow.png');
	}
</style> -->