package com.sertek.db;

public class MultiTable extends TableTemplate {

	public MultiTable(DBUtility db) throws Exception {
		try {
			super.db = db;
			super.classname = "(extends TableTemplate)com.sertek.db.MultiTable";

		} catch (Exception err) {
			throw new Exception("com.sertek.db.MultiTable.MultiTable Exception: \r\n" + err.toString());
		}
	}

}