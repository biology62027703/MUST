package com.sertek.ibatis.util;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import com.sertek.util.DatabaseUtils;

public class EncodingStringTypeHandlerCallback implements TypeHandlerCallback {

	public Object getResult(ResultGetter getter) throws SQLException {
		return DatabaseUtils.transCharset4db2Client(getter.getString());
	}

	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		setter.setString(DatabaseUtils.transCharset4Client2db(parameter));
	}

	public Object valueOf(String arg0) {
		return DatabaseUtils.transCharset4db2Client(arg0);
	}
}