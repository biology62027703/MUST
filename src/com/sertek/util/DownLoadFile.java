package com.sertek.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
public class DownLoadFile extends HttpServlet {

	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO �۰ʲ��ͤ�k Stub
		super.doGet(arg0, arg1);
	}

	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO �۰ʲ��ͤ�k Stub
		super.doPost(arg0, arg1);
	}



	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO �۰ʲ��ͤ�k Stub
		//super.service(arg0, arg1);
		
//		 TODO �۰ʲ��ͤ�k Stub
		//super.service(arg0, arg1);
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
	    String filename = request.getParameter("file");  
	    String name = request.getParameter("filename");
	    String alert = request.getParameter("alert");
	    String errmsg = request.getParameter("errmsg");

	    if( errmsg != null)
	    {
	    	PrintWriter out = response.getWriter();
    		out.println("<script>");			    		
    		out.println("alert('" + util.StrTran(errmsg,"\\","\\\\") + "');");
    		out.println("</script>");
	    }
	    else
	    {
		    if (filename!=null){
			    File file = new File(filename);   
			    if (file.exists()) {  
			    	if (name==null)
			    		name= file.getName();
			    	
			 		if (request.getHeader("User-Agent").indexOf("MSIE 5.5") != -1) {
			    		response.setHeader("Content-Disposition","filename="
			            + new String( name.getBytes("Big5"), "ISO8859_1" ) );
			         }else {
						response.addHeader( "Content-Disposition", "attachment;filename="
			          + new String( name.getBytes("Big5"), "ISO8859_1" ) );
			       }     
			        try {  
			        	
						byte[] buffer = new byte[8192];
						 
			            OutputStream output = response.getOutputStream();   
			            InputStream in = new FileInputStream(file);   
			            int c = in.read(buffer);   
			            while (c != -1) {   
			                output.write(buffer,0,c);   
			                c = in.read(buffer);   
			            }   
			            in.close();   
			            output.close(); // remember to close the OutputStream   
			        } catch (IllegalStateException e) {   
			            System.out.println(e.getMessage());   
			        }    
			    } else {   
			    	System.out.println("�ɮ�"+filename+"���s�b");
			    	
			    	if (alert != null && alert.toLowerCase().equals("true")){
			    		PrintWriter out = response.getWriter();
			    		out.println("<script>");			    		
			    		out.println("alert('" + util.StrTran(filename,"\\","\\\\") + " �ɮפ��s�b');");
			    		out.println("</script>");
			    	}
			    }
		    }
	    }
	}

}
