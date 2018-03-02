<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/tabControlUtil.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/uITableGrid.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/DynUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/FormUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/RenderUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/HelpUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/DateUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/StrUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/TabControlUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/CheckUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/UiTableGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/CALENDAR.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/json2.js"></script>

<script>

String.prototype.Blength = function() {
    var arr = this.match(/[^\x00-\xff]/ig);
    return  arr == null ? this.length : this.length + arr.length;
}

Array.prototype.remove = function(from, to) {
  var rest = this.slice((to || from) + 1 || this.length);
  this.length = from < 0 ? this.length + from : from;
  return this.push.apply(this, rest);
};

$(document).ready(function(){

	$("body").keypress(function(){
		if(event.keyCode==8) event.keyCode = 0;
	}).keyup(function(){
		if(event.keyCode==8) event.keyCode = 0;
	}).keydown(function(){
		if(event.keyCode==8) event.keyCode = 0;
	});	
	
});
</script>