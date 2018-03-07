package com.sertek.db;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.hxtt.sql.access.AccessDriver;

import com.sertek.util.CheckObject;
import com.sertek.util.utility;
/**
 * �ާ@ Access ����Ʈw�A�����O�u��Φb win �@�~�t��
 * �ϥνd��
 * JAccessUtility au = new JAccessUtility("c://temp2//test.mdb");
*
* 	String sql = "";
*	au.openConnection();
*
*	sql = "INSERT INTO Q04 (NAME,AGE) values ('����_',30)";
*	au.doSqlUpdate(sql);
*	Vector vt = new Vector();
*	int field  =2;
*	sql = "select name,age from Q04";
*	vt = au.doSqlSelect(sql,field,false);
*	for(int i=0;i<vt.size()/field;i++){
*		out.println(i +"." + vt.get(i*field) + " " + vt.get(i*field+1) + "<BR>");
*	}
*
*	au.closeConnection();
 * @author Administrator
 *
 */
public class JAccessUtility {
	private String os = "linux";
	private String o_url = "";
	private String url = "";
	private String userid = "";
	private String password = "";
	private Connection conn ;
	private Statement stmt;
	private String dbCharset = System.getProperty("file.encoding");
	private String FileSeperator = System.getProperty("file.separator");
	private boolean isDel = false;
	
	//private DBFUtility dbf;
	private CheckObject check = new CheckObject();

	/**
	 * �غc��
     * @param url dbf���|
     * @param userid �ϥΪ̦W��
     * @param password �ϥΪ̱K�X
	*/
	public JAccessUtility(String url,String userid,String password){
		this.o_url = url;
		this.url = url.replace('\\','/');
		this.userid = userid;
		this.password = password;
		//makeDir(url);
    }

    /**
	* �غc��
     * @param url dbf���|
     * @param userid �ϥΪ̦W��
     * @param password �ϥΪ̱K�X
     * @param isDel �O�_�R���Ҧ���dbf�ɮסA�w�]false
	*/
	public JAccessUtility(String url,String userid,String password,boolean isDel){
		this.o_url = url;
		this.url = url.replace('\\','/');
		this.userid = userid;
		this.password = password;
		this.isDel = isDel;
		//makeDir(url);
	}

   /**
    * �غc��
    * @param url dbf���|
    * @param isDel �O�_�R���Ҧ���dbf�ɮסA�w�]false
    */

    public JAccessUtility(String url,boolean isDel){
        this.o_url = url;
    	this.isDel = isDel;
    	this.url = url.replace('\\','/');
    	//makeDir(url);
    }

    /**
    * �غc��
    * @param url dbf���|
    */
    public JAccessUtility(String url){
    	this.o_url = url;
    	this.url = url.replace('\\','/');
    	//makeDir(url);
    }

	/**
	 * �}�Ҹ�Ʈw
	 */

	public boolean openConnection(){
		return getDdfConnection();
	}


	//�]�w��Ƥ��e���s�X�r��
	public void setCharset(String charset){
		this.dbCharset = charset;
	}
    /**
     * ������Ʈw
     */
    public void closeConnection(){
    	try{
		stmt.close();
		conn.close();
	}catch(SQLException e){
		System.out.println(e.getMessage());
		e.printStackTrace();
	}

    }

    /**
     * �]�w�O�_�۰�commit
     * @param b booean
     */
	public void setAutoCommit(boolean b) throws SQLException{
		conn.setAutoCommit(b);
	}

    /**
     * ���o dbf Connection ����
     * @return Connection  ����
     */
	public Connection getConnection(){
		return conn;
	}

	private boolean getDdfConnection(){
		boolean retVal = true;	
		try{
			os = checkOS();
			Class.forName("com.hxtt.sql.access.AccessDriver").newInstance();

			if (os.equals("linux"))
				url = "jdbc:Access:///"+url;
			else
				url = "jdbc:Access:/"+url;

			conn = DriverManager.getConnection(url, "", "");
			stmt = conn.createStatement();
		}catch(Exception e){
			retVal = false;			
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 *   WINDDOWN �^�� win
	 * �䥦     �^�� linux
	 */
    private String checkOS(){
		String retVal = "linux";
		String enc = System.getProperty("file.separator");
		if (enc.equals("\\"))
			retVal = "win";
		return retVal;
	}

	/**
	 * �P�_�O�_�s�u�w���_
	 * @return boolean
	 */
	public boolean isClosed() throws SQLException{
		boolean retVal = true;
		if (conn!=null){
			retVal = conn.isClosed();
		}
		return retVal;

	}

    /**
     * ��s��Ʈw
     * @return ��s����
     */
    public synchronized int doSqlUpdate(String sql){
		int retVal = 0;
		try
		{
			//sql = new String( sql.getBytes() , dbCharset );
			retVal = stmt.executeUpdate(sql);
		}catch(SQLException sqlexception){
			System.out.println("SQLException:");
			while(sqlexception != null){
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				sqlexception = sqlexception.getNextException();
				System.out.println("");
			}
		}
		return retVal;
    }

/**
 * @param sql sql�y�k
 * @return ResultSet
 */
	public ResultSet doSqlSelect(String sql) throws SQLException{
		return stmt.executeQuery(sql);
	}

/**
 * @param sql sql�y�k
 * @param i ����
 * @param ff �ثe�L�@��
 * @return Vector
 */
	public Vector doSqlSelect(String sql,int i,boolean ff){
		Vector temp = new Vector();
		ResultSet temp1;
		String strTmp = "";
		try{
			temp1 = doSqlSelect(sql);
			while (temp1.next()){
				for (int k=1;k<=i;k++){
					strTmp = (String)check.checkNull(temp1.getString(k),"");
					temp.addElement(strTmp);
				}
			}
			temp1.close();
		}catch(SQLException sqlexception){
			System.out.println("SQLException:");
			while(sqlexception != null){
				System.out.println("SQLState: " + sqlexception.getSQLState());
				System.out.println("Message:  " + sqlexception.getMessage());
				System.out.println("Vendor:   " + sqlexception.getErrorCode());
				sqlexception = sqlexception.getNextException();
				System.out.println("");
			}
		}catch(Exception exception){
			System.out.println("Exception:  " + exception);
			exception.printStackTrace();
		}
		return temp;
	}

	/**
	 * @param sql sql�y�k
	 * @param i ����
	 * @param ff �ثe�L�@��
	 * @return Vector
	 */
		public List doSqlSelect(String sql,boolean ff){
			List temp = new ArrayList();
			ResultSet temp1;
			String strTmp = "";
			try{
				temp1 = doSqlSelect(sql);
				while (temp1.next()){
					ResultSetMetaData md = temp1.getMetaData();
					HashMap rowHt = new HashMap();
					for (int k=1;k<=md.getColumnCount();k++){
						strTmp = (String)check.checkNull(temp1.getString(k),"");
						rowHt.put(md.getColumnName(k).toLowerCase(), strTmp);
					}
					System.out.println("");
					temp.add(rowHt);
				}
				temp1.close();
			}catch(SQLException sqlexception){
				System.out.println("SQLException:");
				while(sqlexception != null){
					System.out.println("SQLState: " + sqlexception.getSQLState());
					System.out.println("Message:  " + sqlexception.getMessage());
					System.out.println("Vendor:   " + sqlexception.getErrorCode());
					sqlexception = sqlexception.getNextException();
					System.out.println("");
				}
			}catch(Exception exception){
				System.out.println("Exception:  " + exception);
				exception.printStackTrace();
			}
			return temp;
		}
    /**
     * rollback
     */
    public void rollback() throws SQLException
    {
           conn.rollback();
    }
    /**
     * commit
     */
    public void commit() throws SQLException
    {
           conn.commit();
    }

/**************************************************************/


	private String setLength(String str,int i)
	{
		String retVal = "";
		int leng = str.length();
		if (leng<=i)
			retVal = str;
		else
			retVal = str.substring(0,i);
		return retVal;
	}

	private Object checkNull(Object o,Object defaultvalue) {
		String s = "";
		if(o == null)
			s = defaultvalue.toString();
		else
			s = o.toString();
		try{


		s = new String( s.getBytes("ISO8859_1") , dbCharset );

		}catch(Exception e){
			System.out.println(e.getMessage());

			e.printStackTrace();
		}
	return s;
	}

	private void makeDir(String dir){
		File f = new File(dir);
		if (!f.exists())
			f.mkdirs();
		f = null;


	}
	public String getDbfPath(){
		return this.o_url;
	}


}