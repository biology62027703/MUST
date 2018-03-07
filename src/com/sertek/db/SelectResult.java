package com.sertek.db;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sertek.util.CheckObject;
import com.sertek.util.utility;

public class SelectResult {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private CheckObject check = new CheckObject();
	private Vector vr;
	private int rowCount;
	private String[] columnName;
	private Hashtable rocordHashTable;
	private int currentRecord = 0;

	private com.sertek.util.utility util = new utility();

	public SelectResult(List ls, String[] inColumnName) throws Exception {
		Vector vr = new Vector();
		for (int i = 0; i < ls.size(); i++) {
			HashMap map = (HashMap) ls.get(i);
			for (int j = 0; j < inColumnName.length; j++) {
				vr.add(check.checkNull(map.get(inColumnName[j]), "").toString());
			}
		}
		
		init(vr, inColumnName);
	}

	public SelectResult(Vector vr, String[] inColumnName) throws Exception {
		init(vr, inColumnName);
	}
	
	private void init(Vector vr, String[] inColumnName) throws Exception {
		try {
			this.vr = vr;
			this.columnName = inColumnName;
			for (int i = 0; i < columnName.length; i++)
				columnName[i] = columnName[i].toUpperCase();
			this.rowCount = (vr.size() / columnName.length);
			this.currentRecord = 0;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �]�w SelectResult ����
	 * @param vr �d�ߵ��G
	 * @param columnName ���W��
	 * @return
	 */
	public void setDate(Vector vr, String[] inColumnName) throws Exception {
		try {
			this.vr = vr;
			this.columnName = inColumnName;
			for (int i = 0; i < columnName.length; i++)
				columnName[i] = columnName[i].toUpperCase();
			this.rowCount = (vr.size() / columnName.length);
			this.currentRecord = 0;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * ���o����`����
	 * @return int ����`����
	 */
	public int count() {
		return rowCount;
	}

	/**
	 * �P�_�O�_�̫�@��
	 */
	public boolean isLast() throws Exception {
		try {
			if (currentRecord == rowCount)
				return true;
			else
				return false;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �NCursor���̫ܳ�@��
	 */
	public void last() throws Exception {
		try {
			currentRecord = rowCount - 1;
			setHasdTableRecord(currentRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �NCursor���ܲĤ@���A�q 0 �}�l
	 */
	public void first() throws Exception {
		try {
			currentRecord = 0;
			setHasdTableRecord(currentRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �NCursor���ܫ��w����
	 */
	public void move(int recordNum) throws Exception {
		try {
			currentRecord = recordNum;
			setHasdTableRecord(currentRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * ���o�ثeCursor�Ҧb��m��ƨñNCursor���U���@��
	 */
	public boolean next() throws Exception {
		try {
			boolean result;

			if (currentRecord >= rowCount)
				result = false;
			else {
				setHasdTableRecord(currentRecord);
				result = true;
			}
			currentRecord++;

			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �N���w��m��Vector�ରHashTable�榡���
	 */
	private void setHasdTableRecord(int rowNum) throws Exception {
		try {
			Hashtable tmpHashTable = new Hashtable();
			util.VrToHash(vr, rowNum, columnName, tmpHashTable);
			rocordHashTable = (Hashtable) tmpHashTable.clone();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * ���o�ثeCursor�Ҧb��m��ƦC�����w��쪺��(String)
	 */
	public String getStr(String columnName) throws Exception {
		try {
			return getRow(columnName.toUpperCase());
		} catch (Exception e) {
			logger.error("(columnName:" + columnName + ")");
			throw e;
		}
	}

	/**
	 * ���o�ثeCursor�Ҧb��m��ƦC�����w��쪺��(Integer)
	 */
	public int getInt(String columnName) throws Exception {
		try {
			return Integer.parseInt(getRow(columnName.toUpperCase()));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * ���o�ثeCursor�Ҧb��m��ƦC�����w��쪺��(double)
	 */
	public double getDouble(String columnName) throws Exception {
		try {
			return Double.parseDouble(getRow(columnName.toUpperCase()));
		} catch (Exception e) {
			logger.error("(columnName:" + columnName + ")");
			throw e;
		}
	}

	/**
	 * ���o�ثeCursor�Ҧb��m��ƦC�����w��쪺��
	 */
	public String getRow(String columnName) throws Exception {
		try {
			if (!rocordHashTable.containsKey(columnName.toUpperCase()))
				throw new Exception("�A�S�� select " + columnName + " ����� \r\n");

			if (rocordHashTable.get(columnName.toUpperCase()) == null)
				return "";
			else
				return rocordHashTable.get(columnName.toUpperCase()).toString();
		} catch (Exception e) {
			logger.error("(columnName:" + columnName + ")");
			throw e;
		}
	}

	/**
	 * ���o�ثeCursor�Ҧb��m��ƦC�����w��쪺�ȡA��Null�ɹw�]�Ȭ� defaultValue
	 */
	public String getRow(String columnName, String defaultValue)
			throws Exception {
		try {
			if (!rocordHashTable.containsKey(columnName.toUpperCase()))
				throw new Exception("�A�S�� select " + columnName + " ����� \r\n");

			if (rocordHashTable.get(columnName.toUpperCase()).equals(""))
				return defaultValue;
			else
				return rocordHashTable.get(columnName.toUpperCase()).toString();
		} catch (Exception e) {
			logger.error("(columnName:" + columnName + ")");
			throw e;
		}
	}
}