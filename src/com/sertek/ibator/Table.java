package com.sertek.ibator;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private String tableName = "";
	
	private String tableComment = "";
	
	private List tabColumnList = new ArrayList();
	
	private List tabPrimaryKeyList = new ArrayList();
	
	public void addColumn(TabColumn column) {
		tabColumnList.add(column);
	}

	public void addPrimaryKey(TabPrimaryKey pk) {
		tabPrimaryKeyList.add(pk);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public List getTabColumnList() {
		return tabColumnList;
	}

	public void setTabColumnList(List tabColumnList) {
		this.tabColumnList = tabColumnList;
	}

	public List getTabPrimaryKeyList() {
		return tabPrimaryKeyList;
	}

	public void setTabPrimaryKeyList(List tabPrimaryKeyList) {
		this.tabPrimaryKeyList = tabPrimaryKeyList;
	}
}
