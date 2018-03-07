/*
 * *************************************************************************************************
 * Sertek, Inc. CONFIDENTIAL AND PROPRIETARY SOURCE CODE OF Sertek INC. Copyright (c) 2000
 * Sertek, Inc. All Rights Reserved. Use of this Source Code is subject to the terms of
 * the applicable license agreement from Sertek, Inc. The copyright notice(s) in this
 * Source Code does not indicate actual or intended publication of this Source Code. File
 * Name : ConPool.java Author : Felix Lin Created Date : 2001/1/27 Modified By : wangchi
 * wang Last Modified : 2004/2/03 Description : This program is a class of Connction Pool
 * ****************************************************************************************************
 */
package com.sertek.db;

import com.sertek.sys.sys_cfg;
import com.sertek.util.util_IniFile;
import java.io.File;
import java.sql.*;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServlet;
import javax.sql.*;

public final class ConPool extends HttpServlet {

	public ConPool() {
	}

	public static void Init() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds[0] = (DataSource) envContext.lookup("jdbc/ORCL");
		} catch (Exception e) {
			ds[0] = null;
			e.printStackTrace();
		}
		System.out.println("使用 TOMCAT Connection Pool ...");
	}

	public static Connection getConnection() throws SQLException {
		return getConnection(0);
	}

	public static Connection getConnection(int i) throws SQLException {
		Connection connection = null;

		try {
			connection = ds[i].getConnection();
		} catch (Exception sqlexception) {
			System.out.println("ConPool.getConnection 函數發生錯誤:"
					+ sqlexception.toString());
			throw new SQLException(sqlexception.toString());
		}
		return connection;
	}

	private static DataSource ds[] = new DataSource[1];

	static {
		Init();
	}

}