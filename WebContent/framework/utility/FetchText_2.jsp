<%@page import="com.acer.util.*, com.acer.apps.*, com.acer.form.*, com.acer.log.*,	java.sql.*, java.util.*, java.io.*, com.sertek.sys.sys_User" contentType="text/html;charset=MS950"%>

<%
	response.setHeader("Pragma",		"no-cache");
	response.setHeader("Cache-Control",	"no-cache");
	response.setDateHeader("Expires",	0);
	response.setContentType("text/html; charset=Big5");

	HashMap		this_requestMap	=	null;
	Exception	this_pageEx		=	null;
	
	try
	{
		this_requestMap	=	FormUtil.getRequest(request);
		if (Utility.isEmpty(this_requestMap.get("FILE")))
		{
			out.println("無法開啟檔案");
		}
		else
		{
			File f = new File(this_requestMap.get("FILE").toString());
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
			PrintWriter	pw = response.getWriter();

			//response.reset();
			response.resetBuffer();
			//response.getOutputStream().write("<PRE>".getBytes(),0,5);
			pw.println("<PRE>");
			byte[] buf = new byte[1024];
			int nRead;
			while( (nRead=in.read(buf)) != -1 )
			{
				//response.getOutputStream().write(buf,0,nRead);
				pw.print(new String(buf,0,nRead,"MS950"));
			}
			in.close();
			//response.getOutputStream().write("</PRE>".getBytes(),0,6);
			pw.println("</PRE>");
			response.setStatus( response.SC_OK );
			response.flushBuffer();
			_jspxFactory = null;
		}
	}
	catch(Exception ex)
	{
		out.println("<!--開啟檔案時發生例外:"+ex.toString()+"-->");
	}
	finally
	{
		this_requestMap	=	null;
	}
%>