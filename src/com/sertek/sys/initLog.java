package com.sertek.sys;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class initLog implements ServletContextListener{

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			init(arg0.getServletContext());
		} catch (ServletException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void init(ServletContext  servletContext) throws ServletException, SQLException{ 
		System.out.println("-------------------初始化「web service」系統資訊參數及Log4J--------------------");
		
		String prefix = servletContext.getRealPath("/");
		char cha = prefix.charAt( prefix.length()-1 );
		
		if ( cha!='\\' && cha!='/' ){
			prefix += File.separator;
		}
		
		System.out.println(Project.WEBROOT_KEY + ", " + prefix);
		System.setProperty(Project.WEBROOT_KEY, prefix);
		
		//System.out.println("fs_root_dir = " + Project.getFSRoot());
		
		String[] dir = {
				prefix + "logs", 
				Project.getAttDir(),
				Project.getCaseDataDir(),
				Project.getCaseDataPdfDir(),
				Project.getPayDataDir(),
				Project.getY6XDataDir(),
				Project.getMailErrTxtDir()
			};
		
		for(int i=0;i<dir.length;i++) {
			File f = new File(dir[i]);
			if( !f.exists() )
				f.mkdirs();
			f = null;
		}
		
		StaticObj.iniData();
	}
}