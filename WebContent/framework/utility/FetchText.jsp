<%@page import="com.acer.util.*, com.acer.apps.*, com.acer.form.*, com.acer.log.*, com.acer.file.*, java.sql.*, java.util.*, java.io.*, com.sertek.sys.sys_User" contentType="text/html;charset=MS950"%>

<%
	response.setHeader("Pragma",		"no-cache");
	response.setHeader("Cache-Control",	"no-cache");
	response.setDateHeader("Expires",	0);
	response.setContentType("text/html; charset=Big5");

	int    		this_logLevel	=	0;
	MyLogger	this_logger		=	null;
	HashMap		this_requestMap	=	null;
	sys_User	this_sysUser	=	null;
	Exception	this_pageEx		=	null;
	
	try
	{
		this_sysUser = (com.sertek.sys.sys_User) session.getAttribute("sys.User");
		this_logger	= new MyLogger(request.getRequestURI().toString());
		this_logger.iniUserInfo(this_sysUser.getRemoteAddr()+" "+this_sysUser.readString("USER","S_USRID")+" "+this_sysUser.readString("USER","S_USRNM"));
		this_requestMap	=	FormUtil.getRequest(request);

		if (Utility.isEmpty(this_requestMap.get("FILE")))
		{
			out.println("無效的檔案名稱");
		}
		else
		{
			File f = new File(this_requestMap.get("FILE").toString());

			if (!f.exists())
			{
				out.println("無法開啟檔案:" + this_requestMap.get("FILE").toString());
			}
			else
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				PrintWriter	pw = response.getWriter();
		
				response.resetBuffer();

				if (Utility.isEmpty(this_requestMap.get("EXPORT")))
				{
					pw.println("<PRE>");
				}
				else
				{
					response.setContentType("Content-Type: application/force-download");
					response.setHeader("Content-Length",Long.toString(f.length()));
					response.setHeader("Content-disposition","attachment; filename="+FileUtil.getFileName(this_requestMap.get("FILE").toString()));
				}
				char[] buf = new char[1024];
				int nRead;
				while( (nRead=br.read(buf)) != -1 )
				{
					pw.write(buf,0,nRead);
				}
				br.close();
				if (Utility.isEmpty(this_requestMap.get("EXPORT")))
				{
					pw.println("</PRE>");
				}
				pw.flush();
				pw.close();
				response.setStatus( response.SC_OK );
				response.flushBuffer();
				_jspxFactory = null;
			}
		}
	}
	catch(Exception ex)
	{
		this_logLevel	=	5;
		
		this_logger.append(ex);
		out.println("<!--開啟檔案時發生例外"+ex.toString()+"-->");
		//this_pageEx = ex;
	}
	finally
	{
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