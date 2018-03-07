package com.sertek.db;
//import com.hxtt.sql.dbf.*;
import com.sertek.util.*;
import java.util.*;
import java.sql.*;
import java.io.*;
/**
 * �����O���Ѩϥ� dbf ��Ʈw�禡
 * <pre>
 * �d�Ҥ@:
 *  DBFUtility dbf = new DBFUtility("c:\\test",false);
 *  dbf.openConnection();
 *  String sql = "";
 *  sql = "create table C01 (name1 char(20),i NUMERIC(10,0),memo1 clob,date1 DATE)";	//�гy table
 *  dbf.doSqlUpdate(sql);
 *  sql ="create index 'C01.NTX' on 'C01.DBF' (i ASC)";					//�гy index
 *  dbf.doSqlUpdate(sql);
 *  sql ="insert into C01 (name1,i,memo1,data1) values ('�s����R',12,'�o�O�@��memo���',{d '1976-02-21'})";
 *  dbf.doSqlUpdate(sql);
 *  sql = "update C01 set i=i+145 where i>200";
 *  dbf.doSqlUpdate(sql);
 *  sql = "select name,i from C01 ";
 *  Vector vt = new Vector();
 *  vt = dbf.doSqlSelect(sql,2,false);
 *  sql = "delete from C01";			//�`�N: delete ���O���[ from
 *  dbf.dlSqlUpdate(sql);
 *  dbf.closeConnection();
 *  dbf = null;
 *
 * �d�ҤG:��Oracle ��X DBF
 * DBFUtility dbf = new DBFUtility("c:\\test",true,db);
 * dbf.openConnection();
 * dbf.transferDbf("select * from g.s03 " ,"S03");
 * dbf.closeConnection();
 * dbf = null;
 *
 *�d�ҤT�G�� DBF �ন insert sql �y�k
 *DBFUtility dbf=new DBFUtility("c:\\temp\\dbf");
 * dbf.openConnection();
 *dbf.transferSql("c:\\temp\\dbf","select c40idi from c40 where c40rtid='R04100' and c40rtkd='01'",1,"G","R08",true);
 * dbf.closeConnection();
 * dbf = null;
 *
 *
 *
 *�d�ҥ|�G��DBF�ɪ�����ioracle��Ʈw
 *DBUtility db=new DBUtility();
 *db.openConnection();
 *DBFUtility dbf = new DBFUtility("c:\\temp\\dbf",false,db);
 *dbf.openConnection();
 *dbf.transferOracle("select c40idi from c40 where c40rtid='R04100' and c40rtkd='01'","H","R08","c40idi","idi",1,false);
 *db.closeConnection();
 *dbf.closeConnection();
 *
 *
 * �`�N:
 * �Ʀr���Шϥ� NUMERIC [(n[,d])]�A�ϥ�INT �|�����D�A���� java.math.BigDecimal ���A
 * MEMO��춷�ŧi���A�� CLOB �A���� java.lang.String ���A
 * �y�k�W�p�n insert ��޸��S��r�����ܡA�����ϥΤϱ׽u��e��Ÿ��A�Ҧp update C01 set IDAPT='�ڬO\'���n\'��'
 *</pre>
 * @author  Wang�@�A�L���M
 * @version 1.0, 11/19/02
 * @since   JDK1.3
 */

/*
--------------------------------------------------------------------------------------------------------------------  
���D�渹�GBug #2618 - CHD9B03�i�x�ᵧ���X�{xx�����ɥ��Ѫ��T��
�ק�K�n�G�ץ�deleteCourtDBF ���Ѫ����D
��s�����G���x�ഫ V9611
�ק�H���GKevin Lin
�ק����G0961207
--------------------------------------------------------------------------------------------------------------------  
*/


public class DBFUtility{

	private String os = "linux";
	public  String o_url = "";
	private String url = "";
	private String userid = "";
	private String password = "";
	private Connection conn ;
	private Statement stmt;
	private String dbCharset = System.getProperty("file.encoding");
	private String FileSeperator = System.getProperty("file.separator");
	private boolean isDel = false;
	private DBUtility db;
	private DBFUtility dbf;
	private CheckObject check = new CheckObject();

	/**
	 * �غc��
     * @param url dbf���|
     * @param userid �ϥΪ̦W��
     * @param password �ϥΪ̱K�X
	*/
	public DBFUtility(String url,String userid,String password){
		this.o_url = url;
		this.url = url.replace('\\','/');
		this.userid = userid;
		this.password = password;
		makeDir(url);
    }

    /**
    * �غc��
    * @param url dbf���|
    * @param isDel �O�_�R���Ҧ���dbf�ɮסA�w�]false
    * @param db DBUtility
    */

    public DBFUtility(String url,boolean isDel,DBUtility db){
        this.o_url = url;
    	this.isDel = isDel;
		this.db = db;
    	this.url = url.replace('\\','/');
    	makeDir(url);
    }

    /**
	* �غc��
     * @param url dbf���|
     * @param userid �ϥΪ̦W��
     * @param password �ϥΪ̱K�X
     * @param isDel �O�_�R���Ҧ���dbf�ɮסA�w�]false
	*/
	public DBFUtility(String url,String userid,String password,boolean isDel){
		this.o_url = url;
		this.url = url.replace('\\','/');
		this.userid = userid;
		this.password = password;
		this.isDel = isDel;
		makeDir(url);
	}

   /**
    * �غc��
    * @param url dbf���|
    * @param isDel �O�_�R���Ҧ���dbf�ɮסA�w�]false
    */

    public DBFUtility(String url,boolean isDel){
        this.o_url = url;
    	this.isDel = isDel;
    	this.url = url.replace('\\','/');
    	makeDir(url);
    }

    /**
    * �غc��
    * @param url dbf���|
    */
    public DBFUtility(String url){
    	this.o_url = url;
    	this.url = url.replace('\\','/');
    	makeDir(url);
    }

	/**
	 * �}�Ҹ�Ʈw
	 * �ϥ� Clipper �榡�}��
	 */

	public void openConnection(){
		getDdfConnection("Clipper");
	}

	/**
	 *     �}�Ҹ�Ʈw
	 * 	�U�� index
	 *	FoxPro : CDX
	 *	Clipper: NTX
	 *	dBase  : MDX
	 *	dbtools: MTX
	 * @param xbase ���خ榡�� xbase �A�{�b�u���� "Clipper", "FoxPro" �G��
	 *
	 */
	public void openConnection(String xbase){
		getDdfConnection(xbase);
	}
	//�]�w��Ƥ��e���s�X�r��
	public void setDBFCharset(String charset){
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

	private void getDdfConnection(String xbase){
		try{
			if (isDel)
				delFile(o_url);

			File f = new File(url);
			if (!f.exists())
				f.mkdirs();
			f = null;

			os = checkOS();
			/*
			 * ����
			 * DBF_JDBC20.jar �M dbf.jar �O�P�@�����~�A�u�O�@�̱N package ���s�q�q��}���q���ӽ���A�� dbf.jar �令 DBF_JDBC20.jar
			 * DBF_JDBC20.jar ����s(com.hxtt.sql.dbf.DBFDriver)�A���ϥΤW�|��out of memory �����D�A�� char ���ץi�H�� 1000
			 * dbf.jar (zyh.sql.dbf.DBFDriver)����¡Achar ���פ���W�L 250�C
			 * ��2009 �~����A�k�|�O�ϥ�DBF_JDBC20.jar
			 * 
			 * 
			 * 2009.06.18
			 * ���֤��q�������t���ʶRŪ���L�naccess���� ACCESS_JDBC30.jar(�@�ˬODBF_JDBC20.jar���@��)
			 * �o�{��ثeDBF_JDBC20.jar�b package �|�۽�(DBF_JDBC20.jar �M ACCESS_JDBC30.jar ����package��class�w�q�ۦP�A���s�ª������P)
			 * �Ҷq�Ӱ��D��A�M�w����DBF_JDBC20.jar�A��θ��ª��� dbf.jar
			 */
			//�sdbf
			
			//String databaseDriverName ="com.hxtt.sql.dbf.DBFDriver";
			
			String databaseDriverName ="zyh.sql.dbf.DBFDriver";
			Class.forName(databaseDriverName ).newInstance();
			//DriverManager.registerDriver(new DBFDriver());
			if (os.equals("linux"))
				url = "jdbc:DBF:///"+url;
			else
				url = "jdbc:DBF:/"+url;
			
			System.out.println(url);
			Properties properties=new Properties();
			 /**
		            Now the created table can be opened by VFP5.0 :0x30,0x03,0x83,
		            Failed to pass the simplest test: 0x02
		           No test:...
		            30h	Visual FoxPro (default)
		            02h	FoxBase
		            03h	dBASE III w/o memo file (83h    dBASE III+ or Clipper V with memo file)
		            04h	dBASE IV or IV w/o memo file
		            05h	dBASE V w/o memo file
		            F5h	FoxPro with memo file
		            8Bh	dBASE IV with memo
		            8Eh	dBASE IV with SQL table
		            7Bh	dBASE IV with memo
		            */
		            xbase = xbase.toUpperCase();
			if  (xbase.equals("CLIPPER")){
				properties.setProperty("Version Number","03");
				properties.setProperty("Default Index Suffix","NTX");
				//properties.setProperty("lockType","CLIPPER");�����D
				//System.out.println("�ϥ� clipper ....");
			}else if (xbase.equals("FOXPRO")){
				properties.setProperty("Version Number","F5");
				//properties.setProperty("lockType","VFP");�����D
				properties.setProperty("Default Index Suffix","CDX");
				properties.setProperty("BLOCKSIZE","64");
				//System.out.println("�ϥ� foxpro ....");
			}

			properties.setProperty("user",userid);
			properties.setProperty("password",password);
			properties.setProperty("charSet","UTF-8");

			
			//conn = DriverManager.getConnection(url, userid,password);
			conn = DriverManager.getConnection(url,properties);
			stmt = conn.createStatement();
			stmt.setFetchSize(1000);
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/*
		�U�� index
		FoxPro : CDX
		Clipper: NTX
		dBase  : MDX
		dbtools: MTX
	*/
	//�R�� dbf ,dbt ,ntx ��
	private void delFile(String file1){
		try{
			File file = new File(file1);

			File[] f = file.listFiles();
			for(int i=0;i<f.length;i++){
				if (f[i].isFile()){
					String S =f[i].getName();
					if (S.lastIndexOf(".")!=-1){
						int k = S.lastIndexOf(".");
						String t = (S.substring(k+1,S.length()).toUpperCase());
						if (t.equals("DBF") || t.equals("DBT") || t.equals("NTX") || t.equals("CDX"))
							f[i].delete();
					}
				}
			}
			file = null;
		}catch(Exception e){
			System.out.println("�R���ɮץ��ѡC");
			e.printStackTrace();
		}
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
    public synchronized int doSqlUpdate(String sql) throws SQLException,IOException{
		int retVal = 0;

		sql = new String( sql.getBytes() , dbCharset );
		retVal = stmt.executeUpdate(sql);
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



	/**
	 * �N Oracle �W������ন DBF �A�ثe�u�䴩 VARCHAR2 �� NUMBER ��쫬�A
	 * @param sql SQL�y�k
	 * @param table ��X��DBF�ɦW(���n�t���ɦW .dbf)
	 * @return ���\�^�� 1�A���Ѧ^�� -1
	 */
	public int transferDbf(String sql,String table)
	{
		int err=1;
		int count=0;
		String colName="";//���W��
		int colType=0;//��쫬�A
		String cols="";//���W��
		try
		{
			ResultSet rs=db.doSqlSelect(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			while (rs.next())
			{
				if (count==0)
				{
					sql="CREATE TABLE "+table+" (";
					for(int j=1;j<=rsmd.getColumnCount();j++)
					{
						colName= setLength(rsmd.getColumnName(j),10);
						cols+=colName+",";
						colType=rsmd.getColumnType(j);
						if (colType==2)
							sql+=""+colName+" NUMERIC("+rsmd.getPrecision(j)+","+rsmd.getScale(j)+"),";
						if (colType==12)
							sql+=colName+" CHAR("+rsmd.getColumnDisplaySize(j)+"),";
					}
					sql=sql.substring(0,sql.length()-1);
					sql+=")";
					//System.out.println(sql);
					cols=cols.substring(0,cols.length()-1);
					doSqlUpdate(sql);
				}
				sql="INSERT INTO "+table+" ("+cols+") VALUES (";
				for(int j=1;j<=rsmd.getColumnCount();j++)
				{
					colType=rsmd.getColumnType(j);
					if (colType==2)
						sql+=checkNull(rs.getString(j),"0")+",";
					if (colType==12)
						sql+="'"+ checkNull(rs.getString(j),"")+"',";
				}
				sql=sql.substring(0,sql.length()-1);
				sql+=")";
				//System.out.println(sql);
				doSqlUpdate(sql);
				count++;
			}
		}
		catch(Exception ex)
		{
			System.out.println("transferDbf"+ex);
			err = -1;
		}
		return err;
	}

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



	/**
	 * �Ndbf����নoracle insert SQL�y�k
	 * @param path SQL��r�ɸ��|
	 * @param dbfsql dbf SQL�y�k
	 * @param fields sql��r�ɸ��|
	 * @param owner oracle��owner
	 * @param table oracle��table
	 * @param fields_flag SQL�y�k�O�_�����
	 * @return ���\�Ǧ^1�A���ѶǦ^-1
	 */
	public int transferSql(String path,String dbfSql,int fields,String owner,String table,boolean fields_flag)
	{
		int err=-1;
		Vector vr=new Vector();
		String sql="";
		String temp="";
		try
		{
			vr=doSqlSelect(dbfSql,fields,true);
			if (vr.size()!=0)
			{
				dbfSql=dbfSql.toUpperCase();
				utility util=new utility();
				BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"\\"+table+".sql",false),"UTF-8"));
				for (int i=0;i<(vr.size()/fields);i++)
				{
					sql=getInsertSql(vr,owner,table,i,fields,fields_flag,dbfSql);
					//System.out.println(sql);
					sql+="\r\n";
					writer.write(sql);
					writer.flush();
				}
				writer.close();
			}
		}
		catch(Exception ex)
		{
			System.out.println("transferSql:"+ex);
			err = -1;
		}
		return err;
	}


	/**
	 * �N DBF�W ������ন ORACLE ��ơA���R��DBF��
	 * @param dbfsql dbf SQL�y�k
	 * @param owner oracle owner
	 * @param table oracle table
	 * @param orafield oracle ���T���
	 * @param oracle sql �O�_�[ ;
	 * @return ���\�^�� 1�A���Ѧ^�� -1
	 */

	public int transferOracle(String dbfsql,String owner,String table,String dbffield,String orafield,int fields,boolean semicolon)
	{
		int err=1;
		String sql="";
		try	{
			dbfsql=dbfsql.toUpperCase();
			utility util=new utility();
			Vector vr=doSqlSelect(dbfsql,fields,true);
			for (int i=0;i<(vr.size()/fields);i++){
				sql=getInsertSql(vr,owner,table,i,fields,true,dbfsql,semicolon);
				sql=util.StrTran(sql,dbffield.toUpperCase(),orafield.toUpperCase());
				err=db.doSqlUpdate(sql);
				if (err<0) break;
			}
		}catch(Exception ex){
			System.out.println("transferOracle"+ex);
			err = -1;
		}
		return err;
	}


	public int transferOracle(String dbfsql,String owner,String table,String dbffield,String orafield,int fields)
	{
		int err=1;
		String sql="";
		try
		{
			dbfsql=dbfsql.toUpperCase();
			utility util=new utility();
			Vector vr=doSqlSelect(dbfsql,fields,true);
			for (int i=0;i<(vr.size()/fields);i++)
			{
				sql=getInsertSql(vr,owner,table,i,fields,true,dbfsql);
				sql=util.StrTran(sql,dbffield.toUpperCase(),orafield.toUpperCase());
				//System.out.println(sql);
				err=db.doSqlUpdate(sql);
				if (err<0) break;
			}
		}
		catch(Exception ex)
		{
			System.out.println("transferOracle"+ex);
			err = -1;
		}
		return err;
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
	/**
	 * �ˬd�O�_��DBF��
	 * @param table �ɦW (���t���ɦW)
	 * @param isDel �s�b�O�_�۰ʧR��
	 * @return �s�b�^�� true �A���b�^�� false
	 */
	public boolean Exists(String table,boolean isDel)
	{
		boolean retVal = false;
		String a = o_url.substring(o_url.length()-1,o_url.length());
		String path = "";
		if (a.equals(File.separator))
			path = o_url + table + ".DBF" ;
		else
			path = o_url + File.separator + table + ".DBF";

		File f = new File(path);
		if (f.exists())
		{
			if (isDel)
				f.delete();


			retVal = true;
		}
		f = null;
		return retVal;
	}

	//���Xoracle insert sql
	private String getInsertSql(Vector vr,String owner,String table,int i,int fields,boolean fields_flag,String dbfSql)
	{
		String sql="";
		sql="INSERT INTO "+owner+"."+table+" ";
		if (fields_flag)
			sql+="("+(dbfSql.substring((dbfSql.indexOf("SELECT")+6),dbfSql.indexOf("FROM"))).trim()+") ";

		sql+="VALUES(";
		for (int j=0;j<fields;j++)
		{
			if (vr.elementAt(i*fields+j)==null)
				sql+="'',";
			else
				sql+="'"+vr.elementAt(i*fields+j)+"',";
		}
		sql=sql.substring(0,sql.length()-1);
		sql+=");";
		return sql;
	}


	//���Xoracle insert sql
	private String getInsertSql(Vector vr,String owner,String table,int i,int fields,boolean fields_flag,String dbfSql,boolean semicolon)
	{
		String sql="";
		sql="INSERT INTO "+owner+"."+table+" ";
		if (fields_flag)
			sql+="("+(dbfSql.substring((dbfSql.indexOf("SELECT")+6),dbfSql.indexOf("FROM"))).trim()+") ";

		sql+="VALUES(";
		for (int j=0;j<fields;j++)
		{
			if (vr.elementAt(i*fields+j)==null)
				sql+="'',";
			else
				sql+="'"+vr.elementAt(i*fields+j)+"',";
		}
		sql=sql.substring(0,sql.length()-1);
		sql+=")";
		if(semicolon)
			sql+=";";
		return sql;
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

/*************************** ���ե� START ********************************/
	public static void main(String[] args) throws Exception
	{
		DBUtility db=new DBUtility();
		db.openConnection();
		DBFUtility dbf = new DBFUtility("c:\\temp\\dbf",true,db);
		dbf.transferDbf("select * from g.s03 " ,"S03");
		dbf.closeConnection();
		db.closeConnection();
	}
/*************************** ���ե� END ********************************/

}