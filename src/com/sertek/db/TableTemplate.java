/*
**********************************************************
* Author             : Kevin Tian
* Finished Date      : 2003/06/22
* Description        : �Ҧ� table class �������O
**********************************************************
* Modified By        : Eve Chiu
* Last Modified Date : 2003/06/24
* Description        : �קאּabstract
**********************************************************
* Modified By        : nono
* Last Modified Date : 2003/06/25
* Description        : �ק�ǤJ�Ѽƭ�
**********************************************************
* Modified By        : Kevin Tian
* Last Modified Date : 2003/07/09
* Description        : �[�J getMaxNo()
**********************************************************
* Modified By        : Kevin Tian
* Last Modified Date : 2003/07/25
* Description        : �[�J getMaxNo(String field, String condition, int string_length)
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
	*���� SQL ���O
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
	*�p�ⵧ��
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
	*�R���ӵ�
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
	*�s�W
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
	*�d��
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
	*�d��
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
	*��s
	* @param columnName
	* @param columnValue	�ǤJ���]�t��޸������ȡAExp {abc, cb, d}
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
	*��s	- for �ݧ�s�ۤvTable�楼�ϥ�
	* @param columnName
	* @param columnValue	�ǤJ�]�t��޸������ȡAExp {'abc', 'cb', 'd'}
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
	* ���o���̤j��(�w�[�@�F), �ثe�u��ϥΤ��e���¼Ʀr��
	* @param field         �n�j�M�����W��
	* @param condition     �䥦���󦡡A�Ҧp"name = '����_'"
	* @return
	* @throws Exception
	*/
	public String getMaxNo(String field, String condition) throws Exception
	{
		try
		{
			String[] columnName = {field};
			condition += " order by length(" +field + ") desc,"+ field +" desc";

			String	retVal		= "";		//�^�ǭ�
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
	* ���o���̤j��(�w�[�@�F), ���X�Ӫ��ȥi�۰ʸɹs; �Y�r����׳]�� 6 ,�Ө��X���̤j�Ȭ�'3' , �h�|�^�� '000003'
	* @param field         �n�j�M�����W��
	* @param condition     �䥦���󦡡A�Ҧp"name = '����_'"
	* @param string_length �r�����
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