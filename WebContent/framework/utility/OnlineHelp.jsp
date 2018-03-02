<%@ page import="com.sertek.util.*, com.sertek.db.*, com.sertek.db.query.*, com.sertek.apps.*, com.sertek.form.*, com.sertek.log.*,	java.sql.*, java.util.*, java.io.*, com.sertek.sys.sys_User"%>
<%
	response.setHeader("Pragma",		"no-cache");
	response.setHeader("Cache-Control",	"no-cache");
	response.setDateHeader("Expires",	0);

	int    		this_logLevel	=	0;
	MyLogger	this_logger		=	null;
	Connection	this_conn		=	null;
	HashMap		this_requestMap	=	null;
	sys_User	this_sysUser	=	null;
	Exception	this_pageEx		=	null;
	
	try
	{
		this_sysUser = (com.sertek.sys.sys_User) session.getAttribute("sys.User");
		this_logger	= new MyLogger(request.getRequestURI().toString());
		this_logger.iniUserInfo(this_sysUser.getRemoteAddr()+" "+this_sysUser.readString("USER","S_USRID")+" "+this_sysUser.readString("USER","S_USRNM"));
		this_conn		=	DBManager.getConnection("ORCL");
		this_requestMap	=	FormUtil.getRequest(request);

		Vector vtData = new Vector();
	
		getContent(vtData, this_conn, this_requestMap, this_logger);
	
		if (vtData.size() > 0)
		{
			response.sendRedirect(Utility.getHashString(vtData.get(0),"ARGVL"));
		}
		else
		{
%>
<html>
<head>
	<meta http-equiv='Content-Type' content='text/html; charset=Big5' />
	<script language="JavaScript">
		window.onload = function()
		{
			alert("尚未完成求助網頁設定!");
			top.close();
		}
	</script>
</head>
</html>
<%
		}
	}
	catch(Exception ex)
	{
		this_logger.append(ex);
		this_pageEx = ex;
	}
	finally
	{
		if (this_conn != null)
		{
			try
			{
				this_conn.close();
			} catch(Exception ex) {}		
		}
		
		if (this_logger != null)
		{
			this_logger.log(this_logLevel);
		}

		this_requestMap	=	null;
		this_logger		=	null;
	}
	
	if (this_pageEx != null)
	{
		throw this_pageEx;
	}
%>

<%!
private	void getContent(Vector data, Connection conn, HashMap req, MyLogger logger) throws Exception
{
	DBResult	rs	=	null;
	Exception	ex	=	null;
	String		sql	= 	"";
		
	try
	{
        rs	=	DBManager.getSimpleResultSet(conn);

		sql	 = "SELECT ARGVL FROM L.S08 WHERE PRGID='ONLINEHELP' AND ARGNM='求助連結' AND ROWNUM=1";

		rs.open();
		rs.executeQuery(logger, sql);

		if (rs.next())
		{
			HashMap	content	= new HashMap();

			for (int i = 1 ; i <= rs.getColumnCount() ; i++)
			{
				content.put(rs.getColumnName(i), rs.getString(i));
			}
			
			data.add(content);
		}
	}
	catch (Exception e)
	{
		ex = e;
	}
	finally
	{
		if (rs != null)
		{
			rs.close();
		}
	}
	
	if (ex != null)
	{
		throw ex;
	}
}
%>