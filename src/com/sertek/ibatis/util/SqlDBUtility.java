package com.sertek.ibatis.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.sertek.sys.StaticObj;
import com.sertek.table.ENSET;
import com.sertek.util.CheckObject;
import com.sertek.util.CryptoUtil;
import com.sertek.util.DatabaseUtils;

public class SqlDBUtility extends SqlMapClientTemplate {
	private SqlMapClientTemplate sqlMapClientTemplate;
	private PlatformTransactionManager transactionManager;
	private TransactionStatus status = null;
	DefaultTransactionDefinition df = new DefaultTransactionDefinition();
	CheckObject check = new CheckObject();

	public void afterPropertiesSet() {
		super.afterPropertiesSet();
	}

	public synchronized TransactionStatus startTransaction() {
		status = transactionManager.getTransaction(df);
		return status;
	}

	/**
	 * commit
	 */
	public void commitTransaction(TransactionStatus status) {
		transactionManager.commit(status);
	}

	/**
	 * rollback
	 */
	public void rollbackTransaction(TransactionStatus status) {
		try {
			transactionManager.rollback(status);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public TransactionStatus getTransactionStatus() {
		return this.status;
	}

	public void delete(String statementName, Object parameterObject, int requiredRowsAffected) throws DataAccessException {
		super.delete(statementName, parameterObject, requiredRowsAffected);
	}

	public int delete(String statementName, Object parameterObject) throws DataAccessException {
		return super.delete(statementName, parameterObject);
	}

	public int delete(String statementName) throws DataAccessException {
		return super.delete(statementName);
	}

	public Object execute(SqlMapClientCallback arg0) throws DataAccessException {
		return super.execute(arg0);
	}

	public List executeWithListResult(SqlMapClientCallback action) throws DataAccessException {
		return super.executeWithListResult(action);
	}

	public Map executeWithMapResult(SqlMapClientCallback action) throws DataAccessException {
		return super.executeWithMapResult(action);
	}

	public DataSource getDataSource() {
		return super.getDataSource();
	}

	public SqlMapClient getSqlMapClient() {
		return super.getSqlMapClient();
	}

	public Object insert(String statementName, Object parameterObject) throws DataAccessException {
		return super.insert(statementName, parameterObject);
	}
	
	/**
	 * 加密insert
	 * @param tblnm
	 * @param statementName
	 * @param parameterMap
	 * @return
	 * @throws DataAccessException
	 */
	public Object insert(String tblnm, String statementName, HashMap parameterMap) throws DataAccessException {
		encParamMap(tblnm, parameterMap);
		return super.insert(statementName, parameterMap);
	}

	/**
	 * 判斷欄位是否要加密處理
	 * @param tblnm
	 * @param parameterMap
	 */
	private void encParamMap(String tblnm, HashMap parameterMap) {
		List ensetList = StaticObj.getEnsetList(tblnm);
		for (int i = 0; i < ensetList.size(); i++) {
			ENSET enset = (ENSET) ensetList.get(i);
			String key = enset.getColnm().toLowerCase();
			if (parameterMap.containsKey(key)) {
				String value = parameterMap.get(key).toString();
				parameterMap.put(key, CryptoUtil.encrypt(value));
			}
		}
	}

	public Object insert(String statementName) throws DataAccessException {
		return super.insert(statementName);
	}

	public List queryForList(String statementName, int skipResults, int maxResults) throws DataAccessException {
		return super.queryForList(statementName, skipResults, maxResults);
	}

	public List queryForListPage(String statementName, HashMap form) throws DataAccessException {
		if (form.get("pageSize") != null && form.get("pageNum") != null) {
			int pageSize = Integer.parseInt(check.checkNull(form.get("pageSize"), "10").toString());
			int pageNum = Integer.parseInt(check.checkNull(form.get("pageNum"), "1").toString());
			return queryForListPage(statementName, form, pageNum, pageSize);
		} else {
			return queryForList(statementName, form);
		}
	}

	public List queryForListPage(String statementName, Object parameterObject, int pageNum, int pageSize) throws DataAccessException {
		int skipResults = (pageNum - 1) * pageSize;
		int maxResults = pageSize;
		return super.queryForList(statementName, parameterObject, skipResults, maxResults);
	}

	public List queryForList(String statementName, Object parameterObject, int skipResults, int maxResults) throws DataAccessException {
		return super.queryForList(statementName, parameterObject, skipResults, maxResults);
	}

	public List queryForList(String statementName, Object parameterObject) throws DataAccessException {
		return super.queryForList(statementName, parameterObject);
	}

	public List queryForList(String statementName) throws DataAccessException {
		return super.queryForList(statementName);
	}

	public Map queryForMap(String statementName, Object parameterObject, String keyProperty, String valueProperty) throws DataAccessException {
		return super.queryForMap(statementName, parameterObject, keyProperty, valueProperty);
	}

	public Map queryForMap(String statementName, Object parameterObject, String keyProperty) throws DataAccessException {
		return super.queryForMap(statementName, parameterObject, keyProperty);
	}

	public Object queryForObject(String statementName, Object parameterObject, Object resultObject) throws DataAccessException {
		return super.queryForObject(statementName, parameterObject, resultObject);
	}

	public Object queryForObject(String statementName, Object parameterObject) throws DataAccessException {
		return super.queryForObject(statementName, parameterObject);
	}

	public Object queryForObject(String statementName) throws DataAccessException {
		return super.queryForObject(statementName);
	}

	public void queryWithRowHandler(String statementName, Object parameterObject, RowHandler rowHandler) throws DataAccessException {
		super.queryWithRowHandler(statementName, parameterObject, rowHandler);
	}

	public void queryWithRowHandler(String statementName, RowHandler rowHandler) throws DataAccessException {
		super.queryWithRowHandler(statementName, rowHandler);
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	public void update(String statementName, Object parameterObject, int requiredRowsAffected) throws DataAccessException {
		super.update(statementName, parameterObject, requiredRowsAffected);
	}

	public int update(String statementName, Object parameterObject) throws DataAccessException {
		return super.update(statementName, parameterObject);
	}
	
	/**
	 * 加密update
	 * @param tblnm
	 * @param statementName
	 * @param parameterMap
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String tblnm, String statementName, HashMap parameterMap) throws DataAccessException {
		encParamMap(tblnm, parameterMap);
		return super.update(statementName, parameterMap);
	}

	/**
	 * 直接下 sql 去 insert update delete
	 * 
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public int doSqlUpdate(String sql) throws DataAccessException {
		return super.update("UTIL.execute", DatabaseUtils.transCharset4Client2db(sql));
	}

	/**
	 * 使用範例 String sql ="select a.crmyy,a.crmid,a.CRMNO,a.rsn,b.address from
	 * g.c60 a,g.c61 b where a.crmyy=b.crmyy and a.crmid=b.crmid and
	 * a.crmno=b.crmno "; List l = sqlDBUtility.doSqlSelect(sql);
	 * 
	 * for(Iterator list = l.iterator();list.hasNext();){ Map m =
	 * (Map)list.next(); //取出來的欄位名稱有分大小寫，依資料庫建立的大小寫為準
	 * System.out.println("crmyy="+m.get("CRMYY"));
	 * System.out.println("crmid="+m.get("CRMID"));
	 * System.out.println("crmno="+m.get("CRMNO"));
	 * System.out.println("rsn="+m.get("RSN")); System.out.println("address="+
	 * (String)m.get("ADDRESS")); }
	 * 
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public List doSqlSelect(String sql) throws DataAccessException {
		return super.queryForList("UTIL.executeSelectSql", DatabaseUtils.transCharset4Client2db(sql));
	}
	
	/**
	 * resultClass是java.util.LinkedHashMap
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public List doSqlSelect2(String sql) throws DataAccessException {
		return super.queryForList("UTIL.executeSelectSql2", DatabaseUtils.transCharset4Client2db(sql));
	}

	public int update(String statementName) throws DataAccessException {
		return super.update(statementName);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}