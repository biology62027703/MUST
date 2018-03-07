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
 * 操作 Access 的資料庫，本類別只能用在 win 作業系統
 * 使用範例
 * JAccessUtility au = new JAccessUtility("c://temp2//test.mdb");
*
* 	String sql = "";
*	au.openConnection();
*
*	sql = "INSERT INTO Q04 (NAME,AGE) values ('王文奇',30)";
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
	 * 建構式
     * @param url dbf路徑
     * @param userid 使用者名稱
     * @param password 使用者密碼
	*/
	public JAccessUtility(String url,String userid,String password){
		this.o_url = url;
		this.url = url.replace('\\','/');
		this.userid = userid;
		this.password = password;
		//makeDir(url);
    }

    /**
	* 建構式
     * @param url dbf路徑
     * @param userid 使用者名稱
     * @param password 使用者密碼
     * @param isDel 是否刪除所有的dbf檔案，預設false
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
    * 建構式
    * @param url dbf路徑
    * @param isDel 是否刪除所有的dbf檔案，預設false
    */

    public JAccessUtility(String url,boolean isDel){
        this.o_url = url;
    	this.isDel = isDel;
    	this.url = url.replace('\\','/');
    	//makeDir(url);
    }

    /**
    * 建構式
    * @param url dbf路徑
    */
    public JAccessUtility(String url){
    	this.o_url = url;
    	this.url = url.replace('\\','/');
    	//makeDir(url);
    }

	/**
	 * 開啟資料庫
	 */

	public boolean openConnection(){
		return getDdfConnection();
	}


	//設定資料內容的編碼字元
	public void setCharset(String charset){
		this.dbCharset = charset;
	}
    /**
     * 關閉資料庫
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
     * 設定是否自動commit
     * @param b booean
     */
	public void setAutoCommit(boolean b) throws SQLException{
		conn.setAutoCommit(b);
	}

    /**
     * 取得 dbf Connection 物件
     * @return Connection  物件
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
	 *   WINDDOWN 回傳 win
	 * 其它     回傳 linux
	 */
    private String checkOS(){
		String retVal = "linux";
		String enc = System.getProperty("file.separator");
		if (enc.equals("\\"))
			retVal = "win";
		return retVal;
	}

	/**
	 * 判斷是否連線已中斷
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
     * 更新資料庫
     * @return 更新筆數
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
 * @param sql sql語法
 * @return ResultSet
 */
	public ResultSet doSqlSelect(String sql) throws SQLException{
		return stmt.executeQuery(sql);
	}

/**
 * @param sql sql語法
 * @param i 欄位數
 * @param ff 目前無作用
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
	 * @param sql sql語法
	 * @param i 欄位數
	 * @param ff 目前無作用
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