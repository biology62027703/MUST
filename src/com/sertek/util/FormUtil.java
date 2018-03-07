package com.sertek.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.sertek.db.DBUtility;
import com.sertek.table.BaseTable;

public class FormUtil {

	public static HashMap RequetToHashMap(HttpServletRequest request) throws UnsupportedEncodingException {
		HashMap ht = new HashMap();
		CheckObject ch = new CheckObject();
		
		boolean isEncode = request.getParameter("isEncode") != null;

		Enumeration allname = request.getParameterNames();
		while (allname.hasMoreElements()) {
			String key = (String) allname.nextElement();
			if( key.indexOf("[]")==-1 ) {			
				String value = (String) ch.checkNull(request.getParameter(key), "");
				if( isEncode )
					value = java.net.URLDecoder.decode(value,"UTF-8");
				ht.put(key, value);
			}else{
				String[] value = request.getParameterValues(key);
				if( isEncode ){
					for(int i=0;i<value.length;i++)
						value[i] = java.net.URLDecoder.decode(value[i],"UTF-8");
				}
				ht.put(key.substring(0, key.length()-2), value);
			}
		}
		return ht;
	}
	
	public static BaseTable HashMapToTable(HashMap form, BaseTable table) {
		Iterator iterator = form.keySet().iterator();
		while (iterator.hasNext()) {
			try {
				String key = iterator.next().toString();
				Object value = form.get(key);

				String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1).toLowerCase();

				Field field = table.getClass().getDeclaredField(key.toLowerCase());

				// type�@�ˤ~set
				if (value.getClass().equals(field.getType())) {
					Class[] paramType = { String.class };
					Method setMethod = table.getClass().getMethod(methodName, paramType);

					Object[] paramValue = { value };
					setMethod.invoke(table, paramValue);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return table;
	}
	
	public static HashMap converResultMap(HashMap resultMap) {
		CheckObject check = new  CheckObject();
		HashMap parameterMap = new HashMap();

		Iterator iterator = resultMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			String value = check.checkNull(resultMap.get(key), "").toString();

			parameterMap.put(key.toLowerCase(), value);
		}
		return parameterMap;
	}
	
	public static DBUtility getDBConnection() throws ServletException, SQLException {
		org.springframework.context.ApplicationContext ctx = new org.springframework.context.support.FileSystemXmlApplicationContext("file:" + com.sertek.sys.Project.getDataSourceXmlPath());
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.getBean("dataSource_diva");
		DBUtility db = new DBUtility();
		db.openConnection(ds.getConnection());
		return db;
	}
	
	public static DBUtility getDBConnection_MUST_WEB() throws ServletException, SQLException {
		org.springframework.context.ApplicationContext ctx = new org.springframework.context.support.FileSystemXmlApplicationContext("file:" + "D:\\Tomcat8\\webapps\\MUST\\WEB-INF\\dataSource.xml");
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.getBean("dataSource_MUST_WEB");
		DBUtility db = new DBUtility();
		db.openConnection(ds.getConnection());
		return db;
	}
	
}