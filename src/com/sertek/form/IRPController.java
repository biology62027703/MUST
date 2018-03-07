package com.sertek.form;

import com.sertek.sys.sys_User;
import com.sertek.table.*;
import com.sertek.util.CryptoUtil;
import com.sertek.util.CheckObject;
import com.sertek.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.sertek.ibatis.util.SqlDBUtility;
import com.sertek.util.Base64Decoder;

public class IRPController extends AbstractCommandController {
	
	private SqlDBUtility sqlDBUtility;

	public SqlDBUtility getSqlDBUtility() {
		return sqlDBUtility;
	}

	public void setSqlDBUtility(SqlDBUtility sqlDBUtility) {
		this.sqlDBUtility = sqlDBUtility;
	}	
	
	public IRPController(){
		setCommandClass(HashMap.class);
	}
	
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object form, BindException arg3) throws Exception 
	{		
		String rowid = CryptoUtil.decrypt(request.getParameter("para").toString());
		HashMap ht = new HashMap();
		ht.put("rowid", rowid);
		
		List ls = this.sqlDBUtility.queryForList("C60_ATT.queryByRowid", ht);
		if( ls.size()>0 ) {			
			C60_ATT att = (C60_ATT)ls.get(0);
			String filename = att.getS_filenm();
			String name = att.getS_file();
			
			File file = new File(filename);   
		    if (file.exists()) {  		    	
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
		            response.setStatus(900);
		        }    
		    }else{
		    	response.setStatus(900);
		    }
			return null;
		}else {
			response.setStatus(900);
			return null;
		}
	}
}
