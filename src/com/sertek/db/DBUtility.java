/*
 **************************************************************************************************
 *                         		      Sertek, Inc.
 *
 * 	CONFIDENTIAL AND PROPRIETARY SOURCE CODE OF Sertek INC.
 *
 * 	Copyright (c) 2000 Sertek, Inc. All Rights Reserved.
 * 	Use of this Source Code is subject to the terms of the applicable
 * 	license agreement from Sertek, Inc.  The c opyright notice(s) 
 * 	in this Source Code does not indicate actual or intended publication 
 * 	of this Source Code.
 *
 *	File Name     : DBUtility.java    //jbcd 2.0
 *	Author        : Felix Lin              	
 *	Created Date  : 2001/1/27           	     
 *	Modified By   : Felix Lin              	
 *	Last Modified : 2001/2/07
 *	Description   : This program is a class of DBconnection
 *	Modified By   : Peric
 *	Last Modified : 2003/9/19 Peric �W�[ doSqlQuery() transCharset() transCharset4db2Client() transCharset4Client2db()�� Method,
 *                  �@�֭ק�UMethod�ҨϥΪ���X�覡 getString()���~->getString()�nthrows Exception
 *
 *                  2005.06.22 Peric public TPDEH0940003 �W�[ Method Hashtable getOneRow(String SqlStatement)
 ***************************************************************************************************** 
 */
/*
 ----------------------------------------------------------------------------------------
 ���D�渹�GBug #1668 - JUDCY0950007  
 �ק�K�n�G���R��KSDMH0940006���ճ�����d�ߡu�_%�v�A��x�B�h�ҷ|�d�줧���D�A�g�d�ҵ��G�p�U�G
 �u�_�v��ASC�X�GAC 5F
 �u�x�v��ASC�X�GAC 78
 �u�h�v��ASC�X�GAC 68
 �� 5F �����u�A�bSQL ���N����@�r���A�G�x�B�h�ҷ|��ܡC
 �ק���ޥd�d�ߵ{���blike�ɭn���B�z�r��,welkin���g�n��function
 �ק�H���Glewiswang                 
 �ק����G0960614             
 ��s�����GV9604
 ----------------------------------------------------------------------------------------
 */
package com.sertek.db;

import java.sql.*;
import javax.servlet.*;
import java.util.Vector;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.*;
import com.sertek.sys.*;
import com.sertek.util.*; //import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Hashtable;

public class DBUtility {

	private ConPool conPool;
	private String jdbcURL;
	private String username;
	private String password;
	private Connection conn;
	private int size = 0;
	private ResultSet rs = null;
	private Statement stmt;
	private PreparedStatement pstmt = null;

	private static String dbCharset = null;
	// private static String clientCharset = "UTF8";
	private static String clientCharset = "UTF-8";
	// private static String clientCharset = "Big5";
	private static boolean _charset = false; // to see whether char need
												// transfer

	// PLSQL �I�sPLSQL�һݪ��ܼ�

	private String _PLSQLStr, _CutStr;
	private Vector _TypeStr, _ParamStr, _InOutStr, TraceStack;
	private int CursorPos;
	private CallableStatement cstmt;

	static {
		/*
		dbCharset = "ISO8859_1";
		dbCharset = (String) sys_cfg.getValue("db_charset");
		if (dbCharset == null)
			dbCharset = "ISO8859_1";
		dbCharset = dbCharset.toUpperCase();
		if (dbCharset.indexOf("8859") != -1)
			dbCharset = "ISO8859_1";
		if (!dbCharset.equals(clientCharset))
			_charset = true;
	*/
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      DBUtility  Constructor&lt;br&gt; 
	 *      �Ѽ�:�L  &lt;br&gt;
	 *      �Ǧ^:�L  &lt;br&gt;
	 *      ����:�إ߸�Ʈw���s���غc�l&lt;br&gt;
	 *</pre>
	 */
	public DBUtility() {
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L  &lt;br&gt;
	 *      �Ǧ^:�L  &lt;br&gt;
	 *      ����:�}�� Connection,�ϥβ�0 ��DataSource��conpool &lt;br&gt;
	 *</pre>
	 */
	public void openConnection() throws ServletException {
		openConnection(0);
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:int conPool_num:���w DataSource ���էO&lt;br&gt;
	 *      �Ǧ^:�L  &lt;br&gt;
	 *      ����:�}�� Connection,�ϥΫ��w��DataSource��conpool ,DataSource ���էO��conPool_num�ǤJ,&lt;br&gt;
	 *           �d���0 &tilde; 3 &lt;br&gt;&lt;br&gt;
	 *</pre>
	 */
	public void openConnection(int conPool_num) throws ServletException {
		try {
			if (conn != null && !conn.isClosed())
				throw new SQLException(
						"The connection has been established already.");
			clearResult();

			System.out.println("USE POOLING");
			System.out.println(conPool_num);

			conn = ConPool.getConnection(conPool_num);
			// stmt =
			// conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt = conn.createStatement();

		} catch (SQLException ex) {
			throw new ServletException(ex.toString());
		}
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:int conPool_num:���w DataSource ���էO&lt;br&gt;
	 *      �Ǧ^:�L  &lt;br&gt;
	 *      ����:�}�� Connection,�ϥΫ��w��DataSource��conpool ,DataSource ���էO��conPool_num�ǤJ,&lt;br&gt;
	 *           �d���0 &tilde; 3 &lt;br&gt;&lt;br&gt;
	 *</pre>
	 */
	public void openConnection(Connection Connect) throws ServletException {
		try {
			if (conn != null && !conn.isClosed())
				throw new SQLException(
						"The connection has been established already.");
			clearResult();
			conn = Connect;
			stmt = conn.createStatement();
		} catch (SQLException ex) {
			throw new ServletException(ex.toString());
		}
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:int conPool_num:���w DataSource ���էO&lt;br&gt;
	 *      �Ǧ^:�L  &lt;br&gt;
	 *      ����:�}�� Connection,�ϥΫ��w��DataSource��conpool ,DataSource ���էO��conPool_num�ǤJ,&lt;br&gt;
	 *           �d���0 &tilde; 3 &lt;br&gt;&lt;br&gt;
	 *</pre>
	 */
	public void openConnection(String Inifile) throws Exception {
		try {
			if (conn != null && !conn.isClosed())
				throw new SQLException(
						"The connection has been established already.");
			clearResult();
			try {
				util_IniFile Inif = new util_IniFile(Inifile);

				DriverManager.registerDriver(new OracleDriver());
				System.out.println("jdbc:oracle:thin:@"
						+ Inif.readString("SET", "ORACLE_URL")
						+ Inif.readString("SET", "db_username").trim()
						+ Inif.readString("SET", "db_password").trim());
				conn = DriverManager.getConnection("jdbc:oracle:thin:@"
						+ Inif.readString("SET", "ORACLE_URL"), Inif
						.readString("SET", "db_username").trim(), Inif
						.readString("SET", "db_password").trim());
				stmt = conn.createStatement();

			} catch (Exception e) {
				throw e;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex.toString());
		}

	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L  &lt;br&gt;
	 *      �Ǧ^:�L  &lt;br&gt;
	 *      ����:�M�� DBUtility �����Ȧs�ܼ�&lt;br&gt;
	 *</pre>
	 */
	private void clearResult() throws SQLException {
		if (stmt != null)
			stmt.close();
		stmt = null;
		if (pstmt != null)
			pstmt.close();
		pstmt = null;
		if (rs != null)
			rs.close();
		rs = null;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:�L&lt;br&gt;
	 *      ����:close Connection&lt;br&gt;
	 *</pre>
	 */
	public void closeConnection() throws SQLException {
		if (!conn.getAutoCommit())
			conn.setAutoCommit(true);
		clearResult();
		if (conn == null)
			throw new SQLException("This connection has been closed already.");
		if (conn.isClosed())
			throw new SQLException("This connection has been closed.");
		conn.close();
		conn = null;
		System.out.println("closeConnection");
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:�L&lt;br&gt;
	 *      ����:close Connection&lt;br&gt;
	 *</pre>
	 */
	public boolean isClosed() throws SQLException {
		boolean isClose = true;
		if (conn != null)
			isClose = conn.isClosed();
		return isClose;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *        �Ѽ�:�L&lt;br&gt;
	 *        �Ǧ^:�L&lt;br&gt;
	 *        ����:this class �����̫� closeConnection&lt;br&gt;
	 *</pre>
	 */
	protected void finalize() throws Throwable {
		closeConnection();
	}

	/***************************************************************************************/

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:String SqlStatement :�ǤJ�� SQL �y�k&lt;br&gt;
	 *      �Ǧ^:ResultSet  &lt;br&gt;         
	 *      ����:�d�T��Ʈw���,���G�HResultSet �^��&lt;br&gt;
	 *</pre>
	 */

	public ResultSet doSqlSelect(String SqlStatement) throws SQLException {
		try {
			this.size = 0;
			// if( _charset)
			// SqlStatement=new String( SqlStatement.getBytes(clientCharset) ,
			// dbCharset );
			SqlStatement = transCharset4Client2db(SqlStatement);
			if (rs != null)
				rs.close();
			rs = null;
			rs = stmt.executeQuery(SqlStatement);
		} catch (Exception exception) {
			System.out.println("Exception:  " + exception);
			System.out.println("SqlStatement:  " + SqlStatement);
			exception.printStackTrace();

		}
		return rs;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:1.String SqlStatement :�ǤJ�� SQL �y�k&lt;br&gt;
	 *           2.int columnNum       :�^�Ǭd�߸�ƪ����ƥ�&lt;br&gt;
	 *           3.int pageNum         :���^�ǲĴX��&lt;br&gt;
	 *           4.int rec_per_page    :�C�@����Ƶ��� &lt;br&gt;
	 *           5.boolean is_cut      :�C��record �O�_�H &quot;|&quot; ����&lt;br&gt;
	 *      �Ǧ^:ResultSet   &lt;br&gt;        
	 *      ����:�d�T��Ʈw���,�i�H���w���d�߲ĴX��,�C�@�����X�����,���G�HVector �^��&lt;br&gt;
	 *</pre>
	 */

	public Vector doSqlSelect(String SqlStatement, int columnNum, int pageNum,
			int rec_per_page, boolean is_cut) {
		Vector vector = new Vector();
		try {
			// if( _charset)
			// SqlStatement=new String( SqlStatement.getBytes(clientCharset) ,
			// dbCharset );
			SqlStatement = transCharset4Client2db(SqlStatement);
			String s = null;
			int start = rec_per_page * (pageNum - 1) + 1;
			int end = rec_per_page * pageNum;
			if (rs != null)
				rs.close();
			rs = null;
			rs = stmt.executeQuery(SqlStatement);
			int j = 0;
			size = 0;
			// rs.last();
			// size=rs.getRow();
			// j=start;
			// rs.absolute(start);
			while (rs.next()) {
				size++;
				if (++j >= start && j <= end)
					if (is_cut) {
						String Str = "";
						for (int k = 1; k <= columnNum; k++) {
							if (end > 1)
								Str = Str + "|";
							s = rs.getString(k);
							// if( (s != null) && ( _charset ) )
							// s = new String( s.getBytes(dbCharset) ,
							// clientCharset );
							// if( s == null) s="";
							s = transCharset4db2Client(s);
							Str = Str + s;

						}

						vector.addElement(Str);
					} else {
						for (int k1 = 1; k1 <= columnNum; k1++) {
							s = rs.getString(k1);
							// if( (s != null) && ( _charset ) )
							// s = new String( s.getBytes(dbCharset) ,
							// clientCharset );
							// if( s == null) s="";
							s = transCharset4db2Client(s);
							vector.addElement(s);
						}
					}
				// if (> end)
				// break;
			}
		} catch (SQLException sqlexception) {
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				System.out.println("SqlStatement:  " + SqlStatement);
				sqlexception = sqlexception.getNextException();
				System.out.println("");
			}
		} catch (Exception exception) {
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return vector;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:1.String SqlStatement :�ǤJ�� SQL �y�k&lt;br&gt;
	 *           2.int columnNum       :�^�Ǭd�߸�ƪ����ƥ�&lt;br&gt;
	 *           3.boolean is_cut      :�C��record �O�_�H &quot;|&quot; ����&lt;br&gt;
	 *      �Ǧ^:ResultSet           &lt;br&gt;
	 *      ����:�d�T��Ʈw���,���G�HVector �^��&lt;br&gt;
	 *</pre>
	 */

	public Vector doSqlSelect(String SqlStatement, int columnNum, boolean is_cut) {
		System.out.println(SqlStatement);
		Vector vector = new Vector();
		try {
			// if( _charset)
			// SqlStatement=new String( SqlStatement.getBytes(clientCharset) ,
			// dbCharset );
			SqlStatement = transCharset4Client2db(SqlStatement);
			String s = null;
			if (rs != null)
				rs.close();
			rs = null;
			size = 0;
			for (rs = stmt.executeQuery(SqlStatement); rs.next();) {
				size++;
				if (is_cut) {
					String Str = "";
					for (int i = 1; i <= columnNum; i++) {
						if (i > 1)
							Str = Str + "|";
						s = rs.getString(i);
						// if( (s != null) && ( _charset ) )
						// s = new String( s.getBytes(dbCharset) , clientCharset
						// );
						// if( s == null) s="";
						s = transCharset4db2Client(s);
						Str = Str + s;
					}

					vector.addElement(Str);
				} else {
					for (int j = 1; j <= columnNum; j++) {
						s = rs.getString(j);
						// if( (s != null) && ( _charset ) )
						// s = new String( s.getBytes(dbCharset) , clientCharset
						// );
						// if( s == null) s="";
						s = transCharset4db2Client(s);
						vector.addElement(s);
					}
				}
			}
		} catch (SQLException sqlexception) {
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				System.out.println("SqlStatement:  " + SqlStatement);
				sqlexception = sqlexception.getNextException();

				System.out.println("");
			}
		} catch (Exception exception) {
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return vector;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *       �Ѽ�:1.String SqlStatement :�ǤJ�� SQL �y�k&lt;br&gt;
	 *       �Ǧ^:int           &lt;br&gt;
	 *       ����:���,��s,�R�����&lt;br&gt;
	 *</pre>
	 */

	public synchronized int doSqlUpdate(String SqlStatement) {
		int i = -1;
		try {
			// if( _charset )
			// SqlStatement = new String(
			// SqlStatement.getBytes(clientCharset),dbCharset);
			//SqlStatement = transCharset4Client2db(SqlStatement);
			i = stmt.executeUpdate(SqlStatement);

		} catch (SQLException sqlexception) {
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				System.out.println("SqlStatement:  " + SqlStatement);
				sqlexception = sqlexception.getNextException();

				System.out.println("");
			}
		} catch (Exception exception) {
			System.out.println("Exception:  " + exception);
			System.out.println("SqlStatement:  " + SqlStatement);
			exception.printStackTrace();
		}
		return i;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:1.String SqlStatement :�ǤJ�� SQL �y�k&lt;br&gt;
	 *      �Ǧ^:boolean           &lt;br&gt;
	 *      ����:���,��s,�R�����,�^��boolean �O�_���\&lt;br&gt;
	 *</pre>
	 */
	public synchronized boolean execute(String SqlStatement) {
		boolean flag = true;
		try {
			flag = stmt.execute(SqlStatement);
		} catch (SQLException sqlexception) {
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				sqlexception = sqlexception.getNextException();
				System.out.println("");
			}
		} catch (Exception exception) {
			System.out.println("Exception:  " + exception);
			System.out.println("SqlStatement:  " + SqlStatement);
			exception.printStackTrace();
		}
		return flag;
	}

	/*
	 * <pre><br> �Ѽ�:�L<br> �Ǧ^:�L<br> ����:doSqlSelect �� doPreparedQuer ����,<br>
	 * �i�H�Q�Φ�function �������� rs ��U�@��<br></pre>
	 */
	public boolean next() throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");
		return rs.next();
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:���L��          &lt;br&gt;
	 *      ����:�P�_�̫�@�������Ū���O�_�� null ��     &lt;br&gt;
	 *</pre>
	 */
	public boolean wasNull() throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");

		return rs.wasNull();
	}

	/******************************** prepareStatement ***************************************/

	/*
	 * <pre><br> �Ѽ�:String SQL :�ǤJ�� SQL �y�k<br> �Ǧ^:�L<br>
	 * ����:�ϥ�prepareStatement��k�d�T�߸�Ʈw���,<BR> ��call prepareStatement �ǤJSQL����,<BR>
	 * �@�@ �Acall doPreparedQuery �����Ʈw�d��<BR> �`�N:�s��I�s prepareStatement <BR> �|�⤧�e��
	 * prepareStatement Object close<BR></pre>
	 */
	public void PreStatement(String SQL) throws Exception {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception ignore) {
			} finally {
				pstmt = null;
			}
		}
		// if( _charset )
		// SQL = new String ( SQL.getBytes(clientCharset),dbCharset);
		SQL = transCharset4Client2db(SQL);
		pstmt = conn.prepareStatement(SQL);
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:ResultSet  &lt;br&gt;         
	 *      ����:��call prepareStatement �ǤJSQL����,&lt;BR&gt;
	 *      �@�@ �Acall ����ư����Ʈw�d��&lt;BR&gt;
	 *           �d�T��Ʈw���,���G�HResultSet �^��&lt;br&gt;
	 *</pre>
	 */
	public ResultSet doPreQuery() throws SQLException {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception ignore) {
			} finally {
				rs = null;
			}
		}
		if (pstmt == null)
			throw new SQLException("Null PrepareStatement");
		rs = pstmt.executeQuery();
		return rs;

	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:1.int columnNum       :�^�Ǭd�߸�ƪ����ƥ�&lt;br&gt;
	 *           2.int pageNum         :���^�ǲĴX��&lt;br&gt;
	 *           3.int rec_per_page    :�C�@����Ƶ��� &lt;br&gt;
	 *           4.boolean is_cut      :�C��record �O�_�H &quot;|&quot; ����&lt;br&gt;
	 *      �Ǧ^:ResultSet   &lt;br&gt;        
	 *      ����:��call prepareStatement �ǤJSQL����,&lt;BR&gt;
	 *      �@�@ �Acall ����ư����Ʈw�d��&lt;BR&gt;
	 *           �d�T��Ʈw���,�i�H���w���d�߲ĴX��,�C�@�����X�����,���G�HVector �^��&lt;br&gt;
	 *</pre>
	 */

	public Vector doPreQuery(int columnNum, int pageNum, int rec_per_page,
			boolean is_cut) {
		Vector vector = new Vector();
		try {
			String s = null;
			int start = rec_per_page * (pageNum - 1) + 1;
			int end = rec_per_page * pageNum;
			if (rs != null)
				rs.close();
			rs = null;
			if (pstmt == null)
				throw new SQLException("Null PrepareStatement");
			rs = pstmt.executeQuery();
			int j = 0;
			size = 0;
			while (rs.next()) {
				size++;
				if (++j >= start && j <= end)
					if (is_cut) {
						String Str = "";
						for (int k = 1; k <= columnNum; k++) {
							if (end > 1)
								Str = Str + "|";
							s = rs.getString(k);
							// if( (s != null) && ( _charset ) )
							// s = new String( s.getBytes(dbCharset) ,
							// clientCharset );
							// if( s == null) s="";
							s = transCharset4db2Client(s);
							Str = Str + s;
						}
						vector.addElement(Str);
					} else {
						for (int k1 = 1; k1 <= columnNum; k1++) {
							s = rs.getString(k1);
							// if( (s != null) && ( _charset ) )
							// s = new String( s.getBytes(dbCharset) ,
							// clientCharset );
							// if( s == null) s="";
							s = transCharset4db2Client(s);
							vector.addElement(s);
						}
					}
			}
		} catch (SQLException sqlexception) {
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				sqlexception = sqlexception.getNextException();
				System.out.println("");
			}
		} catch (Exception exception) {
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return vector;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:1.int columnNum       :�^�Ǭd�߸�ƪ����ƥ�&lt;br&gt;
	 *           2.boolean is_cut      :�C��record �O�_�H &quot;|&quot; ����&lt;br&gt;
	 *      �Ǧ^:ResultSet           &lt;br&gt;
	 *      ����:��call prepareStatement �ǤJSQL����,&lt;BR&gt;
	 *      �@�@ �Acall ����ư����Ʈw�d��&lt;BR&gt;
	 *           �d�T��Ʈw���,���G�HVector �^��&lt;br&gt;
	 *</pre>
	 */

	public Vector doPreQuery(int columnNum, boolean is_cut) {
		Vector vector = new Vector();
		try {
			String s = null;
			if (rs != null)
				rs.close();
			rs = null;
			size = 0;
			if (pstmt == null)
				throw new SQLException("Null PrepareStatement");
			for (rs = pstmt.executeQuery(); rs.next();) {
				size++;
				if (is_cut) {
					String Str = "";
					for (int i = 1; i <= columnNum; i++) {
						if (i > 1)
							Str = Str + "|";
						s = rs.getString(i);
						// if( (s != null) && ( _charset ) )
						// s = new String( s.getBytes(dbCharset) , clientCharset
						// );
						// if( s == null) s="";
						s = transCharset4db2Client(s);
						Str = Str + s;
					}
					vector.addElement(Str);
				} else {
					for (int j = 1; j <= columnNum; j++) {
						s = rs.getString(j);
						// if( (s != null) && ( _charset ) )
						// s = new String( s.getBytes(dbCharset) , clientCharset
						// );
						// if( s == null) s="";
						s = transCharset4db2Client(s);
						vector.addElement(s);
					}
				}
			}
		} catch (SQLException sqlexception) {
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				sqlexception = sqlexception.getNextException();
				System.out.println("");
			}
		} catch (Exception exception) {
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return vector;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L            &lt;br&gt;
	 *      �Ǧ^:int           &lt;br&gt;
	 *      ����:��call prepareStatement �ǤJSQL����,&lt;BR&gt;
	 *      �@�@ �Acall ����ư����Ʈw�d��&lt;BR&gt;
	 *           ���,��s,�R�����&lt;br&gt;
	 *</pre>
	 */
	public int doPreUpdate() throws SQLException {
		int done = 0;
		try {
			if (pstmt == null)
				throw new SQLException("Null PreparedStatement");
			done = pstmt.executeUpdate();
			return done;
		} catch (SQLException sqlexception) {
			return done;
		}
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:1.int parameterIndex  :�Ѽƪ���m  &lt;br&gt;
	 *           2.int sqlType         :�Ѽƪ��κA,defined in java.sql.Types  &lt;br&gt;
	 *      �Ǧ^:�L           &lt;br&gt;
	 *      ����:�M��prepareStatement ���Ѽ�&lt;br&gt;
	 *</pre>
	 */
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		if (pstmt == null)
			throw new SQLException("Null PreparedStatement");
		pstmt.setNull(parameterIndex, sqlType);
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:1.int parameterIndex  :�Ѽƪ���m  &lt;br&gt;
	 *           2.int x               :�Ѽƪ�value  &lt;br&gt;
	 *      �Ǧ^:�L           &lt;br&gt;
	 *      ����:set prepareStatement  �Ʀr�κA���Ѽ�&lt;br&gt;
	 *</pre>
	 */
	public void setInt(int parameterIndex, int x) throws SQLException {
		if (pstmt == null)
			throw new SQLException("Null PrepareStatement");
		pstmt.setInt(parameterIndex, x);
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:1.int parameterIndex  :�Ѽƪ���m  &lt;br&gt;
	 *           2.int x               :�Ѽƪ�value  &lt;br&gt;
	 *      �Ǧ^:�L           &lt;br&gt;
	 *      ����:set prepareStatement  �r��κA���Ѽ�&lt;br&gt;
	 *</pre>
	 */
	public void setString(int parameterIndex, String x) throws Exception {
		if (pstmt == null)
			throw new SQLException("Null PrepareStatement");
		// if( _charset )
		// x = new String(x.getBytes(clientCharset),dbCharset);
		x = transCharset4Client2db(x);
		pstmt.setString(parameterIndex, x);
	}

	/******************************** �I�sPLSQL�{�� ***************************************/

	private void CutString(String SourceStr, Vector LinkVector) { // ���Ѧr����
		int pos, cutLength = _CutStr.length();
		String sTmp = SourceStr;
		System.out.println(sTmp);
		LinkVector.clear(); // �M���쥻�bVector���嵲
		while ((pos = sTmp.indexOf(_CutStr)) >= 0) { // ��X�O�_���ҫ��w�����j�r��
			LinkVector.addElement(sTmp.substring(0, pos));
			sTmp = sTmp.substring(pos + cutLength); // �h���Τ����r��Ѽ�
		}
		LinkVector.addElement(sTmp); // �[�J�̫�@�Ӧr��Ѽ�
	}

	private void Init_InOut_Param() { // �ҩl��X�J�Ѽ��ѧO
		int iSize = _ParamStr.size();
		_InOutStr.clear();
		for (int i = 0; i < iSize; i++) {
			if (_ParamStr.elementAt(i).equals(""))
				_InOutStr.addElement("OutParam");
			else
				_InOutStr.addElement("InParam");
		}
	}

	private void CreateOutParam(int cnt, String Str) throws SQLException {
		int sqlType;
		String sTmp = "";
		if (Str.equals("char")) {
			sqlType = java.sql.Types.CHAR;
			sTmp = "java.sql.Types.CHAR";
		} else if (Str.equals("vchar")) {
			sqlType = java.sql.Types.VARCHAR;
			sTmp = "java.sql.Types.VARCHAR";
		} else if (Str.equals("int")) {
			sqlType = java.sql.Types.INTEGER;
			sTmp = "java.sql.Types.INTEGER";
		} else if (Str.equals("long")) {
			sqlType = java.sql.Types.BIGINT;
			sTmp = "java.sql.Types.BIGINT";
		} else if (Str.equals("float")) {
			sqlType = java.sql.Types.FLOAT;
			sTmp = "java.sql.Types.FLOAT";
		} else if (Str.equals("num")) {
			sqlType = java.sql.Types.NUMERIC;
			sTmp = "java.sql.Types.NUMBERIC";
		} else if (Str.equals("cursor")) {
			sqlType = OracleTypes.CURSOR;
			sTmp = "OracleTypes.CURSOR";
			CursorPos = (CursorPos == 0) ? cnt + 1 : CursorPos; // �u�O���Ĥ@���X�{��Cursor
																// Type
		} else
			throw new SQLException("Value type isn't found ..");
		cstmt.registerOutParameter(cnt + 1, sqlType);
		TraceStack.add("registerOutParameter(" + Integer.toString(cnt + 1)
				+ "," + sTmp + ")\n");
	}

	private void CreateInParam(int cnt, String Str) throws SQLException {
		StringBuffer sTmp = new StringBuffer();
		String ParamStr = _ParamStr.elementAt(cnt).toString();
		if (Str.equals("char") || Str.equals("vchar")) {
			try {
				// if( _charset)
				// ParamStr=new String( ParamStr.getBytes(clientCharset) ,
				// dbCharset );
				ParamStr = transCharset4Client2db(ParamStr);
				cstmt.setString(cnt + 1, ParamStr);
				sTmp.append("setString(");
			} catch (Exception e) {
				System.out.println("Unsupported Encoding");
			}
		} else if (Str.equals("int")) {
			cstmt.setInt(cnt + 1, Integer.parseInt(ParamStr));
			sTmp.append("setInt(");
		} else if (Str.equals("float")) {
			cstmt.setFloat(cnt + 1, Float.parseFloat(ParamStr));
			sTmp.append("setFloat(");
		} else if (Str.equals("long")) {
			cstmt.setLong(cnt + 1, Long.parseLong(ParamStr));
			sTmp.append("setLong(");
		} else if (Str.equals("num")) {
			cstmt.setBigDecimal(cnt + 1, new java.math.BigDecimal(ParamStr));
			sTmp.append("setBigDecimal(");
		}
		sTmp.append(Integer.toString(cnt + 1) + "," + ParamStr + ")");
		TraceStack.add(sTmp.toString() + '\n');
	}

	private void CreatePreCallStr(String PLSql) { // �إ� StoreProcedure �I�s�覡
		int iSize = _ParamStr.size() - 1;
		String tmp = null;
		_PLSQLStr = "{call ";
		tmp = _TypeStr.elementAt(0).toString();
		if (tmp.charAt(0) == '*') {
			_TypeStr.remove(0);
			_TypeStr.insertElementAt(tmp.substring(1), 0);
			iSize--;
			_PLSQLStr = "{? = call ";
		}
		_PLSQLStr = _PLSQLStr + PLSql + "(";
		for (int i = 0; i < iSize; i++) {
			_PLSQLStr = _PLSQLStr + "?,";
		}
		_PLSQLStr = _PLSQLStr + "?)}";
		TraceStack.add(_PLSQLStr + '\n');
	}

	private void Initilizal_call(String PLSQLname, String Typename,
			String Paramname) throws SQLException {
		CursorPos = 0;
		TraceStack.clear();
		if ((Typename.length() > 0 && Paramname.length() == 0)
				|| (Typename.length() == 0 && Paramname.length() > 0))
			throw new SQLException("Can't parser the parameter ..");

		if (!Typename.equals("")) {
			CutString(Typename, _TypeStr);
			CutString(Paramname, _ParamStr);
			Init_InOut_Param();
			CreatePreCallStr(PLSQLname);
			cstmt = conn.prepareCall(_PLSQLStr); // Preparecall StroeProcedure
			// �ҩl�Ѽ�
			if (_InOutStr.size() > 0) {
				for (int i = 0; i < _InOutStr.size(); i++) {
					if (_InOutStr.elementAt(i).equals("OutParam"))
						CreateOutParam(i, _TypeStr.elementAt(i).toString()
								.toLowerCase());
					else
						CreateInParam(i, _TypeStr.elementAt(i).toString()
								.toLowerCase());
				}
			}
		} else {
			_PLSQLStr = "{call " + PLSQLname + "()}";
			cstmt = conn.prepareCall(_PLSQLStr);
			TraceStack.add(_PLSQLStr + '\n');
		}
	}

	public ResultSet doPLSelect(String PLSQLname, String Typename,
			String Paramname) {
		cstmt = null;
		// _CutStr=CutStr; // �ҩl���j�r��Φr��
		_CutStr = "@"; // �ҩl���j�r��Φr��
		_TypeStr = new Vector(); // ���AVector
		_ParamStr = new Vector(); // �Ѽ�Vector
		_InOutStr = new Vector(); // InOutParam �ѧOVector
		TraceStack = new Vector(); // �l�� Vector
		rs = null;
		Typename = (Typename == null) ? "" : Typename;
		Paramname = (Paramname == null) ? "" : Paramname;
		try {
			Initilizal_call(PLSQLname, Typename, Paramname);
			cstmt.executeQuery();
			if (CursorPos > 0)
				rs = (ResultSet) cstmt.getObject(CursorPos);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;
	}

	public Vector doPLSelect(String PLSQLname, String Typename,
			String Paramname, boolean is_cut) {
		Vector vector = new Vector();
		try {

			ResultSetMetaData meta;
			String s = null;
			if (rs != null)
				rs.close();
			rs = null;
			size = 0;
			int columnNum = 0;
			rs = doPLSelect(PLSQLname, Typename, Paramname);
			if (rs != null) {
				meta = rs.getMetaData();
				columnNum = meta.getColumnCount();
			}
			while (rs.next()) {
				for (int j = 1; j <= columnNum; j++) {
					s = rs.getString(j);
					// if( (s != null) && ( _charset ) )
					// s = new String( s.getBytes(dbCharset) , clientCharset );
					// if( s == null) s="";
					s = transCharset4db2Client(s);
					vector.addElement(s);
				}

			}
			/*
			 * for(rs = doPLSelect(PLSQLname,Typename,Paramname); rs.next();) {
			 * for(int j = 1; j <= columnNum; j++) { s=rs.getString(j); if( (s
			 * != null) && ( _charset ) ) s = new String( s.getBytes(dbCharset)
			 * , clientCharset ); if( s == null) s=""; vector.addElement(s); }
			 * 
			 * }
			 */
		} catch (SQLException sqlexception) {
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				sqlexception = sqlexception.getNextException();
				System.out.println("");
			}
		} catch (Exception exception) {
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return vector;
	}

	public int doPLUpdate(String PLSQLname, String Typename, String Paramname) {
		int cnt = -1;
		try {
			Initilizal_call(PLSQLname, Typename, Paramname);
			cnt = cstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return cnt;
	}

	public Object GetOutParam(int i) throws SQLException {
		return cstmt.getObject(i);
	}

	public String StackDump() {
		String sTmp = TraceStack.toString();
		return sTmp.substring(1, sTmp.length() - 1);
	}

	public void doPLSelect(String PLSQLname) {
		doPLSelect(PLSQLname, "", "");
	}

	public int doPLUpdate(String PLSQLname) {
		return doPLUpdate(PLSQLname, "", "");
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:String SQL :�ǤJ�� SQL �y�k&lt;br&gt;
	 *      �Ǧ^:CallableStatement  &lt;br&gt;
	 *      ����:prepareCall ���x��ƩI�s&lt;br&gt;
	 *</pre>
	 */
	public CallableStatement prepareCall(java.lang.String sql)
			throws SQLException {
		return conn.prepareCall(sql);
	}

	/**************************************************************************************/
	/******************** get data and Object **************************************************/

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:int ��Ƶ���  &lt;br&gt;
	 *      ����:���o ResultSet ����Ƶ��� &lt;br&gt;
	 *</pre>
	 */
	public int size() throws SQLException {
		return this.size;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:ColumnName ���W��&lt;br&gt;
	 *      �Ǧ^:String          &lt;br&gt;
	 *      ����:���o ResultSet �ثe���d�b���r�������(�r�ꫬ�A) &lt;br&gt;
	 *</pre>
	 */
	public String getString(String ColumnName) throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");
		// if( _charset)
		// try {
		// ColumnName = new
		// String(ColumnName.getBytes(clientCharset),dbCharset);
		// } catch (Exception ignore) { }
		ColumnName = transCharset4Client2db(ColumnName);

		String s = rs.getString(ColumnName);
		if ((s != null) && (_charset))
			try {
				s = new String(s.getBytes(dbCharset), clientCharset);
			} catch (Exception e) {
				throw new SQLException(e.getMessage());
			}
		if (s == null)
			s = "";
		return s;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:idx ����m&lt;br&gt;
	 *      �Ǧ^:String          &lt;br&gt;
	 *      ����:���o ResultSet �ثe���d�b�������(�r�ꫬ�A)     &lt;br&gt;
	 *</pre>
	 */
	public String getString(int idx) throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");
		String s = rs.getString(idx);
		if ((s != null) && (_charset))
			try {
				s = new String(s.getBytes(dbCharset), clientCharset);
			} catch (Exception e) {
				throw new SQLException(e.getMessage());
			}
		if (s == null)
			s = "";
		return s;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:ColumnName ���W��&lt;br&gt;
	 *      �Ǧ^:integer          &lt;br&gt;
	 *      ����:���o ResultSet �ثe���d�b�������(�ƭȫ��A)     &lt;br&gt;
	 *</pre>
	 */
	public int getInt(String ColumnName) throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");
		return rs.getInt(ColumnName);
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:int idx ����m&lt;br&gt;
	 *      �Ǧ^:integer          &lt;br&gt;
	 *      ����:���o ResultSet �ثe���d�b�������(�ƭȫ��A)     &lt;br&gt;
	 *</pre>
	 */
	public int getInt(int idx) throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");
		return rs.getInt(idx);
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:ColumnName ���W��&lt;br&gt;
	 *      �Ǧ^:String          &lt;br&gt;
	 *      ����:���o ResultSet �ثe���d�b�������(�ƭȫ��A)     &lt;br&gt;
	 *</pre>
	 */
	public String getClob(String ColumnName) throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");

		String str = "";
		try {
			oracle.sql.CLOB clob = null;
			clob = (oracle.sql.CLOB) rs.getObject(ColumnName);
			if (clob != null) {
				str = clob.getSubString((long) 1, (int) clob.length());
			}
			str = new String(str.getBytes(dbCharset), clientCharset);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return str;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:int idx ����m&lt;br&gt;
	 *      �Ǧ^:integer          &lt;br&gt;
	 *      ����:���o ResultSet �ثe���d�b�������(�ƭȫ��A)     &lt;br&gt;
	 *</pre>
	 */
	public String getClob(int idx) throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");
		String str = "";
		try {
			oracle.sql.CLOB clob = null;
			clob = (oracle.sql.CLOB) rs.getObject(idx);
			if (clob != null) {
				str = clob.getSubString((long) 1, (int) clob.length());
			}
			str = new String(str.getBytes(dbCharset), clientCharset);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return str;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:ColumnName ���W��&lt;br&gt;
	 *      �Ǧ^:Object          &lt;br&gt;
	 *      ����:���o ResultSet �ثe���d�b�������(�ƭȫ��A)     &lt;br&gt;
	 *</pre>
	 */
	public Object getObject(String ColumnName) throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");
		return rs.getObject(ColumnName);
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:int idx ����m&lt;br&gt;
	 *      �Ǧ^:integer          &lt;br&gt;
	 *      ����:���o ResultSet �ثe���d�b�������(�ƭȫ��A)     &lt;br&gt;
	 *</pre>
	 */
	public Object getObject(int idx) throws SQLException {
		if (rs == null)
			throw new SQLException("Null ResultSet Object");
		return rs.getObject(idx);
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:�L         &lt;br&gt;
	 *      ����:�Ǧ^�̫�@�� Query �ұo�� ResultSet Object&lt;br&gt;
	 *</pre>
	 */
	public ResultSet getResultSetObject() {
		return rs;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:Connection          &lt;br&gt;
	 *      ����:���o Connection     &lt;br&gt;
	 *</pre>
	 */
	public Connection getConnection() {
		return conn;
	}

	public int getCountDoSqlSelect(String s) {
		int i = 0;
		try {
			s = new String(s.getBytes(clientCharset), dbCharset);
			if (rs != null)
				rs.close();
			rs = null;
			for (rs = stmt.executeQuery(s); rs.next();)
				i++;
		} catch (SQLException sqlexception) {
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				sqlexception = sqlexception.getNextException();
				System.out.println("");
			}
		} catch (Exception exception) {
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return i;
	}

	/************************************************************************************/

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:�L&lt;br&gt;
	 *      ����:commit �T�w��Ƽg�^��Ʈw&lt;br&gt;
	 *</pre>
	 */

	public void commit() {
		try {
			conn.commit();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:�L&lt;br&gt;
	 *      ����:rollback �^�_���&lt;br&gt;
	 *</pre>
	 */
	public void rollback() {
		try {
			conn.rollback();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      �Ѽ�:�L&lt;br&gt;
	 *      �Ǧ^:�L&lt;br&gt;
	 *      ����:rollback �^�_���&lt;br&gt;
	 *</pre>
	 */

	public void setAutoCommit(boolean flag) {
		try {
			conn.setAutoCommit(flag);
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	/*******************************************************************************************/
	/**
	 *<pre>
	 * &lt;br&gt;
	 *        �Ѽ�:table:�n�j�M��table�W��&lt;br&gt;
	 *        	    field:�n�j�M�����W��&lt;br&gt;
	 *        	    conditions:�䥦���󦡡A�Ҧp&quot;name = '����_'&quot;&lt;br&gt;
	 *             parameter:1:�n���o�����e���¼Ʀr:�Ҧp 23152&lt;br&gt;
	 * 	              2:�n���o�����e���Ʀr,�^��æs:�Ҧp A12BC&lt;br&gt;
	 * 	              3:�n���o�����e���­^��:�Ҧp ABD&lt;br&gt;	
	 * 	              4:�n���o�����e���Ʀr,�^��æs(�֥[�覡���P�P��2):�Ҧp A12BC&lt;br&gt;
	 *        �Ǧ^:�r��&lt;br&gt;
	 *        ����:���o��쪺�̤j��,�^�ǭȦp���^�� �A�h�^�嬰�j�g&lt;br&gt;
	 *        	    �p���Ʀr�B�^��æs�A�h9�|�i�즨A�A�Ҧp 19 --&gt; 1A , 1Z --&gt;20&lt;br&gt;
	 *</pre>
	 */

	public String getMaxNo(String table, String field, String conditions,
			int parameter) {
		try {
			table = new String(table.getBytes(clientCharset), dbCharset);
			field = new String(field.getBytes(clientCharset), dbCharset);
			conditions = new String(conditions.getBytes(clientCharset),
					dbCharset);
		} catch (Exception ex) {
			System.out.println("Exception=" + ex.getMessage());
		}
		String sql = "select " + field + " from " + table;
		if (!conditions.equals(""))
			sql += " where " + conditions;
		sql += " order by length(" + field + ") desc," + field + " desc";
		// System.out.println("test="+sql);
		String retVal = ""; // �^�ǭ�
		String value = "";
		String tempString = "";
		int strLength = 0;// �r�����
		int doLength = 0;// �@�Τ����r��index
		try {
			if (rs != null)
				rs.close();
			rs = null;

			rs = stmt.executeQuery(sql);
			if (rs.next()) {

				value = rs.getString(1);
				System.out.println("value=" + value);

				if (parameter == 1) // �¼Ʀr
				{

					retVal = String.valueOf(Integer.parseInt(value) + 1);
				} else if (parameter == 2) // �Ʀr�B�^��æs
				{
					tempString = value.toUpperCase();
					strLength = tempString.length();// �r�����
					doLength = (tempString.length() - 1);// �@�Τ����r��index
					while (true) {
						char char1 = getNextNumber(tempString.charAt(doLength));
						tempString = tempString.substring(0, doLength) + char1
								+ tempString.substring(doLength + 1, strLength);
						tempString.trim();

						if (char1 == '0') {
							doLength--;
							if (doLength == -1)// ����W�XINDEX����
							{
								tempString = "1" + tempString;
								break;
							}

						} else {
							break;
						}

					}
					retVal = tempString;

				} else if (parameter == 3) // �­^��r
				{
					tempString = value.toUpperCase();
					strLength = tempString.length();// �r�����
					doLength = (tempString.length() - 1);// �@�Τ����r��index
					while (true) {
						char char1 = getNextNumber(tempString.charAt(doLength));

						if (char1 == '0' || char1 == '1' || char1 == '2'
								|| char1 == '3' || char1 == '4' || char1 == '5'
								|| char1 == '6' || char1 == '7' || char1 == '8'
								|| char1 == '9') {
							tempString = tempString.substring(0, doLength)
									+ 'A'
									+ tempString.substring(doLength + 1,
											strLength);
							doLength--;
							if (doLength == -1)// ����W�XINDEX����
							{
								tempString = "A" + tempString;
								break;
							}

						} else {
							tempString = tempString.substring(0, doLength)
									+ char1
									+ tempString.substring(doLength + 1,
											strLength);
							break;
						}

						tempString.trim();

					}
					retVal = tempString;
				}
				// 2003.08.21 Peric �ק�,�W�[�ǤJ�Ѽ� 4
				// �Ʀr�B�^��æs; �֥[�覡:�Ʀr�֥[��10�i��;�^��֥[��Z�i��
				else if (parameter == 4) {
					tempString = (value != null) ? value.trim() : "";// �P�_�O�_����
					strLength = tempString.length();// �r�����
					if (strLength > 0)// �r����פj��s�~�ݭn�B�z
					{
						doLength = (tempString.length() - 1);// �@�Τ����r��index
						String oSB = "";// �}�@�Ӫ�StringBuffer for �^�ǭ�
						// �����X�̥k��r���[1
						char char1 = getNextLiteral(tempString.charAt(doLength));
						oSB = String.valueOf(char1) + oSB;
						boolean bOver = (char1 == 'A' || char1 == 'a' || char1 == '0');// �O�_�n�i��
						// �~��B�z��L�r��
						for (int i = 1; i <= doLength; i++) {
							char1 = tempString.charAt(doLength - i);
							if (bOver) {
								char1 = getNextLiteral(char1);// �i��
								bOver = (char1 == 'A' || char1 == 'a' || char1 == '0');// �ݬO�_���A�i��
							}
							oSB = String.valueOf(char1) + oSB;
						}
						if (bOver) // �i��
						{
							if (char1 == 'A')
								oSB = "A" + oSB;
							else if (char1 == 'a')
								oSB = "a" + oSB;
							else if (char1 == '0')
								oSB = "1" + oSB;
						}// end of if (bOver)
						retVal = oSB;
					} // end of (strLength>0)
					else
						retVal = "1";// �ǤJ�r����׵���s�h�^��1

				}// end of else if (parameter==4)

				else {
					retVal = "0"; // �S���o�ذѼ�
				}

			} else {

				if (parameter == 3) {
					// System.out.println("�ϥ�getMaxNo�o�쪺���G��null�A������l�� A");
					retVal = "A";

				} else {
					// System.out.println("�ϥ�getMaxNo�o�쪺���G��null�A������l�� 1");
					retVal = "1";
				}
			}

		} catch (Exception exception) {
			System.out.println(exception);
		}

		return retVal;
	}

	private char getNextNumber(char char1) {
		char char2 = 'A';
		switch (char1) {
		case 'A':
			char2 = 'B';
			break;
		case 'B':
			char2 = 'C';
			break;
		case 'C':
			char2 = 'D';
			break;
		case 'D':
			char2 = 'E';
			break;
		case 'E':
			char2 = 'F';
			break;
		case 'F':
			char2 = 'G';
			break;
		case 'G':
			char2 = 'H';
			break;
		case 'H':
			char2 = 'I';
			break;
		case 'I':
			char2 = 'J';
			break;
		case 'J':
			char2 = 'K';
			break;
		case 'K':
			char2 = 'L';
			break;
		case 'L':
			char2 = 'M';
			break;
		case 'M':
			char2 = 'N';
			break;
		case 'N':
			char2 = 'O';
			break;
		case 'O':
			char2 = 'P';
			break;
		case 'P':
			char2 = 'Q';
			break;
		case 'Q':
			char2 = 'R';
			break;
		case 'R':
			char2 = 'S';
			break;
		case 'S':
			char2 = 'T';
			break;
		case 'T':
			char2 = 'U';
			break;
		case 'U':
			char2 = 'V';
			break;
		case 'V':
			char2 = 'W';
			break;
		case 'W':
			char2 = 'X';
			break;
		case 'X':
			char2 = 'Y';
			break;
		case 'Y':
			char2 = 'Z';
			break;
		case 'Z':
			char2 = '0';
			break;
		case '0':
			char2 = '1';
			break;
		case '1':
			char2 = '2';
			break;
		case '2':
			char2 = '3';
			break;
		case '3':
			char2 = '4';
			break;
		case '4':
			char2 = '5';
			break;

		case '5':
			char2 = '6';
			break;
		case '6':
			char2 = '7';
			break;
		case '7':
			char2 = '8';
			break;
		case '8':
			char2 = '9';
			break;
		case '9':
			char2 = 'A';
			break;

		}
		return char2;

	}

	/*******************************************************************************************/
	/**
	 *<pre>
	 * &lt;br&gt;
	 *    	�S�O���i��覡�A�_�l�i��覡���Ʀr�A���ӦA��^��i��
	 *        �Ѽ�:table:�n�j�M��table�W��&lt;br&gt;
	 *        	    field:�n�j�M�����W��&lt;br&gt;
	 *        	    conditions:�䥦���󦡡A�Ҧp&quot;name = '����_'&quot;&lt;br&gt;
	 *             count: �}�l�ഫ�� �^��i�쪺��ơA�Ҧp �� count �� 2 �ɡA��� 99 �ɡA�U�@��Ƭ� AA
	 * 	             
	 *        �Ǧ^:�r��&lt;br&gt;
	 * 
	 *</pre>
	 */

	public String getMaxNoA(String table, String field, String conditions,
			int count) {
		try {
			table = new String(table.getBytes(clientCharset), dbCharset);
			field = new String(field.getBytes(clientCharset), dbCharset);
			conditions = new String(conditions.getBytes(clientCharset),
					dbCharset);
		} catch (Exception ex) {
			System.out.println("Exception=" + ex.getMessage());
		}
		String sql = "select " + field + " from " + table;
		if (!conditions.equals(""))
			sql += " where " + conditions;
		sql += " order by length(" + field + ") desc," + field + " desc";

		String retVal = ""; // �^�ǭ�
		String value = "";
		int state = 0; // �s��O�H�Ʀr�i��(�N�X:1)�ΥH�^��i��(�N�X:2)
		String tempString = "";
		int strLength = 0;// �r�����
		String max = ""; // �s��n��^��i�쪺�̤j�ȡA�p��count��3�ɡA�n�ন�^��i�쪺�̤j�Ʀr�� 999
		int doLength = 0;// �@�Τ����r��index

		try {
			if (rs != null)
				rs.close();
			rs = null;

			rs = stmt.executeQuery(sql);
			if (rs.next()) {

				value = rs.getString(1);
				// System.out.println("value="+value);

				for (int j = 0; j < count; j++) {
					max = max + "9";
				}
				try {

					if (Integer.parseInt(value) < Integer.parseInt(max))
						state = 1;
					else
						state = 2;
				} catch (NumberFormatException e) {
					state = 2;
				}

				if (state == 1) // �¼Ʀr
				{

					retVal = String.valueOf(Integer.parseInt(value) + 1);
				} else if (state == 2) // �­^��r
				{
					tempString = value.toUpperCase();
					strLength = tempString.length();// �r�����
					doLength = (tempString.length() - 1);// �@�Τ����r��index
					if (max.equals(value)) {
						tempString = "";
						for (int j = 0; j < count; j++) {
							tempString = tempString + "A";
						}
					} else {
						while (true) {
							char char1 = getNextNumber(tempString
									.charAt(doLength));

							if (char1 == '0' || char1 == '1' || char1 == '2'
									|| char1 == '3' || char1 == '4'
									|| char1 == '5' || char1 == '6'
									|| char1 == '7' || char1 == '8'
									|| char1 == '9') {
								tempString = tempString.substring(0, doLength)
										+ 'A'
										+ tempString.substring(doLength + 1,
												strLength);
								doLength--;
								if (doLength == -1)// ����W�XINDEX����
								{
									tempString = "A" + tempString;
									break;
								}

							} else {
								tempString = tempString.substring(0, doLength)
										+ char1
										+ tempString.substring(doLength + 1,
												strLength);
								break;
							}
							tempString.trim();

						}
					}

					retVal = tempString;
				} else {
					retVal = "0"; // �S���o�ذѼ�
				}

			} else {
				// System.out.println("�ϥ�getMaxNo�o�쪺���G��null�A������l�� 1");
				retVal = "1";
			}

		} catch (Exception exception) {
			System.out.println(exception);
		}

		return retVal;
	}

	// 2003.08.21 Peric �W�[ getNextLiteral()
	private char getNextLiteral(char char1) {
		char char2 = '0';
		if (char1 == 'Z')
			char2 = 'A';
		else if (char1 == 'z')
			char2 = 'a';
		else if (char1 == '9')
			char2 = '0';
		else
			char2 = (char) (char1 + 1);
		return char2;
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      Method : transCharset4Client2db (2003.09.19 Peric Add)
	 *      �Ѽ�:1.String sSourceStr      :�ǤJ�n��X���r��&lt;br&gt;
	 *      �Ǧ^: String ���s�s�X�L���r��   &lt;br&gt;
	 *      ����:�r����X&lt;br&gt;
	 *</pre>
	 */
	public static String transCharset4Client2db(String sSourceStr) {
		String sTargetStr = "";
		sSourceStr = (sSourceStr == null) ? "" : sSourceStr;
		if (_charset)
			sTargetStr = transCharset(sSourceStr, clientCharset, dbCharset);
		else
			sTargetStr = sSourceStr;
		sTargetStr = (sTargetStr == null) ? "" : sTargetStr;
		return sTargetStr;

	}// transCharset4Client2db()

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      Method : transCharset4db2Client (2003.09.19 Peric Add)
	 *      �Ѽ�:1.String sSourceStr      :�ǤJ�n��X���r��&lt;br&gt;
	 *      �Ǧ^: String ���s�s�X�L���r��   &lt;br&gt;
	 *      ����:�r����X&lt;br&gt;
	 *</pre>
	 */
	public static String transCharset4db2Client(String sSourceStr) {
		String sTargetStr = "";
		sSourceStr = (sSourceStr == null) ? "" : sSourceStr;
		if (_charset)
			sTargetStr = transCharset(sSourceStr, dbCharset, clientCharset);
		else
			sTargetStr = sSourceStr;
		sTargetStr = (sTargetStr == null) ? "" : sTargetStr;
		return sTargetStr;

	}// transCharset4db2Client()

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      Method : transCharset (2003.09.19 Peric Add)
	 *      �Ѽ�:1.String sSourceStr      :�ǤJ�n��X���r��&lt;br&gt;
	 *           2.String clientCharset   :������s�X�N�X&lt;br&gt;
	 *           3.String dbCharset       :�ҭn��X���s�X�N�X&lt;br&gt;
	 *      �Ǧ^: String ���s�s�X�L���r��   &lt;br&gt;
	 *      ����:�r����X&lt;br&gt;
	 *</pre>
	 */
	public static String transCharset(String sSourceStr, String sSourceCharset,
			String sTargetCharset) { // �`�N:��X�᪺�r�����ӭn�ηs���r���ܼƥh��,�Y�έ��ܼ�,�i��|�o���ण�L�h�����p,ex:
										// jsp���|�ण�L�h.
		String sTargetStr = "";
		try {// 2003.10.03 �`�N!! �Ŧr�ꤣ�i�H��X
			if (sSourceStr != null && sSourceStr.length() > 0) {
				if ((sSourceCharset.equals("UTF-8") || sSourceCharset
						.equals("UTF-8"))
						&& (sTargetCharset.equals("ISO8859-1") || sTargetCharset
								.equals("ISO8859_1"))) {
					StringBuffer sb = new StringBuffer();

					for (int i = 0; i < sSourceStr.length(); i++) {
						String s = sSourceStr.substring(i, i + 1);

						if (s.getBytes(sSourceCharset).length > 1) {
							sb.append(new String(s.getBytes(sSourceCharset),
									sTargetCharset));
						} else {
							sb.append(s);
						}
					}
					sTargetStr = sb.toString();
				} else {
					sTargetStr = new String(
							sSourceStr.getBytes(sSourceCharset), sTargetCharset);
				}
			}
		} catch (Exception e) {
			sTargetStr = sSourceStr;
			// log.error(e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}// end of try-catch
		sTargetStr = (sTargetStr == null) ? "" : sTargetStr;
		return sTargetStr;
	}// transCharset()

	public static String dbLikeStr(Object obj) {

		StringBuffer sb = new StringBuffer();

		try {
			// 2007.06.14 modified by lewiswang [#1668]
			String str = util.checkNull(obj, "");
			for (int i = 0; i < str.length(); i++) {
				String s = str.substring(i, i + 1);
				if (s.getBytes().length > 1) {
					if (transCharset4Client2db(s).indexOf("\\") < 0
							&& transCharset4Client2db(s).indexOf("%") < 0
							&& transCharset4Client2db(s).indexOf("_") < 0
							&& transCharset4Client2db(s).indexOf("'") < 0) {
						sb.append(s);
					} else {
						sb.append(util.replace(util.replace(util.replace(util
								.replace(transCharset4Client2db(s), "\\",
										"\\\\"), "%", "\\%"), "_", "\\_"), "'",
								"''"));
					}
					// sb.append( transCharset4Client2db(
					// util.replace(util.replace(util.replace(util.replace(s,"\\","\\\\"),"%","\\%"),"_","\\_"),"'","''")));
				} else {
					sb.append(s);
				}
			}
			sb.append("' escape '\\");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      Method : doSqlQuery (2003.09.19 Peric Add)
	 *      �Ѽ�:1.String SqlStatement :�ǤJ�� SQL �y�k&lt;br&gt;
	 *           2.int pageNum         :���^�ǲĴX��&lt;br&gt;
	 *           3.int rec_per_page    :�C�@����Ƶ��� &lt;br&gt;
	 *      �Ǧ^:ArrayList   �^�Ǹ��,�ΤG���}�C�B�z &lt;br&gt;
	 *      ����:�d�T��Ʈw���,�i�H���w���d�߲ĴX��,�C�@�����X�����,���G�H�G�� ArrayList �^��&lt;br&gt;
	 *</pre>
	 */
	public ArrayList doSqlQuery(String SqlStatement, int pageNum,
			int rec_per_page) {
		ArrayList oReturnAR = new ArrayList();
		ResultSet oRS = null;
		try {
			SqlStatement = transCharset4Client2db(SqlStatement);
			String s = null;
			int start = rec_per_page * (pageNum - 1) + 1;
			int end = rec_per_page * pageNum;
			// if( oRS!=null ) oRS.close() ;
			oRS = null;
			oRS = stmt.executeQuery(SqlStatement);
			// log.debug("getting columnNum ...");
			ResultSetMetaData oRSMD = oRS.getMetaData();
			// log.debug(oRSMD);
			int columnNum = oRSMD.getColumnCount();
			// log.debug("columnNum="+columnNum);
			int j = 0;
			size = 0;
			oRS.last();
			size = oRS.getRow();
			j = start;
			oRS.absolute(start);
			do {
				if (j >= start && j <= end) {
					ArrayList oRecordAR = new ArrayList();
					for (int k1 = 1; k1 <= columnNum; k1++) {
						s = oRS.getString(k1);
						s = transCharset4db2Client(s);
						oRecordAR.add(s);
					}// end of for
					oReturnAR.add(oRecordAR);
				}// end of if
				if (++j > end)
					break;
			} while (oRS.next());
			oRSMD = null;
			if (oRS != null)
				oRS.close();
			oRS = null;
		} catch (SQLException sqlexception) { // log.debug(" >>>>>>>>>>> "+sqlexception.getMessage());
			// log.debug(sqlexception);
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				System.out.println("SqlStatement:  " + SqlStatement);
				sqlexception = sqlexception.getNextException();
				System.out.println("");
			}
		} catch (Exception exception) { // log.debug(" >>>>>>>>>>> "+exception.getMessage());
			// log.debug(exception);
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return oReturnAR;
	}// doSqlQuery()

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      Method : doSqlQuery (2003.09.19 Peric Add)
	 *      �Ѽ�:1.String SqlStatement :�ǤJ�� SQL �y�k&lt;br&gt;
	 *      �Ǧ^:ArrayList   �^�Ǹ��,�ΤG���}�C�B�z &lt;br&gt;
	 *      ����:�d�T��Ʈw���,���G�H�G�� ArrayList �^��&lt;br&gt;
	 *</pre>
	 */
	public ArrayList doSqlQuery(String SqlStatement) {
		ArrayList oReturnAR = new ArrayList();
		ResultSet oRS = null;
		try {
			// log.debug("Before SqlStatement="+SqlStatement);
			SqlStatement = transCharset4Client2db(SqlStatement);
			// log.debug("After  SqlStatement="+SqlStatement);
			String s = null;
			// log.debug("Before  oRS="+oRS);
			// if( oRS!=null ) oRS.close() ;
			oRS = null;
			size = 0;
			// log.debug("stmt="+stmt.toString());
			oRS = stmt.executeQuery(SqlStatement);
			// log.debug("getting columnNum ...");
			ResultSetMetaData oRSMD = oRS.getMetaData();
			// log.debug(oRSMD);
			int columnNum = oRSMD.getColumnCount();
			// log.debug("columnNum="+columnNum);
			while (oRS.next()) {
				ArrayList oRecordAR = new ArrayList();
				size++;
				for (int j = 1; j <= columnNum; j++) {
					s = oRS.getString(j);
					s = transCharset4db2Client(s);
					oRecordAR.add(s);
				}
				oReturnAR.add(oRecordAR);
			}// end of while
			oRSMD = null;
			if (oRS != null)
				oRS.close();
			oRS = null;
			// log.debug("After  oRS="+oRS);
		} catch (SQLException sqlexception) { // log.debug(" >>>>>>>>>>> "+sqlexception.getMessage());
			// log.debug(sqlexception);
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				System.out.println("SqlStatement:  " + SqlStatement);
				sqlexception = sqlexception.getNextException();

				System.out.println("");
			}
		} catch (Exception exception) { // log.debug(" >>>>>>>>>>> "+exception.getMessage());
			// log.debug(exception);
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return oReturnAR;
	}// doSqlQuery()

	/**
	 *<pre>
	 * &lt;br&gt;
	 *      Method : getOneRow (2005.06.21 Peric Add)
	 *      �Ѽ�:1.String SqlStatement :�ǤJ�� SQL �y�k&lt;br&gt;
	 *      �Ǧ^:Hashtable   �^�Ǹ��,�� Hashtable �^�Ǥ@����� &lt;br&gt;
	 *      ����:�d�T��Ʈw���,Hashtable �^�Ǥ@�����&lt;br&gt;
	 *</pre>
	 */
	public Hashtable getOneRow(String SqlStatement) {
		Hashtable oReturnHT = new Hashtable();
		ResultSet oRS = null;
		try {
			// log.debug("Before SqlStatement="+SqlStatement);
			SqlStatement = transCharset4Client2db(SqlStatement);
			// log.debug("After  SqlStatement="+SqlStatement);
			String sValue = null;
			String sColunm = null;
			// log.debug("Before  oRS="+oRS);
			// if( oRS!=null ) oRS.close() ;
			oRS = null;
			size = 0;
			// log.debug("stmt="+stmt.toString());
			oRS = stmt.executeQuery(SqlStatement);
			// log.debug("getting columnNum ...");
			ResultSetMetaData oRSMD = oRS.getMetaData();
			// log.debug(oRSMD);
			int columnNum = oRSMD.getColumnCount();
			// log.debug("columnNum="+columnNum);
			if (oRS.next()) {
				ArrayList oRecordAR = new ArrayList();
				size++;
				for (int j = 1; j <= columnNum; j++) {
					sColunm = oRSMD.getColumnName(j);
					sValue = oRS.getString(j);
					sValue = transCharset4db2Client(sValue);
					oReturnHT.put(sColunm.toLowerCase(), sValue);
				}
			}// end of while
			oRSMD = null;
			if (oRS != null)
				oRS.close();
			oRS = null;
			// log.debug("After  oRS="+oRS);
		} catch (SQLException sqlexception) { // log.debug(" >>>>>>>>>>> "+sqlexception.getMessage());
			// log.debug(sqlexception);
			System.out.println("SQLException:");
			while (sqlexception != null) {
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				System.out.println("SqlStatement:  " + SqlStatement);
				sqlexception = sqlexception.getNextException();

				System.out.println("");
			}
		} catch (Exception exception) { // log.debug(" >>>>>>>>>>> "+exception.getMessage());
			// log.debug(exception);
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return oReturnHT;
	}// getOneRow()

}