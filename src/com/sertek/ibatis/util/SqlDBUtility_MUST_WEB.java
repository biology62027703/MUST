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

public class SqlDBUtility_MUST_WEB extends SqlDBUtility {
		
}