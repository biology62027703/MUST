package com.sertek.db;

import java.util.Vector;

public class DB_FunctionE
{
	/**
	*執行 SQL 指令
	* @param sql
	*/
	public void executeSql(DBUtility db, String sql) throws Exception
	{
		try
		{

			db.doSqlUpdate(sql);
		}
		catch(Exception err)
		{
			throw new Exception("com.sertek.db.DB_FunctionE.executeSql() Exception: \r\n" + err.toString());
		}
	}

	/**
	依據查詢條件修改指定欄位的資料，若要修改全部則 condition 傳入空白即可
	@param	db		DBUtility
	@param	owner		資料表的擁有人
	@param	tableName	資料表
	@param	columnName	資料表名稱
	@param	columnValue	欄位值
	@param	condition	查詢條件，若要修改全部則 condition 傳入空白即可
	@return
	*/
	public void doUpdate(DBUtility db, String owner, String tableName, String[] columnName, String[] columnValue, String condition, String updateType) throws Exception
	{
		StringBuffer	sql	= new StringBuffer();
		try
		{
			String	tmpStr	= "";

			if (updateType.equals("1"))
				tmpStr	= "'";

			sql.append("Update " + owner + "." + tableName + " Set ");

			for(int i = 0; i < columnName.length; i++)
			{
				if (i == 0)
					sql.append(columnName[i] + " = " + tmpStr + columnValue[i] + tmpStr);
				else
					sql.append(", " + columnName[i] + " = " + tmpStr + columnValue[i] + tmpStr);
			}

			if(condition.length() > 0)
				sql.append(" where " + condition);

			//logger.info("\r\nSQL: " + sql.toString() + "\r\n");

			db.doSqlUpdate(sql.toString());
		}
		catch(Exception err)
		{
			throw new Exception("com.sertek.db.DB_FunctionE.doUpdatei Exception: \r\n" + err.toString());
		}
		finally
		{
			sql	= null;
		}
	}

	/**
	新增指定欄位的資料
	@param	db		DBUtility
	@param	owner		資料表的擁有人
	@param	tableName	資料表名稱
	@param	columnName	欄位名稱
	@param	columnValue	欄位值
	@return
	*/
	public void doInsert(DBUtility db, String owner, String tableName, String[] columnName, String[] columnValue) throws Exception
	{
		StringBuffer	sql	= new StringBuffer();
		try
		{
			sql.append("Insert Into " + owner + "." + tableName + " (");

			for(int i = 0; i < columnName.length; i++)
			{
				if (i == 0)
					sql.append(columnName[i]);
				else
					sql.append(", " + columnName[i]);
			}

			sql.append(") values (");

			for(int i = 0; i < columnValue.length; i++)
			{
				if (i == 0)
					sql.append("'" + columnValue[i] + "'");
				else
					sql.append(", '" + columnValue[i] + "'");
			}
			sql.append(")");

			//logger.info("\r\nSQL: " + sql.toString() + "\r\n");

			db.doSqlUpdate(sql.toString());
		}
		catch(Exception err)
		{
			throw new Exception("com.sertek.db.DB_FunctionE.doInsert Exception: \r\n" + err.toString());
		}
		finally
		{
			sql	= null;
		}
	}

	/**
	刪除查詢條件的資料，若要刪除全部則 condition 傳入空白即可
	@param	db		DBUtility
	@param	owner		資料表的擁有人
	@param	tableName	要刪除資料的資料表
	@param	condition	刪除資料的條件，若要刪除全部則 condition 傳入空白即可
	@return
	*/
	public void doDelete(DBUtility db, String owner, String tableName, String condition) throws Exception
	{
		StringBuffer	sql	= new StringBuffer();

		try
		{
			sql.append("Delete From " + owner + "." + tableName);

			if(condition.length() > 0)
				sql.append(" where " + condition);

			//logger.info("\r\nSQL: " + sql.toString() + "\r\n");

			db.doSqlUpdate(sql.toString());
		}
		catch(Exception err)
		{
			throw new Exception("com.sertek.db.DB_FunctionE.doDelete Exception: \r\n" + err.toString());
		}
		finally
		{
			sql	= null;
		}
	}

	/**
	計算資料表中的資料筆數，若要計算全部筆數則 condition 傳入空白即可
	@param	db		DBUtility
	@param	owner		資料表的擁有人
	@param	tableName	資料表的名稱
	@param	condition	要計算筆數的條件，若要計算全部筆數則 condition 傳入空白即可
	@return	回傳計算的筆數資料(int)
	*/
	public int doCount(DBUtility db, String owner, String tableName, String condition) throws Exception
	{
		StringBuffer	sql	= new StringBuffer();
		try
		{
			sql.append("Select count(*) from " + owner + "." + tableName);

			if(condition.length() > 0)
				sql.append(" Where " + condition);

			//logger.info("\r\nSQL: " + sql.toString() + "\r\n");

			Vector	vr	= db.doSqlSelect(sql.toString(), 1, false);

			return Integer.parseInt((String)vr.elementAt(0));
		}
		catch(Exception err)
		{
			throw new Exception("com.sertek.db.DB_FunctionE.doCount(String tableName, String condition) Exception: \r\n" + err.toString());
		}
		finally
		{
			sql	= null;
		}
	}

	/**
	取得指定欄位的資料，若要取得所有資料則 condition 傳入空白即可
	@param	db		DBUtility
	@param	owner		資料表的擁有人
	@param 	tableName	資料表的名稱
	@param	columnName	欄位名稱
	@param 	condition	查詢條件，若要取得所有資料則 condition 傳入空白即可
	@return Vector
	*/
	public Vector doSelect(DBUtility db, String owner, String tableName, String[] columnName, String condition) throws Exception
	{
		StringBuffer	sql	= new StringBuffer();

		try
		{
			sql.append("Select ");

			for(int i = 0; i < columnName.length; i++)
			{
				if (i == 0)
					sql.append(columnName[i]);
				else
					sql.append(", " + columnName[i]);
			}
			sql.append(" From " + owner + "." + tableName);

			if(condition.length() > 0)
				sql.append(" where " + condition);

			//logger.info("\r\nSQL: " + sql.toString() + "\r\n");

			return db.doSqlSelect(sql.toString(), columnName.length, false);
		}
		catch(Exception err)
		{
			throw new Exception("com.sertek.db.DB_FunctionE.doSelect Exception: \r\n" + err.toString());
		}
		finally
		{
			sql	= null;
		}
	}

	/**
	取得 SqlStment 的資料
	@param	db		DBUtility
	@param	owner		資料表的擁有人
	@param	columnName	欄位名稱
	@param 	SqlStment	SQL 條件
	@return Vector
	*/
	public Vector doSelect(DBUtility db, String owner, String[] columnName, String SqlStment) throws Exception
	{
		try
		{
			//logger.info("SQL: " + SqlStment + "\r\n");

			return db.doSqlSelect(SqlStment, columnName.length, false);
		}
		catch(Exception err)
		{
			throw new Exception("com.sertek.db.DB_FunctionE.doSelect Exception: \r\n" + err.toString());
		}
	}
}