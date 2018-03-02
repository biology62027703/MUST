<%@page import="java.io.*"%>
<script language="javascript" src="../script/ERROR.js"></script>
<%!

//2006/03/06 replace to v.15          
	public void Error(javax.servlet.jsp.JspWriter out,String error) {
		String temp = "";
		try{
			try {
				int i = Integer.parseInt(error);
				temp = "";
			} catch(NumberFormatException e) {
				temp = error;
				error = "0";
			}
			
			out.println("<SCRIPT LANGUAGE=\'JavaScript\'>");
			out.println("<!--");
			out.println("db_display('" + error + "','" + temp + "');");
			out.println("//-->");
			out.println("</SCRIPT>");
		} catch(IOException e) {
		
		}
	}

	public void dbError(javax.servlet.jsp.JspWriter out,String index,String str) {
		try{
			out.println("<SCRIPT LANGUAGE=\'JavaScript\'>");
			out.println("<!--");
			out.println("db_display('" + index + "','" + str + "');");
			out.println("//-->");
			out.println("</SCRIPT>");
		} catch(IOException e) {
		
		}
	}
	
	public void Error(javax.servlet.jsp.JspWriter out,String error,String framename) {
		String temp = "";
		try{
			try {
				int i = Integer.parseInt(error);
				temp = "";
			} catch(NumberFormatException e) {
				temp = error;
				error = "0";
			}
			
			out.println("<SCRIPT LANGUAGE=\'JavaScript\'>");
			out.println("<!--");
			out.println("assignErrmsg('" + framename + "','" + error + "','" + temp + "');");
			out.println("//-->");
			out.println("</SCRIPT>");
		} catch(IOException e) {
		
		}
	}
%>