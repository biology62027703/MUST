package com.sertek.form;

	import java.io.*;
	import javax.servlet.*;
	import javax.servlet.http.*;
	import sun.misc.BASE64Encoder.*;
	 
	public class LDAP_AUTH_AD extends HttpServlet implements Filter
	{
	  
	  public void init(FilterConfig filterConfig)
	  {
	    
	  }
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,ServletException
	  {
	    //:以下:這一段紀錄當user連上server時,將user的windows上的account show 出來---
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    HttpServletResponse httpResponse = (HttpServletResponse) response;
	    //String auth = request.getHeader("Authorization");
	    String auth = httpRequest.getHeader("Authorization");
	    if (auth == null) 
	    {
	      httpResponse.setStatus(httpResponse.SC_UNAUTHORIZED);
	      httpResponse.setHeader("WWW-Authenticate", "NTLM");
	      return;
	    }
	 
	    if (auth.startsWith("NTLM ")) 
	    { 
	      byte[] msg = new sun.misc.BASE64Decoder().decodeBuffer(auth.substring(5));
	      int off = 0, length, offset;
	      String s;
	 
	      if (msg[8] == 1) // first step of authentication
	    { 
	      off = 18;
	      // this part is for full hand-shaking, just tested, didn't care about result passwords
	      byte z = 0;
	      byte[] msg1 = {(byte)'N', (byte)'T', (byte)'L', (byte)'M', (byte)'S', (byte)'S', (byte)'P', z,
	      (byte)2, z, z, z, z, z, z, z,
	      (byte)40, z, z, z, (byte)1, (byte)130, z, z,
	      z, (byte)2, (byte)2, (byte)2, z, z, z, z, // this line is 'nonce'
	      z, z, z, z, z, z, z, z};
	      // remove next lines if you want see the result of first step
	      httpResponse.setStatus(httpResponse.SC_UNAUTHORIZED);
	      httpResponse.setHeader("WWW-Authenticate", "NTLM " + new sun.misc.BASE64Encoder().encodeBuffer(msg1).trim());
	      return;
	    } 
	    else if (msg[8] == 3) // third step of authentization - takes long time, nod needed if zou care only for loginname
	    { 
	      off = 30;
	      length = msg[off+17]*256 + msg[off+16];
	      offset = msg[off+19]*256 + msg[off+18];
	      s = new String(msg, offset, length);
	      //out.println(s + " ");
	    } 
	    else
	    return;
	    length = msg[off+1]*256 + msg[off];
	    offset = msg[off+3]*256 + msg[off+2];
	    s = new String(msg, offset, length);
	    //out.println(s + " ");
	    length = msg[off+9]*256 + msg[off+8];
	    offset = msg[off+11]*256 + msg[off+10];
	    s = new String(msg, offset, length);
	    System.out.println("Welcome," + " " + s + " ");
	    }    
	    //:以上:這一段紀錄當user連上server時,將user的windows上的account show 出來---
	    System.out.println("HELLO FILTER");
	    chain.doFilter(request,response);
	  }
	  public void destroy()
	  {
	    
	  }
	}

