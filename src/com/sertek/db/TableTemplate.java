/*
**********************************************************
* Author             : Kevin Tian
* Finished Date      : 2003/06/22
* Description        : 所有 table class 的父類別
**********************************************************
* Modified By        : Eve Chiu
* Last Modified Date : 2003/06/24
* Description        : 修改為abstract
**********************************************************
* Modified By        : nono
* Last Modified Date : 2003/06/25
* Description        : 修改傳入參數值
**********************************************************
* Modified By        : Kevin Tian
* Last Modified Date : 2003/07/09
* Description        : 加入 getMaxNo()
**********************************************************
* Modified By        : Kevin Tian
* Last Modified Date : 2003/07/25
* Description        : 加入 getMaxNo(String field, String condition, int string_length)
**********************************************************
*/
package com.sertek.db;

import com.sertek.db.DBUtility;
import com.sertek.db.SelectResult;
import com.sertek.util.*;

abstract public class TableTemplate
{
	public	DBUtility	db;
	public	String		owner		= "";
	public	String		tableName	= "";
	public	String		classname	= "";
	public	DB_FunctionE	dbf		= new DB_FunctionE();
	/**
	*執行 SQL 指令
	* @param sql
	*/
	public void executeSql(String sql) throws Exception
	{
		try
		{
			dbf.executeSql(db, sql);
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".executeSql() Exception: \r\n" + err.toString());
		}
	}

	/**
	*計算筆數
	* @param condition
	* @return int
	*/
	public int doCount(String condition) throws Exception
	{
		try
		{
			return dbf.doCount(db, owner, tableName, condition);
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".doCount() Exception: \r\n" + err.toString());
		}
	}

	/**
	*刪除該筆
	* @param condition
	* @return
	*/
	public void doDelete(String condition) throws Exception
	{
		try
		{
			dbf.doDelete(db, owner, tableName, condition);
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".doDelete() Exception: \r\n" + err.toString());
		}
	}

	/**
	*新增
	* @param columnName
	* @param columnValue
	* @return
	*/
	public void doInsert(String[] columnName, String[] columnValue) throws Exception
	{
		try
		{
			dbf.doInsert(db, owner, tableName, columnName, columnValue);
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".doInsert() Exception: \r\n" + err.toString());
		}
	}

	/**
	*查詢
	* @param columnName
	* @param condition
	* @return SelectResultE
	*/
	public SelectResult doSelect(String[] columnName, String condition) throws Exception
	{
		try
		{
			SelectResult SelectResult = new SelectResult(dbf.doSelect(db, owner, tableName, columnName, condition), columnName);
			return SelectResult;
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".doSelect(String[] columnName, String condition) Exception: \r\n" + err.toString());
		}
	}

	/**
	*查詢
	* @param SqlStment
	* @param columnName
	* @param condition
	* @return SelectResultE
	*/
	public SelectResult doSelect(String SqlStment, String[] columnName) throws Exception
	{
		try
		{
			SelectResult	SelectResult	= new SelectResult(dbf.doSelect(db, owner, columnName, SqlStment), columnName);
			return SelectResult;
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".doSelect(String SqlStment, String[] columnName, String condition) Exception: \r\n" + err.toString());
		}
	}

	/**
	*更新
	* @param columnName
	* @param columnValue	傳入不包含單引號的欄位值，Exp {abc, cb, d}
	* @param condition
	* @return
	*/
	public void doUpdate(String[] columnName, String[] columnValue, String condition) throws Exception
	{
		try
		{
			dbf.doUpdate(db, owner, tableName, columnName, columnValue, condition, "1");
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".doUpdate Exception: \r\n" + err.toString());
		}
	}

	/**
	*更新	- for 需更新自己Table欄未使用
	* @param columnName
	* @param columnValue	傳入包含單引號的欄位值，Exp {'abc', 'cb', 'd'}
	* @param condition
	* @return
	*/
	public void doUpdate1(String[] columnName, String[] columnValue, String condition) throws Exception
	{
		try
		{
			dbf.doUpdate(db, owner, tableName, columnName, columnValue, condition, "2");
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".doUpdate1 Exception: \r\n" + err.toString());
		}
	}

       /**
	* 取得欄位最大值(已加一了), 目前只能使用內容為純數字者
	* @param field         要搜尋的欄位名稱
	* @param condition     其它條件式，例如"name = '王文奇'"
	* @return
	* @throws Exception
	*/
	public String getMaxNo(String field, String condition) throws Exception
	{
		try
		{
			String[] columnName = {field};
			condition += " order by length(" +field + ") desc,"+ field +" desc";

			String	retVal		= "";		//回傳值
			String	value		= "";

                        SelectResult   sr      = doSelect(columnName, condition);
			if (sr.next())
			{
				value	= sr.getRow(field, "0");
				retVal	= String.valueOf(Integer.parseInt(value) + 1);
			}
			else
			{
			    retVal = "1";
			}
			return retVal;
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".getMaxNo Exception: \r\n" + err.toString());
		}
	}

	/**
	* 取得欄位最大值(已加一了), 取出來的值可自動補零; 若字串長度設為 6 ,而取出的最大值為'3' , 則會回傳 '000003'
	* @param field         要搜尋的欄位名稱
	* @param condition     其它條件式，例如"name = '王文奇'"
	* @param string_length 字串長度
	* @return
	* @throws Exception
	*/
	public String getMaxNo(String field, String condition, int string_length) throws Exception
	{
		try
		{
			return util.fillStr(getMaxNo(field, condition), string_length, '0');
		}
		catch(Exception err)
		{
			throw new Exception(classname + ".getMaxNo Exception: \r\n" + err.toString());
		}
	}
}