package com.sertek.db;

import java.util.Vector;

public class DB_FunctionE
{
	/**
	*���� SQL ���O
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
	�̾ڬd�߱���ק���w��쪺��ơA�Y�n�ק�����h condition �ǤJ�ťէY�i
	@param	db		DBUtility
	@param	owner		��ƪ��֦��H
	@param	tableName	��ƪ�
	@param	columnName	��ƪ�W��
	@param	columnValue	����
	@param	condition	�d�߱���A�Y�n�ק�����h condition �ǤJ�ťէY�i
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
	�s�W���w��쪺���
	@param	db		DBUtility
	@param	owner		��ƪ��֦��H
	@param	tableName	��ƪ�W��
	@param	columnName	���W��
	@param	columnValue	����
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
	�R���d�߱��󪺸�ơA�Y�n�R�������h condition �ǤJ�ťէY�i
	@param	db		DBUtility
	@param	owner		��ƪ��֦��H
	@param	tableName	�n�R����ƪ���ƪ�
	@param	condition	�R����ƪ�����A�Y�n�R�������h condition �ǤJ�ťէY�i
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
	�p���ƪ�����Ƶ��ơA�Y�n�p��������ƫh condition �ǤJ�ťէY�i
	@param	db		DBUtility
	@param	owner		��ƪ��֦��H
	@param	tableName	��ƪ��W��
	@param	condition	�n�p�ⵧ�ƪ�����A�Y�n�p��������ƫh condition �ǤJ�ťէY�i
	@return	�^�ǭp�⪺���Ƹ��(int)
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
	���o���w��쪺��ơA�Y�n���o�Ҧ���ƫh condition �ǤJ�ťէY�i
	@param	db		DBUtility
	@param	owner		��ƪ��֦��H
	@param 	tableName	��ƪ��W��
	@param	columnName	���W��
	@param 	condition	�d�߱���A�Y�n���o�Ҧ���ƫh condition �ǤJ�ťէY�i
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
	���o SqlStment �����
	@param	db		DBUtility
	@param	owner		��ƪ��֦��H
	@param	columnName	���W��
	@param 	SqlStment	SQL ����
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