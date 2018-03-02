<%@page contentType="text/html;charset=MS950"%>
<%@page import="java.util.*,java.io.*,com.sertek.sys.sys_User,com.acer.util.*,com.acer.log.*,com.acer.form.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
	int		    logLevel	=	0;
	HashMap		requestMap	=	null;
	MyLogger	logger		=	null;
	sys_User	sysUser		=	null;

	try
	{
		requestMap	= FormUtil.getRequest(request);
		sysUser 	= (com.sertek.sys.sys_User) session.getAttribute("sys.User");
		logger		= new MyLogger(request.getRequestURI().toString());
	
		String	className	=	requestMap.get("ClassName").toString();
		String	methodName	=	requestMap.get("MethodName").toString();
	
		logger.iniUserInfo(sysUser.getRemoteAddr()+" "+sysUser.readString("USER","S_USRID")+" "+sysUser.readString("USER","S_USRNM"));
		logger.append("<font color='orange'>[AJAX]</font>Page: 'InvokeJava.jsp' Data: ClassName='"+Utility.checkNull(requestMap.get("ClassName"),"")+"' MethodName='"+Utility.checkNull(requestMap.get("MethodName"),"")+"' MetArgsType='"+Utility.checkNull(requestMap.get("MetArgsType"),"")+"' MetArgsValue='"+Utility.checkNull(requestMap.get("MetArgsValue"),"")+"'");

		out.clear();

		if (requestMap.get("MetArgsType") != null)
		{
			String[]	tmpType			=	Utility.split(requestMap.get("MetArgsType").toString(), "?”¼");
			String[]	tmpValue		=	Utility.split(requestMap.get("MetArgsValue").toString(), "?”¼");
			Class[]		metArgsType		=	new Class[tmpType.length];
			Object[]	metArgsValue	=	new Object[tmpType.length];
	
			for (int i = 0; i < tmpType.length; i++)
			{
				metArgsType[i]	=	mapClass(tmpType[i]);
				//System.out.println("tmpType["+i+"] = "+tmpType[i]);
				metArgsValue[i]	=	tmpValue[i];
				//System.out.println("tmpValue["+i+"] = "+tmpValue[i]);
			}
			out.print(DynamicLoader.staticInvoke(className, methodName, metArgsType, metArgsValue));
		}
		else
		{
			System.out.println("MethodName = "+ requestMap.get("MethodName").toString());
			out.print(DynamicLoader.staticInvoke(className, methodName));
		}
	}
	catch(Exception ex)
	{
		StringWriter	strErr	=	new StringWriter();
		ex.printStackTrace(new PrintWriter(strErr));
		
		logger.iniUserInfo(sysUser.getRemoteAddr()+" "+sysUser.readString("USER","S_USRID")+" "+sysUser.readString("USER","S_USRNM"));

		logger.append("<font color='orange'>[AJAX]</font>" + strErr.toString());
		
		logLevel = 5;
	}
	finally
	{
		logger.log(logLevel);

		requestMap	=	null;
		logger		=	null;
	}
%>

<%!
	public Class mapClass(String TypeName)
	{
		if (TypeName.equals("String"))
			return String.class;
		else if (TypeName.equals("Object"))
			return Object.class;
		else if (TypeName.equals("Integer"))
			return Integer.class;
		else if (TypeName.equals("Double"))
			return Double.class;
		else if (TypeName.equals("Long"))
			return Long.class;
		else
			return null;
	}
%>
