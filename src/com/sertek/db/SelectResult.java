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
	 * 設定 SelectResult 的值
	 * @param vr 查詢結果
	 * @param columnName 欄位名稱
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
	 * 取得資料總筆數
	 * @return int 資料總筆數
	 */
	public int count() {
		return rowCount;
	}

	/**
	 * 判斷是否最後一筆
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
	 * 將Cursor移至最後一筆
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
	 * 將Cursor移至第一筆，從 0 開始
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
	 * 將Cursor移至指定筆數
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
	 * 取得目前Cursor所在位置資料並將Cursor往下移一筆
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
	 * 將指定位置的Vector轉為HashTable格式資料
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
	 * 取得目前Cursor所在位置資料列中指定欄位的值(String)
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
	 * 取得目前Cursor所在位置資料列中指定欄位的值(Integer)
	 */
	public int getInt(String columnName) throws Exception {
		try {
			return Integer.parseInt(getRow(columnName.toUpperCase()));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 取得目前Cursor所在位置資料列中指定欄位的值(double)
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
	 * 取得目前Cursor所在位置資料列中指定欄位的值
	 */
	public String getRow(String columnName) throws Exception {
		try {
			if (!rocordHashTable.containsKey(columnName.toUpperCase()))
				throw new Exception("你沒有 select " + columnName + " 此欄位 \r\n");

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
	 * 取得目前Cursor所在位置資料列中指定欄位的值，當為Null時預設值為 defaultValue
	 */
	public String getRow(String columnName, String defaultValue)
			throws Exception {
		try {
			if (!rocordHashTable.containsKey(columnName.toUpperCase()))
				throw new Exception("你沒有 select " + columnName + " 此欄位 \r\n");

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