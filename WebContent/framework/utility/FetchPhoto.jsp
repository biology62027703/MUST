<%@ page import="com.acer.util.*, com.acer.db.*, com.acer.db.query.*, com.acer.apps.*, com.acer.form.*, com.acer.log.*,	java.sql.*, java.util.*, java.io.*, com.sertek.sys.sys_User"%>

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

		if (!Utility.isEmpty(this_requestMap.get("RID")))
		{
			Vector vtData = new Vector();
		
			getContent(vtData, this_conn, this_requestMap, this_logger);
		
			if (vtData.size() > 0)
			{
				String	targetPath	=	"";
				String	fileName	=	"";
				
				if ( Utility.checkNull(this_requestMap.get("PID")).equals("CL1A10") )
				{
					targetPath	=	APConfig.getProperty("PHOTO_PATH") + "//" + this_sysUser.readString("USER","s_DptCd") + "//" + Utility.getHashString(vtData.get(0),"UPDT").substring(0,3) + "//";
					fileName	=	Utility.getHashString(vtData.get(0),"FILENM");
				}

				if ( Utility.checkNull(this_requestMap.get("PID")).equals("CL1P03") )
				{
					targetPath	=	APConfig.getProperty("PHOTO_PATH") + "//VOL//";
					fileName	=	Utility.getHashString(vtData.get(0),"KD")+"-"+Utility.getHashString(vtData.get(0),"VOLNO")+".jpg";
				}
		
				File f = new File(targetPath+fileName);
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
		
				response.reset();
				response.resetBuffer();

				response.setContentType("image/jpeg");
				//response.setContentLength((int)f.length());
				response.setHeader("Content-Length",Long.toString(f.length()));
				response.setHeader("Content-disposition","attachment; filename="+ Utility.getHashString(vtData.get(0),"NM"));

				byte[] buf = new byte[1024];
				int nRead;
				while( (nRead=in.read(buf)) != -1 )
				{
					response.getOutputStream().write(buf,0,nRead);
				}
				in.close();
				response.setStatus( response.SC_OK );
				response.flushBuffer();
				_jspxFactory = null;
			}
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

		if (Utility.checkNull(req.get("PID")).equals("CL1A10"))
		{
			sql	 = "SELECT UPDT,NM,FILENM ";
			sql += "FROM L.PHOTO ";
			sql += "WHERE ROWID = '"+Utility.dbStr(req.get("RID"))+"' ";
		}

		if (Utility.checkNull(req.get("PID")).equals("CL1P03"))
		{
			sql	 = "SELECT KD,VOLNO ";
			sql += "FROM L.C51 ";
			sql += "WHERE ROWID = '"+Utility.dbStr(req.get("RID"))+"' ";
		}

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