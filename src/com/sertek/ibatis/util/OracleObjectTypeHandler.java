package com.sertek.ibatis.util;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

import com.ibatis.sqlmap.engine.type.BaseTypeHandler;
import com.ibatis.sqlmap.engine.type.TypeHandler;
import com.sertek.util.DatabaseUtils;

public class OracleObjectTypeHandler extends BaseTypeHandler implements
		TypeHandler {

	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		Object object = rs.getObject(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return fix(object);
        }
	}

	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		Object object = rs.getObject(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return fix(object);
        }
	}

	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		  Object object = cs.getObject(columnIndex);
	        if (cs.wasNull()) {
	            return null;
	        } else {
	            return fix(object);
	        }
	}

	public void setParameter(PreparedStatement ps, int i, Object parameter,
			String jdbcType) throws SQLException {
		 ps.setObject(i, parameter);
		// TODO Auto-generated method stub

	}

	public Object valueOf(String arg0) {
		// TODO Auto-generated method stub
		return fix(arg0);
	}
	protected Object fix(Object obj) {
        try {
            if (obj instanceof TIMESTAMP) {
                return new Date(((TIMESTAMP) obj).dateValue().getTime());
            } else if (obj instanceof DATE) {
                return new Date(((DATE) obj).dateValue().getTime());
            } else if (obj instanceof TIMESTAMPLTZ) {
                return new Date(((TIMESTAMPLTZ) obj).dateValue().getTime());
            } else if (obj instanceof TIMESTAMPTZ) {
                return new Date(((TIMESTAMPTZ) obj).dateValue().getTime());
            } else if (obj instanceof String){
            	return DatabaseUtils.transCharset4db2Client((String)obj);
            }else{	
                return obj;
            }
        } catch (Exception e) {
            return obj;
        }
    }
}
