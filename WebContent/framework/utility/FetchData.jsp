<%@page contentType="text/html; charset=MS950"%>
<%@page import="com.acer.util.*,
				com.acer.db.query.*,
				com.acer.form.*,
				com.acer.log.*,
				com.acer.db.*,
				com.acer.encode.*,
				com.sertek.sys.sys_User,
				java.sql.*,
				java.util.*,
				java.io.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8"); 
	
	int		    logLevel	=	0;
	MyLogger	logger		=	null;
	Connection	conn		=	null;
	HashMap		requestMap	=	null;
	sys_User	sysUser		=	null;
	Exception	pageEx		=	null;
	String		sql			=	"";
	
	try
	{
		sysUser = (com.sertek.sys.sys_User) session.getAttribute("sys.User");
		if (sysUser == null)
		{ 
			out.clear();
			out.println("var __VAR_RS__=false;");
			return;
		}		

		logger	=	new MyLogger(request.getRequestURI().toString());
		logger.iniUserInfo(sysUser.getRemoteAddr()+" "+sysUser.readString("USER","S_USRID")+" "+sysUser.readString("USER","S_USRNM"));
	
		conn		=	DBManager.getConnection("ORCL");
		requestMap	=	FormUtil.getRequest(request);

		sql			=	Base64.decode(Utility.checkNull(requestMap.get("sql").toString()));
		logger.append("<font color='orange'>[AJAX]</font>Page: 'FetchData.jsp' Data: req.md5='"+Utility.checkNull(requestMap.get("ignore"),"")+"' sql.md5='"+MD5.encode(sql)+"'");

		if (!sql.equals("") && requestMap.get("ignore") != null && MD5.encode(sql).equals(requestMap.get("ignore").toString()))
		{
			DBResult		rs		=	DBManager.getSimpleResultSet(conn);
			StringBuffer	result	=	new StringBuffer();
	
			rs.open();
			rs.executeQuery(logger, sql);
			
			while (rs.next())
			{
				result.append("__VAR_HT__=new __OBJ_HT__();");
				
				for (int i = 1 ; i <= rs.getColumnCount() ; ++i)
				{
					result.append("__VAR_HT__.put('" + rs.getColumnName(i) + "','" + Utility.jsStr(rs.getString(rs.getColumnName(i))) + "');");					
				}
				result.append("__VAR_RS__.push(__VAR_HT__);");
			} 
			
			out.clear();
			if (result.length() > 0)
			{
				result.insert(0,"var __VAR_RS__=new Array();var __OBJ_HT__=Hashtable;var __VAR_HT__=null;"); 
				out.println(result.toString());
			}
			else
			{
				out.println("var __VAR_RS__=new Array();");
			}
		}
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