<!doctype html>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/CFC.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/tabControlUtil.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/TabControlUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/StrUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/DateUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/util/FormUtil.js"></script>
<%
	if( session.getAttribute("User")==null )
		response.sendRedirect(request.getContextPath() + "/INDEX.jsp");
%>
<script>

function findPosY(obj) {  
  var curtop = 0;  
  if(obj.offsetParent)  
      while(1)  
      {  
        curtop += obj.offsetTop;  
        if(!obj.offsetParent)  
          break;  
        obj = obj.offsetParent;  
      }  
  else if(obj.y)  
      curtop += obj.y;  
  return curtop;   
}

</script>