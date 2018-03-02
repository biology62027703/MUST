<%@page contentType="text/html; charset=MS950" import="com.acer.util.*,com.acer.db.query.*,com.acer.form.*,com.acer.log.*,com.acer.db.*,com.acer.encode.*,com.sertek.sys.sys_User,java.sql.*,java.util.*,java.io.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8"); 
	
	int		    logLevel	=	0;
	MyLogger	logger		=	null;
	Connection	conn		=	null;
	HashMap		requestMap	=	null;
	sys_User	sysUser		=	null;
	Exception	pageEx		=	null;
	String[]	template	= 	null;
	
	try
	{
		requestMap	=	FormUtil.getB64Request(request);

		sysUser = (com.sertek.sys.sys_User) session.getAttribute("sys.User");
		if (sysUser == null)
		{ 
			out.clear();
			//out.println("var __AJAX_STATUS__=false;");
			return;
		}		
		template = getCodeTemplate(Utility.checkNull(requestMap.get("code")), requestMap);
		if (Utility.isEmpty(template[0]))
		{
			out.clear();
			//out.println("var __AJAX_STATUS__=false;");
			return;
		}

		conn		=	DBManager.getConnection("ORCL");

		logger	=	new MyLogger(request.getRequestURI().toString());
		logger.iniUserInfo(sysUser.getRemoteAddr()+" "+sysUser.readString("USER","S_USRID")+" "+sysUser.readString("USER","S_USRNM"));
		logger.append("<font color='orange'>[AJAX]</font>Page: 'FetchCode.jsp' Data: req.code='"+requestMap.get("code").toString()+"'");

		DBResult		rs		=	DBManager.getSimpleResultSet(conn);
		StringBuffer	js	=	new StringBuffer();

		rs.open();
		rs.executeQuery(logger, template[0]);
		
		js.append("self._code").append(requestMap.get("code").toString()).append(" = new Array('").append(template[1]).append("','").append(template[2]).append("','").append(template[3]).append("','").append(template[4]).append("','").append(template[5]).append("');\n");
		while (rs.next())
		{
			for (int i = 1 ; i <= rs.getColumnCount() ; i++)
			{
				js.append("self._code").append(requestMap.get("code").toString()).append(".push('").append(com.acer.util.Utility.jsStr(rs.getString(i))).append("');\n");
			}
		}
		rs.close();		
		out.clear();
		out.println(js.toString());
	}
	catch(Exception ex)
	{
		logLevel = 5;
		
		logger.append(ex);
		pageEx  = ex;
	}
	finally
	{
		if (conn != null)
 		{
			conn.close();
		}
			
		logger.log(logLevel);
		
		requestMap	=	null;
		logger		=	null;
	}

	if (pageEx != null)
	{
		throw pageEx;
	}
%>
<%@include file="/framework/utility/PhraseUtil.jsp"%>